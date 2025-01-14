import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Menu class represents the menu bar of the game application.
 * It extends JFrame and implements the ActionListener interface.
 * The menu bar provides options for file operations, editing, game difficulty, and help.
 */
public class Menu extends JFrame implements ActionListener {

    private Game m;
    private int counter = 5;

    /**
     * Constructor for the Menu class.
     *
     * @param m The Game instance associated with the menu.
     */
    public Menu(Game m) {
        this.m = m;
        initializeMenu();
    }

    /**
     * Getter method to retrieve the menu bar.
     *
     * @return The JMenuBar instance.
     */
    public JMenuBar getMeni() {
        return meni;
    }

    public JMenuBar meni;
    JMenu file, edit, help, difficulty;
    JMenuItem newGame, loadGame, saveGame, exit, about, hint, checkForUpdates;
    JMenuItem setSize, setTargetValue, setNumberOfMoves;
    FileHandling s;


    /**
     * Initializes the menu bar with various menus and menu items.
     */
    private void initializeMenu() {
        meni = new JMenuBar();
        meni.setBackground(new Color(43, 43, 43));

        file = new JMenu("File");
        file.setForeground(new Color(204, 255, 252));
        edit = new JMenu("Edit");
        edit.setForeground(new Color(204, 255, 252));
        help = new JMenu("Help");
        help.setForeground(new Color(204, 255, 252));


        newGame = new JMenuItem("New game");
        newGame.addActionListener(this);
        newGame.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
        newGame.setBackground(new Color(43, 43, 43));
        newGame.setForeground(new Color(204, 255, 252));  // Set text color

        loadGame = new JMenuItem("Load game data..");
        loadGame.addActionListener(this);
        loadGame.setAccelerator(KeyStroke.getKeyStroke("ctrl L"));
        loadGame.setBackground(new Color(43, 43, 43));
        loadGame.setForeground(new Color(204, 255, 252));  // Set text color

        saveGame = new JMenuItem("Save...");
        saveGame.addActionListener(this);
        saveGame.setBackground(new Color(43, 43, 43));
        saveGame.setForeground(new Color(204, 255, 252));  // Set text color

        exit = new JMenuItem("Exit");
        exit.addActionListener(this);
        exit.setBackground(new Color(43, 43, 43));
        exit.setForeground(new Color(204, 255, 252));  // Set text color

        about = new JMenuItem("About");
        about.addActionListener(this);
        about.setBackground(new Color(43, 43, 43));
        about.setForeground(new Color(204, 255, 252));  // Set text color

        hint = new JMenuItem("🎲 Russian roulette button");
        hint.addActionListener(this);
        hint.setAccelerator(KeyStroke.getKeyStroke("ctrl H"));
        hint.setBackground(new Color(43, 43, 43));
        hint.setForeground(new Color(204, 255, 252));  // Set text color

        checkForUpdates = new JMenuItem("Check for updates");

        setSize = new JMenuItem("Set Size");
        setSize.addActionListener(this);
        setSize.setBackground(new Color(43, 43, 43));
        setSize.setForeground(new Color(204, 255, 252));  // Set text color

        setTargetValue = new JMenuItem("Set target value");
        setTargetValue.addActionListener(this);
        setTargetValue.setBackground(new Color(43, 43, 43));
        setTargetValue.setForeground(new Color(204, 255, 252));  // Set text color

        setNumberOfMoves = new JMenuItem("Set number of moves");
        setNumberOfMoves.addActionListener(this);
        setNumberOfMoves.setBackground(new Color(43, 43, 43));
        setNumberOfMoves.setForeground(new Color(204, 255, 252));  // Set text color

        edit.add(setSize);
        edit.add(setTargetValue);
        edit.add(setNumberOfMoves);

        file.add(newGame);
        file.add(loadGame);
        file.add(saveGame);
        file.add(exit);

        help.add(hint);
        help.add(about);

        difficulty = new JMenu("Difficulty");
        difficulty.setForeground(new Color(204, 255, 252));

        JMenuItem easy = new JMenuItem("Easy");
        easy.addActionListener(e -> {
            Game m2 = m;
            m = new Game("easy");
            m2.dispose();
        });
        easy.setBackground(new Color(43, 43, 43));
        easy.setForeground(new Color(204, 255, 252));  // Set text color

        JMenuItem medium = new JMenuItem("Medium");
        medium.addActionListener(e -> {
            Game m2 = m;
            m = new Game("medium");
            m2.dispose();
        });
        medium.setBackground(new Color(43, 43, 43));
        medium.setForeground(new Color(204, 255, 252));  // Set text color

        JMenuItem hard = new JMenuItem("Hard");
        hard.addActionListener(e -> {
            Game m2 = m;
            m = new Game("hard");
            m2.dispose();
        });

        hard.setBackground(new Color(43, 43, 43));
        hard.setForeground(new Color(204, 255, 252));  // Set text color

        difficulty.add(easy);
        difficulty.add(medium);
        difficulty.add(hard);


        meni.add(file);
        meni.add(edit);
        meni.add(difficulty);
        meni.add(help);

        file.setMnemonic('F');

        // Set the menu bar of the Game class using the provided instance
        m.setJMenuBar(meni);
    }


