//package VisualNovel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.TimerTask;

public class MainPanel extends JLayeredPane {
    private JTextArea text;
    private JScrollPane scrollPane;
    private JLabel picture;
    private JButton back;
    private JButton forward;
    private JLabel placeholder1;
    private JLabel placeholder2;
    private JButton button1;
    private JButton button2;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem restart;
    private JMenuItem quit;
    private JMenuItem save;
    private JMenuItem load;
    private JMenu createMenu;
    private JMenuItem newGame;
    private JMenuItem editGame;
    private int index = 0;
    private int choiceIndex = 0;
    private int charIndex = 0;
    private static final InputStream fileName = GetFile.class.getResourceAsStream("StoryLined");
    private static final InputStream fileName2 = GetFile.class.getResourceAsStream("Choices");

    private BufferedImage background = ImageIO.read(getClass().getResourceAsStream("mountains1.png"));
    private ActionListener buttonListener = new ButtonListener();
    private MouseListener mouseListener = new MouseListener();
    private KeyListener keyListener = new KeyListener();
    private boolean enabled = true; //this will allow us to prevent listeners from executing if the user is at a choice


    private JPanel glassPane = new JPanel();
    private JPanel storyPane = new JPanel();
    private GetFile file = new GetFile(fileName);
    private GetFile file2 = new GetFile(fileName2);
    private ArrayList<String> story = file.OpenFile();
    private ArrayList<String> choices = file2.OpenFile();
    //ideally this font should be used for all labels buttons and text areas
    private Font font = new Font("Times New Roman", Font.TRUETYPE_FONT, 23);

