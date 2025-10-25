package model;

/**
 * Functional Interface to represent a simple playback action (e.g., play, pause).
 * This enables the use of lambda expressions for button actions.
 */
@FunctionalInterface
public interface PlayableAction {
    void perform();
}