package cn.ecailan.esy.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ry8.CeaFrame.model.BaseModel;
import org.ry8.CeaFrame.model.BeeCallback;
import org.ry8.CeaFrame.view.MyProgressDialog;
import org.ry8.external.androidquery.callback.AjaxStatus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cn.ecailan.esy.R;
import cn.ecailan.esy.fragment.OperationFragment;
import cn.ecailan.esy.fragment.PurchaseFragment;

/**
 * Created by John on 2015/9/2.
 */
public class SendMsgRequest extends BaseModel {


    //获取历史信息
    public ArrayList<HashMap<String, Object>> historyArrayList = new ArrayList<HashMap<String, Object>>();
    public S_History_Head s_history_head = new S_History_Head();
    public JSONArray arrayHistory;

    //种植清单
    public ArrayList<HashMap<String, Object>> plantArrayList = new ArrayList<HashMap<String, Object>>();
    public S_Plant_Head s_plant_head = new S_Plant_Head();
    public JSONArray arrayPlant;
    //博元控制操作项
    public ArrayList<HashMap<String, Object>> operationArrayList = new ArrayList<HashMap<String, Object>>();
    public S_Operation_Head s_operation_head = new S_Operation_Head();
    public JSONArray arrayOperation;

    //产品
    public ArrayList<HashMap<String, Object>> arrayProductList = new ArrayList<HashMap<String, Object>>();
    public S_Product_Head s_product_head = new S_Product_Head();
    public JSONArray productArray;
    //地块
    public S_Farmland_Head s_farmland_head = new S_Farmland_Head();
    public ArrayList<HashMap<String, Object>> arrayFarmlandList = new ArrayList<HashMap<String, Object>>(); //地块集合（存储地块的信息）
    public JSONArray farmlandArray;

    private Context context;
    public SharedPreferences shared;

    public SendMsgRequest(Context context) {
        super(context);
        this.context = context;
    }


