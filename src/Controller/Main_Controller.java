package Controller;

import Model.League;
import Model.LeagueContainer;
import Utility.MainButtonDesign;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;


public class Main_Controller implements Initializable, MainButtonDesign {
    @FXML
    private AnchorPane mainPane;
    private LinkedList<Button> buttons = new LinkedList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(()->{
            for( League l:LeagueContainer.getInstance().getLeagueList()) {
                createButton(l);
            }
        });
    }


    private void createButton(League l){
        Button b = new Button(l.getName());
        b.setLayoutX(100);
        b.setLayoutY(100+buttons.size()*50);
        buttons.add(b);
        mainPane.getChildren().add(b);
        setDefaultDesign(b);
        b.setOnAction(e -> showLeague(l));
    }

    private void showLeague(League l) {
        new LeagueTable_Controller(mainPane,l);
        new MatchDay_Controller(mainPane,l);
    }



}
