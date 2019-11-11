package com.luoyooi.pojo;

import java.util.ArrayList;

public class MvInfoDO implements Comparable<MvInfoDO>{
	private String name;
	private ArrayList<String> directors;
	private ArrayList<String> screenwriters;
	private ArrayList<String> tostars;
	private ArrayList<String> types;
	private String country;
	private String language;
	private String redate;
	private String mvLong;
	private String othername;
	private double score;
	private long commPeopleNum;
	private ArrayList<String> stars;
	private String posterAdrr;
	
	public MvInfoDO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MvInfoDO(String name, ArrayList<String> directors, ArrayList<String> screenwriters,
			ArrayList<String> tostars, ArrayList<String> types, String country, String language, String redate,
			String mvLong, String othername, double score, long commPeopleNum, ArrayList<String> stars,
			String posterAdrr) {
		super();
		this.name = name;
		this.directors = directors;
		this.screenwriters = screenwriters;
		this.tostars = tostars;
		this.types = types;
		this.country = country;
		this.language = language;
		this.redate = redate;
		this.mvLong = mvLong;
		this.othername = othername;
		this.score = score;
		this.commPeopleNum = commPeopleNum;
		this.stars = stars;
		this.posterAdrr = posterAdrr;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getDirectors() {
		return directors;
	}

	public void setDirectors(ArrayList<String> directors) {
		this.directors = directors;
	}

	public ArrayList<String> getScreenwriters() {
		return screenwriters;
	}

	public void setScreenwriters(ArrayList<String> screenwriters) {
		this.screenwriters = screenwriters;
	}

	public ArrayList<String> getTostars() {
		return tostars;
	}

	public void setTostars(ArrayList<String> tostars) {
		this.tostars = tostars;
	}

	public ArrayList<String> getTypes() {
		return types;
	}

	public void setTypes(ArrayList<String> types) {
		this.types = types;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getRedate() {
		return redate;
	}

	public void setRedate(String redate) {
		this.redate = redate;
	}

	public String getMvLong() {
		return mvLong;
	}

	public void setMvLong(String mvLong) {
		this.mvLong = mvLong;
	}

	public String getOthername() {
		return othername;
	}

	public void setOthername(String othername) {
		this.othername = othername;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public long getCommPeopleNum() {
		return commPeopleNum;
	}

	public void setCommPeopleNum(long commPeopleNum) {
		this.commPeopleNum = commPeopleNum;
	}

	public ArrayList<String> getStars() {
		return stars;
	}

	public void setStars(ArrayList<String> stars) {
		this.stars = stars;
	}

	public String getPosterAdrr() {
		return posterAdrr;
	}

	public void setPosterAdrr(String posterAdrr) {
		this.posterAdrr = posterAdrr;
	}

	@Override
	public String toString() {
		return "mvInfoDO [name=" + name + ", directors=" + directors + ", screenwriters=" + screenwriters + ", tostars="
				+ tostars + ", types=" + types + ", country=" + country + ", language=" + language + ", redate="
				+ redate + ", mvLong=" + mvLong + ", othername=" + othername + ", score=" + score + ", commPeopleNum="
				+ commPeopleNum + ", stars=" + stars + ", posterAdrr=" + posterAdrr + "]";
	}
	@Override
	public int compareTo(MvInfoDO o) {
		return Double.compare(o.score, this.score);
	}
}
