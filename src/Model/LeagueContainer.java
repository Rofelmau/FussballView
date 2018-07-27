package Model;

import java.util.LinkedList;

public class LeagueContainer {
    private static LeagueContainer ourInstance = new LeagueContainer();
    private LinkedList<League> LeagueList = new LinkedList<>();

    public static LeagueContainer getInstance() {
        return ourInstance;
    }

    private LeagueContainer() {

    }

    void addLeague(League league){
        LeagueList.add(league);
    }

    public LinkedList<League> getLeagueList(){
        return this.LeagueList;
    }

    public League findeLeagueByShortCut(String shortCut){
        for(League l: this.getLeagueList()){
            if(l.getShortCut().equals(shortCut)){
                return l;
            }
        }
        return null;
    }
}
