package View;

import javax.swing.*;
import java.awt.*;

public class DisplayInfoInGame extends JPanel {
    JFrame frame;
    private JLabel turnLabel;
    private JLabel namePlayeurLabel;

    public DisplayInfoInGame(JPanel panelGame) {
        setOpaque(false); // Rend le JPanel transparent pour afficher l'image en arrière-plan
        setLayout(new GridBagLayout()); // Définir le layout du JPanel

        JLabel infoLabel = new JLabel("Informations jeu");
        this.namePlayeurLabel = new JLabel("Tour de ");
        this.turnLabel = new JLabel("Tour : 0" );


        JPanel boxContainer  = new JPanel();
        boxContainer.setLayout(new BoxLayout(boxContainer, BoxLayout.Y_AXIS));
        boxContainer.setOpaque(false);
        boxContainer.add(infoLabel);
        boxContainer.add(this.namePlayeurLabel);
        boxContainer.add(this.turnLabel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 0, 0); // Espacement entre le bouton et le haut de la fenêtre
        gbc.anchor = GridBagConstraints.NORTHWEST;

        add(boxContainer);

        panelGame.add(this, gbc);
    }

    public void updatePrintInfo(String name, int turn) {
        this.namePlayeurLabel.setText("Tour de "+ name);
        this.turnLabel.setText("Turn : " + turn);
    }
}
