package cpsc2150.extendedTicTacToe;

import cpsc2150.extendedTicTacToe.BoardPosition;
import cpsc2150.extendedTicTacToe.GameBoard;
import cpsc2150.extendedTicTacToe.IGameBoard;
import org.junit.Test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestGameBoard
{

    private IGameBoard gb(int boardrow, int boardcol, int wincond) {
        return new GameBoard(boardrow, boardcol, wincond);
    }

    private String Board_to_TestString(String [][] board)
    {
        String t = "";
        if( board.length > 10 || board[0].length > 10) {
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[row].length; col++) {
                    if (row <= 10) {
                        if (col <= 10) {
                            if (row == 0 && col == 0) {
                                t += board[row][col] + " | ";
                            } else if (col == board[row].length - 1) {
                                t += board[row][col] + " \n";
                            } else {
                                t += board[row][col] + " | ";
                            }
                        } else {
                            if (row == 0 && col != board[row].length - 1) {
                                t += board[row][col] + " | ";
                            } else if (row > 0 && col != board[row].length - 1) {
                                t += board[row][col] + "  | ";
                            } else if (row == 0 && col == board[row].length - 1) {
                                t += board[row][col] + "\n";
                            } else if (col == board[row].length - 1) {
                                t += board[row][col] + " \n";
                            } else {
                                t += board[row][col] + "   | ";
                            }
                        }
                    } else {
                        if (col <= 10) {
                            if (col == 0) {
                                t += board[row][col] + "| ";
                            } else if (col == board[row].length - 1) {
                                t += board[row][col] + " \n";
                            } else {
                                t += board[row][col] + " | ";
                            }
                        } else {
                            if (row > 0 && col != board[row].length - 1) {
                                t += board[row][col] + "  | ";
                            } else if (col == board[row].length - 1) {
                                t += board[row][col] + " \n";
                            } else {
                                t += board[row][col] + "   | ";
                            }
                        }
                    }
                }
            }
        }else{
            for(int i = 0; i < board.length; i++)
            {
                for(int j = 0; j < board[i].length; j++)
                {
                    if( j != board.length - 1)
                        t = t + board[i][j] + " | " ;
                    else if(j == board.length - 1)
                        t += board[i][j] + "\n";
                }
            }
        }
        return(t);
    }

    @Test
    public void testConstructor_minSize()
    {
        //the max size of the board in our applications is 100
        // the minimum is 3. We will test both, along with an intermediate value as well
        //win cond is also bound from 3 - 25
        IGameBoard t = gb(3, 3, 3);
        assertEquals(3, t.getNumRows());
        assertEquals(3, t.getNumColumns());
        assertEquals(3, t.getNumToWin());
        for(int i = 0; i < t.getNumRows(); i++)
        {
            for(int j = 0; j <t.getNumRows(); j++)
            {
                assertEquals(' ', t.whatsAtPos(new BoardPosition(i, j)));
            }
        }
    }

    @Test
    public void testConstructor_maxSize()
    {
        //the max size of the board in our applications is 100
        // the minimum is 3. We will test both, along with an intermediate value as well
        //win cond is also bound from 3 - 25
        IGameBoard t = gb(100, 100, 25);
        assertEquals(100, t.getNumRows());
        assertEquals(100, t.getNumColumns());
        assertEquals(25, t.getNumToWin());
        for(int i = 0; i < t.getNumRows(); i++)
        {
            for(int j = 0; j < t.getNumRows(); j++)
            {
                assertEquals(' ', t.whatsAtPos(new BoardPosition(i, j)));
            }
        }
    }
    @Test
    public void testConstructor_maxRows_minCols()
    {
        //this case was chosen to ensure that the array is made properly in a pathalogically unbalanced case.
        IGameBoard t = gb(100, 3, 3);
        assertEquals(100, t.getNumRows());
        assertEquals(3, t.getNumColumns());
        assertEquals(3, t.getNumToWin());
        for(int i = 0; i < t.getNumRows(); i++)
        {
            for(int j = 0; j < t.getNumColumns(); j++)
            {
                assertEquals(' ', t.whatsAtPos(new BoardPosition(i, j)));
            }
        }
    }

    @Test
    public void testConstructor_ExpectedBlankBoardString_size5x5()
    {
        //This test case is to ensure that the constructor makes the board in the way that is exactly expected.
        //This is significant because I want to make sure the board not only initializes the every space to ' '
        //AND prints that value. Any deviation from that behavior would throw off formatting and cause problems
        String board_expected =
                "  | 0 | 1 | 2 | 3 | 4\n" +
                "0 |   |   |   |   |  \n" +
                "1 |   |   |   |   |  \n" +
                "2 |   |   |   |   |  \n" +
                "3 |   |   |   |   |  \n" +
                "4 |   |   |   |   |  \n";
        IGameBoard  t = gb(5, 5, 3); //wincondition parm is innsignificant
        String t_tostring = t.toString();
        assertEquals(board_expected, t_tostring);
    }

    @Test
    public void testConstructor_ExpectedBlankBoardString_size12x12()
    {
        //This test case is distinct because I wish to test how the board prints with 2 digit row and column labels.
        //If it prints the two digit labels but does not adjust space width to match, then again formatting will be off
        // and should be corrected.

        IGameBoard  t = gb(12, 12, 3); //wincondition parm is innsignificant
        String [][] board = new String[13][13];
        for(Integer i = 0; i < board.length; i++)
        {
            for(Integer j = 0; j < board[i].length; j++)
            {
                if(i == 0 && j != 0)
                {
                    Integer colnum = j - 1;
                    board[i][j] = colnum.toString();
                }else if(j == 0 && i != 0){
                    Integer rownum = i - 1;
                    board[i][j] = rownum.toString();
                }else{
                    board[i][j] = " ";
                }
            }
        }
        //now place a char at place "1 , 1" which is 2,2 on our board.
        String board_expected = Board_to_TestString(board);
        assertEquals(board_expected, t.toString());
    }

    @Test
    public void testCheckSpace_occupied()
    {
        char player = 'X';
        IGameBoard t = gb(3, 3, 3);
        for(int i = 0; i < t.getNumRows(); i++)
        {
            for(int j = 0; j < t.getNumRows(); j++)
            {
                t.placeMarker(new BoardPosition(i, j), player);
            }
        }
        for(int i = 0; i < t.getNumRows(); i++)
        {
            for(int j = 0; j < t.getNumRows(); j++)
            {
                assertFalse(t.checkSpace(new BoardPosition(i, j)));
            }
        }
    }

    @Test
    public void testCheckSpace_empty()
    {
        char player = 'X';
        IGameBoard t = gb(3, 3, 3);
        //t.placeMarker(new BoardPosition(0, 0), player);
        //no space is filled, expect true on all spaces
        for(int i = 0; i < t.getNumRows(); i++)
        {
            for(int j = 0; j < t.getNumRows(); j++)
            {
                assertTrue(t.checkSpace(new BoardPosition(i, j)));
            }
        }
    }
    @Test
    public void testCheckSpace_OccupiedWithNullChar()
    {
        //so the checkspace relies on whatsAtPos.
        //because Whatsat it may theoretically possible for a player to choose
        // a null char, we need to make sure that the game will still handle this properly

        //further, I rely upon the characters being checked against some defined blank character. I want to make sure
        //my code successfully distinguishes against the defined blank char (space) and the null char
        char player = '\u0000';
        IGameBoard t = gb(3, 3, 3);
        //t.placeMarker(new BoardPosition(0, 0), player);
        //no space is filled, expect true on all spaces
        for(int i = 0; i < t.getNumRows(); i++)
        {
            for(int j = 0; j < t.getNumColumns(); j++)
            {
                assertTrue(t.checkSpace(new BoardPosition(i, j)));
            }
        }
    }
    @Test
    public void TestCheckHorizontalwin_0throw()
    {
        char player = 'X';
        IGameBoard t = gb(3,3,3);
        BoardPosition lastmove = new BoardPosition(0, 2); //set it to the last move we would make no matter what
        for(int i = 0; i < t.getNumColumns(); i++)
        {
            lastmove = new BoardPosition(0, i);
            t.placeMarker( lastmove, player);
        }
        //the warning that lastmove may be null is just the IDE yelling at the syntax
        //seeing as lastmove gets init while in
        assertTrue(t.checkHorizontalWin(lastmove, player));
    }
    @Test
    public void TestCheckHorizontalwin_lastrow()
    {
        char player = 'X';
        IGameBoard t = gb(3,3,3);
        BoardPosition lastmove = new BoardPosition(2, 2); //set it to the last move we would make no matter what
        for(int i = 0; i < t.getNumColumns(); i++)
        {
            lastmove = new BoardPosition(2, i);
            t.placeMarker( lastmove, player);
        }
        assertTrue(t.checkHorizontalWin(lastmove, player));
    }
    @Test
    public void TestCheckHorizontalwin_checkEveryMove()
    {
        //this test case is to ensure that a win is not registered early, but is registered when it
        //is exactly met.
        char player = 'X';
        IGameBoard t = gb(3,3,3);
        BoardPosition lastmove = null;
        for(int i = 0; i < t.getNumColumns(); i++)
        {
            lastmove = new BoardPosition(0, i);
            t.placeMarker( lastmove, player);
            if( i < 2)
            {
                assertFalse(t.checkHorizontalWin(lastmove, player));
            }else if (i == 2){
                 assertTrue(t.checkHorizontalWin(lastmove, player));
            }
        }
    }
    @Test
    public void TestCheckHorizontalWin_4inarow_wincond3()
    {
        char player = 'X';
        IGameBoard t = gb(4,4,3);
        BoardPosition lastmove = null;
        for(int i = 0; i < t.getNumColumns(); i++)
        {
            //pick some nonextreme row to place these on as I test that prior to this test method
            lastmove = new BoardPosition(2, i);
            t.placeMarker( lastmove, player);
        }
        assertTrue(t.checkHorizontalWin(lastmove, player));
    }

    @Test
    public void TestCheckVerticalwin_0throw()
    {
        char player = 'X';
        IGameBoard t = gb(3,3,3);
        BoardPosition lastmove = new BoardPosition(0, 2); //set it to the last move we would make no matter what
        for(int i = 0; i < t.getNumRows(); i++)
        {
            lastmove = new BoardPosition(i, 0);
            t.placeMarker( lastmove, player);
        }
        //the warning that lastmove may be null is just the IDE yelling at the syntax
        //seeing as lastmove gets init while in
        assertTrue(t.checkVerticalWin(lastmove, player));
    }
    @Test
    public void TestCheckVerticalwin_lastrow()
    {
        char player = 'X';
        IGameBoard t = gb(3,3,3);
        BoardPosition lastmove = new BoardPosition(2, 2); //set it to the last move we would make no matter what
        for(int i = 0; i < t.getNumRows(); i++)
        {
            lastmove = new BoardPosition(i, 2);
            t.placeMarker( lastmove, player);
        }
        assertTrue(t.checkVerticalWin(lastmove, player));
    }
    @Test
    public void TestCheckVerticalwin_checkEveryMove()
    {
        //this test case is to ensure that a win is not registered early, but is registered when it
        //is exactly met.
        char player = 'X';
        IGameBoard t = gb(3,3,3);
        BoardPosition lastmove = null;
        for(int i = 0; i < t.getNumRows(); i++)
        {
            lastmove = new BoardPosition(i, 0);
            t.placeMarker( lastmove, player);
            if( i < 2)
            {
                assertFalse(t.checkVerticalWin(lastmove, player));
            }else if (i == 2){
                assertTrue(t.checkVerticalWin(lastmove, player));
            }
        }
    }
    @Test
    public void TestCheckVerticalWin_4inarow_wincond3()
    {
        char player = 'X';
        IGameBoard t = gb(4,4,3);
        BoardPosition lastmove = new BoardPosition(3, 2);
        for(int i = 0; i < t.getNumColumns(); i++)
        {
            //pick some nonextreme row to place these on as I test that prior to this test method
            lastmove = new BoardPosition(i, 2);
            t.placeMarker( lastmove, player);
        }
        assertTrue(t.checkVerticalWin(lastmove, player));
    }
    @Test
    public void TestCheckDiagonalWin_TopleftCorner()
    {
        char player = 'X';
        IGameBoard t = gb(3,3,3);
        BoardPosition lastmove = new BoardPosition(2, 2);
        for(int i = 0, j = 0; i < t.getNumRows() && j < t.getNumColumns(); i++, j++)
        {
            lastmove = new BoardPosition(i,j);
            t.placeMarker(lastmove, player);
        }
        assertTrue(t.checkDiagonalWin(lastmove, player));
    }
    @Test
    public void TestCheckDiagonalWin_TopRightCorner()
    {
        char player = 'X';
        IGameBoard t = gb(3,3,3);
        BoardPosition lastmove = new BoardPosition(2, 0);
        for(int i = 0, j = t.getNumColumns() - 1; i < t.getNumRows() && j >= 0; i++, j--)
        {
            lastmove = new BoardPosition(i,j);
            t.placeMarker(lastmove, player);
        }
        assertTrue(t.checkDiagonalWin(lastmove, player));
    }
    @Test
    public void TestCheckDiagonalWin_bottomLeftCorner()
    {
        char player = 'X';
        IGameBoard t = gb(3,3,3);
        BoardPosition lastmove = new BoardPosition(0, 2);
        for(int i = t.getNumRows() - 1, j = 0; i >= 0 && j < t.getNumColumns(); i--, j++)
        {
            lastmove = new BoardPosition(i,j);
            t.placeMarker(lastmove, player);
        }
        assertTrue(t.checkDiagonalWin(lastmove, player));
    }
    @Test
    public void TestCheckDiagonalWin_bottomRightCorner()
    {
        char player = 'X';
        IGameBoard t = gb(3,3,3);
        BoardPosition lastmove = new BoardPosition(0, 2);
        for(int i = t.getNumRows() - 1, j = t.getNumColumns() - 1; i >= 0 && j >= 0; i--, j--)
        {
            lastmove = new BoardPosition(i,j);
            t.placeMarker(lastmove, player);
        }
        assertTrue(t.checkDiagonalWin(lastmove, player));
    }
    @Test
    public void TestCheckDiagonalWin_MoreThanWinCond()
    {
        char player = 'X';
        IGameBoard t = gb(5,5,3);
        BoardPosition lastmove = new BoardPosition(4, 4);
        for(int i = 0, j = 0; i < t.getNumRows() && j < t.getNumColumns(); i++, j++)
        {
            lastmove = new BoardPosition(i,j);
            t.placeMarker(lastmove, player);
        }
        assertTrue(t.checkDiagonalWin(lastmove, player));
    }
    @Test
    public void TestCheckDiagonalWin_MiddleWin()
    {
        char player = 'X';
        IGameBoard t = gb(5,5,3);
        BoardPosition lastmove = new BoardPosition(3, 3);
        for(int i = 1, j = 1; i < t.getNumRows() - 1 && j < t.getNumColumns() - 1; i++, j++)
        {
            lastmove = new BoardPosition(i,j);
            t.placeMarker(lastmove, player);
        }
        assertTrue(t.checkDiagonalWin(lastmove, player));
    }
    @Test
    public void TestCheckDiagonalWin_TestAbductEnemy()
    {
        //this test case may seem trivial, but I had problems in the past of counting enemy chars as
        //player chars only in my checkdiagonal case. As such, I include this test case not only for the current
        //state of the game, but to anticipate changes in the future and continually check for past bugs that may resurge
        char player = 'X';
        char enemy  = 'O';
        IGameBoard t = gb(5,5,5);
        BoardPosition lastmove = new BoardPosition(5, 5);
        for(int i = 0, j = 0; i < t.getNumRows()  && j < t.getNumColumns() - 1; i++, j++)
        {
            if(i == 2) { //make a single char the enemy, that position is <2,2>
                lastmove = new BoardPosition(i, j);
                t.placeMarker(lastmove, enemy);
            }else{
                lastmove = new BoardPosition(i, j);
                t.placeMarker(lastmove, player);
            }
        }
        assertFalse(t.checkDiagonalWin(lastmove, player));
    }

    @Test
    public void testCheckForDraw_fullnowins()
    {
        IGameBoard t = gb(4,5,5);
        char player1 = 'A', player2 = 'B', player3 = 'C', player4 = 'D', player5 = 'E';
        BoardPosition lastmove = null;
        for(int i = 0; i < t.getNumRows(); i++)
        {
            for(int j = 0; j < t.getNumColumns(); j++)
            {
                if(j == 0)
                {
                    lastmove = new BoardPosition(i,j);
                    t.placeMarker(lastmove, player1);
                }else if(j == 1){
                    lastmove = new BoardPosition(i,j);
                    t.placeMarker(lastmove, player2);
                }else if(j == 2){
                    lastmove = new BoardPosition(i,j);
                    t.placeMarker(lastmove, player3);
                }else if(j == 3){
                    lastmove = new BoardPosition(i,j);
                    t.placeMarker(lastmove, player4);
                }else if(j == 4){
                    lastmove = new BoardPosition(i,j);
                    t.placeMarker(lastmove, player5);
                }
            }
        }
        assertTrue(t.checkForDraw());
    }
    @Test
    public void testCheckForDraw_notfullnowins()
    {
        IGameBoard t = gb(4,5,5);
        assertFalse(t.checkForDraw());
    }
    @Test
    public void testCheckForDraw_Fullminusone() {
        //this one is to test that it evaluates an ALMOST full board to be an invalid draw
        IGameBoard t = gb(4, 5, 5);
        char player1 = 'A', player2 = 'B', player3 = 'C', player4 = 'D', player5 = 'E';
        BoardPosition lastmove = null;
        for (int i = 0; i < t.getNumRows(); i++) {
            for (int j = 0; j < t.getNumColumns(); j++) {
                if (j == 0) {
                    if (i != 0) {
                        lastmove = new BoardPosition(i, j);
                        t.placeMarker(lastmove, player1);
                    }
                } else if (j == 1) {
                    lastmove = new BoardPosition(i, j);
                    t.placeMarker(lastmove, player2);
                } else if (j == 2) {
                    lastmove = new BoardPosition(i, j);
                    t.placeMarker(lastmove, player3);
                } else if (j == 3) {
                    lastmove = new BoardPosition(i, j);
                    t.placeMarker(lastmove, player4);
                } else if (j == 4) {
                    lastmove = new BoardPosition(i, j);
                    t.placeMarker(lastmove, player5);
                }
            }
        }
        assertFalse(t.checkForDraw());
    }
    @Test
    public void testCheckForDraw_Fullminusoneatbottomright() {
        //this one is to test that it evaluates an ALMOST full board to be an invalid draw
        IGameBoard t = gb(4, 5, 5);
        char player1 = 'A', player2 = 'B', player3 = 'C', player4 = 'D', player5 = 'E';
        BoardPosition lastmove = null;
        for (int i = 0; i < t.getNumRows(); i++) {
            for (int j = 0; j < t.getNumColumns(); j++) {
                if (j == 0) {
                    lastmove = new BoardPosition(i, j);
                    t.placeMarker(lastmove, player1);
                } else if (j == 1) {
                    lastmove = new BoardPosition(i, j);
                    t.placeMarker(lastmove, player2);
                } else if (j == 2) {
                    lastmove = new BoardPosition(i, j);
                    t.placeMarker(lastmove, player3);
                } else if (j == 3) {
                    lastmove = new BoardPosition(i, j);
                    t.placeMarker(lastmove, player4);
                } else if (j == 4) {
                    if(i != 3) {
                        lastmove = new BoardPosition(i, j);
                        t.placeMarker(lastmove, player5);
                    }
                }
            }
        }
        assertFalse(t.checkForDraw());
    }
    @Test
    public void TestWhatsAtPos_NoCharAtPos()
    {
        IGameBoard t = gb(3,3,3);
        assertEquals(' ', t.whatsAtPos(new BoardPosition(1, 1)));
    }
    @Test
    public void TestWhatsAtPos_PlayerCharAtPos()
    {
        //this is a general case test. I expect it to fail if whatsatpos fundamentally doesnt work
        IGameBoard t = gb(3,3,3);
        t.placeMarker(new BoardPosition(1,1), 'X');
        assertEquals('X', t.whatsAtPos(new BoardPosition(1, 1)));
    }
    @Test
    public void TestWhatsAtPos_PlayerInTopLeft()
    {
        //I expect this to fail if whatsatpos does not check the 0th edge correctly
        IGameBoard t = gb(5,5,3);
        t.placeMarker(new BoardPosition(0,0), 'X');
        assertEquals('X', t.whatsAtPos(new BoardPosition(0, 0)));
        String board_expected =
                "  | 0 | 1 | 2 | 3 | 4\n" +
                "0 | X |   |   |   |  \n" +
                "1 |   |   |   |   |  \n" +
                "2 |   |   |   |   |  \n" +
                "3 |   |   |   |   |  \n" +
                "4 |   |   |   |   |  \n";
        //board_unexpected was included to ensure that, should the way these data boards are stored and printed changed, it will check
        //to ensure it still output and place the characters in the appropriate positions relative to board labels. I know it seems silly
        //but this is about testing the current functionality as well as anticipating change in the future.
        String board_unexpected =
                "X | 0 | 1 | 2 | 3 | 4\n" +
                "0 |   |   |   |   |  \n" +
                "1 |   |   |   |   |  \n" +
                "2 |   |   |   |   |  \n" +
                "3 |   |   |   |   |  \n" +
                "4 |   |   |   |   |  \n";
        assertEquals(board_expected, t.toString());
        assertNotEquals(board_unexpected, t.toString());
    }
    @Test
    public void TestWhatsAtPos_PlayerInBottomRight()
    {
        //similarly, I expect this to fail if whatsatpos doesnt check the nth edge correctly
        IGameBoard t = gb(5,5,3);
        t.placeMarker(new BoardPosition(4,4), 'X');
        assertEquals('X', t.whatsAtPos(new BoardPosition(4, 4)));
    }

    @Test
    public void TestWhatsAtPos_EnemyAtPos()
    {
        //I specifically chose 1,1 as my position here because I was avoiding overlap with my edge case placement.
        char player = 'X';
        IGameBoard t = gb(5,5,3);
        t.placeMarker(new BoardPosition(1,1), player);
        assertNotEquals('O', t.whatsAtPos(new BoardPosition(1, 1)));
    }

    @Test
    public void TestisPlayerAtPos_NoCharAtPos()
    {
        IGameBoard t = gb(3,3,3);
        //ensure it does not detect a player at this location
        assertFalse(t.isPlayerAtPos(new BoardPosition(1,1), 'X'));
        //I am abusing my abstraction of isplayeratpos here. I didnt specify in any checks that
        //I cant pass in the space character. As such, I expect it to check if the space
        // 1,1 is filled with a space character. It will return true if it is.
        assertTrue(t.isPlayerAtPos(new BoardPosition(1,1), ' '));
    }


    @Test
    public void TestisPlayerAtPos_PlayerAtPos()
    {
        char player = 'X';
        IGameBoard t = gb(3,3,3);
        t.placeMarker(new BoardPosition(1,1), player);
        assertTrue(t.isPlayerAtPos(new BoardPosition(1,1), player));
    }
    @Test
    public void TestisPlayerAtPos_EnemyAtPos()
    {
        char enemy = 'O', player = 'X';
        IGameBoard t = gb(3,3,3);
        t.placeMarker(new BoardPosition(1,1), enemy);
        assertFalse(t.isPlayerAtPos(new BoardPosition(1,1), player));
    }
    @Test
    public void TestisPlayerAtPos_PlayerInTopLeft()
    {
        char player = 'X';
        IGameBoard t = gb(3,3,3);
        t.placeMarker(new BoardPosition(0,0), player);
        assertTrue(t.isPlayerAtPos(new BoardPosition(0,0), player));
    }
    @Test
    public void TestisPlayerAtPos_PlayerInBottomRight()
    {
        char player = 'X';
        IGameBoard t = gb(3,3,3);
        t.placeMarker(new BoardPosition(2,2), player);
        assertTrue(t.isPlayerAtPos(new BoardPosition(2,2), player));
    }
    @Test
    public void TestPlaceMarker_placeOnEmpty()
    {
        char player = 'X';
        IGameBoard t = gb(3,3,3);
        t.placeMarker(new BoardPosition(1,1), player);
        assertEquals(t.whatsAtPos(new BoardPosition(1,1)), player);

        String [][] board = new String[4][4];
        for(Integer i = 0; i < board.length; i++)
        {
            for(Integer j = 0; j < board[i].length; j++)
            {
                if(i == 0 && j != 0)
                {
                    Integer colnum = j - 1;
                    board[i][j] = colnum.toString();
                }else if(j == 0 && i != 0){
                    Integer rownum = i - 1;
                    board[i][j] = rownum.toString();
                }else{
                    board[i][j] = " ";
                }
            }
        }
        //now place a char at place "1 , 1" which is 2,2 on our board.
        board[2][2] = "X";
        String board_expected = Board_to_TestString(board);
        assertEquals(board_expected, t.toString());
    }
    @Test
    public void TestPlaceMarker_placeOnbottomLeft()
    {
        char player = 'X';
        IGameBoard t = gb(3,3,3);
        t.placeMarker(new BoardPosition(2,0), player);
        assertEquals(t.whatsAtPos(new BoardPosition(2,0)), player);
        String [][] board = new String[4][4];
        for(Integer i = 0; i < board.length; i++)
        {
            for(Integer j = 0; j < board[i].length; j++)
            {
                if(i == 0 && j != 0)
                {
                    Integer colnum = j - 1;
                    board[i][j] = colnum.toString();
                }else if(j == 0 && i != 0){
                    Integer rownum = i - 1;
                    board[i][j] = rownum.toString();
                }else{
                    board[i][j] = " ";
                }
            }
        }
        //now place a char at place "1 , 1" which is 2,2 on our board.
        board[3][1] = "X";
        String board_expected = Board_to_TestString(board);
        assertEquals(board_expected, t.toString());
    }
    @Test
    public void TestPlaceMarker_placeOntopLeft()
    {
        char player = 'X';
        IGameBoard t = gb(3,3,3);
        t.placeMarker(new BoardPosition(0,0), player);
        assertEquals(t.whatsAtPos(new BoardPosition(0,0)), player);
        String [][] board = new String[4][4];
        for(Integer i = 0; i < board.length; i++)
        {
            for(Integer j = 0; j < board[i].length; j++)
            {
                if(i == 0 && j != 0)
                {
                    Integer colnum = j - 1;
                    board[i][j] = colnum.toString();
                }else if(j == 0 && i != 0){
                    Integer rownum = i - 1;
                    board[i][j] = rownum.toString();
                }else{
                    board[i][j] = " ";
                }
            }
        }
        //now place a char at place "1 , 1" which is 2,2 on our board.
        board[1][1] = "X";
        String board_expected = Board_to_TestString(board);
        assertEquals(board_expected, t.toString());
    }
    @Test
    public void TestPlaceMarker_placeOnTopRight()
    {
        char player = 'X';
        IGameBoard t = gb(3,3,3);
        t.placeMarker(new BoardPosition(0,2), player);
        assertEquals(t.whatsAtPos(new BoardPosition(0,2)), player);
        String [][] board = new String[4][4];
        for(Integer i = 0; i < board.length; i++)
        {
            for(Integer j = 0; j < board[i].length; j++)
            {
                if(i == 0 && j != 0)
                {
                    Integer colnum = j - 1;
                    board[i][j] = colnum.toString();
                }else if(j == 0 && i != 0){
                    Integer rownum = i - 1;
                    board[i][j] = rownum.toString();
                }else{
                    board[i][j] = " ";
                }
            }
        }
        //now place a char at place "1 , 1" which is 2,2 on our board.
        board[1][3] = "X";
        String board_expected = Board_to_TestString(board);
        assertEquals(board_expected, t.toString());
    }
    @Test
    public void TestPlaceMarker_placeOnbottomRight()
    {
        char player = 'X';
        IGameBoard t = gb(3,3,3);
        t.placeMarker(new BoardPosition(2,2), player);
        assertEquals(t.whatsAtPos(new BoardPosition(2,2)), player);
        String [][] board = new String[4][4];
        for(Integer i = 0; i < board.length; i++)
        {
            for(Integer j = 0; j < board[i].length; j++)
            {
                if(i == 0 && j != 0)
                {
                    Integer colnum = j - 1;
                    board[i][j] = colnum.toString();
                }else if(j == 0 && i != 0){
                    Integer rownum = i - 1;
                    board[i][j] = rownum.toString();
                }else{
                    board[i][j] = " ";
                }
            }
        }
        //now place a char at place "1 , 1" which is 2,2 on our board.
        board[3][3] = "X";
        String board_expected = Board_to_TestString(board);
        assertEquals(board_expected, t.toString());
    }
}
