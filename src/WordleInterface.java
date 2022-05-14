/**
 * Interface of methods implemented by the Wordle View
 *
 * @author vahid
 */
public interface WordleInterface {

    /**
     * Handles moving user to the next row after their guess
     */
    void handleGoToNextRow();

    /**
     * Handles when a user guesses the matching letter as the
     * answer in the same position
     * @param col   column where letter is found
     */
    void handleGreenLetter(int col);

    /**
     * Handles when a user guesses the a letter found in the
     * answer but not in the correct index
     * @param col   column where letter is found
     */
    void handleYellowLetter(int col);

    /**
     * Handles when a user guesses a letter not found in the answer
     * @param col   column where letter is found
     */
    void handleGrayLetter(int col);

    /**
     * Handles when the user guesses a 5-letter word that is not in
     * the games word bank
     */
    void handleInvalidGuess();

    /**
     * Handles when the user wins the Wordle
     */
    void handleWinner();

    /**
     * Handles the user losses the Wordle
     */
    void handleLoss();

}
