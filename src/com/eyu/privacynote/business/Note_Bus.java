package com.eyu.privacynote.business;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eyu.privacynote.DAO.DBHelper;
import com.eyu.privacynote.DAO.NOTE;

public class Note_Bus {
	DBHelper helper;

	public Note_Bus(Context context) {
		helper = new DBHelper(context);
	}

	public List<NOTE> queryAll() {
		SQLiteDatabase db = helper.getReadableDatabase();
		List<NOTE> list = new ArrayList<NOTE>();
		NOTE entity = null;
		Cursor c = db.rawQuery("select * from NOTE", null);
		while (c.moveToNext()) {
			entity = new NOTE();
			entity.setId(c.getString(c.getColumnIndex("id")));
			entity.setNote_date(c.getString(c.getColumnIndex("note_date")));
			entity.setNote_detail(c.getString(c.getColumnIndex("note_detail")));
			list.add(entity);
		}
		c.close();
		db.close();
		return list;
	}

	public NOTE queryForId(String id) {
		NOTE entity = null;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("select * from NOTE where id = " + "'" + id
				+ "'", null);
		if (c.moveToFirst()) {
			entity = new NOTE();

			entity.setId(c.getString(c.getColumnIndex("id")));
			entity.setNote_date(c.getString(c.getColumnIndex("note_date")));
			entity.setNote_detail(c.getString(c.getColumnIndex("note_detail")));

		}
		c.close();
		db.close();
		return entity;
	}

	public void createNote(NOTE entity) {
		SQLiteDatabase db = helper.getWritableDatabase();
		String id = entity.getId();
		String note_date = entity.getNote_date();
		String note_detail = entity.getNote_detail();
		db.execSQL(
				"insert into NOTE (id,note_date,note_detail) values (?,?,?)",
				new Object[] { id, note_date, note_detail });
		db.close();
	}

	public void updateNote(NOTE oldEntity, NOTE newEntity) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("note_detail", newEntity.getNote_detail());
		cv.put("note_date", newEntity.getNote_date());
		db.update("NOTE", cv, "id=?", new String[] { oldEntity.getId() });
		db.close();
	}

	public void deleteNote(NOTE entity) {
		SQLiteDatabase db = helper.getWritableDatabase();
		String id = entity.getId();
		db.delete("NOTE", "id = ?", new String[] { id });
		db.close();
	}

}
