

import javax.swing.JFrame;

/**
 * this class is responsible for creating the frame for which the game of tic tac toe will be played.
 */
public class TTTViewer {
    public static void main(String[] args) {
        //setting the size for the window
        int size = 500;
        JFrame appFrame = new JFrame();
        //creating the ttt board component
        BoardComponent boardLines = new BoardComponent(size);

        //the height has 10%more added on to allow easy view for the status message and reset button in
        //the BoardComponent class
        appFrame.setSize(size, size+((int)(size*.10)));
        appFrame.setResizable(false);
        appFrame.setTitle("Tic Tac Toe");
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //adding the board to the scene
        appFrame.add(boardLines);
        appFrame.setVisible(true);
    }
}