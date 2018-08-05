package Controller;

import Model.Team;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.LinkedList;

public class TeamView_Controller {
    private static TeamView_Controller ourInstance = new TeamView_Controller();
    private LinkedList<Node> teamNodes = new LinkedList<>();

    public static TeamView_Controller getInstance() {
        return ourInstance;
    }

    private TeamView_Controller() {
    }

    void start(Team team, AnchorPane mainPane){
        mainPane.getChildren().removeAll(teamNodes);
        teamNodes.clear();

        Text name = new Text(team.getTeamName());
        name.setFont(Font.font ("Arial", 22));
        name.setX(300);
        name.setY(590);

        ImageView imgv = new ImageView(new Image(team.getTeamIconUrl(),150,150,true,true));
        imgv.setFitHeight(150);
        imgv.setFitHeight(150);
        imgv.setPreserveRatio(true);
        imgv.setX(300);
        imgv.setY(600);

        mainPane.getChildren().addAll(imgv,name);
        teamNodes.add(imgv);
        teamNodes.add(name);
    }
}
