//import java.awt.*;
import java.util.*;
        import java.util.Scanner;

        import javafx.scene.control.*;


//for boolean player , false->AI ; true->User

//there are no structs for Java, so we must make new classes, however we don't need new files for them

public class Controller {

    boolean isEnd = false; //game begins in the playing state
    boolean isTurn = true; //it is always the user's turn at the beginning of the game
    //boolean isAI = false;
    //boolean doubleAI = false;
    boolean isTimeUp = true;
    boolean finishedPieMove = false;
    long timeAllowed;
    int turnCount = 1;
    int numHousesINPUT = 6; //default number of houses is 6;
    int numSeedsINPUT = 4; //default number of seeds/house is 4

    ArrayList<House> player = new ArrayList<House>();
    ArrayList<House> computer = new ArrayList<House>();
    ArrayList<Jar> jars = new ArrayList<Jar>();
    private int Player;


    public Controller(){ //constructs the board and its components
        for(int i = 0; i < numHousesINPUT; i++) {
            player.add(new House(numSeedsINPUT, true));
            computer.add(new House(numSeedsINPUT, false));

        }
        jars.add(new Jar(0,true));
        jars.add(new Jar(0,false));
    }

    public void updateArrayLists(int numHouses, int numMarbles) {
        int oldSize = player.size();
        for( int i = 0; i < numHouses; i++){
            if ( i < oldSize) {
                player.set(i, new House(numMarbles, true));
                computer.set(i, new House(numMarbles, false));
            }
            else {
                player.add(new House(numMarbles,true));
                computer.add(new House(numMarbles,false));
            }

        }
    }


    public  void getUserInputs(){
        Scanner keyboard = new Scanner(System.in);
        do {
            System.out.print("Enter the number of houses (between 4 and 9): ");
            numHousesINPUT = keyboard.nextInt();
        }while(numHousesINPUT < 4 || numHousesINPUT > 9);
        do {
            System.out.print("Enter the number of seeds per house (between 1 and 10): ");
            numSeedsINPUT = keyboard.nextInt();
        }while( numSeedsINPUT <  1 || numSeedsINPUT > 10);
    } //end user inputs


    /******************** RANDOM DISTRIBUTION ******************************/
    public void assignRandomMarbles(){
        String wantRandom = "";
        System.out.print("Random Distribution (Y/N): ");
        Scanner keyboard = new Scanner(System.in);
        wantRandom  = keyboard.nextLine();

        if(wantRandom.equals("y")  || wantRandom.equals("Y")) {
            Random rand = new Random();
            int temp;
            int sum = 0;
            int prod = (numHousesINPUT * numSeedsINPUT); //total number of marbles necessary

            for (int i = 1; i <= numHousesINPUT; i++) {
                House currPlayerHouse = player.get(i - 1);
                House currCompHouse = computer.get(numHousesINPUT - (i));
                if (!(i == numHousesINPUT)) {
                    temp = rand.nextInt((prod - sum) / (numHousesINPUT - i)) + 1;
                    currPlayerHouse.numMarbles = temp;
                    currCompHouse.numMarbles = temp;

                    sum += temp;
                } else {
                    int last = (prod - sum);
                    currPlayerHouse.numMarbles = last;
                    currCompHouse.numMarbles = last;
                    sum += last;
                }
            }
        }
    }//end random marbles

    //checks different aspects of the board and determines if there are any messages to be displayed or changes to be displayed
    public void getBoardStatus(){ //print board
        System.out.println("*********************************************************");
        System.out.print("Board Status: \n\t\t\t\t");
        for (int i = 0; i < numHousesINPUT; i++){
            House compHouse = computer.get(numHousesINPUT-i-1);
            System.out.print((numHousesINPUT - i)+houseAttributes(compHouse) + " ");//reverse order
        }

        Jar playerJar = jars.get(0);
        System.out.println(jarAttributes(playerJar));

        Jar computerJar = jars.get(1);
        System.out.print(jarAttributes(computerJar) + " ");

        for (int i = 0; i < numHousesINPUT; i++){
            House playerHouse = player.get(i);
            System.out.print((i+1)+houseAttributes(playerHouse) + " ");
        }

        System.out.print("\nSide count: " + getSideCount(true) + " " + getSideCount(false));
        System.out.println("\n*********************************************************");

        updateIsEnd();
    }//end board status

