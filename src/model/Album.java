package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Album implements IPlayable {
    // Encapsulation
    private final String albumId;
    private final String title;
    private final String artistName;
    private final int releaseYear;

    // Composition: An Album HAS-A list of Songs
    private final List<Song> songs;

    public Album(String title, String artistName, int releaseYear) {
        this.albumId = UUID.randomUUID().toString();
        this.title = title;
        this.artistName = artistName;
        this.releaseYear = releaseYear;
        this.songs = new ArrayList<>();
    }

    // Encapsulation: Getters
    @Override
    public String getTitle() {
        return title;
    }

    public String getArtistName() {
        return artistName;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public List<Song> getSongs() {
        return songs;
    }

    // Method to manage Composition relationship
    public void addSong(Song song) {
        this.songs.add(song);
    }

    // Polymorphism implementation: Play the whole album
    @Override
    public void play() {
        System.out.println("Starting Album Playback: " + title + " by " + artistName);
        if (!songs.isEmpty()) {
            songs.get(0).play(); // Start with the first track
        }
    }

    @Override
    public String getDetails() {
        return songs.size() + " tracks | Released: " + releaseYear;
    }
}