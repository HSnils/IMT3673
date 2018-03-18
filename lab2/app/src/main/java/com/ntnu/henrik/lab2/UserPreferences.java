package com.ntnu.henrik.lab2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View;

public class UserPreferences extends AppCompatActivity {

    EditText editText;
    public static final String NUMBERofITEMS_PREF = "number_pref";
    public static final String FREQUENCY_PREF = "frequency_pref";
    public static final String URL_PREF = "url_pref";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preferences);

        editText = findViewById(R.id.editText);

       // editText.setText(URL_PREF);

        //creates and fills teh dropdowns
        numberOfItemsDropdown();
        rssRefreshFrequencyDropdown();

    }

    public void numberOfItemsDropdown(){

        //finds the dropdown-menu
        Spinner numberSpinner = findViewById(R.id.displayNumber);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> numberAdapter = ArrayAdapter.createFromResource(this,
                R.array.displayNumber_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        numberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        numberSpinner.setAdapter(numberAdapter);

        // fetch prefs and try to fetch state of spinner
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int spinnerPref = prefs.getInt("number_pref", 0);

        // set position of spinner
        numberSpinner.setSelection(spinnerPref);

        // add this (implements interface) as a listener
        numberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)

                // fetch prefs
                //final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                //SharedPreferences prefs = this.getSharedPreferences();
                final SharedPreferences.Editor editor = prefs.edit();

                // fetch spinner
                Spinner spinner = findViewById(R.id.displayNumber);

                // put the position of the spinner
                editor.putInt(NUMBERofITEMS_PREF, spinner.getSelectedItemPosition());

                // apply changes
                editor.apply();

            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }

    //reads from prefs the number of items when called from RSSview
    static public int getNumberOfItems(Context context){
        SharedPreferences prefs = context.getSharedPreferences("number_pref", 0);
        return prefs.getInt(NUMBERofITEMS_PREF, 10);
    }

    public void rssRefreshFrequencyDropdown(){

        //finds the 2n dropdown-menu with frequencies
        Spinner frequencySpinner = findViewById(R.id.frequencyList);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> frequencyAdapter = ArrayAdapter.createFromResource(this,
                R.array.frequencyList_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        frequencySpinner.setAdapter(frequencyAdapter);

        // fetch prefs and try to fetch state of spinner
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int spinnerPref = prefs.getInt("frequency_pref", 0);

        // set position of spinner
        frequencySpinner.setSelection(spinnerPref);

        // add this (implements interface) as a listener
        frequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)

                // fetch prefs
                //final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                //SharedPreferences prefs = this.getSharedPreferences();
                final SharedPreferences.Editor editor = prefs.edit();

                // fetch spinner
                Spinner spinner = findViewById(R.id.frequencyList);

                // put the position of the spinner
                editor.putInt(FREQUENCY_PREF, spinner.getSelectedItemPosition());

                // apply changes
                editor.apply();

            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }

    //reads from prefs the number of items when called from RSSview
    static public int getRefreshTime(Context context){
        SharedPreferences prefs = context.getSharedPreferences("frequency_pref", 0);
        return prefs.getInt(FREQUENCY_PREF, 10);
    }

    public void saveUserSelectedURL(){
        String RSSInputValue = editText.getText().toString();
        SharedPreferences prefs = this.getSharedPreferences("url_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(URL_PREF, String.valueOf(RSSInputValue));
        editor.apply();
    }

    static public String getSelectedRSSFeed(Context context){
        SharedPreferences prefs = context.getSharedPreferences("url_pref", MODE_PRIVATE);
        return prefs.getString(URL_PREF, "https://www.nasa.gov/rss/dyn/breaking_news.rss");
    }

    //onclick function on button Update to go to ListView
    public void onClickButtonUpdate(View view){
        //saves the rss url
        saveUserSelectedURL();
        Intent updateIntent = getIntent();
        setResult(RESULT_OK, updateIntent);

        //creates intent
        Intent intent = new Intent(this, RSSview.class);

        //starts ListView
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(UserPreferences.this, RSSview.class));
    }
}
