import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Custom JTextField Limit that allows only 1 character of text
 *
 * @author vahid
 */
public class JTextFieldLimit extends PlainDocument {
    private int limit;

    /**
     * Constructor for JTextFieldLimit
     * @param limit
     */
    public JTextFieldLimit(int limit){
        super();
        this.limit = limit;
    }

    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException{
        if(str == null){
            return;
        }
        if((getLength() + str.length()) == limit && str.matches("[a-zA-Z]")){
            super.insertString(offset, str, attr);
        }
    }
}
