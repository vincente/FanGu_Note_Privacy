package com.eyu.privacynote.UI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.eyu.adinsert.FanguInsert;
import com.eyu.pbc.Dmanager;
import com.eyu.privacynote.Constants;
import com.eyu.privacynote.R;
import com.eyu.privacynote.DAO.DBHelper;
import com.eyu.privacynote.DAO.NOTE;
import com.eyu.privacynote.Util.SharePreferenceUtil;
import com.eyu.privacynote.business.Note_Bus;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.UMSsoHandler;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;

public class NoteDetailA extends Activity {

	private SensorManager sensorManager;
	private Vibrator vibrator;// 手机的振动
	private AlertDialog dialog;
	private Sensor sensor;
	private boolean hasShaked = false;// 判断是否已经摇晃的标志位
	private Button btn_left, btn_right, btn_delete, btn_cancle,btn_tuijian;
	private TextView txt_title_name, txt_note_date, txt_title_left,
			txt_title_right;
	private TextView txt_cityName, txt_temp, txt_tempDetail;
	private LinearLayout linear_title_pic, linear_list, linear_settings,
			linear_feedback, linear_more, linear_exit;
	private EditText et_note;
	private ImageView img_detail_delete, img_detail_share;
	private NOTE entity;
	boolean isAdd = true;
	private PopupView popupView;
	SlidingMenu menu;
	DBHelper helper;
	private SharePreferenceUtil util;
	FanguInsert fanguInsert;
	Dmanager dankead;

	final UMSocialService mController = UMServiceFactory.getUMSocialService(
			"com.umeng.share", RequestType.SOCIAL);

