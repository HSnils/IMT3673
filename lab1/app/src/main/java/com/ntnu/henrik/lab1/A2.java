package com.ntnu.henrik.lab1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class A2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a2);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(A1.EXTRA_MESSAGE);

        // Sets T2 as the text from intent
        TextView textView = findViewById(R.id.T2);
        textView.setText("Hello " + message);
    }

    //onclick to go to A3
    public void onClickButtonA2(View view){
        Intent intent = new Intent (this, A3.class);
        startActivityForResult(intent, 1337);
    }

    //when an activity returns info, get it and set the T3 text element
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        String message = data.getStringExtra(A3.EXTRA_MESSAGE);

        // Sets T3 as the text from intent from A3
        TextView textView = findViewById(R.id.T3);
        textView.setText("From A3: " + message);
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(A2.this, A1.class));
    }
}
