package pentago.Core;

import java.util.ArrayList;

public class ArtificialIntelligence extends Manager {
    private int[] bestChoice;
    private int playerIndex;

    /**
     *
     * @param playerIndex number of player which this object of class has built to find best choice of it in this step of game
     */
    public ArtificialIntelligence(int playerIndex) {
        for (int i = 0; i < Manager.getInstance().getCells().size(); i++)
            super.getCells().get(i).setPlayer(Manager.getInstance().getCells().get(i).getPlayer());
        bestChoice = new int[4];
        for (int j = 0; j < 4; j++)
            bestChoice[j] = -1;
        this.playerIndex = playerIndex;
    }

    /**
     * A method that determines whether placing a piece in a specific location causes the player to win
     * @param player number of player
     * @param x x component of player
     * @param y y component of player
     * @return true if placing a piece in the location causes the player to win and false if not
     */
    public boolean isACellCausesToWin(int player , int x , int y) {
        if (findCellPlayer(x , y) != 0)
            return false;
        findCellByCoordinates(x , y).setPlayer(player);
        if (doesThePlayerWin(player)) {
            findCellByCoordinates(x , y).setPlayer(0);
            return true;
        }
        findCellByCoordinates(x , y).setPlayer(0);
        return false;
    }

    /**
     * A method that determines whether rotating a block in a specific direction causes the player to win
     * @param player number of player
     * @param block number of block
     * @param direction number of direction that is 2 or -2
     * @return true if rotating block in the mentioned direction causes the player to win and false if not
     */
    public boolean isABlockRotationCausesToWin(int player , int block , int direction) {
        doBlockRotation(block , direction);
        if (doesThePlayerWin(player) && !doesThePlayerWin(3-player)) {
            doBlockRotation(block , -1 * direction);
            return true;
        }
        doBlockRotation(block , -1 * direction);
        return false;
    }

    /**
     * A method that checks whether the pair of rotating blocks in the specified direction and placing the stone in a specific location causes the player to win.
     * @param player number of player
     * @param x x component of player
     * @param y y component of player
     * @param block number of block
     * @param direction number of direction that is 2 or -2
     * @return
     */
    public boolean isPairCausesToWin(int player , int x , int y , int block , int direction) {
        if (findCellPlayer(x , y) != 0)
            return false;
        findCellByCoordinates(x , y).setPlayer(player);
        doBlockRotation(block , direction);
        if (doesThePlayerWin(player) && !doesThePlayerWin(3-player)) {
            doBlockRotation(block , -1 * direction);
            findCellByCoordinates(x , y).setPlayer(0);
            return true;
        }
        doBlockRotation(block , -1 * direction);
        findCellByCoordinates(x , y).setPlayer(0);
        return false;
    }

    /**
     * A method to find the location of the cell where placing piece on it cause player to win
     * @param player number of player
     * @return array which contain information of block rotation
     */
    private int[] findCellThatCausesToWin(int player) {
        int[] arrayToReturn = new int[4];
        arrayToReturn[2] = -1;
        arrayToReturn[3] = -1;
        for (int y = 0; y < 6; y++)
            for (int x = 0; x < 6; x++)
                if (isACellCausesToWin(player , x , y)) {
                    arrayToReturn[0] = x;
                    arrayToReturn[1] = y;
                    return arrayToReturn;
                }
        return null;
    }

    /**
     * A method to find the direction and the block which rotating the block on that direction cause player to win
     * @param player number of player
     * @return array which contain information of block rotation
     */
    private int[] findBlockRotationThatCausesToWin(int player) {
        int[] arrayToReturn = new int[4];
        arrayToReturn[0] = -1;
        arrayToReturn[0] = -1;
        for (int i = 1; i < 5; i++) {
            if (isABlockRotationCausesToWin(player , i , 2)) {
                arrayToReturn[2] = i;
                arrayToReturn[3] = 2;
                return arrayToReturn;
            }
            if (isABlockRotationCausesToWin(player , i , -2)) {
                arrayToReturn[2] = i;
                arrayToReturn[3] = -2;
                return arrayToReturn;
            }
        }
        return null;
    }

