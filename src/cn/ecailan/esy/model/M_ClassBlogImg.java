package cn.ecailan.esy.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by John on 2015/8/3.
 */
public class M_ClassBlogImg {
    public int classblog_idg;
    public int filehash;
    public int rownum;

    public void fromJson(JSONObject jsonObject)throws JSONException{
        if (null == jsonObject) {
            return;
        }
        this.classblog_idg = jsonObject.optInt("classblog_idg",-1);
        this.filehash = jsonObject.optInt("filehash",-1);
        this.rownum = jsonObject.optInt("rownum",-1);

        return;
    }



}
