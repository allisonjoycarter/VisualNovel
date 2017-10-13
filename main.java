package VisualNovel;

/**************************************************************
 * @author Allison Bickford
 *
 * This program creates a visual novel from a file of choices
 * and a file of story lines. Ideally you should be able to
 * write your own story with corresponding choices and results
 * and it would be implemented in the MainPanel
 *************************************************************/

import javax.swing.*;
import java.io.IOException;

public class main extends JFrame{

    public main() {

    }

    public static void main(String[] args) throws IOException {
        //Creating main frame and the layered panel from MainPanel
        JFrame frame = new JFrame("Best Story");
        JComponent pane = new MainPanel();

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        pane.setOpaque(true);
        frame.setContentPane(pane); //adding MainPanel to frame

        frame.pack(); //estimates frame size based on components in panels
        frame.setVisible(true);
        frame.setResizable(true); //this should be made false later

    }

}
