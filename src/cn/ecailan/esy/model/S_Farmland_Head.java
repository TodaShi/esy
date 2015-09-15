package cn.ecailan.esy.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jiang on 2015/9/2.
 */
public class S_Farmland_Head {

    //定义属性
    public int areaidg;
    public int useridg;
    public int areaname;
    public int size;
    public int lat;
    public int lng;
    public int remark;
    public int disabled;
    public int addtime;
    public int optime;

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }
        this.areaidg = jsonObject.optInt("areaidg",-1);
        this.useridg = jsonObject.optInt("useridg",-1);
        this.areaname = jsonObject.optInt("areaname",-1);
        this.size = jsonObject.optInt("size",-1);
        this.lat = jsonObject.optInt("lat",-1);
        this.lng = jsonObject.optInt("lng",-1);
        this.remark = jsonObject.optInt("remark",-1);
        this.disabled = jsonObject.optInt("disabled",-1);
        this.addtime = jsonObject.optInt("addtime",-1);
        this.optime = jsonObject.optInt("optime",-1);

        return ;
    }


}
