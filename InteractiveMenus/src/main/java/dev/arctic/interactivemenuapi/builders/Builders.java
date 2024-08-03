package dev.arctic.interactivemenuapi.builders;

import dev.arctic.interactivemenuapi.interfaces.*;
import dev.arctic.interactivemenuapi.objects.*;
import dev.arctic.interactivemenuapi.animation.AnimationType;
import dev.arctic.interactivemenuapi.objects.elements.DisplayElement;
import dev.arctic.interactivemenuapi.objects.elements.OverlayElement;
import dev.arctic.interactivemenuapi.objects.elements.TextElement;
import dev.arctic.interactivemenuapi.objects.elements.ToggleElement;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class MenuBuilder {

    private Location rootLocation;
    private int timeoutSeconds;

    public MenuBuilder setRootLocation(Location rootLocation) {
        this.rootLocation = rootLocation;
        return this;
    }

    public MenuBuilder setTimeoutSeconds(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
        return this;
    }

    public IMenu build() {
        return (IMenu) new Menu(rootLocation, timeoutSeconds);
    }
}

public class DivisionBuilder {

    private Menu parentMenu;
    private Location initialLocation;
    private Vector offset;
    private AnimationType animationType = AnimationType.NONE;
    private double animationStepper = 0.0;

    public DivisionBuilder setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
        return this;
    }

    public DivisionBuilder setInitialLocation(Location initialLocation) {
        this.initialLocation = initialLocation;
        return this;
    }

    public DivisionBuilder setOffset(Vector offset) {
        this.offset = offset;
        return this;
    }

    public DivisionBuilder setAnimationType(AnimationType animationType) {
        this.animationType = animationType;
        return this;
    }

    public DivisionBuilder setAnimationStepper(double animationStepper) {
        this.animationStepper = animationStepper;
        return this;
    }

    public IDivision build() {
        return (IDivision) new Division(parentMenu, initialLocation, offset, animationType, animationStepper);
    }
}

public class TextElementBuilder {

    private Menu parentMenu;
    private Division parentDivision;
    private Vector offset;

    public TextElementBuilder setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
        return this;
    }

    public TextElementBuilder setParentDivision(Division parentDivision) {
        this.parentDivision = parentDivision;
        return this;
    }

    public TextElementBuilder setOffset(Vector offset) {
        this.offset = offset;
        return this;
    }

    public ITextElement build() {
        return (ITextElement) new TextElement(parentMenu, parentDivision, offset);
    }
}

public class ToggleElementBuilder {

    private Menu parentMenu;
    private Division parentDivision;
    private Vector offset;
    private AnimationType pressAnimationType = AnimationType.NONE;
    private double pressAnimationStepper = 0.0;

    public ToggleElementBuilder setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
        return this;
    }

    public ToggleElementBuilder setParentDivision(Division parentDivision) {
        this.parentDivision = parentDivision;
        return this;
    }

    public ToggleElementBuilder setOffset(Vector offset) {
        this.offset = offset;
        return this;
    }

    public ToggleElementBuilder setPressAnimationType(AnimationType pressAnimationType) {
        this.pressAnimationType = pressAnimationType;
        return this;
    }

    public ToggleElementBuilder setPressAnimationStepper(double pressAnimationStepper) {
        this.pressAnimationStepper = pressAnimationStepper;
        return this;
    }

    public IToggleElement build() {
        return (IToggleElement) new ToggleElement(parentMenu, parentDivision, offset, pressAnimationType, pressAnimationStepper);
    }
}

public class DisplayElementBuilder {

    private Menu parentMenu;
    private Division parentDivision;
    private Vector offset;
    private ItemStack displayItem;

    public DisplayElementBuilder setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
        return this;
    }

    public DisplayElementBuilder setParentDivision(Division parentDivision) {
        this.parentDivision = parentDivision;
        return this;
    }

    public DisplayElementBuilder setOffset(Vector offset) {
        this.offset = offset;
        return this;
    }

    public DisplayElementBuilder setDisplayItem(ItemStack displayItem) {
        this.displayItem = displayItem;
        return this;
    }

    public IDisplayElement build() {
        return (IDisplayElement) new DisplayElement(parentMenu, parentDivision, offset, displayItem);
    }
}

public class OverlayElementBuilder {

    private Menu parentMenu;
    private Division parentDivision;
    private Vector offset;
    private boolean interactToRemove = false;
    private long displayDuration = 0L;

    public OverlayElementBuilder setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
        return this;
    }

    public OverlayElementBuilder setParentDivision(Division parentDivision) {
        this.parentDivision = parentDivision;
        return this;
    }

    public OverlayElementBuilder setOffset(Vector offset) {
        this.offset = offset.add(new Vector(0, 0, 0.1));
        return this;
    }

    public OverlayElementBuilder setInteractToRemove(boolean interactToRemove) {
        this.interactToRemove = interactToRemove;
        return this;
    }

    public OverlayElementBuilder setDisplayDuration(long displayDuration) {
        this.displayDuration = displayDuration;
        return this;
    }

    public IOverlayElement build() {
        return (IOverlayElement) new OverlayElement(parentMenu, parentDivision, offset, interactToRemove, displayDuration);
    }
}
