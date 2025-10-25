package model;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private final String userId;
    private final String username;
    private final List<Playlist> userPlaylists; // Composition

    public User(String username) {
        this.userId = UUID.randomUUID().toString();
        this.username = username;
        this.userPlaylists = new ArrayList<>();
    }

    // Encapsulation: Getters
    public String getUsername() { return username; }
    public List<Playlist> getUserPlaylists() { return userPlaylists; }

    public void createPlaylist(String name) {
        userPlaylists.add(new Playlist(name));
    }
}