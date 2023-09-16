package pentago;

import pentago.Core.Manager;
import pentago.Core.Run;
import pentago.UI.GraphicalShow;

import pentago.UI.GraphicalShow;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Run run = new Run();
        Scanner scanner = new Scanner(System.in) ;
        System.out.println("\nChoose type of your game :\n1) Single (Consular)\n2) Double (Consular)\n3) Single (Graphical)\n4) Double (Graphical)\n5) Help");
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("1")) {
                run.SingleConsularGame();
                break;
            }
            else if (input.equals("2")) {
                run.runDoubleConsularGame();
                break;
            }
            else if (input.equals("3")) {
                run.SingleGraphicalGame();
                break;
            }
            else if (input.equals("4")) {
                run.DoubleGraphicalGame();
                break;
            }
            else if (input.equals("5")) {
                run.showHelpPanel();
                break;
            }
            else System.out.println("Invalid input ! Enter valid input");
        }

    }
    }


