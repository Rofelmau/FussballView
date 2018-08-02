package Model;

import java.util.LinkedList;

public class MatchDay {
    private int matchDay;
    private String name;
    private LinkedList<Match> matches = new LinkedList<>();

    public MatchDay(int matchDay, String matchdayName){
        this.name = matchdayName;
        this.matchDay = matchDay;
    }

    int getMatchDay() {
        return matchDay;
    }

    public LinkedList<Match> getMatches() {
        return matches;
    }

    public void setMatches(LinkedList<Match> matches) {
        this.matches = matches;
    }

    public String getName() {
        return name;
    }
}
