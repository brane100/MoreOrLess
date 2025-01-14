import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import java.util.Random;

public class Game extends JFrame implements ActionListener, MouseListener {


    private boolean gameStarted = false;
    private Random r = new Random();
    private int n, numberOfMovesTillTheEnd, targetValue, currentResult;
    private int nBackup, targetValueBackup, numberOfMovesTillTheEndBackup;
    private int finalI, finalJ;
    public JLabel currentResultLabel;
    private JLabel MovesTillEnd;
    private JButton previousButton;
    private JButton[][] usedButtons;
    JButton[][] buttons;
    private int[] ButtonValues;
    int sum;
    float deviation;

    /**
     * Getter method for the boolean containing the game state
     *
     * @return boolean
     */
    public boolean isGameStarted() {
        return gameStarted;
    }


    public Game() {
        initializeGame();
        initializeUI();
    }

    /*
     * Constructor for the Game class
     * The constructor initializes the game
     * The size parameter is used to set the number of rows and columns
     * The target value is calculated based on the size parameter
     * The number of moves is calculated based on the target value
     * The game is initialized and the UI is initialized
     *
     * @param size - the number of rows and columns
     * @return void
     * */
    public Game(int size, int target, int moves) {
        initializeGame(size, target, moves);
        initializeUI();
    }

    //    easy:
    //    target = 1/8 * maxSum, moves = target/4
    //
    //    medium:
    //    target = 1/5 * maxSum, moves = target/6
    //
    //    hard:
    //    target = 2/3 * maxSum, moves = target/8

    /*
     * Constructor for the Game class
     * The constructor initializes the game
     * The difficulty parameter is used to set the number of rows and columns
     * and the target value
     * The number of moves is calculated based on the target value
     * The game is initialized and the UI is initialized
     *
     * @param difficulty - the difficulty of the game
     * @return void
     * */
    public Game(String difficulty) {
        switch (difficulty) {
            case "easy":
                setN(r.nextInt(3) + 10);
                ButtonValues = new int[n * n];
                arrayCalcutation();
                setTargetValue((int) ((1 / 8.0) * sum));
                setNumberOfMovesTillTheEnd(targetValue / 4);
                initializeGame(n, targetValue, numberOfMovesTillTheEnd);
                initializeUI();
                break;
            case "medium":
                setN(r.nextInt(2) + 13);
                ButtonValues = new int[n * n];
                arrayCalcutation();
                setTargetValue((int) ((1 / 5.0) * sum));
                setNumberOfMovesTillTheEnd(targetValue / 4);
                initializeGame(n, targetValue, numberOfMovesTillTheEnd);
                initializeUI();
                break;
            case "hard":
                setN(r.nextInt(5) + 16);
                ButtonValues = new int[n * n];
                arrayCalcutation();
                setTargetValue((int) ((2 / 3.0) * sum));
                setNumberOfMovesTillTheEnd(targetValue / 8);
                initializeGame(n, targetValue, numberOfMovesTillTheEnd);
                initializeUI();
                break;
        }

    }

    /*
     * Getter method for the number of rows and columns backup value
     */
    public int getnBackup() {
        return nBackup;
    }

    /*
     * Getter method for the backup target value
     */
    public int getTargetValueBackup() {
        return targetValueBackup;
    }

    /*
     * Getter method for the number of backup moves till the end
     */
    public int getNumberOfMovesTillTheEndBackup() {
        return numberOfMovesTillTheEndBackup;
    }

    // default game initialization method
    /*
     * Void method to initialize the game.
     * The method initializes the number of rows and columns, target value,
     * number of moves, buttons matrix, usedButtons matrix, ButtonValues array,
     * current result, sum of the button values, previous button, gameStarted variable,
     * deviation variable, currentResultLabel, MovesTillEnd label, game panel, menu bar, UI of the Game clas,
     */
    private void initializeGame() {
        this.n = r.nextInt(11) + 10;  // random number between 10 and 20
        this.ButtonValues = new int[n * n];

        arrayCalcutation();

        this.targetValue = (int) Math.floor(1.0 / 10 * sum);
        this.numberOfMovesTillTheEnd = (int) Math.ceil(targetValue / 5.0);
        this.nBackup = n;
        this.targetValueBackup = targetValue;
        this.numberOfMovesTillTheEndBackup = numberOfMovesTillTheEnd;
        this.usedButtons = new JButton[n][n];
        this.buttons = new JButton[n][n];
    }

