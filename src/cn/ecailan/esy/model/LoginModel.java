package cn.ecailan.esy.model;

//
//                       __
//                      /\ \   _
//    ____    ____   ___\ \ \_/ \           _____    ___     ___
//   / _  \  / __ \ / __ \ \    <     __   /\__  \  / __ \  / __ \
//  /\ \_\ \/\  __//\  __/\ \ \\ \   /\_\  \/_/  / /\ \_\ \/\ \_\ \
//  \ \____ \ \____\ \____\\ \_\\_\  \/_/   /\____\\ \____/\ \____/
//   \/____\ \/____/\/____/ \/_//_/         \/____/ \/___/  \/___/
//     /\____/
//     \/___/
//
//  Powered by ry8
//

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;

import org.json.JSONException;
import org.json.JSONObject;
import org.ry8.CeaFrame.model.BaseModel;
import org.ry8.CeaFrame.model.BeeCallback;
import org.ry8.CeaFrame.view.MyProgressDialog;
import org.ry8.CeaFrame.view.ToastView;
import org.ry8.CommonFunc.Interaction;
import org.ry8.external.androidquery.callback.AjaxStatus;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import cn.ecailan.esy.ApiInterface;
import cn.ecailan.esy.R;
import cn.ecailan.esy.protocol.MemberResponse;
import cn.ecailan.esy.protocol.STATUS;
import cn.ecailan.esy.protocol.USER;
import cn.ecailan.esy.protocol.usersigninRequest;
import cn.ecailan.esy.protocol.usersigninResponse;


public class LoginModel extends BaseModel {

    private SharedPreferences shared;
    private SharedPreferences.Editor editor;
    public STATUS responseStatus;
    public Boolean bStatus=false;

