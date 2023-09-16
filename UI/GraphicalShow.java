package pentago.UI;

import pentago.Core.Manager;
import pentago.Core.Run;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
    public class GraphicalShow extends JFrame implements Show {
        private final ArrayList<JButton> buttons;
        private final Icon player1;
        private final Icon player2;
 //       private final Icon empty;
        private final Color color;
        private final Color color1;
        private final Color color2;
        private Manager manager;

        /**
         * A constructor for GraphicalShow class
         *
         * @throws HeadlessException
         */
        public GraphicalShow() throws HeadlessException {
            super("Pentago");
            setLayout(new FlowLayout());
            buttons = new ArrayList<>();
            player1 = new ImageIcon(getClass().getResource("player1.png"));
            player2 = new ImageIcon(getClass().getResource("player2.png"));
  //          empty = new ImageIcon(getClass().getResource("empty.png"));
            color = new Color(0xFFFFB710, true);
            color1 = new Color(0x000000);
            color2 = new Color(0x88001B);
            manager = Manager.getInstance();
            JLabel jLabel = new JLabel("");
            jLabel.setPreferredSize(new Dimension(450 , 15));
            add(jLabel);
            JLabel label = new JLabel(" ");
            label.setPreferredSize(new Dimension(460, 8));
            add(label);
            for (int i = 0; i < 36; i++) {
                if (i % 6 == 3)
                    add((new JLabel("  ")));
                if (i == 18)
                    add(label);
                JButton button = new JButton();
                button.setBackground(color);
                button.setPreferredSize(new Dimension(60, 60));
                add(button);
                ButtonHandler handler = new ButtonHandler();
                button.addActionListener(handler);
                buttons.add(button);
            }

        }

        /**
         * A method that updates and shows JFrame
          */
        public void displayBoard() {
            Icon icon = Run.getFlag() == 1 ? player1 : player2;
            for (int i = 0; i < 36; i++) {
                switch (manager.makePlayersTable()[i]) {
                    case 0:
                        buttons.get(i).setIcon(null);
                        buttons.get(i).setRolloverIcon(icon);
                        break;
                    case 1:
                        buttons.get(i).setIcon(player1);
                        buttons.get(i).setRolloverIcon(null);
                        break;
                    case 2:
                        buttons.get(i).setIcon(player2);
                        buttons.get(i).setRolloverIcon(null);
                        break;
                }
            }
            if(manager.checkEndOfTheGame())
                showWinner();
        }

        /**
         * A method that shows game winner and ends the programme
         */
        public void showWinner() {
            String textShow;
        if(manager.doesThePlayerWin(1) && !manager.doesThePlayerWin(2))
                    textShow = "Black won!";
        else if (!manager.doesThePlayerWin(1) && manager.doesThePlayerWin(2))
                    textShow = "Red won!";
        else textShow = "Nobody won!";

            JOptionPane.showMessageDialog(GraphicalShow.this, textShow);
            System.exit(0);
        }

        /**
         * A method to find which button has been clicked
         * @return the index of clicked button in Array list of buttons
         */
        public int findClickedButton(ActionEvent e) {
            for (int i = 0; i < 36; i++)
                if (e.getSource().equals(buttons.get(i)))
                    return i;
            return -1;
        }

        /**
         * @param i index of Button cell which has been clicked
         * @return true if the cell is empty and it is possible to add new stone to it and false if not
         */
        public boolean setPlayerByIndexOfButton(int i) {
            Icon icon = Run.getFlag() ==1 ? player1 : player2 ;
            int x = (i % 6);
            int y = (i - x) / 6;
            if (manager.findCellPlayer(x, y) == 0) {
                manager.findCellByCoordinates(x, y).setPlayer(Run.getFlag());
                buttons.get(i).setIcon(icon);
                buttons.get(i).setRolloverIcon(null);
                return true;
            }
            return false;

        }

        /**
         * A methods that get user input and rotate selected block in selected direction
         */
        public void doBlockRotationProcess() {
            String [] blocks ={"1","2","3","4"};
            int block = JOptionPane.showOptionDialog(null, "Block",
                    "Choose number of block that you wants to rotate",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, blocks, blocks[0]);

            String[] directions = {"Clockwise", "Anticlockwise"};
            int direction = JOptionPane.showOptionDialog(null, "direction",
                    "Choose rotate direction",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, directions, directions[0]);
            direction = direction == 1 ? -2 : 2;
            manager.doBlockRotation(block+1 , direction);
            displayBoard();
        }

        /**
         * An inner class to handles actions that should be happen after clicking buttons according to button type
         */
        private class ButtonHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (setPlayerByIndexOfButton(findClickedButton(e))) {
                    GraphicalShow.this.revalidate();
                    doBlockRotationProcess();
                    GraphicalShow.this.revalidate();
                    Run.setFlag(3 - Run.getFlag()) ;

                }
            }
        }
    }


