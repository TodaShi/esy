package cn.ecailan.esy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.ecailan.esy.R;


public class PlantSelecAreaAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, Object>> list;

    public PlantSelecAreaAdapter(Context context,ArrayList<HashMap<String, Object>> list) {
        this.context = context;
        this.list = list;
        inflater= LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.plant_select_listview_item, null);
            viewHolder.arearItem = (TextView) convertView.findViewById(R.id.area_item);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.arearItem.setText(list.get(position).get("areaname").toString());

        return convertView;
    }

   private  class ViewHolder{
        public TextView arearItem;
    }

}