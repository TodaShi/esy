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

/**
 * 手机号找回密码
 * 
 * @author Administrator
 * 
 */
public class lg_FindPwdActivity extends BaseActivity implements
		OnClickListener, BusinessResponse {

	private ImageView back;
	private EditText txtphone;
	private Button btnpwd;

	private LoginModel loginModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lg_pwd);

		back = (ImageView) findViewById(R.id.login_back);// 返回
		btnpwd = (Button) findViewById(R.id.find_btnpwd); // 找回密码
		txtphone = (EditText) findViewById(R.id.find_txtphoto); // 输入手机号

		back.setOnClickListener(this);
		btnpwd.setOnClickListener(this);
		txtphone.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.login_back:// 返回
			finish();
			CloseKeyBoard();
			overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
			break;
		case R.id.find_btnpwd: //
			String phone = txtphone.getText().toString();

			if ("".equals(phone) || phone.length() != 11) {
				ToastView toast = new ToastView(this,"请输入正确的手机号！");
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}

			loginModel = new LoginModel(lg_FindPwdActivity.this);
			loginModel.addResponseListener(this);
			loginModel.sendPsw(phone);
			CloseKeyBoard();
			break;
		}

	}

	@Override
	public void OnMessageResponse(String url,
			JSONObject jo, AjaxStatus status)
			 {
		if (loginModel.bStatus) {
			ToastView toast = new ToastView(this, "找回密码成功，系统已通过短信把密码发送给您的手机！");
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			finish();
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
		txtphone.clearFocus();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(txtphone.getWindowToken(), 0);
	}
}