    /**
     * 输入流转 byte[]
     */
    public static byte[] read(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        int length = inStream.available();
        byte[] buffer = new byte[length];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    // 获取种植清单
    public void getPlant() {

        BeeCallback<JSONObject> bc = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendMsgRequest.this.callback(url, jo, status);
                if (null != jo) {
                    plantArrayList.clear();
                    try {
                        s_plant_head.fromJson(jo.getJSONObject("data").getJSONObject("datatable").getJSONObject("header"));
                        arrayPlant = jo.getJSONObject("data").getJSONObject("datatable").getJSONArray("rows");
                        for (int i = 0; i < arrayPlant.length(); i++) {
                            JSONArray jsonArray = arrayPlant.getJSONArray(i);
                            HashMap<String, Object> hashMap = new HashMap<String, Object>();
                            hashMap.put("traceidg", jsonArray.get(s_plant_head.traceidg));
                            hashMap.put("traceno", jsonArray.get(s_plant_head.traceno));
                            hashMap.put("addtime", jsonArray.get(s_plant_head.addtime));
                            hashMap.put("areaidg", jsonArray.get(s_plant_head.areaidg));
                            hashMap.put("useridg", jsonArray.get(s_plant_head.useridg));
                            hashMap.put("productidg", jsonArray.get(s_plant_head.productidg));
                            hashMap.put("output", jsonArray.get(s_plant_head.output));
                            plantArrayList.add(hashMap);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    SendMsgRequest.this.OnMessageResponse(url, jo, status);

                }
            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("func", "get_plant");
        params.put("memberid",shared.getString("useridg", ""));
        params.put("usertype",shared.getInt("usertypeid", -1));
        JSONObject jsonObj = new JSONObject(params);
        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj.toString());
        bc.url("").type(JSONObject.class).params(paramsTo).requestCode("get_plant");
        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍等");
        aq.progress(mPro.mDialog).ajax(bc);
    }


    public void getOpname() {
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendMsgRequest.this.callback(url, jo, status);
                if (null != jo) {
                    operationArrayList.clear();
                    try {
                        s_operation_head.fromJson(jo.getJSONObject("data").getJSONObject("datatable").getJSONObject("header"));
                        arrayOperation = jo.getJSONObject("data").getJSONObject("datatable").getJSONArray("rows");
                        for (int i = 0; i < arrayOperation.length(); i++) {
                            JSONArray jsonArray = arrayOperation.getJSONArray(i);
                            HashMap<String, Object> hashMap = new HashMap<String, Object>();
                            hashMap.put("operateidg", jsonArray.get(s_operation_head.operateidg));
                            hashMap.put("opname", jsonArray.get(s_operation_head.opname));
                            hashMap.put("optime", jsonArray.get(s_operation_head.optime));
                            operationArrayList.add(hashMap);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("func", "get_opname");
        params.put("memberid", shared.getString("useridg",""));
        params.put("usertype", shared.getInt("usertypeid", -1));
        Map<String, Object> paramsTo = new HashMap<String, Object>();
        JSONObject jsonObj = new JSONObject(params);
        paramsTo.put("msg", jsonObj);
        cb.url("").type(JSONObject.class).params(paramsTo).requestCode("get_opname");
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }

    //增添操作项
    public void addOpname(String opname) {

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendMsgRequest.this.callback(url, jo, status);
                if (status.getError() == null && null != jo) {

                    SendMsgRequest.this.OnMessageResponse(url, jo, status);
                }
            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("func", "add_opname");
        params.put("memberid",shared.getString("useridg", ""));
        params.put("usertype",shared.getInt("usertypeid", -1));
        Map<String, Object> params2 = new HashMap<String, Object>();
        params2.put("opname", opname);
        JSONObject jsonObj3 = new JSONObject(params2);
        params.put("data", jsonObj3);
        Map<String, Object> paramsTo = new HashMap<String, Object>();
        JSONObject jsonObj = new JSONObject(params);
        paramsTo.put("msg", jsonObj);

        cb.url("").type(JSONObject.class).params(paramsTo).requestCode("add_opname");
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }


    //删除操作项
    public void delOpname(String opname) {

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendMsgRequest.this.callback(url, jo, status);

                if (status.getError() == null && null != jo) {

                    SendMsgRequest.this.OnMessageResponse(url, jo, status);
                }

            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("func", "del_opname");

        params.put("memberid",shared.getString("useridg", ""));
        params.put("usertype",shared.getInt("usertypeid", -1));
        Map<String, Object> params2 = new HashMap<String, Object>();
        params2.put("opname", opname);
        JSONObject jsonObj3 = new JSONObject(params2);
        params.put("data", jsonObj3);
        Map<String, Object> paramsTo = new HashMap<String, Object>();
        JSONObject jsonObj = new JSONObject(params);
        paramsTo.put("msg", jsonObj);

        cb.url("").type(JSONObject.class).params(paramsTo).requestCode("del_opname");
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    public void getProduct(){
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendMsgRequest.this.callback(url, jo, status);
                if(null != jo) {
                    arrayProductList.clear();
                    if (status.getError() == null && null != jo){
                        try {
                            s_product_head.fromJson(jo.getJSONObject("data").getJSONObject("datatable").getJSONObject("header"));
                            productArray = jo.getJSONObject("data").getJSONObject("datatable").getJSONArray("rows");
                            for (int i=0 ; i<productArray.length();i++){
                                JSONArray jsonArray = (JSONArray) productArray.get(i);
                                //给集合赋值
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("productidg",jsonArray.get(s_product_head.productidg));
                                hashMap.put("name",jsonArray.get(s_product_head.name));
                                hashMap.put("classid",jsonArray.get(s_product_head.classid));
                                hashMap.put("picpath",jsonArray.get(s_product_head.picpath));
                                hashMap.put("price",jsonArray.get(s_product_head.price));
                                hashMap.put("barCode",jsonArray.get(s_product_head.barCode));
                                hashMap.put("stock",jsonArray.get(s_product_head.stock));

                                arrayProductList.add(hashMap);  //把Map集合信息添加到list集合中

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        SendMsgRequest.this.OnMessageResponse(url, jo, status);

                    }
                }

            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("func", "get_product");
        params.put("memberid",shared.getString("useridg", ""));
        params.put("usertype",shared.getInt("usertypeid", -1));
        Map<String, Object> paramsTo = new HashMap<String, Object>();
        JSONObject jsonObj = new JSONObject(params);
        paramsTo.put("msg", jsonObj);

        cb.url("").type(JSONObject.class).params(paramsTo).requestCode("get_product");
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }


    //获取种植过程
    public void getTraceitem(String traceidg){
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendMsgRequest.this.callback(url, jo, status);
                if(null != jo) {
                    historyArrayList.clear();
                    if (status.getError() == null && null != jo){
                        try {
                            s_history_head.fromJson(jo.getJSONObject("data").getJSONObject("datatable").getJSONObject("header"));
                            arrayHistory = jo.getJSONObject("data").getJSONObject("datatable").getJSONArray("rows");
                            for (int i=0 ; i<arrayHistory.length();i++){
                                JSONArray jsonArray = (JSONArray) arrayHistory.get(i);
                                //给集合赋值
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("traceitemidg",jsonArray.get(s_history_head.traceitemidg));
                                hashMap.put("traceidg",jsonArray.get(s_history_head.traceidg));
                                hashMap.put("opname",jsonArray.get(s_history_head.opname));
                                hashMap.put("remark",jsonArray.get(s_history_head.remark));
                                hashMap.put("img",jsonArray.get(s_history_head.img));
                                hashMap.put("addtime",jsonArray.get(s_history_head.addtime));

                                historyArrayList.add(hashMap);  //把Map集合信息添加到list集合中

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        SendMsgRequest.this.OnMessageResponse(url, jo, status);

                    }
                }
            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("func", "get_traceitem");
        params.put("memberid",shared.getString("useridg", ""));
        params.put("usertype",shared.getInt("usertypeid", -1));
        Map<String, Object> params2 = new HashMap<String, Object>();
        params2.put("traceidg", traceidg);
        JSONObject jsonObj3 = new JSONObject(params2);
        params.put("data", jsonObj3);
        Map<String, Object> paramsTo = new HashMap<String, Object>();
        JSONObject jsonObj = new JSONObject(params);
        paramsTo.put("msg", jsonObj);

        cb.url("").type(JSONObject.class).params(paramsTo).requestCode("get_traceitem");
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }



    //增添种植过程
    public void addTraceitem(String opname,String remark,String traceidg,String img) {

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendMsgRequest.this.callback(url, jo, status);

                if (status.getError() == null && null != jo) {
                    Toast.makeText(context,"上传成功",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context,"上传失败，请重试",Toast.LENGTH_LONG).show();
                }
                SendMsgRequest.this.OnMessageResponse(url, jo, status);

            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("func", "add_traceitem");
        params.put("memberid",shared.getString("useridg", ""));
        params.put("usertype",shared.getInt("usertypeid", -1));
        Map<String, Object> params2 = new HashMap<String, Object>();
        UUID uuid = UUID.randomUUID();
        params2.put("traceitemidg", uuid);//全球统一标识符
        params2.put("opname", opname);
        params2.put("remark", remark);
        params2.put("traceidg", traceidg);
        JSONObject jsonObj3 = new JSONObject(params2);
        params.put("data", jsonObj3);
        FileInputStream fStream = null;
        try {
            fStream = new FileInputStream(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Map<String, Object> paramsTo = new HashMap<String, Object>();
        JSONObject jsonObj = new JSONObject(params);
        paramsTo.put("msg", jsonObj);
        paramsTo.put(img, fStream);
        cb.url("").type(JSONObject.class).params(paramsTo).requestCode("add_traceitem");
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }



    //增加种植列表
    public void addPlant(String tracno,String areaidg,String productidg) {

        BeeCallback<JSONObject> bc = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendMsgRequest.this.callback(url, jo, status);
                if (null != jo) {

                    SendMsgRequest.this.OnMessageResponse(url, jo, status);

                }
            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("func", "add_plant");
        params.put("memberid",shared.getString("useridg", ""));
        params.put("usertype",shared.getInt("usertypeid", -1));
        Map<String, Object> params3 = new HashMap<String, Object>();
        UUID uuid = UUID.randomUUID();
        params3.put("plantidg", uuid);//全球统一标识符
        params3.put("traceno", tracno);//内容
        params3.put("areaidg", areaidg);
        params3.put("productidg",productidg);
        JSONObject jsonObj3 = new JSONObject(params3);
        params.put("data", jsonObj3);
        JSONObject jsonObj = new JSONObject(params);
        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj.toString());
        bc.url("").type(JSONObject.class).params(paramsTo).requestCode("add_plant");
        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍等");
        aq.progress(mPro.mDialog).ajax(bc);
    }

    //采收数量
    public void setProductOutput(String output){
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendMsgRequest.this.callback(url, jo, status);

                if (status.getError() == null && null != jo) {

                    SendMsgRequest.this.OnMessageResponse(url, jo, status);
                }


            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("func", "set_product_output");
        params.put("memberid",shared.getString("useridg", ""));
        params.put("usertype",shared.getInt("usertypeid", -1));
        Map<String, Object> params2 = new HashMap<String, Object>();
        UUID uuid = UUID.randomUUID();
        params2.put("traceitemidg", uuid);//全球统一标识符
        params2.put("output",output);
        JSONObject jsonObj3 = new JSONObject(params2);
        params.put("data", jsonObj3);
        Map<String, Object> paramsTo = new HashMap<String, Object>();
        JSONObject jsonObj = new JSONObject(params);
        paramsTo.put("msg", jsonObj);

        cb.url("").type(JSONObject.class).params(paramsTo).requestCode("set_product_output");
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }


    //获取地块信息
    public void getArea(){
        BeeCallback<JSONObject> bc = new BeeCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status){
                SendMsgRequest.this.callback(url, jo, status);

                if(null != jo) {
                    arrayFarmlandList.clear();   //清空arrayFarmlandList（地块）数组
                    if (status.getError() == null && null != jo){
                        try {
                            s_farmland_head.fromJson(jo.getJSONObject("data").getJSONObject("datatable").getJSONObject("header"));
                            farmlandArray = jo.getJSONObject("data").getJSONObject("datatable").getJSONArray("rows");
                            for (int i=0 ; i<farmlandArray.length();i++){
                                JSONArray jsonArray = (JSONArray) farmlandArray.get(i);
                                //给集合赋值
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("areaidg",jsonArray.get(s_farmland_head.areaidg));  //地块Id
                                hashMap.put("useridg",jsonArray.get(s_farmland_head.useridg));  //拥有者（用户Id）
                                hashMap.put("areaname",jsonArray.get(s_farmland_head.areaname)); //地块名称
                                hashMap.put("size",jsonArray.get(s_farmland_head.size));  //地块大小
                                hashMap.put("lat",jsonArray.get(s_farmland_head.lat));    //百度坐标  纬度
                                hashMap.put("lng",jsonArray.get(s_farmland_head.lng));    //百度坐标  经度
                                hashMap.put("remark",jsonArray.get(s_farmland_head.remark));  //地块说明
                                hashMap.put("disabled",jsonArray.get(s_farmland_head.disabled));  //地块是否停用（布尔量）
                                hashMap.put("addtime",jsonArray.get(s_farmland_head.addtime));  //添加时间
                                hashMap.put("optime",jsonArray.get(s_farmland_head.optime));    //最后操作时间
                                arrayFarmlandList.add(hashMap);  //把Map集合信息添加到list集合中

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        SendMsgRequest.this.OnMessageResponse(url, jo, status);

                    }
                }
            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("func", "get_area");
        params.put("memberid",shared.getString("useridg", ""));
        params.put("usertype",shared.getInt("usertypeid", -1));
        Map<String, Object> paramsTo = new HashMap<String, Object>();
        JSONObject jsonObj = new JSONObject(params);//其他信息
        paramsTo.put("msg", jsonObj);

        bc.url("").type(JSONObject.class).params(paramsTo).requestCode("get_area");
        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍等");
        aq.progress(mPro.mDialog).ajax(bc);

    }

    //删除地块
    public void delArea(String area_idg){
        BeeCallback<JSONObject> bc = new BeeCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status){
                if(status.getError() == null && jo != null){
                    SendMsgRequest.this.OnMessageResponse(url, jo, status);
                }
            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("func", "del_area");
        params.put("memberid",shared.getString("useridg", ""));
        params.put("usertype",shared.getInt("usertypeid", -1));
        Map<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("area_idg", area_idg);
        JSONObject jsonObjTo = new JSONObject(paramsTo);
        params.put("data", jsonObjTo);

        JSONObject jsonObj = new JSONObject();
        HashMap<String, Object> paramsThr = new HashMap<String, Object>();
        paramsThr.put("msg", jsonObj.toString());

        bc.url("").type(JSONObject.class).params(paramsThr).requestCode("del_area \n"+ area_idg);
        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍等");
        aq.progress(mPro.mDialog).ajax(bc);
    }


    //增添地块
    public void addArea(String areaname, String size, String lat, String lng, String remark,String img){
        BeeCallback<JSONObject> bc = new BeeCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status){
                SendMsgRequest.this.callback(url, jo, status);
                if (null != jo){
                    arrayFarmlandList.clear();
                    if (status.getError() == null && jo != null){
                        SendMsgRequest.this.OnMessageResponse(url, jo, status);
                    }
                }
            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("func", "add_area");
        params.put("memberid",shared.getString("useridg", ""));
        params.put("usertype",shared.getInt("usertypeid", -1));
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("areaname", areaname);
        data.put("size", size);
        data.put("lat", "0");
        data.put("lng", "0");
        data.put("remark", remark);

        UUID uuid = UUID.randomUUID();
        data.put("areaidg", uuid);//全球统一标识符
        JSONObject dataObj = new JSONObject(data);
        params.put("data", dataObj);
        FileInputStream fStream = null;
        try {
            fStream = new FileInputStream(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(params);
        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObject.toString());
        paramsTo.put(img,fStream);

        bc.url("").type(JSONObject.class).params(paramsTo).requestCode("add_area");
        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍等");
        aq.progress(mPro.mDialog).ajax(bc);

    }

}
