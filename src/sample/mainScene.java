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
    /*GUI variables*/
        //Buttons
    Button playButton =  new Button("PLAY");

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


        /*ADD ATTRIBUTES TO SCENE*/
        grid.getChildren().add(playButton);
        grid.getChildren().add(welcomeLabel);
        grid.getChildren().add(instructionsLabel);

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

                //this.show();
            }//end action
        });


    }
} //end class
