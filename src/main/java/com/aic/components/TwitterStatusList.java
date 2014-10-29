package com.aic.components;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;

import java.io.IOException;
import java.lang.ClassNotFoundException;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

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
		return new Date(this.get(0).getTimestamp());
	}

	/**
	 * return ending timestamp
	 */
	public Date getEnd(){
		return new Date(this.get(this.size()-1).getTimestamp()); //to get last
	}

	public String getKeyword(){
		return keyword;
	}

	public TwitterStatusList trim(Date start, Date end){
		TwitterStatusList result = new TwitterStatusList(this.keyword);

		for(TwitterStatus ts: this){
			if(ts.getTimestamp() >= start.getTime() && ts.getTimestamp() < end.getTime()){
				result.add(ts);
			}
		}

		return result;
	}



	private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(this.keyword);
    }
 
    private void readObject(ObjectInputStream in) throws IOException,ClassNotFoundException {
        in.defaultReadObject();
        this.keyword = (String)in.readObject();
    }

}