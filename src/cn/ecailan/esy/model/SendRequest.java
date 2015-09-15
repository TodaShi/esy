package cn.ecailan.esy.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ry8.CeaFrame.model.BaseModel;
import org.ry8.CeaFrame.model.BeeCallback;
import org.ry8.CeaFrame.view.MyProgressDialog;
import org.ry8.external.androidquery.callback.AjaxStatus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.ecailan.esy.R;
import cn.ecailan.esy.fragment.PurchaseFragment;

/**
 * Created by 陈艺强 on 2015-5-18.
 */
public class SendRequest extends BaseModel {
    public ArrayList<HashMap<String, Object>> arrayClassblog = new ArrayList<HashMap<String, Object>>();//动态列表
    public ArrayList<HashMap<String, Object>> arrayClassblogImg = new ArrayList<HashMap<String, Object>>();//动态图片列表
    public ArrayList<HashMap<String, Object>> arrayComment = new ArrayList<HashMap<String, Object>>();//回复列表
    public ArrayList<HashMap<String, Object>> arrayNews = new ArrayList<HashMap<String, Object>>();//公告列表
    public ArrayList<HashMap<String, Object>> arrayClass = new ArrayList<HashMap<String, Object>>();//所有班级列表
    public ArrayList<HashMap<String, Object>> arrayFoodClass = new ArrayList<HashMap<String, Object>>();//所有班级列表
    public ArrayList<HashMap<String, Object>> arrayStudent = new ArrayList<HashMap<String, Object>>();//全班小朋友
    public HashMap<String, Object> hashPhoto = new HashMap<String, Object>();
    public M_ClassBlog_Head m_classBlog_head = new M_ClassBlog_Head();
    public JSONArray dataBlog;
    public M_ClassBlogImg m_classBlogImg = new M_ClassBlogImg();
    public JSONArray dataBlogImg;
    public M_ClassBlogReply m_classBlogReply = new M_ClassBlogReply();
    public JSONArray dataBlogReply;
    public M_Student_Head m_student_head = new M_Student_Head();
    public JSONArray dataStudent;

    public M_ClassBlogImg getM_classBlogImg() {
        return m_classBlogImg;
    }

    public Context context;
    public SharedPreferences shared;


    //构造器
    public SendRequest(Context context) {
        super(context);
        this.context = context;
    }

