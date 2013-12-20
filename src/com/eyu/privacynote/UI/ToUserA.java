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

public class ToUserA extends Activity {
	private Button btn_title_left, btn_title_right;
	private TextView txt_title_name, txt_title_left, txt_title_right;
	private TextView txt_cityName,txt_temp,txt_tempDetail;
	private LinearLayout linear_title_pic, linear_list, linear_settings,
			linear_feedback,linear_more, linear_exit;
	SlidingMenu menu;
	private SharePreferenceUtil util;
	FanguInsert fanguInsert;
	Dmanager dankead;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.touser);
		util = new SharePreferenceUtil(this, Constants.SAVE_USER);
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
		txt_title_name.setText(string.titlebar_txt_title_touser);
		txt_title_left.setVisibility(View.GONE);
		txt_title_right.setVisibility(View.GONE);
		btn_title_right.setVisibility(View.INVISIBLE);
		btn_title_left.setOnClickListener(new OnClickEvent());
		
		txt_cityName.setText(util.getCityName());
		txt_temp.setText(util.getTemp());
		txt_tempDetail.setText(util.getTempDetail());


		linear_title_pic.setBackgroundResource(util.getMenuTitlePic());
		linear_list.setOnClickListener(new OnClickEvent());
		linear_settings.setOnClickListener(new OnClickEvent());
		linear_feedback.setOnClickListener(new OnClickEvent());
		linear_more.setOnClickListener(new OnClickEvent());
		linear_exit.setOnClickListener(new OnClickEvent());

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

			case R.id.menu_linear_list:
				intent.setClass(getApplicationContext(), NoteListA.class);
				startActivity(intent);
				ToUserA.this.finish();
				menu.showContent();
				break;

			case R.id.menu_linear_settings:
				intent.setClass(getApplicationContext(), SettingsA.class);
				startActivity(intent);
				ToUserA.this.finish();
				menu.showContent();
				break;
			case R.id.menu_linear_feedback:
				FeedbackAgent agent = new FeedbackAgent(ToUserA.this);
				agent.startFeedbackActivity();
				menu.showContent();
				// NoteListA.this.finish();
				break;
			case R.id.menu_linear_more:
				// 获取自定义广告列表
				dankead.showlist(getApplicationContext());
				break;
			case R.id.menu_linear_exit:
				//fanguInsert.showpp(ToUserA.this);
				new AlertDialog.Builder(menu.getContext())
						.setTitle(string.dialog_exit_title)
						.setMessage(string.dialog_exit_content1)
						.setPositiveButton(string.dialog_exit_btn_done,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										ToUserA.this.finish();
									}
								})
						.setNegativeButton(string.dialog_exit_btn_cancel, null)
						.show();
				break;
			default:
				break;
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(ToUserA.this, AboutA.class);
		startActivity(intent);
		ToUserA.this.finish();
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
