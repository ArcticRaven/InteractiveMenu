package dev.arctic.interactivemenuapi.interfaces;

import dev.arctic.interactivemenuapi.objects.Division;
import dev.arctic.interactivemenuapi.objects.Menu;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * Interface representing a generic Element in the Interactive Menu API.
 * All specific element types should implement this interface or its sub-interfaces.
 */
public interface IElement {

    /**
     * Updates the location of the element relative to its parent division's location.
     *
     * @param divisionLocation The location of the parent division.
     */
    void updateLocation(Location divisionLocation);

    /**
     * Cleans up the element, removing any associated entities and freeing resources.
     */
    void cleanup();

    /**
     * Handles interactions with the element.
     */
    void onInteract();

    /**
     * Applies any necessary animations to the element.
     */
    void applyAnimation();

    /**
     * Gets the parent menu of this element.
     *
     * @return The parent menu.
     */
    Menu getParentMenu();

    /**
     * Sets the parent menu of this element.
     *
     * @param parentMenu The parent menu.
     */
    void setParentMenu(Menu parentMenu);

    /**
     * Gets the parent division of this element.
     *
     * @return The parent division.
     */
    Division getParentDivision();

    /**
     * Sets the parent division of this element.
     *
     * @param parentDivision The parent division.
     */
    void setParentDivision(Division parentDivision);

    /**
     * Gets the current location of this element.
     *
     * @return The current location.
     */
    Location getCurrentLocation();

    /**
     * Sets the current location of this element.
     *
     * @param currentLocation The current location.
     */
    void setCurrentLocation(Location currentLocation);

    /**
     * Gets the offset of this element relative to the parent division's location.
     *
     * @return The offset.
     */
    Vector getOffset();

    /**
     * Sets the offset of this element relative to the parent division's location.
     *
     * @param offset The offset.
     */
    void setOffset(Vector offset);
}

/**
 * Interface representing a Text Element in the Interactive Menu API.
 * Text Elements have no interactions or animations.
 */
public interface ITextElement extends IElement {
}

/**
 * Interface representing a Toggle Element in the Interactive Menu API.
 * Toggle Elements can switch between two states: Pressed and Unpressed.
 */
public interface IToggleElement extends IElement {

    /**
     * Toggles the state of the element.
     */
    void toggle();

    /**
     * Gets whether the element is currently pressed.
     *
     * @return True if pressed, false otherwise.
     */
    boolean isPressed();

    /**
     * Sets whether the element is currently pressed.
     *
     * @param pressed True if pressed, false otherwise.
     */
    void setPressed(boolean pressed);
}

/**
 * Interface representing a Display Element in the Interactive Menu API.
 * Display Elements show a single ItemStack.
 */
public interface IDisplayElement extends IElement {

    /**
     * Gets the item being displayed by this element.
     *
     * @return The displayed ItemStack.
     */
    ItemStack getDisplayItem();

    /**
     * Sets the item to be displayed by this element.
     *
     * @param displayItem The ItemStack to display.
     */
    void setDisplayItem(ItemStack displayItem);
}

/**
 * Interface representing an Overlay Element in the Interactive Menu API.
 * Overlay Elements are layered in front of other elements and can either be timed or interactively removed.
 */
public interface IOverlayElement extends IElement {

    /**
     * Gets whether the overlay element should be interactively removed.
     *
     * @return True if interactively removed, false otherwise.
     */
    boolean isInteractToRemove();

    /**
     * Sets whether the overlay element should be interactively removed.
     *
     * @param interactToRemove True if interactively removed, false otherwise.
     */
    void setInteractToRemove(boolean interactToRemove);

    /**
     * Gets the display duration of the overlay element in ticks.
     *
     * @return The display duration in ticks.
     */
    long getDisplayDuration();

    /**
     * Sets the display duration of the overlay element in ticks.
     *
     * @param displayDuration The display duration in ticks.
     */
    void setDisplayDuration(long displayDuration);
}
