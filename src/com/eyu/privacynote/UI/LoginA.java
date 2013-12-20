package com.eyu.privacynote.UI;

import com.eyu.adinsert.FanguInsert;
import com.eyu.privacynote.R;
import com.eyu.privacynote.Constants;
import com.eyu.privacynote.Util.SharePreferenceUtil;
import com.eyu.vincent.Dudu;
import com.umeng.analytics.MobclickAgent;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginA extends Activity {

	ImageButton imgBtn1, imgBtn2, imgBtn3, imgBtn4, imgBtn5, imgBtn6, imgBtn7,
			imgBtn8, imgBtn9, imgBtn0;
	ImageView imgCircle1, imgCircle2, imgCircle3, imgCircle4;
	Button btn_forgetPwd, btn_clear, btn_tips;
	public static int clickCount;
	public static StringBuilder sb = new StringBuilder();
	private SharePreferenceUtil util;
	boolean isShowTips;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		initAd();
		util = new SharePreferenceUtil(this, Constants.SAVE_USER);
		findViews();
		findListeners();
		MobclickAgent.setDebugMode(true);

	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				imgCircle1.setImageResource(R.drawable.login_circle_null);
				imgCircle2.setImageResource(R.drawable.login_circle_null);
				imgCircle3.setImageResource(R.drawable.login_circle_null);
				imgCircle4.setImageResource(R.drawable.login_circle_null);
				break;
			case 1:
				imgCircle1.setImageResource(R.drawable.login_circle_full);
				imgCircle2.setImageResource(R.drawable.login_circle_null);
				imgCircle3.setImageResource(R.drawable.login_circle_null);
				imgCircle4.setImageResource(R.drawable.login_circle_null);
				break;
			case 2:
				imgCircle1.setImageResource(R.drawable.login_circle_full);
				imgCircle2.setImageResource(R.drawable.login_circle_full);
				imgCircle3.setImageResource(R.drawable.login_circle_null);
				imgCircle4.setImageResource(R.drawable.login_circle_null);
				break;
			case 3:
				imgCircle1.setImageResource(R.drawable.login_circle_full);
				imgCircle2.setImageResource(R.drawable.login_circle_full);
				imgCircle3.setImageResource(R.drawable.login_circle_full);
				imgCircle4.setImageResource(R.drawable.login_circle_null);
				break;
			case 4:
				imgCircle1.setImageResource(R.drawable.login_circle_full);
				imgCircle2.setImageResource(R.drawable.login_circle_full);
				imgCircle3.setImageResource(R.drawable.login_circle_full);
				imgCircle4.setImageResource(R.drawable.login_circle_full);

				if (sb.toString().trim().equals(util.getUserPwd())) {
					Intent intent = new Intent(LoginA.this, NoteListA.class);
					startActivity(intent);
					clickCount = 0;
					sb.delete(0, sb.length());
					overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
					LoginA.this.finish();
				} else {
					Toast.makeText(getApplicationContext(),
							R.string.toast_wrong_password, Toast.LENGTH_SHORT)
							.show();
					clickCount = 0;
					sb.delete(0, sb.length());
					Animation shake = AnimationUtils.loadAnimation(LoginA.this,
							R.anim.shake);// 加载动画资源文件
					findViewById(R.id.linear_circles).startAnimation(shake); // 给组件播放动画效果

					imgCircle1.setImageResource(R.drawable.login_circle_null);
					imgCircle2.setImageResource(R.drawable.login_circle_null);
					imgCircle3.setImageResource(R.drawable.login_circle_null);
					imgCircle4.setImageResource(R.drawable.login_circle_null);

				}
				break;

			default:
				break;
			}
		}
	};

	private void findViews() {
		imgBtn0 = (ImageButton) findViewById(R.id.img_btn_0);
		imgBtn1 = (ImageButton) findViewById(R.id.img_btn_1);
		imgBtn2 = (ImageButton) findViewById(R.id.img_btn_2);
		imgBtn3 = (ImageButton) findViewById(R.id.img_btn_3);
		imgBtn4 = (ImageButton) findViewById(R.id.img_btn_4);
		imgBtn5 = (ImageButton) findViewById(R.id.img_btn_5);
		imgBtn6 = (ImageButton) findViewById(R.id.img_btn_6);
		imgBtn7 = (ImageButton) findViewById(R.id.img_btn_7);
		imgBtn8 = (ImageButton) findViewById(R.id.img_btn_8);
		imgBtn9 = (ImageButton) findViewById(R.id.img_btn_9);

		imgCircle1 = (ImageView) findViewById(R.id.img_no1);
		imgCircle2 = (ImageView) findViewById(R.id.img_no2);
		imgCircle3 = (ImageView) findViewById(R.id.img_no3);
		imgCircle4 = (ImageView) findViewById(R.id.img_no4);

		btn_forgetPwd = (Button) findViewById(R.id.btn_forgetPwd);
		btn_clear = (Button) findViewById(R.id.btn_clear);
		btn_tips = (Button) findViewById(R.id.btn_tips);
		isShowTips = util.getTips();
		if (isShowTips) {
			btn_tips.setVisibility(View.VISIBLE);
		} else {
			btn_tips.setVisibility(View.INVISIBLE);
		}

	}

	private void initAd() {
		Dudu dudu = Dudu.getInstance(this);
		dudu.getM(getApplicationContext(), "1803702df0f264386660caca46ce9433",
				2);
		FanguInsert fanguInsert = FanguInsert.getInstance(
				getApplicationContext(), "1803702df0f264386660caca46ce9433");
		fanguInsert.loadpp(getApplicationContext());

	}

	private void findListeners() {
		imgBtn0.setOnClickListener(new NumListener());
		imgBtn1.setOnClickListener(new NumListener());
		imgBtn2.setOnClickListener(new NumListener());
		imgBtn3.setOnClickListener(new NumListener());
		imgBtn4.setOnClickListener(new NumListener());
		imgBtn5.setOnClickListener(new NumListener());
		imgBtn6.setOnClickListener(new NumListener());
		imgBtn7.setOnClickListener(new NumListener());
		imgBtn8.setOnClickListener(new NumListener());
		imgBtn9.setOnClickListener(new NumListener());

		btn_forgetPwd.setOnClickListener(new NumListener());
		btn_clear.setOnClickListener(new NumListener());
		btn_tips.setOnClickListener(new NumListener());

	}

	class NumListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.img_btn_0:
				if (clickCount < 4) {
					sb.append("0");
					clickCount++;
				}
				break;
			case R.id.img_btn_1:
				if (clickCount < 4) {
					sb.append("1");
					clickCount++;
				}
				break;
			case R.id.img_btn_2:
				if (clickCount < 4) {
					sb.append("2");
					clickCount++;
				}
				break;
			case R.id.img_btn_3:
				if (clickCount < 4) {
					sb.append("3");
					clickCount++;
				}
				break;
			case R.id.img_btn_4:
				if (clickCount < 4) {
					sb.append("4");
					clickCount++;
				}
				break;
			case R.id.img_btn_5:
				if (clickCount < 4) {
					sb.append("5");
					clickCount++;
				}
				break;
			case R.id.img_btn_6:
				if (clickCount < 4) {
					sb.append("6");
					clickCount++;
				}
				break;
			case R.id.img_btn_7:
				if (clickCount < 4) {
					sb.append("7");
					clickCount++;
				}
				break;
			case R.id.img_btn_8:
				if (clickCount < 4) {
					sb.append("8");
					clickCount++;
				}
				break;
			case R.id.img_btn_9:
				if (clickCount < 4) {
					sb.append("9");
					clickCount++;
				}
				break;
			case R.id.btn_forgetPwd:
				if (util.getQuestion() == null) {
					AlertDialog.Builder builder = new Builder(LoginA.this);
					builder.setMessage(R.string.dialog_forget_content);
					builder.setTitle(R.string.dialog_forget_title);
					builder.setPositiveButton(
							R.string.dialog_forget_ok,
							new android.content.DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});
					builder.create().show();
				} else {
					final EditText text = new EditText(getApplicationContext());
					AlertDialog.Builder builder = new Builder(LoginA.this);
					builder.setTitle(util.getQuestion());
					builder.setView(text)
							.setPositiveButton(
									R.string.dialog_forget_btn_done,
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											String answerStr = text.getText()
													.toString().trim();
											if (answerStr.equals(util
													.getAnswer().trim())) {
												Intent intent = new Intent(
														LoginA.this,
														ResetPwdA.class);
												startActivity(intent);
											} else if (!answerStr.equals(util
													.getAnswer().trim())) {
												dialog.dismiss();
												Toast.makeText(
														LoginA.this,
														R.string.toast_wrong_answer,
														Toast.LENGTH_SHORT)
														.show();
											}

										}
									})
							.setNegativeButton(
									R.string.dialog_forget_btn_cancel,
									new android.content.DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											dialog.dismiss();
										}
									}).show();
				}

				break;
			case R.id.btn_clear:
				if (clickCount > 0) {
					sb.deleteCharAt(sb.length() - 1);
					clickCount--;
				}

				break;
			case R.id.btn_tips:
				if (isShowTips) {
					btn_tips.setVisibility(View.INVISIBLE);
					util.setTips(false);
				}
				break;

			default:

				break;
			}

			Message message = handler.obtainMessage();
			if (clickCount == 0) {
				message.what = 0;
			} else if (clickCount == 1) {
				message.what = 1;
			} else if (clickCount == 2) {
				message.what = 2;
			} else if (clickCount == 3) {
				message.what = 3;
			} else if (clickCount == 4) {
				message.what = 4;
			}
			handler.sendMessage(message);

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
