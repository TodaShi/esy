package cn.ecailan.esy.model;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ry8.CeaFrame.model.BaseModel;
import org.ry8.CeaFrame.model.BeeCallback;
import org.ry8.CeaFrame.view.MyProgressDialog;
import org.ry8.CommonFunc.Convert;
import org.ry8.external.androidquery.callback.AjaxStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 记事
 * @author leehy
 *
 */
public class M_ScheduleFood extends BaseModel{

    public M_ScheduleFood_Head tableHead=new M_ScheduleFood_Head() ;
    public JSONArray dataArray = new JSONArray();
    public static final int PER_PAGE = 1000;

    public List<HashMap<String,Object>> arrayScheduleFood=new ArrayList<HashMap<String,Object>>();

    public M_ScheduleFood(Context context)
    {
        super(context);
    }

    public void loadData(String month)
    {

        int page = 1;
        int per_page = PER_PAGE;

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>(){

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status)
            {
                if (status.getError()==null&& null != jo) {
                    arrayScheduleFood.clear();
                    if (jo.optString("status").equals("0")) {

                        try {
                            dataArray = jo.optJSONObject("data").optJSONObject("datatable").optJSONArray("rows");
                            tableHead.fromJson(jo.optJSONObject("data").optJSONObject("datatable").optJSONObject("header"));

                            for(int i=0;i<dataArray.length();i++){
                                JSONArray json=dataArray.getJSONArray(i);
                                HashMap<String,Object> hashMap=new HashMap<String,Object>();
                                hashMap.put("ScheduleFoodIDG",json.get(0));
                                hashMap.put("title",json.get(1));
                                hashMap.put("dates",json.get(2));
                                hashMap.put("sortnum",json.get(3));
                                hashMap.put("foodclass_name",json.get(4));
                                hashMap.put("foodclass_img",json.get(5));
                                hashMap.put("foodclass_idg",json.get(6));
                                arrayScheduleFood.add(hashMap);
                            }
                            M_ScheduleFood.this.OnMessageResponse(url, jo, status);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                M_ScheduleFood.this.callback(url, jo, status);
            }

        };
        SharedPreferences shared = mContext.getSharedPreferences("userInfo", 0);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("func", "getschedulefood");
        params.put("devicecode", shared.getString("DeviceCode", ""));
        params.put("memberid", shared.getInt("memberid", -1));

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("sdate", month);

        JSONObject jsonData=new JSONObject(data);
        params.put("data",jsonData);

        JSONObject jsonObj = new JSONObject(params);

        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj.toString());

        cb.requestCode(M_ScheduleFood.class.toString()).type(JSONObject.class).params(paramsTo);

        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍后...");
        aq.progress(mPro.mDialog).ajax(cb);
    }


    public ArrayList<HashMap<String,Object>>  getData(Date date) {
        ArrayList<HashMap<String,Object>> ls=new ArrayList<HashMap<String,Object>>();
        String sdate= Convert.toDateString(date);
        for(int i=0;i<arrayScheduleFood.size();i++)
        {
//            JSONArray js=dataArray.optJSONArray(i);
            if(arrayScheduleFood.get(i)!=null)
            {
                String st=arrayScheduleFood.get(i).get("dates").toString();
                if(st.startsWith(sdate))
                {
                    ls.add(arrayScheduleFood.get(i));
                }
            }
        }
        return ls;
    }
}
