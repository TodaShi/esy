package cn.ecailan.esy.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import cn.ecailan.esy.R;
import cn.ecailan.esy.activity.PlantMainActivity;
import cn.ecailan.esy.activity.PlantProcessActivity;
import cn.ecailan.esy.activity.PlantProcessHistoryActivity;


public class PlantAdapter extends BaseAdapter implements View.OnClickListener{

    private Context context;
    private LayoutInflater inflater;
    public ArrayList<HashMap<String, Object>> plantArrayList;

    public PlantAdapter (Context context,ArrayList<HashMap<String, Object>> plantArrayList) {
        this.context = context;
        this.plantArrayList = plantArrayList;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return plantArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return plantArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //优化运行速度
        ViewHolder holder;
        //绑定控件
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.plant_main_listview_item,null);
            holder.operationItem = (TextView)convertView.findViewById(R.id.plantResultName);
            holder.operationSelect = (TextView)convertView.findViewById(R.id.operateSelect);
            holder.operationHistory= (TextView)convertView.findViewById(R.id.operateHistory);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.operationItem.setText(plantArrayList.get(position).get("traceno").toString());

        holder.operationSelect.setOnClickListener(this);
        holder.operationHistory.setOnClickListener(this);
        holder.operationSelect.setTag(position);
        holder.operationHistory.setTag(position);



        return convertView;
    }



    private  class ViewHolder{
        public  TextView operationItem;
        public  TextView operationSelect;
        public   TextView operationHistory;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        int position =(Integer) v.getTag();
        switch (v.getId()){

            case R.id.operateSelect:
                intent.setClass(context, PlantProcessActivity.class);
                intent.putExtra("traceidg",plantArrayList.get(position).get("traceidg").toString());
                context.startActivity(intent);
                break;

            case R.id.operateHistory:
                intent.setClass(context, PlantProcessHistoryActivity.class);
                intent.putExtra("traceidg",plantArrayList.get(position).get("traceidg").toString());
                context.startActivity(intent);
                break;

        }
    }
}
