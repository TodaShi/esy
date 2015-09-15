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
import java.util.Map;

import cn.ecailan.esy.R;

/**
 * Created by Myuluo on 2015/9/7.
 */
public class PlantProcessAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<HashMap<String, Object>>  list;

    public PlantProcessAdapter(Context context,ArrayList<HashMap<String, Object>> list) {
        this.list = list;
        this.context = context;
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
        ViewHolder holder = null;
        //绑定控件
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.plant_process_layout_item,null);
            holder.textView = (TextView)convertView.findViewById(R.id.plant_process_layout_item_textview);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.textView.setText(list.get(position).get("opname").toString());
        return convertView;
    }
   private  class ViewHolder{
       public  TextView textView;
    }
}
