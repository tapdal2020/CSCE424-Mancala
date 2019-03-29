package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("MANCALA");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }


    public static void main(String[] args){
       //initialize objects
        Controller b = new Controller();
        b.getUserInputs();
        b.assignRandomMarbles();
        b.setTimeLimit();
        b.gameType();

        //System.out.println(b.getSideCount(true));
       // b.getBoardStatus();
       // b.moveMarbles();
        b.getBoardStatus();

        //Launch GUI Window
        launch(args);
    }
}
