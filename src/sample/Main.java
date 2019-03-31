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

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        new  mainScene();
        /* Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("MANCALA");
        Scene scene = new Scene(root, 900, 500);
        primaryStage.setScene(scene);

        primaryStage.show();*/
    }


    public static void main(String[] args){
      //initialize objects
       // Controller b = new Controller();
       // b.getUserInputs();
        //b.assignRandomMarbles();
        //b.gameType();

    //BoardGUI board = new BoardGUI(b.numHousesINPUT, b.numSeedsINPUT);

        //System.out.println(b.getSideCount(true));
       // b.getBoardStatus();
       // b.moveMarbles();

        System.out.println("Starting up ....");
        //Launch GUI Window
        launch(args);

        System.out.println("Finished");
    }
}
