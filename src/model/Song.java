package model;
import java.util.UUID;

public class Song implements IPlayable {
    // Encapsulation
    private final String songId;
    private final String title;
    private final String artist;
    private final int durationSeconds;

    public Song(String title, String artist, int durationSeconds) {
        this.songId = UUID.randomUUID().toString();
        this.title = title;
        this.artist = artist;
        this.durationSeconds = durationSeconds;
    }

    // Encapsulation: Getters
    @Override
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public int getDurationSeconds() { return durationSeconds; }

    // Polymorphism implementation
    @Override
    public void play() {
        System.out.println("Now Playing: " + title + " by " + artist);
    }

    @Override
    public String getDetails() {
        return title + " - " + artist + " (" + (durationSeconds / 60) + ":" + String.format("%02d", durationSeconds % 60) + ")";
    }
}