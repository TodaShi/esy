package cn.ecailan.esy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONObject;
import org.ry8.CeaFrame.model.BusinessResponse;
import org.ry8.external.androidquery.callback.AjaxStatus;

import cn.ecailan.esy.R;
import cn.ecailan.esy.model.SendMsgRequest;

/**
 * Created by Myuluo on 2015/9/11.
 */
/**
 * 采收清单
 */
public class SetProductOutputActivity extends Activity implements BusinessResponse {

    private LinearLayout close;//关闭当前Activity
    private EditText output;//产量
    private Button btnOk;//确定上传
    private SendMsgRequest sendMsgRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_product_output);
    }
    private void initView(){
        close = (LinearLayout)findViewById(R.id.close);
        output = (EditText)findViewById(R.id.set_product_output_edittext);
        btnOk = (Button)findViewById(R.id.set_product_output_button);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMsgRequest.setProductOutput(output.getText().toString());
            }
        });
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) {

    }
}
