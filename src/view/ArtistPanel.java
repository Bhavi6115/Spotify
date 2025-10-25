package view;

import controller.SpotifyController;
import model.Song;
import model.Player;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ArtistPanel extends JPanel {

    // Spotify Color Palette
    private static final Color DARK_BG = new Color(18, 18, 18);
    private static final Color SPOTIFY_GREEN = new Color(30, 215, 96);
    private static final Color TEXT_COLOR = new Color(255, 255, 255);
    private static final Color LIGHT_GREY = new Color(200, 147, 234);

    private final String artistName;
    private boolean isFollowing = false;
    private JButton followButton;
    private final SpotifyController controller;
    private final MainFrame mainFrame;

    public ArtistPanel(String artistName, SpotifyController controller, MainFrame mainFrame) {
        this.artistName = artistName;
        this.controller = controller;
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(DARK_BG);

        initializeUI();
    }

    private void initializeUI() {
        // Use a wrapper panel with BoxLayout for the top info and a list below it
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(DARK_BG);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Artist Info Header
        JLabel nameLabel = new JLabel(artistName);
        nameLabel.setForeground(TEXT_COLOR);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 48));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel followersLabel = new JLabel("1.5M Followers (Mock Data)");
        followersLabel.setForeground(LIGHT_GREY);
        followersLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        followersLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));
        followersLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        followButton = new JButton("FOLLOW");
        styleButton(followButton);
        followButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        followButton.addActionListener(e -> toggleFollow());

        contentPanel.add(nameLabel);
        contentPanel.add(followersLabel);
        contentPanel.add(followButton);

        // 2. Discography Header
        JLabel discographyHeader = new JLabel("Popular Tracks");
        discographyHeader.setForeground(SPOTIFY_GREEN);
        discographyHeader.setFont(new Font("Arial", Font.BOLD, 20));
        discographyHeader.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
        discographyHeader.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel.add(discographyHeader);

        // 3. Song List (Discography)
        JPanel songListPanel = createDiscographyList();
        songListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel.add(songListPanel);
        contentPanel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(DARK_BG);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createDiscographyList() {
        JPanel songList = new JPanel();
        songList.setLayout(new BoxLayout(songList, BoxLayout.Y_AXIS));
        songList.setBackground(DARK_BG);

        List<Song> songs = controller.getSongsByArtist(artistName);
        Player player = controller.getPlayer();
        MainFrame self = mainFrame;

        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);

            SongPanel songPanel = new SongPanel(
                    i + 1,
                    song,
                    // Play Action Lambda
                    () -> {
                        player.play(song);
                        self.updateNowPlaying(song);
                    },
                    // Pause Action Lambda
                    () -> {
                        player.pause();
                        self.updateNowPlaying(null);
                    }
            );

            songList.add(songPanel);
            songList.add(Box.createVerticalStrut(2));
        }

        return songList;
    }

    private void toggleFollow() {
        isFollowing = !isFollowing;
        if (isFollowing) {
            followButton.setText("FOLLOWING");
            followButton.setBackground(TEXT_COLOR);
            followButton.setForeground(DARK_BG);
            System.out.println("User is now FOLLOWING " + artistName);
        } else {
            followButton.setText("FOLLOW");
            followButton.setBackground(SPOTIFY_GREEN);
            followButton.setForeground(Color.BLACK);
            System.out.println("User UNFOLLOWED " + artistName);
        }
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 40));
        button.setMinimumSize(new Dimension(150, 40));
        button.setMaximumSize(new Dimension(150, 40));
        // Initial styling
        if (isFollowing) {
            button.setBackground(TEXT_COLOR);
            button.setForeground(DARK_BG);
        } else {
            button.setBackground(SPOTIFY_GREEN);
            button.setForeground(Color.BLACK);
        }
    }
}