    /**
     * Handles actions performed on menu items.
     *
     * @param e The ActionEvent associated with the triggered action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        s = new FileHandling(m);
        if (e.getSource() == newGame) {
            int result = JOptionPane.showConfirmDialog(null, "Your current game progress will be lost.", "New game", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                Game m2 = m;
                m = new Game();
                m2.dispose();
            }
        } else if (e.getSource() == loadGame) {
            if (m.isGameStarted()) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to load a new game? Your current game will be lost.", "Load game", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    s.loadIt();
                }
            } else {
                s.loadIt();
            }
        } else if (e.getSource() == about) {
            JOptionPane.showMessageDialog(null, """
                    Igro sestavlja dvodimenzionalno polje velikosti nxn (igralno polje), kjer n je med 10 in 20. Vsak element igralnega polja je gumb, ki mu pripada številka.
                    Igralec z izbiro gumba (z vnaprej določeno cifro 1 - 9) izbere možne kandidate za naslednjo izbiro.
                    Vsota števil na izbranih gumbih igralnega polja se mora čim bolj približati ciljni vrednosti.
                    Good luck!""", "About MORE OR LESS, LESS IS MORE", JOptionPane.INFORMATION_MESSAGE);

        } else if (e.getSource() == hint) {
            if (m.isGameStarted()) {
                if ((counter > 0 || counter < -2)) {
                    m.getHint();
                    counter--;
                } else {
                    JOptionPane.showMessageDialog(null, "Guess you've wasted all" +
                            " the hints😐\nGood luck winning now 😏", "Well, that's unfortunate.", JOptionPane.INFORMATION_MESSAGE);
                    counter--;
                }
            } else {
                JOptionPane.showMessageDialog(null, """
                        By using the hint feature to show you the best buttons you can click,
                        you are aware that it may not always lead you to victory.""", "Disclaimer", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == checkForUpdates) {
            JOptionPane.showMessageDialog(null, "No updates available!", "Check for updates", JOptionPane.PLAIN_MESSAGE, new ImageIcon(
                    new ImageIcon("src/Icons/yahoo_1051270.png").getImage().getScaledInstance(64, 64, Image.SCALE_AREA_AVERAGING)));


        } else if (e.getSource() == saveGame) {
            s.saveIt();

        } else if (e.getSource() == exit) {
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else if (e.getSource() == setSize) {
            if (m.isGameStarted())
                JOptionPane.showMessageDialog(null, "You can't change the size of the matrix while playing a game!", "Error", JOptionPane.PLAIN_MESSAGE, new ImageIcon(
                        new ImageIcon("src/Icons/yahoo_1051270.png").getImage().getScaledInstance(64, 64, Image.SCALE_AREA_AVERAGING)));
            else {
                // create new spinner input dialog from 10 to 20
                JSpinner spinner = new JSpinner(new SpinnerNumberModel(10, 10, 20, 1));
                int result = JOptionPane.showConfirmDialog(null, spinner, "Set size", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    int value = (int) spinner.getValue();
                    Game m2 = m;
                    System.out.println("m:\tn = " + m.getnBackup() + ", target = " + m.getTargetValueBackup() + ", moves = " + m.getNumberOfMovesTillTheEndBackup());
                    System.out.println("m2:\tn = " + m2.getnBackup() + ", target = " + m2.getTargetValueBackup() + ", moves = " + m2.getNumberOfMovesTillTheEndBackup());
                    m = new Game(value, m2.getTargetValueBackup(), m2.getNumberOfMovesTillTheEndBackup());
                    System.out.println("novo m:\tn = " + m.getnBackup() + ", target = " + m.getTargetValueBackup() + ", moves = " + m.getNumberOfMovesTillTheEndBackup());
                    m2.dispose();
                }
            }

        } else if (e.getSource() == setTargetValue) {
            if (m.isGameStarted())
                JOptionPane.showMessageDialog(null, "You can't change the target value while playing a game!", "Error", JOptionPane.PLAIN_MESSAGE, new ImageIcon(
                        new ImageIcon("src/Icons/yahoo_1051270.png").getImage().getScaledInstance(64, 64, Image.SCALE_AREA_AVERAGING)));
            else {
                // create new slider input dialog from 1 to 1800 with numbers I can see
                JSlider slider = new JSlider(1, 1800, 1);
                slider.setMajorTickSpacing(249);
                slider.setMinorTickSpacing(50);
                slider.setPaintTicks(true);
                slider.setPaintTrack(true);
                slider.setPaintLabels(true);
                int result = JOptionPane.showConfirmDialog(null, slider, "Set target value", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    int value = slider.getValue();
                    Game m2 = m;
                    System.out.println("m:\tn = " + m.getnBackup() + ", target = " + m.getTargetValueBackup() + ", moves = " + m.getNumberOfMovesTillTheEndBackup());
                    System.out.println("m2:\tn = " + m2.getnBackup() + ", target = " + m2.getTargetValueBackup() + ", moves = " + m2.getNumberOfMovesTillTheEndBackup());
                    m = new Game(m2.getnBackup(), value, m2.getNumberOfMovesTillTheEndBackup());
                    System.out.println("novo m:\tn = " + m.getnBackup() + ", target = " + m.getTargetValueBackup() + ", moves = " + m.getNumberOfMovesTillTheEndBackup());
                    m2.dispose();
                }
            }

        } else if (e.getSource() == setNumberOfMoves) {
            if (m.isGameStarted())

                JOptionPane.showMessageDialog(null, "You can't change the number of moves while playing a game!",
                        "Friendly reminder",
                        JOptionPane.PLAIN_MESSAGE, new ImageIcon(
                                new ImageIcon("src/Icons/yahoo_1051270.png").getImage().getScaledInstance(64, 64, Image.SCALE_AREA_AVERAGING)));
            else {
                // option page with text field, check if number is between 1 and 200
                JTextField field = new JTextField();
                Object[] message = {
                        "Set number of moves (1-200):", field
                };
                int result = JOptionPane.showConfirmDialog(null, message, "Edit number of moves", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    int value = Integer.parseInt(field.getText());
                    if (value > 200 || value < 1) {
                        JOptionPane.showMessageDialog(null, "Wrong number!", "Error", JOptionPane.PLAIN_MESSAGE, new ImageIcon(
                                new ImageIcon("src/Icons/yahoo_1051270.png").getImage().getScaledInstance(64, 64, Image.SCALE_AREA_AVERAGING)));
                        return;
                    }
                    Game m2 = m;
                    System.out.println("m:\tn = " + m.getnBackup() + ", target = " + m.getTargetValueBackup() + ", moves = " + m.getNumberOfMovesTillTheEndBackup());
                    System.out.println("m2:\tn = " + m2.getnBackup() + ", target = " + m2.getTargetValueBackup() + ", moves = " + m2.getNumberOfMovesTillTheEndBackup());
                    m = new Game(m2.getnBackup(), m2.getTargetValueBackup(), value);
                    System.out.println("novo m:\tn = " + m.getnBackup() + ", target = " + m.getTargetValueBackup() + ", moves = " + m.getNumberOfMovesTillTheEndBackup());
                    m2.dispose();
                }
            }
        }
    }

}
