package dev.arctic.interactivemenuapi;

import dev.arctic.interactivemenuapi.interfaces.IElement;
import dev.arctic.interactivemenuapi.interfaces.IInteractiveMenu;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class ElementBuilder {
    private Plugin plugin;
    private IInteractiveMenu menu;
    private Vector offset = new Vector(0, 0, 0);
    private String stringFlag;
    private Element.DisplayType displayType;
    private Object displayContent;
    private Element.AnimationType animationType = Element.AnimationType.NONE;

    public ElementBuilder(Plugin plugin, IInteractiveMenu menu) {
        this.plugin = plugin;
        this.menu = menu;
    }

    public ElementBuilder offset(Vector offset) {
        this.offset = offset;
        return this;
    }

    public ElementBuilder stringFlag(String flag) {
        this.stringFlag = flag;
        return this;
    }

    public ElementBuilder textDisplay(Component text) {
        this.displayType = Element.DisplayType.TEXT;
        this.displayContent = text;
        return this;
    }

    public ElementBuilder itemDisplay(ItemStack item) {
        this.displayType = Element.DisplayType.ITEM;
        this.displayContent = item;
        return this;
    }

    public ElementBuilder animationType(Element.AnimationType type) {
        this.animationType = type;
        return this;
    }

    public Element build() {
        if (plugin == null || menu == null || stringFlag == null || displayContent == null) {
            throw new IllegalStateException("Plugin, menu, string flag, and display content must be set");
        }
        return new Element(plugin, menu, offset, stringFlag, displayType, displayContent, animationType);
    }
}