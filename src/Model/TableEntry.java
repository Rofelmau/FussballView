package Model;

import com.google.gson.annotations.SerializedName;

public class TableEntry {

    @SerializedName("draw")
    private int draw;
    @SerializedName("goals")
    private int goals;
    @SerializedName("lost")
    private int lost;
    @SerializedName("matches")
    private int matches;
    @SerializedName("opponentGoals")
    private int opponentGoals;
    @SerializedName("points")
    private int points;
    @SerializedName("won")
    private int won;
    private Team team;
    private int position;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }


    public int getPoints() {
        return points;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
