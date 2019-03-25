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
        //get user input for number of seeds and houses
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the number of houses: ");
        b.numHousesINPUT = keyboard.nextInt();

        System.out.println("Enter the number of seeds per house: ");
        b.numSeedsINPUT = keyboard.nextInt();

        //System.out.println(b.getSideCount(true));
        b.getBoardStatus();
        b.moveMarbles();

        //Launch GUI Window
        launch(args);
    }
}
