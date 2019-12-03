import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class TTTGui extends JPanel {

    //These are the X and Y coordinates for the mouse upon a mouse click but just keep in mind HOW frequently you check for updates
    public static int xMouse;
    public static int yMouse;
    public static int Clicked;

    //These are markers to indicate whether any particular block is occupied and hence not able to be occupied any more
    public static int S11; //First row First Column (Top Left)
    public static int S12; //First Row Second Column (Top Middle)
    public static int S13; //First Row Third Column (Top Right)
    public static int S21; //Middle Left
    public static int S22; //Middle (Center)
    public static int S23; //Middle Right
    public static int S31; //Bottom Left
    public static int S32; //Bottom Middle
    public static int S33; //Bottom Right

    //These two are what keep score of a current game. When p1/p2 = 15 then one player managed to win.
    public static int p1 = 0;
    public static int p2 = 0;
    //Keeps a running tab of wins accrued over a session
    public static int player1 = 0;
    public static int player2 = 0;
    public static int TotalGame = 0;

    //This one tests which player is trying to play. So False is Player 1 and True is Player 2
    public static Boolean state = false;
    //The purpose is so that you cannot continue to press mouse to accrue points after winning a game - Bug that was found
    public static Boolean Wcheck = true;

    //These stablish the GUI frame and the panel overlay to put buttons, lines, etc
    public static JFrame mainFrame;
    public static DrawPanel panel;

    //Developing a thread to run later on, mostly to repaint X and Os
    public MyThread Th1 = new MyThread();

    //These are our text boxes we can update and edit whenever a player wins
    public static JTextField T1; //Player 1 - Points
    public static JTextField T2; //Player 2 - Points
    public static JTextField T3; //Status of the game - Connected Etc

    public static void main(String args[]) {

        //The trigger for measuring clicks in our program
        Clicked = 0;

        // -- ESTABLISHING THE MAIN GAME BORDER --//
        panel = new DrawPanel();
        //Title of the window
        mainFrame = new JFrame("Tic Tac Toe");
        // -- ESTABLISHING THE MOUSE LISTENER TO USE IN THE GAME

        //Note since we are adding it to our mainFrame it will ONLY report clicks/drags/locations done on the main frame
        mainFrame.addMouseListener(new MouseAdapter() {
            int clicks = 0;
            //So we can inherit stuff from the drawpanel below, namely Graphics2D g2
            DrawPanel Draw = new DrawPanel();

            //When the mouse is clicked
            @Override
            public void mousePressed(MouseEvent e) {
                clicks++;
                Clicked++;

                //So we can call the thrhead outside of this void
                TTTGui GUI = new TTTGui();
                Thread TF = GUI.Th1;

                System.out.println("Clicks: " + clicks);

                //e.getX(); Finds the location of the mouse in the x plane relative to the upper left corner (0,0) of the frame
                int x = e.getX();
                int y = e.getY();

                //The purpose of this is to 'throw' our values to something outside of the void so we can retrieve it later
                xMouse = x;
                yMouse = y;

                // -- The Following is for Player 1
                //Make player 1 give S11 =1 and player 2 can make it 2 or something, so we can choose to mark it X or O
                if ( (xMouse > 49) && (xMouse < 210) && (yMouse > 99) && (yMouse < 250) && (S11 == 0) && ( state == false) )
                {
                    //This toggles the boolean. So if it's false it goes to true and if true it goes to false
                    state = !state;
                    //Set the flag for S11 being taken up by player 1
                    S11 = 1;
                    //Based on the magic square model
                    p1 = p1 + 8;
                }

                else if ( (xMouse > 211) && (xMouse < 390) && (yMouse > 99) && (yMouse < 250) && (S12 == 0) && ( state == false) )
                {
                    state = !state;
                    S12 = 1;
                    p1 = p1 + 1;
                }

                else if ( (xMouse > 391) && (xMouse < 551) && (yMouse > 99) && (yMouse < 250) && (S13 == 0) && ( state == false) )
                {
                    state = !state;
                    S13 = 1;
                    p1 = p1 + 6;
                }

                else if ( (xMouse > 49) && (xMouse < 210) && (yMouse > 251) && (yMouse < 450) && (S21 == 0) && ( state == false) )
                {
                    state = !state;
                    S21 = 1;
                    p1 = p1 + 3;
                }

                else if ( (xMouse > 211) && (xMouse < 390) && (yMouse > 251) && (yMouse < 450) && (S22 == 0) && ( state == false) )
                {
                    state = !state;
                    S22 = 1;
                    p1 = p1 + 5;
                }

                else if ( (xMouse > 391) && (xMouse < 551) && (yMouse > 251) && (yMouse < 450) && (S23 == 0) && ( state == false) )
                {
                    state = !state;
                    S23 = 1;
                    p1 = p1 + 7;
                }

                else if ( (xMouse > 49) && (xMouse < 210) && (yMouse > 450) && (yMouse < 601) && (S31 == 0) && ( state == false) )
                {
                    state = !state;
                    S31 = 1;
                    p1 = p1 + 4;
                }

                else if ( (xMouse > 211) && (xMouse < 390) && (yMouse > 450) && (yMouse < 601) && (S32 == 0) && ( state == false) )
                {
                    state = !state;
                    S32 = 1;
                    p1 = p1 + 9;
                }

                else if ( (xMouse > 391) && (xMouse < 551) && (yMouse > 450) && (yMouse < 601) && (S33 == 0) && ( state == false) )
                {
                    state = !state;
                    S33 = 1;
                    p1 = p1 + 2;
                }

                // -- THE FOLLOWING IS FOR PLAYER 2 -> IF DOING TCP THIS CAN BE REMOVED AND REPLACED WITH TCP SENDING 2!

                else if ( (xMouse > 49) && (xMouse < 210) && (yMouse > 99) && (yMouse < 250) && (S11 == 0) && ( state == true) )
                {
                    state = !state;
                    //This says that the top left box is occupied by player 2
                    S11 = 2;
                    p2 = p2 + 8;
                }

                else if ( (xMouse > 211) && (xMouse < 390) && (yMouse > 99) && (yMouse < 250) && (S12 == 0) && ( state == true) )
                {
                    state = !state;
                    S12 = 2;
                    p2 = p2 + 1;
                }

                else if ( (xMouse > 391) && (xMouse < 551) && (yMouse > 99) && (yMouse < 250) && (S13 == 0) && ( state == true) )
                {
                    state = !state;
                    S13 = 2;
                    p2 = p2 + 6;
                }

                else if ( (xMouse > 49) && (xMouse < 210) && (yMouse > 251) && (yMouse < 450) && (S21 == 0) && ( state == true) )
                {
                    state = !state;
                    S21 = 2;
                    p2 = p2 + 3;
                }

                else if ( (xMouse > 211) && (xMouse < 390) && (yMouse > 251) && (yMouse < 450) && (S22 == 0) && ( state == true) )
                {
                    state = !state;
                    S22 = 2;
                    p2 = p2 + 5;
                }

                else if ( (xMouse > 391) && (xMouse < 551) && (yMouse > 251) && (yMouse < 450) && (S23 == 0) && ( state == true) )
                {
                    state = !state;
                    S23 = 2;
                    p2 = p2 + 7;
                }

                else if ( (xMouse > 49) && (xMouse < 210) && (yMouse > 450) && (yMouse < 601) && (S31 == 0) && ( state == true) )
                {
                    state = !state;
                    S31 = 2;
                    p2 = p2 + 4;
                }

                else if ( (xMouse > 211) && (xMouse < 390) && (yMouse > 450) && (yMouse < 601) && (S32 == 0) && ( state == true) )
                {
                    state = !state;
                    S32 = 2;
                    p2 = p2 + 9;
                }

                else if ( (xMouse > 391) && (xMouse < 551) && (yMouse > 450) && (yMouse < 601) && (S33 == 0) && ( state == true) )
                {
                    //This toggles the boolean. So if it's false it goes to true and if true it goes to false
                    state = !state;
                    S33 = 2;
                    p2 = p2 + 2;
                }

                else
                    System.out.println("You are out of the game area, click inside a box  to play!");


                System.out.println("X Position: " + x + " Y position: " + y);

                //This is simply to test whether or not I am indeed changing the game elements on mouse click
                //Figure out a way to get the lines drawn and redone

                T3.setText("I CHANGED :)");

                //Run the thread
                TF.start();

                //This will redraw our panel so once we flag it as after clicking it checks the flags and places the X or O
                //panel.repaint();

            }
        });

        //This tells the frame what to do when you press the X
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //This contributes the panel to the mainframe
        mainFrame.add(panel);
        //THIS REMOVES DEFAULT LAYOUT SO YOU CAN POSITION BUTTONS BUT CAN LOOK WONKY ON SOME DISPLAYS
        panel.setLayout(null);
        //Setting dimensions on it - (X,Y)
        mainFrame.setSize(600,650);
        //Sets the original location for the frame
        mainFrame.setLocation(220, 150);
        //Setting the color of the background
        mainFrame.getContentPane().setBackground(Color.lightGray);
        //Setting the color of the foreground
        //mainFrame.GetContentPane().setForeground(Color.<INSERTCOLOR>);

        // -- ESTABLISHING CLICKABLE BUTTONS & TEXT FIELDS FOR THE USER GUI -- //
        //NOTE: In Java moving buttons around is not quite as easy as you'd expect
        //It's based on Layouts so it sort of 'snaps' into specific regions and you need to use some sort of absolute
        //Layout in order to specify very specific locations for buttons. It is possible to remove the layout and move it
        //To where you want it to be BUUUUT it can look wonky on different displays so use caution

        //Button 1
        JButton B1 = new JButton("PLAY VS PLAYER");
        //setBounds (Start X, Start Y, Length X, Length Y);
        B1.setBounds(30,10,200,30);
        panel.add(B1);

        //When B1 is clicked do Y
        B1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                //do whatever should happen when the button is clicked...
            }

        });

        //Button 2
        JButton B2 = new JButton("RESET GAME");
        B2.setBounds(370,10, 200, 30);
        panel.add(B2);

        // When B2 is clicked do X
        B2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                //Replace all states with 0 and start with a fresh slate
                state = false;
                Wcheck = true;
                p1 = 0;
                p2 = 0;
                S11 = 0;
                S12 = 0;
                S13 = 0;
                S21 = 0;
                S22 = 0;
                S23 = 0;
                S31 = 0;
                S32 = 0;
                S33 = 0;

                // -- THIS CLEARS THE PLACED UPON UI ELEMENTS
                mainFrame.getContentPane().repaint();
                String TotalWin = Integer.toString(TotalGame);
                T3.setText("Game:" + TotalWin);

                //Reset should NOT reset the running tab of wins
                //Need to figure out a way to 'clean' off Squares and Ovals along with restarting all the background stuff here


            }

        });

        //Text Field 1 -- Display Player 1 score &  JTextField(5) -> Create text thing that is 5 characters long
        T1 = new JTextField(20);
        T1.setBounds(100,40,110,30);
        T1.setText("Player 1 score");
        panel.add(T1);

        //Text Field 2 - Display Player 2 score
        T2 = new JTextField(20);
        T2.setBounds(400,40,110,30);
        T2.setText("Player 2 Score");
        panel.add(T2);

        //Text Field 3 - The purpose is to show the user what mode he's connected to and if he's currently playing or not
        T3 = new JTextField(20);
        T3.setBounds(235,10,130,30);
        //Update this depending on what state you are at so like "FINDING CONNECTION w/PLAYER" and "PLAYING VS AI" etc
        T3.setText("NOT CONNECTED");
        panel.add(T3);

        //"Go ahead and display the frame"
        mainFrame.setVisible(true);

    }

    public class MyThread extends Thread {

        public void run(){

            // -- ESTABLISHING NEW LINE DRAWING UPON MOUSE CLICK

            DrawPanel Draw = new DrawPanel();
            //Graphics2D Drawing = Draw.g2;

            //Grabbing available graphics from panel and putting our own X and Os to it
            Graphics g = panel.getGraphics();
            Graphics2D g2 = (Graphics2D) g;

            g2.setStroke(new BasicStroke(4));

            g2.setColor(Color.green);
            //Draw the circles we need -- X Coordinate, Y Coordinate, Vertical Radius, Horizontal Radius
            //g2.drawOval(240, 280, 125, 125); //Rough Center Coordinates

            // -- ESTABLISHING O's for Player 1! (Could also change it to X as well)

            //Note The difference between if and else if is the following

            //if - Else -- if (If condition for if is fufilled ignore all the following
            //if - if   -- Regardless of if the condition is filled, check all the other if statements

            if (( S11 == 1 ) && ( S11 != 0 ) && ( S11 != 2 ))
                g2.drawOval(70, 110, 125, 125); //Rough Center Coordinates

            if (( S12 == 1 ) && ( S12 != 0 ) && ( S12 != 2 ))
                g2.drawOval(230, 110, 125, 125);

            if (( S13 == 1 ) && ( S13 != 0 ) && ( S13 != 2 ))
                g2.drawOval(410, 110, 125, 125);

            if (( S21 == 1 ) && ( S21 != 0 ) && ( S21 != 2 ))
                g2.drawOval(70, 280, 125, 125);

            if (( S22 == 1 ) && ( S22 != 0 ) && ( S22 != 2 ))
                g2.drawOval(230, 280, 125, 125); //Center O

            if (( S23 == 1 ) && ( S23 != 0 ) && ( S23 != 2 ))
                g2.drawOval(410, 280, 125, 125);

            if (( S31 == 1 ) && ( S31 != 0 ) && ( S31 != 2 ))
                g2.drawOval(70, 460, 125, 125);

            if (( S32 == 1 ) && ( S32 != 0 ) && ( S32 != 2 ))
                g2.drawOval(230, 460, 125, 125);

            if (( S33 == 1 ) && ( S33 != 0 ) && ( S33 != 2 ))
                g2.drawOval(410, 460, 125, 125);

            //Changing the color to RED
            g2.setColor(Color.red);

            //X (Well in this case Rectangles) for our second player
            //Basically IF Player 2 clicks and the slot ISNT empty and Player 1 didn't already fill it then fill the slot in
            if (( S11 == 2 ) && ( S11 != 0 ) && ( S11 != 1 ))
                g2.drawRect(70, 110, 125, 125);

            if (( S12 == 2 ) && ( S12 != 0 ) && ( S12 != 1 ))
                g2.drawRect(230, 110, 125, 125);

            if (( S13 == 2 ) && ( S13 != 0 ) && ( S13 != 1 ))
                g2.drawRect(410, 110, 125, 125);

            if (( S21 == 2 ) && ( S21 != 0 ) && ( S21 != 1 ))
                g2.drawRect(70, 280, 125, 125);

            if (( S22 == 2 ) && ( S22 != 0 ) && ( S22 != 1 ))
                g2.drawRect(230, 280, 125, 125);

            if (( S23 == 2 ) && ( S23 != 0 ) && ( S23 != 1 ))
                g2.drawRect(410, 280, 125, 125);

            if (( S31 == 2 ) && ( S31 != 0 ) && ( S31 != 1 ))
                g2.drawRect(70, 460, 125, 125);

            if (( S32 == 2 ) && ( S32 != 0 ) && ( S32 != 1 ))
                g2.drawRect(230, 460, 125, 125);

            if (( S33 == 2 ) && ( S33 != 0 ) && ( S33 != 1 ))
                g2.drawRect(410, 460, 125, 125);

            // -- ESTABLISHING SCORE TRACKING AND WIN TABBING

            //Hard coded 8 possible win scenarios for Player 1 - WE DID IT :)
            if ((S11 == 1 && S12 == 1 && S13 == 1 ) || (S21 == 1 && S22 == 1 && S23 == 1) || (S31 == 1 && S32 == 1 && S33 == 1)
                    || (S11 == 1 && S21 == 1 && S31 == 1) || (S12 == 1 && S22 == 1 && S32 == 1)
                    || (S13 == 1 && S23 == 1 && S33 == 1) || (S11 == 1 && S22 == 1 && S33 == 1)
                    || (S13 == 1 && S22 == 1 && S31 == 1)) {

                if (Wcheck == true) {
                    System.out.println("Player 1 Wins!");
                    //So if Player 1 wins increment player1, convert it to string and keep a running log of it
                    player1++;
                    TotalGame++;
                    String Player1 = Integer.toString(player1);
                    T1.setText(Player1);
                    T3.setText("Player 1 Wins!");
                    //What I noticed would happen is once you won a game, if a user kept pressing the mouse button you could keep getting points
                    //Which is obviously not what I want to happen so by having a flag check like this it wouldn't keep letting you get points unless you RESET
                    //Also has the bonus benefit of keeping two players from 'winning' a game
                    Wcheck = false;
                }
            }
            //Player 2 wins
            if ((S11 == 2 && S12 == 2 && S13 == 2 ) || (S21 == 2 && S22 == 2 && S23 == 2) || (S31 == 2 && S32 == 2 && S33 == 2)
                    || (S11 == 2 && S21 == 2 && S31 == 2) || (S12 == 2 && S22 == 2 && S32 == 2)
                    || (S13 == 2 && S23 == 2 && S33 == 2) || (S11 == 2 && S22 == 2 && S33 == 2)
                    || (S13 == 2 && S22 == 2 && S31 == 2)) {

                if (Wcheck == true) {
                    System.out.println("Player 2 Wins!");
                    player2++;
                    //Increments the amount of games if you win
                    TotalGame++;
                    String Player2 = Integer.toString(player2);
                    T2.setText(Player2);
                    T3.setText("Player 2 Wins!");
                    Wcheck = false;
                }
            }

            //Cats game
            if ( ((S11 == 1) || (S11 ==2)) && ((S12 == 1) || (S12 == 2)) && ((S13 == 1) || (S13 == 2)) && ((S21 == 1) || (S21 == 2))
                    && ((S22 == 1) || (S22 == 2)) && ((S23 == 1) || (S23 == 2)) && ((S31 == 1) || (S31 == 2)) && ((S32 == 1) || (S32 == 2))
                    && ((S32 == 1) || (S32 == 2))){
                System.out.println("Cats game!");
                T3.setText("Cats Game!");
                TotalGame++;
            }

            //This is supposed to redraw the panel but I don't think it's really doing anything
            repaint();

        }
    }


}

