package cpsc2150.extendedTicTacToe;
/*
NAME: Philip Merry
        Assignment: CPSC 2150 HW 5
        Class CPSC 2151 - 004
        Due: 11/18/2019 @ 10:00 PM
 */

/**
 * @invariant board_row > 2 && board_col > 2 && min(board_row, board_col) >= wincondition
 * @invariant boardMap is map of key type Character and value List<BoardPos>
 * @invariant 3 <= board_row <= 100 | 3 <= board_col <= 100 
 * @invariant 3 <= wincondition <= 25
 * @correspondence board_row and board_col corresponds to MxN respectively when representing the board
 *                  as a square  matrix of m rows and n columns
 * @correspondence Wincondition corresponds to the number of consecutive characters needed to win
 */
public class GameBoard extends absBoard{
    /*
     * INTERNAL NOTES
     *
     * Shorthand
     *      *pos = the value at pos, I used the dereference as shorthand
     *
     */

    /*
    class attributes
     */

    /*
     * boardSize_ is used for instantiation
     *
     * MAX_SIZE is used for comparison and indexing
     *
     */
    //max size is the max indexable size

    //this means that the game will have MAX SIZE
    //of 7 for the run time of the object.
    //Consider making it
    //non final in future to allow
    //a user to select a MAX_size

    //A soft max of 20k exists, for memory
    //Ill institute a hard max of 99 or so later so that I dont have to worry about
    // fixing the toString method for more than 3 digits
    //private int MAX_SIZE; //allowed val bc Static & final
    private int board_row;// MAX_SIZE + 1; //this is the number of spaces on one side
    private int board_col;
    private int wincondition;
    private char [][] boardArr;
    /*
    class methods
     */
    /**
     * @pre Call when expecting default case game board, 8x8
     *
     * @post the board will be instantiated to 8 spaces along any side
     *       it will required 5 consecutive spaces to win
     *       the board will contain no markers, but instead will
     *       be initialized to "blank" spaces, allowing moves in
     *       any cell until filled.
     * @ensures An instance of one game board with size 8 and
     *        a square grid of characters with dimension 8 x 8
     *        All spaces initialized to ' ' (ascii 'u0032')
     */
    GameBoard()
    {
        //Default constructor gives a 8x8 board requiring
        // 5 consecutive markers  to win. BoardSpaces
        //get init to ' ' (ascii space char)
        int MAX_SIZE = 7;
        wincondition = 5;
        board_row = MAX_SIZE;
        board_col = MAX_SIZE;
        boardArr = new char[board_row][board_col];
        for(int row = 0; row <= board_row; row++)
        {
            for(int col = 0; col <= board_col; col++)
            {
                boardArr[row][col] = ' ';
            }
        }
    }

    /**
     * @pre Call when expecting a game board of variable side length
     *      and variable win condition
     *
     * @post the board will be instantiated to 3 <= #board_size <= 100 spaces along any side
     *      it will required 3 <= wincondition <= 25 consecutive spaces to win
     *      the board will contain no markers, but instead will
     *      be initialized to "blank" spaces, allowing moves in
     *      any cell until filled.
     *
     * @ensures An instance of one game board with size #board_size and
     *    a square grid of characters with dim #board_size x #board_size
     *
     * @param boardrow = the number of rows on the board
     *                   3 <= board_row <= 100 (bound because of other methods handling of size > 100)
     * @param boardcol = the number of cols on the board
     *                  3 <= board_col <= 100 (bound because of other methods handling of size > 100)
     * @param wincond = The number of consecutive spaces needed to win
     *                3 <= wincond <= 25 (This is a soft bound meant to prevent unnecessarily long games)
     */
    public GameBoard(int boardrow, int boardcol, int wincond)
    {
        wincondition = wincond;
        board_row = boardrow;
        board_col = boardcol;
        //MAX_SIZE =  - 1; //set to avoid inappropriate use
        boardArr = new char[board_row][board_col];
        for(int row = 0; row < board_row; row++)
        {
            for(int col = 0; col < board_col; col++)
            {
                boardArr[row][col] = ' '; // u0032 aka ascii space char;
            }
        }
    }


    /**
     * @pre boardArr[marker.getrow()][marker.getcol()] == ' '
     * @post this will overwrite the char in the specified position with #Player
     * @param marker must be an unused boardposition
     * @param player must be a valid player name
     */
    public void placeMarker(BoardPosition marker, char player)
    {   //void method, simply set boardarr to a marker val
        boardArr[marker.getrow()][marker.getcol()] = player;
    }

    /**
     * @pre only use when one needs to know what is at a specific position
     * @post gets char in position from board structure
     * @param pos must be a valid boardpostion
     * @return returns the character at position specified by pos
     */
    public char whatsAtPos(BoardPosition pos)
    {
        char retchar = boardArr[pos.getrow()][pos.getcol()];
        /*
        if(retchar == '\u0000') //used ascii code to be explicit.
        {
            retchar = ' ';
        }
        */
        return(retchar);
    }

    /**
     * @post returns the number of board rows
     * @return returns board_row, the number of rows the board has
     */
    public int getNumRows()
    {
        return(board_row);
    }

    /**
     * @post returns the number of board columns
     * @return returns the board_col, the number of cols the board has
     */
    public int getNumColumns()
    {
        return(board_col);
    }

    /**
     * @post returns the number of consecutive markers needed to confirm a win
     * @return returns wincondition
     */
    public int getNumToWin()
    {
        return(wincondition);
    }
}


