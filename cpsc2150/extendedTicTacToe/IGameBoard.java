package cpsc2150.extendedTicTacToe;
/*
Name: Philip Merry
Class: CPSC 2151 - 004
Assignment: HW 5
Due: 11/18/2019
*/
/**
 * @Correspondence: This corresponds to any abstract representation of a board.
 *
 * @defines: this defines any underlying implementation to define the secondary
 * (non - default) methods specified below.
 *	
 * @defines the concept of the board having players at a position, as such it
 * must support changing data inside data structure, and requires the use of
 * a character player markers.
 * 
 * @constraint board has no dimension greater than 100 placeable spaces
 */
public interface IGameBoard {

    /**
     * @pre equipped to check the char array board for open positions
     *          pos.getrow() and pos.getcol() must both return values within
     *          bounds of the board
     * @post returns true if a space is a valid move to make
     *          (IE true iff whatsAtPos(pos) = ' ')
     * @param pos any BoardPosition object is valid, class invar protects this
     * @return returns true IFF position has no player in it
     */
    default boolean checkSpace(BoardPosition pos)
    {
        //true IFF space is empty, IE ' '
        int row = pos.getrow();
        int col = pos.getcol();
        char at = whatsAtPos(new BoardPosition(row, col));
        return(at == ' ');
    }

    /**
     * @pre must not pass a used boardposition
     * @post this will overwrite the char in the specified position with #Player
     * @param marker must be an unused boardposition
     * @param player must be a valid player name
     */
    void placeMarker(BoardPosition marker, char player);

    /**
     * @pre must pass a used boardposition
     * @post will evaluate the board with respect to win condition
     * @param lastPos this is the position last placed, to check win possibilites from
     * @param player this is the player who stands to win, placed last in lastpos
     */
    default boolean checkForWinner(BoardPosition lastPos, char player)
    {
        //iff all 3 eval false, return false
        //if any one is true, you won on SOME condition
        return ((checkHorizontalWin(lastPos, player))||(checkVerticalWin(lastPos, player))||(checkDiagonalWin(lastPos, player)));
    }

    /**
     * @pre call IFF Win Condition is confirmed to be unmet
     *
     * @post checks the member variable array for stalemate conditions
     *          (IE every position filled, no wins found)
     *
     * @return returns true IFF every position is used and a win isnt in order
     */
    default boolean checkForDraw()
    {
        boolean isfull = true;
        //take advantage of board being square, bound boardsize
        for(int i = 0; i < getNumRows(); i++)
        {
            for(int j = 0; j < getNumColumns(); j++)
            {
                if(whatsAtPos(new BoardPosition(i,j)) == ' ')
                {
                    isfull = false; //any spot unfilled, we can
                    //safely assume it is not a draw until that spot
                    //is filled and checked
                }
            }
        }
        //isfull = true? no winners and nowhere to go - issa draw
        //isfull = false? keep movin buddy
        return(isfull);
    }

    /**
     * @pre must pass a used boardposition
     * @post will evaluate the board with respect to win condition
     * @param lastPos this is the position last placed, to check win possibilites from
     * @param player this is the player who stands to win, placed last in lastpos
     * @return returns true IFF a horizontal win is found containing lastpos
     */
    default boolean checkHorizontalWin(BoardPosition lastPos, char player)
    {
        //this method is capable of being private, but it wont be bc
        //Im not allowed to make the design decisions here.
        //Truly tho, you gave us a "checkwinner" method, which should access our
        //underlying methods to check specifically hor, vert, and diag
        //to give the client access to these methods simply means that they can
        //do incomplete checks on win conditions
        //which is what information hiding aims to avoid
        int row = lastPos.getrow();
        int colmax = getNumColumns();
        //countwin counts consecutive chars
        //wincond is how many must be in a row to win
        int countwin = 0, wincond = getNumToWin();
        for(int i = 0; i < colmax; i++) //start at far left col, iter to far right
        {
            /*
             * because I know it goes sequentially left to right
             * and to win all markers must be consecutive to win
             * I need only count consecutive markers, and return true
             * when I get that number up to the win condition
             */

            if(isPlayerAtPos(new BoardPosition(row, i), player))
            {
                countwin++;
            }else{
                countwin = 0;
            }
            if(countwin >= wincond)
            {
                return(true);
            }
        }
        //if control makes it here, it was NOT a win condition
        return(false);
    }

