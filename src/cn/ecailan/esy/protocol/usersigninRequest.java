
package cn.ecailan.esy.protocol;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ry8.external.activeandroid.Model;
import org.ry8.external.activeandroid.annotation.Column;
import org.ry8.external.activeandroid.annotation.Table;

@Table(name = "usersigninRequest")
public class usersigninRequest extends Model
{

     @Column(name = "name")
     public String   name;

     @Column(name = "password")
     public String   password;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          this.name = jsonObject.optString("name");

          this.password = jsonObject.optString("password");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("name", name);
          localItemObject.put("password", password);
          return localItemObject;
     }

}
