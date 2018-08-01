package Controller;

import Model.*;
import Utility.NetworkConnection;
import View.switchPageButtonDesign;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.LinkedList;

class MatchDay_Controller implements switchPageButtonDesign {

    private LinkedList<Node> nodes = new LinkedList<>();
    private TableView<Match> matchDayTableView;
    private AnchorPane mainPane;
    private ObservableList<Match> tableViewElements = FXCollections.observableArrayList();
    private static MatchDay_Controller instance = new MatchDay_Controller();

    static MatchDay_Controller getInstance(){
        return instance;
    }


    private MatchDay_Controller() {
    }

    void start(AnchorPane pane, League league){
        this.mainPane = pane;
        try{
            JSONArray result =  NetworkConnection.getInstance().getResultAsJSONArray(new URL("https://www.openligadb.de/api/getmatchdata/" + league.getShortCut()));
            int matchDayId = generateMatchDay(result, league);
            generateMatchDay(matchDayId+1,league);
            generateMatchDay(matchDayId-1,league);
            displayMatchDay(matchDayId,league);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private int generateMatchDay(JSONArray result, League league){
        if(result.length() > 0) {
            int matchdayID = result.getJSONObject(0).getJSONObject("Group").getInt("GroupOrderID");
            MatchDay mDay = new MatchDay(matchdayID);
            for (int i = 0; i < result.length(); i++) {
                JSONObject currentMatch = result.getJSONObject(i);
                int goalOne = 0;
                int goalTwo = 0;
                int resultLength = currentMatch.getJSONArray("MatchResults").length();
                if(resultLength > 0){
                    goalOne = currentMatch.getJSONArray("MatchResults").getJSONObject(resultLength-1).getInt("PointsTeam1");
                    goalTwo = currentMatch.getJSONArray("MatchResults").getJSONObject(resultLength-1).getInt("PointsTeam2");

                }
                Match match = new Match(currentMatch.getInt("MatchID"), currentMatch.getBoolean("MatchIsFinished"), TeamContainer.getInstance().findTeamById(currentMatch.getJSONObject("Team1").getInt("TeamId")), TeamContainer.getInstance().findTeamById(currentMatch.getJSONObject("Team2").getInt("TeamId")),goalOne,goalTwo);
                LinkedList<Match> matches = mDay.getMatches();
                matches.add(match);
                mDay.setMatches(matches);
            }
            LinkedList<MatchDay> matchDays = league.getMatchDays();
            matchDays.add(mDay);
            league.setMatchDays(matchDays);
            return matchdayID;
        }else
            return 0;
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
        matchDayTableView.setLayoutX(750);
        matchDayTableView.setLayoutY(125);
        matchDayTableView.setPrefWidth(425);





        TableColumn<Match, ImageView> iconTeamHome  = new TableColumn<>("");
        iconTeamHome.setCellValueFactory(param -> {
            ImageView imgv = new ImageView();
            if(!param.getValue().getTeamHome().getTeamIconUrl().isEmpty()) {
                imgv.setImage(param.getValue().getTeamHome().getSmaleIcon());
                imgv.setFitHeight(20);
                imgv.setFitWidth(20);
                imgv.setPreserveRatio(true);

            }
            return new SimpleObjectProperty<>(imgv);
        });
        TableColumn<Match, String> homeTeam = new TableColumn<>("Heim");
        homeTeam.setCellValueFactory( cell -> new SimpleStringProperty(cell.getValue().getTeamHome().getTeamName()));
        homeTeam.setMinWidth(158);
        TableColumn<Match, String> result = new TableColumn<>("");
        result.setCellValueFactory( cell ->{
            result.setStyle("-fx-alignment: CENTER;");
            if(cell.getValue().isFinished())
                return new SimpleStringProperty("" + cell.getValue().getGoalTeam1() + ":" + cell.getValue().getGoalTeam2() + "");
            else
                return new SimpleStringProperty("time");
        });

        TableColumn<Match, String> guestTeam = new TableColumn<>("Gast");
        guestTeam.setCellValueFactory( cell -> new SimpleStringProperty(cell.getValue().getTeamGuest().getTeamName()));
        guestTeam.setMinWidth(158);
        guestTeam.setStyle("-fx-alignment: CENTER-RIGHT");
        TableColumn<Match, ImageView> iconTeamGuest  = new TableColumn<>("");
        iconTeamGuest.setCellValueFactory(param -> {
            ImageView imgv = new ImageView();
            if(!param.getValue().getTeamGuest().getTeamIconUrl().isEmpty()) {
                imgv.setImage(param.getValue().getTeamGuest().getSmaleIcon());
                imgv.setFitHeight(20);
                imgv.setFitWidth(20);
                imgv.setPreserveRatio(true);

            }
            return new SimpleObjectProperty<>(imgv);
        });



        matchDayTableView.getColumns().addAll(iconTeamHome, homeTeam, result, guestTeam, iconTeamGuest);
        for(TableColumn c:matchDayTableView.getColumns()){
            c.setSortable(false);
        }
        matchDayTableView.setItems(tableViewElements);

        Text txt = new Text(matchDayId + ". Spieltag");
        txt.setX(800);
        txt.setY(120);
        nodes.add(txt);
        nodes.add(matchDayTableView);
        mainPane.getChildren().addAll(matchDayTableView,txt);

        if(matchDayId > 1){
            Button btn = new Button("<--");
            btn.setLayoutX(750);
            btn.setLayoutY(100);
            btn.setOnAction(e -> {
                displayMatchDay(matchDayId-1,league);
                generateMatchDay(matchDayId-2,league);
            });
            setDefaultDesign(btn);
            nodes.add(btn);
            mainPane.getChildren().add(btn);
        }
        if(matchDayId < ((league.getTeamList().size()-1)*2)){
            Button btn = new Button("-->");
            btn.setLayoutX(900);
            btn.setLayoutY(100);
            btn.setOnAction(e -> {
                displayMatchDay(matchDayId+1,league);
                generateMatchDay(matchDayId+2,league);
            });
            setDefaultDesign(btn);
            nodes.add(btn);
            mainPane.getChildren().add(btn);
        }
    }
}
