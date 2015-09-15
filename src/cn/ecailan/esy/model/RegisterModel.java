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
import android.view.Gravity;


import org.json.JSONException;
import org.json.JSONObject;
import org.ry8.CeaFrame.model.BaseModel;
import org.ry8.CeaFrame.model.BeeCallback;
import org.ry8.CeaFrame.view.MyProgressDialog;
import org.ry8.CeaFrame.view.ToastView;
import org.ry8.external.androidquery.callback.AjaxStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.ecailan.esy.R;
import cn.ecailan.esy.ApiInterface;
import cn.ecailan.esy.protocol.STATUS;
import cn.ecailan.esy.protocol.USER;
import cn.ecailan.esy.protocol.usersigninResponse;
import cn.ecailan.esy.protocol.usersignupRequest;

/**
 * 用户注册
 * 
 * @author Administrator
 * 
 */
public class RegisterModel extends BaseModel {

	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
	public ArrayList<SIGNUPFILEDS> signupfiledslist = new ArrayList<SIGNUPFILEDS>();
	public STATUS responseStatus;
	public USER user = new USER();

	public RegisterModel(Context context) {
		super(context);

		shared = context.getSharedPreferences("userInfo", 0);
		editor = shared.edit();
	}

	/*
	 * public void signupFields() { usersignupFieldsRequest request = new
	 * usersignupFieldsRequest();
	 * 
	 * BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
	 * 
	 * @Override public void callback(String url, JSONObject jo, AjaxStatus
	 * status) {
	 * 
	 * RegisterModel.this.callback(url, jo, status); try {
	 * usersignupFieldsResponse response = new usersignupFieldsResponse();
	 * response.fromJson(jo); if (jo != null) { responseStatus =
	 * response.status; if (responseStatus.succeed ==
	 * ErrorCodeConst.ResponseSucceed) { ArrayList<SIGNUPFILEDS> data =
	 * response.data; if (null != data && data.size() > 0) {
	 * signupfiledslist.clear(); signupfiledslist.addAll(data); } } else if
	 * (responseStatus.error_code == ErrorCodeConst.UserOrEmailExist) {
	 * Resources resource = mContext.getResources(); String user_or_email_exists
	 * = resource .getString(R.string.user_or_email_exists); ToastView toast =
	 * new ToastView(mContext, user_or_email_exists);
	 * toast.setGravity(Gravity.CENTER, 0, 0); toast.show(); }
	 * 
	 * RegisterModel.this.OnMessageResponse(url, jo, status); } } catch
	 * (JSONException e) {
	 * 
	 * e.printStackTrace(); } }
	 * 
	 * }; cb.url(ApiInterface.USER_SIGNUPFIELDS).type(JSONObject.class);
	 * MyProgressDialog pd = new MyProgressDialog(mContext, mContext
	 * .getResources().getString(R.string.hold_on));
	 * aq.progress(pd.mDialog).ajax(cb); }
	 */

