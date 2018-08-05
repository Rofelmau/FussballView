package Controller;

import Model.League;
import Model.LeagueContainer;
import Utility.MainButtonDesign;
import com.sun.prism.paint.Color;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;


public class Main_Controller implements Initializable, MainButtonDesign {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private ScrollPane scrollPane;
    private LinkedList<Button> buttons = new LinkedList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(()->{
            scrollPane.setStyle("-fx-background-color: #FFFFFF");

            for( League l : League.values() ){
                createButton(l);
            }
            mainPane.prefHeightProperty().bind(mainPane.getScene().getWindow().heightProperty());
            Line l1 = new Line(280,0,280,700);
            l1.setStrokeWidth(4);
            l1.endYProperty().bind(mainPane.heightProperty());
            //scrollPane.hmaxProperty()
            mainPane.getChildren().add(l1);
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
        LeagueTable_Controller.getInstance().start(mainPane,l);
        MatchDay_Controller.getInstance().start(mainPane,l);
    }



}
