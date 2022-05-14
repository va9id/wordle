import javax.swing.*;
import java.awt.*;

/**
 * Wordle View
 *
 * @author Vahid
 */
public class WordleView extends JFrame implements WordleInterface{
    public static final int ROW = 6;
    public static final int COL = 5;
    private final JTextField[][] grid;
    private final WordleModel model;
    private final JButton restartButton;

    /**
     * Wordle View Constructor
     */
    public WordleView(){
        super("Wordle Clone");
        this.setLayout(new GridLayout(ROW + 1, COL, 3, 3));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(520, 650);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.model = new WordleModel();
        model.addView(this);

        this.grid = new JTextField[ROW][COL];

        JButton submitButton = new JButton("Submit");
        this.getRootPane().setDefaultButton(submitButton);
        submitButton.setFocusTraversalKeysEnabled(false);
        submitButton.addActionListener(e->handleSubmission());


        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e->handleQuit());
        quitButton.setFocusTraversalKeysEnabled(false);

        restartButton = new JButton("Restart");
        restartButton.addActionListener(e->handleRestart());
        quitButton.setFocusTraversalKeysEnabled(false);

        WordleController cMid = new WordleController(model, false, false);
        WordleController cLeft = new WordleController(model, true, false);
        WordleController cRight = new WordleController(model, false, true);

        for(int i=0; i < ROW ; i++) {
            for (int j = 0; j < COL; j++) {
                JTextField tf = new JTextField();
                tf.setEnabled(i == 0);
                tf.setOpaque(true);
                tf.setFont(new Font("Helvetica Neue", Font.BOLD, 28));
                tf.setHorizontalAlignment(JTextField.CENTER);
                tf.setDocument(new JTextFieldLimit(1));
                tf.setFocusTraversalKeysEnabled(false);
                switch (j) {
                    case 0 -> tf.addKeyListener(cLeft);
                    case 4 -> tf.addKeyListener(cRight);
                    default -> tf.addKeyListener(cMid);
                }
                this.grid[i][j] = tf;
                this.add(tf);
            }
        }
        this.add(submitButton);
        this.add(new JLabel(""));
        this.add(new JLabel(""));
        this.add(restartButton);
        this.add(quitButton);
        this.setVisible(true);
    }

    /**
     * Handles when a user quits the game
     */
    private void handleQuit() { System.exit(0); }

    /**
     * Generates the prompt when the game is over
     * @param s result of the game
     */
    private void endGamePrompt(String s){
        String[] options = {"Quit", "Play Again"};
        JLabel centeredText = new JLabel(s, SwingConstants.CENTER);
        int choice = JOptionPane.showOptionDialog(this, centeredText, "Game Over",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, 0);
        if(choice != 0){
            restartButton.doClick();
        }
        else{
            handleQuit();
        }
    }

    /**
     * Handles the submission of a users guess
     */
    private void handleSubmission() {
        StringBuilder guess = new StringBuilder();
        for(int col=0; col < COL; col++){
            String textField = this.grid[model.getCurrentRow()][col].getText().toUpperCase();
            if(textField.isEmpty()) return; //Validates and blocks a guess from being checked if less than 5 letters
            guess.append(textField);
        }
        this.model.compareToAnswer(guess.toString());
    }

    /**
     * Handles when the user restarts the game
     */
    private void handleRestart() {
        model.restartGame();
        this.grid[0][0].grabFocus();
        for(int i = 0; i < ROW; i++){
            for(int j = 0; j < COL; j++){
                this.grid[i][j].setEnabled(i==0);
                this.grid[i][j].setText(null);
                this.grid[i][j].setBackground(Color.WHITE);
            }
        }
    }

    /**
     * Handles moving the user to the next row when they submit a guess
     */
    @Override
    public void handleGoToNextRow() {
        for(int col = 0; col < COL; col++){
            this.grid[model.getCurrentRow()][col].setEnabled(true);
            for(int row = model.getCurrentRow() - 1 ; row >= 0; row--){
                this.grid[row][col].setEnabled(false);
            }
        }
        this.grid[model.getCurrentRow()][0].grabFocus();
    }

    /**
     * Handles when the user guesses the correct letter in the correct position
     * @param col   column where letter is found
     */
    @Override
    public void handleGreenLetter(int col) { this.grid[model.getCurrentRow()][col].setBackground(Color.GREEN); }

    /**
     * Handles when the user guesses the correct letter in the incorrect position
     * @param col   column where letter is found
     */
    @Override
    public void handleYellowLetter(int col) {
        this.grid[model.getCurrentRow()][col].setBackground(Color.YELLOW);
    }

    /**
     * Handles when the user guesses an incorrect letter
     * @param col   column where letter is found
     */
    @Override
    public void handleGrayLetter(int col) {
        this.grid[model.getCurrentRow()][col].setBackground(Color.LIGHT_GRAY);
    }

    /**
     * Handles when the user guesses a 5 letter word that is not in the games list of words
     */
    @Override
    public void handleInvalidGuess() {
        for(int col = 0; col < COL; col++){
            this.grid[model.getCurrentRow()][col].setText("");
        }
        JOptionPane.showMessageDialog(this, "Not in word list",
                "Invalid Word", JOptionPane.ERROR_MESSAGE, null);
        this.grid[model.getCurrentRow()][0].grabFocus();
    }

    /**
     * Handles when a user wins the Wordle
     */
    @Override
    public void handleWinner() {
        for(int col = 0; col < COL; col++){
            this.grid[model.getCurrentRow()][col].setBackground(Color.GREEN);
        }
        endGamePrompt("You Solved the Wordle!");
    }

    /**
     * Handles when a user losses the Wordle
     */
    @Override
    public void handleLoss() {
        endGamePrompt("You Lost, the Wordle was: " + model.getAnswer());
    }

}
