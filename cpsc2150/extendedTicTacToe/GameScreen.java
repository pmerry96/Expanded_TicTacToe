package cpsc2150.extendedTicTacToe;
/*
NAME: Philip Merry
        Assignment: CPSC 2150 HW 5
        Class CPSC 2151 - 004
        Due: 11/18/2019 @ 10:00 PM

 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.lang.Character;

import static java.lang.Character.*;

/**
 * @invariant currPlayer_ = [only Valid Player Name]
 * @invariant lastPlay_ = [A valid filled board position]
 */
public class GameScreen {
    /*
    Below are the class attributes
     */
    //private static int MAX_SIZE;
    //I always want to access this, even if it is null
    private BoardPosition lastPlay_;

    /*
    Below are class methods
     */
    /**
     * @pre nothing must be done before running main
     * @post the program will run and allow end users to play a game of tic
     *          tac toe of ambiguous board size and win condition
     * @param args [the program is not equipped to take command line arguments]
     */
    public static void main(String [] args)
    {
        //I used a fair number of do...while loops in this code. To enhance readability i have commented the purpose
        //of each loop beside the "do" clause as applicable.
        // I also gave some interior comments for any shorthand or nontrivial operations I am doing

        //before we ever start the game, define the boundary object
        GameScreen boardscr = new GameScreen();
        //this is important because it must exist so that we can
        //control the play again conditions and such
        //ensure game runs once, user wouldnt start without wanting to
        //start a game
        do { //while (playagain)
            boolean haswinner = false;
            boolean isdraw = false;
            //start by simply doing a new gameboard, thus
            //we know the grid is not going to have any prefilled spots

            //these are my "pre - game" conditions
            //The below
            Scanner sc = new Scanner(System.in);

            //find out how many players there are
            System.out.println("How many Players would you like to have? Please enter a number between 2 and 10 inclusive.");
            int numplayers = sc.nextInt();
            while(numplayers < 2 || numplayers > 10)
            {
                System.out.println("You cannot have " + numplayers + " players. Please pick a value greater than or equal to 2 and less than or equal to 10");
                numplayers = sc.nextInt();
            }
            //create collecion of player markers
            ArrayList<Character> PlayerList; // = new List<Character>(numplayers));
            PlayerList = new ArrayList<>(0);
            System.out.println("Now the players will select their markers.");
            //take input for the player markers
            for(int i = 0; i < numplayers; i++)
            {
                System.out.println("Player" + (i+1) + ":");
                char chartoadd = sc.next().charAt(0); //assume it is alphabetic
                chartoadd = toUpperCase(chartoadd); //is this an imdepotent method? I could make this faster by removing the "islowercase" check

                while(PlayerList.contains(chartoadd) || chartoadd == ' ') //protect against duplicate player names
                {
                    if(chartoadd == ' ')
                    {
                        System.out.println("You Cannot Play as the Space Character. Please pick a valid symbol");
                    }else {
                        System.out.println(chartoadd + " is taken! Please pick a marker other than:");
                        for (int j = 0; j < PlayerList.size(); j++) //print the used character names, so selection is easier
                        {
                            System.out.println("Player " + (j + 1) + ": " + PlayerList.get(j));
                        }
                    }
                    chartoadd = sc.next().charAt(0);
                    chartoadd = isLowerCase(chartoadd) ? toUpperCase(chartoadd) : chartoadd; //this is the above "islowercase" check, but done with ternary op
                }
                PlayerList.add(chartoadd);//grab the next char
                sc.nextLine(); //this is to eat the enter char and any leftovers
            }

            int bsizerow, bsizecol, winc;
            boolean checkedsize = false, checkedwincond = false;

            //get the number of rows on the board
            do { //while bsizerow within bounds
                if(!checkedsize) {
                    System.out.println("How many spaces tall would you like your board? Please enter a number between 3 and 100 inclusive.");
                    checkedsize = true;
                }else {
                    System.out.println("You entered an invalid size. Please only integers between 3 and 100 inclusive.");
                }
                bsizerow = sc.nextInt();
            }while((bsizerow < 3)||(bsizerow > 100));

            checkedsize = false; //reset to ensure that messages print properly for columns as well
            //get the number of cols on the board
            do{ //while bsizecol within bounds
                    if(!checkedsize) {
                        System.out.println("How many spaces long would you like your board? Please enter a number between 3 and 100 inclusive");
                        checkedsize = true;
                    }else {
                        System.out.println("You entered an invalid size. Please only integers between 3 and 100 inclusive.");
                    }
                    bsizecol = sc.nextInt();
            }while((bsizecol < 3)||(bsizecol > 100));

            //this will execute at least once, taking input for winning circumstances
            do{ //while winc in bounds
                if(!checkedwincond)
                {
                    System.out.println("How many consecutive characters are needed to win? Please enter a number between 3 and 25 inclusive.");
                    checkedwincond = true;
                }else{
                    System.out.println("You entered an invalid win condition. Please only integers between 3 and 25 inclusive.");
                }
                winc = sc.nextInt();
            }while((winc < 3)||(winc > 25));
            //This is to protect against unwinnable games IE concept of MAX_LINEAR_SPACE_COUNT < #winc
            while((winc > bsizerow)&&(winc > bsizecol))
            {
                System.out.println("You have entered an invalid win condition.");
                int minsize = Math.min(bsizecol, bsizerow); //do not replace with math.min, needs another
                                                                          // import statement then
                System.out.println("Please choose a win condition that is less than or equal to " + minsize + "." );
                System.out.println("This is to make sure you do not start an un-winnable game.");
                winc = sc.nextInt();
            }



            //select whether to use the Fast priority or Memory Priority.
            System.out.println("Memory Efficient (m/M) or Fast (f,F) ?");
            char gameopt = sc.next().charAt(0);
            while(gameopt != 'f' && gameopt != 'F' && gameopt != 'm' && gameopt != 'M')
            {
                System.out.println("Please select either F or M");
                gameopt = sc.next().charAt(0);
            }

            //The below code is an example of coding to the interface.
            //both gameboard objs implement the interface, and thus they are both starting life as the interface type
            //being the declared type of the object. The if statement will then "cast" the object to the correct implementation
            //of the interface for the priority the user chose
            IGameBoard board = null;//new GameBoard(bsizerow, bsizecol, winc);
            if(gameopt == 'm' || gameopt == 'M')
            {
                board = new GameBoardMem(bsizerow, bsizecol, winc);
            }else{
                board = new GameBoard(bsizerow, bsizecol, winc);
            }

            // at this point, the game will initialize the other important data to begin
            int playerplace = 0; //this ensures starting player = player1
            char player = PlayerList.get(0), prevplayer = PlayerList.get(PlayerList.size() - 1);
                //both must be instantiated, and this is the only time i will index PlayerList with a value other than
                    //playerplace%numplayers
            BoardPosition nextmove = new BoardPosition(0, 0);  //go into the code with a dummy boardpos
            boardscr.lastPlay_ = nextmove; //this is why we have the dummy position, because I have to check a space to avoid nullptr exceptions
            String boardString; //just make sure it exists in this scope so I will never loos handle on boardString
            boardString = board.toString();
            System.out.println(boardString); //print the initial board  before placement

            while(!(board.checkForWinner(boardscr.lastPlay_, prevplayer)) && (!isdraw)) //this is the gameplay while loop
            {
                int counter = 0; //this counter make
                System.out.println("Player  " + player + ", make a move"); //prompt player to make move based on

                do { //while space is available
                    if(counter > 0)
                    {
                        System.out.println("You picked an invalid spot. Pick Again!");
                    }
                    nextmove = boardscr.getMove(board.getNumRows(), board.getNumColumns());
                    counter++;
                }while(!board.checkSpace(nextmove));//if true, spot valid, return false (quit loop)
                                                    //if false, spot invalid, pick again
                // so it lets me get past here if I give invalid input

                System.out.println("You moved to spot: " + nextmove.toString());
                boardscr.lastPlay_ = nextmove;
                board.placeMarker(boardscr.lastPlay_, player);

                playerplace++; //increment the index of current player's marker, bc its time to change players
                prevplayer = player;
                player = advPlayer(PlayerList, playerplace);

                boardString = board.toString();

                System.out.println(boardString);
                if(board.checkForWinner(boardscr.lastPlay_, prevplayer))
                {
                    haswinner = true;
                }
                //must not have a winner to check for draw
                if((!haswinner)&&(board.checkForDraw()))
                {
                    isdraw = true;
                }
            }
            //add no more statements or this will need brackets
            if(haswinner)
                System.out.println("PLAYER " + prevplayer + " WINS");
            else if(isdraw)
                System.out.println("No player wins. Its a draw.");
        }while((boardscr.playAgain()));
    }


