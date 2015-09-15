package cn.ecailan.esy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import cn.ecailan.esy.R;

/**
 * Created by John on 2015/9/12.
 */
public class BoyuanProductAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private LayoutInflater inflater;

    public BoyuanProductAdapter(Context context,ArrayList<HashMap<String, Object>> list) {
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

        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.boyuan_product_fragment_layout,null);
            holder.productItem = (TextView) convertView.findViewById(R.id.boyuan_product_item);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.productItem.setText(list.get(position).get("name").toString());
        return convertView;
    }

  private   class ViewHolder {
        public  TextView productItem;
    }
}
