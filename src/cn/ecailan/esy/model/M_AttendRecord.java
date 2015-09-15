package cn.ecailan.esy.model;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
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

/*
 *	 ______    ______    ______
 *	/\  __ \  /\  ___\  /\  ___\
 *	\ \  __<  \ \  __\_ \ \  __\_
 *	 \ \_____\ \ \_____\ \ \_____\
 *	  \/_____/  \/_____/  \/_____/
 *
 *
 *	Copyright (c) 2013-2014, {Bee} open source community
 *	http://www.bee-framework.com
 *
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a
 *	copy of this software and associated documentation files (the "Software"),
 *	to deal in the Software without restriction, including without limitation
 *	the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *	and/or sell copies of the Software, and to permit persons to whom the
 *	Software is furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in
 *	all copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *	FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 *	IN THE SOFTWARE.
 */

public class M_AttendRecord extends BaseModel
{
    public static int NewAttendCount;
    public M_AttendRecord_Head tableHead=new M_AttendRecord_Head() ;
    public JSONArray dataArray = new JSONArray();
    public List<HashMap<String,Object>> data_ttArray=new ArrayList<HashMap<String, Object>>();
    public static final int PER_PAGE = 1000;

    public M_AttendRecord(Context context)
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
                if (jo.optString("status").equals("0")) {

                    try {
                        dataArray = jo.optJSONObject("data").optJSONObject("datatable").optJSONArray("rows");
                        tableHead.fromJson(jo.optJSONObject("data").optJSONObject("datatable").optJSONObject("header"));
                        M_AttendRecord.this.OnMessageResponse(url, jo, status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
                M_AttendRecord.this.callback(url, jo, status);
            }

        };
        SharedPreferences shared = mContext.getSharedPreferences("userInfo", 0);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("func", "getattendrecord");
        params.put("devicecode", shared.getString("DeviceCode", ""));
        params.put("memberid", shared.getInt("memberid", -1));

            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("smonth", month);
            JSONObject jsondata=new JSONObject(data);
            params.put("data",jsondata);

        JSONObject jsonObj = new JSONObject(params);

        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj.toString());

        cb.requestCode(M_AttendRecord.class.toString()).type(JSONObject.class).params(paramsTo);

        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍后...");
        aq.progress(mPro.mDialog).ajax(cb);
    }

    public void queryHasNew()
    {
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status)
            {

                if (status.getError()==null&& null != jo) {
                    if (jo.optString("status").equals("0")) {

                        try {
                            NewAttendCount = jo.optJSONObject("data").optJSONObject("model").optInt("cc");
                            M_AttendRecord.this.OnMessageResponse(url, jo, status);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                M_AttendRecord.this.callback(url, jo, status);
            }
        };
        SharedPreferences shared = mContext.getSharedPreferences("userInfo", 0);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("func", "getnewattendcount");
        params.put("devicecode", shared.getString("DeviceCode", ""));
        params.put("memberid", shared.getInt("memberid", -1));

        JSONObject jsonObj = new JSONObject(params);

        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj.toString());

        cb.requestCode(M_AttendRecord.class.toString()+"_queryHasNew").type(JSONObject.class).params(paramsTo);

        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍后...");
        aq.progress(mPro.mDialog).ajax(cb);
    }

    public ArrayList<JSONArray>  getData(Date date) {
        ArrayList<JSONArray> ls=new ArrayList<JSONArray>();
        String sdate= Convert.toDateString(date);
        for(int i=0;i<dataArray.length();i++)
        {
            JSONArray js=dataArray.optJSONArray(i);
            if(js!=null)
            {
                String st=js.optString(tableHead.AttendanceTime);
                if(st.startsWith(sdate))
                {
                    ls.add(js);
                }
            }
        }
        return ls;
    }


public void teatherLoad(String month){
    BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>(){

        @Override
        public void callback(String url, JSONObject jo, AjaxStatus status)
        {

            if (status.getError()==null&& null != jo) {
                if (jo.optString("status").equals("0")) {
                    data_ttArray.clear();
                    try {
                        JSONArray rows = jo.optJSONObject("data").optJSONObject("datatable").optJSONArray("rows");
                        for(int i=0;i<rows.length();i++){
                            JSONArray json=rows.getJSONArray(i);
                            HashMap<String,Object> hashMap=new HashMap<String,Object>();
                            hashMap.put("StudentID",json.get(0));
                            hashMap.put("StudentName",json.get(1));
                            hashMap.put("isattend",json.get(2));
                            hashMap.put("AttendanceTime",json.get(3));
                            data_ttArray.add(hashMap);

                        }
                        M_AttendRecord.this.OnMessageResponse(url, jo, status);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            M_AttendRecord.this.callback(url, jo, status);
        }

    };
    SharedPreferences shared = mContext.getSharedPreferences("userInfo", 0);
    HashMap<String, Object> params = new HashMap<String, Object>();
    params.put("func", "get_calss_attend_byday");
    params.put("devicecode", shared.getString("DeviceCode", ""));
    params.put("memberid", shared.getInt("memberid", -1));

    HashMap<String, Object> data = new HashMap<String, Object>();
    data.put("sdate", month);
    data.put("classid",shared.getInt("ClassID", -1));
    JSONObject jsondata=new JSONObject(data);
    params.put("data",jsondata);

    JSONObject jsonObj = new JSONObject(params);

    HashMap<String, Object> paramsTo = new HashMap<String, Object>();
    paramsTo.put("msg", jsonObj.toString());

    cb.requestCode(M_AttendRecord.class.toString()).type(JSONObject.class).params(paramsTo);

    MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍后...");
    aq.progress(mPro.mDialog).ajax(cb);

}
}
