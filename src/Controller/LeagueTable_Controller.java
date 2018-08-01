package Controller;

import Model.League;
import Model.TableEntry;
import Model.Team;

import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javax.sound.sampled.Line;
import java.util.LinkedList;

class LeagueTable_Controller {

    private LinkedList<Node> nodes = new LinkedList<>();
    private LinkedList<Node> teamNodes = new LinkedList<>();
    private TableView<TableEntry> tableTableView;
    private AnchorPane mainPane;
    private ObservableList<TableEntry> tableViewElements = FXCollections.observableArrayList();
    private static LeagueTable_Controller instance = new LeagueTable_Controller();

    static LeagueTable_Controller getInstance(){
        return instance;
    }

    private void clearElements(AnchorPane pane){
        pane.getChildren().removeAll(nodes);
        nodes.clear();
        pane.getChildren().removeAll(teamNodes);
        teamNodes.clear();
        tableViewElements.clear();
    }

    void start(AnchorPane mainPane, League l){
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
        tableTableView.setPrefWidth(420);


        TableColumn<TableEntry, String> position = new TableColumn<>("");
        position.setCellValueFactory( cell -> new SimpleStringProperty(cell.getValue().getPosition()+""));
        TableColumn<TableEntry, ImageView> icon  = new TableColumn<>("");
        icon.setCellValueFactory(param -> {
            ImageView imgv = new ImageView();
            if(!param.getValue().getTeam().getTeamIconUrl().isEmpty()) {
                imgv.setImage(param.getValue().getTeam().getSmaleIcon());
                imgv.setFitHeight(20);
                imgv.setFitWidth(20);
                imgv.setPreserveRatio(true);

            }
            return new SimpleObjectProperty<>(imgv);
        });
        TableColumn<TableEntry, String> name = new TableColumn<>("Name");
        name.setCellValueFactory( cell -> new SimpleStringProperty(cell.getValue().getTeam().getTeamName()));
        TableColumn<TableEntry, String> points = new TableColumn<>("P");
        points.setCellValueFactory( cell -> new SimpleStringProperty(cell.getValue().getPoints()+""));
        TableColumn<TableEntry, String> win = new TableColumn<>("G");
        win.setCellValueFactory(cell -> new SimpleStringProperty(""+cell.getValue().getWon()));
        TableColumn<TableEntry, String> draw = new TableColumn<>("U");
        draw.setCellValueFactory(cell -> new SimpleStringProperty(""+cell.getValue().getDraw()));
        TableColumn<TableEntry, String> lost = new TableColumn<>("V");
        lost.setCellValueFactory(cell -> new SimpleStringProperty(""+cell.getValue().getLost()));
        TableColumn<TableEntry, String> goals = new TableColumn<>("Tore");
        goals.setCellValueFactory(cell -> new SimpleStringProperty(""+cell.getValue().getGoals()+":"+cell.getValue().getOpponentGoals()));
        TableColumn<TableEntry, String> dif = new TableColumn<>("Dif");
        dif.setCellValueFactory(cell -> new SimpleStringProperty(""+(cell.getValue().getGoals()-cell.getValue().getOpponentGoals())));
        goals.setStyle("-fx-alignment: CENTER");
        points.setStyle("-fx-alignment: CENTER");
        win.setStyle("-fx-alignment: CENTER");
        draw.setStyle("-fx-alignment: CENTER");
        lost.setStyle("-fx-alignment: CENTER");
        dif.setStyle("-fx-alignment: CENTER");

        tableTableView.getColumns().addAll(position, icon, name, points, win, draw, lost, goals, dif);
        for(TableColumn c: tableTableView.getColumns()){
            c.setSortable(false);
        }
        tableTableView.setItems(tableViewElements);


        nodes.add(title);
        nodes.add(tableTableView);
        mainPane.getChildren().addAll(title, tableTableView);


        tableTableView.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> changed()
        );
    }

    private LeagueTable_Controller() {

    }


    private void displayTeam(Team t){
        mainPane.getChildren().removeAll(teamNodes);
        teamNodes.clear();

        Text name = new Text(t.getTeamName());
        name.setFont(Font.font ("Arial", 22));
        name.setX(300);
        name.setY(565);

        ImageView imgv = new ImageView(new Image(t.getTeamIconUrl(),100,100,true,true));
        imgv.setFitHeight(100);
        imgv.setFitHeight(100);
        imgv.setPreserveRatio(true);
        imgv.setX(300);
        imgv.setY(575);

        //Line line1 = new Line(290, 0, 290, 400);

        mainPane.getChildren().addAll(imgv,name);
        teamNodes.add(imgv);
        teamNodes.add(name);

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
