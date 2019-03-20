package sample;

import java.util.*;

//for boolean player , false->AI ; true->User

//there are no structs for Java, so we must make new classes, however we don't need new files for them

public class Controller {

    boolean isEnd = false; //game begins in the playing state
    boolean isTurn = true; //it is always the user's turn at the beginning of the game
    int moves = 0;

    ArrayList<House> player = new ArrayList<House>();
    ArrayList<House> computer = new ArrayList<House>();
    ArrayList<Jar> jars = new ArrayList<>();

    public Controller(){ //constructs the board and its components
        for(int i = 0; i < 6; i++) {
            player.add(new House(4, true));
            computer.add(new House(4, false));

        }
        
        jars.add(new Jar(0,true));
        jars.add(new Jar(0,false));
    }

    //checks different aspects of the board and determines if there are any messages to be displayed or changes to be displayed
     public void getBoardStatus(){ //print board

        System.out.print("\t \t \t\t");
        for (House playerHouse: player){
            System.out.print(houseAttributes(playerHouse) + " ");
        }

        Jar playerJar = jars.get(0);
        System.out.println(jarAttributes(playerJar));

        Jar computerJar = jars.get(1);
        System.out.print(jarAttributes(computerJar) + " ");

        for (House compHouse: computer){
            System.out.print(houseAttributes(compHouse) + " ");
        }

    }

   public boolean isValidMove(House h){
        if(getHouseCount(h) > 0) { //marbles exist in house 
            return true;
        } //else empty (not a valid move)
        return false;
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

class House{ //represents the divots in the board that hold the marbles still in play
    public int numMarbles;
    boolean player;

    public House(int num, boolean p){
        numMarbles = num;
        player = p;
    }
}

class Jar{
    public int numMarbles;
    boolean player;

    public Jar(int num, boolean p){
        numMarbles = num;
        player = p;
    }
}