    /**
     * 输入流转 byte[]
     */
    public static byte[] read(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        int length = inStream.available();
        byte[] buffer = new byte[length];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 发表动态
     */
    public void upLoad(List list, String body) {
        String imgName = null;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendRequest.this.callback(url, jo, status);
                try {
                    if (status.getError() == null && null != jo) {//jo={"status":"0","data":{"savename":"6b11da52b1f66e4cba8aa5ea5915e9649804"}}
//                        LoginModel.this.OnMessageResponse(url, jo, status);
//                        PurchaseFragment.sendRequest.getClassblogAll(1, 100);
                    }
                    SendRequest.this.OnMessageResponse(url, jo, status);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        shared = context.getSharedPreferences("userInfo", 0);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("func", "upload_classblogimg");
        params.put("devicecode", shared.getString("DeviceCode", ""));
        params.put("memberid", shared.getInt("memberid", -1));
        params.put("usertype", shared.getInt("usertype", -1));
        Map<String, Object> params3 = new HashMap<String, Object>();
        UUID uuid = UUID.randomUUID();
        params3.put("classblog_idg", uuid);//全球统一标识符
        params3.put("body", body);//内容
        params3.put("title", "");
        params3.put("classid", shared.getInt("ClassID", -1));
        JSONObject jsonObj3 = new JSONObject(params3);
        params.put("data", jsonObj3);
//        String uploadFile = "/sdcard/mypic1.JPEG";

        Map<String, Object> paramsTo = new HashMap<String, Object>();
        JSONObject jsonObj = new JSONObject(params);//其他信息

        paramsTo.put("msg", jsonObj);
        uuid = null;
        try {
            for (int i = 0; i < list.size(); i++) {
                FileInputStream fStream = null;

                fStream = new FileInputStream(list.get(i).toString());
                paramsTo.put(list.get(i).toString(), fStream);
            }
        } catch (Exception ee) {
            Log.d("classblog upload img error:", ee.getMessage());
        }
        cb.url("").type(JSONObject.class).params(paramsTo).requestCode("upLoad");
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    /**
     * 获取JSON数据——班级动态
     */
    public void getClassblogAll(int pageno, int pagecount) {
        BeeCallback<JSONObject> bc = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendRequest.this.callback(url, jo, status);
                if (null != jo) {
                    arrayClassblog.clear();
                    arrayClassblogImg.clear();
                    arrayComment.clear();
                    try {
                        if (status.getError() == null && null != jo) {

                            m_classBlog_head.fromJson(jo.getJSONObject("data").getJSONObject("classblog").getJSONObject("header"));
                            dataBlog = jo.getJSONObject("data").getJSONObject("classblog").getJSONArray("rows");
                            for (int i = 0; i < dataBlog.length(); i++) {
                                JSONArray json = dataBlog.getJSONArray(i);
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("classblog_idg", json.get(m_classBlog_head.classblog_idg));
                                hashMap.put("DeviceCode", json.get(m_classBlog_head.DeviceCode));
                                hashMap.put("classid", json.get(m_classBlog_head.classid));
                                hashMap.put("title", json.get(m_classBlog_head.title));
                                hashMap.put("body", json.get(m_classBlog_head.body));
                                hashMap.put("views_count", json.get(m_classBlog_head.views_count));
                                hashMap.put("likes_count", json.get(m_classBlog_head.likes_count));
                                hashMap.put("comments_count", json.get(m_classBlog_head.comments_count));
                                hashMap.put("memberid", json.get(m_classBlog_head.memberid));
                                hashMap.put("usertype", json.get(m_classBlog_head.usertype));
                                hashMap.put("addtime", json.get(m_classBlog_head.addtime));
                                hashMap.put("islikes", json.get(m_classBlog_head.islikes));
                                hashMap.put("picture", json.get(m_classBlog_head.picture));
                                hashMap.put("name", json.get(m_classBlog_head.name));
                                hashMap.put("rownum", json.get(m_classBlog_head.rownum));
                                arrayClassblog.add(hashMap);
                            }
                            m_classBlogImg.fromJson(jo.getJSONObject("data").getJSONObject("classblogimg").getJSONObject("header"));
                            dataBlogImg = jo.getJSONObject("data").getJSONObject("classblogimg").getJSONArray("rows");

                            for (int j = 0; j < dataBlogImg.length(); j++) {
                                JSONArray json = dataBlogImg.getJSONArray(j);
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("classblog_idg", json.get(m_classBlogImg.classblog_idg));
                                hashMap.put("filehash", json.get(m_classBlogImg.filehash));
                                hashMap.put("rownum", json.get(m_classBlogImg.rownum));
                                arrayClassblogImg.add(hashMap);
                            }
                            m_classBlogReply.fromJson(jo.getJSONObject("data").getJSONObject("classblog_reply").getJSONObject("header"));
                            dataBlogReply = jo.getJSONObject("data").getJSONObject("classblog_reply").getJSONArray("rows");

                            for (int k = 0; k < dataBlogReply.length(); k++) {
                                JSONArray json = dataBlogReply.getJSONArray(k);
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("comment_id", json.get(m_classBlogReply.comment_id));
                                hashMap.put("DeviceCode", json.get(m_classBlogReply.DeviceCode));
                                hashMap.put("classblog_idg", json.get(m_classBlogReply.classblog_idg));
                                hashMap.put("likes_count", json.get(m_classBlogReply.likes_count));
                                hashMap.put("body", json.get(m_classBlogReply.body));
                                hashMap.put("memberid", json.get(m_classBlogReply.memberid));
                                hashMap.put("usertype", json.get(m_classBlogReply.usertype));
                                hashMap.put("addtime", json.get(m_classBlogReply.addtime));
                                hashMap.put("picture", json.get(m_classBlogReply.picture));
                                hashMap.put("name", json.get(m_classBlogReply.name));
                                hashMap.put("rownum", json.get(m_classBlogReply.rownum));
                                arrayComment.add(hashMap);
                            }
                            SendRequest.this.OnMessageResponse(url, jo, status);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        shared = context.getSharedPreferences("userInfo", 0);

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("func", "get_classblog");
        params.put("devicecode", shared.getString("DeviceCode", ""));
        params.put("memberid", shared.getInt("memberid", -1));
        params.put("usertype", shared.getInt("usertype", -1));
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("classid", shared.getInt("ClassID", -1));
        data.put("pageno", pageno);
        data.put("pagecount", pagecount);
        JSONObject dataObj = new JSONObject(data);
        params.put("data", dataObj);

        JSONObject jsonObj = new JSONObject(params);
        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj.toString());

        bc.url("").type(JSONObject.class).params(paramsTo).requestCode("getClassblogAll");
        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍等");
        aq.progress(mPro.mDialog).ajax(bc);
    }


    /**
     * 获取JSON数据——班级动态
     */
    public void getClassblog() {
        BeeCallback<JSONObject> bc = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendRequest.this.callback(url, jo, status);
                if (null != jo) {
                    arrayClassblog.clear();
                    arrayClassblogImg.clear();

                    try {
                        if (status.getError() == null && null != jo) {

                            JSONArray rows = jo.getJSONObject("data").getJSONObject("classblog").getJSONArray("rows");
                            JSONArray rowsImg = jo.getJSONObject("data").getJSONObject("classblogimg").getJSONArray("rows");
                            for (int i = 0; i < rows.length(); i++) {
                                JSONArray json = rows.getJSONArray(i);
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("classblog_idg", json.get(0));
                                hashMap.put("DeviceCode", json.get(1));
                                hashMap.put("classid", json.get(2));
                                hashMap.put("title", json.get(3));
                                hashMap.put("body", json.get(4));
                                hashMap.put("views_count", json.get(5));
                                hashMap.put("likes_count", json.get(6));
                                hashMap.put("comments_count", json.get(7));
                                hashMap.put("memberid", json.get(8));
                                hashMap.put("usertype", json.get(9));
                                hashMap.put("addtime", json.get(10));
                                hashMap.put("islikes", json.get(11));
                                hashMap.put("picture", json.get(12));
                                hashMap.put("name", json.get(13));
                                hashMap.put("rownum", json.get(14));
                                arrayClassblog.add(hashMap);
                            }
                            for (int j = 0; j < rowsImg.length(); j++) {
                                JSONArray json = rowsImg.getJSONArray(j);
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("classblog_idg", json.get(0));
                                hashMap.put("filehash", json.get(1));
                                hashMap.put("rownum", json.get(2));
                                arrayClassblogImg.add(hashMap);
                            }

                            SendRequest.this.OnMessageResponse(url, jo, status);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        shared = context.getSharedPreferences("userInfo", 0);

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("func", "get_classblog");
        params.put("devicecode", shared.getString("DeviceCode", ""));
        params.put("memberid", shared.getInt("memberid", -1));
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("classid", shared.getInt("ClassID", -1));
        JSONObject dataObj = new JSONObject(data);
        params.put("data", dataObj);

        JSONObject jsonObj = new JSONObject(params);
        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj.toString());

        bc.url("").type(JSONObject.class).params(paramsTo);
        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍等");
        aq.progress(mPro.mDialog).ajax(bc);
    }

    /**
     * 发表回复
     */
    public void addComment(String body, String cb_id) {
        BeeCallback<JSONObject> bc = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendRequest.this.callback(url, jo, status);
                try {
                    if (status.getError() == null && null != jo) {

                        SendRequest.this.OnMessageResponse(url, jo, status);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("func", "add_comment");
        params.put("devicecode", shared.getString("DeviceCode", ""));
        params.put("memberid", shared.getInt("memberid", -1));
        params.put("usertype", shared.getInt("usertype", -1));
        HashMap<String, Object> data = new HashMap<String, Object>();
        UUID uuid = UUID.randomUUID();
        data.put("comment_id", uuid);
        data.put("classblog_idg", cb_id);
        data.put("body", body);
        JSONObject dataObj = new JSONObject(data);
        params.put("data", dataObj);

        JSONObject jsonObj = new JSONObject(params);
        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj.toString());

        bc.url("").type(JSONObject.class).params(paramsTo).requestCode("addComment");
        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍等");
        aq.progress(mPro.mDialog).ajax(bc);
    }


    public void addComment(String body, String cb_id, Object tag) {
        BeeCallback<JSONObject> bc = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendRequest.this.callback(url, jo, status);
                try {
                    if (status.getError() == null && null != jo) {

                        SendRequest.this.OnMessageResponse(url, jo, status);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("func", "add_comment");
        params.put("devicecode", shared.getString("DeviceCode", ""));
        params.put("memberid", shared.getInt("memberid", -1));
        params.put("usertype", shared.getInt("usertype", -1));
        HashMap<String, Object> data = new HashMap<String, Object>();
        UUID uuid = UUID.randomUUID();
        data.put("comment_id", uuid);
        data.put("classblog_idg", cb_id);
        data.put("body", body);
        JSONObject dataObj = new JSONObject(data);
        params.put("data", dataObj);

        JSONObject jsonObj = new JSONObject(params);
        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj.toString());

        bc.url("").type(JSONObject.class).params(paramsTo).requestCode("addComment").tag(tag);
        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍等");
        aq.progress(mPro.mDialog).ajax(bc);
    }

    /**
     * 获取同学花名册
     */
    public void getStudent() {
        BeeCallback<JSONObject> bc = new BeeCallback<JSONObject>() {
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendRequest.this.callback(url, jo, status);
                if (null != jo) {
                    arrayStudent.clear();
                    try {
                        if (status.getError() == null && null != jo) {
                            m_student_head.fromJson(jo.getJSONObject("data").getJSONObject("datatable").getJSONObject("header"));
                            dataStudent = jo.getJSONObject("data").getJSONObject("datatable").getJSONArray("rows");
                            for (int i = 0; i < dataStudent.length(); i++) {
                                JSONArray json = dataStudent.getJSONArray(i);
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("localdb", json.get(m_student_head.localdb));
                                hashMap.put("DeviceCode", json.get(m_student_head.DeviceCode));
                                hashMap.put("memberid", json.get(m_student_head.memberid));
                                hashMap.put("usertype", json.get(m_student_head.usertype));
                                hashMap.put("SchoolID", json.get(m_student_head.SchoolID));
                                hashMap.put("ClassID", json.get(m_student_head.ClassID));
                                hashMap.put("name", json.get(m_student_head.name));
                                hashMap.put("LoginPW", json.get(m_student_head.LoginPW));
                                hashMap.put("CardAddress", json.get(m_student_head.CardAddress));
                                hashMap.put("GradeID", json.get(m_student_head.GradeID));
                                hashMap.put("ClassNo", json.get(m_student_head.ClassNo));
                                hashMap.put("ClassName", json.get(m_student_head.ClassName));
                                hashMap.put("ChargeClassTeacherID", json.get(m_student_head.ChargeClassTeacherID));
                                hashMap.put("EntranceYear", json.get(m_student_head.EntranceYear));
                                hashMap.put("GradeName", json.get(m_student_head.GradeName));
                                hashMap.put("SchoolNo", json.get(m_student_head.SchoolNo));
                                hashMap.put("SchoolName", json.get(m_student_head.SchoolName));
                                hashMap.put("HomePhone", json.get(m_student_head.HomePhone));
                                hashMap.put("HomeAddress", json.get(m_student_head.HomeAddress));
                                hashMap.put("RegTime", json.get(m_student_head.RegTime));
                                hashMap.put("Disuse", json.get(m_student_head.Disuse));
                                hashMap.put("CardAddress2", json.get(m_student_head.CardAddress2));
                                hashMap.put("CardAddress3", json.get(m_student_head.CardAddress3));
                                hashMap.put("CardAddress4", json.get(m_student_head.CardAddress4));
                                hashMap.put("picture", json.get(m_student_head.picture));
                                hashMap.put("pic0", json.get(m_student_head.pic0));
                                hashMap.put("pic1", json.get(m_student_head.pic1));
                                hashMap.put("pic2", json.get(m_student_head.pic2));
                                hashMap.put("pic3", json.get(m_student_head.pic3));
                                hashMap.put("pic4", json.get(m_student_head.pic4));
                                arrayStudent.add(hashMap);
                            }
                            SendRequest.this.OnMessageResponse(url, jo, status);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("func", "get_student");
        params.put("devicecode", shared.getString("DeviceCode", ""));
        params.put("memberid", shared.getInt("memberid", -1));
        params.put("usertype", shared.getInt("usertype", -1));
        JSONObject jsonObj = new JSONObject(params);
        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj.toString());
        bc.url("").type(JSONObject.class).params(paramsTo);
        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍等");
        aq.progress(mPro.mDialog).ajax(bc);
    }


    /**
     * 查询回复列表
     */
    public void getComment(String cb_id) {
        BeeCallback<JSONObject> bc = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendRequest.this.callback(url, jo, status);
                if (null != jo) {
                    arrayComment.clear();
                    try {
                        if (status.getError() == null && null != jo) {
                            JSONArray rows = jo.getJSONObject("data").getJSONObject("datatable").getJSONArray("rows");
                            for (int i = 0; i < rows.length(); i++) {
                                JSONArray json = rows.getJSONArray(i);
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("comment_id", json.get(0));
                                hashMap.put("DeviceCode", json.get(1));
                                hashMap.put("classblog_idg", json.get(2));
                                hashMap.put("likes_count", json.get(3));
                                hashMap.put("body", json.get(4));
                                hashMap.put("memberid", json.get(5));
                                hashMap.put("usertype", json.get(6));
                                hashMap.put("addtime", json.get(7));
                                hashMap.put("picture", json.get(8));
                                hashMap.put("name", json.get(9));
                                arrayComment.add(hashMap);
                            }
                            SendRequest.this.OnMessageResponse(url, jo, status);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("func", "get_comment");
        params.put("devicecode", shared.getString("DeviceCode", ""));
        params.put("memberid", shared.getInt("memberid", -1));
        params.put("usertype", shared.getInt("usertype", -1));
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("classblog_idg", cb_id);
        JSONObject dataObj = new JSONObject(data);
        params.put("data", dataObj);

        JSONObject jsonObj = new JSONObject(params);
        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj.toString());

        bc.url("").
                type(JSONObject.class).params(paramsTo).requestCode("getComment");
        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍等");
        aq.progress(mPro.mDialog).ajax(bc);
    }

    /**
     * 点赞
     */
    public void setLikes(String cb_id, Object tag) {
        BeeCallback<JSONObject> bc = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendRequest.this.callback(url, jo, status);

                try {
                    if (status.getError() == null && null != jo) {

                        SendRequest.this.OnMessageResponse(url, jo, status);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("func", "set_likes");
        params.put("devicecode", shared.getString("DeviceCode", ""));
        params.put("memberid", shared.getInt("memberid", -1));
        params.put("usertype", shared.getInt("usertype", -1));
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("classblog_idg", cb_id);
        JSONObject dataObj = new JSONObject(data);
        params.put("data", dataObj);

        JSONObject jsonObj = new JSONObject(params);
        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj.toString());

        bc.url("").type(JSONObject.class).params(paramsTo).tag(tag).requestCode("setLikes");
        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍等");
        aq.progress(mPro.mDialog).ajax(bc);
    }


    //上传家长照片

    public void upload_photo(String img, int picindex) {
        BeeCallback<JSONObject> bc = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendRequest.this.callback(url, jo, status);

                try {
                    if (status.getError() != null || null == jo) {
                        Toast.makeText(mContext.getApplicationContext(), "上传失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        shared = context.getSharedPreferences("userInfo", 0);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("func", "upload_photo");
        params.put("devicecode", shared.getString("DeviceCode", ""));
        params.put("memberid", shared.getInt("memberid", -1));
        params.put("usertype", shared.getInt("usertype", -1));
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("picindex", picindex);
        JSONObject dataObj = new JSONObject(data);
        params.put("data", dataObj);
        FileInputStream fStream = null;
        try {
            fStream = new FileInputStream(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JSONObject jsonObj = new JSONObject(params);
        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj.toString());
        paramsTo.put(img, fStream);
        File f = new File(img);
        bc.url("").type(JSONObject.class).params(paramsTo);
        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍等");
        aq.progress(mPro.mDialog).ajax(bc);
    }


    //获取家长照片
    public void get_photo() {
        BeeCallback<JSONObject> bc = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendRequest.this.callback(url, jo, status);
                try {
                    JSONObject data = jo.optJSONObject("data");
                    JSONObject model = data.optJSONObject("model");

                    hashPhoto.put("memberidg", model.optString("memberidg", ""));
                    hashPhoto.put("DeviceCode", model.optString("DeviceCode", ""));
                    hashPhoto.put("memberid", model.optInt("memberid", -1));
                    hashPhoto.put("usertype", model.optInt("usertype", -1));
                    hashPhoto.put("picture", model.optString("picture", ""));
                    hashPhoto.put("addtime", model.optString("addtime", ""));
                    hashPhoto.put("pic0", model.optString("pic0", ""));
                    hashPhoto.put("pic1", model.optString("pic1", ""));
                    hashPhoto.put("pic2", model.optString("pic2", ""));
                    hashPhoto.put("pic3", model.optString("pic3", ""));
                    hashPhoto.put("pic4", model.optString("pic4", ""));

//                    if (status.getError() == null && null != jo) {
//                        shared = context.getSharedPreferences("userInfo", 0);
//                        SharedPreferences.Editor editor = shared.edit();
//                        editor.putString("pic0", jo.getJSONObject("model").get("pic0").toString());
//                        editor.putString("pic1", jo.getJSONObject("model").get("pic1").toString());
//                        editor.putString("pic2", jo.getJSONObject("model").get("pic2").toString());
//                        editor.putString("pic3", jo.getJSONObject("model").get("pic3").toString());
//                        editor.putString("pic4", jo.getJSONObject("model").get("pic4").toString());
//                        editor.commit();
                    SendRequest.this.OnMessageResponse(url, jo, status);
//                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("func", "get_photo");
        params.put("devicecode", shared.getString("DeviceCode", ""));
        params.put("memberid", shared.getInt("memberid", -1));
        params.put("usertype", shared.getInt("usertype", -1));
        JSONObject jsonObj = new JSONObject(params);
        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj.toString());
        bc.url("").type(JSONObject.class).params(paramsTo);
        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍等");
        aq.progress(mPro.mDialog).ajax(bc);
    }


    /**
     * 上传头像
     */
    public void upload_headerimg(String img) {
        BeeCallback<JSONObject> bc = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendRequest.this.callback(url, jo, status);

                try {
                    if (status.getError() == null && null != jo) {
                        shared = context.getSharedPreferences("userInfo", 0);
                        SharedPreferences.Editor editor = shared.edit();
                        editor.putString("picture", jo.getJSONObject("data").get("picture").toString());
                        editor.commit();
                        SendRequest.this.OnMessageResponse(url, jo, status);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("func", "upload_headerimg");
        params.put("devicecode", shared.getString("DeviceCode", ""));
        params.put("memberid", shared.getInt("memberid", -1));
        params.put("usertype", shared.getInt("usertype", -1));
        FileInputStream fStream = null;
        try {
            fStream = new FileInputStream(img);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JSONObject jsonObj = new JSONObject(params);
        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj.toString());
        paramsTo.put(img, fStream);
        File f = new File(img);
        bc.url("").type(JSONObject.class).params(paramsTo).requestCode("upload_headerimg");
        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍等");
        aq.progress(mPro.mDialog).ajax(bc);
    }


    /**
     * 添加校园公告
     */
    public void addNews(String body, String title) {
        BeeCallback<JSONObject> bc = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendRequest.this.callback(url, jo, status);
                try {
                    if (status.getError() == null && null != jo) {

                        SendRequest.this.OnMessageResponse(url, jo, status);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("func", "add_news");
        params.put("devicecode", shared.getString("DeviceCode", ""));
        params.put("memberid", shared.getInt("memberid", -1));
        params.put("usertype", shared.getInt("usertype", -1));
        HashMap<String, Object> data = new HashMap<String, Object>();
        UUID uuid = UUID.randomUUID();
        data.put("news_idg", uuid);
        data.put("title", title);
        data.put("body", body);
        JSONObject dataObj = new JSONObject(data);
        params.put("data", dataObj);

        JSONObject jsonObj = new JSONObject(params);
        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj.toString());

        bc.url("").type(JSONObject.class).params(paramsTo);
        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍等");
        aq.progress(mPro.mDialog).ajax(bc);
    }


    /**
     * 查询校园公告
     */
    public void getNews() {
        BeeCallback<JSONObject> bc = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendRequest.this.callback(url, jo, status);
                if (null != jo) {
                    arrayNews.clear();
                    try {
                        if (status.getError() == null && null != jo) {
                            JSONArray rows = jo.getJSONObject("data").getJSONObject("datatable").getJSONArray("rows");
                            for (int i = 0; i < rows.length(); i++) {
                                JSONArray json = rows.getJSONArray(i);
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("news_idg", json.get(0));
                                hashMap.put("DeviceCode", json.get(1));
                                hashMap.put("title", json.get(2));
                                hashMap.put("body", json.get(3));
                                hashMap.put("memberid", json.get(4));
                                hashMap.put("usertype", json.get(5));
                                hashMap.put("addtime", json.get(6));
                                hashMap.put("name", json.get(7));

                                arrayNews.add(hashMap);
                            }
                            SendRequest.this.OnMessageResponse(url, jo, status);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("func", "get_news");
        params.put("devicecode", shared.getString("DeviceCode", ""));
        params.put("memberid", shared.getInt("memberid", -1));
        params.put("usertype", shared.getInt("usertype", -1));

        JSONObject jsonObj = new JSONObject(params);
        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj.toString());

        bc.url("").
                type(JSONObject.class).params(paramsTo);
        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍等");
        aq.progress(mPro.mDialog).ajax(bc);
    }


    /**
     * 查询所有班级
     */
    public void getClassName() {
        BeeCallback<JSONObject> bc = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendRequest.this.callback(url, jo, status);
                if (null != jo) {
                    arrayClass.clear();
                    try {
                        if (status.getError() == null && null != jo) {
                            JSONArray rows = jo.getJSONObject("data").getJSONObject("datatable").getJSONArray("rows");
                            for (int i = 0; i < rows.length(); i++) {
                                JSONArray json = rows.getJSONArray(i);
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("AutoID", json.get(0));
                                hashMap.put("ClassName", json.get(1));
                                hashMap.put("GradeID", json.get(2));
                                hashMap.put("ClassNo", json.get(3));
                                arrayClass.add(hashMap);
                            }
                            SendRequest.this.OnMessageResponse(url, jo, status);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("func", "get_classlist");
        params.put("devicecode", shared.getString("DeviceCode", ""));
        params.put("memberid", shared.getInt("memberid", -1));
        params.put("usertype", shared.getInt("usertype", -1));

        JSONObject jsonObj = new JSONObject(params);
        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj.toString());

        bc.url("").
                type(JSONObject.class).params(paramsTo);
        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍等");
        aq.progress(mPro.mDialog).ajax(bc);
    }

    /**
     * 切换班级
     */
    public void setTeatherclass(String classid) {
        BeeCallback<JSONObject> bc = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendRequest.this.callback(url, jo, status);
                if (null != jo) {

                    if (status.getError() == null && null != jo) {

//                            SendRequest.this.OnMessageResponse(url, jo, status);
                    }
                }
            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("func", "set_teatherclass");
        params.put("devicecode", shared.getString("DeviceCode", ""));
        params.put("memberid", shared.getInt("memberid", -1));
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("classid", classid);

        JSONObject dataObj = new JSONObject(data);
        params.put("data", dataObj);

        JSONObject jsonObj = new JSONObject(params);
        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj.toString());

        bc.url("").
                type(JSONObject.class).params(paramsTo);
        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍等");
        aq.progress(mPro.mDialog).ajax(bc);
    }


    /**
     * 上传菜谱
     * *
     */
    public void upLoadFoodClass(List list, String name) {
        String imgName = null;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                try {
                    if (status.getError() == null && null != jo) {//jo={"status":"0","data":{"savename":"6b11da52b1f66e4cba8aa5ea5915e9649804"}}
//                        LoginModel.this.OnMessageResponse(url, jo, status);
                    } else {

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        shared = context.getSharedPreferences("userInfo", 0);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("func", "upload_foodclass");
        params.put("devicecode", "testDB");
        params.put("memberid", shared.getInt("memberid", -1));
        Map<String, Object> params3 = new HashMap<String, Object>();
        UUID uuid = UUID.randomUUID();
        params3.put("foodclass_idg", uuid);//全球统一标识符
        params3.put("foodclass_name", name);
        JSONObject jsonObj3 = new JSONObject(params3);
        params.put("data", jsonObj3);
//        String uploadFile = "/sdcard/mypic1.JPEG";

        Map<String, Object> paramsTo = new HashMap<String, Object>();
        JSONObject jsonObj = new JSONObject(params);//其他信息

        paramsTo.put("msg", jsonObj);
        try {
            for (int i = 0; i < list.size(); i++) {
                FileInputStream fStream = null;

                fStream = new FileInputStream(list.get(i).toString());
                paramsTo.put(list.get(i).toString(), fStream);
            }
        } catch (Exception ee) {
            Log.d("classblog upload img error:", ee.getMessage());
        }

        cb.url("").type(JSONObject.class).params(paramsTo);//
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }


    /**
     * 查询伙食类
     * *
     */


    public void getFoodClass() {
        BeeCallback<JSONObject> bc = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                SendRequest.this.callback(url, jo, status);
                if (null != jo) {
                    arrayFoodClass.clear();
                    try {
                        if (status.getError() == null && null != jo) {
                            JSONArray rows = jo.getJSONObject("data").getJSONObject("datatable").getJSONArray("rows");
                            for (int i = 0; i < rows.length(); i++) {
                                JSONArray json = rows.getJSONArray(i);
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("foodclass_idg", json.get(0));
                                hashMap.put("foodclass_name", json.get(1));
                                hashMap.put("foodclass_img", json.get(2));
                                hashMap.put("addtime", json.get(3));
                                arrayFoodClass.add(hashMap);
                            }
                            SendRequest.this.OnMessageResponse(url, jo, status);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("func", "get_foodclass");
        params.put("devicecode", shared.getString("DeviceCode", ""));
        params.put("memberid", shared.getInt("memberid", -1));

        JSONObject jsonObj = new JSONObject(params);
        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj.toString());

        bc.url("").
                type(JSONObject.class).params(paramsTo);
        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍等");
        aq.progress(mPro.mDialog).ajax(bc);
    }


    //    删除食物类
    public void del_foodclass(String foodclass_idg) {
        BeeCallback<JSONObject> bc = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
//                SendRequest.this.callback(url, jo, status);
                if (status.getError() == null && null != jo) {
                    SendRequest.this.OnMessageResponse(url, jo, status);
                }
            }
        };
        shared = context.getSharedPreferences("userInfo", 0);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("func", "del_foodclass");
        params.put("devicecode", shared.getString("DeviceCode", ""));
        params.put("memberid", shared.getInt("memberid", -1));

        Map<String, Object> params2 = new HashMap<String, Object>();
        params2.put("foodclass_idg", foodclass_idg);
        JSONObject jsonObj2 = new JSONObject(params2);
        params.put("data", jsonObj2);

        JSONObject jsonObj = new JSONObject(params);
        HashMap<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj.toString());

        bc.url("").
                type(JSONObject.class).params(paramsTo).requestCode("del_foodclass\n" + foodclass_idg);
        MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍等");
        aq.progress(mPro.mDialog).ajax(bc);
    }
}
