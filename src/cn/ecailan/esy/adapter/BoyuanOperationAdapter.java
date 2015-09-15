package cn.ecailan.esy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.ecailan.esy.R;

/**
 * Created by Myuluo on 2015/9/2.
 */
public class BoyuanOperationAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private LayoutInflater inflater;

    public BoyuanOperationAdapter(Context context,ArrayList<HashMap<String, Object>> list) {
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
        //优化运行速度
        ViewHolder holder = null;
        //绑定控件
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.boyuan_operation_listview_item_parent,null);
            holder.operationItem = (TextView)convertView.findViewById(R.id.operation_item);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.operationItem.setText(list.get(position).get("opname").toString());
        return convertView;
    }
   private  class ViewHolder{
        public TextView operationItem;
    }
}
