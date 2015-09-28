package com.ambrogio.dan.playoffs;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class ShowStats extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showstats);

        TableLayout table = (TableLayout)findViewById(R.id.statsTable);
        table.setStretchAllColumns(true);

        android.widget.TableRow.LayoutParams p = new android.widget.TableRow.LayoutParams();
        p.rightMargin = 10; //pixels -> should really be dp
        p.leftMargin = 10;

        TableRow header = new TableRow(getApplicationContext());
        TextView titleName = new TextView(getApplicationContext());
        titleName.setText("Team");
        TextView titleWin = new TextView(getApplicationContext());
        titleWin.setText("Rounds Won");
        titleWin.setLayoutParams(p);
        titleWin.setBackgroundColor(getResources().getColor(R.color.slighttint));
        TextView titleLose = new TextView(getApplicationContext());
        titleLose.setText("Rounds Lost");
        TextView titleCups = new TextView(getApplicationContext());
        titleCups.setText("Stanley Cups");
        titleCups.setLayoutParams(p);
        titleCups.setBackgroundColor(getResources().getColor(R.color.slighttint));
        TextView titleBest = new TextView(getApplicationContext());
        titleBest.setText("Best Showing");
        header.addView(titleName);
        header.addView(titleWin);
        header.addView(titleLose);
        header.addView(titleCups);
        header.addView(titleBest);
        table.addView(header);

        // Create the table of teams
        for (String s : getResources().getStringArray(R.array.teamnames)){
            TableRow row = new TableRow(getApplicationContext());
            // Ignore the first string
            if (!s.equals("Select a team...")){
                Team team = MyDBHandler.findTeam(s);

                // TextView - name
                TextView tvName = new TextView(getApplicationContext());
                tvName.setTextColor(getResources().getColor(R.color.font));
                tvName.setId(View.generateViewId());
                tvName.setText(String.valueOf(s));

                // TextView - Rounds won
                TextView tvWinRound = new TextView(getApplicationContext());
                tvWinRound.setTextColor(getResources().getColor(R.color.font));
                tvWinRound.setId(View.generateViewId());
                tvWinRound.setText(String.valueOf(team.getRoundsWon()));
                tvWinRound.setGravity(Gravity.CENTER_HORIZONTAL);
                tvWinRound.setBackgroundColor(getResources().getColor(R.color.slighttint));
                tvWinRound.setLayoutParams(p);

                // Text View - Rounds Lost
                TextView tvLoseRound = new TextView(getApplicationContext());
                tvLoseRound.setTextColor(getResources().getColor(R.color.font));
                tvLoseRound.setId(View.generateViewId());
                tvLoseRound.setText(String.valueOf(team.getRoundsLost()));
                tvLoseRound.setGravity(Gravity.CENTER_HORIZONTAL);

                // Text View - Cups Won
                TextView tvWinCups = new TextView(getApplicationContext());
                tvWinCups.setTextColor(getResources().getColor(R.color.font));
                tvWinCups.setId(View.generateViewId());
                tvWinCups.setText(String.valueOf(team.getCupsWon()));
                tvWinCups.setGravity(Gravity.CENTER_HORIZONTAL);
                tvWinCups.setBackgroundColor(getResources().getColor(R.color.slighttint));
                tvWinCups.setLayoutParams(p);

                // Text View - Best Showing
                TextView tvBestShowing = new TextView(getApplicationContext());
                tvBestShowing.setTextColor(getResources().getColor(R.color.font));
                tvBestShowing.setId(View.generateViewId());
                tvBestShowing.setText(String.valueOf(Team.generateBestShowingString(team.getBestShowing())));

                row.addView(tvName);
                row.addView(tvWinRound);
                row.addView(tvLoseRound);
                row.addView(tvWinCups);
                row.addView(tvBestShowing);
                table.addView(row);
            }
        }
        //scroll.addView(vRow);
    }

    public void onClickHome(View v){
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_stats, menu);
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