    /*
     * Method to calculate the sum of the button values
     * and initialize the ButtonValues array
     * */
    private void arrayCalcutation() {
        for (int i = 0; i < n * n; i++) {
            ButtonValues[i] = r.nextInt(9) + 1;
            this.sum += ButtonValues[i];
        }
    }

    /*
     * Void method to initialize the game
     * The method first checks if the parameters are valid
     * If not, an exception is thrown
     * If yes, the game is initialized
     *
     * @param size - the number of rows and columns
     * @param target - the target value
     * @param moves - the number of moves
     *
     * @return void
     */
    private void initializeGame(int size, int target, int moves) {
        if (size < 10 || size > 20) {
            throw new IllegalArgumentException("Size must be between 10 and 20");
        } else if (target < 1) {
            throw new IllegalArgumentException("Target value must be at least 1");
        } else if (moves < 1) {
            throw new IllegalArgumentException("Number of moves must be at least 1.");
        }

        this.n = size;
        this.targetValue = target;
        this.numberOfMovesTillTheEnd = moves;
        this.nBackup = n;
        this.targetValueBackup = targetValue;
        this.numberOfMovesTillTheEndBackup = numberOfMovesTillTheEnd;
        this.usedButtons = new JButton[n][n];
        this.buttons = new JButton[n][n];
        this.ButtonValues = new int[n * n];
    }

