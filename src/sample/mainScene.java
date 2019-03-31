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
    boolean randomDistrBOOL = false; //default

    //GUI variables
    Button enterButton =  new Button("PLAY");
    Button randomDistYES = new Button ("Yes");
    Button randomDistNO = new Button("No");
    private Label randomDistTEXT = new Label("Random Distribution of Marbles? ");
    private Label welcomeLabel = new Label("Welcome to MANCALA");
    private Label instructionsLabel = new Label("Instructions");
    final TextField houseInputTEXT = new TextField();
    final TextField marbleInputTEXT = new TextField();

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


        GridPane.setConstraints(welcomeLabel, 0,0);
        welcomeLabel.setAlignment(Pos.CENTER);
        GridPane.setColumnSpan(welcomeLabel,6);
        GridPane.setConstraints(instructionsLabel, 0,1);
        instructionsLabel.setAlignment(Pos.CENTER);

        welcomeLabel.setFont(Font.font("Arial",30)); //TODO: Change font, font size
        //instructionsLabel.setText("") //TODO: Add instructions text

        GridPane.setColumnSpan(houseInputTEXT, 2);
        houseInputTEXT.setPromptText("Number of houses");
        GridPane.setConstraints(houseInputTEXT,1,5);

        GridPane.setColumnSpan(marbleInputTEXT, 2);
        marbleInputTEXT.setPromptText("Number of marbles");
        GridPane.setConstraints(marbleInputTEXT,3,5);

        GridPane.setConstraints(enterButton,1,6);
        enterButton.setAlignment(Pos.CENTER);

        GridPane.setConstraints(randomDistTEXT,1,3);
        GridPane.setConstraints(randomDistYES,1,4);
        randomDistYES.setAlignment(Pos.CENTER);
        GridPane.setConstraints(randomDistNO,2,4);
        randomDistNO.setAlignment(Pos.CENTER);











        /*ADD ATTRIBUTES TO SCENE*/
        grid.getChildren().add(enterButton);
        grid.getChildren().add(welcomeLabel);
        grid.getChildren().add(houseInputTEXT);
        grid.getChildren().add(marbleInputTEXT);
        grid.getChildren().add(randomDistTEXT);
        grid.getChildren().add(randomDistYES);
        grid.getChildren().add(randomDistNO);

        this.show();


        /*BUTTON ON ACTIONS*/
        enterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) { //TODO: add error handling for text input (non numerical values)
                if( houseInputTEXT.getText() != null && !houseInputTEXT.getText().isEmpty()){
                    numHouse = Integer.parseInt(houseInputTEXT.getText());
                }
                if (marbleInputTEXT.getText() != null && !marbleInputTEXT.getText().isEmpty()){
                    numMarbles = Integer.parseInt(marbleInputTEXT.getText());
                }

                new BoardGUI(numHouse, numMarbles,randomDistrBOOL);
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

    }
} //end class
