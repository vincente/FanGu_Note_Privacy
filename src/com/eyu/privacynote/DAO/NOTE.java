package com.eyu.privacynote.DAO;

public class NOTE {
	private String id;
	private String note_detail;
	private String note_date;

	public NOTE() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNote_detail() {
		return note_detail;
	}

	public void setNote_detail(String note_detail) {
		this.note_detail = note_detail;
	}

	public String getNote_date() {
		return note_date;
	}

	public void setNote_date(String note_date) {
		this.note_date = note_date;
	}

}
