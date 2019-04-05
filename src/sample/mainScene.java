package sample;

import javafx.application.Application;
import javafx.event.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.geometry.*;



public class mainScene extends Stage{
    //helper variables
    int numHouse = 6; //default
    int numMarbles = 4; //default
    long timerLength = 0; //default
    boolean randomDistrBOOL = false; //default
    boolean gameTypeAIBOOL = false; //default two player game (false -> 2P, true -> AI)
    boolean localGameBool = true; //default to local game (true ->local, false -> remote)

    /*GUI variables*/
        //Buttons
    Button playButton =  new Button("PLAY");
    Button randomDistYES = new Button ("Yes");
    Button randomDistNO = new Button("No");
    Button AIYESBtn = new Button("AI");
    Button twoPlayerYESBtn = new Button("Two Players");
    Button localButton = new Button("Local");
    Button remoteButton = new Button("Remote");

        //Labels
    private Label randomDistTEXT = new Label("Random Distribution of Marbles? ");
    private Label welcomeLabel = new Label("Welcome to MANCALA");
    private Label instructionsLabel = new Label("Instructions");
    private Label houseInputLabel = new Label("Enter a number between 4 and 9");
    private Label marbleInputLabel = new Label("Enter a number between 1 and 10");
    private Label gameTypeLabel = new Label("Select Game Type");
    private Label timerLabel = new Label("Enter the desired time length");
    private Label remoteLocalLabel = new Label("Local or Remote Game?");

        //Text Input Boxes
    final TextField houseInputTEXT = new TextField();
    final TextField marbleInputTEXT = new TextField();
    final TextField timerInputTEXT = new TextField();

    //creating scene properties
    Group root = new Group();
    GridPane grid = new GridPane();
    Scene scene = new Scene(root, 900, 900);




