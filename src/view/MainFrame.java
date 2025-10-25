package view;

import controller.SpotifyController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private final SpotifyController controller;

    // Spotify Color Palette
    private static final Color DARK_BG = new Color(37, 4, 62, 205);
    private static final Color MEDIUM_BG = new Color(200, 113, 234);
    private static final Color SPOTIFY_GREEN = new Color(235, 202, 239);
    private static final Color TEXT_COLOR = new Color(19, 3, 28, 205); // White for primary text

    private JTabbedPane mainTabs;
    private JLabel nowPlayingLabel;
    private JButton playPauseButton;

    public MainFrame(SpotifyController controller) {
        this.controller = controller;
        setTitle("Spotify-Lite App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // --- COLOR CORRECTION FOR JTabbedPane ---
            // These ensure the tabs and their text are visible against the dark theme.
            UIManager.put("TabbedPane.selected", MEDIUM_BG);
            UIManager.put("TabbedPane.contentAreaColor", DARK_BG);
            UIManager.put("TabbedPane.borderHightlightColor", DARK_BG);
            UIManager.put("TabbedPane.foreground", TEXT_COLOR); // Tab text color
            UIManager.put("TabbedPane.background", DARK_BG);

        } catch (Exception e) {}

        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // --- 1. Left Sidebar (Navigation) ---
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        // --- 2. Main Content Area (Tabs) ---
        mainTabs = createMainContentTabs();
        add(mainTabs, BorderLayout.CENTER);

        // --- 3. Bottom Player Bar ---
        JPanel playerBar = createPlayerBar();
        add(playerBar, BorderLayout.SOUTH);

        getContentPane().setBackground(DARK_BG);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(111, 2, 154, 215));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));

        // Library Header
        JLabel libraryLabel = new JLabel("YOUR LIBRARY \uD83D\uDCDA");
        libraryLabel.setForeground(TEXT_COLOR);
        libraryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        libraryLabel.setBorder(BorderFactory.createEmptyBorder(20, 15, 10, 15));
        sidebar.add(libraryLabel);

        MainFrame self = this;

        // Add Artist Button to Sidebar
        JButton artistBtn = new JButton("\uD83C\uDFA4 Artist Page");
        styleSidebarButton(artistBtn);
        artistBtn.addActionListener(e -> {
            String artistTabName = "Artist: The Weeknd";
            int tabIndex = mainTabs.indexOfTab(artistTabName);
            if (tabIndex == -1) {
                // Instantiates ArtistPanel with required dependencies
                ArtistPanel artistPanel = new ArtistPanel("The Weeknd", controller, self);
                mainTabs.addTab(artistTabName, artistPanel);
                tabIndex = mainTabs.getTabCount() - 1;
            }
            mainTabs.setSelectedIndex(tabIndex);
        });
        sidebar.add(artistBtn);

        // Add separator
        sidebar.add(Box.createVerticalStrut(10));

        // Add Playlists to Sidebar & Tabbed Pane
        List<Playlist> playlists = controller.getCurrentUserPlaylists();

        for (Playlist playlist : playlists) {
            JButton playlistBtn = new JButton("\u266B " + playlist.getName());
            styleSidebarButton(playlistBtn);

            playlistBtn.addActionListener(e -> {
                String tabName = playlist.getName();
                int tabIndex = mainTabs.indexOfTab(tabName);
                if (tabIndex == -1) {
                    PlaylistPanel panel = new PlaylistPanel(playlist, controller, self);
                    mainTabs.addTab(tabName, panel);
                    tabIndex = mainTabs.getTabCount() - 1;
                }
                mainTabs.setSelectedIndex(tabIndex);
            });
            sidebar.add(playlistBtn);
        }

        sidebar.add(Box.createVerticalGlue());

        return sidebar;
    }

    private JTabbedPane createMainContentTabs() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(DARK_BG);
        tabs.setForeground(TEXT_COLOR);
        tabs.setFont(new Font("Arial", Font.BOLD, 12));

        // 1. "Discover" tab listing all songs
        JPanel allSongsPanel = createAllSongsPanel();
        tabs.addTab("\uD83C\uDFB5 Discover", allSongsPanel);

        return tabs;
    }

    private JPanel createAllSongsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(DARK_BG);

        JLabel header = new JLabel("  DISCOVER ALL SONGS");
        header.setForeground(SPOTIFY_GREEN);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        panel.add(header, BorderLayout.NORTH);

        JPanel songList = new JPanel();
        songList.setLayout(new BoxLayout(songList, BoxLayout.Y_AXIS));
        songList.setBackground(DARK_BG);

        List<Song> songs = controller.getAllSongs();
        Player player = controller.getPlayer();
        MainFrame self = this;

        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);

            // Lambda Usage: Play/Pause actions update the global player bar
            SongPanel songPanel = new SongPanel(
                    i + 1,
                    song,
                    () -> {
                        controller.playItem(song);
                        self.updateNowPlaying(song); // Update player bar
                    },
                    () -> {
                        player.pause();
                        self.updateNowPlaying(null); // Clear player bar
                    }
            );

            songList.add(songPanel);
            songList.add(Box.createVerticalStrut(2));
        }

        JScrollPane scrollPane = new JScrollPane(songList);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(DARK_BG);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createPlayerBar() {
        JPanel playerBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        playerBar.setPreferredSize(new Dimension(getWidth(), 60));
        playerBar.setBackground(MEDIUM_BG);

        nowPlayingLabel = new JLabel("Now Playing: Nothing selected...");
        nowPlayingLabel.setForeground(TEXT_COLOR);
        nowPlayingLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        playPauseButton = new JButton("\u25B6");
        styleButton(playPauseButton, SPOTIFY_GREEN, Color.BLACK);
        playPauseButton.setFont(new Font("Arial", Font.BOLD, 18));

        // Global Play/Pause action placeholder
        playPauseButton.addActionListener(e -> {
            if (playPauseButton.getText().equals("\u25B6")) {
                System.out.println("Global Play attempted.");
                playPauseButton.setText("\u23F8"); // Pause symbol
            } else {
                controller.getPlayer().pause();
                playPauseButton.setText("\u25B6"); // Play symbol
            }
        });

        playerBar.add(nowPlayingLabel);
        playerBar.add(playPauseButton);

        return playerBar;
    }

    // Public method to be called by SongPanel/PlaylistPanel to update status
    public void updateNowPlaying(IPlayable item) {
        if (item != null) {
            nowPlayingLabel.setText("Now Playing: " + item.getTitle() + " | " + item.getDetails());
            playPauseButton.setText("\u23F8"); // Pause symbol
        } else {
            nowPlayingLabel.setText("Now Playing: Nothing selected...");
            playPauseButton.setText("\u25B6"); // Play symbol
        }
    }

    // Helper methods
    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(40, 40));
    }

    private void styleSidebarButton(JButton button) {
        button.setBackground(new Color(202, 91, 237, 200));
        button.setForeground(TEXT_COLOR);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
    }
}