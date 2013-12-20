package com.eyu.privacynote.UI;

import com.eyu.privacynote.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

public class PopupView extends PopupWindow {
	private Button btn_delete, btn_cancle;
	private View mView;

	public PopupView(Context context) {
		super(context);
	}

	@SuppressWarnings("deprecation")
	public PopupView(Activity context, OnClickListener itemsOnclick) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = inflater.inflate(R.layout.popup_delete, null);
		btn_delete = (Button) mView.findViewById(R.id.btn_delete);
		btn_cancle = (Button) mView.findViewById(R.id.btn_cancle);
		btn_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});

		btn_delete.setOnClickListener(itemsOnclick);
		this.setContentView(mView);
		this.setWidth(LayoutParams.FILL_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setAnimationStyle(R.style.PopupAnimation);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
	}

}