    public MainPanel() throws IOException {
        //setting up MainPanel, which is a LayeredPane
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setSize(storyPane.getPreferredSize());
//        setBounds(0,0,1200, 750);
        setOpaque(true);

        menuBar();
        storyPane();
        glass();
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //these cycle through the story array
            if (e.getSource() == forward && enabled == true) {
                index++;
                text.setText(story.get(index));
                checkForChoice();
            }
            if (index > 0 && e.getSource() == back && !story.get(index - 1).equals("choice")) {
                index--;
                text.setText(story.get(index));
                //this checks the story so that if you hit back after encountering a choice, you can go forward again
                if (!story.get(index).equals("choice")) {
                    activateGlassPane(false);
                }
            }

        }
    }

    private class choiceListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //making a choice progresses the story
            if (e.getSource() == button1 || e.getSource() == button2) {
                index++;
                text.setText(story.get(index));
                activateGlassPane(false);
                /* adding 3 to the choice index cycles through choices with the assumption that
                    they are set up in the file like so:
                    Choice 1
                    Choice 2

                    NextChoice1... and so on */
                choiceIndex = choiceIndex + 3;
            }
        }
    }

    private class KeyListener implements java.awt.event.KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            //the SpaceBar and Enter keys should advance the story, which is implemented here
            //obviously these don't work if the enabled is set to false because of a choice
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_SPACE && enabled == true) {
                index++;
                text.setText(story.get(index));
                checkForChoice();
            } else if (code == KeyEvent.VK_ENTER && enabled == true) {
                index++;
                text.setText(story.get(index));
                checkForChoice();
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {}
    }

    private class MouseListener implements java.awt.event.MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (enabled == true) {
                index++;
                text.setText(story.get(index));
                checkForChoice();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }
    }

    public void checkForChoice() {
        //"choice" is used to represent a choice encounter
        if (story.get(index).equals("choice")) {
            text.setText(story.get(index - 1)); //index - 1 so that the story does not show "choice"
            button1.setText(choices.get(choiceIndex));
            button2.setText(choices.get(choiceIndex + 1));
            activateGlassPane(true);
        }
    }

    public void menuBar() {
        restart = new JMenuItem("Restart");
        quit = new JMenuItem("Quit");
        save = new JMenuItem("Save");
        load = new JMenuItem("Load");
        menu = new JMenu("Game");
        menuBar = new JMenuBar();
        createMenu = new JMenu("Creation Menu");
        newGame = new JMenuItem("Create New");
        editGame = new JMenuItem("Edit Game");

        quit.addActionListener((ActionEvent e) ->  {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to exit?", "Warning", dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION){
                System.exit(0);
            } else {

            }
        });

        newGame.addActionListener((ActionEvent e) -> {
            JDialog gameCreatorGUI = new GameCreatorGUI().getCreator();
            gameCreatorGUI.setSize(new Dimension(608,400));
            gameCreatorGUI.setVisible(true);
        });
        createMenu.add(newGame);
        createMenu.add(editGame);
        menu.add(restart);
        menu.add(quit);
        menu.add(save);
        menu.add(load);
        menuBar.add(menu);
        menuBar.add(createMenu);
        //simply adding the menubar to boxlayout will align it along the center, so a box is created here
        //alternatively menubar.setAlignmentx(Component.LEFT_ALIGNMENT) should work but it doesn't so, box.
        Box box = Box.createHorizontalBox();
        box.add(menuBar);
        box.add(Box.createHorizontalGlue()); //horizontal glue fills in all the empty space next to box
        //so the setup is: edge|box(menu)horizontalgluuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuue|edge
        add(box, 0);


    }

    public void glass() {
        glassPane.setLayout(new GridBagLayout());
        glassPane.setSize(storyPane.getPreferredSize());
        glassPane.setOpaque(false);
        GridBagConstraints constraints = new GridBagConstraints();

        //originally button1 and button2 were in the storyPane but experimenting with glasspane gave me this
        //glasspane was useless because it didn't block any listeners
        //so this may be an extra panel.
        button1 = new JButton(" ");
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        constraints.ipady = 0;
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.insets = new Insets(0,5, 5, 2);
        button1.addActionListener(new choiceListener());
        glassPane.add(button1, constraints);

        button2 = new JButton(" ");
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.insets = new Insets(0,3, 5, 5);
        constraints.anchor = GridBagConstraints.PAGE_END;
        button2.addActionListener(new choiceListener());
        glassPane.add(button2, constraints);

        button1.setEnabled(false);
        button2.setEnabled(false);
        add(glassPane, 2);
    }

    public void activateGlassPane(boolean activate) {
        glassPane.setOpaque(activate);
        button1.setEnabled(activate);
        button2.setEnabled(activate);
        enabled = false; //disables mouse/action/key listeners that allow for story progression
        if (activate) {
            glassPane.requestFocusInWindow();
            button1.setText(choices.get(choiceIndex));
            button2.setText(choices.get(choiceIndex + 1));
        }
        if (activate == false) {
            //set text to blank so the choices don't stay on the buttons
            button1.setText(" ");
            button2.setText(" ");
            enabled = true; //reenables listeners
        }
    }

    public void storyPane() {

        storyPane.setLayout(new GridBagLayout());
        storyPane.setSize(storyPane.getPreferredSize());
        storyPane.setOpaque(true);
        GridBagConstraints constraints = new GridBagConstraints();
        text = new JTextArea(5, 30);

        text.setOpaque(false);
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setFont(font);
        text.setText(story.get(index));
        text.addKeyListener(keyListener);
        text.addMouseListener(mouseListener);

        back = new JButton("Back");
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        constraints.gridx = 0;
        constraints.gridy = 0;
        storyPane.add(back, constraints);
        back.addActionListener(buttonListener);

        forward = new JButton("Forward");
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        constraints.gridx = 1;
        constraints.gridy = 0;
        storyPane.add(forward, constraints);
        forward.addActionListener(buttonListener);

        scrollPane = new JScrollPane(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (scrollPane.isAncestorOf(text)) {
                    g.setColor(new Color(255, 255, 255, 50));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.ipady = 30;
        constraints.weightx = 0;
        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.insets = new Insets(10, 5, 10, 5);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.setViewportBorder(null);
        scrollPane.setBorder(null);
        storyPane.add(scrollPane, constraints);

        placeholder1 = new JLabel("");
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        constraints.ipady = 0;
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.insets = new Insets(0, 5, 5, 2);
        storyPane.add(placeholder1, constraints);
//        button1.addActionListener(buttonListener);

        placeholder2 = new JLabel("");
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.insets = new Insets(0, 3, 5, 5);
        constraints.anchor = GridBagConstraints.PAGE_END;
        storyPane.add(placeholder2, constraints);
//        button2.addActionListener(buttonListener);

        picture = new JLabel(new ImageIcon(background));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.ipady = 60;
        constraints.gridwidth = 3;
        constraints.gridheight = 2;
//        constraints.insets = new Insets(5, 2, 5, 2);
        storyPane.add(picture, constraints);

        add(storyPane, 1);
    }


    private void characterTimed() {
        final Timer timer = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taText = text.getText();
                taText += story.get(index).charAt(charIndex);
                text.setText(taText);
                charIndex++;
                if (charIndex >= story.get(index).length()) {
                    ((Timer)e.getSource()).stop();
                }
            }
        });
        timer.start();
    }
}
