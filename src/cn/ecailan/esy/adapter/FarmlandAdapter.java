package cn.ecailan.esy.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import cn.ecailan.esy.R;

/**
 * Created by jiang on 2015/9/2.
 */
public class FarmlandAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, Object>> list;

    //创建构造函数
    public FarmlandAdapter(Context context,ArrayList<HashMap<String, Object>> list){
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
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null){
            convertView = inflater.inflate(R.layout.farmland_listview_item, null);
            viewHolder.viewName = (TextView) convertView.findViewById(R.id.farmland_item_layout_name);
            viewHolder.viewInvisible = (TextView) convertView.findViewById(R.id.farmland_item_layout_invisible);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.viewName.setText(list.get(position).get("areaname").toString());
//        if(list.get(position).get("disabled").equals(true)){    //如果list.get(position).get("disabled")的值等于true，
//            viewHolder.viewInvisible.setVisibility(View.VISIBLE);  //则显示TextView的值
//        }else{
//            viewHolder.viewInvisible.setVisibility(View.INVISIBLE);
//        }
        if(list.get(position).get("disabled").equals(true)) {
            viewHolder.viewName.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.viewInvisible.setText("已停用");
            viewHolder.viewInvisible.setClickable(false);
        }
        return convertView;
    }
    //初始化控件
    public class ViewHolder{
        public TextView viewName;
        public TextView viewInvisible;
    }
}
