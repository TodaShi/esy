package cn.ecailan.esy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;
import org.ry8.CeaFrame.Utils.NetHelper;
import org.ry8.CeaFrame.model.BusinessResponse;
import org.ry8.external.androidquery.callback.AjaxStatus;

import cn.ecailan.esy.activity.YxtMainActivity;

import cn.ecailan.esy.model.M_AttendRecord;

public class QueryAttendanceService extends Service implements BusinessResponse {

    private MyBinder myBinder = new MyBinder();
    private int num = 0;
    private M_AttendRecord m_attendRecord;
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;

    private NotificationManager manager;
    private Notification notification;
    private PendingIntent pi;
    private int uid;

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public class MyBinder extends Binder {
        public String getResult(String str) {
            return "fucking......" + str;
        }
    }

    @Override
    public void onCreate() {

        shared = this.getSharedPreferences("userInfo", 0);
        editor = shared.edit();


        m_attendRecord = new M_AttendRecord(this);
        m_attendRecord.addResponseListener(this);

        mHandler.sendEmptyMessageDelayed(1, 5000);

        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Intent intentOrder = new Intent();
        intentOrder.setAction("cn.ecailan.yxt.QueryAttendanceService");
        startService(intentOrder);
        Log.v("QueryAttendanceService", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        //super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStart ...");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("onUnbind ...");
        return true;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    uid = shared.getInt("memberid", -1);
                    Context v = getApplicationContext();
                    if (uid > 0 && NetHelper.IsHaveInternet(v)) {
                        m_attendRecord.queryHasNew();
                    }
				/*else{
					editor.putString("orderList", "");
                    editor.commit();
				}*/
                    this.sendEmptyMessageDelayed(1, 20000);
            }
        }
    };

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) {
        // TODO Auto-generated method stub
        if (M_AttendRecord.NewAttendCount > 0) {

            notification(11000, "您有新的未查看接送记录"); //orderid  您有订单("+goodorded.ordernum+")到货，请于"+goodorded.arrivetime_halfday+"

        }
    }


    private void notification(int day, String content) {
        // 获取系统的通知管理器
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notification = new Notification(R.drawable.logo_yxt, content,
                System.currentTimeMillis());
        notification.defaults = Notification.DEFAULT_ALL; // 使用默认设置，比如铃声、震动、闪灯
        notification.flags = Notification.FLAG_AUTO_CANCEL; // 但用户点击消息后，消息自动在通知栏自动消失
        notification.flags |= Notification.FLAG_NO_CLEAR;// 点击通知栏的删除，消息不会依然不会被删除


        Intent intent = new Intent(getApplicationContext(), YxtMainActivity.class);
        intent.putExtra("flag", "newattendrecord");
        intent.putExtra("newattendcount", M_AttendRecord.NewAttendCount);
        pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        notification.setLatestEventInfo(getApplicationContext(), "接送通知", content, pi);

        Notification notification = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.logo_yxt)).setSmallIcon(R.drawable.logo_yxt)
                .setTicker("接送通知")
                .setContentTitle("接送通知").setContentText(content)
                .setNumber(day).setOnlyAlertOnce(true)
                .setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL).setContentIntent(pi)
                .build();
        // 将消息推送到状态栏
        manager.notify(day, notification);
        GlobalVariable.tabsFragment.tab3frament = null;
        GlobalVariable.tabsFragment.attend_notify_num.setVisibility(View.VISIBLE);
        GlobalVariable.tabsFragment.attend_notify_num.setText(M_AttendRecord.NewAttendCount+"");
    }

}
