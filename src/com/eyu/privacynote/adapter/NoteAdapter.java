package com.eyu.privacynote.adapter;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.eyu.privacynote.R;
import com.eyu.privacynote.DAO.NOTE;

public class NoteAdapter extends BaseAdapter {

	private Context context;
	private List<NOTE> list = new ArrayList<NOTE>();

	public NoteAdapter(Context context, List<NOTE> list) {
		this.context = context;
		this.list = list;
		// this.type = tf;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.note_item, null);
		}
		TextView txt_id = (TextView) convertView.findViewById(R.id.txt_id);
		TextView txt_noteDetail = (TextView) convertView
				.findViewById(R.id.txt_note_title);
		// txt_noteDetail.setTypeface(type);
		// txt_noteDetail.getPaint().setFakeBoldText(true);

		TextView txt_noteDate = (TextView) convertView
				.findViewById(R.id.txt_note_date);

		ImageView img_arrow = (ImageView) convertView
				.findViewById(R.id.img_arrow);

		NOTE note = list.get(position);
		txt_id.setText(note.getId());
		txt_noteDetail.setText(note.getNote_detail());

		txt_noteDate.setText(note.getNote_date().substring(0, 10));
		img_arrow.setImageResource(R.drawable.icon_triangle);
		return convertView;
	}

}
