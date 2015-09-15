
package cn.ecailan.esy.protocol;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ry8.external.activeandroid.Model;
import org.ry8.external.activeandroid.annotation.Column;
import org.ry8.external.activeandroid.annotation.Table;


@Table(name = "FIELD")
public class FIELD extends Model
{

     @Column(name = "FIELD_id")
     public int id;

     @Column(name = "value")
     public String   value;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          this.id = jsonObject.optInt("id");

          this.value = jsonObject.optString("value");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("id", id);
          localItemObject.put("value", value);
          return localItemObject;
     }

}
