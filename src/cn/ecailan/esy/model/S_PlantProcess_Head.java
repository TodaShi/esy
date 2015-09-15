package cn.ecailan.esy.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Myuluo on 2015/9/8.
 */
public class S_PlantProcess_Head {
    public int traceidg;
    public int traceno;
    public int addtime;
    public int areaidg;
    public int useridg;
    public int productidg;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.traceidg = jsonObject.optInt("traceidg", -1);
        this.traceno = jsonObject.optInt("traceno", -1);
        this.addtime = jsonObject.optInt("addtime", -1);
        this.areaidg = jsonObject.optInt("areaidg", -1);
        this.useridg = jsonObject.optInt("useridg", -1);
        this.productidg = jsonObject.optInt("productidg", -1);

        return;
    }
}
