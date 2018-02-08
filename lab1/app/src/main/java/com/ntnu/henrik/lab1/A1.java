package com.ntnu.henrik.lab1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class A1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a1);
    }

    public void onButtonTap(View v){
        Toast myToast = Toast.makeText(
                getApplicationContext(),
                "TEST!",
                Toast.LENGTH_LONG);
        myToast.show();
    }
}
