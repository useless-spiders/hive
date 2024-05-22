package View;

import Structure.FrameMetrics;

import javax.swing.*;
import java.awt.*;

public class DisplayBackground extends JPanel {
    private Image gameBackground;

    public DisplayBackground() {}

    public void paintBackground(Graphics g, JFrame frame, String backGroundName) {
        //Affichage du background
        this.gameBackground = DisplayMain.loadBackground(backGroundName);
        Dimension frameSize = FrameMetrics.getFrameSize(frame);
        g.drawImage(this.gameBackground, 0, 0, frameSize.width, frameSize.height, this);
    }
}