    /**
     * A method to find the pair cause player to win
     * @param player number of player
     * @return array which contain information of the pair
     */
    private int[] findPairThatCausesToWin(int player) {
        int[] arrayToReturn = new int[4];
        for (int y = 0; y < 6; y++)
            for (int x = 0; x < 6; x++)
                for (int j = 1; j < 5; j++) {
                    if (isPairCausesToWin(player , x , y , j , 2)) {
                        arrayToReturn[0] = x;
                        arrayToReturn[1] = y;
                        arrayToReturn[2] = j;
                        arrayToReturn[3] = 2;
                        return arrayToReturn;
                    }
                    if (isPairCausesToWin(player , x , y , j , -2)) {
                        arrayToReturn[0] = x;
                        arrayToReturn[1] = y;
                        arrayToReturn[2] = j;
                        arrayToReturn[3] = -2;
                        return arrayToReturn;
                    }
                }
        return null;
    }

    /**
     * A method that checks whether the player has some(numberOfEnoughStones) consecutive pieces by starting from a specific coordinate and going in a certain direction.
     * @param xOfOrigin x coordinates of the cell which wants to be check
     * @param yOfOrigin x coordinates of the cell which wants to be check
     * @param xPlus x coordinates of direction
     * @param yPlus y coordinates of direction
     * @param numberOfEnoughStones The number of stones to be checked is whether the player has that number of consecutive stones in the mentioned direction and origin
     * @return true if the player has that number of stones in the mentioned direction and false if not
     */
    public boolean doesPlayerHaveEnoughStonesInDirection(int player,int xOfOrigin , int yOfOrigin , int xPlus , int yPlus , int numberOfEnoughStones ) {
        int xCopy ;
        int yCopy ;
        for (int i = 0; i < numberOfEnoughStones; i++) {
            xCopy = xOfOrigin +(xPlus*i);
            yCopy = yOfOrigin + (yPlus*i);
            if (findCellPlayer(xCopy,yCopy) != player)
                return false;
        }
        return true;
    }

    /**
     * A method that checks whether the player has some(numberOfEnoughStones) consecutive pieces .
     * @param player number of player
     * @param numberOfEnoughStones The number of stones to be checked is whether the player has that number of consecutive stones
     * @return  true if the player has that number of stones and false if not
     */
    public boolean doesThePlayerHaveEnoughStones(int player , int numberOfEnoughStones){
        for (int y = 0 ; y < 6 ; y++)
            for (int x = 0 ; x < 6 ; x++) {
                int [] xPlus = {1 , 0 , 1 , 1 , -1 , 0 , -1 , -1};
                int [] yPlus = {0 , 1 , 1 , -1 , 0 , -1 , -1 , 1};
                for (int i = 0 ; i<8 ; i++)
                if (doesPlayerHaveEnoughStonesInDirection(player, x, y, xPlus[i], yPlus[i],numberOfEnoughStones))
                    return true;
            }
        return false ;
    }

    /**
     * A method for finding the cell that placing stone on it will lead the largest number of stone in one direction that is possible
     * @param player number of player
     * @return the information for placing stone on cell(x component and y component of cell)
     */
   private int[] findCellChoiceThatMakesTheHighestNumberOfAlignedCells(int player) {
       int[] arrayToReturn = new int[4];
       arrayToReturn[2] = -1;
       arrayToReturn[3] = -1;
           for (int j = 4; j >= 0; j--)
               for (int y = 0; y < 6; y++) {
                   for (int x = 0; x < 6; x++) {
                       if (findCellByCoordinates(x , y).getPlayer() == 0) {
                           findCellByCoordinates(x , y).setPlayer(player);
                           if (doesThePlayerHaveEnoughStones(player , j)) {
                               arrayToReturn[0] = x;
                               arrayToReturn[1] = y;
                               findCellByCoordinates(x , y).setPlayer(0);
                               return arrayToReturn;
                           }
                           else
                               findCellByCoordinates(x , y).setPlayer(0);
                       }
                   }
               }
           return null;
   }

