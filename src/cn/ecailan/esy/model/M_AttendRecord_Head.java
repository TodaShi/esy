
package cn.ecailan.esy.model;
import org.json.JSONException;
import org.json.JSONObject;

//@Table(name = "AttendRecord_Head")
public class M_AttendRecord_Head// extends Model
{
//     @Column(name = "id",unique = true)
     public int AttendanceIDG;
     
//     @Column(name = "DeviceCode")
     public int DeviceCode;

//     @Column(name = "StudentID")
     public int StudentID;

//     @Column(name = "CardNo")
     public int CardNo;
     
//     @Column(name = "AttendanceTime")
 	public int AttendanceTime;

//     @Column(name = "addtime")
     public int addtime;

    public int outCardNo;


    public int outtime;

    public int optime;




 public void  fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }

     this.AttendanceIDG = jsonObject.optInt("AttendanceIDG",-1);
     this.DeviceCode = jsonObject.optInt("DeviceCode",-1);
     this.StudentID = jsonObject.optInt("StudentID",-1);
     this.CardNo = jsonObject.optInt("CardNo",-1);
     this.AttendanceTime = jsonObject.optInt("AttendanceTime",-1);
     this.addtime=jsonObject.optInt("addtime",-1);
     this.outCardNo = jsonObject.optInt("outCardNo",-1);
     this.outtime = jsonObject.optInt("outtime",-1);
     this.optime = jsonObject.optInt("optime",-1);

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
