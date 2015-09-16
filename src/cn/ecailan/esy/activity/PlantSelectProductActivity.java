package cn.ecailan.esy.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import cn.ecailan.esy.adapter.PlantSelectProductAdapter;
import cn.ecailan.esy.model.SendMsgRequest;

/**
 * Created by flywithme on 2012/1/1.
 * // 农户添加种植清单，选择地块界面。
 */
public class PlantSelectProductActivity extends Activity implements BusinessResponse{

    private TextView title;
    private FrameLayout nav_bar;
    private LinearLayout backUpPage;
    private SendMsgRequest sendMsgRequest;
    private ListView listView;
    private PlantSelectProductAdapter plantSelectProductAdapter;
    private String areaidg;
    private String areaname;
    private String productId;
    private String productName;
    private String tranco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_product_layout);

        areaidg = getIntent().getStringExtra("areaidg");
        areaname = getIntent().getStringExtra("areaname");

        sendMsgRequest = new SendMsgRequest(this);
        sendMsgRequest.addResponseListener(this);
        sendMsgRequest.getProduct();

        initView();
    }

    private void initView() {
        //标题

        

        title = (TextView)findViewById(R.id.topview_title);
        title.setText("产品列表");
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
            }
        });


        listView =(ListView)findViewById(R.id.listView);
        plantSelectProductAdapter = new PlantSelectProductAdapter(this,sendMsgRequest.arrayProductList);
        listView.setAdapter(plantSelectProductAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                productName = sendMsgRequest.arrayProductList.get(position).get("name").toString();
                productId = sendMsgRequest.arrayProductList.get(position).get("productidg").toString();
                tranco = areaname + "," + productName;

                sendMsgRequest.addPlant(tranco,areaidg,productId);
                finish();

            }
        });

    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) {

        if ("add_plant".equals(status.getRequestCode())){


        }

    }


}
