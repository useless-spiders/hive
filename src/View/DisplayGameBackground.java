package View;

import Structure.FrameMetrics;

import javax.swing.*;
import java.awt.*;

public class DisplayGameBackground extends JPanel {
    private JFrame frame;
    private Image gameBackground;

    public DisplayGameBackground(JFrame frame) {
        this.frame = frame;
    }

    public void paintGameBackground(Graphics g) {
        //Affichage du background
        this.gameBackground = DisplayMain.loadBackground("Game_background.png");
        Dimension frameSize = FrameMetrics.getFrameSize(this.frame);
        g.drawImage(this.gameBackground, 0, 0, frameSize.width, frameSize.height, this);
    }
}
