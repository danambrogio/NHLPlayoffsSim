package com.ambrogio.dan.playoffs;

import java.util.Random;

/**
 * Created by Dan on 4/13/2015.
 */
public class PlayoffRound {

    private Team lowSeed;
    private Team highSeed;
    private int winner;

    private static Random rand;
    private static final double RANDOMNESS_FACTOR = 0.1;

    /**
     *
     * @param lowSeed
     * @param highSeed
     * @param randomMethod  0 for pure random
     */
    public PlayoffRound(Team lowSeed, Team highSeed, int round, int randomMethod){
        this.lowSeed = lowSeed;
        this.highSeed = highSeed;
        if (rand == null){
            rand = new Random();
        }

        switch(randomMethod){
            case 0: // Purely- random - flip a coin
                determineWinner();
            case 1: // Weighted winner (60-40 for the team with more points)
                weightedDetermineWinner();
        }

        // Update the best showing for the losing team
       switch(this.winner){
           case -1:     //low seed won, update high seed if better
               if (highSeed.getBestShowing() < round){
                   MyDBHandler.updateBestShowing(highSeed.getName(), round);
               }
               break;
           case 1:      //high seed won, update low seed if better
               if (lowSeed.getBestShowing() < round){
                   MyDBHandler.updateBestShowing(lowSeed.getName(), round);
               }
               break;
       }

        // if this is the finals, update the winner too
        if (round == 3){
            switch(this.winner){
                case -1:    // low seed won the cup
                    MyDBHandler.updateBestShowing(lowSeed.getName(), round);    // Best showing: cup
                    MyDBHandler.updateCupsWon(lowSeed.getName(), lowSeed.getCupsWon() + 1); // cups++
                    break;
                case 1:     // high seed won the cup
                    MyDBHandler.updateBestShowing(highSeed.getName(), round);    // Best showing: cup
                    MyDBHandler.updateCupsWon(highSeed.getName(), highSeed.getCupsWon() + 1); // cups++
                    break;
            }
        }
    }

    /**
     * Pure random
     */
    private void determineWinner(){
        if (rand.nextDouble() > 0.5){
            // High seed won
            this.winner = 1;
            MyDBHandler.updateRoundsLost(lowSeed.getName(), (lowSeed.getRoundsLost() + 1));
            lowSeed.setRoundsLost(lowSeed.getRoundsLost() + 1);
            MyDBHandler.updateRoundsWon(highSeed.getName(), (highSeed.getRoundsWon() + 1));
            highSeed.setRoundsWon(highSeed.getRoundsWon() + 1);
        }
        else{
            // Low seed won
            this.winner = -1;
            MyDBHandler.updateRoundsLost(highSeed.getName(), (highSeed.getRoundsLost() + 1));
            highSeed.setRoundsLost(highSeed.getRoundsLost() + 1);
            MyDBHandler.updateRoundsWon(lowSeed.getName(), (lowSeed.getRoundsWon() + 1));
            lowSeed.setRoundsWon(lowSeed.getRoundsWon() + 1);
        }
    }

    private void weightedDetermineWinner(){
        // Rolling a number UNDER target means low seed wins
        // Rolling a number OVER target means high seed wins

        // A randomness factor of 0.1 means the team with the most points
        // has a 60% chance of winning vs the low team's 40% chance.

        double target = 0.5;
        if (highSeed.getPoints() > lowSeed.getPoints()){
            target -= RANDOMNESS_FACTOR;
        }
        else{
            target += RANDOMNESS_FACTOR;
        }

        if (rand.nextDouble() > target){
            // High seed won
            this.winner = 1;
            MyDBHandler.updateRoundsLost(lowSeed.getName(), (lowSeed.getRoundsLost() + 1));
            lowSeed.setRoundsLost(lowSeed.getRoundsLost() + 1);
            MyDBHandler.updateRoundsWon(highSeed.getName(), (highSeed.getRoundsWon() + 1));
            highSeed.setRoundsWon(highSeed.getRoundsWon() + 1);
        }
        else{
            // Low seed won
            this.winner = -1;
            MyDBHandler.updateRoundsLost(highSeed.getName(), (highSeed.getRoundsLost() + 1));
            highSeed.setRoundsLost(highSeed.getRoundsLost() + 1);
            MyDBHandler.updateRoundsWon(lowSeed.getName(), (lowSeed.getRoundsWon() + 1));
            lowSeed.setRoundsWon(lowSeed.getRoundsWon() + 1);
        }

    }

    /**
     * Returns -1 for the low seed winning. Returns 1 for the high seed winning.
     * Returns 0 if the winner has not been determined yet.
     * @return
     */
    public int getWinner(){
        return this.winner;
    }

    public String getWinnerMessage(){
        switch (winner){
            case -1:
                return lowSeed.getName() + " beat " + highSeed.getName() + "!";
            case 1:
                return highSeed.getName() + " beat " + lowSeed.getName() + "!";
            default:
                return "The round hasn't been played yet...";

        }
    }

    /**
     * Returns the winning team
     * @return
     */
    public Team getWinningTeam(){
        if (this.winner == -1){
            return lowSeed;
        }
        else{
            return highSeed;
        }
    }

    public Team getLosingTeam(){
        if (this.winner == -1){
            return highSeed;
        }
        else{
            return lowSeed;
        }
    }
}
