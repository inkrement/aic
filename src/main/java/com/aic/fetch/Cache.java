package com.aic.fetch;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
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
	//UNTESTED
	public static List<TwitterStatusList> load(String name, Date start, Date end){
		List<TwitterStatusList> result = new ArrayList<TwitterStatusList>();

		//find all files matching name_*
		File[] files = getFilePaths(name);


		//parse timestamps and eventually load files

		for(File f: files){
			//parse timestamp and check
			String[] parts = f.getName().split("_");
			Date f_start = new Date(Long.parseLong(parts[1], 10));
			Date f_end = new Date(Long.parseLong(parts[2], 10));

			if(overlap(f_start, f_end, start, end)){
				//load into list
				result.add(loadFile(f));
			}
		}

		return result;
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

	//UNTESTED
	public static TwitterStatusList loadFile(File file){
		TwitterStatusList result = null;

		try{
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);

			result = (TwitterStatusList) in.readObject();
			in.close();
			fileIn.close();
		} catch(IOException i){
			i.printStackTrace();
		} catch(ClassNotFoundException c){
			System.out.println("Employee class not found");
			c.printStackTrace();
		}

		return result;
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


	public static boolean overlap(Date start1, Date end1, Date start2, Date end2){
	    return start1.getTime() <= end2.getTime() && start2.getTime() <= end1.getTime(); 
	}

/*
	private void writeToFile(File file, String content){
		File file = new File("C:\\user\\Desktop\\dir1\\dir2\\filename.txt");
		file.getParentFile().mkdirs();
		FileWriter writer = new FileWriter(file);
	}
*/

}