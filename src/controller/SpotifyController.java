package controller;

import model.*;
import java.util.ArrayList;
import java.util.List;

public class SpotifyController {
    private final User currentUser;
    private final List<Song> allSongs;
    private final Player player;

    public SpotifyController() {
        this.player = new Player();
        this.allSongs = new ArrayList<>();

        // Dummy Song Data
        allSongs.add(new Song("Midnight Rain", "Taylor Swift", 174));
        allSongs.add(new Song("Blinding Lights", "The Weeknd", 200));
        allSongs.add(new Song("Levitating", "Dua Lipa", 203));
        allSongs.add(new Song("Stay", "The Kid LAROI", 141));
        allSongs.add(new Song("Heat Waves", "Glass Animals", 238));

        this.currentUser = new User("MusicLover99");

        // Dummy Playlist Data
        currentUser.createPlaylist("Favorites");
        currentUser.createPlaylist("Chill Mix");
        currentUser.createPlaylist("Gym Beats");

        // Add songs to the playlists
        currentUser.getUserPlaylists().get(0).addSong(allSongs.get(0));
        currentUser.getUserPlaylists().get(0).addSong(allSongs.get(2));
        currentUser.getUserPlaylists().get(1).addSong(allSongs.get(3));
        currentUser.getUserPlaylists().get(2).addSong(allSongs.get(1));
        currentUser.getUserPlaylists().get(2).addSong(allSongs.get(4));
    }

    // Public methods to interact with the model (View calls these)
    public Player getPlayer() {
        return player;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<Playlist> getCurrentUserPlaylists() {
        return currentUser.getUserPlaylists();
    }

    public List<Song> getAllSongs() {
        return allSongs;
    }

    public void playItem(IPlayable item) {
        player.play(item);
    }

    // NEW METHOD: Used by ArtistPanel to list relevant songs
    public List<Song> getSongsByArtist(String artistName) {
        List<Song> artistSongs = new ArrayList<>();
        // Mock logic to filter songs by artist (case-insensitive)
        for (Song song : allSongs) {
            if (song.getArtist().equalsIgnoreCase(artistName)) {
                artistSongs.add(song);
            }
        }
        return artistSongs;
    }
}