package com.ambrogio.dan.playoffs;

/**
 * Created by Dan on 4/12/2015.
 * Stores the data for a hockey team
 */
public class Team implements Comparable<Team>{

    /**
     * Unique id for data persistence
     */
    private int _id;

    /**
     * The team's long-form name
     */
    private String name;

    /**
     * The team's short-form name
     */
    private String initials;

    /**
     * The number of rounds the team has won
     */
    private int roundsWon;

    /**
     * The number of rounds the team has lost
     */
    private int roundsLost;

    /**
     * The number of cups the team has won
     */
    private int cupsWon;

    /**
     * The best the team has done so far
     * 0 - lost in quarterfinals
     * 1 - lost in semifinals
     * 2 - lost in finals
     * 3 - won finals (cup)
     */
    private int bestShowing;

    // Regular season stats starts

    private int wins;

    private int losses;

    private int overtimeLosses;

    // Regular season stats ends


    public Team(){
    }

    /**
     * Creates a new team of the given name
     * @param id
     * @param name
     * @param initials
     */
    public Team(int id, String name, String initials){
        this._id = id;
        this.name = name;
        if (initials.length() > 4){
            initials = initials.substring(0,4);
        }
        this.initials = initials;
    }

    public Team(int id, String name, String initials, int roundsWon, int roundsLost, int cupsWon,
                int wins, int losses, int overtimeLosses, int bestShowing){
        this._id = id;
        this.name = name;
        this.initials = initials;
        this.roundsWon = roundsWon;
        this.roundsLost = roundsLost;
        this.cupsWon = cupsWon;
        this.wins = wins;
        this.losses = losses;
        this.overtimeLosses = overtimeLosses;
        this.bestShowing = bestShowing;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public int getRoundsWon() {
        return roundsWon;
    }

    public void setRoundsWon(int roundsWon) {
        this.roundsWon = roundsWon;
    }

    public int getRoundsLost() {
        return roundsLost;
    }

    public void setRoundsLost(int roundsLost) {
        this.roundsLost = roundsLost;
    }

    public int getCupsWon() {
        return cupsWon;
    }

    public void setCupsWon(int cupsWon) {
        this.cupsWon = cupsWon;
    }

    public int getBestShowing(){
        return bestShowing;
    }

    /**
     * Generates a string from the integer representation of
     * their best showing in the playoffs.
     * @param bestShowing
     * @return
     */
    public static String generateBestShowingString(int bestShowing) {
        switch (bestShowing){
            case 0:
                return "Out in the first round";
            case 1:
                return "Made it to the Semifinals";
            case 2:
                return "Made it to the Finals!";
            case 3:
                return "Won the cup!";
            default:
                return "";
        }
    }

    public void setBestShowing(int showing){
        this.bestShowing = showing;
    }

    /**
     * Number of wins this season (2 points)
     */
    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    /**
     * Number of regulation losses this season (0 points)
     */
    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    /**
     * Number of overtime losses this season (1 point)
     */
    public int getOvertimeLosses() {
        return overtimeLosses;
    }

    public void setOvertimeLosses(int overtimeLosses) {
        this.overtimeLosses = overtimeLosses;
    }

    /**
     * Gets the total points for the team in the regular season
     * @return
     */
    public int getPoints(){
        return (this.wins * 2) + (this.overtimeLosses);
    }

    /**
     * Compares this object to the specified object to determine their relative
     * order.
     *
     * @param another the object to compare to this instance.
     * @return a negative integer if this instance is less than {@code another};
     * a positive integer if this instance is greater than
     * {@code another}; 0 if this instance has the same order as
     * {@code another}.
     * @throws ClassCastException if {@code another} cannot be converted into something
     *                            comparable to {@code this} instance.
     */
    @Override
    public int compareTo(Team another) {
        // compareTo should return < 0 if this is supposed to be
        // less than other, > 0 if this is supposed to be greater than
        // other and 0 if they are supposed to be equal
        return Integer.compare(this.getPoints(), another.getPoints());
    }
}
