package com.aic.fetch;

import java.util.Date;
import java.util.List;
import java.io.*;
import com.aic.components.*;

/**
 * @todo implement
 */

public class Cache{
	//TODO: will not work on windows
	private static final String path = "/tmp";

	/**
	 * loads a list of already cached datasets
	 * cuts overlapping parts that are not in the given timerange
	 */
	public static List<TwitterStatusList> load(String name, Date start, Date end){
		return null;
	}

	/**
	 * serializes and stores the given tweetlist.
	 */
	public static void store(TwitterStatusList tweets){
		String filename = path + "/" + tweets.getKeyword() + "_" + tweets.getStart().getTime() + "_" + tweets.getEnd().getTime();

		try{
			FileOutputStream f_out = new FileOutputStream(filename);
			ObjectOutputStream obj_out = new ObjectOutputStream (f_out);

			// Write object out to disk
			obj_out.writeObject ( tweets );
		} catch(FileNotFoundException e){
			System.err.println("could not find file: " + filename);
		} catch(IOException e){
			System.err.println("could not write to file: " + filename);
		}

	}


}