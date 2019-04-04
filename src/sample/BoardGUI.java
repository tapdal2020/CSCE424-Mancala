package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.geometry.*;


import java.util.Scanner;

public class BoardGUI extends Stage{

    Button playerHouseButton;
    Button computerHouseButton;
    Button playerJarButton;
    Button computerJarButton;
    Label label = new Label("Game Board");



    //creating scene properties
    Group root = new Group();
    GridPane grid = new GridPane();
    Scene scene = new Scene(root, 900, 900);

    void updateButtonLabels(){

    }

    public BoardGUI(int numHouses, int numMarbles, boolean randDistMarbles){
        //set scene properties
        grid.setPadding(new Insets(30,80,50,80));
        grid.setVgap(9);
        grid.setHgap(9);
        scene.setRoot(grid);
        this.setScene(scene);

        //label constraints
        GridPane.setColumnSpan(label,3);


        //backend functions are in controller
        Controller b = new Controller();
        b.numHousesINPUT = numHouses;
        b.numSeedsINPUT = numMarbles;
        updateButtonLabels();

        //random distribution of marbles
        if(randDistMarbles) {
            b.assignRandomMarbles(); //TODO: remove print statements
        }
            //b.getBoardStatus(); //TODO: remove later
            updateButtonLabels();



        //button board //TODO: FIX ME
        for( int i = 0; i < numHouses; i++){
            computerHouseButton = new Button();
            computerHouseButton.setText(Integer.toString(b.computer.get(i).numMarbles));
            GridPane.setConstraints(computerHouseButton,i+1,2);
            grid.getChildren().add(computerHouseButton);

            playerHouseButton = new Button();
            playerHouseButton.setText(Integer.toString(b.player.get(i).numMarbles));
            GridPane.setConstraints(playerHouseButton,i+1,3);
            grid.getChildren().add(playerHouseButton);

        }

        //player jar
        playerJarButton = new Button();
        playerJarButton.setText(Integer.toString(b.jars.get(0).numMarbles));
        GridPane.setConstraints(playerJarButton,0,2);
        GridPane.setRowSpan(playerJarButton,2);

        //computer jar
        computerJarButton = new Button();
        computerJarButton.setText(Integer.toString(b.jars.get(1).numMarbles));
        GridPane.setConstraints(computerJarButton,numHouses+2,2);
        GridPane.setRowSpan(computerJarButton,2);



        /*ADD ATTRIBUTES TO SCENE*/
        grid.getChildren().add(label);
        grid.getChildren().add(playerJarButton);
        grid.getChildren().add(computerJarButton);

        this.show();
    }

}