    /*******************Jar/House Functions ****************************/
    private String houseAttributes(House h) {
        String values = Integer.toString((h.numMarbles));
        String play  = Boolean.toString(h.player);
        return "[" + values + ","+ play + "]";
    }

    private String jarAttributes(Jar h) {
        String values = Integer.toString((h.numMarbles));
        String play  = Boolean.toString((h.player));
        return "[JAR: " + values + ","+ play + "]";
    }

    public void updateIsEnd(){
        if(getSideCount(true) == 0 || getSideCount(false) == 0){
            isEnd = true;
            //function to determine who wins and who lost
            //set a boolean to change it on the screen
        }
        System.out.println("\n*********************************************************");
    }//end board status


    public void updateButtonLabels(){
        for( int i = 0; i < numHousesINPUT; i++){
            System.out.println("i: update button label "+ i);
            House currHouse = (player).get(i);
            Button currButton = currHouse.houseButton;
            currButton.setText(Integer.toString(currHouse.numMarbles));

            currHouse = (computer).get(i);
            currButton = currHouse.houseButton;
            currButton.setText(Integer.toString(currHouse.numMarbles));
            System.out.println("end for update button label");
        }

        for( int j = 0; j < 2; j++){
            Jar currJar = jars.get(j);
            Button currButton = currJar.jarButton;
            currButton.setText(Integer.toString(currJar.numMarbles));
        }
    }


    public void setTimeLimit(){
        System.out.println("\nHow long would you like the timer to be? (sec): ");
        Scanner keyboard = new Scanner(System.in);
        long reqTime  = keyboard.nextLong();
        timeAllowed = reqTime*1000;

    }

    public int getSideCount(boolean b) {
        int total = 0;
        if(b){
            for(int i = 0; i < numHousesINPUT; i++){
                total+= getHouseCount(player.get(i));
            }
        }else{
            for(int i = 0; i < numHousesINPUT; i++){
                total+= getHouseCount(computer.get(i));
            }
        }
        return total;
    }



    /*
     * Imported timer function
     * */

    public class TimedScanner implements Runnable
    {
        private Scanner scanner;
        //private boolean AImoveMade;//true for move decided, false for still deciding
        private StringBuilder buffer;
        private boolean reading;
        private Thread t;

        public TimedScanner()
        {
            //AImoveMade = false;
            scanner = new Scanner(System.in);
            buffer = new StringBuilder();
            reading = false;
            t = new Thread(this);
            t.setDaemon(true);
            t.start();
        }

        public String nextLine(long time)
        {
            reading = true;
            String result = null;
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < time && result == null)
            {
                try
                {
                    Thread.sleep(30);
                }
                catch (InterruptedException e)
                {
                }
                synchronized (buffer)
                {
                    if (buffer.length() > 0)
                    {
                        Scanner temp = new Scanner(buffer.toString());
                        result = temp.nextLine();
                    }
                }
            }
            reading = false;
            return result;
        }

