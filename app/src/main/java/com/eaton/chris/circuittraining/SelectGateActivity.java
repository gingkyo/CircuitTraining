package com.eaton.chris.circuittraining;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SelectGateActivity extends Activity {
    ArrayList<Gate> gateList;
    private GateListAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gate);
        listView=(ListView)findViewById(R.id.selectGate_listView);
        gateList=Gate.buildFullGateList();
        adapter=new GateListAdapter(this,R.layout.activity_select_gate,gateList);
        listView.setAdapter(adapter);

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
        ((ImageView)convertView.findViewById(R.id.gateList_imageView))
                .setImageResource(gateItem.getGateImageByName());
        ((TextView)convertView.findViewById(R.id.gateList_label_textView))
                .setText(gateItem.getGateType());
        return convertView;
    }
}
