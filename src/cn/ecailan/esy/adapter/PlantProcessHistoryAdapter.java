package cn.ecailan.esy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import cn.ecailan.esy.R;

/**
 * Created by John on 2015/9/10.
 */
public class PlantProcessHistoryAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, Object>> list;

    public PlantProcessHistoryAdapter(Context context, ArrayList<HashMap<String, Object>> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.plant_process_history_listview_item, null);
            holder.history_img = (ImageView) convertView.findViewById(R.id.history_img);
            holder.history_opname = (TextView) convertView.findViewById(R.id.history_opname);
            holder.history_remark = (TextView) convertView.findViewById(R.id.history_remark);
            holder.history_addtime = (TextView) convertView.findViewById(R.id.history_addtime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.history_opname.setText(list.get(position).get("opname").toString());
        holder.history_remark.setText(list.get(position).get("remark").toString());
        holder.history_addtime.setText(list.get(position).get("addtime").toString());
        return convertView;
    }

    private class ViewHolder {
        public ImageView history_img;
        public TextView history_opname;
        public TextView history_remark;
        public TextView history_addtime;

    }
}
