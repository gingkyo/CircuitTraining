package com.eaton.chris.circuittraining;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class InstructionsActivity extends Activity {
    private String [] instrArr;
    private String [] titleArr;
    TextView infoText;
    TextView title;
    private int counter;
    View.OnClickListener instrHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.instruction_back_button :
                    counter--;
                    if(counter<=0)
                        counter=0;
                    break;
                case R.id.instruction_next_button :
                    counter++;
                    break;
            }
            setInstructions();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        counter-=0;
        infoText =(TextView)findViewById(R.id.instruction_textView);
        title=(TextView)findViewById(R.id.instruction_title_textView);
        Button back =(Button)findViewById(R.id.instruction_back_button);
        back.setOnClickListener(instrHandler);
        Button next=(Button)findViewById(R.id.instruction_next_button);
        next.setOnClickListener(instrHandler);
        instrArr=getResources().getStringArray(R.array.instruction_manual);
        titleArr=getResources().getStringArray(R.array.Instruction_manual_title);
        setInstructions();
    }
    public void setInstructions(){
        if(counter<instrArr.length){
            infoText.setText(instrArr[counter]);
            title.setText(titleArr[counter]);
        }else finish();

    }
}
