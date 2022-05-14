import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Controller for the Wordle game
 *
 * @author vahid
 */
public class WordleController implements KeyListener {

    private WordleModel model;
    private boolean isLeftmostCol;
    private boolean isRightMostCol;

    /**
     * Wordle Controller Constructor
     * @param model Wordle Model
     * @param isLeftmostCol true if controller is for the left most column in the grid
     * @param isRightMostCol true if controller is for the right most column in the grid
     */
    public WordleController(WordleModel model, boolean isLeftmostCol, boolean isRightMostCol){
        this.model = model;
        this.isLeftmostCol = isLeftmostCol;
        this.isRightMostCol = isRightMostCol;
    }

    /**
     * Transfers cursor focus when keys certain keys are pressed
     * @param e KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e) {
        JTextField src = ((JTextField)e.getSource());
        int kc = e.getKeyCode();
        if(kc == KeyEvent.VK_BACK_SPACE && !isLeftmostCol) {
            src.transferFocusBackward();
        }
        else if( ( (kc >= 'A' && kc <= 'Z') || (kc >= 'a' && kc <= 'z') ) && !isRightMostCol) {
            src.transferFocus();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

}
