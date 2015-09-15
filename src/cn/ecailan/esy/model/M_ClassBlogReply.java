package cn.ecailan.esy.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by John on 2015/8/3.
 */
public class M_ClassBlogReply {

    public int comment_id;
    public int DeviceCode;
    public int classblog_idg;
    public int likes_count;
    public int body;
    public int memberid;
    public int usertype;
    public int addtime;
    public int picture;
    public int name;
    public int rownum;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.comment_id = jsonObject.optInt("comment_id", -1);
        this.DeviceCode = jsonObject.optInt("DeviceCode", -1);
        this.classblog_idg = jsonObject.optInt("classblog_idg", -1);
        this.likes_count = jsonObject.optInt("likes_count", -1);
        this.body = jsonObject.optInt("body", -1);
        this.usertype = jsonObject.optInt("usertype", -1);
        this.addtime = jsonObject.optInt("addtime", -1);
        this.picture = jsonObject.optInt("picture", -1);
        this.name = jsonObject.optInt("name", -1);
        this.rownum = jsonObject.optInt("rownum", -1);

        return;
    }


}
