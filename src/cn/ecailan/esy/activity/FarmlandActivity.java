package cn.ecailan.esy.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;
import org.ry8.CeaFrame.model.BusinessResponse;
import org.ry8.CeaFrame.theme.ResourcesFactory;
import org.ry8.external.androidquery.callback.AjaxStatus;

import cn.ecailan.esy.R;
import cn.ecailan.esy.adapter.FarmlandAdapter;
import cn.ecailan.esy.model.SendMsgRequest;

/**
 * Created by jiang on 2015/9/2.
 */
public class FarmlandActivity extends Activity implements BusinessResponse {
    private TextView title;  //标题设置
    private FrameLayout nav_bar;
    private LinearLayout imageBack;    //头部返回图标
    public  static  SendMsgRequest sendMsgRequest;   //初始化SendMsgRequest类对象
    private Button btnAddArea;   //确定按钮
    private ListView mListView;   //listView列表
    public static FarmlandAdapter mAdapter;   //初始化FarmlandAdapte列表适配器


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmland_layout);

        //标题设置
        title = (TextView) findViewById(R.id.topview_title);
        title.setText("种植地管理");  //文字
        nav_bar = (FrameLayout) findViewById(R.id.nav_bar);
        Drawable drawable = ResourcesFactory.getDrawable(getResources(), R.drawable.nav_background);  //背景

        if (null != drawable) {
            nav_bar.setBackgroundDrawable(drawable);
        }
//        name = getIntent().getIntExtra("001",1);
        sendMsgRequest = new SendMsgRequest(this);  //创建SendMsgRequest类对象
        sendMsgRequest.addResponseListener(this);   //调用其监听方法
        sendMsgRequest.getArea();  //调用获取地块的方法

        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    //初始化
    private void initView() {
        btnAddArea = (Button) findViewById(R.id.add_area);  //绑定按钮
        btnAddArea.setOnClickListener(new View.OnClickListener() {  //绑定监听事件
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(FarmlandActivity.this, AddFarmlandActivity.class);
                FarmlandActivity.this.startActivity(intent);  //启动意图
            }
        });

        imageBack = (LinearLayout)findViewById(R.id.close);   //绑定图片
        imageBack.setOnClickListener(new View.OnClickListener() {   //绑定图片的监听事件
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent();
                intent.setClass(FarmlandActivity.this, ???);
                FarmlandActivity.this.startActivity(intent);*/
                finish();  //关闭
            }
        });

        mListView = (ListView) findViewById(R.id.farmland_layout_listview);   //绑定列表
        mAdapter = new FarmlandAdapter(this, sendMsgRequest.arrayFarmlandList);  //创建FarmlandAdapte适配器对象
        mListView.setAdapter(mAdapter);  //给ListView列表项赋值

    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) {


    }
}