    /*
     * Method to initialize the UI of the Game class
     * The UI consists of a menu bar and a game panel
     * The game panel consists of a matrix of buttons
     * The buttons are initialized with a random value between 1 and 9
     * and the buttons are added to the game panel
     *
     * */
    private void initializeUI() {
        ImageIcon icon = new ImageIcon("src\\Icons\\icon.png");
        this.setIconImage(icon.getImage());

        setTitle("MORE OR LESS, LESS IS MORE");
        setLayout(new BorderLayout(0, 0));

//        initialize the up, left, right and down panels

        JPanel upPanel = new JPanel(new GridLayout(1, 2));
        JLabel valueLabel = new JLabel("Target: " + targetValue) {{
            setFont(new Font("Roboto", Font.BOLD, 16));
            setForeground(new Color(204, 255, 252));
            setHorizontalAlignment(JLabel.CENTER);
            setVerticalAlignment(JLabel.CENTER);
        }};
        MovesTillEnd = new JLabel("Remaining: " + numberOfMovesTillTheEnd) {{
            setFont(new Font("Roboto", Font.BOLD, 16));
            setForeground(new Color(204, 255, 252));
            setHorizontalAlignment(JLabel.CENTER);
            setVerticalAlignment(JLabel.CENTER);
        }};

        upPanel.add(valueLabel);
        upPanel.add(MovesTillEnd);
        upPanel.setBackground(new Color(29, 29, 29));
        upPanel.setPreferredSize(new Dimension(0, 50));
        add(upPanel, BorderLayout.NORTH);

        add(new JPanel() {{
            setBackground(new Color(29, 29, 29));
            setPreferredSize(new Dimension(50, 0));
        }}, BorderLayout.WEST);


        add(new JPanel() {{
            setBackground(new Color(29, 29, 29));
            setPreferredSize(new Dimension(50, 0));
        }}, BorderLayout.EAST);

        JPanel downPanel = new JPanel(new BorderLayout());
        currentResultLabel = new JLabel("Current: " + currentResult) {{
            setFont(new Font("Roboto", Font.BOLD, 16));
            setForeground(Color.WHITE);
            setHorizontalAlignment(JLabel.CENTER);
            setVerticalAlignment(JLabel.CENTER);
        }};
        downPanel.add(currentResultLabel, BorderLayout.CENTER);
        downPanel.setBackground(new Color(29, 29, 29));
        downPanel.setPreferredSize(new Dimension(0, 50));

        add(downPanel, BorderLayout.SOUTH);

        JPanel game = new JPanel();
        initMatrix(game, n, targetValue, numberOfMovesTillTheEnd, currentResultLabel, MovesTillEnd);
        add(game, BorderLayout.CENTER);

        // set the menu bar of the Game class using the Menu instance
        Menu menu = new Menu(this);
        setJMenuBar(menu.getMeni());

        revalidate();
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /*
     * Setter method for the number of rows and columns
     */
    public void setN(int n) {
        this.n = n;
    }

    /*
     * Setter method for the number of moves
     */
    public void setNumberOfMovesTillTheEnd(int numberOfMovesTillTheEnd) {
        this.numberOfMovesTillTheEnd = numberOfMovesTillTheEnd;
    }

    /*
     * Setter method for the target value
     */
    public void setTargetValue(int targetValue) {
        this.targetValue = targetValue;
    }

    /*
     * Method to initialize the matrix of buttons
     * and add them to the game panel
     * The buttons are added to the buttons matrix
     * and the values of the buttons are added to the ButtonValues array
     * The buttons are initialized with a random value between 1 and 9
     * and the buttons are added to the game panel
     *
     * @param game - the panel where the buttons will be added
     * @param size - the size of the matrix
     * @param target - the target value
     * @param moves - the number of moves
     * @param currentResultLabel - the label that shows the current result
     * @param MovesTillEnd - the label that shows the number of moves till the end
     *
     * @return void
     */
    private void initMatrix(JPanel game, int size, int target, int moves, JLabel currentResultLabel, JLabel MovesTillEnd) {
        game.setLayout(new GridLayout(size, size));
        buttons = new JButton[size][size];
        for (int i = 0; i < size * size; i++) {
            finalI = i / size;
            finalJ = i % size;

            ButtonValues[i] = r.nextInt(9) + 1;
            buttons[i / size][i % size] = new JButton(Integer.toString(ButtonValues[i]));
            buttons[i / size][i % size].addActionListener(this);
            buttons[i / size][i % size].addMouseListener((MouseListener) this);
            buttons[i / size][i % size].setFocusable(false);
            buttons[i / size][i % size].setFont(new Font("Comic Sans", Font.PLAIN, 18));
            buttons[i / size][i % size].setBackground(new Color(97, 255, 92));
            buttons[i / size][i % size].setPreferredSize(new Dimension(44, 34));
            buttons[i / size][i % size].setBorder(BorderFactory.createLineBorder(new Color(40, 40, 40)));
            game.add(buttons[i / size][i % size]);
        }
    }

    /*
     * Void method to customize the selected button
     *
     * @param b - the button to be selected
     * @return void
     * */
    private void selectedButton(JButton b) {
        b.setEnabled(false);
        b.setBackground(new Color(255, 0, 128));
        b.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
        b.setFont(new Font("Comic Sans", Font.PLAIN, 18));
        b.setForeground(new Color(0, 0, 0));
    }

    /*
     * Void method to update the game state
     * Here we update the current result, the number of moves till the end
     * and the state of the buttons
     * If the current result is equal to the target value, the user wins
     * If the current result is bigger than the target value, the user loses
     * If there are no more moves, the user loses
     * If all the buttons are bigger than the number needed to win, the user loses
     *
     * @param button - the button that was clicked
     * @return void
     * */
    private void updateGameState(JButton button) {

        int value = Integer.parseInt(button.getText());
        int previousValue = (previousButton == null) ? 0 : Integer.parseInt(previousButton.getText());

        currentResult += value;
        currentResultLabel.setText("Current: " + currentResult);
        numberOfMovesTillTheEnd--;
        MovesTillEnd.setText("Remaining: " + numberOfMovesTillTheEnd);

        boolean biggerResult = currentResult > targetValue;
        boolean allButtonValueBigger = true;
        int toWin = targetValue - currentResult;
        int totalEnabledValue = 0;

        for (int k = 0; k < n * n; k++) {
            JButton currentButton = buttons[k / n][k % n];
            boolean isCurrentLegit = (k / n + 1) % value == 0 || (k % n + 1) % value == 0;
            boolean overAllLegit;
            if (previousValue != 0) {
                overAllLegit = (isCurrentLegit) && (((k / n) + 1) % previousValue == 0 || ((k % n) + 1) % previousValue == 0);
            } else {
                overAllLegit = isCurrentLegit;
            }
            if (overAllLegit) {
                currentButton.setEnabled(true);
                currentButton.setBackground(new Color(0, 255, 255));
                currentButton.setForeground(new Color(0, 0, 0));
                currentButton.setFont(new Font("Comic Sans", Font.PLAIN, 18));
                totalEnabledValue++;
            } else {
                currentButton.setEnabled(false);
                currentButton.setBackground(new Color(34, 34, 34));
                currentButton.setForeground(new Color(255, 255, 255));
                currentButton.setFont(new Font("Comic Sans", Font.PLAIN, 18));
            }

            if (currentButton == usedButtons[k / n][k % n]) {
                currentButton.setEnabled(false);
                currentButton.setBackground(new Color(29, 29, 29));
                currentButton.setForeground(new Color(255, 255, 255));
                currentButton.setFont(new Font("Comic Sans", Font.PLAIN, 18));

                if (overAllLegit) totalEnabledValue--;
            }

            if (currentButton == button) {
                selectedButton(currentButton);
                if (overAllLegit) totalEnabledValue--;
            }
        }

        for (int k = 0; k < n * n; k++) {
            JButton currentButton = buttons[k / n][k % n];
            if (currentButton.isEnabled() && Integer.parseInt(currentButton.getText()) <= toWin) {
                allButtonValueBigger = false;
            }
        }

//        Debugging stataments
        /*System.out.println("currentResult: " + currentResult);
        System.out.println("targetValue: " + targetValue);
        System.out.println("totalEnabledValue: " + totalEnabledValue);
        System.out.println("numberOfMovesTillTheEnd: " + numberOfMovesTillTheEnd);
        System.out.println("biggerResult: " + biggerResult);
        System.out.println("allButtonValueBigger: " + allButtonValueBigger);*/
        if (currentResult == targetValue) {
            JOptionPane.showMessageDialog(null, "You won!", "Success", JOptionPane.PLAIN_MESSAGE, new ImageIcon("src/Icons/party_2274543.png"));
            maybeNewGame();
            dispose();
        } else {
            // if there are no more moves or
            // the result is bigger than the target value or
            // all the buttons are bigger than the number needed to win
            // the game is over
            // show also deviation
            deviation = Math.abs(currentResult - targetValue);
            if (totalEnabledValue == 0) {
                showMessageAndMaybeNewGame("No more available buttons to select!\nYou lost!\n\nDeviation: " + deviation);
            } else if (numberOfMovesTillTheEnd == 0 && currentResult < targetValue) {
                showMessageAndMaybeNewGame("You ran out of moves!\nYou lost!\n\nDeviation: " + deviation);
            } else if (biggerResult) {
                showMessageAndMaybeNewGame("You went over the target value!\nYou lost!\n\nDeviation: " + deviation);
            } else if (allButtonValueBigger) {
                showMessageAndMaybeNewGame("All available buttons are bigger than the remaining value!\nYou lost!\n\nDeviation: " + deviation);
            }

        }

        if (previousButton != null) {
            previousButton.setBackground(new Color(8, 24, 138));
            previousButton.setForeground(new Color(255, 255, 255));
            previousButton.setFont(new Font("Comic Sans", Font.PLAIN, 18));
        }


        for (int k = 0; k < n * n; k++) {
            if (buttons[k / n][k % n] == button) {
                finalI = k / n;
                finalJ = k % n;
                break;
            }
        }

        usedButtons[finalI][finalJ] = button;
        previousButton = button;

        System.out.println("You chose " + buttons[finalI][finalJ].getText());
    }

    /*
     * Void method to show a message and ask the user if he wants to play again
     * @param message - the message to be shown
     * @return void
     * */
    private void showMessageAndMaybeNewGame(String message) {
        JOptionPane.showMessageDialog(null, message, "Game over", JOptionPane.PLAIN_MESSAGE, new ImageIcon("src/Icons/lose.png"));
        maybeNewGame();
    }

    /**
     * Void method to handle the button clicks
     * gameStarted is used to check if the game has started
     * if not, the game starts
     * if yes, the game continues and the game state is updated
     *
     * @param e
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameStarted) {
            gameStarted = true;
        }

        JButton button = (JButton) e.getSource();
        updateGameState(button);
    }


    /*
     * getter method for the buttons matrix
     * */
    public JButton[][] getButtons() {
        return buttons;
    }

    /*
     * Method to ask the user if he wants to play again
     * If yes, start a new game
     * If no, exit the game
     * */
    private void maybeNewGame() {
        int result = JOptionPane.showConfirmDialog(null, "Let's play again, shall we?", "New game", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            Game m2 = this;
            dispose();
            new Game();
        } else {
            JOptionPane.showMessageDialog(this, "Exiting the game like a boss.", "Exit", JOptionPane.PLAIN_MESSAGE, new ImageIcon(new ImageIcon("src/Icons/boss.png").getImage().getScaledInstance(173, 256, Image.SCALE_DEFAULT)));
            dispose();
        }
    }

    /*
     * Method void to get hint for the next move,
     * choosing the biggest button value that is less than or equal to the number needed to win
     * and highlighting it
     * if there is no such button, highlight the biggest button value
     * Choosing hint is like playing russian roulette:
     * You may continue playing, or you may lose
     * Use at your own risk.
     * */
    public void getHint() {
        int toWin = targetValue - currentResult;
        if (toWin > 0 && toWin <= 9) {
            highlightButton(toWin);
        } else {
            highlightButton(findMaxValueButton(toWin));
        }
    }

    /*
     * Helper method to find the maximum button value
     * that is less than or equal to the number needed to win
     * and return it
     *
     * @param toWin - the number needed to win
     * @return maxValue - the maximum button value that is less than or equal to toWin
     * */
    private int findMaxValueButton(int toWin) {
        int maxValue = Integer.MIN_VALUE;

        for (int i = 0; i < n * n; i++) {
            JButton currentButton = buttons[i / n][i % n];
            int buttonValue = Integer.parseInt(currentButton.getText());

            // Ensure the button is enabled and its value is less than or equal to toWin
            if (currentButton.isEnabled() && buttonValue <= toWin && buttonValue > maxValue) {
                maxValue = buttonValue;
            }

            // Debugging print statements
//            System.out.println("Button[" + (i / n) + "][" + (i % n) + "] - Value: " + buttonValue + ", Enabled: " + currentButton.isEnabled());
        }

        return maxValue;
    }


    /*
     * method to highlight the buttons with the given value
     *
     * @param targetValue - the value to be highlighted
     *
     * @return void
     *
     * */
    private void highlightButton(int targetValue) {

        for (int i = 0; i < n * n; i++) {
            JButton currentButton = buttons[i / n][i % n];
            if (currentButton.getText().equals(Integer.toString(targetValue)) && buttons[i / n][i % n].isEnabled()) {
                buttons[i / n][i % n].setBackground(new Color(255, 221, 0));
                buttons[i / n][i % n].setFont(new Font("Impact", Font.PLAIN, 18));
                buttons[i / n][i % n].setForeground(new Color(114, 11, 11, 255));
            }
        }


    }

    /*
     * Override method to reset the current result label color
     * */
    @Override
    public void mouseClicked(MouseEvent e) {
        currentResultLabel.setForeground(Color.WHITE);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    int tmp = currentResult;


    /**
     * Override method to increase the future result
     * by the value of the button user is now hovering over
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.isEnabled()) {
            tmp += Integer.parseInt(button.getText());
            currentResultLabel.setForeground(Color.GREEN);
            updateSumLabel();
        }
    }

    /*
     * Override method to show the current result
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.isEnabled()) {
            tmp -= Integer.parseInt(button.getText());
            currentResultLabel.setForeground(Color.WHITE);
            updateSumLabel();
        }
    }

    /*
     * Method to show the future result
     * if the user chooses the given button
     */
    private void updateSumLabel() {
        currentResultLabel.setText("Current: " + tmp);
    }

}