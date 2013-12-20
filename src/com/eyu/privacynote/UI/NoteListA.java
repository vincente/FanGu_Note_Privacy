package com.eyu.privacynote.UI;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.eyu.adinsert.FanguInsert;
import com.eyu.pbc.Dmanager;
import com.eyu.privacynote.Constants;
import com.eyu.privacynote.R;
import com.eyu.privacynote.DAO.NOTE;
import com.eyu.privacynote.Util.SharePreferenceUtil;
import com.eyu.privacynote.adapter.NoteAdapter;
import com.eyu.privacynote.business.Note_Bus;
import com.eyu.vincent.Dudu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;


public class NoteListA extends Activity {
	private Button btn_title_left, btn_title_right,btn_tuijian;
	private TextView txt_title_name, txt_title_left, txt_title_right;
	private TextView txt_cityName,txt_temp,txt_tempDetail;
	private LinearLayout linear_title_pic, linear_list, linear_settings,
			linear_feedback, linear_more,linear_exit;
	private ListView note_list;
	private ImageView img_list_null;
	private List<NOTE> list = new ArrayList<NOTE>();
	public static final int requestCode_refresh = 1;
	boolean isSliderOpen;
	private NoteAdapter adapter;
	SlidingMenu menu;
	private SharePreferenceUtil util;
	FanguInsert fanguInsert;
	Dmanager dankead;
	static boolean isFirstLogin = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notelist);
		initSlideMenu();
		findViews();
		handleEvents();
		autoUpdate();
		initAd();
		
		if(isFirstLogin){
			fanguInsert.showpp(NoteListA.this);
			isFirstLogin = false;
		}

	}

	private void initAd() {
		fanguInsert = FanguInsert.getInstance(getApplicationContext(),
				"1803702df0f264386660caca46ce9433");
		fanguInsert.loadpp(getApplicationContext());

		Dudu dudu = Dudu.getInstance(getApplicationContext());
		dudu.getM(getApplicationContext(), "1803702df0f264386660caca46ce9433",
				2);
		
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
		btn_tuijian = (Button) findViewById(R.id.btn_tuijian);

		img_list_null = (ImageView) findViewById(R.id.img_list_null);
		note_list = (ListView) findViewById(R.id.note_list);
		
		txt_cityName = (TextView) findViewById(R.id.txt_cityName);
		txt_temp = (TextView) findViewById(R.id.txt_temp);
		txt_tempDetail = (TextView) findViewById(R.id.txt_tempDetail);

		linear_title_pic = (LinearLayout) findViewById(R.id.menu_title_pic);
		linear_list = (LinearLayout) findViewById(R.id.menu_linear_list);
		linear_settings = (LinearLayout) findViewById(R.id.menu_linear_settings);
		linear_more = (LinearLayout) findViewById(R.id.menu_linear_more);
		linear_feedback = (LinearLayout) findViewById(R.id.menu_linear_feedback);
		linear_exit = (LinearLayout) findViewById(R.id.menu_linear_exit);
	}

	private void handleEvents() {
		txt_title_name.setText(R.string.titlebar_txt_title_list);
		txt_title_left.setVisibility(View.GONE);
		txt_title_right.setVisibility(View.GONE);
		btn_title_left.setOnClickListener(new OnClickEvent());
		btn_title_right.setOnClickListener(new OnClickEvent());
		btn_tuijian.setOnClickListener(new OnClickEvent());

		linear_title_pic.setBackgroundResource(util.getMenuTitlePic());
		linear_list.setOnClickListener(new OnClickEvent());
		linear_settings.setOnClickListener(new OnClickEvent());
		linear_more.setOnClickListener(new OnClickEvent());
		linear_feedback.setOnClickListener(new OnClickEvent());
		linear_exit.setOnClickListener(new OnClickEvent());
		note_list.setOnItemClickListener(new OnItemClick_list());
		
		txt_cityName.setText(util.getCityName());
		txt_temp.setText(util.getTemp());
		txt_tempDetail.setText(util.getTempDetail());

		img_list_null.setVisibility(View.INVISIBLE);
		Note_Bus bus = new Note_Bus(NoteListA.this);
		list = bus.queryAll();
		if (list.isEmpty()) {
			img_list_null.setVisibility(View.VISIBLE);
		} else {
			img_list_null.setVisibility(View.INVISIBLE);
		}

		adapter = new NoteAdapter(this, list);
		note_list.setAdapter(adapter);

	}

	private void autoUpdate() {
		FeedbackAgent agent = new FeedbackAgent(this);
		agent.sync();
		UmengUpdateAgent.update(this);

		UmengUpdateAgent.setUpdateAutoPopup(false);
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
			@Override
			public void onUpdateReturned(int updateStatus,
					UpdateResponse updateInfo) {
				switch (updateStatus) {
				case 0: // has update
					UmengUpdateAgent.showUpdateDialog(NoteListA.this,
							updateInfo);
					break;
				}
			}

		});
	}

	class OnClickEvent implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.btn_title_right:
				intent.setClass(NoteListA.this, NoteDetailA.class);
				startActivityForResult(intent, requestCode_refresh);
				NoteListA.this.finish();
				break;
			case R.id.btn_title_left:
				if (menu.isMenuShowing()) {
					menu.showContent();
				} else {
					menu.showMenu();
				}
				break;
			case R.id.btn_tuijian:
				fanguInsert.showpp(NoteListA.this);
				break;

			case R.id.menu_linear_list:
				intent.setClass(getApplicationContext(), NoteListA.class);
				startActivity(intent);
				NoteListA.this.finish();
				menu.showContent();
				break;

			case R.id.menu_linear_settings:
				intent.setClass(getApplicationContext(), SettingsA.class);
				startActivity(intent);
				NoteListA.this.finish();
				menu.showContent();
				break;

			case R.id.menu_linear_feedback:
				FeedbackAgent agent = new FeedbackAgent(NoteListA.this);
				agent.startFeedbackActivity();
				menu.showContent();
				// NoteListA.this.finish();
				break;
			case R.id.menu_linear_more:
				// 获取自定义广告列表
				dankead.showlist(getApplicationContext());

				break;
			case R.id.menu_linear_exit:
				//fanguInsert.showpp(NoteListA.this);
				new AlertDialog.Builder(menu.getContext())
						.setTitle(R.string.dialog_exit_title)
						.setMessage(R.string.dialog_exit_content1)
						.setPositiveButton(R.string.dialog_exit_btn_done,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										NoteListA.this.finish();

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

	class OnItemClick_list implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			TextView txt_id = (TextView) view.findViewById(R.id.txt_id);
			String str_id = txt_id.getText().toString();

			Intent intent = new Intent();
			intent.putExtra(NoteDetailA.KEY.id, str_id);
			intent.setClass(NoteListA.this, NoteDetailA.class);
			startActivity(intent);
			NoteListA.this.finish();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case requestCode_refresh:
			if (resultCode != RESULT_OK)
				return;
			findViews();
			handleEvents();
			break;

		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		//fanguInsert.showpp(NoteListA.this);
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			new AlertDialog.Builder(this)
					.setTitle(R.string.dialog_exit_title)
					.setMessage(R.string.dialog_exit_content1)
					.setPositiveButton(R.string.dialog_exit_btn_done,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									NoteListA.this.finish();

								}
							})
					.setNegativeButton(R.string.dialog_exit_btn_cancel, null)
					.show();
		}
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
