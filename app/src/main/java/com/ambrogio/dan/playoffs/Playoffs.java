package com.ambrogio.dan.playoffs;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Dan on 4/13/2015.
 */
public class Playoffs {

    private static final int PURE_RANDOM = 0;
    private static final int WEIGHTED_RANDOM = 1;

    // Full results
    private PlayoffRound westq1;
    private PlayoffRound westq2;
    private PlayoffRound westq3;
    private PlayoffRound westq4;
    private PlayoffRound eastq1;
    private PlayoffRound eastq2;
    private PlayoffRound eastq3;
    private PlayoffRound eastq4;
    private PlayoffRound wests1;
    private PlayoffRound wests2;
    private PlayoffRound easts1;
    private PlayoffRound easts2;
    private PlayoffRound westFinal;
    private PlayoffRound eastFinal;
    private PlayoffRound finals;

    /**
     * Receives a sorted list of 16 teams and creates playoff brackets from there
     * @param teams
     */
    public Playoffs(ArrayList<Team> teams){
        // Split the teams into two groups
        ArrayList<Team> westConference = new ArrayList<>();
        ArrayList<Team> eastConference = new ArrayList<>();

        for (int i = 0; i < 16; i = i + 2){
            westConference.add(teams.get(i));
            eastConference.add(teams.get(i+1));
        }
        Log.e("sizeofconferencewest", "West conf size = " + westConference.size());
        Log.e("sizeofconferenceeast", "East conf size = " + eastConference.size());
        runSim(westConference, eastConference);

    }

    public String getCupWinner(){
        return "The " + finals.getWinningTeam().getName() + " won the cup!";
    }

    private void runSim(ArrayList<Team> westConference, ArrayList<Team> eastConference){
        // Both conferences are still sorted; pair off highest and lowest
        // Quarterfinals
        // 1 vs 8, 2 vs 7, 3 vs 6, 4 vs 5
        // West
        setWestq1(new PlayoffRound(westConference.get(0), westConference.get(7), 0, WEIGHTED_RANDOM));
        setWestq2(new PlayoffRound(westConference.get(1), westConference.get(6), 0, WEIGHTED_RANDOM));
        setWestq3(new PlayoffRound(westConference.get(2), westConference.get(5), 0, WEIGHTED_RANDOM));
        setWestq4(new PlayoffRound(westConference.get(3), westConference.get(4), 0, WEIGHTED_RANDOM));
        // East
        setEastq1(new PlayoffRound(eastConference.get(0), eastConference.get(7), 0, WEIGHTED_RANDOM));
        setEastq2(new PlayoffRound(eastConference.get(1), eastConference.get(6), 0, WEIGHTED_RANDOM));
        setEastq3(new PlayoffRound(eastConference.get(2), eastConference.get(5), 0, WEIGHTED_RANDOM));
        setEastq4(new PlayoffRound(eastConference.get(3), eastConference.get(4), 0, WEIGHTED_RANDOM));

        // Semifinals
        // 1 vs 2, 3 vs 4
        // West
        setWests1(new PlayoffRound(getWestq1().getWinningTeam(), getWestq2().getWinningTeam(), 1, WEIGHTED_RANDOM));
        setWests2(new PlayoffRound(getWestq3().getWinningTeam(), getWestq4().getWinningTeam(), 1, WEIGHTED_RANDOM));
        // East
        setEasts1(new PlayoffRound(getEastq1().getWinningTeam(), getEastq2().getWinningTeam(), 1, WEIGHTED_RANDOM));
        setEasts2(new PlayoffRound(getEastq3().getWinningTeam(), getEastq4().getWinningTeam(), 1, WEIGHTED_RANDOM));

        // Conference Finals
        // 1 vs 2
        // West
        setWestFinal(new PlayoffRound(getWests1().getWinningTeam(), getWests2().getWinningTeam(), 2, WEIGHTED_RANDOM));
        // East
        setEastFinal(new PlayoffRound(getEasts1().getWinningTeam(), getEasts2().getWinningTeam(), 2, WEIGHTED_RANDOM));

        // Stanley Cup Finals
        setFinals(new PlayoffRound(getWestFinal().getWinningTeam(), getEastFinal().getWinningTeam(),
                3, WEIGHTED_RANDOM));

        // Return the teams in a specific order
        // 1 - Cup winner
        // 2 - Lost in finals
    }

    public PlayoffRound getWestq1() {
        return westq1;
    }

    public void setWestq1(PlayoffRound westq1) {
        this.westq1 = westq1;
    }

    public PlayoffRound getWestq2() {
        return westq2;
    }

    public void setWestq2(PlayoffRound westq2) {
        this.westq2 = westq2;
    }

    public PlayoffRound getWestq3() {
        return westq3;
    }

    public void setWestq3(PlayoffRound westq3) {
        this.westq3 = westq3;
    }

    public PlayoffRound getWestq4() {
        return westq4;
    }

    public void setWestq4(PlayoffRound westq4) {
        this.westq4 = westq4;
    }

    public PlayoffRound getEastq1() {
        return eastq1;
    }

    public void setEastq1(PlayoffRound eastq1) {
        this.eastq1 = eastq1;
    }

    public PlayoffRound getEastq2() {
        return eastq2;
    }

    public void setEastq2(PlayoffRound eastq2) {
        this.eastq2 = eastq2;
    }

    public PlayoffRound getEastq3() {
        return eastq3;
    }

    public void setEastq3(PlayoffRound eastq3) {
        this.eastq3 = eastq3;
    }

    public PlayoffRound getEastq4() {
        return eastq4;
    }

    public void setEastq4(PlayoffRound eastq4) {
        this.eastq4 = eastq4;
    }

    public PlayoffRound getWests1() {
        return wests1;
    }

    public void setWests1(PlayoffRound wests1) {
        this.wests1 = wests1;
    }

    public PlayoffRound getWests2() {
        return wests2;
    }

    public void setWests2(PlayoffRound wests2) {
        this.wests2 = wests2;
    }

    public PlayoffRound getEasts1() {
        return easts1;
    }

    public void setEasts1(PlayoffRound easts1) {
        this.easts1 = easts1;
    }

    public PlayoffRound getEasts2() {
        return easts2;
    }

    public void setEasts2(PlayoffRound easts2) {
        this.easts2 = easts2;
    }

    public PlayoffRound getWestFinal() {
        return westFinal;
    }

    public void setWestFinal(PlayoffRound westFinal) {
        this.westFinal = westFinal;
    }

    public PlayoffRound getEastFinal() {
        return eastFinal;
    }

    public void setEastFinal(PlayoffRound eastFinal) {
        this.eastFinal = eastFinal;
    }

    public PlayoffRound getFinals() {
        return finals;
    }

    public void setFinals(PlayoffRound finals) {
        this.finals = finals;
    }
}
