/*
    Name: Philip Merry
    Class: CPSC 2151 - 004
    Assignemtn: HW 5
    Due: 11/18/2019
*/

package cpsc2150.extendedTicTacToe;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
/**
    @invariant board_row > 2 && board_col > 2 && min(board_row, board_col)
    >=wincondtion
    @invariant board_row <= 100 && board_col <= 100
    @invariant 3 <= wincondition <= 25

    @correspondence board_row and board_col refer to number of rows and columns
    respectively when representing board as a 2d grid

    @correspondence wincondition is the number of consecutive characters to win

*/
public class GameBoardMem extends absBoard{
    private int board_row;// MAX_SIZE + 1; //this is the number of spaces on one side
    private int board_col;
    private int wincondition;
    //the way this will work , is that we have a 1d array of characters key values
        //the characters key value corresponds to a reference to and ArrayList containing
        //the locations the character (specified by key) is at
    private Map<Character, ArrayList<BoardPosition> > boardMap;

    /**
     * @ensures a gameboard of size #boardrow x #boardcol will be made with the criteria to win defined by
     *          #wincond. The board will take moves from players characters
     * @param boardrow : 3 <= #boardrow <= 100
     * @param boardcol : 3 <= #boardcol <= 100
     * @param wincond : 3 <= #wincond <= 25
     * @param playerList : #playerlist.size() > 0 &&  ['A', 'Z'] ∈ playerlist
     */
    GameBoardMem(int boardrow, int boardcol, int wincond)
    {
        boardMap = new HashMap<>(); //instantiate blank hashmap
        board_row = boardrow;
        board_col = boardcol;
        wincondition = wincond;
    }

    /**
     * @pre 0 <= marker.getRow() < board_row && 0 <= marker.getCol() < board_col && (checkspace(marker) == true)
     * @param marker must be an unused boardposition
     * @param player must be a valid player name
     */
    @Override
    public void placeMarker(BoardPosition marker, char player) {
        if(!boardMap.containsKey(player))
        {
            boardMap.put(player, new ArrayList<BoardPosition>());
        }
        boardMap.get(player).add(marker);
    }

    /**
     * @pre - [no special cases]
     * @param pos - the position to look to for a player value
     * @return - returns ' ' iff pos ∉ boardMap.get(<for all players>)
     *          otherwise returns the key that allowed pos ∈ boardMap.get(player_char) to evaluate true
     */
    @Override
    public char whatsAtPos(BoardPosition pos) {
        for(Map.Entry<Character, ArrayList<BoardPosition>> m: boardMap.entrySet())
        {
            Character cur = m.getKey();
            if (boardMap.get(cur).contains(pos)) return(cur);
            //  |^^^^^^^^^^^^^^^|
            //  evals to type ArrayList
            //thus this is the list that contains the spaces the marker is on, and we are checking "contains"
            //with arguments being the position we wish to verify is empty or full
        }
        return ' ';
    }

    /**
     * @pre - 0 <= #pos.getRow() <= board_row && 0 <= #pos.getCol() <= board_col
     *          && #boardMap.containsKey(player)
     * @post - returns true iff pos ∉ boardMap.get(player)
     * @param pos the position in question to check. 0 <= #pos.getrow() < board_rows and 0 <= #pos.getcol() < board_col
     * @param player the player who is suspected to be in the position.
     * @return true iff player is at position defined by #pos
     */
    @Override
    public boolean isPlayerAtPos(BoardPosition pos, char player)
    {
        //return true when player key's value contains pos
        //return false when doesn contain pos
        if(boardMap.containsKey(player))
            return(boardMap.get(player).contains(pos));
        else
            return false;
    }

    /**
     * @post returns the number of board rows
     * @return returns #board_row, the number of rows the board has
     */
    public int getNumRows() {
        return board_row;
    }

    /**
     * @post returns the number of board columns
     * @return returns #board_col, the number of cols the board has
     */
    public int getNumColumns() {
        return board_col;
    }

    /**
     * @post returns the number of consecutive markers needed to confirm a win
     * @return returns #wincondition
     */
    public int getNumToWin() {
        return(wincondition);
    }
}
