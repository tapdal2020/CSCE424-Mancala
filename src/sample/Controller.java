package sample;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.awt.*;
import java.util.*;

//for boolean player , false->AI ; true->User

//there are no structs for Java, so we must make new classes, however we don't need new files for them

public class Controller {

    boolean isEnd = false; //game begins in the playing state
    boolean isTurn = true; //it is always the user's turn at the beginning of the game
    boolean isAI = false; //game begins in users turn
    int moves = 0;
    int numHousesINPUT = 6; //default number of houses is 6;
    int numSeedsINPUT = 4; //default number of seeds/house is 4

    ArrayList<House> player = new ArrayList<House>();
    ArrayList<House> computer = new ArrayList<House>();

    ArrayList<Jar> jars = new ArrayList<>();


    public Controller(){ //constructs the board and its components
        for(int i = 0; i < numHousesINPUT; i++) {
            player.add(new House(numSeedsINPUT, true));
            computer.add(new House(numSeedsINPUT, false));
        }
        jars.add(new Jar(0,true));
        jars.add(new Jar(0,false));
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
            House compHouse = computer.get(i);
            System.out.print((i+1)+houseAttributes(compHouse) + " ");
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

    public void updateIsEnd(){
        if(getSideCount(true) == 0 || getSideCount(false) == 0){
            isEnd = true;
            //function to determine who wins and who lost
            //set a boolean to change it on the screen
        }
    }
    /******************** Move Marbles ****************************/
    public void moveMarblesPlayer1(ArrayList<House> playerList, ArrayList<House> computerList){
        updateIsEnd();

        if ( !isEnd) { //TODO Fix end game ( empty array check)
            getBoardStatus();
            /*PLAYER INPUT*/
            System.out.println("PLAYER 1");
            Scanner keyboard = new Scanner(System.in);
            System.out.print("Enter an integer of the house you wish to select: ");
            int userInput = keyboard.nextInt();

            House selectedHouse = playerList.get(userInput - 1); //index differs by 1
            if (isValidMove(selectedHouse)) {

                /*get number of marbles in selected house*/
                int numMarblesToMove = selectedHouse.numMarbles;
                selectedHouse.numMarbles = 0;

                while (numMarblesToMove > 0) {
                    //own houses
                    for (int i = userInput; i < numHousesINPUT; i++) {
                        House currHouse = playerList.get(i);
                        if (numMarblesToMove > 0) {
                            currHouse.numMarbles++;
                            numMarblesToMove--;
                        }
                        if( numMarblesToMove == 0){ //last marble
                            //landed in empty house and opposite house containts marbls
                            //capture the opposite house
                            if( currHouse.numMarbles == 1 && computerList.get(i).numMarbles > 0 ){
                                Jar playerJar = jars.get(0);
                                House oppositeHouse = computerList.get(i);
                                playerJar.numMarbles = playerJar.numMarbles + oppositeHouse.numMarbles + 1;
                                oppositeHouse.numMarbles = 0;
                                currHouse.numMarbles = 0;
                            }
                        }
                    }
                    //own jar
                    if (numMarblesToMove > 0) {
                        Jar playerJar = jars.get(0);
                        playerJar.numMarbles++;
                        numMarblesToMove--;

                        if( numMarblesToMove == 0){ //user landed in own jar, gets free play
                            if(!isEnd) {
                                isTurn = true;
                                moveMarblesPlayer1(playerList, computerList);
                            }
                            /*else{
                                System.out.print("END GAME !!!!");
                            }*/
                        }
                    }
                    //other players houses
                    for (int i = numHousesINPUT - 1; i >= 0; i--) {
                        House currHouse = computerList.get(i);
                        if (numMarblesToMove > 0) {
                            currHouse.numMarbles++;
                            numMarblesToMove--;
                        }
                    }
                    userInput = 0; //move index back to first house of player to keep moving marbles correctly
                }
                //getBoardStatus();
                isTurn = false; // this will set the state message to say "The computer is playing". At the end of our AI turn function, we will return the value back to true.
                Tree t = new Tree();
                System.out.println("Suggested move: " + t.AIMove());
                moveMarblesPlayer2(playerList,computerList,jars,0);
            } else {
                System.out.println("\nError: Invalid entry\nPlease select a non-empty house by entering values 1-6\n\n");
                isTurn = true; //stay on player 1 turn
                moveMarblesPlayer1(playerList, computerList);
            }
        }
        /*else {
            System.out.println("END GAME, NO MARBLES ON ONE SIDE p1" );
            break;
        }*/

    }//end move marbles player 1

    public void moveMarblesPlayer2(ArrayList<House> playerList, ArrayList<House> computerList, ArrayList<Jar> jarList,int AIInput){

        updateIsEnd();
        if ( !isEnd) {
            getBoardStatus();
            int userInput;
            if(!isAI) {
                System.out.println("PLAYER 2:");
                Scanner keyboard = new Scanner(System.in);
                System.out.print("Enter an integer of the house you wish to select: ");
                userInput = keyboard.nextInt();
            }
            else{
                userInput = AIInput;
            }
            House selectedHouse = computerList.get(userInput - 1);
            if (isValidMove(selectedHouse)) {
                int numMarblesToMove = selectedHouse.numMarbles;
                selectedHouse.numMarbles = 0;

                while (numMarblesToMove > 0) {
                    if ( userInput != 1) { //if first index is chosen can move to jar
                        for (int i = userInput - 2; i >= 0; i--) {
                            House currHouse = computerList.get(i);
                            if (numMarblesToMove > 0) {
                                currHouse.numMarbles++;
                                numMarblesToMove--;
                            }
                            if( numMarblesToMove == 0){
                                //landed in empty house and opposite house containts marbls
                                //capture the opposite house
                                if( currHouse.numMarbles == 1 && playerList.get(i).numMarbles > 0 ){
                                    Jar playerJar = jarList.get(1);
                                    House oppositeHouse = playerList.get(i);
                                    playerJar.numMarbles = playerJar.numMarbles + oppositeHouse.numMarbles + 1;
                                    oppositeHouse.numMarbles = 0;
                                    currHouse.numMarbles = 0;
                                }
                            }
                        }
                    }
                    if (numMarblesToMove > 0) {
                        Jar playerJar = jarList.get(1);
                        playerJar.numMarbles++;
                        numMarblesToMove--;
                        if( numMarblesToMove == 0){ //player2 landed in own jar, gets free play
                            if(!isEnd) {
                                isTurn = false;
                                Tree t = new Tree();
                                System.out.println("Suggested move: " + t.AIMove());
                                moveMarblesPlayer2(playerList, computerList, jarList, 0);
                            }
                            /*else{
                                System.out.print("END OF GAME !!!");
                            }*/
                        }
                    }

                    for (int i = 0; i < numHousesINPUT; i++) {
                        House currHouse = playerList.get(i);
                        if (numMarblesToMove > 0) {
                            currHouse.numMarbles++;
                            numMarblesToMove--;
                        }
                    }
                    userInput = numHousesINPUT+1; //move index back to first house of player to keep moving marbles correctly
                } //end while numMarbles > 0
               // getBoardStatus();
                isTurn = true; // return to player 1 turn
                moveMarblesPlayer1(playerList,computerList);
            } else {
                System.out.println("\nError: Invalid entry\nPlease select a non-empty house by entering values 1-6\n\n");
                isTurn = false; // stay on player 2 turn
                Tree t = new Tree();
                System.out.println("Suggested move: " + t.AIMove());
                moveMarblesPlayer2(playerList,computerList,jarList,0);
            }
        }
        /*else {
            System.out.println("END GAME, NO MARBLES ON ONE SIDE p2");
        }*/

    }//end move marbles player 2

    /******************** Valid Move ****************************/
    public boolean isValidMove(House h){
        String findHouseSide = "none";

        //check if house is on the player or computer's side
        for (int i = 0; i < numHousesINPUT; i++) {
            if (player.get(i) == h) {
                findHouseSide = "player";
                System.out.println("player's turn");

            }
            if (computer.get(i) == h) {
                findHouseSide = "computer";
                System.out.println("computer's turn, picked house");
            }
        }

        if ((isTurn && findHouseSide.equals("player")) || (!isTurn && findHouseSide.equals("computer"))) {
            if (findHouseSide.equals( "player") ){
                if (getHouseCount(h) > 0) {
                    return true;
                }
                else{
                    return false;
                }
            }
            if (findHouseSide == "computer") {
                if (getHouseCount(h) > 0) {
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }//end is valid move


    /****************** PLAY GAME *******************/
    public void twoPlayerGame(){
        moveMarblesPlayer1(player, computer);
        if(isEnd) {
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
                System.out.println("Player WON");
            } else if (computerJar > playerJar) {
                System.out.println("PLayer 2 Won");
            } else {
                System.out.println("TIE Game");
            }
        }
    }

    public void aiGame(){
        System.out.print("AI GAME IMPLEMENTATION: IN PROGRESS"); //TODO
    }

    public void gameType(){
        String gameTypeINPUT = "";
        System.out.print("Game Type (AI/2P): ");
        Scanner keyboard = new Scanner(System.in);
        gameTypeINPUT = keyboard.nextLine();
        if ( gameTypeINPUT.equals("AI")){
            isAI = true;
            aiGame();
        }
        else if ( gameTypeINPUT.equals("2P")){
            isAI = false;
            twoPlayerGame();
        }
    }

    /*******************Jar/House Functions ****************************/
    public int getJarCount(Jar j){
        return j.numMarbles;
    }

    public int getHouseCount(House h){
        return h.numMarbles;
    }

    public int getSideCount(boolean p){
        int total = 0;
        if(p){
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
        String play  = Boolean.toString((h.player));
        return "[" + values + ","+ play + "]";
    }

    public String jarAttributes(Jar h){
        String values = Integer.toString((h.numMarbles));
        String play  = Boolean.toString((h.player));
        return "[JAR: " + values + ","+ play + "]";
    }

}

/*******************House Class ****************************/
class House{ //represents the divots in the board that hold the marbles still in play
    public int numMarbles;
    boolean player;

    public House(int num, boolean p){
        numMarbles = num;
        player = p;
    }


}

/*******************Jar Class ****************************/
class Jar{
    public int numMarbles;
    boolean player;

    public Jar(int num, boolean p){
        numMarbles = num;
        player = p;
    }
}

/*****************NODE CLASS ****************************/
class Node{
    int depth;
    int index;
    int score;
    int value;
    ArrayList <Node> children = new ArrayList<Node>();

    public Node(int i, int d, int v){
        index = i;
        depth = d;
        score = 0;
        value = v;
    }
}

/********* TREE CLASS ***************************/
class Tree extends Controller{

    Node root = new Node(0,0,0);

    int move = 0;

    //adds a new set of children to a node
    void constructTree(Node n){
       for(int i = 0; i < numHousesINPUT; i++){
           ArrayList<House> pTemp = player;
           ArrayList<House> cTemp = computer;
           ArrayList<Jar>jTemp = jars;
           moveMarblesPlayer2(pTemp,cTemp,jTemp,i);
           n.children.add(new Node(i,0,cTemp.get(i).numMarbles));
           n.children.get(i).score = getScore(n.children.get(i),jTemp.get(1).numMarbles);
       }
    }

    void clearTree(){
        for(Node x: root.children){
            /*for(Node y: x.children){
                y = null;
            }*/
            x=null;
        }
        root = null;
    }

    int getScore(Node n, int newVal){
        int score = 0;
        if(freeTurn(n)){
            score = score + 5;
        }

        score += newVal - jars.get(1).numMarbles;

        return score;
    }

    int minimax(Node n, int depth, boolean max){
        if(depth == 0 || n.children.size()==0){
            return n.score;
        }
        if(max){
            int bestValue = -1000000;
            for(Node x: n.children){
                if(freeTurn(x)){
                    max = true;
                }else{
                    max = false;
                }
                int val = minimax(x,depth-1,max);
                if(val>bestValue){
                    move = x.index;
                    bestValue = val;
                }
                n.score = bestValue;
                return bestValue;
            }
        }else {
            int bestValue = 1000000;
            for (Node x : n.children) {
                if (freeTurn(x)) {
                    max = false;
                } else {
                    max = true;
                }
                int val = minimax(x, depth - 1, max);
                if(val < bestValue){
                    move = x.index;
                    bestValue = val;
                }
                n.score = bestValue;
                return bestValue;

            }
        }
        return -1;
    }

    boolean freeTurn(Node n){
        if((numHousesINPUT+1)-n.index == computer.get(n.index).numMarbles){
            return true;
        }else{
            return false;
        }
    }

    int AIMove(){
        this.constructTree(this.root);
        return minimax(this.root,2,true);
    }

}
