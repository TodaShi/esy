package cn.ecailan.esy.model;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ry8.CeaFrame.model.BaseModel;
import org.ry8.CeaFrame.model.BeeCallback;
import org.ry8.CeaFrame.view.MyProgressDialog;
import org.ry8.CommonFunc.Interaction;
import org.ry8.external.androidquery.callback.AjaxStatus;

import java.util.HashMap;
import java.util.UUID;

/**
 * 记事
 * @author leehy
 *
 */
public class M_ScheduleFood_Item extends BaseModel {
    public M_ScheduleFood_Item(Context context) {
        super(context);
    }

    public String ScheduleFoodIDG;//id
    public String title = "";//标题
    public String context = "";//内容
    public String dates = "";//日期 yyyy-MM-dd
    public int ClassID = -1;//
    public int sortnum = -1;//
    public String addtime = "";
    public String DeviceCode = "";
    public int memberid = -1;//


    public M_ScheduleFood_Item fromJson(JSONArray jsonArray, M_ScheduleFood_Head h) {
        if (null == jsonArray) {
            return this;
        }
        this.ScheduleFoodIDG = jsonArray.optString(h.ScheduleFoodIDG, "");
        this.title = jsonArray.optString(h.title, "");
        this.context = jsonArray.optString(h.context, "");
        this.dates = jsonArray.optString(h.dates, "");
        this.ClassID = jsonArray.optInt(h.ClassID, -1);
        this.sortnum = jsonArray.optInt(h.sortnum, -1);
        this.addtime = jsonArray.optString(h.addtime, "");
        this.DeviceCode = jsonArray.optString(h.DeviceCode, "");
        this.memberid = jsonArray.optInt(h.memberid, -1);
        return this;
    }

    public void upSave(String type) {
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                M_ScheduleFood_Item.this.callback(url, jo, status);
                if (status.getError() == null && null != jo) {
                    if (jo.optString("status").equals("0")) {
                        Interaction.toastShow(M_ScheduleFood_Item.this.mContext, "保存成功");
                    } else {
                        Interaction.toastShow(M_ScheduleFood_Item.this.mContext, "保存失败");
                    }
                }
                M_ScheduleFood_Item.this.OnMessageResponse(url, jo, status);

            }
        };

        SharedPreferences shared = mContext.getSharedPreferences("userInfo", 0);
        HashMap<String, Object> params = new HashMap<String, Object>();
        if (type.equals("update"))
            params.put("func", "update_schedulefood");
        if (type.equals("add"))
            params.put("func", "add_schedulefood");
        if (type.equals("del"))
            params.put("func", "del_schedulefood");
        params.put("devicecode", shared.getString("DeviceCode", ""));
        params.put("memberid", shared.getInt("memberid", -1));//shared.getInt("memberid",-1)

        HashMap<String, Object> data = new HashMap<String, Object>();
        if (type.equals("update"))
            data.put("ScheduleFoodIDG", ScheduleFoodIDG);
        if (type.equals("add"))
            data.put("ScheduleFoodIDG", UUID.randomUUID());

        if (type.equals("del"))
            data.put("ScheduleFoodIDG", ScheduleFoodIDG);
        else {
            data.put("title", title);
            data.put("context", context);
            data.put("dates", dates);
            data.put("sortnum", 0);
            data.put("ClassID", 1);
        }
        JSONObject jsonData=new JSONObject(data);
        params.put("data", jsonData);

        JSONObject jsonObj = new JSONObject(params);

        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj.toString());

        cb.requestCode(M_ScheduleFood_Item.class.toString()).type(JSONObject.class).params(paramsTo);

        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍后...");
        aq.progress(mPro.mDialog).ajax(cb);
    }
}
