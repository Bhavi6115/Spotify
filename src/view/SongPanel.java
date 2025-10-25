package view;

import model.Song;
import model.PlayableAction;
import javax.swing.*;
import java.awt.*;

// Reusable component for a single song listing
public class SongPanel extends JPanel {
    private final Song song;

    // Spotify Color Palette
    private static final Color DARK_BG = new Color(18, 18, 18);
    private static final Color SPOTIFY_GREEN = new Color(30, 215, 96);
    private static final Color TEXT_COLOR = new Color(255, 255, 255);
    private static final Color LIGHT_GREY = new Color(179, 179, 179);
    private static final Color HOVER_COLOR = new Color(40, 40, 40);

    // Corrected constructor accepting functional interfaces (PlayableAction)
    public SongPanel(int trackNum, Song song, PlayableAction playAction, PlayableAction pauseAction) {
        this.song = song;

        setLayout(new BorderLayout());
        setBackground(DARK_BG);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        // Mouse listener for hover effect
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { setBackground(HOVER_COLOR); }
            public void mouseExited(java.awt.event.MouseEvent evt) { setBackground(DARK_BG); }
            public void mouseClicked(java.awt.event.MouseEvent evt) { playAction.perform(); } // Play on click
        });

        // --- Left side: Track Number and Title ---
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftPanel.setBackground(getBackground());

        JLabel numLabel = new JLabel(String.valueOf(trackNum));
        numLabel.setForeground(LIGHT_GREY);
        leftPanel.add(numLabel);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBackground(getBackground());
        JLabel titleLabel = new JLabel(song.getTitle());
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JLabel artistLabel = new JLabel(song.getArtist());
        artistLabel.setForeground(LIGHT_GREY);
        artistLabel.setFont(new Font("Arial", Font.PLAIN, 11));

        titlePanel.add(titleLabel);
        titlePanel.add(artistLabel);
        leftPanel.add(titlePanel);

        add(leftPanel, BorderLayout.WEST);

        // --- Right side: Duration and Action Button ---
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightPanel.setBackground(getBackground());

        JLabel durationLabel = new JLabel(formatDuration(song.getDurationSeconds()));
        durationLabel.setForeground(LIGHT_GREY);
        rightPanel.add(durationLabel);

        // Play/Pause Control Buttons
        JButton playBtn = new JButton("\u25B6");
        JButton pauseBtn = new JButton("||");

        styleControlButton(playBtn, SPOTIFY_GREEN, Color.BLACK);
        styleControlButton(pauseBtn, TEXT_COLOR, Color.BLACK);

        playBtn.addActionListener(e -> playAction.perform()); // Lambda for Play
        pauseBtn.addActionListener(e -> pauseAction.perform()); // Lambda for Pause

        rightPanel.add(playBtn);
        rightPanel.add(pauseBtn);

        add(rightPanel, BorderLayout.EAST);
    }

    private void styleControlButton(JButton button, Color bgColor, Color fgColor) {
        button.setPreferredSize(new Dimension(35, 25));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("Arial", Font.BOLD, 10));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private String formatDuration(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}