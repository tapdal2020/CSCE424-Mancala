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
import sun.awt.image.GifImageDecoder;

import java.util.ArrayList;
import java.util.Scanner;

public class BoardGUI extends Stage{

    Button playerHouseButton;
    Button computerHouseButton;
    Button playerJarButton;
    Button computerJarButton;
    Button enterButton = new Button("Enter");


    Button backButton = new Button("Back to Main Menu");

    Label playerTurnLabel= new Label("Player 1's Turn");
    Label indexLabel = new Label("1");
    Label player1Label = new Label ("Player 1:");
    Label player2Label = new Label("Player 2: ");

    final TextField player1INPUT = new TextField();
    final TextField player2INPUT = new TextField();

    ArrayList<Button> playerButtonsList = new ArrayList<>(9);
    ArrayList<Button> computerButtonsList = new ArrayList<>(9);


    //creating scene properties
    Group root = new Group();
    GridPane grid = new GridPane();
    Scene scene = new Scene(root, 900, 900);

    int turnCount;

    public BoardGUI(int numHouses, int numMarbles, boolean randDistMarbles, boolean gameType, long timerLength){
        //set scene properties
        grid.setPadding(new Insets(30,80,50,80));
        grid.setVgap(9);
        grid.setHgap(9);
        scene.setRoot(grid);
        this.setScene(scene);

        //label constraints
        GridPane.setColumnSpan(playerTurnLabel,3);
        GridPane.setConstraints(playerTurnLabel,6,5);
        playerTurnLabel.setFont(Font.font("Arial",30));


        //backend functions are in controller
        Controller b = new Controller();
        b.numHousesINPUT = numHouses;
        b.numSeedsINPUT = numMarbles;
        b.updateArrayLists(numHouses, numMarbles);
        b.updateButtonLabels();
        b.turnCount = turnCount;

        //random distribution of marbles
        if(randDistMarbles) {
            b.assignRandomMarbles(); //TODO: remove print statements
            b.getBoardStatus();
            b.updateButtonLabels();
        }


        //Player 1 label, input field box //TODO
        GridPane.setConstraints(player1Label,0,2);
        GridPane.setConstraints(player1INPUT,0,3);

        //Player 2 label, input field box //TODO
        GridPane.setConstraints(player2Label,7,2);
        GridPane.setConstraints(player2INPUT,7,3);

        //enter button
        GridPane.setConstraints(enterButton,8,8);


        //button board
        for( int i = 0; i < numHouses; i++) {
            indexLabel = new Label(Integer.toString(i + 1));
            GridPane.setConstraints(indexLabel, 2, i + 2);
            grid.getChildren().add(indexLabel);

            playerHouseButton = (b.player).get(i).houseButton;
            playerHouseButton.setText(Integer.toString(b.player.get(i).numMarbles));
            GridPane.setConstraints(playerHouseButton, 3, i + 2);
            playerHouseButton.setMinSize(60, 60);
            playerButtonsList.add(playerHouseButton);
            grid.getChildren().add(playerHouseButton);

            computerHouseButton = (b.computer).get(i).houseButton;
            computerHouseButton.setText(Integer.toString(b.computer.get(i).numMarbles));
            GridPane.setConstraints(computerHouseButton, 4, i + 2);
            computerHouseButton.setMinSize(60, 60);
            computerButtonsList.add(computerHouseButton);
            grid.getChildren().add(computerHouseButton);

            indexLabel = new Label(Integer.toString(numHouses - i));
            GridPane.setConstraints(indexLabel, 5, i + 2);
            grid.getChildren().add(indexLabel);
        }

        //player jar
        playerJarButton = new Button();
        playerJarButton.setText(Integer.toString(b.jars.get(0).numMarbles));
        GridPane.setConstraints(playerJarButton,3,1);
        GridPane.setColumnSpan(playerJarButton,2);
        playerJarButton.setMinSize(130,60);

        //computer jar
        computerJarButton = new Button();
        computerJarButton.setText(Integer.toString(b.jars.get(1).numMarbles));
        GridPane.setConstraints(computerJarButton,3,numHouses + 2);
        GridPane.setColumnSpan(computerJarButton,2);
        computerJarButton.setMinSize(130,60);


        //Back Button
        GridPane.setConstraints(backButton,8,10);
        GridPane.setRowSpan(backButton,2);
        GridPane.setColumnSpan(backButton,5);


        /*ADD ATTRIBUTES TO SCENE*/
        grid.getChildren().add(playerTurnLabel);
        grid.getChildren().add(playerJarButton);
        grid.getChildren().add(computerJarButton);
        grid.getChildren().add(backButton);
        grid.getChildren().add(enterButton);

        grid.getChildren().add(player1Label);
        grid.getChildren().add(player1INPUT);

        grid.getChildren().add(player2Label);
        grid.getChildren().add(player2INPUT);

        grid.setGridLinesVisible(true);

        this.show();

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //mainSceneWindow.show();
            }
        });

        enterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //if player 2 turn
                if(turnCount%2 ==0) {

                    playerTurnLabel.setText("Player 2's Turn");
                    if (player2INPUT.getText() != null && !player2INPUT.getText().isEmpty()) {
                        int index = Integer.parseInt(player2INPUT.getText());
                        b.moveMarbles(b.player,b.computer,b.jars,false,index);
                        Button currButton = computerButtonsList.get(index);
                    }
                }
                //else if player 1 turn
                else {
                    playerTurnLabel.setText("Player 1's Turn");
                    if (player1INPUT.getText() != null && !player1INPUT.getText().isEmpty()) {
                        int index = Integer.parseInt(player1INPUT.getText());
                        b.moveMarbles(b.player,b.computer,b.jars,true, index);
                        Button currButton = playerButtonsList.get(index);
                    }
                }
            }
        });
    }

    public BoardGUI(int numHousesINPUT, int numSeedsINPUT) {
    }
}