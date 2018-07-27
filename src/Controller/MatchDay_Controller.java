package Controller;

import Model.*;
import Utility.NetworkConnection;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.LinkedList;

class MatchDay_Controller {

    private LinkedList<Node> nodes = new LinkedList<>();
    private TableView<Match> matchDayTableView;
    private AnchorPane mainPane;
    private ObservableList<Match> tableViewElements = FXCollections.observableArrayList();

    MatchDay_Controller(AnchorPane pane, League league) {
        this.mainPane = pane;
        try{
            JSONArray result =  NetworkConnection.getInstance().getResultAsJSONArray(new URL("https://www.openligadb.de/api/getmatchdata/" + league.getShortCut()));
           // Gson mGson = new Gson();
            generateMatchDay(result, league);
            generateMatchDay(2,league);
        }catch(Exception e){
            e.printStackTrace();
        }
        displayMatchDay(1,league);
    }

    private void generateMatchDay(JSONArray result, League league){
            int matchdayID = result.getJSONObject(0).getJSONObject("Group").getInt("GroupOrderID");
            MatchDay mDay = new MatchDay(matchdayID);
            for (int i = 0; i < result.length(); i++) {
                // Match match = mGson.fromJson(result.get(i).toString(), Match.class);
                JSONObject currentMatch = result.getJSONObject(i);
                Match match = new Match(currentMatch.getInt("MatchID"), currentMatch.getBoolean("MatchIsFinished"), TeamContainer.getInstance().findTeamById(currentMatch.getJSONObject("Team1").getInt("TeamId")), TeamContainer.getInstance().findTeamById(currentMatch.getJSONObject("Team2").getInt("TeamId")));
                LinkedList<Match> matches = mDay.getMatches();
                matches.add(match);
                mDay.setMatches(matches);
                //System.out.println(match.getTeamGuest().getTeamName());

            }
            LinkedList<MatchDay> matchDays = league.getMatchDays();
            matchDays.add(mDay);
            league.setMatchDays(matchDays);
    }

    private void generateMatchDay(int matchDayID, League league){
        if(matchDayID > 0 && matchDayID < ((league.getTeamList().size()-1)*2) && !league.matchDayExists(matchDayID)){
            try {
                JSONArray result = NetworkConnection.getInstance().getResultAsJSONArray(new URL("https://www.openligadb.de/api/getmatchdata/" + league.getShortCut() + "/" + league.getYear() + "/" + matchDayID));
                generateMatchDay(result, league);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private void displayMatchDay(int matchDayId, League league){
        mainPane.getChildren().removeAll(nodes);

        MatchDay matchday = league.getMatchDay(matchDayId);
        assert matchday != null;

        tableViewElements.addAll(matchday.getMatches());

        matchDayTableView = new TableView<>();
        matchDayTableView.setEditable(true);
        matchDayTableView.setLayoutX(900);
        matchDayTableView.setLayoutY(100);
        matchDayTableView.setPrefWidth(350);


     /*   TableColumn<TableEntry, String> position = new TableColumn<>("");
        position.setCellValueFactory( cell -> new SimpleStringProperty(cell.getValue().getPosition()+""));
        TableColumn<TableEntry, ImageView> icon  = new TableColumn<>("");
        icon.setCellValueFactory(param -> {
            ImageView imgv = new ImageView(new Image(param.getValue().getTeam().getTeamIconUrl(), 20, 20, true, true));
            imgv.setFitHeight(20);
            imgv.setFitWidth(20);
            imgv.setPreserveRatio(true);
            return new SimpleObjectProperty<>(imgv);
        }); */
        TableColumn<Match, String> homeTeam = new TableColumn<>("");
        homeTeam.setCellValueFactory( cell -> new SimpleStringProperty(cell.getValue().getTeamHome().getTeamName()));
        TableColumn<Match, String> space = new TableColumn<>("");
        space.setCellValueFactory( cell -> new SimpleStringProperty(" : "));
        TableColumn<Match, String> guestTeam = new TableColumn<>("");
        guestTeam.setCellValueFactory( cell -> new SimpleStringProperty(cell.getValue().getTeamHome().getTeamName()));



        matchDayTableView.getColumns().addAll(homeTeam, space, guestTeam);
        matchDayTableView.setItems(tableViewElements);



        nodes.add(matchDayTableView);
        mainPane.getChildren().addAll(matchDayTableView);
    }
}
