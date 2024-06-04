package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayText {

    /**
     * Ajoute un pop-up de texte à une JFrame.
     *
     * @param message String
     * @param frame JFrame
     */
    public static void addTextPopUp(String message, JFrame frame) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);

        JLabel messageLabel = new JLabel();
        messageLabel.setText(message);
        messageLabel.setVisible(true);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 30));
        messageLabel.setForeground(Color.WHITE); // Définir la couleur du texte en blanc pour la visibilité
        messageLabel.setOpaque(true); // Activer l'opacité pour définir la couleur de fond
        messageLabel.setBackground(Color.BLACK); // Définir la couleur de fond en noir

        panel.add(messageLabel);

        panel.setBounds(frame.getWidth() / 2 - 300, frame.getHeight() - 250, 600, 50);

        frame.getLayeredPane().add(panel, JLayeredPane.POPUP_LAYER);
        frame.repaint(); // Repeindre la fenêtre pour afficher le panneau
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
