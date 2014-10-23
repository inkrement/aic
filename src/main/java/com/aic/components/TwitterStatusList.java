package com.aic.components;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;


public class TwitterStatusList extends ArrayList<TwitterStatus> implements Serializable{
	private String keyword;

	private TwitterStatusList(){
		super();
	}

	public TwitterStatusList(String keyword){
		super();

		this.keyword = keyword;
	}

	/**
	 * return starting timestamp
	 */
	public Date getStart(){
		return this.get(0).getTimestamp();
	}

	/**
	 * return ending timestamp
	 */
	public Date getEnd(){
		return this.get(this.size()-1).getTimestamp(); //to get last
	}

	public String getKeyword(){
		return keyword;
	}

}