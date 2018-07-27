package Model;

import Utility.NetworkConnection;
import com.google.gson.Gson;
import org.json.JSONArray;

import java.net.URL;
import java.util.LinkedList;

public enum League {
    bl1("1. Bundesliga","bl1","/2018"),
    bl2("2. Bundesliga","bl2","/2018"),
    bl3("3. Bundesliga","bl3","/2018"),
    dfb2018("DFB Pokal","dfb2018","/2018"),
    Lig("Ligue 1","Lig","/2018"),
    seriea("Seria A","seriea","/2017");

    private LinkedList<Team> TeamList = new LinkedList<>();
    private String name;
    private String shortCut;
    private String year;
    private LinkedList<TableEntry> table = new LinkedList<>();
    private LinkedList<MatchDay> matchDays = new LinkedList<>();


    League(){
        LeagueContainer.getInstance().addLeague(this);
    }

    League(String name, String shortcut, String year){
        this.name = name;
        this.shortCut = shortcut;
        this.year = year;
        LeagueContainer.getInstance().addLeague(this);
    }

    public String getName(){
        return this.name;
    }
    public String getShortCut(){
        return this.shortCut;
    }
    public String getYear() { return this.year; }

    public LinkedList<Team> getTeamList(){
        return this.TeamList;
    }
    public void setTeamList(LinkedList<Team> teams){ this.TeamList = teams; }


    public LinkedList<TableEntry> getTable() {
        return table;
    }

    public void setTable(LinkedList<TableEntry> table) {
        this.table = table;
    }

    public void loadTable() throws Exception{
        URL url = new URL( "https://www.openligadb.de/api/getbltable/" + this.getShortCut() + this.getYear());
        JSONArray array = NetworkConnection.getInstance().getResultAsJSONArray(url);
        Gson mGson = new Gson();
        table.clear();
        for(int i = 0; i < array.length(); i++){
            TableEntry tabEntry = mGson.fromJson(array.get(i).toString(), TableEntry.class);
            if(array.getJSONObject(i).getInt("TeamInfoId") != 175)
                tabEntry.setTeam(TeamContainer.getInstance().findTeamById(array.getJSONObject(i).getInt("TeamInfoId")));
            else
                tabEntry.setTeam(TeamContainer.getInstance().findTeamById(123));
            tabEntry.setPosition(i+1);
            this.table.add(tabEntry);
        }

    }

    public LinkedList<MatchDay> getMatchDays() {
        return matchDays;
    }

    public void setMatchDays(LinkedList<MatchDay> matchDays) {
        this.matchDays = matchDays;
    }

    public boolean matchDayExists(int matchDayID){
        for(MatchDay mDay: this.getMatchDays()){
            if(mDay.getMatchDay() == matchDayID)
                return true;
        }
        return false;
    }

    public MatchDay getMatchDay(int matchdayId){
        for(MatchDay mDay: this.getMatchDays()){
            if(mDay.getMatchDay() == matchdayId)
                return mDay;
        }
        return null;
    }
}
