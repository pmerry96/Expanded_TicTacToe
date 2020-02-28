/*
Name: Philip Merry
Class: CPSC 2151 - 004 (lab section)
Assignment: HW5
Due: 11/18/2019 @ 10:00PM
*/
package cpsc2150.extendedTicTacToe;

public abstract class absBoard implements IGameBoard {

    /**
     * @pre pass a board as a character array
     * @post will create this a string representation of gameboard, formatted for Tic Tac Toe
     * @return returns a string to the string representation of board
     */
    public String toString()
    {
        int boardrow = getNumRows(), boardcol = getNumColumns();
        int toadd, jin, iin;
        String boardStr = "";
        BoardPosition thispos;
        for(int i = 0; i <= boardrow; i++)
        {
            for(int j = 0; j <= boardcol; j++)
            {
                if(i == 0)//toprow, dont count this
                {
                    toadd = j - 1;
                    if(j == 0)
                    {
                        boardStr = boardStr + "  | ";
                        //note the commented out part is implied on this next  line
                    } else if((j < boardcol)){
                        boardStr = boardStr + toadd + " | ";
                    }else{//j == boardcol
                        boardStr = boardStr + toadd +'\n';//no ending pipe
                    }
                }else if (i <= 10){ //all other rows
                    toadd = i - 1;
                    iin = i - 1; //get rid of toprow
                    jin = j - 1; //to get rid of leftrow
                    thispos = new BoardPosition(iin, jin);
                    if(j == 0)
                    {
                        boardStr = boardStr + toadd + " |";
                        //print the char on the end of the line, repped by
                    } else if((j < boardcol)){
                        if(j <= 10) {
                            boardStr = boardStr + " " + whatsAtPos(thispos) + " |";
                        }else {
                            boardStr = boardStr + "  " + whatsAtPos(thispos) + " |";
                        }
                    }else{//j == boardsize
                        if(j <= 10){
                            boardStr = boardStr + " " + whatsAtPos(thispos) + "\n";
                        }else {
                            boardStr = boardStr + "  " + whatsAtPos(thispos) + "\n";
                        }
                    }
                }else {
                    toadd = i - 1;
                    iin = i - 1; //get rid of toprow
                    jin = j - 1; //to get rid of leftrow
                    thispos = new BoardPosition(iin, jin);
                    if(j == 0)
                    {
                        boardStr = boardStr + toadd + "|";
                        //print the char on the end of the line, repped by
                    } else if((j < boardcol)){
                        if(j <= 10) {
                            boardStr = boardStr + " " + whatsAtPos(thispos) + " |";
                        }else {
                            boardStr = boardStr + "  " + whatsAtPos(thispos) + " |";
                        }
                    }else{//j == boardsize
                        if(j <= 10){
                            boardStr = boardStr + " " + whatsAtPos(thispos) + "\n";
                        }else {
                            boardStr = boardStr + "  " + whatsAtPos(thispos) + "\n";
                        }
                    }
                }
            }
        }
        return(boardStr);
    }
}
