package com.luoyooi.pojo;

import java.util.ArrayList;

public class COMMVO {
	ArrayList<String> words;//对应x轴
	ArrayList<Integer> times;//对应y轴
	
	public COMMVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public COMMVO(ArrayList<String> words, ArrayList<Integer> times) {
		super();
		this.words = words;
		this.times = times;
	}
	public ArrayList<String> getWords() {
		return words;
	}
	public void setWords(ArrayList<String> words) {
		this.words = words;
	}
	public ArrayList<Integer> getTimes() {
		return times;
	}
	public void setTimes(ArrayList<Integer> times) {
		this.times = times;
	}
	@Override
	public String toString() {
		return "COMMVO [words=" + words + ", times=" + times + "]";
	}
	
}
