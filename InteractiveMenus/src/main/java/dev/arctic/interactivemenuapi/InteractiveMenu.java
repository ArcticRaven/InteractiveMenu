package dev.arctic.interactivemenuapi;

import dev.arctic.interactivemenuapi.interfaces.IElement;
import dev.arctic.interactivemenuapi.interfaces.IInteractiveMenu;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
public class InteractiveMenu implements IInteractiveMenu {
    protected final Plugin plugin;
    protected Interaction anchorEntity;
    protected BukkitTask updateTask;
    protected List<IElement> elements = new CopyOnWriteArrayList<>();
    protected Location anchorLocation;
    protected Location rootLocation;
    protected Player owner = null;

    private int timeoutSeconds;
    private boolean autoCleanupEnabled = true;
    private long lastInteractionTime;

    public InteractiveMenu(Plugin plugin, Location rootLocation, int timeoutSeconds) {
        this.plugin = plugin;
        this.anchorLocation = rootLocation;
        this.rootLocation = rootLocation;
        this.timeoutSeconds = timeoutSeconds;
        this.lastInteractionTime = System.currentTimeMillis() / 1000;
        initializeMenu();
    }

    @Override
    public void initializeMenu() {
        createAnchor(new Vector(0, 0, 0));
        startRunnableUpdateGUI();
        startCleanupTask();
    }

    protected void createAnchor(Vector spawnOffset) {
        if (rootLocation.getWorld() == null) return;
        anchorEntity = rootLocation.getWorld().spawn(anchorLocation.add(spawnOffset), Interaction.class, interaction -> {
            interaction.setInteractionWidth(0f);
            interaction.setInteractionHeight(0f);
            interaction.setResponsive(false);
        });
    }

    @Override
    public void addElement(IElement element) {
        elements.add(element);
        updateLastInteractionTime();
    }

    @Override
    public void eventManager(UUID uuid, Player player) {
        for (IElement element : elements) {
            if (element.getInteractionUUID().equals(uuid)) {
                element.fireEvent(player);
                updateLastInteractionTime();
                return;
            }
        }
    }

    protected Player getClosestPlayer() {
        Player closestPlayer = null;
        double closestDistance = Double.MAX_VALUE;

        if (anchorLocation.getWorld() == null) return null;
        for (Player player : anchorLocation.getWorld().getPlayers()) {
            double distance = player.getLocation().distance(anchorLocation);
            if (distance < closestDistance && distance <= 10) {
                closestDistance = distance;
                closestPlayer = player;
            }
        }

        return closestPlayer;
    }

    protected void updateAnchorLocation() {
        // To be overridden by child classes for specific rotation logic
    }

    protected void updateMenuLocation() {
        for (IElement element : elements) {
            element.updateLocation(anchorEntity.getLocation());
        }
    }

    protected void startRunnableUpdateGUI() {
        updateTask = new BukkitRunnable() {
            @Override
            public void run() {
                updateAnchorLocation();
                updateMenuLocation();
            }
        }.runTaskTimer(plugin, 0L, 5L); // Update every 5 ticks
    }

    protected void startCleanupTask() {
        if (!autoCleanupEnabled) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (isTimeoutExceeded()) {
                    cleanup();
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L); // Check every second
    }

    private void updateLastInteractionTime() {
        this.lastInteractionTime = System.currentTimeMillis() / 1000;
    }

    private boolean isTimeoutExceeded() {
        return (System.currentTimeMillis() / 1000 - lastInteractionTime) >= timeoutSeconds;
    }

    @Override
    public void clearMenu() {
        for (IElement element : elements) {
            element.cleanup();
        }
        elements.clear();
    }

    @Override
    public void cleanup() {
        clearMenu(); // Clear all elements first
        try {
            anchorEntity.setPersistent(false);
            anchorEntity.remove();
        } catch (Exception e) {
            plugin.getLogger().warning("Error removing anchor entity: " + e.getMessage());
        }
        if (updateTask != null) {
            updateTask.cancel();
        }
    }

    @Override
    public Location getAnchorLocation() {
        return anchorLocation.clone();
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setAutoCleanupEnabled(boolean enabled) {
        this.autoCleanupEnabled = enabled;
    }

    public boolean isAutoCleanupEnabled() {
        return autoCleanupEnabled;
    }

    public void setTimeoutSeconds(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }
}