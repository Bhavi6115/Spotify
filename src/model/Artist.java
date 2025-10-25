package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Artist {
    // Encapsulation: Private fields
    private final String artistId;
    private final String name;
    private String bio;

    // Composition: An Artist HAS-A list of Songs and Albums
    private final List<Song> songs;
    private final List<Album> albums;

    public Artist(String name, String bio) {
        this.artistId = UUID.randomUUID().toString();
        this.name = name;
        this.bio = bio;
        this.songs = new ArrayList<>();
        this.albums = new ArrayList<>();
    }

    // Encapsulation: Getters
    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    // Encapsulation: Setter for Bio (since it might be updated)
    public void setBio(String bio) {
        this.bio = bio;
    }

    // Methods to manage Composition relationships
    public void addSong(Song song) {
        this.songs.add(song);
    }

    public void addAlbum(Album album) {
        this.albums.add(album);
    }

    public void displayInfo() {
        System.out.println("Artist: " + name + " (" + songs.size() + " songs, " + albums.size() + " albums)");
    }
}