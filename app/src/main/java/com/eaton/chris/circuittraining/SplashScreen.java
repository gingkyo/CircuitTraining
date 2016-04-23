package com.eaton.chris.circuittraining;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SplashScreen extends Activity {
    View.OnClickListener enterHandler =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(SplashScreen.this,CircuitActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Button title=(Button)findViewById(R.id.splash_title_button);
        Typeface typeFace= Typeface.createFromAsset(getAssets(),"fonts/bansheepilot.ttf");
        title.setTypeface(typeFace);
        title.setOnClickListener(enterHandler);
    }
}
