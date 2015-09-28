package com.ambrogio.dan.playoffs;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class RunSimulation extends ActionBarActivity {

    public static final String MYPREFS = "MYSHAREDPREFERENCES";

    private ArrayList<Team> teams = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runsimulation);

        ArrayList<String> teamNames = getIntent().getStringArrayListExtra("teams");
        runSim(teamNames);
    }

    public void onClickDoneSim(View v){
        finish();
    }

    /**
     * Runs the playoff simulation
     * @param teamNames
     */
    private void runSim(ArrayList<String> teamNames){
        // Turn the list of names into a list of teams
        ArrayList<Team> teamsArray = new ArrayList<>(16);
        for (String t : teamNames){
            Team team = MyDBHandler.findTeam(t);
            teamsArray.add(team);
        }
        Collections.sort(teamsArray);   //Sorted low to high
        Playoffs playoffs = new Playoffs(teamsArray);

        writeResults(playoffs); // For portrait display
        //displayResults(playoffs); //For landscape display
        //TODO layout view?

        SharedPreferences prefs = getSharedPreferences(MYPREFS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        //Set the last winner of the cup
        String winner = playoffs.getFinals().getWinningTeam().getName();
        Log.e("lastWinner", "Last winner was " + winner);
        ed.putString("lastwinner", winner);
        ed.commit();
    }

    public void writeResults(Playoffs playoffs){
        LinearLayout results = (LinearLayout)findViewById(R.id.portraitResults);

        // Show who won the cup, first of all
        TextView tvWinner = new TextView(getApplicationContext());
        tvWinner.setText(String.valueOf(playoffs.getCupWinner()));
        tvWinner.setTextSize(16);
        tvWinner.setTextColor(getResources().getColor(R.color.winnerfont));
        results.addView(tvWinner);

        int font = getResources().getColor(R.color.font);

        // Add the conference finals
        TextView wConf = new TextView(getApplicationContext());
        wConf.setTextColor(font);
        wConf.setTextSize(12);
        wConf.setText(String.valueOf("Western Conference Finals Results:"));
        TextView wConfFinal = new TextView(getApplicationContext());
        wConfFinal.setTextColor(font);
        wConfFinal.setText(playoffs.getWestFinal().getWinnerMessage());
        results.addView(wConf);
        results.addView(wConfFinal);

        TextView eConf = new TextView(getApplicationContext());
        eConf.setTextColor(font);
        eConf.setTextSize(12);
        eConf.setText(String.valueOf("Eastern Conference Finals Results:"));
        TextView eConfFinal = new TextView(getApplicationContext());
        eConfFinal.setTextColor(font);
        eConfFinal.setText(playoffs.getEastFinal().getWinnerMessage());
        results.addView(eConf);
        results.addView(eConfFinal);

        // Add the semifinals
        TextView wSemis = new TextView(getApplicationContext());
        wSemis.setTextColor(font);
        wSemis.setTextSize(12);
        wSemis.setText(String.valueOf("Western Semifinals Results:"));
        TextView wests1 = new TextView(getApplicationContext());
        wests1.setTextColor(font);
        wests1.setText(playoffs.getWests1().getWinnerMessage());
        TextView wests2 = new TextView(getApplicationContext());
        wests2.setTextColor(font);
        wests2.setText(playoffs.getWests2().getWinnerMessage());
        results.addView(wSemis);
        results.addView(wests1);
        results.addView(wests2);

        TextView eSemis = new TextView(getApplicationContext());
        eSemis.setTextColor(font);
        eSemis.setTextSize(12);
        eSemis.setText(String.valueOf("Eastern Semifinals Results:"));
        TextView easts1 = new TextView(getApplicationContext());
        easts1.setTextColor(font);
        easts1.setText(playoffs.getEasts1().getWinnerMessage());
        TextView easts2 = new TextView(getApplicationContext());
        easts2.setTextColor(font);
        easts2.setText(playoffs.getEasts2().getWinnerMessage());
        results.addView(eSemis);
        results.addView(easts1);
        results.addView(easts2);

        // Add the quarterfinals
        TextView wQuarters = new TextView(getApplicationContext());
        wQuarters.setTextColor(font);
        wQuarters.setTextSize(12);
        wQuarters.setText(String.valueOf("Western Quarterfinals Results:"));
        TextView westq1 = new TextView(getApplicationContext());
        westq1.setTextColor(font);
        westq1.setText(playoffs.getWestq1().getWinnerMessage());
        TextView westq2 = new TextView(getApplicationContext());
        westq2.setTextColor(font);
        westq2.setText(playoffs.getWestq2().getWinnerMessage());
        TextView westq3 = new TextView(getApplicationContext());
        westq3.setTextColor(font);
        westq3.setText(playoffs.getWestq3().getWinnerMessage());
        TextView westq4 = new TextView(getApplicationContext());
        westq4.setTextColor(font);
        westq4.setText(playoffs.getWestq4().getWinnerMessage());
        results.addView(wQuarters);
        results.addView(westq1);
        results.addView(westq2);
        results.addView(westq3);
        results.addView(westq4);

        TextView eQuarters = new TextView(getApplicationContext());
        eQuarters.setTextColor(font);
        eQuarters.setTextSize(12);
        eQuarters.setText(String.valueOf("Eastern Quarterfinals Results:"));
        TextView eastq1 = new TextView(getApplicationContext());
        eastq1.setTextColor(font);
        eastq1.setText(playoffs.getEastq1().getWinnerMessage());
        TextView eastq2 = new TextView(getApplicationContext());
        eastq2.setTextColor(font);
        eastq2.setText(playoffs.getEastq2().getWinnerMessage());
        TextView eastq3 = new TextView(getApplicationContext());
        eastq3.setTextColor(font);
        eastq3.setText(playoffs.getEastq3().getWinnerMessage());
        TextView eastq4 = new TextView(getApplicationContext());
        eastq4.setTextColor(font);
        eastq4.setText(playoffs.getEastq4().getWinnerMessage());
        results.addView(eQuarters);
        results.addView(eastq1);
        results.addView(eastq2);
        results.addView(eastq3);
        results.addView(eastq4);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_run_simulation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
