package Model;

import java.util.LinkedList;

public class MatchDay {
    private int matchDay;
    private LinkedList<Match> matches = new LinkedList<>();

    public MatchDay(int matchDay){
        this.matchDay = matchDay;
    }

    public int getMatchDay() {
        return matchDay;
    }

    public LinkedList<Match> getMatches() {
        return matches;
    }

    public void setMatches(LinkedList<Match> matches) {
        this.matches = matches;
    }
}
