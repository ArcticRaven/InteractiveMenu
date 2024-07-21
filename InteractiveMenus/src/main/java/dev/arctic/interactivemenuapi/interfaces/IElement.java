package dev.arctic.interactivemenuapi.interfaces;

import dev.arctic.interactivemenuapi.Element;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface IElement {
    /**
     * Gets the UUID of the interaction entity associated with this element.
     * @return The UUID of the interaction entity.
     */
    UUID getInteractionUUID();

    /**
     * Updates the location of the element based on the anchor location.
     * @param anchorLocation The new anchor location.
     */
    void updateLocation(Location anchorLocation);

    /**
     * Handles the event when a player interacts with this element.
     * @param player The player who interacted with the element.
     */
    void fireEvent(Player player);

    /**
     * Cleans up the resources associated with this element.
     */
    void cleanup();

    /**
     * Sets the display text of the element.
     * @param text The new display text.
     */
    void setText(Component text);

    /**
     * Sets the tooltip text of the element.
     * @param tooltip The new tooltip text.
     */
    void setTooltip(Component tooltip);

    /**
     * Sets the secondary text of the element (used for button animations).
     * @param secondaryText The new secondary text.
     */
    void setSecondaryText(Component secondaryText);

    void setItem(ItemStack item);

    void setScale(float scale);

    void setRotation(float yaw, float pitch);

    /**
     * Sets the animation type of the element.
     * @param animationType The new animation type.
     */
    void setAnimationType(Element.AnimationType animationType);

    /**
     * Gets the current animation type of the element.
     * @return The current animation type.
     */
    Element.AnimationType getAnimationType();

    /**
     * Checks if the element is currently pressed.
     * @return True if the element is pressed, false otherwise.
     */
    boolean isPressed();

    void setPressed(boolean pressed);
}