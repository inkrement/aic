package com.aic.fetch;

import java.util.Date;
import java.util.List;
import java.io.*;
import com.aic.components.*;

import java.nio.file.*;

/**
 * @todo implement
 */

public class Cache{
	//TODO: will not work on windows
	public static final File path = new File("/tmp/aic");

	/**
	 * loads a list of already cached datasets
	 * cuts overlapping parts that are not in the given timerange
	 */
	public static List<TwitterStatusList> load(String name, Date start, Date end){

		//find all files matching name_*


		//parse timestamps and eventually load files


		return null;
	}

	/**
	 * serializes and stores the given tweetlist.
	 */
	public static void store(TwitterStatusList tweets){
		String filename = path + "/" + tweets.getKeyword() + "_" + tweets.getStart().getTime() + "_" + tweets.getEnd().getTime();
		//TODO: create subfolder

		path.getParentFile().mkdirs();


		try{
			FileOutputStream f_out = new FileOutputStream(filename);
			ObjectOutputStream obj_out = new ObjectOutputStream (f_out);

			// Write object out to disk
			obj_out.writeObject ( tweets );
		} catch(FileNotFoundException e){
			System.err.println("could not find file: " + filename + " " + e.getMessage());
		} catch(IOException e){
			System.err.println("could not write to file: " + filename + " " + e.getMessage());
		}

	}


	public static File[] getFilePaths(final String keyword){
		File dir = new File(path.getPath());
		File [] files = dir.listFiles(new FilenameFilter() {
		    @Override
		    public boolean accept(File dir, String name) {
		        return name.startsWith(keyword);
		    }
		});

		return files;
	}

	public static void clear(){
		clearDirectory(path);
	}

	public static void clearDirectory(File dir){
		//Files.deleteIfExists(path);
		for(File file: dir.listFiles()) file.delete();
	}

/*
	private void writeToFile(File file, String content){
		File file = new File("C:\\user\\Desktop\\dir1\\dir2\\filename.txt");
		file.getParentFile().mkdirs();
		FileWriter writer = new FileWriter(file);
	}
*/

}