package com.ntnu.henrik.lab1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class A3 extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.ntnu.henrik.lab1.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a3);
    }

    //onclick function on button A3 to go to A2
    public void onClickButtonA3(View view){
        //creates intent
        Intent intent = new Intent();

        //gets the text from T1
        EditText editText = (EditText) findViewById(R.id.T4);
        String message = editText.getText().toString();

        //puts the message in intent
        intent.putExtra(EXTRA_MESSAGE, message);

        //starts A2 with result ok
        setResult(RESULT_OK, intent);

        finish();
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(A3.this, A2.class));
    }
}
