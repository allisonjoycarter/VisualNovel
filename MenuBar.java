package Playground.VisualNovel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MenuBar {
    private static JMenuBar bar;
    private JMenu menu;

    public MenuBar() {
        bar = new JMenuBar();
        menu = new JMenu("Game");
        bar.setOpaque(true);

        bar.add(menu);
        JMenuItem restart = new JMenuItem("Restart");
        JMenuItem quit = new JMenuItem("Quit");

        menu.add(restart);
        menu.add(quit);

//        restart.addActionListener(new MainPanel.ButtonListener());
        quit.addActionListener((ActionEvent e) ->  {
            exitPane();
        });
    }

    public void exitPane() {
        JOptionPane optionPane = new JOptionPane("Are you sure you want to exit?",
                JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
        JDialog exitPane = new JDialog();
        exitPane.setContentPane(optionPane);
        exitPane.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        exitPane.pack();
        exitPane.setVisible(true);
    }

    public static JMenuBar getBar() {
        return bar;
    }

    public void setBar(JMenuBar bar) {
        this.bar = bar;
    }
}
