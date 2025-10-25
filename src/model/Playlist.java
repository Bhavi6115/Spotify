package model;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Playlist implements IPlayable {
    private final String playlistId;
    private String name;
    private final List<Song> songs; // Composition: Playlist HAS-A list of Songs

    public Playlist(String name) {
        this.playlistId = UUID.randomUUID().toString();
        this.name = name;
        this.songs = new ArrayList<>();
    }

    // Encapsulation: Getters and Setters
    public String getName() { return name; }
    public List<Song> getSongs() { return songs; }

    public void addSong(Song song) {
        this.songs.add(song);
    }

    @Override
    public void play() {
        System.out.println("Starting Playlist: " + name);
        if (!songs.isEmpty()) {
            songs.get(0).play();
        }
    }

    @Override
    public String getTitle() { return name; }

    @Override
    public String getDetails() {
        return songs.size() + " songs";
    }
}