    /**
     * @pre must pass a used boardposition
     * @post will evaluate the board with respect to win condition
     * @param lastPos this is the position last placed, to check win possibilites from
     * @param player this is the player who stands to win, placed last in lastpos
     * @return returns true IFF a vertical win is found containing lastpos
     */
    default boolean checkVerticalWin(BoardPosition lastPos, char player)
    {   //This seems to be similar to checkhorizontal
        /*
         * IE it will check up and down the column, but count the same way
         * that checkhorizontal did
         */
        int rowmax = getNumRows();
        int col = lastPos.getcol();
        //countwin counts consecutive chars
        //wincond is how many must be in a row to win
        int countwin = 0, wincond = getNumToWin();
        for(int i = 0; i < rowmax; i++) //start at far left col, iter to far right
        {
            /*
             * because I know it goes sequentially left to right
             * and to win all markers must be consecutive to win
             * I need only count consecutive markers, and return true
             * when I get that number up to the win condition
             */
            if(isPlayerAtPos(new BoardPosition(i, col), player))
            {
                countwin++;
            }else{
                countwin = 0;
            }
            if(countwin >= wincond)
            {
                return(true);
            }
        }
        //if control makes it here, it was NOT a win condition
        return(false);
    }

    /**
     * @pre must pass a used boardposition
     * @post will evaluate the board with respect to win condition
     * @param lastPos this is the position last placed, to check win possibilites from
     * @param player this is the player who stands to win, placed last in lastpos
     * @return returns true IFF a diagonal win is found containing lastpos
     */
    default boolean checkDiagonalWin(BoardPosition lastPos, char player)
    {
        int wincond = getNumToWin();
        int row = lastPos.getrow(); //4
        int col = lastPos.getcol(); //4
        int negcountleft = 0;
        int negcountright = 0;
        int poscountleft = 0;
        int poscountright = 0;
        boolean more = true;
        //count neg/left (row--, col--)
        for(int i = row, j = col; (i >= 0)&&(j>=0); i--, j--)
        {
            if((isPlayerAtPos(new BoardPosition(i,j), player))&&(more))
            {
                negcountleft += 1;
            }else{
                //im done with this side
                more = false;
            }
        }
        more = true;
        //now count to the RIGHT of the neg diag
        //take advantage of the "slope" of diagonals being 1/1
        //IE only check when i and j both get incremented together (IE 0,0 -> size,size)
        for(int i = row, j = col; (i < getNumRows())&&(j < getNumColumns()); i++, j++)
        {
            if((isPlayerAtPos(new BoardPosition(i,j), player))&&(more))
            {
                negcountright += 1;
            }else{
                //im done with this side
                more = false;
            }
        }
        //I count the inside part twice, so subtract one in the below statement to correct
        if((negcountleft + negcountright) - 1 >= wincond)
        {
            return(true);
        }
        more = true;
        //poscountleft loop, IE i
        for(int i = row, j = col; (i >= 0)&&(j<getNumColumns()); i--,j++)
        {
            if((isPlayerAtPos(new BoardPosition(i,j), player))&&(more))
            {
                poscountleft += 1;
            }else{
                //im done with this side
                more =  false;
            }
        }
        more = true;
        for(int i = row, j = col; (i < getNumRows())&&(j>=0); i++,j--)
        {
            if((isPlayerAtPos(new BoardPosition(i,j), player))&&(more))
            {
                poscountright += 1;
            }else{
                //im done with this side if player not at the current pos
                more = false;
            }
        }
        return (poscountleft + poscountright) - 1 >= wincond;
    }


    char whatsAtPos(BoardPosition pos);
    /**
     * @pre call when needing to know if a particular character is in a position
     * @post returns true IFF position contains char #player
     * @param pos the position in question to check. 0 <= pos.getrow() < board_rows and 0 <= pos.getcol() < board_col
     * @param player the player who is suspected to be in the position.
     * @return returns true if the player is at the specified position.
     */
    default boolean isPlayerAtPos(BoardPosition pos, char player)
    {
        return(whatsAtPos(pos) == player);
    }

    String toString();

    int getNumRows();

    int getNumColumns();

    int getNumToWin();
}
