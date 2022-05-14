import java.util.*;

/**
 * Wordle Model
 *
 * @author Vahid
 */
public class WordleModel {

    private static final int WORDLE_LENGTH = 5;
    private final List<String> allWords = Arrays.asList(
            "ABAFT", "ABOON", "ABOUT", "ABOVE", "ABUSE", "ACUTE", "ADMIT", "ABUSE", "ADOPT", "ADOWN", "ADULT",
            "AFORE", "AFTER", "AGENT", "AGREE", "ALIVE", "ALLOW", "ALONE", "ALIVE", "ALONG", "ALOOF", "ALTER",
            "AMONG", "ANGER", "ANGRY", "APPLE", "APPLY", "ARGUE", "ARISE", "APPLY", "AVOID", "AWARD", "AWARE",
            "AWFUL", "BASIC", "BASIS", "BEACH", "BEGIN", "BELOW", "BIRTH", "BEGIN", "BLACK", "BLAME", "BLIND",
            "BLOCK", "BLOOD", "BOARD", "BRAIN", "BRAVE", "BREAD", "BREAK", "BRAVE", "BRIEF", "BRING", "BROAD",
            "BROWN", "BUILD", "BURST", "BUYER", "CARRY", "CATCH", "CAUSE", "CARRY", "CHAIN", "CHAIR", "CHEAP",
            "CHECK", "CHEST", "CHIEF", "CHILD", "CHINA", "CIRCA", "CIVIL", "CHINA", "CLAIM", "CLASS", "CLEAN",
            "CLEAR", "CLIMB", "CLOCK", "CLOSE", "COACH", "COAST", "COUNT", "COACH", "COURT", "COVER", "CRAZY",
            "CREAM", "CRIME", "CROSS", "CROWD", "CROWN", "CYCLE", "DAILY", "CROWN", "DANCE", "DEATH", "DEPTH",
            "DIRTY", "DOUBT", "DRAFT", "DRAMA", "DREAM", "DRESS", "DRINK", "DREAM", "DRIVE", "EARLY", "EARTH",
            "EMPTY", "ENEMY", "ENJOY", "ENTER", "ENTRY", "EQUAL", "ERROR", "ENTRY", "EVENT", "EXACT", "EXIST",
            "EXTRA", "FAINT", "FAITH", "FALSE", "FAULT", "FIELD", "FIFTH", "FAULT", "FIGHT", "FINAL", "FIRST",
            "FLOOR", "FOCUS", "FORCE", "FRAME", "FRANK", "FRESH", "FRONT", "FRANK", "FRUIT", "FUNNY", "FURTH",
            "GIANT", "GLASS", "GRAND", "GRANT", "GRASS", "GREAT", "GREEN", "GRASS", "GROSS", "GROUP", "GUESS",
            "GUIDE", "HAPPY", "HARSH", "HEART", "HEAVY", "HENRY", "HORSE", "HEAVY", "HOTEL", "HOUSE", "HUMAN",
            "IDEAL", "IMAGE", "IMPLY", "INDEX", "INNER", "INPUT", "ISSUE", "INNER", "JAPAN", "JOINT", "JONES",
            "JUDGE", "KNIFE", "LARGE", "LAUGH", "LAURA", "LAYER", "LEARN", "LAURA", "LEAVE", "LEGAL", "LEVEL",
            "LEWIS", "LIGHT", "LIMIT", "LOCAL", "LOOSE", "LUCKY", "LUNCH", "LOOSE", "MAGIC", "MAJOR", "MARCH",
            "MARRY", "MATCH", "METAL", "MINOR", "MINUS", "MODEL", "MONEY", "MINUS", "MONTH", "MORAL", "MOTOR",
            "MOUTH", "MUSIC", "NAKED", "NASTY", "NAVAL", "NEATH", "NIGHT", "NAVAL", "NOISE", "NORTH", "NOVEL",
            "NURSE", "OCCUR", "OFFER", "ORDER", "OTHER", "OUTER", "OWNER", "OTHER", "PANEL", "PAPER", "PARTY",
            "PEACE", "PETER", "PHASE", "PHONE", "PIANO", "PIECE", "PILOT", "PIANO", "PITCH", "PLACE", "PLAIN",
            "PLANE", "PLANT", "PLATE", "POINT", "POUND", "POWER", "PRESS", "POUND", "PRICE", "PRIDE", "PRIME",
            "PRIOR", "PRIZE", "PROOF", "PROUD", "PROVE", "QUEEN", "QUICK", "PROVE", "QUIET", "RADIO", "RAISE",
            "RANGE", "RAPID", "RATIO", "REACH", "READY", "REFER", "RELAX", "READY", "REPLY", "RIGHT", "RIVER",
            "ROMAN", "ROUGH", "ROUND", "ROUTE", "ROYAL", "RUGBY", "RURAL", "ROYAL", "SCALE", "SCENE", "SCOPE",
            "SCORE", "SENSE", "SERVE", "SHALL", "SHAPE", "SHARE", "SHARP", "SHAPE", "SHEEP", "SHEER", "SHEET",
            "SHIFT", "SHIRT", "SHOCK", "SHOOT", "SHORT", "SIGHT", "SILLY", "SHORT", "SIMON", "SINCE", "SIXTH",
            "SKILL", "SLEEP", "SMALL", "SMART", "SMILE", "SMITH", "SMOKE", "SMILE", "SOLID", "SOLVE", "SORRY",
            "SOUND", "SOUTH", "SPACE", "SPARE", "SPEAK", "SPEED", "SPEND", "SPEAK", "SPITE", "SPLIT", "SPORT",
            "SQUAD", "STAFF", "STAGE", "STAND", "STARK", "START", "STATE", "STARK", "STEAM", "STEEL", "STEEP",
            "STICK", "STILL", "STOCK", "STONE", "STORE", "STUDY", "STUFF", "STORE", "STYLE", "SUGAR", "SUPER",
            "SWEET", "TABLE", "TALLY", "TASTE", "TEACH", "TERRY", "THANK", "TEACH", "THEME", "THICK", "THING",
            "THINK", "THIRD", "THROW", "TIGHT", "TITLE", "TOTAL", "TOUCH", "TITLE", "TOUGH", "TOWER", "TRACK",
            "TRADE", "TRAIN", "TREAT", "TREND", "TRIAL", "TRULY", "TRUST", "TRIAL", "TRUTH", "TWICE", "UNCLE",
            "UNDER", "UNION", "UNITY", "UNTIL", "UPPER", "UPSET", "URBAN", "UPPER", "USUAL", "VAGUE", "VALID",
            "VALUE", "VIDEO", "VISIT", "VITAL", "VOICE", "WASTE", "WATCH", "VOICE", "WATER", "WHERE", "WHILE",
            "WHITE", "WHOLE", "WOMAN", "WORLD", "WORRY", "WOULD", "WRITE", "WORRY", "WRONG", "YOUNG", "YOUTH");

