package cn.ecailan.esy.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by John on 2015/9/7.
 */
public class S_Operation_Head {

    public int operateidg;
    public int opname;
    public int optime;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        this.operateidg = jsonObject.optInt("operateidg", -1);
        this.opname = jsonObject.optInt("opname", -1);
        this.optime = jsonObject.optInt("optime", -1);


        return;


    }

}
