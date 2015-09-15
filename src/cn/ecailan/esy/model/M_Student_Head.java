package cn.ecailan.esy.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by John on 2015/8/17.
 */
public class M_Student_Head {

    public int localdb;
    public int DeviceCode;
    public int memberid;
    public int usertype;
    public int SchoolID;
    public int ClassID;
    public int name;
    public int LoginPW;
    public int CardAddress;
    public int GradeID;
    public int ClassNo;
    public int ClassName;
    public int ChargeClassTeacherID;
    public int EntranceYear;
    public int GradeName;
    public int SchoolNo;
    public int SchoolName;
    public int HomePhone;
    public int HomeAddress;
    public int RegTime;
    public int Disuse;
    public int CardAddress2;
    public int CardAddress3;
    public int CardAddress4;
    public int picture;
    public int pic0;
    public int pic1;
    public int pic2;
    public int pic3;
    public int pic4;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.localdb = jsonObject.optInt("localdb", -1);
        this.DeviceCode = jsonObject.optInt("DeviceCode", -1);
        this.memberid = jsonObject.optInt("memberid", -1);
        this.usertype = jsonObject.optInt("usertype", -1);
        this.SchoolID = jsonObject.optInt("SchoolID", -1);
        this.ClassID = jsonObject.optInt("ClassID", -1);
        this.name = jsonObject.optInt("name", -1);
        this.LoginPW = jsonObject.optInt("LoginPW", -1);
        this.CardAddress = jsonObject.optInt("CardAddress", -1);
        this.GradeID = jsonObject.optInt("GradeID", -1);
        this.ClassNo = jsonObject.optInt("ClassNo", -1);
        this.ClassName = jsonObject.optInt("ClassName", -1);
        this.ChargeClassTeacherID = jsonObject.optInt("ChargeClassTeacherID", -1);
        this.EntranceYear = jsonObject.optInt("EntranceYear", -1);
        this.GradeName = jsonObject.optInt("GradeName", -1);
        this.SchoolNo = jsonObject.optInt("SchoolNo", -1);
        this.SchoolName = jsonObject.optInt("SchoolName", -1);
        this.HomePhone = jsonObject.optInt("HomePhone", -1);
        this.HomeAddress = jsonObject.optInt("HomeAddress", -1);
        this.RegTime = jsonObject.optInt("RegTime", -1);
        this.Disuse = jsonObject.optInt("Disuse", -1);
        this.CardAddress2 = jsonObject.optInt("CardAddress2", -1);
        this.CardAddress3 = jsonObject.optInt("CardAddress3", -1);
        this.CardAddress4 = jsonObject.optInt("CardAddress4", -1);
        this.picture = jsonObject.optInt("picture", -1);
        this.pic0 = jsonObject.optInt("pic0", -1);
        this.pic1 = jsonObject.optInt("pic1", -1);
        this.pic2 = jsonObject.optInt("pic2", -1);
        this.pic3 = jsonObject.optInt("pic3", -1);
        this.pic4 = jsonObject.optInt("pic4", -1);
        return;
    }
}
