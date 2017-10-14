//package VisualNovel;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class GameCreatorGUI {
    private JPanel toolbar;
    private JButton load;
    private JButton newItem;
    private JButton deleteItem;
    private JButton moveUp;
    private JButton moveDown;
    private JButton startOver;
    private JButton done;
    private JDialog creator;
    private StoryTree storyTree;


    public GameCreatorGUI() {
        storyTree = new StoryTree();
        creator = new JDialog();


        creator.setLayout(new BorderLayout());
        creator.setVisible(false);

        toolbar = new JPanel();
        toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.X_AXIS));
        load = new JButton("Load Game");
        newItem = new JButton("Add Item");
        deleteItem = new JButton("Delete Item");
        moveUp = new JButton("Move Item Up");
        moveDown = new JButton("Move Item Down");
        startOver = new JButton("Start Over");
        done = new JButton("Finished");
        load.addActionListener(new ButtonListener());
        newItem.addActionListener(new ButtonListener());
        deleteItem.addActionListener(new ButtonListener());
        moveUp.addActionListener(new ButtonListener());
        moveDown.addActionListener(new ButtonListener());
        startOver.addActionListener(new ButtonListener());
        done.addActionListener(new ButtonListener());
        toolbar.add(load);
        toolbar.add(newItem);
        toolbar.add(deleteItem);
        toolbar.add(moveUp);
        toolbar.add(moveDown);
        toolbar.add(startOver);
        toolbar.add(done);
        creator.add(toolbar, BorderLayout.NORTH);
        creator.add(storyTree);
    }
    class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == newItem) {
                storyTree.addObject("Set Name");
            } else if (e.getSource() == deleteItem) {
                storyTree.removeCurrentNode();
            } else if (e.getSource() == startOver) {
                storyTree.clear();
            } else if (e.getSource() == done) {
                storyTree.serialize();
            }
            if (e.getSource() == load) {
                storyTree.deserialize();
            }
        }
    }



    public JDialog getCreator() {
        return creator;
    }

    public void setCreator(JDialog creator) {
        this.creator = creator;
    }

}
