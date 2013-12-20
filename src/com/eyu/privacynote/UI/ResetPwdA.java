package com.eyu.privacynote.UI;

import com.eyu.privacynote.R;
import com.eyu.privacynote.Constants;
import com.eyu.privacynote.R.string;
import com.eyu.privacynote.Util.SharePreferenceUtil;
import com.umeng.analytics.MobclickAgent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ResetPwdA extends Activity {
	private EditText et_reset_new, et_reset_confirm;
	private Button btn_title_left, btn_title_right;
	private TextView txt_title_name, txt_title_left, txt_title_right;
	private SharePreferenceUtil util;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resetpwd);
		findViews();
		handleEvents();
	}

	private void findViews() {
		util = new SharePreferenceUtil(getApplicationContext(),
				Constants.SAVE_USER);

		et_reset_new = (EditText) findViewById(R.id.et_reset_new);
		et_reset_confirm = (EditText) findViewById(R.id.et_reset_confirm);

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);
		txt_title_left = (TextView) findViewById(R.id.txt_title_left);
		txt_title_right = (TextView) findViewById(R.id.txt_title_right);
		txt_title_name = (TextView) findViewById(R.id.txt_title_name);
	}

	private void handleEvents() {
		txt_title_name.setText(string.titlebar_txt_title_reset);
		txt_title_left.setText(string.titlebar_txt_back);
		txt_title_right.setText(string.titlebar_txt_done);
		btn_title_left.setVisibility(View.GONE);
		btn_title_right.setVisibility(View.GONE);

		txt_title_left.setOnClickListener(new OnClickEvent());
		txt_title_right.setOnClickListener(new OnClickEvent());

	}

	class OnClickEvent implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.txt_title_left:
				ResetPwdA.this.finish();
				break;
			case R.id.txt_title_right:
				String newStr01 = et_reset_new.getText().toString();
				String newStr02 = et_reset_confirm.getText().toString();
				if (newStr01.equals(newStr02) && !newStr01.equals("")
						&& !newStr02.equals("") && newStr01.length() == 4
						&& newStr02.length() == 4) {
					util.setUserPwd(newStr01);
					ResetPwdA.this.finish();
				} else if (!newStr01.equals(newStr02)) {
					Toast.makeText(getApplicationContext(),
							string.toast_modify_different, Toast.LENGTH_SHORT)
							.show();
				} else if (newStr01.equals("") || newStr02.equals("")) {
					Toast.makeText(getApplicationContext(),
							string.toast_modify_isnull, Toast.LENGTH_SHORT)
							.show();
				} else if (newStr01.length() != 4 || newStr02.length() != 4) {
					Toast.makeText(getApplicationContext(),
							string.toast_modify_input_number,
							Toast.LENGTH_SHORT).show();
				}

				break;
			default:
				break;
			}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

}