    public LoginModel(Context context) {
        super(context);
        shared = context.getSharedPreferences("userInfo", 0);
        editor = shared.edit();
    }

      
   /*   public void async_html() { //同步html分析
    	  System.out.println("------async_html-----");
    	  String url = "http://ecailan.cn/mobile/index.php/Mobile/Mobile/Experience.php";
  		AQuery aq = new AQuery(mContext);
  		 MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍后...");
  		 
  		 Map<String, Object> params = new HashMap<String, Object>();
	      params.put("Func", "Class_List");
	      params.put("Model", "Experience");
	      try {
		      JSONObject localItemObject = new JSONObject();
		      //localItemObject.put("Name", "tigerst");
		      //localItemObject.put("password", "c4eaa3098cf67de8bbd924dd613f223b");
		      localItemObject.put("parent", 0);
		      params.put("data",localItemObject);
	      } catch (JSONException e) {
	            e.printStackTrace();
	        }
	      JSONObject jsonObj = new JSONObject(params);    
	      
	      Map<String, Object> paramsTo = new HashMap<String, Object>();
	      paramsTo.put("msg", jsonObj.toString());
       
  		 
  		aq.progress(mPro.mDialog).ajax(url, paramsTo,String.class, new AjaxCallback<String>() {

  					@Override
  					public void callback(String url, String html,AjaxStatus status) {

  						 if(html != null){               
  		                      System.out.println(html);
  		                      
  		                      
  		                 JSONArray jsonArray;
						try {
							
							com.alibaba.fastjson.JSONObject jobj = JSON.parseObject(html.trim());
							com.alibaba.fastjson.JSONArray  jobj2 = (com.alibaba.fastjson.JSONArray) jobj.get("mlist");
							com.alibaba.fastjson.JSONObject jobj3 = JSON.parseObject(jobj2.get(1).toString());
							System.out.println(jobj3.get("name"));
							
							jsonArray = new JSONArray(html.trim());
  		                  Map<String, String> map = null;   
  		                  List<Map<String, String>> list = new ArrayList<Map<String, String>>();   
  		                
  		                  for (int i = 0; i < jsonArray.length(); i++) {   
  		                      JSONObject item = jsonArray.getJSONObject(i); //每条记录又由几个Object对象组成   
  		                      int id = item.getInt("id");     // 获取对象对应的值   
  		                      String name = item.getString("realname");   
  		        
  		                      map = new HashMap<String, String>(); // 存放到MAP里面   
  		                      map.put("id", id + "");   
  		                      map.put("realname", name);  

  		                      System.out.println(id+"--"+name);
  		                      list.add(map);   
  		                  //}   
  		                
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						
						
  		               }else{          
  		               	     System.out.println("    //html error");
  		               }
  					}

  				});

  	}
      
      
      public void async_Josnhtml() { //同步html分析
    	  System.out.println("------async_Josnhtml-----");
    	  String url = "http://ecailan.cn/mobile/index.php/Mobile/Mobile/Experience.php";
  		AQuery aq = new AQuery(mContext);
  		 MyProgressDialog mPro = new MyProgressDialog(mContext, "请稍后...");
  		 
  		 Map<String, Object> params = new HashMap<String, Object>();
	      params.put("Func", "checkLogin");
	      params.put("Model", "Experience");
	      try {
		      JSONObject localItemObject = new JSONObject();
		      localItemObject.put("Name", "tigerst");
		      localItemObject.put("password", "c4eaa3098cf67de8bbd924dd613f223b");
		      params.put("data",localItemObject);
	      } catch (JSONException e) {
	            e.printStackTrace();
	        }
	      JSONObject jsonObj = new JSONObject(params);    
	      
	      Map<String, Object> paramsTo = new HashMap<String, Object>();
	      paramsTo.put("msg", jsonObj.toString());
       
  		 
  		aq.progress(mPro.mDialog).ajax(url, paramsTo,String.class, new AjaxCallback<String>() {

  					@Override
  					public void callback(String url, String html,AjaxStatus status) {

  						 if(html != null){               
  		                      System.out.println(html);
  		                      
  		                      
  		                 JSONArray jsonArray;
						try {
							
							com.alibaba.fastjson.JSONObject jobj = JSON.parseObject(html);
							com.alibaba.fastjson.JSONArray  jobj2 = (com.alibaba.fastjson.JSONArray) jobj.get("mlist");
							com.alibaba.fastjson.JSONObject jobj3 = JSON.parseObject(jobj2.get(1).toString());
							System.out.println(jobj3.get("name"));
  		                
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						
						
  		               }else{          
  		               	     System.out.println("    //html error");
  		               }
  					}

  				});

  	}
     
      
      public void testJsonList(){
        String url = "http://ecailan.cn/mobile/index.php/Mobile/Mobile/Experience.php";
        //{"Func":"Class_List","Model":"Experience","data":{"parent":0}}
        Map<String, Object> params = new HashMap<String, Object>();
	      params.put("Func", "Class_List");
	      params.put("Model", "Experience");
	      try {
		      JSONObject localItemObject = new JSONObject();
		      localItemObject.put("parent", 0);
		      params.put("data",localItemObject);
	      } catch (JSONException e) {
	            e.printStackTrace();
	        }
	      JSONObject jsonObj = new JSONObject(params);    
	      
	      Map<String, Object> paramsTo = new HashMap<String, Object>();
	      paramsTo.put("msg", jsonObj.toString());
	      
	   //   AQuery aq = new AQuery(mContext);
	      
	      if(aq==null){
	    	  AQuery aq = new AQuery(mContext);
	    	  aq.ajax(url, paramsTo, String.class, this, "jsonCallback");
	      }
	      else
	      {
  	    	//aq.ajax(url, paramsTo, String.class, this, "jsonCallback");
	      }

      }
      
      
      public void jsonCallback(String url, String  json, AjaxStatus status){
          
          if(json != null){               
                 System.out.println(json.toString());
          }else{          
          	 System.out.println("    //ajax error");
          }
          
  }*/
      
    
    public void login(String name, String password) {
    	
        usersigninRequest request = new usersigninRequest();
        BeeCallback<String> cb = new BeeCallback<String>() {

            @Override
            public void callback(String url, String sjo, AjaxStatus status) {
                JSONObject jo = null;
                try {
                    if (sjo != null)
                        jo = new JSONObject(sjo);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    Interaction.toastShow(mContext,"登陆失败！");
                    return;
                }
                LoginModel.this.callback(url, jo, status);
                try {
                    if (jo!=null && jo.optString("status").equals("0")) {
                        MemberResponse response = new MemberResponse();
                        response.fromJson(jo);
                        M_Member user = response.data;
                        editor.putString("useridg",user.useridg);
                        editor.putInt("usertypeid",user.usertypeid);
                        editor.putInt("userstateid",user.userstateid);
                        editor.putString("username",user.username);
                        editor.putString("password",user.password);
                        editor.putString("paypassword",user.paypassword);
                        editor.putString("realname",user.realname);
                        editor.putString("phone",user.phone);
                        editor.putString("address",user.address);
                        editor.putString("sex",user.sex);
                        editor.putString("iden",user.iden);
                        editor.putInt("money",user.money);
                        editor.putInt("expense",user.expense);
                        editor.putInt("points",user.points);
                        editor.putString("email",user.email);
                        editor.putString("qq",user.qq);
                        editor.putString("addtime",user.addtime);
                        editor.putString("optime",user.optime);
                        editor.putString("unsure_phone",user.unsure_phone);
                        editor.putString("lid",user.lid);
                        editor.putBoolean("disabled",user.disabled);
                        editor.putString("opuseridg",user.opuseridg);
                        editor.commit();
                        responseStatus=response.status;
                        bStatus = true;
                        LoginModel.this.OnMessageResponse(url, jo, status);
                    } else {
                        bStatus = false;
                        Interaction.toastShow(mContext,"账号密码错误！");
//                			 LoginModel.this.callErrorBack(url, jo, status);
                    }
                } catch (Exception e) {
                    Interaction.toastShow(mContext,"登陆失败！");
                    e.printStackTrace();
                }

            }
        };
        request.name = name;
        request.password = password;
        
        
          Map<String, Object> params = new HashMap<String, Object>();
	      params.put("func", "checklogin");
	      try {
		      JSONObject localItemObject = new JSONObject();
		      localItemObject.put("name", name);
		      localItemObject.put("pass",  MD5(password));
		      params.put("data",localItemObject);
	      } catch (JSONException e) {
	            e.printStackTrace();
	        }
	      JSONObject jsonObj = new JSONObject(params);    
	      
	      Map<String, Object> paramsTo = new HashMap<String, Object>();
	      paramsTo.put("msg", jsonObj.toString());

        cb.url(ApiInterface.USER_SIGNIN).type(String.class).params(paramsTo);//
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }
    
    
    