	public class KEY {
		public static final String id = "id";
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notedetail);
		initSlideMenu();
		findViews();
		handleEvents();
		initIntent();
		initAd();

	}

	private void initAd() {
		fanguInsert = FanguInsert.getInstance(getApplicationContext(),
				"1803702df0f264386660caca46ce9433");
		fanguInsert.loadpp(getApplicationContext());
		dankead = Dmanager.getInstance(getApplicationContext(),
				"1803702df0f264386660caca46ce9433");
		dankead.setThemeStyle(getApplicationContext(), 1);
	}

	private void initSlideMenu() {
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.main_side_shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		menu.setMenu(R.layout.menulayout_below);
	}

	private SensorEventListener listener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			float values[] = event.values;
			float x = values[0];// x轴方向的重力加速度
			float y = values[1];// y轴方向的重力加速度
			float z = values[2];// z轴方向的重力加速度

			// 这里设置的一个阈值为18，经测试比较满足一般的摇晃，也可以自己按需定义修改
			int medumValue = 19;
			if ((Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math
					.abs(z) > medumValue) && hasShaked == false) {
				if ((!(et_note.getText().toString().equals("")))
						&& hasShaked == false) {
					vibrator.vibrate(200);// 设置振动的频率
					showDialog();
					hasShaked = true;
				}
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub

		}
	};

	private void showDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		dialog = builder.create();
		LayoutInflater inflater = LayoutInflater.from(this);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.alertdialog, null);
		dialog.setCanceledOnTouchOutside(false);// 点击框外的空白处不会让对话框消失
		dialog.show();
		dialog.setContentView(layout, new LayoutParams(400, 250));

		btn_delete = (Button) layout.findViewById(R.id.btn_delete);
		btn_delete.setOnClickListener(new OnDialogClick());
		btn_cancle = (Button) layout.findViewById(R.id.btn_cancle);
		btn_cancle.setOnClickListener(new OnDialogClick());
	}

	private void findViews() {
		util = new SharePreferenceUtil(this, Constants.SAVE_USER);
		btn_left = (Button) findViewById(R.id.btn_title_left);
		btn_right = (Button) findViewById(R.id.btn_title_right);
		txt_title_name = (TextView) findViewById(R.id.txt_title_name);
		txt_title_left = (TextView) findViewById(R.id.txt_title_left);
		txt_title_right = (TextView) findViewById(R.id.txt_title_right);
		txt_note_date = (TextView) findViewById(R.id.txt_note_date);
		et_note = (EditText) findViewById(R.id.et_note);
		img_detail_share = (ImageView) findViewById(R.id.img_detail_forward);
		img_detail_delete = (ImageView) findViewById(R.id.img_detail_delete);
		btn_tuijian = (Button) findViewById(R.id.btn_tuijian);

		txt_cityName = (TextView) findViewById(R.id.txt_cityName);
		txt_temp = (TextView) findViewById(R.id.txt_temp);
		txt_tempDetail = (TextView) findViewById(R.id.txt_tempDetail);

		linear_title_pic = (LinearLayout) findViewById(R.id.menu_title_pic);
		linear_list = (LinearLayout) findViewById(R.id.menu_linear_list);
		linear_settings = (LinearLayout) findViewById(R.id.menu_linear_settings);
		linear_feedback = (LinearLayout) findViewById(R.id.menu_linear_feedback);
		linear_more = (LinearLayout) findViewById(R.id.menu_linear_more);
		linear_exit = (LinearLayout) findViewById(R.id.menu_linear_exit);
	}

	private void handleEvents() {
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

		txt_title_left.setVisibility(View.GONE);
		btn_left.setOnClickListener(new OnClickEvent());
		txt_title_name.setText(R.string.titlebar_txt_title_new);
		btn_right.setVisibility(View.GONE);
		txt_title_right.setOnClickListener(new OnClickEvent());
		btn_tuijian.setOnClickListener(new OnClickEvent());

		txt_cityName.setText(util.getCityName());
		txt_temp.setText(util.getTemp());
		txt_tempDetail.setText(util.getTempDetail());

		img_detail_share.setOnClickListener(new OnClickEvent());
		img_detail_delete.setOnClickListener(new OnClickEvent());

		linear_title_pic.setBackgroundResource(util.getMenuTitlePic());
		linear_list.setOnClickListener(new OnClickEvent());
		linear_settings.setOnClickListener(new OnClickEvent());
		linear_feedback.setOnClickListener(new OnClickEvent());
		linear_more.setOnClickListener(new OnClickEvent());
		linear_exit.setOnClickListener(new OnClickEvent());
	}

	private void initIntent() {
		Intent intent = this.getIntent();
		String id = intent.getStringExtra(KEY.id);
		if (id == null || id.trim().equals("")) {
			isAdd = true;
			entity = new NOTE();
			entity.setId(UUID.randomUUID().toString().replace("-", "").trim()
					.toString());
			setEntityToUI(entity);
		} else {
			isAdd = false;
			Note_Bus bus = new Note_Bus(this);
			entity = bus.queryForId(id);
			setEntityToUI(entity);

		}
	}

	public void setEntityToUI(NOTE entity) {
		if (isAdd) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.getDefault());
			String dateStr = sdf.format(date);
			txt_note_date.setText(dateStr);
		} else {
			txt_note_date.setText(entity.getNote_date());
		}
		et_note.setText(entity.getNote_detail());
		et_note.setSelection(et_note.getText().length());
	}

	class OnClickEvent implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.btn_title_left:
				if (menu.isMenuShowing()) {
					menu.showContent();
				} else {
					menu.showMenu();
				}
				break;

			case R.id.txt_title_right:
				save();
				break;
			case R.id.btn_tuijian:
				fanguInsert.showpp(NoteDetailA.this);
				break;
			case R.id.img_detail_forward:
				
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(et_note.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
				shareEvent();
				// 打开平台选择面板，参数2为打开分享面板时是否强制登录,false为不强制登录
				mController.openShare(NoteDetailA.this, false);
				break;
			case R.id.img_detail_delete:
				popupView = new PopupView(NoteDetailA.this, itemsOnclick);
				popupView.showAtLocation(
						NoteDetailA.this.findViewById(R.id.linear_notedetail),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
				break;
			case R.id.menu_linear_list:
				intent.setClass(getApplicationContext(), NoteListA.class);
				startActivity(intent);
				NoteDetailA.this.finish();
				menu.showContent();
				break;

			case R.id.menu_linear_settings:
				intent.setClass(getApplicationContext(), SettingsA.class);
				startActivity(intent);
				NoteDetailA.this.finish();
				menu.showContent();
				break;
			case R.id.menu_linear_feedback:
				FeedbackAgent agent = new FeedbackAgent(NoteDetailA.this);
				agent.startFeedbackActivity();
				menu.showContent();
				break;
			case R.id.menu_linear_more:
				// 获取自定义广告列表
				dankead.showlist(getApplicationContext());

				break;

			case R.id.menu_linear_exit:
				//fanguInsert.showpp(getApplicationContext());
				new AlertDialog.Builder(menu.getContext())
						.setTitle(R.string.dialog_exit_title)
						.setMessage(R.string.dialog_exit_content2)
						.setPositiveButton(R.string.dialog_exit_btn_done,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										NoteDetailA.this.finish();

									}
								})
						.setNegativeButton(R.string.dialog_exit_btn_cancel,
								null).show();
				break;

			default:
				break;
			}
		}
	}

	private void shareEvent() {
		// 设置分享内容
		mController.setShareContent(et_note.getText().toString());
		// 设置分享图片, 参数2为图片的地址
		mController
				.setShareMedia(new UMImage(NoteDetailA.this,
						"http://images.cnblogs.com/cnblogs_com/eyu8874521/527508/o_share_pic.png"));

		// --------------分享到微信和朋友圈相关----------------------
		String appID = "wx8efa863d6203e77c";
		// 微信图文分享必须设置一个url
		String contentUrl = "http://app.xiaomi.com/download/50627";
		// 添加微信平台，参数1为当前Activity, 参数2为用户申请的AppID, 参数3为点击分享内容跳转到的目标url
		mController.getConfig().supportWXPlatform(NoteDetailA.this, appID,
				contentUrl);
		// 支持微信朋友圈
		mController.getConfig().supportWXCirclePlatform(NoteDetailA.this,
				appID, contentUrl);
		// mController.setShareContent(et_note.getText().toString());
		// 设置分享图片, 参数2为图片的资源引用
		// mController.setShareMedia(new UMImage(NoteDetailA.this,
		// R.drawable.list_null));

		// --------------分享到QQ相关----------------------
		mController.getConfig().supportQQPlatform(NoteDetailA.this,
				"http://app.xiaomi.com/download/50627");
		mController.getConfig().setSsoHandler(
				new QZoneSsoHandler(NoteDetailA.this));

		// --------------分享到新浪微博相关----------------------
		// 设置新浪SSO handler
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		// --------------分享到腾讯微博相关----------------------
		// 设置腾讯微博SSO handler
		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
		// 设置腾讯微博SSO handler
		// mController.getConfig().setPlatforms(SHARE_MEDIA.FACEBOOK);

		mController.getConfig().removePlatform(SHARE_MEDIA.DOUBAN);
		mController.getConfig().removePlatform(SHARE_MEDIA.EMAIL);
		mController.getConfig().removePlatform(SHARE_MEDIA.QQ);

		mController.getConfig().setPlatformOrder(SHARE_MEDIA.WEIXIN,
				SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA,
				SHARE_MEDIA.SMS, SHARE_MEDIA.TENCENT, SHARE_MEDIA.RENREN,
				SHARE_MEDIA.FACEBOOK);
	}

	class OnDialogClick implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_delete:
				et_note.getText().clear();
				dialog.dismiss();
				hasShaked = false;
				break;
			case R.id.btn_cancle:
				dialog.dismiss();
				hasShaked = false;
			default:
				break;
			}
		}

	}

	/**
	 * 完成和返回列表通用的保存方法
	 * 
	 * @throws java.text.ParseException
	 */
	private void save() {
		Note_Bus bus = new Note_Bus(getApplicationContext());
		NOTE oldEntity = bus.queryForId(entity.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault());
		String dateStr = sdf.format(new Date());
		entity.setNote_date(dateStr);
		entity.setNote_detail(et_note.getText().toString());
		if (isAdd && entity.getNote_detail() != null
				&& !entity.getNote_detail().trim().equals("")) {
			bus.createNote(entity);
		} else if (!isAdd
				&& oldEntity.getNote_detail().equals(entity.getNote_detail())) {
		} else if (!isAdd && entity.getNote_detail() != null
				&& !entity.getNote_detail().trim().equals("")
				&& !oldEntity.getNote_detail().equals(entity.getNote_detail())) {
			oldEntity = bus.queryForId(entity.getId());
			bus.updateNote(oldEntity, entity);
		}

		setResult(Activity.RESULT_OK, null);
		Intent intent = new Intent(NoteDetailA.this, NoteListA.class);
		startActivity(intent);
		NoteDetailA.this.finish();

	}

	private OnClickListener itemsOnclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			popupView.dismiss();
			switch (v.getId()) {
			case R.id.btn_delete:
				Note_Bus bus = new Note_Bus(getApplicationContext());
				bus.deleteNote(entity);
				Toast.makeText(NoteDetailA.this, R.string.toast_delete_success,
						Toast.LENGTH_LONG).show();
				// 若果删除成功发个结果码回去，便于页面的刷新
				setResult(Activity.RESULT_OK, null);
				NoteDetailA.this.finish();

				break;
			case R.id.btn_cancle:
				popupView.dismiss();
				break;
			default:
				break;
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// fanguInsert.showpp(NoteDetailA.this);
			new AlertDialog.Builder(this)
					.setTitle(R.string.dialog_exit_title)
					.setMessage(R.string.dialog_exit_content2)
					.setPositiveButton(R.string.dialog_exit_btn_done,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									Intent intent = new Intent(
											NoteDetailA.this, NoteListA.class);
									startActivity(intent);
									NoteDetailA.this.finish();

								}
							})
					.setNegativeButton(R.string.dialog_exit_btn_cancel, null)
					.show();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
				requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
		if (sensorManager != null) {
			sensorManager.registerListener(listener, sensor,
					SensorManager.SENSOR_DELAY_NORMAL);
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
		if (sensorManager != null) {
			sensorManager.unregisterListener(listener);
		}
	}

}
