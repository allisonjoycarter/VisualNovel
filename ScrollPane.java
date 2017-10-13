package VisualNovel;

import javax.swing.*;

public class ScrollPane extends JPanel {
    private JTextArea text;

    public ScrollPane() {
        setLayout(new ScrollPaneLayout());

        text = new JTextArea(5, 30);
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
    }
}
