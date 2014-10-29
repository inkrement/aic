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
	public static final File path = new File("tmp" + File.separator + "cache");

	/**
	 * loads a list of already cached datasets
	 * cuts overlapping parts that are not in the given timerange
	 */
	public static List<TwitterStatusList> load(String name, Date start, Date end){
		List<TwitterStatusList> result = new ArrayList<TwitterStatusList>();

		//find all files matching name_*
		File[] files = getFilePaths(name);

		//parse timestamps and eventually load files

		for(File f: files){
			String[] parts = f.getName().split("_");
			Date f_start = new Date(Long.parseLong(parts[1], 10));
			Date f_end = new Date(Long.parseLong(parts[2], 10));

			if(overlap(f_start, f_end, start, end)){
				//load list and trim it to fit timerange
				result.add(loadFile(f).trim(start, end));
			}
		}

		return result;
	}

	/**
	 * serializes and stores the given tweetlist.
	 */
	public static File store(TwitterStatusList tweets){
		File outputfile = new File(path + File.separator + tweets.getKeyword() + "_" + tweets.getStart().getTime() + "_" + tweets.getEnd().getTime());
		outputfile.getParentFile().mkdirs();

		try{
			FileOutputStream f_out = new FileOutputStream(outputfile);
			ObjectOutputStream obj_out = new ObjectOutputStream (f_out);

			// Write object out to disk
			obj_out.writeObject ( tweets );
			obj_out.flush();
		} catch(FileNotFoundException e){
			System.err.println("could not find file: " + outputfile + " " + e.getMessage());
		} catch(IOException e){
			System.err.println("could not write to file: " + outputfile + " " + e.getMessage());
		}

		return outputfile;
	}

	//UNTESTED
	public static TwitterStatusList loadFile(File file){
		TwitterStatusList result = null;

		try{
			System.out.println("loadF: " + file);
			FileInputStream fileIn = new FileInputStream(file);
			System.out.println("loadF: " + fileIn);
			ObjectInputStream in = new ObjectInputStream(fileIn);

			System.out.println("loadF: " + in);

			result = (TwitterStatusList) in.readObject();

			System.out.println("loadF: " + result);

			in.close();
			fileIn.close();
		} catch(FileNotFoundException e){
			System.err.println("loadFile: file not found");
		} catch(IOException i){
			System.err.println("cound not load file");
		} catch(ClassNotFoundException c){
			System.err.println("TwitterStatusList class not found");
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

}