    public mainScene(){
        //set scene properties
        grid.setPadding(new Insets(30,80,50,80));
        grid.setVgap(9);
        grid.setHgap(9);
        scene.setRoot(grid);
        this.setScene(scene);

        //TODO: remove later
        //grid.setGridLinesVisible(true);

        /*Labels*/
        GridPane.setConstraints(welcomeLabel, 0,0);
        welcomeLabel.setAlignment(Pos.CENTER);
        GridPane.setColumnSpan(welcomeLabel,8);
        GridPane.setConstraints(instructionsLabel, 0,1);
        instructionsLabel.setAlignment(Pos.CENTER);
        GridPane.setColumnSpan(instructionsLabel,8);

        GridPane.setConstraints(gameTypeLabel,0,7);
        gameTypeLabel.setAlignment(Pos.CENTER);
        GridPane.setColumnSpan(gameTypeLabel,8);

        welcomeLabel.setFont(Font.font("Arial",30)); //TODO: Change font, font size
        instructionsLabel.setText("Select Game Style Below "); //TODO: Add instructions text

        GridPane.setConstraints(houseInputLabel, 1,5);
        houseInputLabel.setAlignment(Pos.CENTER);
        GridPane.setColumnSpan(houseInputLabel,8);

        GridPane.setConstraints(marbleInputLabel, 3,5);
        marbleInputLabel.setAlignment(Pos.CENTER);
        GridPane.setColumnSpan(marbleInputLabel,8);

        GridPane.setConstraints(timerLabel,1,9);
        timerLabel.setAlignment(Pos.CENTER);
        GridPane.setColumnSpan(timerLabel,8);

        GridPane.setConstraints(remoteLocalLabel, 1,11);
        remoteLocalLabel.setAlignment(Pos.CENTER);
        GridPane.setColumnSpan(remoteLocalLabel,8);

        /*Input fields*/
        GridPane.setColumnSpan(houseInputTEXT, 2);
        houseInputTEXT.setPromptText("Number of Houses");
        GridPane.setConstraints(houseInputTEXT,1,6);

        GridPane.setColumnSpan(marbleInputTEXT, 2);
        marbleInputTEXT.setPromptText("Number of Marbles");
        GridPane.setConstraints(marbleInputTEXT,3,6);

        GridPane.setConstraints(timerInputTEXT,1,10);
        timerInputTEXT.setPromptText("Timer Length");
        GridPane.setColumnSpan(timerInputTEXT,2);

        /*Buttons*/
        GridPane.setConstraints(playButton,1,15);
        playButton.setAlignment(Pos.CENTER);

        GridPane.setConstraints(randomDistTEXT,1,3);
        GridPane.setConstraints(randomDistYES,1,4);
        randomDistYES.setAlignment(Pos.CENTER);
        GridPane.setConstraints(randomDistNO,2,4);
        randomDistNO.setAlignment(Pos.CENTER);

        GridPane.setConstraints(AIYESBtn,1,8);
        GridPane.setConstraints(twoPlayerYESBtn,2,8);
        AIYESBtn.setAlignment(Pos.CENTER);
        twoPlayerYESBtn.setAlignment(Pos.CENTER);

        GridPane.setConstraints(localButton,1,12);
        localButton.setAlignment(Pos.CENTER);
        GridPane.setConstraints(remoteButton,2,12);
        remoteButton.setAlignment(Pos.CENTER);











        /*ADD ATTRIBUTES TO SCENE*/
        grid.getChildren().add(playButton);
        grid.getChildren().add(welcomeLabel);
        grid.getChildren().add(instructionsLabel);
        grid.getChildren().add(houseInputTEXT);
        grid.getChildren().add(marbleInputTEXT);
        grid.getChildren().add(randomDistTEXT);
        grid.getChildren().add(randomDistYES);
        grid.getChildren().add(randomDistNO);

        grid.getChildren().add(houseInputLabel);
        grid.getChildren().add(marbleInputLabel);

        grid.getChildren().add(AIYESBtn);
        grid.getChildren().add(twoPlayerYESBtn);
        grid.getChildren().add(gameTypeLabel);

        grid.getChildren().add(timerLabel);
        grid.getChildren().add(timerInputTEXT);

        grid.getChildren().add(remoteButton);
        grid.getChildren().add(localButton);
        grid.getChildren().add(remoteLocalLabel);

        this.show();



        /*BUTTON ON ACTIONS*/
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) { //TODO: add error handling for text input (non numerical values)
                boolean errorLabelShow = false;
                if( houseInputTEXT.getText() != null && !houseInputTEXT.getText().isEmpty()){
                    numHouse = Integer.parseInt(houseInputTEXT.getText());
                    if( numHouse < 4 || numHouse > 9){
                        errorLabelShow = true;
                    }
                    else {
                        errorLabelShow = false;
                    }
                }
                else{ //empty input
                    numHouse = 6; //default
                }
                if (marbleInputTEXT.getText() != null && !marbleInputTEXT.getText().isEmpty()){
                    numMarbles = Integer.parseInt(marbleInputTEXT.getText());
                    if( numMarbles < 1 || numMarbles > 10){
                        errorLabelShow = true;
                    }
                    else {
                        errorLabelShow = false;
                    }
                }

                if ( timerInputTEXT.getText() != null && !timerInputTEXT.getText().isEmpty()){
                    timerLength = Integer.parseInt(timerInputTEXT.getText());
                }

                if( !errorLabelShow){
                    new BoardGUI(numHouse, numMarbles,randomDistrBOOL,gameTypeAIBOOL, timerLength);
                }

                //this.show();
            }//end action
        });

        randomDistNO.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                randomDistrBOOL = false;
            }
        });

        randomDistYES.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                randomDistrBOOL = true;
            }
        });

        AIYESBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameTypeAIBOOL = true;
            }
        });

        twoPlayerYESBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameTypeAIBOOL = false;
            }
        });

        localButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                localGameBool = true;
            }
        });

        remoteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                localGameBool = false;
            }
        });
    }
} //end class
