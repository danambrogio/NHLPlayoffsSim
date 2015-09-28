package com.ambrogio.dan.playoffs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    public static final String MYPREFS = "MYSHAREDPREFERENCES";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Show the last cup winner
        // Default: LA Kings (2014 winner)
        SharedPreferences prefs = getSharedPreferences(MYPREFS, Activity.MODE_PRIVATE);
        // Get the "lastwinner" value, or L.A. Kings if there isn't one
        String lastWinner = prefs.getString("lastwinner", "L.A. Kings");
        TextView winner = (TextView)findViewById(R.id.winnerTextView);
        winner.setText("Last Winner: " + lastWinner);
        MyDBHandler db = MyDBHandler.getInstance(getApplicationContext());
    }

    @Override
    protected void onResume(){
        super.onResume();

        SharedPreferences prefs = getSharedPreferences(MYPREFS, Activity.MODE_PRIVATE);
        // Get the "lastwinner" value, or L.A. Kings if there isn't one
        String lastWinner = prefs.getString("lastwinner", "L.A. Kings");
        TextView winner = (TextView)findViewById(R.id.winnerTextView);
        winner.setText("Last Winner: " + lastWinner);
    }

    /**
     * Goes to the "Edit a Team" screen.
     * @param v
     */
    public void editTeamOnClick(View v){
        // Go to the Edit Team screen
        //Button editTeam = (Button)findViewById(R.id.menuBtnEdit);
        startActivity(new Intent(getApplicationContext(), EditTeam.class));
    }

    public void runSimOnClick(View v){
        // Go to the Run Sim screen
        startActivity(new Intent(getApplicationContext(), SelectTeam.class));
    }

    public void showStatsOnClick(View v){
        startActivity(new Intent(getApplicationContext(), ShowStats.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
