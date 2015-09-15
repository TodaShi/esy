
package cn.ecailan.esy.model;
import org.json.JSONObject;

import java.io.Serializable;

//@Table(name = "ScheduleFood_Head")
public class M_ScheduleFood_Head  implements Serializable// extends Model
{
//     @Column(name = "id",unique = true)
     public int ScheduleFoodIDG;
     
//     @Column(name = "DeviceCode")
     public int DeviceCode;

//     @Column(name = "ClassID")
     public int ClassID;

//     @Column(name = "dates")
     public int   dates;

//    @Column(name = "sortnum")
    public int   sortnum;

//     @Column(name = "title")
     public int title;

    //     @Column(name = "context")
    public int context;
     
//     @Column(name = "addtime")
 	public int addtime;

    //     @Column(name = "memberid")
    public int memberid;


 public void  fromJson(JSONObject jsonObject)
 {
     if(null == jsonObject){
       return ;
      }

     this.ScheduleFoodIDG = jsonObject.optInt("ScheduleFoodIDG",-1);
     this.DeviceCode = jsonObject.optInt("DeviceCode",-1);
     this.ClassID = jsonObject.optInt("ClassID",-1);
     this.dates = jsonObject.optInt("dates",-1);
     this.sortnum = jsonObject.optInt("sortnum",-1);
     this.context = jsonObject.optInt("context",-1);
     this.title = jsonObject.optInt("title",-1);
     this.addtime=jsonObject.optInt("addtime",-1);
     this.memberid=jsonObject.optInt("memberid",-1);
     return ;
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
