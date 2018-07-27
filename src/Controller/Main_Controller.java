package Controller;

import Model.League;
import Model.LeagueContainer;
import Model.TableEntry;
import Utility.MainButtonDesign;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class Main_Controller implements Initializable, MainButtonDesign {
    @FXML
    private AnchorPane mainPane;
    private TableView<TableEntry> listOfTeams;

    private LinkedList<Button> buttons = new LinkedList<>();
    private LinkedList<Node> nodes = new LinkedList<>();

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
        try {
            l.loadTable();
        }catch(Exception e){
            e.printStackTrace();
        }
        mainPane.getChildren().removeAll(nodes);
        nodes.clear();
        AtomicInteger pos = new AtomicInteger(1);

        Text title = new Text("Tabelle der " + l.getName());
        title.setLayoutX(300);
        title.setLayoutY(110);

        ObservableList<TableEntry> listViewItems = FXCollections.observableArrayList();
        listViewItems.addAll(l.getTable());

        listOfTeams = new TableView<>();
        listOfTeams.setEditable(true);

        listOfTeams.setLayoutX(300);
        listOfTeams.setLayoutY(125);
        TableColumn<TableEntry, String> position //
                = new TableColumn<TableEntry, String>("Position");
        position.setCellValueFactory( cell -> new SimpleStringProperty(cell.getValue().getPosition()+""));
        TableColumn<TableEntry, ImageView> icon //
                = new TableColumn<TableEntry, ImageView>("");
        icon.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TableEntry , ImageView>, ObservableValue<ImageView>>() {

            @Override
            public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<TableEntry , ImageView> param) {
                return new SimpleObjectProperty<>(new ImageView(new Image(param.getValue().getTeam().getTeamIconUrl(), 20, 20, true, true)));

            }
        });

        TableColumn<TableEntry, String> name //
                = new TableColumn<TableEntry, String>("Name");
        name.setCellValueFactory( cell -> new SimpleStringProperty(cell.getValue().getTeam().getTeamName()));
        TableColumn<TableEntry, String> points //
                = new TableColumn<TableEntry, String>("Punkte");
        points.setCellValueFactory(new PropertyValueFactory<>("points"));
        listOfTeams.getColumns().addAll(position,icon,name,points);

        listOfTeams.setItems(listViewItems);

        nodes.add(title);
        nodes.add(listOfTeams);
        mainPane.getChildren().addAll(title,listOfTeams);
    }

}
