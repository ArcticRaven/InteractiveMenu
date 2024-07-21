package dev.arctic.interactivemenuapi;

import dev.arctic.interactivemenuapi.interfaces.IInteractiveMenu;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class InteractiveMenuBuilder {
    private final Plugin plugin;
    private Location rootLocation;
    private int timeoutSeconds = 60; // Default value
    private Player owner;
    private boolean autoCleanupEnabled = true;

    public InteractiveMenuBuilder(Plugin plugin) {
        this.plugin = plugin;
    }

    public InteractiveMenuBuilder rootLocation(Location location) {
        this.rootLocation = location;
        return this;
    }

    public InteractiveMenuBuilder timeoutSeconds(int seconds) {
        this.timeoutSeconds = seconds;
        return this;
    }

    public InteractiveMenuBuilder owner(Player player) {
        this.owner = player;
        return this;
    }

    public InteractiveMenuBuilder autoCleanupEnabled(boolean enabled) {
        this.autoCleanupEnabled = enabled;
        return this;
    }

    public IInteractiveMenu build() {
        if (plugin == null || rootLocation == null) {
            throw new IllegalStateException("Plugin and root location must be set");
        }
        InteractiveMenu menu = new InteractiveMenu(plugin, rootLocation, timeoutSeconds);
        menu.setOwner(owner);
        menu.setAutoCleanupEnabled(autoCleanupEnabled);
        return menu;
    }
}