	public void signup(String name, String mail, String passwordStr,
			String passwordRepeatStr, String s_qq, String s_iden,
			String s_phone, String s_address, String s_realname,String s_business_id) {
		usersignupRequest request = new usersignupRequest();

		BeeCallback<String> cb = new BeeCallback<String>() {

			@Override
			public void callback(String url, String sjo, AjaxStatus status) {

				JSONObject jo = null;
				try {
					if (sjo != null)
						jo = new JSONObject(sjo);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				RegisterModel.this.callback(url, jo, status);
				try {
					usersigninResponse response = new usersigninResponse();
					response.fromJson(jo);
					responseStatus = response.status;
					if (jo != null && response.status.succeed == 1) {
						user = response.data;
						if (!"-1".equals(user.id+"")) {
							editor.putInt("uid", user.id);
//							editor.putString("sid", user.classid);
//							editor.putString("realname", user.realname);
//							editor.putString("email", user.email);
							editor.putString("users", user.toJson().toString());
							editor.commit();
						} else {
                            ToastView toast = new ToastView(mContext,jo.getString("info"));
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
						}
						RegisterModel.this.OnMessageResponse(url, jo, status);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Func", "reg");
		params.put("Model", "Experience");
		try {
			JSONObject localItemObject = new JSONObject();
			localItemObject.put("qq", s_qq);
			localItemObject.put("membername", name);
			localItemObject.put("iden", s_iden);
			localItemObject.put("password", passwordStr);
			localItemObject.put("paypassword", passwordRepeatStr);
			localItemObject.put("phone", s_phone);
			localItemObject.put("email", mail);
			localItemObject.put("address", s_address);
			localItemObject.put("realname", s_realname);
			localItemObject.put("BusinessID",s_business_id);
			params.put("data", localItemObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONObject jsonObj = new JSONObject(params);

		Map<String, Object> paramsTo = new HashMap<String, Object>();
		paramsTo.put("msg", jsonObj.toString());

		cb.url(ApiInterface.USER_SIGNUP).type(String.class).params(paramsTo);
		MyProgressDialog pd = new MyProgressDialog(mContext, mContext
				.getResources().getString(R.string.hold_on));
		aq.progress(pd.mDialog).ajax(cb);
	}

	//
	public void recharge_step1(String s_cardNo, String s_pin, String s_cardPsw,
			String s_cardrand) {
		usersignupRequest request = new usersignupRequest();

		BeeCallback<String> cb = new BeeCallback<String>() {

			@Override
			public void callback(String url, String sjo, AjaxStatus status) {

				JSONObject jo = null;
				try {
					if (sjo != null)
						jo = new JSONObject(sjo);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				RegisterModel.this.callback(url, jo, status);
				try {
					String state = jo.getString("state");
					if ("-1".equalsIgnoreCase(state)) {
						ToastView toast = new ToastView(mContext,"卡序列号和密码验证无效,请重新输入！");
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					} else if ("-2".equalsIgnoreCase(state)) {
						ToastView toast = new ToastView(mContext,"充值卡未激活,请与客服联系,电话400-101-4816！");
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}else if ("1".equalsIgnoreCase(state)) {
						ToastView toast = new ToastView(mContext, "请耐心接收短信,1分钟内没有收到短信可以再次获取！");
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						RegisterModel.this.OnMessageResponse(url, jo, status);
					}else {
						ToastView toast = new ToastView(mContext,"未知错误");
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Func", "recharge_step1");
		params.put("Model", "Experience");
		try {
			JSONObject localItemObject = new JSONObject();
			localItemObject.put("uid", shared.getString("uid", ""));
			localItemObject.put("code", "" + s_cardNo + s_pin);
			localItemObject.put("psw", s_cardPsw);
			params.put("data", localItemObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONObject jsonObj = new JSONObject(params);

		Map<String, Object> paramsTo = new HashMap<String, Object>();
		paramsTo.put("msg", jsonObj.toString());

		cb.url(ApiInterface.RECHARGE_STEP1).type(String.class).params(paramsTo);
		MyProgressDialog pd = new MyProgressDialog(mContext, mContext
				.getResources().getString(R.string.hold_on));
		aq.progress(pd.mDialog).ajax(cb);

	}
	
	
	public void recharge_step2(String s_cardNo, String s_pin, String s_cardPsw,
			String s_cardrand) {
		usersignupRequest request = new usersignupRequest();

		BeeCallback<String> cb = new BeeCallback<String>() {

			@Override
			public void callback(String url, String sjo, AjaxStatus status) {

				JSONObject jo = null;
				try {
					if (sjo != null)
						jo = new JSONObject(sjo);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				RegisterModel.this.callback(url, jo, status);
				try {
					String state = jo.getString("state");
					if ("-1".equalsIgnoreCase(state)) {
						ToastView toast = new ToastView(mContext,"卡序列号和密码验证无效,请重新输入！");
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					} else if ("-2".equalsIgnoreCase(state)) {
						ToastView toast = new ToastView(mContext,"充值卡未激活,请与客服联系,电话400-101-4816！");
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}else if ("1".equalsIgnoreCase(state)) {
						ToastView toast = new ToastView(mContext, "充值成功！");
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						RegisterModel.this.OnMessageResponse(url, jo, status);
					}else {
						ToastView toast = new ToastView(mContext,"未知错误");
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Func", "recharge_step2");
		params.put("Model", "Experience");
		try {
			JSONObject localItemObject = new JSONObject();
			localItemObject.put("uid", shared.getString("uid", ""));
			localItemObject.put("code", "" + s_cardNo + s_pin);
			localItemObject.put("psw", s_cardPsw);
			localItemObject.put("randomcode", s_cardrand);
			params.put("data", localItemObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONObject jsonObj = new JSONObject(params);

		Map<String, Object> paramsTo = new HashMap<String, Object>();
		paramsTo.put("msg", jsonObj.toString());

		cb.url(ApiInterface.RECHARGE_STEP2).type(String.class).params(paramsTo);
		MyProgressDialog pd = new MyProgressDialog(mContext, mContext
				.getResources().getString(R.string.hold_on));
		aq.progress(pd.mDialog).ajax(cb);

	}

}
