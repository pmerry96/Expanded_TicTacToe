package cpsc2150.extendedTicTacToe;
/*
NAME: Philip Merry
        Assignment: CPSC 2150 HW 5
        Class CPSC 2151 - 004
        Due: 11/18/2019 @ 10:00 PM

 */
/**
 * @invariant row < board_row and col < board_col
 */

public class BoardPosition{
    /*
    This is the class attributes
     */
    private int row_ , col_;
    /*
    this is the class methods
     */

    /**
     *
     * @post if row > MAX_SIZE or col > MAX_SIZE a default case is made
     *       otherwise boardposition instance created
     *
     */
    public BoardPosition(int row, int col) //constructor is always a primary method
    {
        row_ = row;
        col_ = col;
    }

    /**
     * @pre [no special consideration]
     *
     * @post will only return the value held by #row_
     *
     * @return this will return the attribute row_
     */
    public int getrow()
    {
        return(this.row_);
    } //must be primary bc it cannot call itself

    /**
     * @pre [no special consideration]
     *
     * @post will only return the value held by #col_
     *
     * @return this will return the attribute col_
     */
    public int getcol()
    {
        return(this.col_);
    } //also must be primary

    /**
     * @pre [no consideration]
     *
     * @post if passed a BoardPosition Object,
     *      compares elementwise, two BoardPosition objects
     *      returns true IFF pos instanceof BoardPosition
     *          && this.getrow() = pos.getrow()
     *          && this.getcol() = pos.getcol()
     *      otherwise - returns false
     *
     * @return returns true IFF pos instanceof BoardPosition && this.getRow() == pos.getRow() && this.getCol() == pos.getCol()
     */
    @Override
    public boolean equals(Object pos) //made secondary by changing this.col_ to this.getcol()
    {
        if(pos instanceof BoardPosition) {
            return ((this.getrow() == ((BoardPosition) pos).getrow()) && (this.getcol() == ((BoardPosition) pos).getcol()));
        }else {
            return (false); //didnt pass an instance of BP, thus it cannot be equal to a board position
        }
    }//this is now being used. It was corrected to ensure that it takes any reference type and only evals equality when
        //a Boardposition object has been passed

    /**
     * @pre any boardposition object can be safely passed
     *
     * @post this will create a string representation of a specific coordinate
     *          [EX Output: <row>,<col>]
     *
     * @return returns a string in format <row>,<col>
     */
    public String toString() { //is already secondary by changing this.row_ to this.getrow()
        return(this.getrow() + "," + this.getcol());
    }
}
