package pentago.UI;

import pentago.Core.Cell;
import pentago.Core.Manager;

import java.util.ArrayList;

public class ConsularShow implements Show {
    private final static String ANSI_RESET = "\u001B[0m";
    private final static String ANSI_BLACK = "\u001B[30m";
    private final static String ANSI_RED = "\u001B[31m";
    private final static String ANSI_WHITE = "\u001B[37m";
    private final  String player1 ;
    private final  String player2 ;
    private final  String emptyCell ;
    private Manager manager ;

    public ConsularShow() {
        player1 =  ANSI_BLACK +(char)0x23FA+ ANSI_RESET;
        player2 = ANSI_RED +(char)0x23FA+ ANSI_RESET ;
        emptyCell = ANSI_WHITE +(char)0x23FA +ANSI_RESET;
        manager = Manager.getInstance() ;
    }

    /**
     * A method that makes board to use it in displayBoard method
     * @param primitiveBoard the board with number of each cell player
     * @return the array of board which contains the String that is assigned to each type of cells . with player1 , there are 3 types cell .
     * A cell that contains stone of player1
     * A cell that contains stone of player2
     * A cell that is empty and does not have any stone
     */
    public String[] makeBoardToShow(int[] primitiveBoard){
       String[] finalBoard = new String[36] ;
       for (int i =0 ; i< 36 ; i++)
            switch (primitiveBoard[i]) {
                case 0:
                    finalBoard[i] = emptyCell;
                    break;
                case 1:
                    finalBoard[i] = player1;
                    break;
                case 2:
                    finalBoard[i] = player2;
                    break;
            }

        return finalBoard ;
    }

    /**
     * A method that displays board
     */
    public void displayBoard(){
        String [] a = makeBoardToShow(manager.makePlayersTable());
        System.out.println();
        System.out.println( ANSI_WHITE+"   0"+"  1"+"  2"+"   3"+"  4"+" 5"+ANSI_RESET);
        for (int i =0 ; i<3 ; i++){
            System.out.println(ANSI_WHITE+i+ANSI_RESET+"  "+a[i*6+0]+" "+a[i*6+1]+" "+a[i*6+2]+ANSI_WHITE+" | "+ANSI_RESET+a[i*6+3]+" "+a[i*6+4]+" "+a[i*6+5]);
        }
        System.out.println(ANSI_WHITE+"   -----------------"+ANSI_RESET);

        for (int i =3; i<6 ; i++){
            System.out.println(ANSI_WHITE+i+ANSI_RESET+"  "+a[i*6+0]+" "+a[i*6+1]+" "+a[i*6+2]+ANSI_WHITE+" | "+ANSI_RESET+a[i*6+3]+" "+a[i*6+4]+" "+a[i*6+5]);
        }
        System.out.println();
        if (manager.checkEndOfTheGame())
            showWinner();
    }
    public int findWinner(){
        if (manager.doesThePlayerWin(1) && !manager.doesThePlayerWin(2))
            return 1;
        if (manager.doesThePlayerWin(2) && !manager.doesThePlayerWin(1))
            return 2;
        return 0 ;
    }

    /**
     * A method that shows the winner at the end of the game
    // * @param winner Number of winner
     */
    public void showWinner(){
        switch (1){
            case 1 :
                System.out.println(player1+player1+player1+" Black won! "+player1+player1+player1);
                break;
            case 2 :
                System.out.println(player2+player2+player2+" Red won! "+player2+player2+player2);
                break;
            case 0 :
                System.out.println(emptyCell+emptyCell+emptyCell+" Nobody won! "+emptyCell+emptyCell+emptyCell);
        }
        System.exit(0);
    }
}
