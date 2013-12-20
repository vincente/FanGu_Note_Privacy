package com.eyu.privacynote.UI;

import com.eyu.adinsert.FanguInsert;
import com.eyu.pbc.Dmanager;
import com.eyu.privacynote.Constants;
import com.eyu.privacynote.R;
import com.eyu.privacynote.Util.SharePreferenceUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.newxp.common.ExchangeConstants;
import com.umeng.newxp.controller.ExchangeDataService;
import com.umeng.newxp.view.ExchangeViewManager;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.UMSsoHandler;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.update.UmengDownloadListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
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

public class SettingsA extends Activity {
	private Button btn_title_left, btn_title_right;
	private TextView txt_title_name, txt_title_left, txt_title_right;
	private TextView txt_cityName, txt_temp, txt_tempDetail;
	private LinearLayout linear_title_pic, linear_list, linear_settings,
			linear_feedback, linear_more, linear_exit, linear_modify,
			linear_question, linear_about, linear_share, linear_myapps,linear_random,linear_location,
			linear_update_pic, linear_update;
	SlidingMenu menu;
	private SharePreferenceUtil util;
	final UMSocialService mController = UMServiceFactory.getUMSocialService(
			"com.umeng.share", RequestType.SOCIAL);

	FanguInsert fanguInsert;
	Dmanager dankead;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		initSlideMenu();
		findViews();
		handleEvents();
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

