package cn.ecailan.esy.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.ry8.CeaFrame.model.BusinessResponse;
import org.ry8.CeaFrame.theme.ResourcesFactory;
import org.ry8.external.androidquery.callback.AjaxStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.ecailan.esy.R;
import cn.ecailan.esy.model.SendMsgRequest;

/**
 * Created by jiang on 2015/9/7.
 */
public class AddFarmlandActivity extends Activity implements BusinessResponse{

    private static int REQUEST_CODE = 1;
    private static int CROP_REQUEST_CODE = 2;
    private TextView title;   //标题设置
    private FrameLayout nav_bar;
    private LinearLayout imageBack;    //头部返回按钮
    private Button btnLocation;    //定位按钮
    private Button btnOK;   //“确定”按钮
    private TextView takePhoto;
    private ImageView photo;
    private SendMsgRequest sendMsgRequest;  //初始化SendMsgRequest类
    private EditText areaName;   //
    private EditText areaSize;
    private TextView areaLng;
    private TextView areaLat;
    private EditText areaRemark;   //地块描述
    private String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //标题设置
        setContentView(R.layout.add_farmland_layout);
        title = (TextView)findViewById(R.id.topview_title);
        title.setText("新增种植地");
        nav_bar = (FrameLayout) findViewById(R.id.nav_bar);
        Drawable drawable = ResourcesFactory.getDrawable(getResources(), R.drawable.nav_background);

        if (null != drawable) {
            nav_bar.setBackgroundDrawable(drawable);
        }

        sendMsgRequest = new SendMsgRequest(this);  //创建SendMsgRequest类对象
        sendMsgRequest.addResponseListener(this);   //调用其监听方法

