
import java.awt.Color;

import javax.swing.JFrame;

public class mainfile {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        frame.setBounds(10, 10, 905, 700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        Gamepanel panel = new Gamepanel();
        panel.setBackground(Color.darkGray);
        frame.add(panel);

        frame.setVisible(true);
    }
}
 