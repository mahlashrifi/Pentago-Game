package pentago.Core;

public class Cell {

    private int x ;
    private int y ;
    private int position ;
    private int block ;
    private int player ;

    /**
     * A constructor for Cell class
     * @param x Component x the coordinates of the cell on the screen
     *  @param y Component y the coordinates of the cell on the screen
     */

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        player = 0 ;
    }


    /**
     * A method which assigned position of cell in its block to cell position field according to below table
     * for each block we have below table :
     * |0    1   2|        |0   1  2|
     * |10  11  12|  ->    |7   9  3|
     * |20  21  22|        |6   5  4|
     */
    public void setPosition() {
        int assignedNum = 10 *(y%3)+(x%3);
        switch (assignedNum){
            case 0 :
                position = 0;
                break;
            case 1 :
                position = 1;
                break;
            case 2 :
                position = 2;
                break;
            case 10 :
                position = 7;
                break;
            case 11 :
                position = 9;
                break;
            case 12 :
                position = 3;
                break;
            case 20 :
                position = 6;
                break;
            case 21 :
                position = 5;
                break;
            case 22 :
                position = 4;
                break;
        }
    }

    /**
     * A method that sets block of each cell
     */
    public void setBlock() {
     if(x < 3 && y < 3)
         block = 1 ;
     else if (x >= 3 && y < 3)
         block = 2 ;
     else if (x < 3 && y >= 3)
         block = 3 ;
     else if (x >= 3 && y >= 3)
         block = 4;
    }

    /**
     * A method to set player of each cell ( if player is 0 means there is no stone in the cell )
     * @param player (index of player)
     */
    public void setPlayer(int player) {
        if (player==0 ||player==1 || player==2)
            this.player = player ;
    }

    /**
     * A getter for Block field
     * @return block of cell
     */
    public int getBlock() {
        return block;
    }

    /**
     * A getter for position field
     * @return position of cell
     */
    public int getPosition() {
        return position;
    }

    /**
     * A getter for player field
     * @return player
     */
    public int getPlayer() {
        return player;
    }

    /**
     * A getter for x field
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * A getter for y field
     * @return y
     */
    public int getY() {
        return y;
    }
}
