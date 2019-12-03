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
import java.io.IOException;
import java.util.Scanner;
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        new  mainScene();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("MANCALA");
        Scene scene = new Scene(root, 900, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args){
        //initialize objects
        Controller b = new Controller();
        Server server = new Server();
        Client client = new Client();
        b.getUserInputs();
        b.assignRandomMarbles();
        b.setTimeLimit();
        b.twoPlayerGame();
        //b.gameType();
        BoardGUI board = new BoardGUI(b.numHousesINPUT, b.numSeedsINPUT);
        System.out.println(b.getSideCount(true));
        b.getBoardStatus();
        //b.moveMarbles();
        System.out.println("Finished");
    }
}

/*
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


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("MANCALA");
        Scene scene = new Scene(root, 900, 500);
        primaryStage.setScene(scene);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(30,80,50,80));
        grid.setVgap(9);
        grid.setHgap(9);

        scene.setRoot(grid);

        final TextField houseInputTEXT = new TextField();
        houseInputTEXT.setPrefWidth(75);
        houseInputTEXT.setPromptText("Add Points");
        houseInputTEXT.setAlignment(Pos.CENTER);

        primaryStage.show();
    }


    public static void main(String[] args){

        //System.out.println(b.getSideCount(true));
        // b.getBoardStatus();
        // b.moveMarbles();

        //Launch GUI Window
        launch(args);
    }
}

 */