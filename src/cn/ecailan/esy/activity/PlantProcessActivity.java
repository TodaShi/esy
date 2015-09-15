package cn.ecailan.esy.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;
import org.ry8.CeaFrame.model.BusinessResponse;
import org.ry8.CeaFrame.theme.ResourcesFactory;
import org.ry8.external.androidquery.callback.AjaxStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.ecailan.esy.R;
import cn.ecailan.esy.adapter.PlantProcessAdapter;
import cn.ecailan.esy.model.SendMsgRequest;

/**
 * Created by Myuluo on 2015/9/7.
 * 农户种植过程 操作界面1
 */
public class PlantProcessActivity extends Activity implements BusinessResponse {

    private TextView title;
    private FrameLayout nav_bar;
    private ListView listView;
    private PlantProcessAdapter adapter;
    private String traceidg;
    private SendMsgRequest sendMsgRequest;
    private LinearLayout close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_process_layout);
        traceidg = getIntent().getStringExtra("traceidg");
        sendMsgRequest = new SendMsgRequest(this);
        sendMsgRequest.addResponseListener(this);
        sendMsgRequest.getOpname();
        initView();
        getListView();

    }
     private void initView(){
         title = (TextView)findViewById(R.id.topview_title);
         close = (LinearLayout)findViewById(R.id.close);
         close.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
         title.setText("操作列表");
         nav_bar = (FrameLayout)findViewById(R.id.nav_bar);
         Drawable drawable = ResourcesFactory.getDrawable(getResources(), R.drawable.nav_background);
         if (null != drawable) {
             nav_bar.setBackgroundDrawable(drawable);
         }
    }
    private void getListView(){
        listView = (ListView)findViewById(R.id.plant_process_layout_listview);
        adapter = new PlantProcessAdapter(this,sendMsgRequest.operationArrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlantProcessActivity.this,PlantProcessDetailActivity.class);
                intent.putExtra("opname",sendMsgRequest.operationArrayList.get(position).get("opname").toString());
                intent.putExtra("traceidg",traceidg);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_from_left);
            }
        });
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) {

    }
}
