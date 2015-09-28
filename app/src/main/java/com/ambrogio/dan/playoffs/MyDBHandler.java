package com.ambrogio.dan.playoffs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dan on 4/12/2015.
 */
public class MyDBHandler extends SQLiteOpenHelper {

    private static MyDBHandler sInstance = null;
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "hockeyhalloffame.db";
    private static final String TABLE_TEAMS = "teams";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_INITIALS = "initials";
    public static final String COLUMN_ROUNDSWON = "roundswon";
    public static final String COLUMN_ROUNDSLOST = "roundslost";
    public static final String COLUMN_CUPSWON = "cupswon";
    public static final String COLUMN_RSWINS = "wins";
    public static final String COLUMN_RSLOSSES = "losses";
    public static final String COLUMN_RSOTL = "overtimelosses";
    public static final String COLUMN_SHOWING = "bestshowing";

    private final Context context;

    public static synchronized MyDBHandler getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new MyDBHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    private MyDBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.context = context;
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TEAMS_TABLE = "CREATE TABLE " +
                TABLE_TEAMS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_INITIALS + " TEXT,"
                + COLUMN_ROUNDSWON + " INTEGER,"
                + COLUMN_ROUNDSLOST + " INTEGER,"
                + COLUMN_CUPSWON + " INTEGER,"
                + COLUMN_RSWINS + " INTEGER,"
                + COLUMN_RSLOSSES + " INTEGER,"
                + COLUMN_RSOTL + " INTEGER,"
                + COLUMN_SHOWING + " INTEGER"
                + ");";
        db.execSQL(CREATE_TEAMS_TABLE);

        // Populate table
        List<Team> teams = new ArrayList<Team>();
        InputStream inputStream = context.getResources().openRawResource(R.raw.nhl_standings);
        CSVFile csvFile = new CSVFile(inputStream);
        List scoreList = csvFile.read();

        for (int i = 0; i < scoreList.size(); i++){
            String[] row = (String[]) scoreList.get(i);
            Team team = new Team(
                    i,                          // _id
                    row[0],                     // name
                    row[1],                     // initials
                    Integer.parseInt(row[2]),   // rounds won
                    Integer.parseInt(row[3]),   // rounds lost
                    Integer.parseInt(row[4]),   // cups won
                    Integer.parseInt(row[5]),   // wins
                    Integer.parseInt(row[6]),   // losses
                    Integer.parseInt(row[7]),   // overtime losses
                    Integer.parseInt(row[8]));  // best showing
            teams.add(team);
        }

        for (Team team: teams){
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, team.getName());
            values.put(COLUMN_INITIALS, team.getInitials());
            values.put(COLUMN_ROUNDSWON, team.getRoundsWon());
            values.put(COLUMN_ROUNDSLOST, team.getRoundsLost());
            values.put(COLUMN_CUPSWON, team.getCupsWon());
            values.put(COLUMN_RSWINS, team.getWins());
            values.put(COLUMN_RSLOSSES, team.getLosses());
            values.put(COLUMN_RSOTL, team.getOvertimeLosses());
            values.put(COLUMN_SHOWING, team.getBestShowing());