		linear_modify = (LinearLayout) findViewById(R.id.setting_linear_modify);
		linear_question = (LinearLayout) findViewById(R.id.setting_linear_question);
		linear_about = (LinearLayout) findViewById(R.id.setting_linear_about);
		linear_share = (LinearLayout) findViewById(R.id.setting_linear_share);
		linear_myapps = (LinearLayout) findViewById(R.id.setting_linear_myapps);
		linear_random = (LinearLayout) findViewById(R.id.setting_linear_random);
		linear_update_pic = (LinearLayout) findViewById(R.id.setting_linear_update_pic);
		linear_location = (LinearLayout) findViewById(R.id.setting_linear_location);
		linear_update = (LinearLayout) findViewById(R.id.setting_linear_update);
	}

	private void handleEvents() {
		txt_title_name.setText(R.string.titlebar_txt_title_setting);
		txt_title_left.setVisibility(View.GONE);
		txt_title_right.setVisibility(View.GONE);
		btn_title_right.setVisibility(View.INVISIBLE);
		btn_title_left.setOnClickListener(new OnClickEvent());

		linear_title_pic.setBackgroundResource(util.getMenuTitlePic());
		linear_list.setOnClickListener(new OnClickEvent());
		linear_settings.setOnClickListener(new OnClickEvent());
		linear_feedback.setOnClickListener(new OnClickEvent());
		linear_more.setOnClickListener(new OnClickEvent());
		linear_exit.setOnClickListener(new OnClickEvent());

		txt_cityName.setText(util.getCityName());
		txt_temp.setText(util.getTemp());
		txt_tempDetail.setText(util.getTempDetail());

		linear_modify.setOnClickListener(new OnClickEvent());
		linear_question.setOnClickListener(new OnClickEvent());
		linear_about.setOnClickListener(new OnClickEvent());
		linear_share.setOnClickListener(new OnClickEvent());
		linear_myapps.setOnClickListener(new OnClickEvent());
		linear_random.setOnClickListener(new OnClickEvent());
		linear_update_pic.setOnClickListener(new OnClickEvent());
		linear_location.setOnClickListener(new OnClickEvent());
		linear_update.setOnClickListener(new OnClickEvent());
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
				SettingsA.this.finish();
				menu.showContent();
				break;

			case R.id.menu_linear_settings:
				intent.setClass(getApplicationContext(), SettingsA.class);
				startActivity(intent);
				SettingsA.this.finish();
				menu.showContent();
				break;
			case R.id.menu_linear_feedback:
				FeedbackAgent agent = new FeedbackAgent(SettingsA.this);
				agent.startFeedbackActivity();
				menu.showContent();
				break;
			case R.id.menu_linear_more:
				// 获取自定义广告列表
				dankead.showlist(getApplicationContext());
				break;
			case R.id.menu_linear_exit:
				// fanguInsert.showpp(SettingsA.this);
				new AlertDialog.Builder(menu.getContext())
						.setTitle(R.string.dialog_exit_title)
						.setMessage(R.string.dialog_exit_content1)
						.setPositiveButton(R.string.dialog_exit_btn_done,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										SettingsA.this.finish();

									}
								})
						.setNegativeButton(R.string.dialog_exit_btn_cancel,
								null).show();
				break;

			case R.id.setting_linear_modify:
				intent.setClass(getApplicationContext(), ModifyPwdA.class);
				startActivity(intent);
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				SettingsA.this.finish();
				break;
			case R.id.setting_linear_question:
				intent.setClass(getApplicationContext(), SetQuestionA.class);
				startActivity(intent);
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				SettingsA.this.finish();
				break;
			case R.id.setting_linear_about:
				intent.setClass(getApplicationContext(), AboutA.class);
				startActivity(intent);
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				SettingsA.this.finish();
				break;
			case R.id.setting_linear_share:
				shareEvent();
				// 打开平台选择面板，参数2为打开分享面板时是否强制登录,false为不强制登录
				mController.openShare(SettingsA.this, false);
				break;
			case R.id.setting_linear_myapps:
				ExchangeDataService service = new ExchangeDataService();
				ExchangeConstants.APPKEY = "525baf6c56240bf01d0039b3";
				new ExchangeViewManager(SettingsA.this, service).addView(
						ExchangeConstants.type_list_curtain, null);
				break;
			case R.id.setting_linear_random:
				fanguInsert.showpp(SettingsA.this);
				break;
			case R.id.setting_linear_update_pic:
				intent.setClass(getApplicationContext(), UpdatePicA.class);
				startActivity(intent);
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				SettingsA.this.finish();
				break;
			case R.id.setting_linear_location:
				intent.setClass(getApplicationContext(), CitySelectionA.class);
				startActivity(intent);
				SettingsA.this.finish();
				break;
			case R.id.setting_linear_update:

				UmengUpdateListener updateListener = new UmengUpdateListener() {
					@Override
					public void onUpdateReturned(int updateStatus,
							UpdateResponse updateInfo) {
						switch (updateStatus) {
						case 0: // has update
							UmengUpdateAgent.showUpdateDialog(SettingsA.this,
									updateInfo);
							break;
						case 1: // has no update
							Toast.makeText(SettingsA.this,
									R.string.toast_latest_version,
									Toast.LENGTH_SHORT).show();
							break;
						case 2: // none wifi
							Toast.makeText(SettingsA.this,
									R.string.toast_no_wifi, Toast.LENGTH_SHORT)
									.show();
							break;
						case 3: // time out
							Toast.makeText(SettingsA.this,
									R.string.toast_timeout, Toast.LENGTH_SHORT)
									.show();
							break;
						}

					}
				};
				UmengUpdateAgent.setUpdateOnlyWifi(false); // 目前我们默认在Wi-Fi接入情况下才进行自动提醒。如需要在其他网络环境下进行更新自动提醒，则请添加该行代码
				UmengUpdateAgent.setUpdateAutoPopup(false);
				UmengUpdateAgent.setUpdateListener(updateListener);
				UmengUpdateAgent
						.setDownloadListener(new UmengDownloadListener() {

							@Override
							public void OnDownloadStart() {
								Toast.makeText(SettingsA.this,
										R.string.toast_start_download,
										Toast.LENGTH_SHORT).show();
							}

							@Override
							public void OnDownloadUpdate(int progress) {
								Toast.makeText(
										SettingsA.this,
										R.string.toast_download_process
												+ progress + "%",
										Toast.LENGTH_SHORT).show();
							}

							@Override
							public void OnDownloadEnd(int result, String file) {
								// Toast.makeText(mContext, "download result : "
								// + result , Toast.LENGTH_SHORT).show();
								Toast.makeText(SettingsA.this,
										R.string.toast_finish_download,
										Toast.LENGTH_SHORT).show();
							}
						});
				UmengUpdateAgent.forceUpdate(SettingsA.this);
				break;

			default:
				break;
			}
		}
	}

	private void shareEvent() {
		// 设置分享内容
		mController
				.setShareContent("我正在使用《私密笔记》，还不错哦，推荐给大家~！~！妈妈再也不用担心老婆翻我手机啦！"
						+ "-->>" + "http://app.xiaomi.com/download/50627");
		// 设置分享图片, 参数2为图片的地址
		mController
				.setShareMedia(new UMImage(SettingsA.this,
						"http://images.cnblogs.com/cnblogs_com/eyu8874521/527508/o_share_pic.png"));

		mController.getConfig().setPlatformOrder(SHARE_MEDIA.WEIXIN,
				SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA,
				SHARE_MEDIA.TENCENT, SHARE_MEDIA.RENREN);

		
		
		
		
		// --------------分享到微信和朋友圈相关----------------------
		String appID = "wx8efa863d6203e77c";
		// 微信图文分享必须设置一个url
		String contentUrl = "http://app.xiaomi.com/download/50627";
		//String contentUrl = "http://www.baidu.com";
		// 添加微信平台，参数1为当前Activity, 参数2为用户申请的AppID, 参数3为点击分享内容跳转到的目标url
		mController.getConfig().supportWXPlatform(SettingsA.this, appID,
				contentUrl);
		// 支持微信朋友圈
		mController.getConfig().supportWXCirclePlatform(SettingsA.this, appID,
				contentUrl);
		
		// 设置分享文字     	
		mController
				.setShareContent("我正在使用《私密笔记》，推荐给大家~！妈妈再也不用担心老婆翻我手机啦！请猛戳--》http://app.xiaomi.com/download/50627");
		// 设置分享图片, 参数2为图片的资源引用
		mController.setShareMedia(new UMImage(SettingsA.this,
				"http://images.cnblogs.com/cnblogs_com/eyu8874521/527508/o_share_pic.png"));

		// --------------分享到QQ相关----------------------
		mController.getConfig().supportQQPlatform(SettingsA.this,
				"http://app.xiaomi.com/download/50627");
		mController.getConfig().setSsoHandler(
				new QZoneSsoHandler(SettingsA.this));

		// --------------分享到新浪微博相关----------------------
		// 设置新浪SSO handler
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		// --------------分享到腾讯微博相关----------------------
		// 设置腾讯微博SSO handler
		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
		mController.getConfig().removePlatform(SHARE_MEDIA.DOUBAN);
		mController.getConfig().removePlatform(SHARE_MEDIA.EMAIL);
		mController.getConfig().removePlatform(SHARE_MEDIA.QQ);

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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		// fanguInsert.showpp(SettingsA.this);
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
									SettingsA.this.finish();

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
