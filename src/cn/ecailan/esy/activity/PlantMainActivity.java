package cn.ecailan.esy.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.json.JSONObject;
import org.ry8.CeaFrame.model.BusinessResponse;
import org.ry8.CeaFrame.theme.ResourcesFactory;
import org.ry8.CeaFrame.view.ToastView;
import org.ry8.external.androidquery.callback.AjaxStatus;

import cn.ecailan.esy.R;
import cn.ecailan.esy.adapter.PlantAdapter;
import cn.ecailan.esy.model.SendMsgRequest;

/**
 * Created by John on 2015/9/2.
 * 现有种植清单
 * 主界面
 */
public class PlantMainActivity extends Activity implements BusinessResponse,View.OnClickListener {

    private TextView title;//标题
    private FrameLayout nav_bar;
    private LinearLayout backUpPage;
    private SharedPreferences shared;
    private ListView mListView;
    private Button btnAdd;//新增
    private Button btnPurchase;//采收
    private Button btnManagement;//管理
    private SendMsgRequest sendMsgRequest;
    private PlantAdapter plantAdapter;
    private ImageView rightButton;
    private LinearLayout linearlayout_parent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shared = this.getSharedPreferences("userInfo", 0);

        int s = shared.getInt("usertypeid", -1);
        Intent intent = new Intent();
        if (s == 0) {

        }else if(s == 1){

        }else if(s == 2){
            intent.setClass(this,BoyuanConntrolActivity.class);
            startActivity(intent);
            finish();
        }else{
            intent.setClass(this, lg_SigninActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.plant_main_layout);

        //标题
        title = (TextView)findViewById(R.id.topview_title);
        title.setText("现有种植清单");
        nav_bar = (FrameLayout)findViewById(R.id.nav_bar);
        Drawable drawable = ResourcesFactory.getDrawable(getResources(), R.drawable.nav_background);
        if (null != drawable) {
            nav_bar.setBackgroundDrawable(drawable);
        }
        linearlayout_parent = (LinearLayout) findViewById(R.id.linearlayout_parent);
        backUpPage = (LinearLayout)findViewById(R.id.close);
        backUpPage.setVisibility(View.INVISIBLE);
        rightButton = (ImageView) findViewById(R.id.rightButton);
        rightButton.setVisibility(View.VISIBLE);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  new RightPopupWindows(PlantMainActivity.this,linearlayout_parent);
            }
        });

        sendMsgRequest = new SendMsgRequest(this);
        sendMsgRequest.addResponseListener(this);
        sendMsgRequest.getPlant();

        initView();


    }

    private void initView() {
        btnAdd = (Button)findViewById(R.id.esy_main_add_item);
        btnPurchase = (Button)findViewById(R.id.esy_main_purchase_item);
        btnManagement = (Button)findViewById(R.id.esy_main_management_item);
        btnAdd.setOnClickListener(this);
        btnPurchase.setOnClickListener(this);
        btnManagement.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.esy_main_layout_listview);
        plantAdapter = new PlantAdapter(this,sendMsgRequest.plantArrayList);
        mListView.setAdapter(plantAdapter);

    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) {

    }

    @Override
    public void onClick(View v) {
        Intent intent  = new Intent();
        switch (v.getId()){
            case R.id.esy_main_add_item:
                intent.setClass(this,PlantSelectAreaActivity.class);
                PlantMainActivity.this.startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_from_left);
                break;
            case R.id.esy_main_purchase_item:
                intent.setClass(this,SetProductOutputActivity.class);
                PlantMainActivity.this.startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_from_left);
                break;
            case R.id.esy_main_management_item:
                intent.setClass(this,FarmlandActivity.class);
                PlantMainActivity.this.startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_from_left);
                break;
            default:
                break;
        }
    }

    //添加图片View
    public class RightPopupWindows extends PopupWindow {

        public RightPopupWindows(Context mContext, View parent) {

            View view = View
                    .inflate(mContext, R.layout.right_popup_window_item, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            LinearLayout show_right_pupup = (LinearLayout) view
                    .findViewById(R.id.show_right_pupup);
            show_right_pupup.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.push_in_from_up));

            setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.RIGHT|Gravity.TOP,0,105);
            update();

            Button bt1 = (Button) view
                    .findViewById(R.id.change_account);//更换账户
            Button bt2 = (Button) view
                    .findViewById(R.id.change_password);//更改密码


            bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putInt("usertypeid", -1);
                    editor.putString("username", "");
                    editor.putString("password", "");
                    editor.commit();
                    Intent intent = new Intent();
                    intent.setClass(PlantMainActivity.this, lg_SigninActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

        }
    }


    // 退出操作
    private boolean isExit = false;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit == false) {
                isExit = true;
                ToastView toast = new ToastView( getApplicationContext(), "再按一次退出");
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Handler mHandler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        isExit = false;
                    }
                };
                mHandler.sendEmptyMessageDelayed(0, 3000);
                return true;
            } else {
                finish();
                return false;
            }
        }
        return true;
    }

}