        @Override
        public void run()
        {
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                synchronized (buffer)
                {
                    if (reading)
                    {

                        buffer.append(line);
                        buffer.append("\n");

                    }
                    else
                    {
                        // flush the buffer
                        if (buffer.length() != 0)
                        {
                            buffer.delete(0, buffer.length());
                        }
                    }
                }
            }
        }
    }

    public void moveMarbles(ArrayList<House> playerList, ArrayList<House> computerList, ArrayList<Jar> jarList, boolean b, int Player) {

        updateIsEnd();
        if (!isEnd) {


            getBoardStatus();
            int userInput;

            System.out.println("\n Turn number: " + turnCount);
            int countNumber = turnCount;
            ArrayList<House> tempPlayerList = computerList;
            ArrayList<House> tempComputerList = playerList;
            if(countNumber == 2 && finishedPieMove == false){
                getBoardStatus();
                Scanner keyboard = new Scanner(System.in);
                String pieInput;
                do {
                    System.out.println("\nWould Player 2 like to use Pie Move? Y/N: ");
                    pieInput = keyboard.nextLine();
                    System.out.println("Move received: ."+ pieInput+".");
                    if (pieInput.equals("Y")|| pieInput.equals("y")) {
                        System.out.println("\n Pie move accepted");


                        System.out.println("\nStarting Pie Move...");
                        ArrayList<House> tempHouseArray = playerList;
                        tempPlayerList = playerList;
                        tempComputerList = computerList;

                        player = tempPlayerList;
                        computer = tempComputerList;

                        Jar tempJar = jarList.get(0);
                        jarList.set(0, jarList.get(1));
                        jarList.set(1, tempJar);
                        jarList.get(0).player = true;
                        jarList.get(1).player = false;
                        isTurn = true;

                        getBoardStatus();
                        //player 1 goes again, but with houses/jars switched
                        finishedPieMove = true;
                        System.out.println("Pie move finished, updating board status");

                        moveMarbles(tempPlayerList, tempComputerList, jarList, true, -1);

                        getBoardStatus();
                    } else if (pieInput.equals("N") || pieInput.equals("n")){
                        System.out.println("Turned down pie move");
                    }
                    else{
                        System.out.println("Don't know what was received");
                    }
                }while(pieInput.equals("N") && pieInput.equals("n") && pieInput.equals("Y") && pieInput.equals("y"));
                computerList = tempComputerList;
                playerList = tempPlayerList;
            }

            if (Player == -1) {
                System.out.println("\nPLAYER 1: ");
            } else if (Player == -2) {
                System.out.println("\nPLAYER 2: ");
            } else {
                System.out.println("\n don't know which player is going...");
            }

            //Timed move
            Scanner keyboard = new Scanner(System.in);
                    TimedScanner scanner = new TimedScanner();
                    String tempUserInput = scanner.nextLine(timeAllowed);
                    if (tempUserInput == null) {
                        System.out.println("\nTime's up, random move will be made for you: ");
                        isTimeUp = true;
                        userInput = 1 + (int) (Math.random() * numHousesINPUT);
                        System.out.println(userInput);
                        if (isValidMove(playerList.get(userInput)) == false) {
                            if(Player == -1) {
                                System.out.println("\nAutomatic move was invalid, Player 1 loses");
                            }
                            else{
                                System.out.println("\nAutomatic move was invalid, Player 2 loses");
                            }
                            isEnd = true;
                        }else{
                            isTimeUp = false;
                        }
                    } else {
                        System.out.println("\nMove made within time limit...");
                        isTimeUp = false;
                        userInput = Integer.parseInt(tempUserInput);
                    }

            userInput = keyboard.nextInt();
            //House selectedHouse = computerList.get(userInput - 1);
            if (isValidMove(playerList.get(userInput - 1))) {
                House selectedHouse = playerList.get(userInput - 1); //index differs by 1
                if (isValidMove(selectedHouse)) {

                    /*get number of marbles in selected house*/
                    int numMarblesToMove = selectedHouse.numMarbles;
                    selectedHouse.numMarbles = 0;

                    int houseNumber = -1;

                    if(numMarblesToMove <= (numHousesINPUT-userInput)) {
                        for (int i = userInput; i < numHousesINPUT; i++) {
                            House currHouse = playerList.get(i);
                            if (numMarblesToMove > 0) {
                                currHouse.numMarbles++;
                                numMarblesToMove--;
                                houseNumber = i;
                            }
                        }
                        House currHouse = playerList.get(houseNumber);
                        House oppositeHouse = computerList.get(numHousesINPUT - houseNumber-1);
                        if (numMarblesToMove == 0) {
                            //landed in empty house and opposite house contains marbles
                            //capture the opposite house
                            if (currHouse.numMarbles == 1 && oppositeHouse.numMarbles >= 0) {
                                Jar playerJar;
                                if (Player == -2) {
                                    playerJar = jars.get(1);
                                } else {
                                    playerJar = jars.get(0);
                                }
                                playerJar.numMarbles = playerJar.numMarbles + oppositeHouse.numMarbles + 1;
                                oppositeHouse.numMarbles = 0;
                                System.out.print(("\nLanded in house"+ houseNumber));
                                System.out.println("\nCaptured Marbles in House " + (numHousesINPUT-houseNumber) +
                                        ". Marbles remaining in house: "+ computerList.get(numHousesINPUT-houseNumber-1).numMarbles);
                                getBoardStatus();
                                currHouse.numMarbles = 0;
                                computerList.get(numHousesINPUT - houseNumber).numMarbles = oppositeHouse.numMarbles;

                            }
                        }
                    }
                    else {
                        while (numMarblesToMove > 0) {
                            if (userInput != numHousesINPUT) { //if first index is chosen can move to jar
                                for (int i = userInput; i < numHousesINPUT; i++) {
                                    House currHouse = playerList.get(i);
                                    House oppositeHouse;
                                    if (numMarblesToMove > 0) {
                                        currHouse.numMarbles++;
                                        numMarblesToMove--;
                                        houseNumber = i;

                                    }
                                    oppositeHouse = computerList.get(numHousesINPUT - houseNumber -1);
                                    if (numMarblesToMove == 0) {
                                        //landed in empty house and opposite house contains marbles
                                        //capture the opposite house
                                        if (playerList.get(houseNumber).numMarbles == 1 && oppositeHouse.numMarbles > 0) {
                                            Jar playerJar;
                                            if (Player == -2) {
                                                playerJar = jars.get(1);
                                            } else {
                                                playerJar = jars.get(0);
                                            }
                                            playerJar.numMarbles = playerJar.numMarbles + oppositeHouse.numMarbles + 1;
                                            //computerList.get(numHousesINPUT-houseNumber).numMarbles = 0;

                                            oppositeHouse.numMarbles = 0;
                                            currHouse.numMarbles = 0;
                                            System.out.print(("\nLanded in house"+ houseNumber));
                                            System.out.println("\nCaptured Marbles in House " + (numHousesINPUT-houseNumber) +
                                                    ". Marbles remaining in house: "+ computerList.get(numHousesINPUT-houseNumber-1).numMarbles);

                                        }
                                    }
                                }
                            }
                            //own jar
                            if (numMarblesToMove > 0) {
                                if ((playerList.get(userInput - 1).numMarbles != 1) ||
                                        (playerList.get(userInput - 1) == playerList.get(numHousesINPUT - 1))) {
                                    Jar playerJar;
                                    if (Player == -1) {
                                        playerJar = jarList.get(0);
                                    } else {
                                        playerJar = jarList.get(1);
                                    }
                                    playerJar.numMarbles++;
                                    numMarblesToMove--;
                                } else {
                                    playerList.get(userInput).numMarbles++;
                                }
                            }
                            if (numMarblesToMove == 0) { //user landed in own jar, gets free play
                                if (!isEnd) {
                                    if (Player == -1) {
                                        System.out.println("\nStaying on Player 1...");
                                        moveMarbles(playerList, computerList, jarList, true, -1);
                                    } else {//it's the player with the bottom houses
                                        System.out.println("\nStaying on Player 2...");

                                        moveMarbles(playerList, computerList, jarList, true, -2);
                                        //playelist and computerlist have already switched places when first called
                                    }
                                }

                            }
                            //dropping marbles on opposite side
                            for (int i = 0; i < numHousesINPUT; i++) {
                                House currHouse = computerList.get(i);
                                if (numMarblesToMove > 0) {
                                    currHouse.numMarbles++;
                                    numMarblesToMove--;
                                }
                            }
                            userInput = 0; //move index back to first house of player to keep moving marbles correctly
                        } //end while numMarbles > 0
                    }
                }

                getBoardStatus();

                turnCount++;
                System.out.println("\n isTurn: " + isTurn);
                //switch players
                if(Player == -2) {
                    isTurn = true; // this will set the state message to say "The computer is playing". At the end of our AI turn function, we will return the value back to true.

                    System.out.println("\nGoing to Player 1...");
                    moveMarbles(computerList, playerList, jarList, true, -1);//player 1
                }else{
                    isTurn = false; // this will set the state message to say "The computer is playing". At the end of our AI turn function, we will return the value back to true.
                    System.out.print(("\nGoing to Player 2..."));
                    moveMarbles(computerList,playerList,jarList, true, -2);//player 2
                }
            }
            else {
                if(isTimeUp == false) {
                    System.out.println("\nError: Invalid entry\nPlease select a non-empty house by entering values 1-" + numHousesINPUT + "\n\n");
                    if (Player == -1) {
                        isTurn = true; //stay on player 1 turn
                        moveMarbles(playerList, computerList, jarList, true, -1);//-1 means human player 1
                    } else if (Player == -2) {
                        isTurn = false; //stay on player 2 turn
                        moveMarbles(playerList, computerList, jarList, true, -2);//-1 means human player 1
                    } else {
                        System.out.println("\nGoing to AI....");
                    }
                }
                System.out.println("\n");

            }
        }
    }

        /*else {
            System.out.println("END GAME, NO MARBLES ON ONE SIDE p2");
        }*/

    //end move marbles both
    /******************** Valid Move ****************************/
    public boolean isValidMove(House h){
        String findHouseSide = "none";
        boolean check = false;
        //check if house is on the player or computer's side
        for (int i = 0; i < numHousesINPUT; i++) {
            if (player.get(i) == h) {
                findHouseSide = "player";

                System.out.println("\nplayer's turn, picked house "+(i+1)+", isTurn is "+isTurn);

            }
            if (computer.get(i) == h) {
                findHouseSide = "computer";
                System.out.println("\ncomputer's turn, picked house "+(i+1)+", isTurn is "+isTurn);
            }
        }
        System.out.println("\nisTurn = " + isTurn +" \nfindHouseSide" +findHouseSide);
        if ((isTurn && findHouseSide.equals("player")) || (!isTurn && findHouseSide.equals("computer"))) {
            if (findHouseSide.equals("player")) {
                if (getHouseCount(h) > 0) {
                    check = true;
                }
            }
            if (findHouseSide == "computer") {
                if (getHouseCount(h) > 0) {
                    check = true;
                }
            }
        }
        System.out.println("\nMarble count: " + getHouseCount(h)+ " check is "+ check);

        return check;
    }//end is valid move


    /****************** PLAY GAME * @return*******************/

    public void twoPlayerGame() {
       moveMarblesPlayer1(player, computer);
        if (isEnd) {
            System.out.println("END GAME, NO MARBLES ON ONE SIDE ");
            if (getSideCount(true) > 0) {
                for (int i = 0; i < numHousesINPUT; i++) {
                    House currHouse = player.get(i);
                    Jar playerJar = jars.get(0);
                    playerJar.numMarbles = playerJar.numMarbles + currHouse.numMarbles;
                    currHouse.numMarbles = 0;
                }
            }
            if (getSideCount(false) > 0) {
                for (int i = 0; i < numHousesINPUT; i++) {
                    House currHouse = computer.get(i);
                    Jar computerJar = jars.get(1);
                    computerJar.numMarbles = computerJar.numMarbles + currHouse.numMarbles;
                    currHouse.numMarbles = 0;
                }
            }
            moveMarbles(player, computer, jars, true, -1);
            if (isEnd) {
                System.out.println("END GAME, NO MARBLES ON ONE SIDE ");
                if (getSideCount(true) > 0) {
                    for (int i = 0; i < numHousesINPUT; i++) {
                        House currHouse = player.get(i);
                        Jar playerJar = jars.get(0);
                        playerJar.numMarbles = playerJar.numMarbles + currHouse.numMarbles;
                        currHouse.numMarbles = 0;
                    }
                }
                if (getSideCount(false) > 0) {
                    for (int i = 0; i < numHousesINPUT; i++) {
                        House currHouse = computer.get(i);
                        Jar computerJar = jars.get(1);
                        computerJar.numMarbles = computerJar.numMarbles + currHouse.numMarbles;
                        currHouse.numMarbles = 0;
                    }
                }

                getBoardStatus();
                int playerJar = jars.get(0).numMarbles;
                int computerJar = jars.get(1).numMarbles;

                if (playerJar > computerJar) {
                    System.out.println("Player 1 WON");
                } else if (computerJar > playerJar) {
                    System.out.println("PLayer 2 Won");
                } else {
                    System.out.println("TIE Game");
                }
            }

            getBoardStatus();

            while (getSideCount(true) > 0 && getSideCount(false) > 0) {
                moveMarblesPlayer1(player, computer);
                //while free play move player1 again
                moveMarblesPlayer2();
                //while free play move player 2 again
            }
        }



    /*
    public boolean getSideCount(boolean b){
        int total = 0;
        if(b){
            for(int i = 0; i < numHousesINPUT; i++){
                total+= getHouseCount(player.get(i));
            }
        }else{
            for(int i = 0; i < numHousesINPUT; i++){
                total+= getHouseCount(computer.get(i));
            }
        }
        return total;
    }

    public String houseAttributes(House h){
        String values = Integer.toString((h.numMarbles));
        String play  = Boolean.toString(h.player);
        return "[" + values + ","+ play + "]";
    }

    public String jarAttributes(Jar h){
        String values = Integer.toString((h.numMarbles));
        String play  = Boolean.toString((h.player));
        return "[JAR: " + values + ","+ play + "]";
    } */

}

    private void moveMarblesPlayer2() {
    }

    private void moveMarblesPlayer1(ArrayList<House> player, ArrayList<House> computer) {
    }

    private int getHouseCount(House house) {
        return house.numMarbles;
    }}

