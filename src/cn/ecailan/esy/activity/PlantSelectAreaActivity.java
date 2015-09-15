package cn.ecailan.esy.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;
import org.ry8.CeaFrame.model.BusinessResponse;
import org.ry8.CeaFrame.theme.ResourcesFactory;
import org.ry8.external.androidquery.callback.AjaxStatus;

import java.util.ArrayList;
import java.util.List;

import cn.ecailan.esy.R;
import cn.ecailan.esy.adapter.PlantSelecAreaAdapter;
import cn.ecailan.esy.model.SendMsgRequest;

// 农户添加种植清单，选择地块界面。

public class PlantSelectAreaActivity extends Activity implements BusinessResponse{

    private TextView title;
    private FrameLayout nav_bar;
    private LinearLayout backUpPage;
    private SendMsgRequest sendMsgRequest;
    private ListView listView;
    private PlantSelecAreaAdapter plantSelectAreaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_select_layout);

        //标题
        title = (TextView)findViewById(R.id.topview_title);
        title.setText("种植地列表");
        nav_bar = (FrameLayout)findViewById(R.id.nav_bar);
        Drawable drawable = ResourcesFactory.getDrawable(getResources(), R.drawable.nav_background);
        if (null != drawable) {
            nav_bar.setBackgroundDrawable(drawable);
        }
        backUpPage = (LinearLayout)findViewById(R.id.close);
        backUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_from_left,R.anim.slide_out_from_right);
            }
        });

        sendMsgRequest = new SendMsgRequest(this);
        sendMsgRequest.addResponseListener(this);
        sendMsgRequest.getArea();

        listView = (ListView)findViewById(R.id.plantSelectlw);
        plantSelectAreaAdapter = new PlantSelecAreaAdapter(this,sendMsgRequest.arrayFarmlandList);
        listView.setAdapter(plantSelectAreaAdapter);
        /*给选产品页面传递参数*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlantSelectAreaActivity.this,PlantSelectProductActivity.class);
                intent.putExtra("areaidg",sendMsgRequest.arrayFarmlandList.get(position).get("areaidg").toString());
                intent.putExtra("areaname",sendMsgRequest.arrayFarmlandList.get(position).get("areaname").toString());
                startActivity(intent);
                finish();//关闭当前页面
            }
        });

    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) {

    }
}
