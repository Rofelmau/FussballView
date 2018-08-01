package Model;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

public class TeamContainer {
    private static TeamContainer ourInstance = new TeamContainer();
    private LinkedList<Team> TeamList = new LinkedList<>();

    public static TeamContainer getInstance() {
        return ourInstance;
    }

    private TeamContainer() {
    }

    void addTeam(Team team){
        TeamList.add(team);
    }

    public LinkedList<Team> getTeamList(){
        return  this.TeamList;
    }

    public LinkedList<Team> generateTeams(JSONArray array){
        LinkedList<Team> teamList = new LinkedList<>();
        Gson mGson = new Gson();
        for(int i = 0; i < array.length(); i++){
            Team t;
            if(!teamIsCreated(array.getJSONObject(i))) {
                t = mGson.fromJson(array.get(i).toString(), Team.class);

                Runnable task = t::setIcon;
                Thread thread = new Thread(task);
                thread.setDaemon(true);
                thread.start();

            }else{
                t = findTeamById(array.getJSONObject(i).getInt("TeamId"));
            }
            teamList.add(t);

        }
        return teamList;
    }

    private boolean teamIsCreated(JSONObject teamObject){
        for (Team team: this.getTeamList()) {
            if(team.getTeamId() == teamObject.getInt("TeamId")){
                return true;
            }
        }
        return false;
    }

    public Team findTeamById(int teamId){
        for (Team team: this.getTeamList()) {
            if (team.getTeamId() == teamId) {
                return team;
            }
        }
        return null;
    }
}