    private String answer;
    private int currentRow;
    private WordleView view;

    /**
     * Wordle Model Constructor
     */
    public WordleModel(){
        this.currentRow = 0;
        setAnswer();
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public String getAnswer() {
        return answer;
    }

    public void addView(WordleView view){ this.view = view; }

    /**
     * Restarts the game
     */
    public void restartGame(){
        currentRow = 0;
        setAnswer();
    }

    /**
     * Moves user to the next row
     */
    public void incrementCurrentRow(){
        if(currentRow < WORDLE_LENGTH) {
            this.currentRow++;
        }
        else{
            view.handleLoss();
        }
    }

    /**
     * Sets an answer for the game
     */
    private void setAnswer() {
        Random rand = new Random();
        int wordToSelect = rand.nextInt(allWords.size());
        this.answer = allWords.get(wordToSelect);
        this.answer = this.answer.toUpperCase();
    }

    /**
     * Helper method to replace indexes of the users
     * guess with underscores that are green or yellow
     * @param str   users guess
     * @param index index of letter that needs to be modified
     * @return  string with underscores where certain indexes were yellow or green
     */
    private String replaceLetter(String str, int index){
        if(str == null || index < 0 || index >= str.length()){
            return str;
        }
        char[] chars = str.toCharArray();
        chars[index] = '_';
        return String.valueOf(chars);
    }

    /**
     * Compares the users guess to the correct answer of the Wordle
     *
     * @param currentGuess users curren guess
     */
    public void compareToAnswer(String currentGuess) {
        if (!allWords.contains(currentGuess)) {  //Invalid word
            view.handleInvalidGuess();
            return;
        }
        if (answer.equals(currentGuess)) {    //Perfect Match
            view.handleWinner();
            return;
        }

        String ans = answer;
        String gus = currentGuess;
        ArrayList<Integer> nonGrey = new ArrayList<>();

        for (int i = 0; i < WORDLE_LENGTH; i++) {
            char a = ans.charAt(i);
            char g = gus.charAt(i);
            if (a != '_' && a == g && ans.contains(String.valueOf(g))) {   //Green
                ans = replaceLetter(ans, i);
                gus = replaceLetter(gus, i);
                view.handleGreenLetter(i);
                nonGrey.add(i);
            }
        }
        for (int i = 0; i < WORDLE_LENGTH; i++) {
            char a = ans.charAt(i);
            char g = gus.charAt(i);
            if (g != '_' && a != g && ans.contains(String.valueOf(g))) {   //Yellow
                int yellowIndex = ans.indexOf(g);
                ans = replaceLetter(ans, yellowIndex);
                gus = replaceLetter(gus, i);
                view.handleYellowLetter(i);
                nonGrey.add(i);
            }
        }
        for (int i = 0; i < WORDLE_LENGTH; i++) {
            if(!nonGrey.contains(i)){
                view.handleGrayLetter(i);
            }
        }
        incrementCurrentRow();
        view.handleGoToNextRow();
    }

}


