package dev.arctic.interactivemenuapi;

import dev.arctic.interactivemenuapi.events.MenuInteractionEvent;
import dev.arctic.interactivemenuapi.interfaces.IElement;
import dev.arctic.interactivemenuapi.interfaces.IInteractiveMenu;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.UUID;

@Getter @Setter
public class Element implements IElement {
    public enum AnimationType {
        BUTTON,
        FADE,
        NONE
    }

    public enum DisplayType {
        TEXT,
        ITEM
    }

    private final Plugin plugin;
    private final IInteractiveMenu interactiveMenu;
    private final String stringFlag;
    private final Display displayEntity;
    private final Interaction interactionEntity;
    private final UUID cleanupUUID;
    private final Vector offset;
    private AnimationType animationType;
    private Location location;
    private boolean pressed = false;
    private DisplayType displayType;

    public Element(Plugin plugin, IInteractiveMenu interactiveMenu, Vector offset, String stringFlag,
                   DisplayType displayType, Object displayContent, AnimationType animationType) {
        this.plugin = plugin;
        this.interactiveMenu = interactiveMenu;
        this.offset = offset;
        this.stringFlag = stringFlag;
        this.animationType = animationType;
        this.displayType = displayType;
        this.location = interactiveMenu.getAnchorLocation().clone().add(offset);

        this.displayEntity = createDisplayEntity(displayContent);
        this.interactionEntity = createInteractionEntity();

        this.cleanupUUID = UUID.randomUUID();
        interactiveMenu.addElement(this);
    }

    private Display createDisplayEntity(Object displayContent) {
        if (displayType == DisplayType.TEXT && displayContent instanceof Component) {
            return location.getWorld().spawn(location, TextDisplay.class, textDisplay -> {
                textDisplay.text((Component) displayContent);
                textDisplay.setBillboard(Display.Billboard.CENTER);
            });
        } else if (displayType == DisplayType.ITEM && displayContent instanceof ItemStack) {
            return location.getWorld().spawn(location, ItemDisplay.class, itemDisplay -> {
                itemDisplay.setItemStack((ItemStack) displayContent);
                itemDisplay.setBillboard(Display.Billboard.CENTER);
            });
        } else {
            throw new IllegalArgumentException("Invalid display content for the specified display type");
        }
    }

    private Interaction createInteractionEntity() {
        return location.getWorld().spawn(location, Interaction.class, interaction -> {
            interaction.setPersistent(false);
            interaction.setMetadata("InteractiveMenu", new FixedMetadataValue(plugin, interactiveMenu));
        });
    }

    @Override
    public UUID getInteractionUUID() {
        return interactionEntity.getUniqueId();
    }

    @Override
    public void updateLocation(Location anchorLocation) {
        float yaw = anchorLocation.getYaw();
        Vector adjustedOffset = getAdjustedOffset(anchorLocation, offset, yaw);

        Location elementLocation = anchorLocation.clone().add(adjustedOffset);
        displayEntity.teleport(elementLocation);
        interactionEntity.teleport(elementLocation);

        displayEntity.setRotation(yaw, 0);
        interactionEntity.setRotation(yaw, 0);
    }

    private Vector getAdjustedOffset(Location anchorLocation, Vector offset, float yaw) {
        yaw = (yaw % 360 + 360) % 360;

        double x = offset.getX();
        double y = offset.getY();
        double z = offset.getZ();

        if (yaw >= 315 || yaw < 45) {
            return new Vector(x, y, z);
        } else if (yaw >= 45 && yaw < 135) {
            return new Vector(-z, y, x);
        } else if (yaw >= 135 && yaw < 225) {
            return new Vector(-x, y, -z);
        } else {
            return new Vector(z, y, -x);
        }
    }

    @Override
    public void fireEvent(Player player) {
        if (player != interactiveMenu.getOwner()) {
            player.sendMessage("You do not own this menu!");
            return;
        }
        if (!pressed) {
            pressed = true;
            if (animationType == AnimationType.BUTTON) {
                playButtonAnimation(player);
            } else {
                Bukkit.getPluginManager().callEvent(new MenuInteractionEvent(interactiveMenu, stringFlag, player));
            }
        }
    }

    private void playButtonAnimation(Player player) {
        // Implement button animation logic here
        // For example, you could scale the display entity briefly
        Transformation originalTransform = displayEntity.getTransformation();
        Vector3f originalScale = originalTransform.getScale();
        Vector3f newScale = new Vector3f(originalScale).mul(1.2f);  // Scale up by 20%

        Transformation scaledTransform = new Transformation(
                originalTransform.getTranslation(),
                originalTransform.getLeftRotation(),
                newScale,
                originalTransform.getRightRotation()
        );
        displayEntity.setTransformation(scaledTransform);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            displayEntity.setTransformation(originalTransform);
            Bukkit.getPluginManager().callEvent(new MenuInteractionEvent(interactiveMenu, stringFlag, player));
        }, 4L);
    }

    @Override
    public void cleanup() {
        if (displayEntity != null && !displayEntity.isDead()) {
            displayEntity.remove();
        }
        if (interactionEntity != null && !interactionEntity.isDead()) {
            interactionEntity.remove();
        }
        interactiveMenu.getElements().remove(this);
    }

    @Override
    public void setText(Component text) {
        if (displayType == DisplayType.TEXT && displayEntity instanceof TextDisplay) {
            ((TextDisplay) displayEntity).text(text);
        } else {
            throw new IllegalStateException("Cannot set text on a non-text display");
        }
    }

    @Override
    public void setTooltip(Component tooltip) {

    }

    @Override
    public void setSecondaryText(Component secondaryText) {

    }

    @Override
    public void setItem(ItemStack item) {
        if (displayType == DisplayType.ITEM && displayEntity instanceof ItemDisplay) {
            ((ItemDisplay) displayEntity).setItemStack(item);
        } else {
            throw new IllegalStateException("Cannot set item on a non-item display");
        }
    }

    @Override
    public void setScale(float scale) {
        Transformation currentTransform = displayEntity.getTransformation();
        Vector3f newScale = new Vector3f(scale, scale, scale);

        Transformation newTransform = new Transformation(
                currentTransform.getTranslation(),
                currentTransform.getLeftRotation(),
                newScale,
                currentTransform.getRightRotation()
        );
        displayEntity.setTransformation(newTransform);
    }

    @Override
    public void setRotation(float yaw, float pitch) {
        displayEntity.setRotation(yaw, pitch);
    }

    @Override
    public void setAnimationType(AnimationType animationType) {
        this.animationType = animationType;
    }

    @Override
    public AnimationType getAnimationType() {
        return this.animationType;
    }

    @Override
    public boolean isPressed() {
        return this.pressed;
    }

    @Override
    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
}