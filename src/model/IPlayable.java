package model;

// Abstraction: Defines what any playable item must be able to do.
public interface IPlayable {
    String getTitle();
    String getDetails();
    void play();
}