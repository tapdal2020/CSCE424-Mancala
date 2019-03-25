package sample;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.awt.*;
import java.util.*;

//for boolean player , false->AI ; true->User

//there are no structs for Java, so we must make new classes, however we don't need new files for them

public class Controller {

    boolean isEnd = false; //game begins in the playing state
    boolean isTurn = true; //it is always the user's turn at the beginning of the game
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
        //ArrayList<Jar> jars = new ArrayList<>();

        jars.add(new Jar(0,true));
        jars.add(new Jar(0,false));

        //getBoardStatus();
    }

    //checks different aspects of the board and determines if there are any messages to be displayed or changes to be displayed
    public void getBoardStatus(){ //print board

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

        if(getSideCount(true) == 0 || getSideCount(false) == 0){
            isEnd = true;
            //function to determine who wins and who lost
            //set a boolean to change it on the screen
        }
    }

    public void moveMarbles(){
        while( getSideCount(true) > 0 || getSideCount(false) >0 ) {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("\nEnter an integer of the house you wish to select: ");
            int userInput = keyboard.nextInt();

            House selectedHouse = player.get(userInput - 1);
            if (isValidMove(selectedHouse)) {

                int numMarblesToMove = selectedHouse.numMarbles;
                selectedHouse.numMarbles = 0;

                while(numMarblesToMove > 0 ) {
                    for (int i = userInput; i < numHousesINPUT; i++) {
                        House currHouse = player.get(i);
                        if (numMarblesToMove > 0) {
                            currHouse.numMarbles++;
                            numMarblesToMove--;
                        } else {
                            break;
                        }
                    }
                    if (numMarblesToMove > 0) {
                        Jar playerJar = jars.get(0);
                        playerJar.numMarbles++;
                        numMarblesToMove--;
                    }

                    for (int i = numHousesINPUT - 1; i >= 0; i--) {
                        House currHouse = computer.get(i);
                        if (numMarblesToMove > 0) {
                            currHouse.numMarbles++;
                            numMarblesToMove--;
                        } else {
                            break;
                        }
                    }
                    userInput = 0; //move index back to first house of player to keep moving marbles correctly
                    System.out.print("Number of marbles left to move: " + numMarblesToMove + "\n"); //REMOVE LATER FIX ME
                }
            } else {
                System.out.println("\nError: Invalid entry\nPlease select a non-empty house by entering values 1-6\n\n");
            }
            getBoardStatus();
            //isTurn=false; // this will set the state message to say "The computer is playing". At the end of our AI turn function, we will return the value back to true.
        }
    }
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

        if ((isTurn == true && findHouseSide == "player") || (isTurn == false && findHouseSide == "computer")) {
            if (findHouseSide == "player") {
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
    }

    public int getJarCount(Jar j){
        return j.numMarbles;
    }

    public int getHouseCount(House h){
        return h.numMarbles;
    }

    public int getSideCount(boolean p){
        int total = 0;
        if(p){
            for(int i = 0; i < player.size(); i++){
                total+= getHouseCount(player.get(i));
            }
        }else{
            for(int i = 0; i < computer.size(); i++){
                total+= getHouseCount(player.get(i));
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
