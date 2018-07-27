package Controller;

import Model.League;
import Model.TableEntry;
import Model.Team;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.LinkedList;

class LeagueTable_Controller {

    private LinkedList<Node> nodes = new LinkedList<>();
    private TableView<TableEntry> tableTableView;
    private AnchorPane mainPane;
    private ObservableList<TableEntry> tableViewElements = FXCollections.observableArrayList();

    private void clearElements(AnchorPane pane){
        pane.getChildren().removeAll(nodes);
        nodes.clear();
        tableViewElements.clear();
    }

     LeagueTable_Controller(AnchorPane mainPane, League l) {
        this.mainPane = mainPane;
        try {
            l.loadTable();
        }catch(Exception e){
            e.printStackTrace();
        }


        clearElements(mainPane);


        Text title = new Text("Tabelle der " + l.getName());
        title.setLayoutX(300);
        title.setLayoutY(110);


        tableViewElements.addAll(l.getTable());

        tableTableView = new TableView<>();
        tableTableView.setEditable(true);
        tableTableView.setLayoutX(300);
        tableTableView.setLayoutY(125);
        tableTableView.setPrefWidth(500);


        TableColumn<TableEntry, String> position = new TableColumn<>("");
        position.setCellValueFactory( cell -> new SimpleStringProperty(cell.getValue().getPosition()+""));
        TableColumn<TableEntry, ImageView> icon  = new TableColumn<>("");
        icon.setCellValueFactory(param -> {
            ImageView imgv = new ImageView(new Image(param.getValue().getTeam().getTeamIconUrl(), 20, 20, true, true));
            imgv.setFitHeight(20);
            imgv.setFitWidth(20);
            imgv.setPreserveRatio(true);
            return new SimpleObjectProperty<>(imgv);
        });
        TableColumn<TableEntry, String> name = new TableColumn<>("Name");
        name.setCellValueFactory( cell -> new SimpleStringProperty(cell.getValue().getTeam().getTeamName()));
        TableColumn<TableEntry, Integer> points = new TableColumn<>("Punkte");
        points.setCellValueFactory(new PropertyValueFactory<>("points"));
        TableColumn<TableEntry, Integer> win = new TableColumn<>("Siege");
        points.setCellValueFactory(new PropertyValueFactory<>("won"));
        TableColumn<TableEntry, Integer> draw = new TableColumn<>("Unentschieden");
        points.setCellValueFactory(new PropertyValueFactory<>("draw"));
        TableColumn<TableEntry, Integer> lost = new TableColumn<>("Verloren");
        points.setCellValueFactory(new PropertyValueFactory<>("lost"));
        TableColumn<TableEntry, Integer> goals = new TableColumn<>("Tore");
        points.setCellValueFactory(new PropertyValueFactory<>("goals"));
        TableColumn<TableEntry, Integer> opponentGoals = new TableColumn<>("Gegentore");
        points.setCellValueFactory(new PropertyValueFactory<>("opponentGoals"));


        tableTableView.getColumns().addAll(position, icon, name, points, win, draw, lost, goals, opponentGoals);
        tableTableView.setItems(tableViewElements);


        nodes.add(title);
        nodes.add(tableTableView);
        mainPane.getChildren().addAll(title, tableTableView);


        tableTableView.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> changed()
        );
    }

    private void displayTeam(Team t){
        ImageView imgv = new ImageView(new Image(t.getTeamIconUrl(),100,100,true,true));
        imgv.setFitHeight(100);
        imgv.setFitHeight(100);
        imgv.setPreserveRatio(true);
        imgv.setX(850);
        imgv.setY(100);

        Text name = new Text(t.getTeamName());
        name.setX(1000);
        name.setY(110);

        mainPane.getChildren().addAll(imgv,name);
    }

    private void changed() {
        if (tableTableView.getSelectionModel().getSelectedItem() != null) {
            TableView.TableViewSelectionModel<TableEntry> selectionModel = tableTableView.getSelectionModel();
            ObservableList<TablePosition> selectedCells = selectionModel.getSelectedCells();
            TablePosition tablePosition = selectedCells.get(0);
            Team val = tableViewElements.get(tablePosition.getRow()).getTeam();
            displayTeam(val);
        }
    }
}