        initView();  //调用初始化方法
    }

    //初始化的方法
    private void initView() {
        btnOK = (Button) findViewById(R.id.btn_ok);  //绑定确定按钮
        imageBack = (LinearLayout)findViewById(R.id.close);   //绑头部定返回图标
        btnLocation = (Button)findViewById(R.id.farmland_location);   //绑定定位按钮
        takePhoto = (TextView)findViewById(R.id.take_photo);     //绑定拍照提示文字
        photo = (ImageView)findViewById(R.id.farmland_img);     //绑定照片显示框
        areaName = (EditText) findViewById(R.id.farmland_name);   //名称
        areaSize = (EditText) findViewById(R.id.farmland_size);   //面积（大小）
        areaLng = (TextView) findViewById(R.id.farmland_lng);     //纬度
        areaLat = (TextView) findViewById(R.id.farmland_lat);     //经度
        areaRemark = (EditText) findViewById(R.id.farmland_remark);   //说明

        imageBack.setOnClickListener(new View.OnClickListener() {  //绑定返回图标的监听事件
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent();
                intent.setClass(AddFarmlandActivity.this, FarmlandActivity.class);
                AddFarmlandActivity.this.startActivity(intent);*/
                finish();
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {  //绑定定位按钮的监听事件
            @Override
            public void onClick(View v) {
                doGetLocation();  //调用自定义的获取位置的方法
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {   //绑定确定按钮的监听事件
            @Override
            public void onClick(View v) {
                sendMsgRequest.addArea(areaName.getText().toString(), areaSize.getText().toString(), areaLng.getText().toString(),
                        areaLat.getText().toString(), areaRemark.getText().toString(),img);  //添加数据
                finish();  //关闭
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {   //绑定拍照提示文字的监听事件
            @Override
            public void onClick(View v) {
                doTakePhoto();  //调用自定义拍照的方法
            }
        });

    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) {

        if("add_area".equals(status.getRequestCode())){
            HashMap<String,Object> hashMap = new HashMap<String, Object>();
            hashMap.put("areaname",areaName.getText());
            hashMap.put("disabled",false);
            FarmlandActivity.sendMsgRequest.arrayFarmlandList.add(hashMap);
            FarmlandActivity.mAdapter.notifyDataSetChanged();
        }

    }

    //自定义的获取位置方法
    private void doGetLocation() {

        //获取到LocationManager对象
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //创建一个Criteria对象
        Criteria criteria = new Criteria();
        //设置粗略精确度
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        //设置是否需要返回海拔信息
        criteria.setAltitudeRequired(false);
        //设置是否需要返回方位信息
        criteria.setBearingRequired(false);
        //设置是否允许付费服务
        criteria.setCostAllowed(true);
        //设置电量消耗等级
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        //设置是否需要返回速度信息
        criteria.setSpeedRequired(false);
        //根据设置的Criteria对象，获取最符合此标准的provider对象
        String currentProvider = locationManager.getBestProvider(criteria, true);
        //根据当前provider对象获取最后一次位置信息
        Location currentLocation = locationManager.getLastKnownLocation(currentProvider);
        //如果位置信息为null，则请求更新位置信息
        if (currentLocation == null) {
            locationManager.requestLocationUpdates(currentProvider, 0, 0, locationListener);
        }
        //直到获得最后一次位置信息为止，如果未获得最后一次位置信息，则显示默认经纬度
        //每隔60秒获取一次位置信息
        while (true) {
            currentLocation = locationManager.getLastKnownLocation(currentProvider);
            if (currentLocation != null) {
                Log.d("Location", "Latitude: " + currentLocation.getLatitude());
                Log.d("Location", "location: " + currentLocation.getLongitude());
                String slongitude = String.valueOf(currentLocation.getLongitude());
                String slatitude = String.valueOf(currentLocation.getLatitude());
                slongitude = slongitude.substring(0,slongitude.indexOf(".")+3);
                slatitude = slatitude.substring(0,slatitude.indexOf(".")+3);
                areaLng.setText(slongitude);
                areaLat.setText(slatitude);
                break;
            } else {
                Log.d("Location", "Latitude: " + 0);
                Log.d("Location", "location: " + 0);
            }
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                Log.e("Location", e.getMessage());
            }
        }

        Geocoder geoCoder = new Geocoder(AddFarmlandActivity.this);
        try {
            int latitude = (int) currentLocation.getLatitude();
            int longitude = (int) currentLocation.getLongitude();
            List<Address> list = geoCoder.getFromLocation(latitude, longitude, 2);
            for (int i = 0; i < list.size(); i++) {
                Address address = list.get(i);
                Toast.makeText(AddFarmlandActivity.this, address.getCountryName() + address.getAdminArea() + address.getFeatureName(), Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            Toast.makeText(AddFarmlandActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //创建位置监听器
    private LocationListener locationListener = new LocationListener() {
        //位置发生改变时调用
        @Override
        public void onLocationChanged(Location location) {
            Log.d("Location", "onLocationChanged");
        }

        //provider失效时调用
        @Override
        public void onProviderDisabled(String provider) {
            Log.d("Location", "onProviderDisabled");
        }

        //provider启用时调用
        @Override
        public void onProviderEnabled(String provider) {
            Log.d("Location", "onProviderEnabled");
        }

        //状态改变时调用
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("Location", "onStatusChanged");
        }
    };


    private void doTakePhoto() {

        //调用系统照相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_CODE);

    }


    private Uri saveBitmap(Bitmap bm) {
        File tmpDir = new File(Environment.getExternalStorageDirectory() + "/formats");

        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }
        File img = new File(tmpDir.getAbsolutePath() + "/image" + ".JPEG");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.JPEG, 85, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //截取头像
    private void startImageZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 4);
        intent.putExtra("aspectY", 4);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }


    // 回调处理照相机返回的信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE){
            if (data == null) {
                return;
            } else {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap bm = extras.getParcelable("data");
                    Uri uri = saveBitmap(bm);
                    startImageZoom(uri);
                }
            }
        }else if(requestCode == CROP_REQUEST_CODE){
            if (data == null) {
                return;
            }
            Bundle extras = data.getExtras();
            if (extras == null) {
                return;
            }
            Bitmap bm = extras.getParcelable("data");
            photo.setImageBitmap(bm);
            img = Environment.getExternalStorageDirectory() + "/formats/image.JPEG";

        }
    }

}
