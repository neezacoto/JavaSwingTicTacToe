import java.util.ArrayList;
import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;


/**
 * This class is responsible for creating the tic tac toe board and all it's behaviours. That means, the cells,
 * the status for each player, and the reset button, once the game is over.
 */
public class BoardComponent extends JComponent implements ActionListener{
    //creates an array for the buttons
    private ArrayList<JButton> buttons;
    //true is X and false is Zero
    boolean isXorO;
    //this will keep track of the turns and will be used to determine a draw
    private int turns;
    //size of the container to help determine how components should be sized
    private final int size;
    //lets the players know the status of the game
    JLabel status;
    //allows for a reset when the game is over
    JButton reset;


    /**
     * constructor that creates the board and all it's contents
     * @param size the size of the window
     */
    public BoardComponent(int size)
    {
        isXorO = true;
        turns = 1;
        setLayout(new GridBagLayout());
        GridBagConstraints buttonsConst = new GridBagConstraints();

        //fills an array with buttons
        buttons = new ArrayList<>();
        for(int i = 0; i<9;i++)
        {
            buttons.add(new JButton(" "));
            buttons.get(i).addActionListener(this);
            buttons.get(i).setSize(100,(int)(size*.25));
        }
        this.size = size;

        //anchor the grid to the top of the window so there will be space at the bottom for dialogue
        buttonsConst.anchor = GridBagConstraints.PAGE_START;
        //keeps track of the buttons added
        int count = 0;
        //rows
        for(int y = 0; y<3;y++)
        {
            //columns
            for(int x = 0; x<3; x++)
            {
                //changing the length of the cells
                buttonsConst.ipady = (int)(size *.25);
                buttonsConst.ipadx = (int)(size *.20);
                //filling the buttons into their respective places
                buttonsConst.gridx =x;
                buttonsConst.gridy =y;
                //adding a little padding
                buttonsConst.insets= new Insets(5,5,5,5);
                add(buttons.get(count),buttonsConst);

                count++;
            }
        }
        //resetting the padding and sizing for the status message and jbutton
        buttonsConst.insets= new Insets(0,0,0,0);
        buttonsConst.ipady = 0;
        buttonsConst.ipadx = 0;

        //adds status message to the frame
        status = new JLabel("X's Turn to Play");
        buttonsConst.gridy = 4;
        buttonsConst.gridx= 0;
        buttonsConst.gridwidth = 3;
        add(status,buttonsConst);

        //adds reset button for after the game
        reset = new JButton("Reset");
        reset.addActionListener(this);
        reset.setVisible(false);
        buttonsConst.gridwidth = 0;
        buttonsConst.gridy = 5;
        buttonsConst.gridx= 0;
        add(reset,buttonsConst);

    }

    /**
     * default constructor that creates the board and all it's contents
     */
    public BoardComponent()
    {
        size = 500;
        isXorO = true;
        turns = 1;
        setLayout(new GridBagLayout());
        GridBagConstraints buttonsConst = new GridBagConstraints();

        //fills an array with buttons
        buttons = new ArrayList<>();
        for(int i = 0; i<9;i++)
        {
            buttons.add(new JButton(" "));
            buttons.get(i).addActionListener(this);
            buttons.get(i).setSize(100,(int)(size*.25));
        }

        //anchor the grid to the top of the window so there will be space at the bottom for dialogue
        buttonsConst.anchor = GridBagConstraints.PAGE_START;
        //keeps track of the buttons added
        int count = 0;
        //rows
        for(int y = 0; y<3;y++)
        {
            //columns
            for(int x = 0; x<3; x++)
            {
                //changing the length of the cells
                buttonsConst.ipady = (int)(size *.25);
                buttonsConst.ipadx = (int)(size *.20);
                //filling the buttons into their respective places
                buttonsConst.gridx =x;
                buttonsConst.gridy =y;
                //adding a little padding
                buttonsConst.insets= new Insets(5,5,5,5);
                add(buttons.get(count),buttonsConst);

                count++;
            }
        }
        //resetting the padding and sizing for the status message and jbutton
        buttonsConst.insets= new Insets(0,0,0,0);
        buttonsConst.ipady = 0;
        buttonsConst.ipadx = 0;

        //adds status message to the frame
        status = new JLabel("X's Turn to Play");
        buttonsConst.gridy = 4;
        buttonsConst.gridx= 0;
        buttonsConst.gridwidth = 3;
        add(status,buttonsConst);

        //adds reset button for after the game
        reset = new JButton("Reset");
        reset.addActionListener(this);
        reset.setVisible(false);
        buttonsConst.gridwidth = 0;
        buttonsConst.gridy = 5;
        buttonsConst.gridx= 0;
        add(reset,buttonsConst);
    }

