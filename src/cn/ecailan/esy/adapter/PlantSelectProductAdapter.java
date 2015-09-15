package cn.ecailan.esy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.ecailan.esy.R;


public class PlantSelectProductAdapter extends BaseAdapter {

    private Context context;
    //    private ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, Object>> list;


    public PlantSelectProductAdapter(Context context, ArrayList<HashMap<String, Object>> list) {
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
            convertView = inflater.inflate(R.layout.plant_product_listview_item, null);
            viewHolder.productItem = (TextView) convertView.findViewById(R.id.product_item);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.productItem.setText(list.get(position).get("name").toString());

        return convertView;

    }

   private class ViewHolder{

        public TextView productItem;
    }

}