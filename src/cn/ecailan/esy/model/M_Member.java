
package cn.ecailan.esy.model;

import org.json.JSONException;
import org.json.JSONObject;
import org.ry8.external.activeandroid.Model;
import org.ry8.external.activeandroid.annotation.Column;
import org.ry8.external.activeandroid.annotation.Table;

@Table(name = "Member")
public class M_Member extends Model
{

    @Column(name = "useridg")
    public String useridg;
    @Column(name = "usertypeid")
    public int usertypeid;
    @Column(name = "userstateid")
    public int userstateid;
    @Column(name = "username")
    public String username;
    @Column(name = "password")
    public String password;
    @Column(name = "paypassword")
    public String paypassword;
    @Column(name = "realname")
    public String realname;
    @Column(name = "phone")
    public String phone;
    @Column(name = "address")
    public String address;
    @Column(name = "sex")
    public String sex;
    @Column(name = "iden")
    public String iden;
    @Column(name = "money")
    public int money;
    @Column(name = "expense")
    public int expense;
    @Column(name = "points")
    public int points;
    @Column(name = "email")
    public String email;
    @Column(name = "qq")
    public String qq;
    @Column(name = "addtime")
    public String addtime;
    @Column(name = "optime")
    public String optime;
    @Column(name = "unsure_phone")
    public String unsure_phone;
    @Column(name = "lid")
    public String lid;
    @Column(name = "disabled")
    public boolean disabled;
    @Column(name = "opuseridg")
    public String opuseridg;

    @Column(name = "info")
    public String info;
    @Column(name = "errormsg")
    public String errormsg;
    @Column(name = "errorcode")
    public String errorcode;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }
         this.useridg = jsonObject.optString("useridg");
         this.usertypeid = jsonObject.optInt("usertypeid");
         this.userstateid = jsonObject.optInt("userstateid");
         this.username = jsonObject.optString("username");
         this.password = jsonObject.optString("password");
         this.paypassword = jsonObject.optString("paypassword");
         this.realname = jsonObject.optString("realname");
         this.phone = jsonObject.optString("phone");
         this.address = jsonObject.optString("address");
         this.sex = jsonObject.optString("sex");
         this.iden = jsonObject.optString("iden");
         this.money = jsonObject.optInt("money");
         this.expense = jsonObject.optInt("expense");
         this.points = jsonObject.optInt("points");
         this.email = jsonObject.optString("email");
         this.qq = jsonObject.optString("qq");
         this.optime = jsonObject.optString("optime");
         this.unsure_phone = jsonObject.optString("unsure_phone");
         this.lid = jsonObject.optString("lid");
         this.disabled = jsonObject.optBoolean("disabled");
         this.opuseridg = jsonObject.optString("opuseridg");
         this.info = jsonObject.optString("info");
         this.errormsg = jsonObject.optString("errormsg");
         this.errorcode = jsonObject.optString("errorcode");

          return ;
     }


}
