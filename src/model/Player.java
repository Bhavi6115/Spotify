package model;

/**
 * Handles the actual playback logic.
 */
public class Player {
    private IPlayable currentItem;

    public void play(IPlayable item) {
        this.currentItem = item;
        System.out.println("PLAYER ACTION: Starting playback of: " + item.getTitle());
        item.play();
    }

    public void pause() {
        if (currentItem != null) {
            System.out.println("PLAYER ACTION: Paused: " + currentItem.getTitle());
        } else {
            System.out.println("PLAYER ACTION: Nothing is currently playing to pause.");
        }
    }
}