/*******************House Class ****************************/
class House{ //represents the divots in the board that hold the marbles still in play
    public int numMarbles;
    boolean player;
    Button houseButton = new Button();

    public House(int num, boolean p) {
        numMarbles = num;
        player = p;
        houseButton.setText(Integer.toString(numMarbles));
    }
}

/*******************Jar Class ****************************/
class Jar{
    public int numMarbles;
    boolean player;
    Button jarButton = new Button();

    public Jar(int num, boolean p){
        numMarbles = num;
        player = p;
        jarButton.setText(Integer.toString(numMarbles));
    }
}

/*****************NODE CLASS ****************************/
class Node{
    int depth;
    int index;
    int score;
    int value;
    ArrayList <Node> children = new ArrayList<Node>();

    public Node(int i, int d){
        index = i;
        depth = d;
        score = 0;
    }
}


/********* TREE CLASS ***************************/
class Tree extends Controller {

    Node root = new Node(0, 0);

    int move = 0;

    //adds a new set of children to a node
    /*void constructTree(Node n) {

        int count = 0;
        for (int i = 0; i < numHousesINPUT; i++) {
            ArrayList<House> pTemp = this.player;
            ArrayList<House> cTemp = this.computer;
            ArrayList<Jar> jTemp = this.jars;
            if (isValidMove(cTemp.get(i))) {
                this.moveMarblesAI(pTemp, cTemp, jTemp, i);
                n.children.add(new Node(i, 1));
            }
            for (Node j : n.children) {
                ArrayList<House> pTemp2 = pTemp;
                ArrayList<House> cTemp2 = cTemp;
                ArrayList<Jar> jTemp2 = jTemp;
                if (isValidMove(cTemp2.get(j.index))) {
                    this.moveMarblesAI(pTemp2, cTemp2, jTemp, count);
                    n.children.get(i).children.add(new Node(count, 2));
                    n.children.get(i).children.get(count).score = getScore(n.children.get(i).children.get(count), jTemp2.get(1).numMarbles);
                    count++;
                }
            }
        }
    }

    void clearTree() {
        for (Node x : this.root.children) {
            for (Node y : x.children) {
                y = null;
            }
            x = null;
        }
        root = null;
    } */

