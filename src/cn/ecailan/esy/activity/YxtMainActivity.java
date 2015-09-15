package cn.ecailan.esy.activity;

//
//                       __
//                      /\ \   _
//    ____    ____   ___\ \ \_/ \           _____    ___     ___
//   / _  \  / __ \ / __ \ \    <     __   /\__  \  / __ \  / __ \
//  /\ \_\ \/\  __//\  __/\ \ \\ \   /\_\  \/_/  / /\ \_\ \/\ \_\ \
//  \ \____ \ \____\ \____\\ \_\\_\  \/_/   /\____\\ \____/\ \____/
//   \/____\ \/____/\/____/ \/_//_/         \/____/ \/___/  \/___/
//     /\____/
//     \/___/
//
//  Powered by ry8
//

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.ry8.CeaFrame.Utils.UpdateManager;
import org.ry8.CeaFrame.model.BusinessResponse;
import org.ry8.CeaFrame.view.ToastView;
import org.ry8.external.androidquery.callback.AjaxStatus;
import org.ry8.external.androidquery.service.MarketService;

import cn.ecailan.esy.R;
import cn.ecailan.esy.SlideMenu;
import cn.ecailan.esy.model.M_AttendRecord;
import cn.ecailan.esy.model.SendRequest;


public class YxtMainActivity extends FragmentActivity implements BusinessResponse,View.OnClickListener {
    public TextView title;
    public ImageView leftButton,rightButton;
    private Spinner spinner_class;
    private SharedPreferences shared;
    private SendRequest send;
    ArrayAdapter<String> adapter;
    private SlideMenu slideMenu;


	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 软件自动更新
        MarketService ms = new MarketService(YxtMainActivity.this);
        ms.level(MarketService.MINOR).checkVersion();

//        提醒
        Intent intentOrder = new Intent();
        intentOrder.setAction("cn.ecailan.yxt.QueryAttendanceService");
        intentOrder.setPackage(getPackageName());
        startService(intentOrder);

        shared = this.getSharedPreferences("userInfo", 0);

        int s = shared.getInt("memberid", -1);
        if (s <= 0) {
            Intent intent2 = new Intent(this, lg_SigninActivity.class);
            startActivity(intent2);
            finish();
        }
        setContentView(R.layout.yxtmain);
        slideMenu = (SlideMenu) findViewById(R.id.slide_menu);


        //侧边栏按钮
        ImageView cbl = (ImageView)findViewById(R.id.cbl);
        cbl.setVisibility(View.VISIBLE);
        cbl.setOnClickListener(this);

        Intent it=getIntent();
        if("newattendrecord".equals(it.getStringExtra("flag")))
        {
            if(M_AttendRecord.NewAttendCount>0)
            {
               TextView attend_notify_num=(TextView)findViewById(R.id.fourtoolbar_attend_notify_num);
                attend_notify_num.setVisibility(View.VISIBLE);
                attend_notify_num.setText(M_AttendRecord.NewAttendCount+"");
            }
        }
        //注销
        Button bt = (Button) findViewById(R.id.side_sliding_menu_bt_logout);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = shared.edit();
                editor.putInt("memberid", -1);
                editor.putString("password", "");
                editor.commit();
                Intent intent = new Intent(YxtMainActivity.this, YxtMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(
                        R.anim.push_buttom_in, R.anim.push_buttom_out);
            }
        });

        RelativeLayout rlay1 = (RelativeLayout) findViewById(R.id.side_sliding_menu_rlay1);
        rlay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getPackageManager().getLaunchIntentForPackage("cn.ecailan.nnryx");
                if (intent != null) {
                    startActivity(intent);
                } else {
                    // 没有安装要跳转的app应用，提醒一下?
                    UpdateManager mUpdateManager = new UpdateManager(
                            YxtMainActivity.this, "ecailan.apk");//
                    mUpdateManager.setApkUrl("http://www.ecailan.cn/mobile/ecailan.apk");
                    mUpdateManager.setDialogTitle("下载：易菜篮");
                    mUpdateManager.setUpdateMsg("下载：易菜篮");
                    mUpdateManager.DownloadDialog();
                }
            }
        });

        RelativeLayout rlay2 = (RelativeLayout) findViewById(R.id.side_sliding_menu_rlay2);
        rlay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =getPackageManager().getLaunchIntentForPackage("com.webapps.dingding");
                if (intent != null) {
                    startActivity(intent);
                } else {
                    // 没有安装要跳转的app应用，提醒一下
                    UpdateManager mUpdateManager = new UpdateManager(
                            YxtMainActivity.this,"yingwudingding.apk");//
                    mUpdateManager.setApkUrl("http://124.226.64.12/dd.myapp.com/16891/158BC668DE7246062A19444FCA7FCD3E.apk");
                    mUpdateManager.setDialogTitle("下载：鹦鹉丁丁");
                    mUpdateManager.setUpdateMsg("下载：鹦鹉丁丁");
                    mUpdateManager.DownloadDialog();
                }
            }
        });





        RelativeLayout rlay3 = (RelativeLayout) findViewById(R.id.side_sliding_menu_rlay3);
        rlay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(YxtMainActivity.this,lg_EditPwdActivity .class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        });

        RelativeLayout rlay4 = (RelativeLayout) findViewById(R.id.side_sliding_menu_rlay4);
                send=new SendRequest(YxtMainActivity.this);
                send.addResponseListener(this);
                send.getClassName();

    }

    // 退出操作
    private boolean isExit = false;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
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

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) {

         spinner_class=(Spinner)findViewById(R.id.spinner);
        adapter=new ArrayAdapter<String>(YxtMainActivity.this,R.layout.myspinner);
        adapter.add("更换班级");
        for(int i=0;i<send.arrayClass.size();i++)
        {adapter.add(send.arrayClass.get(i).get("ClassName").toString());
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_class.setAdapter(adapter);
//        spinner_class.setSelection(0,true);//默认选择“切换班级”

        spinner_class.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        if(arg2!=0) {
            if(shared.getInt("usertype",-1)==1)
            {
//                spinner_class.setSelection(arg2-1,true);
                send.setTeatherclass(send.arrayClass.get(arg2 - 1).get("AutoID").toString());

                SharedPreferences.Editor editor = shared.edit();
                editor.putInt("ClassID", Integer.parseInt(send.arrayClass.get(arg2 - 1).get("AutoID").toString()));
                editor.putString("ClassName", send.arrayClass.get(arg2 - 1).get("ClassName").toString());
                editor.commit();

                refresh();


            }else{ toast("非教师用户无法进行此操作！！！");}
            }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                arg0.setVisibility(View.VISIBLE);
            }
        });

    }


    public void refresh(){
        finish();
        Intent intent=new Intent(YxtMainActivity.this,YxtMainActivity.class);
        startActivity(intent);
    }


    /**
     * 弹出信息
     */
    private void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    //侧边栏
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cbl:
                if (slideMenu.isMainScreenShowing()) {
                    slideMenu.openMenu();
                } else {
                    slideMenu.closeMenu();
                }
                break;
        }

    }
}
