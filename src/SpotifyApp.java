import view.MainFrame;
import controller.SpotifyController;

import javax.swing.SwingUtilities;

public class SpotifyApp {
    public static void main(String[] args) {
        // Run the GUI creation on the Event Dispatch Thread (Swing standard practice)
        SwingUtilities.invokeLater(() -> {
            // Initialize Controller
            SpotifyController controller = new SpotifyController();

            // Initialize View
            MainFrame frame = new MainFrame(controller);

            // Display the application
            frame.setVisible(true);
        });
    }
}