    int getScore(Node n, int newVal) {
        int score = 0;
        if (freeTurn(n)) {
            score = score + 5;
        }

        score += newVal - jars.get(1).numMarbles;

        return score;
    }

    int minimax(Node n, boolean max) {
        if (n.children.size() == 0) {
            return n.score;
        }
        if (max) {
            int maxBestValue = -1000000;
            for (Node x : n.children) {
                if (freeTurn(x)) {
                    max = true;
                } else {
                    max = false;
                }
                int val = minimax(x, max);
                if (val > maxBestValue) {
                    this.move = x.index + 1;
                    System.out.println("new move = " + this.move);
                    maxBestValue = val;
                }
                n.score = maxBestValue;
                return maxBestValue;
            }
        } else {
            int bestValue = 1000000;
            for (Node x : n.children) {
                if (freeTurn(x)) {
                    max = false;
                } else {
                    max = true;
                }
                int val = minimax(x, max);
                if (val < bestValue) {
                    this.move = x.index + 1;
                    System.out.println("new move = " + this.move);
                    bestValue = val;
                }
                n.score = bestValue;
                return bestValue;
            }
        }
        return -1;
    }

    boolean freeTurn(Node n) {
        if ((numHousesINPUT + 1) - n.index == computer.get(n.index).numMarbles) {
            return true;
        } else
            return false;
        }
    }
