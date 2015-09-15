
package cn.ecailan.esy.protocol;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ry8.external.activeandroid.Model;
import org.ry8.external.activeandroid.annotation.Column;
import org.ry8.external.activeandroid.annotation.Table;

import cn.ecailan.esy.model.M_Member;


@Table(name = "usersigninResponse")
public class MemberResponse extends Model
{

     @Column(name = "status")
     public STATUS   status;

     @Column(name = "data")
     public M_Member data;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;
          STATUS  status = new STATUS();
          status.fromJson(1);
          this.status = status;
         M_Member  data = new M_Member();
          data.fromJson(jsonObject.optJSONObject("data").optJSONObject("model"));
          this.data = data;
          return ;
     }

//     public JSONObject  toJson() throws JSONException
//     {
//          JSONObject localItemObject = new JSONObject();
//          if(null != status)
//          {
//            localItemObject.put("status", status.toJson());
//          }
//          if(null != data)
//          {
//            localItemObject.put("data", data.toJson());
//          }
//          return localItemObject;
//     }

}
