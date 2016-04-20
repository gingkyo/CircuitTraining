package com.eaton.chris.circuittraining;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            String gateName=extras.getString("gateName");
            TextView title=(TextView)findViewById(R.id.gate_info_title);
            title.setText(gateName);
            TextView gateInfoText=(TextView)findViewById(R.id.gate_info_textView);
            gateInfoText.setText(Gate.getGateDescByName(gateName));
            ImageView iv=(ImageView)findViewById(R.id.gate_info_imageView);
            iv.setImageResource(Gate.getGateImageByName(gateName));
        }
    }
}
