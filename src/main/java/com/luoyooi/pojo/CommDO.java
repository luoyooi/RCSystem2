package com.luoyooi.pojo;

public class CommDO {
	private String commDate;
	private String comment;
	public CommDO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CommDO(String commDate, String comment) {
		super();
		this.commDate = commDate;
		this.comment = comment;
	}
	public String getCommDate() {
		return commDate;
	}
	public void setCommDate(String commDate) {
		this.commDate = commDate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "commDo [commDate=" + commDate + ", comment=" + comment + "]";
	}
	
}
