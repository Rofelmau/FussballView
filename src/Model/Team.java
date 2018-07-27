package Model;

import com.google.gson.annotations.SerializedName;

import java.net.URL;

public class Team {

    @SerializedName("ShortName")
    private String ShortName;
    @SerializedName("TeamGroupName")
    private String TeamGroupName;
    @SerializedName("TeamIconUrl")
    private String TeamIconUrl;
    @SerializedName("TeamId")
    private int TeamId;
    @SerializedName("TeamName")
    private String TeamName;


    public Team(){
        TeamContainer.getInstance().addTeam(this);
    }

    public String getTeamIconUrl() {
        return TeamIconUrl;
    }

    public void setTeamIconUrl(String teamIconUrl) {
        TeamIconUrl = teamIconUrl;
    }

    public String getTeamGroupName() {
        return TeamGroupName;
    }

    public void setTeamGroupName(String teamGroupName) {
        TeamGroupName = teamGroupName;
    }

    public String getShortName() {
        return ShortName;
    }

    public void setShortName(String shortName) {
        ShortName = shortName;
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public int getTeamId() {
        return TeamId;
    }

    public void setTeamId(int teamId) {
        TeamId = teamId;
    }
}
