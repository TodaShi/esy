package cn.ecailan.esy.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONObject;
import org.ry8.CeaFrame.model.BusinessResponse;
import org.ry8.external.androidquery.callback.AjaxStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.ecailan.esy.R;
import cn.ecailan.esy.model.SendMsgRequest;

/**
 * Created by Myuluo on 2015/9/9.
 * 农户种植过程 操作界面2
 */
public class PlantProcessDetailActivity extends Activity implements BusinessResponse {

    private static int REQUEST_CODE = 1;
    private static int CROP_REQUEST_CODE = 2;
    private EditText remark;
    private ImageView photo;
    private Button btnOk;
    private String opname;
    private String traceidg;
    private SendMsgRequest sendMsgRequest;
    private LinearLayout close;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_process_detail_layout);
        sendMsgRequest = new SendMsgRequest(this);
        sendMsgRequest.addResponseListener(this);
        opname = getIntent().getStringExtra("opname");
        traceidg = getIntent().getStringExtra("traceidg");
        initView();
    }

    private void initView() {
        remark = (EditText)findViewById(R.id.plant_process_detail_layout_edit);
        photo = (ImageView)findViewById(R.id.plant_process_detail_layout_img);
        btnOk = (Button)findViewById(R.id.plant_process_detail_layout_btn);
        close = (LinearLayout)findViewById(R.id.close);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMsgRequest.addTraceitem(opname,remark.getText().toString(),traceidg,path);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) {

    }


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
        intent.putExtra("aspectX", 3);
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
            path = Environment.getExternalStorageDirectory() + "/formats/iamge.JPEG";

        }
    }



}
