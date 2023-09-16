package pentago.Core;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A constructor for Manager class
 */
public class Manager {
    protected ArrayList<Cell> cells ;
    private final static Manager manager = new Manager();
    protected Manager() {
         cells = new ArrayList<>();
         makeBoard();
    }

    /**
     * Manager class is singleton and this method is to get the only instance of that .
     * @return the only object off Manager class
     */
     public static Manager getInstance(){
        return manager ;
    }

    /**
     * A getter for cells array
     * @return array of cells
     */
    public ArrayList<Cell> getCells() {
        return cells;
    }

    /**
     * A method which make the primitive game board at the beginning of program .
     */
    public void makeBoard(){
        for (int y = 0 ; y <6 ; y++)
            for (int x = 0 ; x <6 ; x++) {
                Cell cell = new Cell(x, y);
                cell.setBlock();
                cell.setPosition();
                cells.add(cell);
            }
    }

    /**
     * A method that makes an array which each element of that shows player of one cell .
     * @return array of players
     */
    public int[] makePlayersTable(){
        int[] arrayToReturn = new int[36] ;
        int i = 0 ;
        for(int y=0 ; y< 6 ; y++)
            for (int x=0  ; x < 6 ; x++ )
                arrayToReturn[i++] = findCellByCoordinates(x , y).getPlayer();
        return arrayToReturn ;
    }

    /**
     * A method that checks whether the player has 5 consecutive pieces by starting from a specific coordinate and going in a certain direction.
     * @param xOfOrigin x coordinates of the cell which wants to be check
     * @param yOfOrigin x coordinates of the cell which wants to be check
     * @param xPlus x coordinates of direction
     * @param yPlus y coordinates of direction
     * @return true if the player wins in that direction and false if not
     */
    public boolean doesPlayerWinInDirection(int player,int xOfOrigin , int yOfOrigin , int xPlus , int yPlus ) {
        int xCopy ;
        int yCopy ;
        for (int i = 0; i < 5; i++) {
            xCopy = xOfOrigin +(xPlus*i);
            yCopy = yOfOrigin + (yPlus*i);
            if (findCellPlayer(xCopy,yCopy) != player)
                return false;
        }

        return true;
    }

    /**
     * A method that checks whether the player has 5 consecutive stones or not .
     * @param player number of player
     * @return true if player has 5 consecutive pieces and false if not
     */
    public boolean doesThePlayerWin(int player ){
        for (int y = 0 ; y < 6 ; y++)
            for (int x = 0 ; x < 6 ; x++) {
                if (doesPlayerWinInDirection(player, x, y, 1, 0))
                    return true;
                if (doesPlayerWinInDirection(player, x, y, 0, 1))
                    return true;
                if (doesPlayerWinInDirection(player, x, y, 1, 1))
                    return true;
                if (doesPlayerWinInDirection(player, x, y, 1, -1))
                    return true;
            }
        return false ;
    }

    /**
     * A method to find and get a cell by its block and position .
     * @param block block of cell
     * @param position position of cell in block
     * @return the cell
     */
    public Cell findCellByPosition(int block , int position ){
       for (int i =0 ; i<cells.size() ; i++){
           if (cells.get(i).getBlock() == block && cells.get(i).getPosition() == position)
               return cells.get(i);
       }
       return null ;
    }

    /**
     * A method to get cell by its coordinates
     * @param x x component of cell
     * @param y x component of cell
     * @return the cell
     */
    public Cell findCellByCoordinates(int x , int y){
        for (Cell cell : cells) {
            if (cell.getX()==x && cell.getY()==y)
                return cell;
        }
        return null ;
    }

    /**
     * A method to find player of cell by its coordinate
     * @param x x component of cell
     * @param y y component of cell
     * @return the cell
     */
    public int findCellPlayer(int x , int y ){
        if(findCellByCoordinates(x,y)!= null)
            return findCellByCoordinates(x,y).getPlayer();
        return -1 ;
    }

    /**
     * A method that checks whether the game has reached its end or not
     * @return true if the game has reached its end and false if not
     */
    public boolean checkEndOfTheGame(){
        if (doesThePlayerWin(1) || doesThePlayerWin(2) )
            return true ;
        for (int y = 0 ; y < 6 ; y++)
            for (int x = 0 ; x < 6 ;x++)
                if (findCellPlayer(x,y)==0)
                    return false ;
        return true ;
    }

    /**
     * A way to perform block rotation process .
     * @param block number of bock
     * @param direction direction that you wants to rotate block in it
     */
    public void doBlockRotation(int block , int direction){
        int [] blockCells = new int[8] ;
        for (int i = 0 ; i < 8 ; blockCells[i]= findCellByPosition(block,i).getPlayer(),i++);
        for (int i =0 ; i<8 ; i++){
            int a = (i-direction)%8 ;
            a = a >=0 ?a :a+8 ;
            findCellByPosition(block , i).setPlayer(blockCells[a]);
        }
    }

    /**
     * A method that checks whether the input is related to placing a piece in a cell is valid or not
     * @return true if the input string is valid and false if not
     */
    private boolean isEnteredCellValidInput(char[] input){
        if (input.length !=3 )
            return false;
        if (input[0]>'5' || input[0] <'0' || input[2]>'5' || input[2]<'0'|| input[1]!=' ')
            return false ;
        if(findCellByCoordinates((int)(input[2])-48,(int)input[0]-48).getPlayer()==0)
            return true ;
        return false ;
    }

    /**
     * A method that checks whether the input is related to rotating a block  is valid or not
     * @return true if input string is valid and false if not
     */
    private boolean isEnteredBlockRotationValid(char[] input){
        if(input.length !=3)
            return false ;
        if (input[0]<'5' && input[0] >'0' && input[1]==' ' && (input[2]=='C' || input[2]=='A') )
            return true ;
        return false ;
    }

    /**
     * A method to get input which related to placing a piece on the board
     * @param player number of player who wants to place a piece
     */
    public void getTheInputCell(int player )
    {
        Scanner scanner = new Scanner(System.in);
        char[] input ;
        System.out.println("Enter the coordinates of your selected cell");
        while (!isEnteredCellValidInput(input = scanner.nextLine().toCharArray())) {
            System.out.println("Your input in not valid ! Enter another one \nValid format = \"x y\" ");
        }
       findCellByCoordinates((int)input[2]-48 ,(int)input[0]-48).setPlayer(player);
    }

    /**
     * A method to get input which related to rotating a block on board
     */
    public void getBlockRotationInput(){
        Scanner scanner = new Scanner(System.in);
        char[] input ;
        System.out.println("Enter block and the direction to rotate ");
        while (!isEnteredBlockRotationValid(input = scanner.nextLine().toCharArray())){
            System.out.println("Your input in not valid ! Enter another one \nValid format = <Block number(between 1 to 4 clockwise)> <(C for clockwise and A for anticlockwise)>\nExample = 2 A which Up-left block will rotate");
        }
        int a = input[2]=='C'?2 :-2 ;
        doBlockRotation((int)input[0]-48, a);
    }
}

