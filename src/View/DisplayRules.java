package View;

import Pattern.GameActionHandler;
import Structure.RessourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class DisplayRules extends JPanel implements WindowListener {
    private final JFrame frameRules;
    int numRules = 1;
    Image background;
    private final int MIN = 1;
    private final int MAX = 11;

    private final JButton previous;
    private final JButton next;
    private final JButton close;

    private final GameActionHandler gameActionHandler;
    private final RessourceLoader ressourceLoader;

    public DisplayRules(JFrame frameRules, GameActionHandler gameActionHandler) {
        this.frameRules = frameRules;
        this.gameActionHandler = gameActionHandler;
        this.ressourceLoader = new RessourceLoader(gameActionHandler);
        this.background = this.ressourceLoader.loadRules("Rule_1.png");
        this.frameRules.addWindowListener(this);

        this.previous = this.createButtonPrevious();
        this.next = this.createButtonNext();
        this.close = this.createButtonClose();

        setOpaque(false); // Rend le JPanel transparent pour afficher l'image en arrière-plan
        setLayout(new GridBagLayout()); // Définir le layout du JPanel
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel navigatorButtonContainer = new JPanel(new GridBagLayout());
        navigatorButtonContainer.setOpaque(false);
        GridBagConstraints navigatorGbc = new GridBagConstraints();
        navigatorGbc.anchor = GridBagConstraints.CENTER; // Center alignment within the container
        navigatorGbc.gridheight = 1;

        navigatorGbc.gridx = 0;
        navigatorGbc.gridy = 0;
        navigatorButtonContainer.add(this.previous, navigatorGbc);

        navigatorGbc.gridx = 1;
        navigatorButtonContainer.add(this.next, navigatorGbc);

        navigatorGbc.gridx = 2; // Changer la position X du bouton "Close" vers la droite
        navigatorGbc.insets = new Insets(10, 30, 10, 10);
        navigatorButtonContainer.add(this.close, navigatorGbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.SOUTH; // Align the container to the bottom of the main panel
        gbc.weightx = 1.0; // Allow horizontal stretching
        gbc.weighty = 1.0; // Allow vertical stretching to push the container down
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding around the container
        gbc.fill = GridBagConstraints.HORIZONTAL; // Allow horizontal stretching
        add(navigatorButtonContainer, gbc);

        frameRules.setContentPane(this); // Définir le JPanel comme contenu de la JFrame
        frameRules.setResizable(false); // Désactiver le redimensionnement
        frameRules.pack(); // Redimensionne la JFrame pour adapter le JPanel
    }

    private void actionPrevious() {
        if (this.numRules > this.MIN) {
            this.numRules--;
            this.updateImage();
        }
    }

    private void actionNext() {
        if (this.numRules < this.MAX) {
            this.numRules++;
            this.updateImage();
        }
    }

    private void actionClose() {
        this.resetToFirstSlide();
        this.frameRules.setVisible(false);
    }

    private void updateImage() {
        this.background = this.ressourceLoader.loadRules("Rule_" + this.numRules + ".png");
        repaint();
    }

    private JButton createButtonPrevious() {
        JButton button = new JButton(this.gameActionHandler.getLang().getString("display.rules.previous"));
        button.setEnabled(this.numRules > this.MIN);
        button.addActionListener(e -> actionPrevious());
        return button;
    }


    private JButton createButtonNext() {
        JButton button = new JButton(this.gameActionHandler.getLang().getString("display.rules.next"));
        button.setEnabled(this.numRules < this.MAX);
        button.addActionListener(e -> actionNext());
        return button;
    }

    private JButton createButtonClose() {
        JButton button = new JButton(this.gameActionHandler.getLang().getString("display.rules.close"));
        button.addActionListener(e -> actionClose());
        return button;
    }

    private void updateButtons() {
        this.previous.setEnabled(this.numRules > this.MIN);
        this.next.setEnabled(this.numRules < this.MAX);
    }

    private void resetToFirstSlide() {
        this.numRules = 1;
        this.updateImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        this.updateButtons();
        g.drawImage(this.background, 25, 25, this.frameRules.getWidth() - 130, this.frameRules.getHeight() - 160, this);
    }

    @Override
    public void windowOpened(WindowEvent var1) {
    }

    @Override
    public void windowClosing(WindowEvent var1) {
        this.resetToFirstSlide();
    }

    @Override
    public void windowClosed(WindowEvent var1) {
    }

    @Override
    public void windowIconified(WindowEvent var1) {
    }

    @Override
    public void windowDeiconified(WindowEvent var1) {
    }

    @Override
    public void windowActivated(WindowEvent var1) {
    }

    @Override
    public void windowDeactivated(WindowEvent var1) {
    }
}
