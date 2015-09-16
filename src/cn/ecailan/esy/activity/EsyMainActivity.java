package cn.ecailan.esy.activity;

import android.app.Activity;

import android.os.Bundle;
import android.widget.ListView;


import org.json.JSONObject;
import org.ry8.CeaFrame.model.BusinessResponse;
import org.ry8.external.androidquery.callback.AjaxStatus;

import cn.ecailan.esy.R;
import cn.ecailan.esy.adapter.PlantAdapter;
import cn.ecailan.esy.model.SendMsgRequest;

/**
 * Created by John on 2015/9/2.
 * <p/>
   无用界面
 */
public class EsyMainActivity extends Activity implements BusinessResponse {

    private ListView mListView;
    private SendMsgRequest sendMsgRequest;
    private PlantAdapter plantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.esy_main_layout);

        sendMsgRequest = new SendMsgRequest(this);
        sendMsgRequest.addResponseListener(this);
        sendMsgRequest.getPlant();
        initView();

    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.esy_main_layout_listview);
//        plantAdapter = new PlantAdapter(this, sendMsgRequest.plantArrayList);



    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) {

    }
}