            db.insert(TABLE_TEAMS, null, values);
        }

        //db.close();
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);
        onCreate(db);
    }

    /**
     * Adds a team to the database
     * @param team      The team to add
     *//*
    public void addTeam(Team team) {

        SQLiteDatabase db = null;
        try {
            db = MyDBHandler.getInstance(context).getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, team.getName());
            values.put(COLUMN_INITIALS, team.getInitials());
            values.put(COLUMN_ROUNDSWON, team.getRoundsWon());
            values.put(COLUMN_ROUNDSLOST, team.getRoundsLost());
            values.put(COLUMN_CUPSWON, team.getCupsWon());
            values.put(COLUMN_RSWINS, team.getWins());
            values.put(COLUMN_RSLOSSES, team.getLosses());
            values.put(COLUMN_RSOTL, team.getLosses());
            values.put(COLUMN_SHOWING, team.getBestShowing());

            db.insert(TABLE_TEAMS, null, values);
        }
        finally {
            if (db != null) {
                db.close();
            }
        }
    }*/

    /**
     * Updates a team's regular season record
     * @param name      The team name
     * @param wins      The new number of wins
     * @param losses    The new number of losses
     * @param otl       The new number of overtime losses
     */
    public void updateRecord(String name, int wins, int losses, int otl){
        String query = "UPDATE " + TABLE_TEAMS + " SET "
                + COLUMN_RSWINS + " = " + wins + ", "
                + COLUMN_RSLOSSES + " = " + losses + ", "
                + COLUMN_RSOTL + " = " + otl + " "
                + "WHERE " + COLUMN_NAME + " = '" + name + "'";

        SQLiteDatabase db = sInstance.getWritableDatabase();
        Log.d("Updated a record", query);

        Cursor cursor = db.rawQuery(query, null);
        Log.d("Updated response: ", String.valueOf(cursor.getCount()));
        db.close();
    }

    /**
     * Updates the number of rounds won by a specific team.
     * @param name
     * @param won
     */
    public static void updateRoundsWon(String name, int won){
        String query = "UPDATE " + TABLE_TEAMS + " SET "
                + COLUMN_ROUNDSWON + " = " + won + " "
                + "WHERE " + COLUMN_NAME + " = '" + name + "'";

        SQLiteDatabase db = sInstance.getWritableDatabase();

        Cursor c = db.rawQuery(query, null);
        Log.d("updateRoundsWon", query + " : " + c.getCount());
        db.close();
    }

    /**
     * Updates the number of rounds lost by a specific team.
     * @param name
     * @param lost
     */
    public static void updateRoundsLost(String name, int lost){
        String query = "UPDATE " + TABLE_TEAMS + " SET "
                + COLUMN_ROUNDSLOST + " = " + lost + " "
                + "WHERE " + COLUMN_NAME + " = '" + name + "'";

        SQLiteDatabase db = sInstance.getWritableDatabase();

        Cursor c = db.rawQuery(query, null);
        Log.d("updateRoundsLost", query + " : " + c.getCount());
        db.close();
    }

    /**
     * Updates the number of cups won by a specific team.
     * @param name
     * @param cups
     */
    public static void updateCupsWon(String name, int cups){
        String query = "UPDATE " + TABLE_TEAMS + " SET "
                + COLUMN_CUPSWON + " = " + cups + " "
                + "WHERE " + COLUMN_NAME + " = '" + name + "'";

        SQLiteDatabase db = sInstance.getWritableDatabase();


        Cursor c = db.rawQuery(query, null);
        Log.d("updateCupsWon", query + " : " + c.getCount());
        db.close();
    }

    /**
     *
     * @param name
     * @param showing
     */
    public static void updateBestShowing(String name, int showing){
        String query = "UPDATE " + TABLE_TEAMS + " SET "
                + COLUMN_SHOWING + " = " + showing + " "
                + "WHERE " + COLUMN_NAME + " = '" + name + "'";

        SQLiteDatabase db = sInstance.getWritableDatabase();

        Cursor c = db.rawQuery(query, null);
        Log.d("updateBestShowing", query + " : " + c.getCount());
        db.close();
    }

    /**
     * Retrieves a team from the database
     * @param teamname
     * @return
     */
    public static Team findTeam(String teamname) {
        String query = "Select * FROM " + TABLE_TEAMS + " WHERE " + COLUMN_NAME+ " =  \"" + teamname + "\"";
        Team team = new Team();
        SQLiteDatabase db = null;
        try{
            db = sInstance.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                team.set_id(cursor.getInt(0));
                team.setName(cursor.getString(1));
                team.setInitials(cursor.getString(2));
                team.setRoundsWon(cursor.getInt(3));
                team.setRoundsLost(cursor.getInt(4));
                team.setCupsWon(cursor.getInt(5));
                team.setWins(cursor.getInt(6));
                team.setLosses(cursor.getInt(7));
                team.setOvertimeLosses(cursor.getInt(8));
                team.setBestShowing(cursor.getInt(9));
                cursor.close();
            } else {
                team = null;
            }
        }
        finally{
            if (db != null) {
                db.close();
            }
        }
        return team;
    }

    /**
     * Retreives all teams from the database
     * @return
     */
    public List<Team> listTeams(){
        List<Team> allTeams = new ArrayList<Team>();

        String query = "SELECT * FROM " + TABLE_TEAMS + ";";

        SQLiteDatabase db = sInstance.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(query, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Team team = new Team();
                        team.set_id(cursor.getInt(0));
                        team.setName(cursor.getString(1));
                        team.setInitials(cursor.getString(2));
                        team.setRoundsWon(cursor.getInt(3));
                        team.setRoundsLost(cursor.getInt(4));
                        team.setCupsWon(cursor.getInt(5));
                        team.setWins(cursor.getInt(6));
                        team.setLosses(cursor.getInt(7));
                        team.setOvertimeLosses(cursor.getInt(8));
                        team.setBestShowing(cursor.getInt(9));
                        allTeams.add(team);
                    } while (cursor.moveToNext());
                }

            } finally {
                try { cursor.close(); } catch (Exception ignore) {}
            }

        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }

        return allTeams;
    }
}
