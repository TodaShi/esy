
package cn.ecailan.esy.protocol;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ry8.external.activeandroid.Model;
import org.ry8.external.activeandroid.annotation.Column;
import org.ry8.external.activeandroid.annotation.Table;


@Table(name = "usersigninResponse")
public class usersigninResponse extends Model
{

     @Column(name = "status")
     public STATUS   status;

     @Column(name = "data")
     public USER   data;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;
          STATUS  status = new STATUS();
          status.fromJson(1);
          this.status = status;
          USER  data = new USER();
          data.fromJson(jsonObject);
          this.data = data;
          return ;
     }

     public JSONObject  toJson() throws JSONException
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          if(null != status)
          {
            localItemObject.put("status", status.toJson());
          }
          if(null != data)
          {
            localItemObject.put("data", data.toJson());
          }
          return localItemObject;
     }

}
