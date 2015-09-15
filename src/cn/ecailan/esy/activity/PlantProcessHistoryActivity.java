package cn.ecailan.esy.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;
import org.ry8.CeaFrame.model.BusinessResponse;
import org.ry8.CeaFrame.theme.ResourcesFactory;
import org.ry8.external.androidquery.callback.AjaxStatus;

import java.util.ArrayList;
import java.util.HashMap;

import cn.ecailan.esy.R;
import cn.ecailan.esy.adapter.PlantProcessHistoryAdapter;
import cn.ecailan.esy.model.SendMsgRequest;

/**
 * Created by John on 2015/9/10.
 * 农户种植过程 历史操作
 */
public class PlantProcessHistoryActivity extends Activity implements BusinessResponse{

    private ListView mListView;
    private PlantProcessHistoryAdapter mAdapter;
    private SendMsgRequest sendMsgRequest;
    private String traceidg;
    private TextView title;
    private FrameLayout nav_bar;
    private LinearLayout close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_process_history_layout);
        traceidg = getIntent().getStringExtra("traceidg");
        sendMsgRequest = new SendMsgRequest(this);
        sendMsgRequest.addResponseListener(this);
        sendMsgRequest.getTraceitem(traceidg);
        initView();

    }

    private void initView() {
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
        mListView = (ListView) findViewById(R.id.history_listview);
        mAdapter = new PlantProcessHistoryAdapter(this,sendMsgRequest.historyArrayList);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) {

    }
}
