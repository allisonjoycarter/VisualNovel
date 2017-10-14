import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class StoryTree extends JPanel{
    protected DefaultMutableTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    protected JTree tree;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private int newNodeSuffix = 1;
    private JTextField input;
    private String setName;
    private String nodeName;
    private JScrollPane treeScrollPane;

    public StoryTree() {
        setLayout(new GridLayout(1, 0));
        setName = new String("Set Name");

        treeModel = null;
        rootNode = new DefaultMutableTreeNode("Set Name");
        if (treeModel == null) {
            treeModel = new DefaultTreeModel(rootNode);
        }
        treeModel.addTreeModelListener(new TreeModelListener());
        tree = new JTree(treeModel);
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);

        treeScrollPane = new JScrollPane(tree);
        add(treeScrollPane);

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
            MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
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
            parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
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
            node = (DefaultMutableTreeNode) (e.getTreePath().getLastPathComponent());

            int index = e.getChildIndices()[0];
            node = (DefaultMutableTreeNode) (node.getChildAt(index));

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

    public void serialize() {
        try {
            FileOutputStream file = new FileOutputStream("VisualNovel\\serialisation");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(treeModel);
            out.flush();
            out.close();
        } catch (Exception er) {
            er.printStackTrace();
        }
    }

    final File treeFile = new File("VisualNovel\\serialisation");

    public void deserialize() {

        if (treeFile.exists()) {
            try {
//                FileInputStream file = new FileInputStream("VisualNovel\\serialisation");
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(treeFile));
                treeModel = (DefaultTreeModel) in.readObject();
                in.close();
                System.out.println("Tried To do the thing");
            } catch (Exception er) {
                er.printStackTrace();
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

    public JTree getTree() {
        return tree;
    }

    public void setTree(JTree tree) {
        this.tree = tree;
    }
}

