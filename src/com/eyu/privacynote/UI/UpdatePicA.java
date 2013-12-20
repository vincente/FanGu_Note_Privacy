package com.eyu.privacynote.UI;

import com.eyu.adinsert.FanguInsert;
import com.eyu.pbc.Dmanager;
import com.eyu.privacynote.Constants;
import com.eyu.privacynote.R;
import com.eyu.privacynote.R.string;
import com.eyu.privacynote.Util.SharePreferenceUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UpdatePicA extends Activity {
	private Button btn_title_left, btn_title_right;
	private TextView txt_title_name, txt_title_left, txt_title_right;
	private TextView txt_cityName,txt_temp,txt_tempDetail;
	private LinearLayout linear_title_pic, linear_list, linear_settings,
			linear_feedback,linear_more, linear_exit;
	private SharePreferenceUtil util;
	LinearLayout linearPic;
	SlidingMenu menu;
	FanguInsert fanguInsert;
	Dmanager dankead;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updatepic);
		initSlideMenu();
		findViews();
		handleEvents();
		initAd();
	}

	private void initAd() {
		fanguInsert = FanguInsert.getInstance(getApplicationContext(),
				"1803702df0f264386660caca46ce9433");
		fanguInsert.loadpp(getApplicationContext());
		
		dankead = Dmanager.getInstance(getApplicationContext(),"1803702df0f264386660caca46ce9433");
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

	private void findViews() {
		util = new SharePreferenceUtil(this, Constants.SAVE_USER);
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);
		txt_title_left = (TextView) findViewById(R.id.txt_title_left);
		txt_title_right = (TextView) findViewById(R.id.txt_title_right);
		txt_title_name = (TextView) findViewById(R.id.txt_title_name);
		
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
		txt_title_name.setText(string.titlebar_txt_title_settheme);
		txt_title_left.setVisibility(View.GONE);
		txt_title_right.setVisibility(View.GONE);
		btn_title_left.setOnClickListener(new OnClickEvent());
		btn_title_right.setVisibility(View.INVISIBLE);
		
		txt_cityName.setText(util.getCityName());
		txt_temp.setText(util.getTemp());
		txt_tempDetail.setText(util.getTempDetail());
		

		linear_title_pic.setBackgroundResource(util.getMenuTitlePic());
		linear_list.setOnClickListener(new OnClickEvent());
		linear_settings.setOnClickListener(new OnClickEvent());
		linear_feedback.setOnClickListener(new OnClickEvent());
		linear_more.setOnClickListener(new OnClickEvent());
		linear_exit.setOnClickListener(new OnClickEvent());

		findViewById(R.id.update_pic01).setOnClickListener(new OnClickEvent());
		findViewById(R.id.update_pic02).setOnClickListener(new OnClickEvent());
		findViewById(R.id.update_pic03).setOnClickListener(new OnClickEvent());
		findViewById(R.id.update_pic04).setOnClickListener(new OnClickEvent());
		findViewById(R.id.update_pic05).setOnClickListener(new OnClickEvent());
		findViewById(R.id.update_pic06).setOnClickListener(new OnClickEvent());
		findViewById(R.id.update_pic07).setOnClickListener(new OnClickEvent());
		findViewById(R.id.update_pic08).setOnClickListener(new OnClickEvent());
		findViewById(R.id.update_pic09).setOnClickListener(new OnClickEvent());
		findViewById(R.id.update_pic10).setOnClickListener(new OnClickEvent());

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
					handleEvents();
				} else {
					menu.showMenu();
					handleEvents();
				}
				break;

			case R.id.menu_linear_list:
				intent.setClass(getApplicationContext(), NoteListA.class);
				startActivity(intent);
				UpdatePicA.this.finish();
				menu.showContent();
				break;

			case R.id.menu_linear_settings:
				intent.setClass(getApplicationContext(), SettingsA.class);
				startActivity(intent);
				UpdatePicA.this.finish();
				menu.showContent();
				break;
			case R.id.menu_linear_feedback:
				FeedbackAgent agent = new FeedbackAgent(UpdatePicA.this);
				agent.startFeedbackActivity();
				menu.showContent();
				break;
			case R.id.menu_linear_more:
				// 获取自定义广告列表
				dankead.showlist(getApplicationContext());
				break;
			case R.id.menu_linear_exit:
				//fanguInsert.showpp(UpdatePicA.this);
				new AlertDialog.Builder(menu.getContext())
						.setTitle(string.dialog_exit_title)
						.setMessage(string.dialog_exit_content1)
						.setPositiveButton(string.dialog_exit_btn_done,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										UpdatePicA.this.finish();

									}
								})
						.setNegativeButton(string.dialog_exit_btn_cancel, null)
						.show();
				break;

			case R.id.update_pic01:
				util.setMenuTitlePic(R.drawable.menu_title_pic01);
				ToastTips();
				handleEvents();
				break;
			case R.id.update_pic02:
				util.setMenuTitlePic(R.drawable.menu_title_pic02);
				ToastTips();
				handleEvents();
				break;
			case R.id.update_pic03:
				util.setMenuTitlePic(R.drawable.menu_title_pic03);
				ToastTips();
				handleEvents();
				break;
			case R.id.update_pic04:
				util.setMenuTitlePic(R.drawable.menu_title_pic04);
				ToastTips();
				handleEvents();
				break;
			case R.id.update_pic05:
				util.setMenuTitlePic(R.drawable.menu_title_pic05);
				ToastTips();
				handleEvents();
				break;
			case R.id.update_pic06:
				util.setMenuTitlePic(R.drawable.menu_title_pic06);
				ToastTips();
				handleEvents();
				break;
			case R.id.update_pic07:
				util.setMenuTitlePic(R.drawable.menu_title_pic07);
				ToastTips();
				handleEvents();
				break;
			case R.id.update_pic08:
				util.setMenuTitlePic(R.drawable.menu_title_pic08);
				ToastTips();
				handleEvents();
				break;
			case R.id.update_pic09:
				util.setMenuTitlePic(R.drawable.menu_title_pic09);
				ToastTips();
				handleEvents();
				break;
			case R.id.update_pic10:
				util.setMenuTitlePic(R.drawable.menu_title_pic10);
				ToastTips();
				handleEvents();
				break;
			}
		}
	}

	private void ToastTips() {
		Toast.makeText(UpdatePicA.this, string.toast_theme_success,
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getApplicationContext(), SettingsA.class);
		startActivity(intent);
		UpdatePicA.this.finish();
		return super.onKeyDown(keyCode, event);
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
