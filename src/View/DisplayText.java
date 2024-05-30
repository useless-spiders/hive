package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayText {

    public static void addTextPopUp(String message, JFrame frame) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);

        JLabel messageLabel = new JLabel();
        messageLabel.setText(message);
        messageLabel.setVisible(true);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 30));
        messageLabel.setForeground(Color.WHITE); // Set the text color to red for visibility
        messageLabel.setOpaque(true); // Enable opacity to set background color
        messageLabel.setBackground(Color.BLACK); // Set background color to blue

        panel.add(messageLabel);

        panel.setBounds(frame.getWidth() / 2 - 300, frame.getHeight() - 250, 600, 50);

        frame.getLayeredPane().add(panel, JLayeredPane.POPUP_LAYER);
        frame.repaint(); // Repaint the frame to show the panel
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageLabel.setText("");
                messageLabel.setVisible(false);
                messageLabel.setOpaque(false);

            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}
