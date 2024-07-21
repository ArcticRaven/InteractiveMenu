package dev.arctic.interactivemenuapi.interfaces;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public interface IInteractiveMenu {
    /**
     * Initializes the menu.
     */
    void initializeMenu();

    /**
     * Adds an element to the menu.
     * @param element The element to add.
     */
    void addElement(IElement element);

    /**
     * Handles interactions with menu elements.
     * @param uuid The UUID of the interacted element.
     * @param player The player who interacted with the element.
     */
    void eventManager(UUID uuid, Player player);

    /**
     * Removes all elements from the menu.
     */
    void clearMenu();

    /**
     * Cleans up the menu and its resources.
     */
    void cleanup();

    /**
     * Gets the location of the menu's anchor.
     * @return The anchor location.
     */
    Location getAnchorLocation();

    /**
     * Gets the owner of the menu.
     * @return The player who owns the menu.
     */
    Player getOwner();

    /**
     * Sets the owner of the menu.
     * @param owner The player to set as the owner.
     */
    void setOwner(Player owner);

    /**
     * Gets the list of elements in the menu.
     * @return The list of elements.
     */
    List<IElement> getElements();

    /**
     * Sets whether auto-cleanup is enabled.
     * @param enabled True to enable auto-cleanup, false otherwise.
     */
    void setAutoCleanupEnabled(boolean enabled);

    /**
     * Checks if auto-cleanup is enabled.
     * @return True if auto-cleanup is enabled, false otherwise.
     */
    boolean isAutoCleanupEnabled();

    /**
     * Sets the timeout duration in seconds.
     * @param timeoutSeconds The timeout duration in seconds.
     */
    void setTimeoutSeconds(int timeoutSeconds);

    /**
     * Gets the timeout duration in seconds.
     * @return The timeout duration in seconds.
     */
    int getTimeoutSeconds();
}