    /**
     * @pre End user must access command line for response. [method can be called without preparation]
     *
     * @post returns true iff players choose to play again
     *
     * @return will return true if the play again option is selected
     */
    private boolean playAgain()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Would You like to play Again? (y,n)");
        String selection = sc.next();
        //assume the player passes ONLY y or n?
        //if not, we have some scrubbin to do

        while((!selection.equals("y"))&&(!selection.equals("n"))&&(!selection.equals("Y"))&&(!selection.equals("N")))
        {
            System.out.println("You're only allowed to enter Y or N, please nothing else");
            selection = sc.next();
        }
        return(selection.charAt(0) == 'y');
    }


    /**
     * @pre advances to the next player in a set of players
     *
     * @post player = Playerlist(playerplace
     *
     * @param Playerlist must be passed either 'X' or 'O'
     */
    //i do not know we will always use char for the player marker, but do know i will
    //always be getting SOMETHING to inherit from object.
    private static char advPlayer(List<Character> Playerlist, int playerplace)
    {
        char player = Playerlist.get(playerplace % Playerlist.size());
        return(player);
    }

    /**
     * @pre [This will ask user for command line response]
     *
     * @post [Will only return BoardPositions that are valid.
     *      No garauntee the space is unused.]
     *
     * @return will return a valid BoardPosition instance
     */
    private BoardPosition getMove(int bsizerow, int bsizecol)
    {
        Scanner sc = new Scanner(System.in);
        int row = -1, col = -1;
        while ((row < 0) || (row >= bsizerow)) {
            System.out.println("What row do you want? Please ONLY enter an Integer");
            row = sc.nextInt();
        }
        while ((col < 0) || (col >= bsizecol)) {
            System.out.println("What column do you want?  Please ONLY enter an Integer");
            col = sc.nextInt();
        }
        BoardPosition movedto = new BoardPosition(row, col);
        //this.lastPlay_ =  movedto;
        return(movedto);
    }

}
