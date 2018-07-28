package Controller;

import Model.*;
import Utility.NetworkConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.LinkedList;

class MatchDay_Controller {

    private LinkedList<Node> nodes = new LinkedList<>();
    private TableView<Match> matchDayTableView;
    private AnchorPane mainPane;
    private ObservableList<Match> tableViewElements = FXCollections.observableArrayList();
    private static MatchDay_Controller instance = new MatchDay_Controller();

    public static MatchDay_Controller getInstance(){
        return instance;
    }


    private MatchDay_Controller() {
    }

    void start(AnchorPane pane, League league){
        this.mainPane = pane;
        try{
            JSONArray result =  NetworkConnection.getInstance().getResultAsJSONArray(new URL("https://www.openligadb.de/api/getmatchdata/" + league.getShortCut()));
            // Gson mGson = new Gson();
            int matchDayId = generateMatchDay(result, league);
            generateMatchDay(matchDayId+1,league);
            generateMatchDay(matchDayId-1,league);
            displayMatchDay(matchDayId,league);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private int generateMatchDay(JSONArray result, League league){
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
            return matchdayID;
    }

    private void generateMatchDay(int matchDayID, League league){
        if(matchDayID > 0 && matchDayID <= ((league.getTeamList().size()-1)*2) && !league.matchDayExists(matchDayID)){
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
        nodes.clear();
        tableViewElements.clear();

        MatchDay matchday = league.getMatchDay(matchDayId);
        if(matchday != null){
            tableViewElements.addAll(matchday.getMatches());
        }

        matchDayTableView = new TableView<>();
        matchDayTableView.setEditable(true);
        matchDayTableView.setLayoutX(900);
        matchDayTableView.setLayoutY(130);
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
        space.setCellValueFactory( cell -> new SimpleStringProperty("  : "));
        TableColumn<Match, String> guestTeam = new TableColumn<>("");
        guestTeam.setCellValueFactory( cell -> new SimpleStringProperty(cell.getValue().getTeamGuest().getTeamName()));



        matchDayTableView.getColumns().addAll(homeTeam, space, guestTeam);
        matchDayTableView.setItems(tableViewElements);

        Text txt = new Text(matchDayId + ". Spieltag");
        txt.setX(950);
        txt.setY(120);
        nodes.add(txt);
        nodes.add(matchDayTableView);
        mainPane.getChildren().addAll(matchDayTableView,txt);

        if(matchDayId > 1){
            Button btn = new Button("<--");
            btn.setLayoutX(900);
            btn.setLayoutY(100);
            btn.setOnAction(e -> {
                displayMatchDay(matchDayId-1,league);
                generateMatchDay(matchDayId-2,league);
            });
            nodes.add(btn);
            mainPane.getChildren().add(btn);
        }
        if(matchDayId < ((league.getTeamList().size()-1)*2)){
            Button btn = new Button("-->");
            btn.setLayoutX(1050);
            btn.setLayoutY(100);
            btn.setOnAction(e -> {
                displayMatchDay(matchDayId+1,league);
                generateMatchDay(matchDayId+2,league);
            });
            nodes.add(btn);
            mainPane.getChildren().add(btn);
        }
    }
}
