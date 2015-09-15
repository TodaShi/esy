package cn.ecailan.esy.activity;

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
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONObject;
import org.ry8.CeaFrame.activity.BaseActivity;
import org.ry8.CeaFrame.model.BusinessResponse;
import org.ry8.CeaFrame.view.ToastView;
import org.ry8.external.androidquery.callback.AjaxStatus;

import cn.ecailan.esy.R;
import cn.ecailan.esy.model.LoginModel;
import cn.ecailan.esy.model.SESSION;

/**
 * 修改密码
 * 
 * @author Administrator
 * 
 */
public class lg_EditPwdActivity extends BaseActivity implements
		OnClickListener, BusinessResponse {

	private ImageView back;
	private EditText oldpwd;
	private EditText newpwd;
	private EditText confirmpwd;
	private Button btnpwd;

	private LoginModel loginModel;
	

	private SharedPreferences shared;
	private SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lg_editpwd);

		back = (ImageView) findViewById(R.id.login_back);// 返回
		btnpwd = (Button) findViewById(R.id.find_btnpwd); // 修改密码
		oldpwd = (EditText) findViewById(R.id.set_oldpwd); // 旧密码
		newpwd = (EditText) findViewById(R.id.set_newpwd); // 旧密码
		confirmpwd = (EditText) findViewById(R.id.set_confirmpwd); // 旧密码

		back.setOnClickListener(this);
		btnpwd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Resources resource = (Resources) getBaseContext().getResources();
		switch (v.getId()) {
		case R.id.login_back:// 返回
			finish();
			CloseKeyBoard();
			overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
			break;
		case R.id.find_btnpwd: //
			String oldp = oldpwd.getText().toString();
			String pwd1 = newpwd.getText().toString();
			String pwd2 = confirmpwd.getText().toString();

			if ("".equals(oldp) || oldp.length() == 0) {
				ToastView toast = new ToastView(this,"请输入旧密码");
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}
			
			if ("".equals(pwd1) || pwd1.length() == 0) {
				ToastView toast = new ToastView(this,"请输入新密码");
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}
			
			else if(pwd1.length()<6){
                ToastView toast = new ToastView(this, resource.getString(R.string.password_too_short));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            else if(pwd1.length()>20){
                ToastView toast = new ToastView(this, resource.getString(R.string.password_too_long));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
			
			if (!pwd1.equals(pwd2)) {
				ToastView toast = new ToastView(this,"两次输入的密码不正确");
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}

			shared = this.getSharedPreferences("userInfo", 0);
			editor = shared.edit();
			String uid = shared.getString("uid", "");
			loginModel = new LoginModel(lg_EditPwdActivity.this);
			loginModel.addResponseListener(this);
			loginModel.editPsw(uid,oldp,pwd1);
			CloseKeyBoard();
			break;
		}

	}

	@Override
	public void OnMessageResponse(String url,
			JSONObject jo, AjaxStatus status)
			 {
		if (loginModel.bStatus) {
			editor.putString("uid", "");
			editor.putString("sid", "");
			editor.putString("users", "");
			editor.commit();
			SESSION.getInstance().uid = shared.getString("uid", "");
			SESSION.getInstance().sid = shared.getString("sid", "");
			finish();
			
			ToastView toast = new ToastView(this, "修改密码成功，请重新登陆！");
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			
			Intent intent = new Intent(this, lg_SigninActivity.class);
			startActivity(intent);
			
			overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
		}
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
		}
		return true;
	}

	// 关闭键盘
	public void CloseKeyBoard() {
		confirmpwd.clearFocus();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(confirmpwd.getWindowToken(), 0);
	}
}
