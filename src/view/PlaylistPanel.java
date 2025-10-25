package view;

import controller.SpotifyController;
import model.Playlist;
import model.Song;
import model.Player;
import model.PlayableAction;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PlaylistPanel extends JPanel {
    private static final Color DARK_BG = new Color(18, 18, 18);
    private static final Color SPOTIFY_GREEN = new Color(30, 215, 96);

    public PlaylistPanel(Playlist playlist, SpotifyController controller, MainFrame mainFrame) {
        setLayout(new BorderLayout());
        setBackground(DARK_BG);

        // Header
        JLabel header = new JLabel("  PLAYLIST: " + playlist.getName() + " (" + playlist.getDetails() + ")");
        header.setForeground(SPOTIFY_GREEN);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(header, BorderLayout.NORTH);

        // Song List Area (Scrollable)
        JPanel songList = new JPanel();
        songList.setLayout(new BoxLayout(songList, BoxLayout.Y_AXIS));
        songList.setBackground(DARK_BG);

        List<Song> songs = playlist.getSongs();
        Player player = controller.getPlayer();

        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);

            // Correct Lambda Usage: Passes play/pause action to the SongPanel
            SongPanel songPanel = new SongPanel(
                    i + 1,
                    song,
                    // Play Action Lambda
                    () -> {
                        player.play(song);
                        mainFrame.updateNowPlaying(song);
                    },
                    // Pause Action Lambda
                    () -> {
                        player.pause();
                        mainFrame.updateNowPlaying(null);
                    }
            );

            songList.add(songPanel);
            songList.add(Box.createVerticalStrut(2));
        }

        JScrollPane scrollPane = new JScrollPane(songList);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(DARK_BG);

        add(scrollPane, BorderLayout.CENTER);
    }
}