    public void sendPsw(String phone){
    	usersigninRequest request = new usersigninRequest();
        BeeCallback<String> cb = new BeeCallback<String>() {

            @Override
            public void callback(String url, String sjo, AjaxStatus status) {
            	JSONObject jo = null;
				try {
					if(sjo!=null)
						jo=new JSONObject(sjo);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
               LoginModel.this.callback(url, jo, status);
                try {
                	usersigninResponse response = new usersigninResponse();
                    response.fromJson(jo);
                    responseStatus=response.status;
                	 if (jo != null && response.status.succeed == 1) {
                		 USER user = response.data;
                		 if (user.info.equals("修改成功!")) 
                             bStatus = true;
                		 else{
                			 bStatus = false;
                			 ToastView toast = new ToastView(mContext,user.info);
              				toast.setGravity(Gravity.CENTER, 0, 0);
              				toast.show();	
                		 }                		 
                		 LoginModel.this.OnMessageResponse(url, jo, status);
                	 }
                	
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
        
        
          Map<String, Object> params = new HashMap<String, Object>();
	      params.put("Func", "sendPsw");
	      params.put("Model", "Experience");
	      try {
		      JSONObject localItemObject = new JSONObject();
		      localItemObject.put("Name", phone);
		      params.put("data",localItemObject);
	      } catch (JSONException e) {
	            e.printStackTrace();
	        }
	      JSONObject jsonObj = new JSONObject(params);    
	      
	      Map<String, Object> paramsTo = new HashMap<String, Object>();
	      paramsTo.put("msg", jsonObj.toString());

        cb.url(ApiInterface.SENDPSW).type(String.class).params(paramsTo);//
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }
    
    
    public void editPsw(String id,String password,String newpassword){

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                LoginModel.this.callback(url, jo, status);
                try {
                    if (jo != null && jo.optString("status").equals("0")) {
                        bStatus = true;
                    } else {
                        bStatus = false;
                        ToastView toast = new ToastView(mContext, "旧密码错误，修改失败！");
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    LoginModel.this.OnMessageResponse(url, jo, status);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
        
        
          Map<String, Object> params = new HashMap<String, Object>();
	      params.put("Func", "editpsw");
        params.put("devicecode", shared.getString("DeviceCode", ""));
        params.put("memberid", shared.getInt("memberid", -1));
        params.put("usertype", shared.getInt("usertype", -1));
	      try {
		      JSONObject localItemObject = new JSONObject();
		      localItemObject.put("password", MD5(password));
		      localItemObject.put("newpassword", MD5(newpassword));
		      params.put("data",localItemObject);
	      } catch (JSONException e) {
	            e.printStackTrace();
	        }
	      JSONObject jsonObj = new JSONObject(params);    
	      
	      Map<String, Object> paramsTo = new HashMap<String, Object>();
	      paramsTo.put("msg", jsonObj.toString());

        cb.requestCode("editpsw").type(JSONObject.class).params(paramsTo);//
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }

    public void test() {
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    if (status.getError()==null&& null != jo) {//jo={"status":"0","data":{"savename":"6b11da52b1f66e4cba8aa5ea5915e9649804"}}
                        LoginModel.this.OnMessageResponse(url, jo, status);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("Func", "upload_classblogimg");
        params.put("devicecode", "testDB");
        params.put("memberid", 1);
        params.put("usetype", 0); String uploadFile = "/sdcard/mypic1.JPEG";
        FileInputStream fStream=null;
try {
    fStream = new FileInputStream(uploadFile);
}
catch (Exception ee)
{
    Log.d("classblog upload img error:",ee.getMessage());
}
        JSONObject jsonObj = new JSONObject(params);
        Map<String, Object> paramsTo = new HashMap<String, Object>();
        paramsTo.put("msg", jsonObj);
        paramsTo.put("1.jpg", fStream);
        File f=new File(uploadFile);
        cb.url("").type(JSONObject.class).params(paramsTo);//
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }
}
