package com.ambrogio.dan.playoffs;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;


public class EditTeam extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editteam);

        // code here
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.teamnames, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner)findViewById(R.id.spinner);
                String spinnerString = null;
                spinnerString = spinner.getSelectedItem().toString();

                EditText wins = (EditText) findViewById(R.id.inputWins);
                EditText losses = (EditText) findViewById(R.id.inputLosses);
                EditText otl = (EditText) findViewById(R.id.inputOtl);
                EditText pts = (EditText) findViewById(R.id.inputPts);
                // If a valid value is selected
                if (!spinnerString.equals("Select a team...")) {
                    //MyDBHandler db = MyDBHandler.getInstance(getApplicationContext());
                    Team team = MyDBHandler.findTeam(spinnerString);

                    // inputWins
                    wins.setText(String.valueOf(team.getWins()));
                    // inputLosses
                    losses.setText(String.valueOf(team.getLosses()));
                    // inputOtl
                    otl.setText(String.valueOf(team.getOvertimeLosses()));
                    // inputPts
                    pts.setText(String.valueOf(team.getPoints()));
                }
                // Clear the text fields
                else {
                    wins.setText("");
                    losses.setText("");
                    otl.setText("");
                    pts.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Goes back to the main screen
     * @param v
     */
    public void onClickCancel(View v){
        //Button cancelBtn = (Button)findViewById(R.id.btnCancel);
        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    public void onClickSave(View v){
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        String spinnerString = null;
        spinnerString = spinner.getSelectedItem().toString();
        if (!spinnerString.equals("Select a team...")) {
            EditText wins = (EditText) findViewById(R.id.inputWins);
            EditText losses = (EditText) findViewById(R.id.inputLosses);
            EditText otl = (EditText) findViewById(R.id.inputOtl);
            EditText pts = (EditText) findViewById(R.id.inputPts);

            // Check for valid input (integers)
            try {
                Integer.parseInt(wins.getText().toString());
                Integer.parseInt(losses.getText().toString());
                Integer.parseInt(otl.getText().toString());

                MyDBHandler db = MyDBHandler.getInstance(getApplicationContext());
                db.updateRecord(spinnerString, Integer.parseInt(wins.getText().toString()),
                        Integer.parseInt(losses.getText().toString()), Integer.parseInt(otl.getText().toString()));
                Team temp = db.findTeam(spinnerString);
                pts.setText(String.valueOf(temp.getPoints()));
                Toast.makeText(getApplicationContext(), "Team Updated!", Toast.LENGTH_SHORT).show();
            }
            catch (NumberFormatException e){   // Input was not integers! Reload old values
                Toast.makeText(getApplicationContext(), "Whole numbers only, please", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editteam, menu);
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
