
package cn.ecailan.esy.model;

import org.json.JSONException;
import org.json.JSONObject;

//@Table(name = "AttendRecord_Head")
public class M_ClassBlog_Head// extends Model
{
    public int classblog_idg;
    public int DeviceCode;
    public int classid;
    public int title;
    public int body;
    public int views_count;
    public int likes_count;
    public int comments_count;
    public int memberid;
    public int usertype;
    public int addtime;
    public int islikes;
    public int picture;
    public int name;
    public int rownum;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.picture = jsonObject.optInt("picture", -1);
        this.name = jsonObject.optInt("name", -1);
        this.rownum = jsonObject.optInt("rownum", -1);

        this.likes_count = jsonObject.optInt("likes_count", -1);
        this.comments_count = jsonObject.optInt("comments_count", -1);
        this.memberid = jsonObject.optInt("memberid", -1);
        this.usertype = jsonObject.optInt("usertype", -1);
        this.addtime = jsonObject.optInt("addtime", -1);
        this.islikes = jsonObject.optInt("islikes", -1);


        this.classblog_idg = jsonObject.optInt("classblog_idg", -1);
        this.DeviceCode = jsonObject.optInt("DeviceCode", -1);
        this.classid = jsonObject.optInt("classid", -1);
        this.title = jsonObject.optInt("title", -1);
        this.body = jsonObject.optInt("body", -1);
        this.views_count = jsonObject.optInt("views_count", -1);

        return;
    }


    //public JSONObject  toJson() throws JSONException
    //{
    //JSONObject localItemObject = new JSONObject();
     /*JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("id", id);
     localItemObject.put("shop_price", shop_price);
     localItemObject.put("market_price", market_price);
     localItemObject.put("name", name);
     if(null!=img)
     {
       localItemObject.put("img", img.toJson());
     }
     localItemObject.put("brief", brief);
     localItemObject.put("promote_price", promote_price);
     localItemObject.put("goods_id", goods_id);*/
    //return localItemObject;
    //}

}
