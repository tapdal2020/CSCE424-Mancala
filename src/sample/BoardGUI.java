package sample;

import javafx.application.Application;
import javafx.event.*;
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

    Button backButton = new Button("Back to Main Menu");
    Label label = new Label("Game Board");



    //creating scene properties
    Group root = new Group();
    GridPane grid = new GridPane();
    Scene scene = new Scene(root, 900, 900);


    public BoardGUI(int numHouses, int numMarbles, boolean randDistMarbles, boolean gameType, long timerLength){
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
        b.updateArrayLists(numHouses, numMarbles);
        b.updateButtonLabels();

        //random distribution of marbles
        if(randDistMarbles) {
            b.assignRandomMarbles(); //TODO: remove print statements
            b.getBoardStatus();
            b.updateButtonLabels();
        }


        //button board //TODO: FIX ME
        for( int i = 0; i < numHouses; i++){
            computerHouseButton = new Button();
            computerHouseButton.setText(Integer.toString(b.computer.get(i).numMarbles));
            GridPane.setConstraints(computerHouseButton,1,i+2);
            computerHouseButton.setMinSize(60,60);
            grid.getChildren().add(computerHouseButton);

            playerHouseButton = new Button();
            playerHouseButton.setText(Integer.toString(b.player.get(i).numMarbles));
            GridPane.setConstraints(playerHouseButton,2,i+2);
            playerHouseButton.setMinSize(60,60);
            grid.getChildren().add(playerHouseButton);

        }

        //player jar
        playerJarButton = new Button();
        playerJarButton.setText(Integer.toString(b.jars.get(0).numMarbles));
        GridPane.setConstraints(playerJarButton,1,1);
        GridPane.setColumnSpan(playerJarButton,2);
        playerJarButton.setMinSize(130,60);

        //computer jar
        computerJarButton = new Button();
        computerJarButton.setText(Integer.toString(b.jars.get(1).numMarbles));
        GridPane.setConstraints(computerJarButton,1,numHouses + 2);
        GridPane.setColumnSpan(computerJarButton,2);
        computerJarButton.setMinSize(130,60);


        //Back Button
        GridPane.setConstraints(backButton,15,15);
        GridPane.setRowSpan(backButton,2);
        GridPane.setColumnSpan(backButton,5);


        /*ADD ATTRIBUTES TO SCENE*/
        grid.getChildren().add(label);
        grid.getChildren().add(playerJarButton);
        grid.getChildren().add(computerJarButton);
        grid.getChildren().add(backButton);

        this.show();

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //mainSceneWindow.show();
            }
        });

        computerHouseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /*
                * if computers turn
                *   get index of button, get numOfMarbles (label),
                *   set numOfMarbles (label) to 0
                * else
                * nothing*/
            }
        });
    }

}
