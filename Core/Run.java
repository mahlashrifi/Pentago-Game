package pentago.Core;

import pentago.UI.ConsularShow;
import pentago.UI.GraphicalShow;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

/**
 * A class to run different type of games
 */
public class Run {
    private GraphicalShow gShow;
    private ConsularShow cShow;
    private Manager manager;
    private ArtificialIntelligence ai;
    private static int flag;

    public Run() {
        gShow = new GraphicalShow();
        cShow = new ConsularShow();
        manager = Manager.getInstance();
        flag = 1 ;
    }

    public static void setFlag(int flag) {
        Run.flag = flag;
    }

    public static int getFlag() {
        return flag;
    }

    private void doProcessOfPerformingTheMovementsOfAPlayerInConsularShow(int player) {
        String name = player == 1 ? "Black" : "Red";
        System.out.println(name + " is your turn ");
        manager.getTheInputCell(player);
        cShow.displayBoard();
        manager.getBlockRotationInput();
        cShow.displayBoard();

    }

    private void doProcessOfPerformingTheMovementsOfAPlayerInGraphicalShow() {
        if (flag == 1) {
            gShow.getContentPane().setBackground(new Color(0xAC4C9F));
            gShow.displayBoard();
            gShow.revalidate();
        }
        if (flag == 2) {
            gShow.getContentPane().setBackground(new Color(0x5D883F));
            gShow.displayBoard();
            gShow.revalidate();
        }

    }

    public void runDoubleConsularGame() {
        System.out.println();
        cShow.displayBoard();
        while (true) {
            doProcessOfPerformingTheMovementsOfAPlayerInConsularShow(1);
            doProcessOfPerformingTheMovementsOfAPlayerInConsularShow(2);
        }
    }

    public void SingleConsularGame() {
        cShow.displayBoard();
        while (true) {
            doProcessOfPerformingTheMovementsOfAPlayerInConsularShow(1);
            ai = new ArtificialIntelligence(2);
            int choice[] = ai.makeBestChoice();
            manager.findCellByCoordinates(choice[0] , choice[1]).setPlayer(2);
            manager.doBlockRotation(choice[2] , choice[3]);
            cShow.displayBoard();
        }
    }
    public void DoubleGraphicalGame() {
        gShow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gShow.setSize(450 , 500);
        gShow.setVisible(true);
        gShow.setResizable(false);
        while (true)
            doProcessOfPerformingTheMovementsOfAPlayerInGraphicalShow();
    }
    public void SingleGraphicalGame() {
        gShow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gShow.setSize(450 , 500);
        gShow.setVisible(true);
        gShow.setResizable(false);
        while (true) {
            if (flag == 1) {
                gShow.getContentPane().setBackground(new Color(0xAC4C9F));
                gShow.displayBoard();
                gShow.revalidate();
            }
            if (flag == 2) {
                ai = new ArtificialIntelligence(2);
                int choice[] = ai.makeBestChoice();
                manager.findCellByCoordinates(choice[0] , choice[1]).setPlayer(2);
                manager.doBlockRotation(choice[2] , choice[3]);
                flag = 1;
                gShow.getContentPane().setBackground(new Color(0x54881B));
                gShow.displayBoard();
                gShow.revalidate();
            }
        }
    }

    public void showHelpPanel() {
        System.out.println("Valid formats in console game are mentioned below");
        System.out.println("After question = \"Enter the coordinates of your selected cell\"" +
                "\nValid format = <Block number(between 1 to 4 clockwise)> <(C for clockwise and A for anticlockwise)>" +
                "\nExample = 2 A which Up-left block will rotate");

        System.out.println("");

        System.out.println("After question = \"Enter the coordinates of your selected cell\"" +
                "\nValid format = \"x y\"" +
                "\nExample = 2 1");


    }
}

