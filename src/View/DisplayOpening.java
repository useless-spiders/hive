package View;

import Pattern.GameActionHandler;
import Structure.RessourceLoader;

import javax.swing.*;
import java.awt.*;

public class DisplayOpening extends JPanel {
    JFrame frameOpening;
    private final Image background;
    private final GameActionHandler gameActionHandler;
    private final RessourceLoader ressourceLoader;

    /**
     * Constructeur de la classe DisplayOpening.
     *
     * @param frameOpening La JFrame de l'écran d'ouverture.
     * @param gameActionHandler Le gestionnaire des actions du jeu.
     */
    public DisplayOpening(JFrame frameOpening, GameActionHandler gameActionHandler) {
        this.frameOpening = frameOpening;
        this.gameActionHandler = gameActionHandler;
        this.ressourceLoader = new RessourceLoader(gameActionHandler);
        this.background = this.ressourceLoader.loadBackground("Opening.png");

        setOpaque(false); // Rend le JPanel transparent pour afficher l'image en arrière-plan
        setLayout(new GridBagLayout()); // Définir le layout du JPanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(610, 0, 0, 0); // Espacement entre le bouton et le haut de la fenêtre
        add(createButton(this.gameActionHandler.getLang().getString("display.opening.play")), gbc);

        frameOpening.setContentPane(this); // Définir le JPanel comme contenu de la JFrame
        frameOpening.pack(); // Redimensionne la JFrame pour adapter le JPanel
    }

    /**
     * Crée un bouton avec le texte spécifié et ajoute une action pour passer à l'écran de menu.
     *
     * @param text Le texte à afficher sur le bouton.
     * @return JButton configuré avec une action listener.
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 100)); // Définir une police pour le texte

        // Définir la bordure du bouton en noir
        button.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f), Color.BLACK));

        // Définir une taille personnalisée pour le bouton
        button.setPreferredSize(new Dimension(330, 140)); // Largeur : 300 pixels, Hauteur : 80 pixels

        // Définir les couleurs pour le clignotement
        Color color1 = new Color(255, 187, 89);  //orange
        Color color2 = new Color(173, 216, 230); //bleu

        // Créer un Timer pour changer la couleur du bouton à intervalles réguliers
        Timer timer = new Timer(1000, e -> {
            // Alterner entre les deux couleurs à chaque tic du Timer
            if (button.getBackground().equals(color1)) {
                button.setBackground(color2);
            } else {
                button.setBackground(color1);
            }
        });

        // Démarrer le Timer
        timer.start();

        button.addActionListener(e -> this.gameActionHandler.getPageController().openingToMenu());
        return button;
    }

    /**
     * Dessine le composant avec l'image de fond redimensionnée pour s'adapter à la taille du cadre.
     *
     * @param g Le contexte graphique.
     */
    @Override
    public void paintComponent(Graphics g) {
        // Affichage du background
        g.drawImage(this.background, 0, 0, this.frameOpening.getWidth(), this.frameOpening.getHeight(), this);
    }
}