// -- ESTABLISHING LINE BORDERS FOR THE GAME (GUI and NOT ACTUAL GAME HIT BOX --//

class DrawPanel extends JPanel{

    private static final long serialVersionUID = 1L;
    //This enables us to draw in the X and O in other places
    public static Graphics2D g2;

    //These two lines draw in the Clicked from the first class down to here so we can tell our paint component when and when to repaint
    TTTGui GUI = new TTTGui();
    int Clicked = GUI.Clicked;

    @Override
    public void paintComponent(Graphics g){
        //Line 1
        super.paintComponent(g);
        g2 = (Graphics2D) g;
        //This lets you set the thickness of the line
        g2.setStroke(new BasicStroke(4));
        //Set the color of the lines
        g2.setColor(Color.black);
        // -- LINES (X Pos start, Y Pos start, X Pos End, Y Pos End) --//
        //Horizontal Lines
        g2.drawLine(50, 250, 550, 250);
        g2.drawLine(50, 450, 550, 450);
        //Vertical Lines
        g2.drawLine(210, 100, 210, 600);
        g2.drawLine(390, 100, 390, 600);

        //Defines the game borders - That is puts a ring around it
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(2));

        //Horizontal Lines
        g2.drawLine(50,99,550,99);
        g2.drawLine(50,601,550,601);
        //Vertical Lines
        g2.drawLine(49, 100, 49, 600);
        g2.drawLine(551, 100, 551, 600);
    }

}
