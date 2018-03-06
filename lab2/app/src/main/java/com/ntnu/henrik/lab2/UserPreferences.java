package com.ntnu.henrik.lab2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View;

public class UserPreferences extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preferences);

        //finds the dropdown-menu
        Spinner spinner = (Spinner) findViewById(R.id.displayNumber);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.displayNumber_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        //finds the 2n dropdown-menu
        Spinner spinner2 = (Spinner) findViewById(R.id.frequencyList);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.frequencyList_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);


    }

    //onclick function on button Update to go to ListView
    public void onClickButtonUpdate(View view){

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
