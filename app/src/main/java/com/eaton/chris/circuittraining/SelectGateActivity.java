package com.eaton.chris.circuittraining;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SelectGateActivity extends Activity implements View.OnClickListener  {
    ArrayList<Gate> gateList;
    private GateListAdapter adapter;
    ListView listView;

    AdapterView.OnItemClickListener selectGateItemHandler= new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Intent returnIntent= new Intent();
            returnIntent.putExtra("gateName",adapter.getItem(position).getGateType());
            setResult(CircuitActivity.RESULT_OK, returnIntent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gate);
        listView=(ListView)findViewById(R.id.selectGate_listView);
        gateList=Gate.buildFullGateList();
        adapter=new GateListAdapter(this,R.layout.activity_select_gate,gateList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(selectGateItemHandler);


    }
    @Override
    public void onClick(View v) {
        Intent infoIntent= new Intent(SelectGateActivity.this,InfoActivity.class);
        infoIntent.putExtra("gateName",v.getTag().toString());
        startActivity(infoIntent);

    }
}
class GateListAdapter extends ArrayAdapter<Gate>{
    private ArrayList<Gate> gates;

    LayoutInflater layoutInflater=(LayoutInflater)getContext()
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    public GateListAdapter(Context context, int resource, List<Gate> objects) {
        super(context, resource, objects);
        this.gates=(ArrayList<Gate>)objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView ==null){
            convertView=layoutInflater.inflate(R.layout.gate_list_item_layout,null);
        }
        Gate gateItem=gates.get(position);
        String gateType=gateItem.getGateType();
        ((ImageView)convertView.findViewById(R.id.gateList_imageView))
                .setImageResource(gateItem.gateDrawableResID);
        ((TextView)convertView.findViewById(R.id.gateList_label_textView))
                .setText(gateType);
        Button button=(Button)convertView.findViewById(R.id.gateList_info_button);
        button.setTag(gateType);
        button.setOnClickListener((View.OnClickListener)getContext());

        return convertView;
    }
}
