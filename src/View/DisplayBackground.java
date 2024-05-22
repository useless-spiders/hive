package View;

import Structure.FrameMetrics;

import javax.swing.*;
import java.awt.*;

public class DisplayBackground extends JPanel {
    private Image gameBackground;
    private Image ruleBackground;

    public DisplayBackground() {}

    public void paintRule(Graphics g, JFrame frame, String ruleName) {
        //Affichage du background
        this.ruleBackground = DisplayMain.loadRules(ruleName);
        Dimension frameSize = FrameMetrics.getFrameSize(frame);
        g.drawImage(this.ruleBackground, 25, 25, frameSize.width - 130, frameSize.height - 130, this);
    }

    public void paintBackground(Graphics g, JFrame frame, String backGroundName) {
        //Affichage du background
        this.gameBackground = DisplayMain.loadBackground(backGroundName);
        Dimension frameSize = FrameMetrics.getFrameSize(frame);
        g.drawImage(this.gameBackground, 0, 0, frameSize.width, frameSize.height, this);
    }
}