    /**
     * A method for finding the block and direction that rotating block on that direction  will lead the largest number of stone in one direction that is possible
     * @param player number of player
     * @return information of block and direction
     */
    private int[] findBlockRotationChoiceThatMakesTheHighestNumberOfAlignedCells(int player){
        int[] arrayToReturn = new int[4];
        arrayToReturn[0] = -1 ;
        arrayToReturn[1] = -1 ;
        for (int j = 4 ; j >= 0 ; j--)
                for (int i = 1 ; i < 5 ; i++) {
                    doBlockRotation(i , 2);
                    if (doesThePlayerHaveEnoughStones(player , j)){
                        arrayToReturn[2]= i ;
                        arrayToReturn[3]= 2 ;
                        doBlockRotation(i , -2);
                        return arrayToReturn ;
                    }
                    doBlockRotation(i , -2);
                    doBlockRotation(i , -2);
                    if (doesThePlayerHaveEnoughStones(player , j)){
                        arrayToReturn[2]= i ;
                        arrayToReturn[3]= -2 ;
                        doBlockRotation(i , 2);
                        return arrayToReturn ;
                    }
                        doBlockRotation(i , 2);

                }
        return null ;
    }

    /**
     * A method that takes a process to make the player win in its turn(if possible)
     */
    public void winDirectly() {
        if (findCellThatCausesToWin(playerIndex) != null) {
            bestChoice[0] = findCellThatCausesToWin(playerIndex)[0];
            bestChoice[1] = findCellThatCausesToWin(playerIndex)[1];
            return;
        }
        if (findBlockRotationThatCausesToWin(playerIndex) != null) {
            bestChoice[2] = findBlockRotationThatCausesToWin(playerIndex)[2];
            bestChoice[3] = findBlockRotationThatCausesToWin(playerIndex)[3];
            return;
        }
        if (findPairThatCausesToWin(playerIndex) != null) {
            bestChoice = findPairThatCausesToWin(playerIndex);
            return;
        }
    }

    /**
     * A method to set the best selected cell according to the current situation
     */
    public void setBestCellChoice(){
        if (findCellThatCausesToWin(3-playerIndex)!= null){
            bestChoice[0] = findCellThatCausesToWin(3 - playerIndex)[0] ;
            bestChoice[1] = findCellThatCausesToWin(3 - playerIndex)[1] ;
            return;
        }
        if(findPairThatCausesToWin(3 - playerIndex)!= null){
            bestChoice[0] = findPairThatCausesToWin(3 - playerIndex)[0] ;
            bestChoice[1] = findPairThatCausesToWin(3 - playerIndex)[1] ;
            return;
        }
        int[] a = findCellChoiceThatMakesTheHighestNumberOfAlignedCells(playerIndex);
        bestChoice[0] = a[0];
        bestChoice[1] = a[1];
    }

    /**
     * A method to set the best selected block and direction according to the current situation
     */
    public void setBestBlockRotationChoice(){
        if (findCellThatCausesToWin(3-playerIndex)!= null){
            bestChoice[2] = findBlockRotationThatCausesToWin(3 - playerIndex)[2] ;
            bestChoice[3] = findBlockRotationThatCausesToWin(3 - playerIndex)[3] ;
            return;
        }
        if(findPairThatCausesToWin(3 - playerIndex)!= null){
            bestChoice[2] = findPairThatCausesToWin(3 - playerIndex)[2] ;
            bestChoice[3] = findPairThatCausesToWin(3 - playerIndex)[3] ;
            return;
        }
        int[] a = findBlockRotationChoiceThatMakesTheHighestNumberOfAlignedCells(playerIndex);
        bestChoice[2] = a[2] ;
        bestChoice[3] = a[3] ;
    }

    /**
     * A method to make best choice array
     * @return bestChoice array . bestChoice array contain bellow information
     * bestChoice[0] = x component of a cell
     * bestChoice[1] = y component of a cell
     * bestChoice[2] = number of a block
     * bestChoice[3] = direction of a block rotation that is 2 or -2
     */
    public int[] makeBestChoice(){
        winDirectly();
        if(bestChoice[0]+bestChoice[1]==-2) {
            setBestCellChoice();
            findCellByCoordinates(bestChoice[0] , bestChoice[1]).setPlayer(playerIndex);
        }
        if(bestChoice[2]+bestChoice[3]==-2) {
            setBestBlockRotationChoice();
        }
        return bestChoice;
    }

}