package cn.ecailan.esy.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by John on 2015/9/12.
 */
public class S_History_Head  {

    public int traceitemidg;
    public int traceidg;
    public int opname;
    public int remark;
    public int img;
    public int addtime;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.traceitemidg = jsonObject.optInt("traceitemidg", -1);
        this.traceidg = jsonObject.optInt("traceidg", -1);
        this.opname = jsonObject.optInt("opname", -1);
        this.remark = jsonObject.optInt("remark", -1);
        this.img = jsonObject.optInt("img", -1);
        this.addtime = jsonObject.optInt("addtime", -1);

        return;


    }

}
