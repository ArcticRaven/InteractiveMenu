package dev.arctic.interactivemenuapi.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Vector;

@Getter
@Setter
public abstract class Element {

    protected Menu parentMenu;
    protected Division parentDivision;
    protected Location location;
    protected Vector offset;
    protected Interaction interactionEntity;
    protected TextDisplay textDisplayEntity;

    public Element(Menu parentMenu, Division parentDivision, Vector offset) {
        this.parentMenu = parentMenu;
        this.parentDivision = parentDivision;
        this.offset = offset;
        this.location = parentDivision.getCurrentLocation().clone().add(offset);
        initializeEntities();
    }

    protected void initializeEntities() {
        this.interactionEntity = location.getWorld().spawn(location, Interaction.class, interaction -> {
            interaction.setPersistent(false);
        });

        this.textDisplayEntity = location.getWorld().spawn(location, TextDisplay.class, textDisplay -> {
            textDisplay.setPersistent(false);
        });
    }

    public void updateLocation(Location divisionLocation) {
        Location newLocation = divisionLocation.clone().add(offset);
        interactionEntity.teleport(newLocation);
        textDisplayEntity.teleport(newLocation);

        this.location = newLocation;
    }

    public void cleanup() {
        if (interactionEntity != null && !interactionEntity.isDead()) {
            interactionEntity.remove();
        }
        if (textDisplayEntity != null && !textDisplayEntity.isDead()) {
            textDisplayEntity.remove();
        }
    }

    public abstract void onInteract();

    public abstract void applyAnimation();
}