    /**
     * checks to see if anyone has won the game. Called from the actionPreformed() method.
     */
    public boolean checkWin(){
        //I did not want to write a bunch of if statements so I came up with another method for checking.
        ArrayList<String> cells = new ArrayList<>();

        for(JButton button: buttons) {
            cells.add(button.getText());
        }
        //converts vertical columns into horizontal rows
        ArrayList<String> cellsV = new ArrayList<>();
        //goes across the first row adding the respecting columns
        for(int i = 0; i<3;i++) {
            cellsV.add(cells.get(i));
            //by adding three the middle cell is obtained
            cellsV.add(cells.get(i+3));
            //by adding six the bottom cell is obtained
            cellsV.add(cells.get(i+6));
        }
        //horizontal check
        if(queueRows(cells))
            return true;
        //vertical check
        if(queueRows(cellsV))
            return true;
        //diagonals
        String diag1 = cells.get(0)+cells.get(4)+cells.get(8);
        String diag2 = cells.get(2)+cells.get(4)+cells.get(6);
        //checks diagonals
        if(diag1.equals("XXX")|| diag1.equals("OOO"))
            return true;
        return diag2.equals("XXX") || diag2.equals("OOO");
    }

    /**
     * helper of checkWin()
     * @param cells the rows that are to be checked for a win condition
     * @return if there is a win or not
     */
    private boolean queueRows(ArrayList<String> cells)
    {
        //holds a queue for cells to be checked
        String[] queue = new String[3];
        int pos_queue=0;

        //checks for horizontal matches
        for(int i = 0; i<9;i++)
        {
            queue[pos_queue] = cells.get(i);
            if((pos_queue+1)%3 ==0)
            {
                String str = queue[0] + queue[1] + queue[2];
                if(str.equals("XXX")|| str.equals("OOO"))
                {
                    return true;
                }
                pos_queue=0;
            }else{
                pos_queue++;
            }

        }
        return false;
    }

    /**
     * when a button is pressed this function will check if the reset button was pressed or one of the tttcells. If a
     * tttcell was chosen it will check to see if there is a win condition or a draw, if not, the game will continue.
     * @param e the action event that will come from a button pressed, since that is the only actions that are
     *          linked
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //checks to see if the button should be X or O
        String toSet = (isXorO) ? "X" : "O";
        //resets the round for another game
        if(e.getSource() == reset)
        {
            //resets all the buttons
            for(JButton button : buttons)
            {
                button.setText(" ");
                button.setEnabled(true);
            }
            status.setText(toSet+"' Turn");
            reset.setVisible(false);
            turns = 1;
        }
        else {
            for(JButton button : buttons)
            {
                if(e.getSource() == button)
                {

                    //sets text of the button
                    button.setText(toSet);

                    //disable the button because it's already been used
                    button.setEnabled(false);
                    if(checkWin())
                    {
                        for(JButton tbdisabled : buttons) {
                            tbdisabled.setEnabled(false);
                        }
                        status.setText(toSet+" Has Won!");
                        //swap turns
                        isXorO = !isXorO;
                        reset.setVisible(true);
                    }else if(turns ==9){
                        status.setText("Draw.");
                        reset.setVisible(true);
                    }else {
                        //changes isXorO to the opposite boolean for the next player to use
                        isXorO = !isXorO;
                        //sets up for next round
                        toSet = (isXorO) ? "X" : "O";
                        status.setText(toSet+"'s Turn");


                    }
                    //add to the counter to check for a draw later
                    turns++;
                }
            }

        }

    }
}

//    I wanted to paint the board but I didn't end up getting it to work the way I wanted it to
//    @Override
//    public void paintComponent(Graphics g) {
//        //where to paint the middle lines going horizontally
//        int middleH = (int)(size *.04);
//        //where to paint the middle lines going vertically
//        int middleV = (int)(size *.03);
//
//        int length = (int)(size * .90);
//        int thickness = (int)(size*.02);
//
//        //positions (1-2: left-right: top-bottom)
//        int posH1 = (int)(size*.30);
//        int posH2 = (int)(size*.63);
//
//        int posV1 = (int)(size*.31);
//        int posV2 = (int)(size*.63);
//
//        // Cast to Graphics2D
//        Graphics2D boardObj = (Graphics2D) g;
//
//        //Horizontal top bar
//        Rectangle line1 = new Rectangle(middleH, posH1, length, thickness);
//        Color lineColor1 = new Color(0, 0, 0);
//        boardObj.setColor(lineColor1);
//        boardObj.fill(line1);
//        //Horizontal bottom bar
//        Rectangle line2 = new Rectangle(middleH, posH2, length, thickness);
//        Color lineColor2 = new Color(0, 0, 0);
//        boardObj.setColor(lineColor2);
//        boardObj.fill(line2);
//
//        //Horizontal left bar
//        Rectangle line3 = new Rectangle(posV1, middleV, thickness, length);
//        Color lineColor3 = new Color(0, 0, 0);
//        boardObj.setColor(lineColor3);
//        boardObj.fill(line3);
//        //Horizontal right bar
//        Rectangle line4 = new Rectangle(posV2, middleV, thickness, length);
//        Color lineColor4 = new Color(0, 0, 0);
//        boardObj.setColor(lineColor4);
//        boardObj.fill(line4);
//    }