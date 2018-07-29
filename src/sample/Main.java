package sample;

import Model.League;
import Model.LeagueContainer;
import Model.Team;
import Model.TeamContainer;
import Controller.Main_Controller;
import Utility.NetworkConnection;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.LinkedList;

import org.json.JSONArray;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        for( League l : League.values() ){
            URL url = new URL( "https://www.openligadb.de/api/getavailableteams/" + l.getShortCut() + l.getYear());
            JSONArray jsonArray = NetworkConnection.getInstance().getResultAsJSONArray(url);
            LinkedList<Team> teams = TeamContainer.getInstance().generateTeams(jsonArray);
            l.setTeamList(teams);
        }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Main.fxml"));

        Main_Controller controller = new Main_Controller();
        loader.setController(controller);
        Parent root = loader.load();

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
