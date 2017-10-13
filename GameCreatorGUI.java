package VisualNovel;

import javax.swing.*;
import java.awt.*;

public class GameCreatorGUI {
    JPanel toolbar;
    JButton newItem;
    JButton deleteItem;
    JButton moveUp;
    JButton moveDown;
    JButton startOver;
    JButton done;
    JDialog creator;

    public GameCreatorGUI() {
        creator = new JDialog();
        creator.setLayout(new BorderLayout());
        creator.setVisible(false);

        toolbar = new JPanel();
        toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.X_AXIS));
        newItem = new JButton("Add Item");
        deleteItem = new JButton("Delete Item");
        moveUp = new JButton("Move Item Up");
        moveDown = new JButton("Move Item Down");
        startOver = new JButton("Start Over");
        done = new JButton("Finished");
        toolbar.add(newItem);
        toolbar.add(deleteItem);
        toolbar.add(moveUp);
        toolbar.add(moveDown);
        toolbar.add(startOver);
        toolbar.add(done);
        creator.add(toolbar, BorderLayout.NORTH);

    }

    public JDialog getCreator() {
        return creator;
    }

    public void setCreator(JDialog creator) {
        this.creator = creator;
    }
    //    public void addToolbar(){
//        toolbar.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//        newItem = new JButton("Add Item");
//        deleteItem = new JButton("Delete Item");
//        moveUp = new JButton("Move Item Up");
//        moveDown = new JButton("Move Item Down");
//        startOver = new JButton("Start Over");
//        done = new JButton("Finished");
//        toolbar.add(newItem);
//        toolbar.add(deleteItem);
//        toolbar.add(moveUp);
//        toolbar.add(moveDown);
//        toolbar.add(startOver);
//        toolbar.add(done);
//        add(toolbar, BorderLayout.NORTH);
//    }
}
