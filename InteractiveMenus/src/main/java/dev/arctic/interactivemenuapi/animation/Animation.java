package dev.arctic.interactivemenuapi.animation;

import org.bukkit.util.Vector;

public class Animation {
    private AnimationType type;
    private double stepper;

    public Animation(AnimationType type, double stepper) {
        this.type = type;
        this.stepper = stepper;
    }

    public AnimationResult apply() {
        Vector vectorChange = new Vector(0, 0, 0);
        double opacity = 1.0; // Default opacity

        switch (type) {
            case LEFT -> vectorChange.setX(-stepper);
            case RIGHT -> vectorChange.setX(stepper);
            case UP -> vectorChange.setY(stepper);
            case DOWN -> vectorChange.setY(-stepper);
            case FORWARD -> vectorChange.setZ(stepper);
            case BACKWARD -> vectorChange.setZ(-stepper);
            case VISIBILITY -> opacity = stepper;  // Assuming stepper represents opacity level
            case NONE -> {
                // No changes to vector or opacity
            }
        }

        return new AnimationResult(type, vectorChange, opacity);
    }

    public record AnimationResult(AnimationType type, Vector vectorChange, double opacity) {
    }
}
