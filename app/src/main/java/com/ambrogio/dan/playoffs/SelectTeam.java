package com.ambrogio.dan.playoffs;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class SelectTeam extends ActionBarActivity {

    /**
     * The team names of the selected teams for the playoffs
     */
    public ArrayList<String> selectedTeams = new ArrayList<String>();
    public static final String TEAMS = "TEAMS";

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectteam);

        if (savedInstanceState != null){
            selectedTeams = savedInstanceState.getStringArrayList(TEAMS);
        }

        // Set up list of checkboxes in the scrollview
        ScrollView scroll = (ScrollView)findViewById(R.id.selectScrollView);
        LinearLayout vRow = new LinearLayout(getApplicationContext());
        vRow.setOrientation(LinearLayout.VERTICAL);
        for (String s : getResources().getStringArray(R.array.teamnames)){
            LinearLayout hRow = new LinearLayout(getApplicationContext());
            hRow.setOrientation(LinearLayout.HORIZONTAL);
            // Ignore the first string
            if (!s.equals("Select a team...")){
                // CheckBox
                CheckBox cb = new CheckBox(getApplicationContext());
                cb.setTextColor(getResources().getColor(R.color.font));
                cb.setId(View.generateViewId());
                for (String teamName : selectedTeams){
                    if (s.equals(teamName)){
                        cb.setChecked(true);
                        cb.callOnClick();
                        updateCount();
                        break;
                    }
                }

                // TextView
                TextView tv = new TextView(getApplicationContext());
                tv.setTextColor(getResources().getColor(R.color.font));
                tv.setId(View.generateViewId());
                final String text = String.valueOf(s);
                tv.setText(text);

                // Update the number of checked boxes
                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox thisBox = (CheckBox)v;

                        // User checked the box
                        if (thisBox.isChecked()) {
                            // increment number of selected teams
                            if (selectedTeams.size() < 16){
                                selectedTeams.add(text);
                            }
                            else{   // There are already 16 teams; don't add any
                                thisBox.setChecked(false);
                            }
                        }
                        // User unchecked the box
                        else{
                            // decrement number of selected teams
                            // search the list for the item to remove
                            for (int i = 0; i < selectedTeams.size(); i++){
                                if (selectedTeams.get(i).equals(text)){
                                    selectedTeams.remove(i);
                                }
                            }
                        }
                        updateCount();
                    }
                });
                hRow.addView(cb);
                hRow.addView(tv);
                vRow.addView(hRow);
            }
        }
        scroll.addView(vRow);
    }

    public void updateCount(){
        int size = selectedTeams.size();
        Button go = (Button)findViewById(R.id.btnGo);
        TextView num = (TextView)findViewById(R.id.textNumChecked);
        num.setText(String.valueOf(size + "/16"));
        if (size == 0){
            num.setTextColor(getResources().getColor(R.color.red));
            go.setEnabled(false);
        }
        else if (size == 16){
            num.setTextColor(getResources().getColor(R.color.green));
            go.setEnabled(true);
        }
        else{
            num.setTextColor(getResources().getColor(R.color.yellow));
            go.setEnabled(false);
        }
    }

    public void onClickBack(View v){
        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    public void onClickGo(View v){
        // Pass the team names
        Intent data = new Intent(getApplicationContext(), RunSimulation.class);
        data.putExtra("teams", selectedTeams);
        startActivity(data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_team, menu);
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

    @Override
    protected void onSaveInstanceState(Bundle outBundle){
        super.onSaveInstanceState(outBundle);

        outBundle.putStringArrayList(TEAMS, selectedTeams);
    }
}
