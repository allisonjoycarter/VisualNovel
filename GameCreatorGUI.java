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
    private JPanel treePanel;
    protected DefaultMutableTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    protected JTree tree;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private int newNodeSuffix = 1;
    private JTextField input;
    private String setName;
    private String nodeName;
    private JScrollPane treeScrollPane;


    public GameCreatorGUI() {
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

        createTree();
        setName = new String("Set Name");

    }

    public void createTree() {
        rootNode = new DefaultMutableTreeNode("Set Name");
        treeModel = new DefaultTreeModel(rootNode);
        treeModel.addTreeModelListener(new TreeModelListener());
        tree = new JTree(treeModel);
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);

        treeScrollPane = new JScrollPane(tree);
        creator.add(treeScrollPane, BorderLayout.CENTER);
    }

    public void clear() {
        rootNode.removeAllChildren();
        treeModel.reload();
    }

    public void removeCurrentNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                    (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
                return;
            }
        }
        toolkit.beep();
    }
    public DefaultMutableTreeNode addObject(Object child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();

        if (parentPath == null) {
            parentNode = rootNode;
        } else {
            parentNode = (DefaultMutableTreeNode)(parentPath.getLastPathComponent());
        }
        return addObject(parentNode, child, true);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child) {
        return addObject(parent, child, false);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child,
                                            boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
        if (parent == null) {
            parent = rootNode;
        }

        treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }

    class TreeModelListener implements javax.swing.event.TreeModelListener {
        @Override
        public void treeNodesChanged(TreeModelEvent e) {
            DefaultMutableTreeNode node;
            node = (DefaultMutableTreeNode)(e.getTreePath().getLastPathComponent());

            int index = e.getChildIndices()[0];
            node = (DefaultMutableTreeNode)(node.getChildAt(index));

            System.out.println("Finished editing tree.");
            System.out.println("New value: " + node.getUserObject());
        }

        @Override
        public void treeNodesInserted(TreeModelEvent e) {

        }

        @Override
        public void treeNodesRemoved(TreeModelEvent e) {

        }

        @Override
        public void treeStructureChanged(TreeModelEvent e) {

        }
    }

    class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == newItem) {
                addObject(setName);
            } else if (e.getSource() == deleteItem) {
                removeCurrentNode();
            } else if (e.getSource() == startOver) {
                clear();
            } else if (e.getSource() == done) {
                try {
                    FileOutputStream file = new FileOutputStream("VisualNovel\\serialisation");
                    ObjectOutputStream out = new ObjectOutputStream(file);
                    out.writeObject(tree);
                    out.close();
                    file.close();
                }
                catch (Exception er) {
                    er.printStackTrace();
                }
            } if (e.getSource() == load) {
                JTree tree2 = null;
                try {
                    FileInputStream file = new FileInputStream("VisualNovel\\serialisation");
                    ObjectInputStream in = new ObjectInputStream(file);
                    tree2 = (JTree) in.readObject();
                    in.close();
                    file.close();
                }
                catch (Exception err) {
                    err.printStackTrace();
                }
                treeScrollPane.add(tree2);

            }
        }
    }
    //apparently this is obsolete. wooo
    private String namingNodes() {


        nodeName = new String();
        input = new JTextField(15);
        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nodeName = input.getText();

            }
        });
        return nodeName;
    }


    public JDialog getCreator() {
        return creator;
    }

    public void setCreator(JDialog creator) {
        this.creator = creator;
    }

}
