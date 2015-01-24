package com.aic.sentiment_analysis.fetch;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class provides the caching functionality.
 */
/**
 * @author Florian Taus
 *
 */
/**
 * @author Florian Taus
 */
/**
 * @author Florian Taus
 */
public class Cache {
	/** The path of the cache files. */
	public static final String PATH = "tmp" + File.separator + "cache" + File.separator;

	/**
	 * Loads a list of already cached datasets and cuts overlapping parts that are not in the given timerange.
	 *
	 * @param name
	 *            The keyword
	 * @param start
	 *            The start date of the timerange
	 * @param end
	 *            The end date of the timerange
	 * @return A list of twitter status lists
	 */
	public static List<TwitterStatusList> load(String name, Date start, Date end) {
		List<TwitterStatusList> result = new ArrayList<TwitterStatusList>();

		// find all files matching name_*
		File[] files = getFilePaths(name);

		// parse timestamps and eventually load files
		System.out.println("Files for " + name + ": " + files.length);
		for (File f: files) {
			String[] parts = f.getName().split("_");
			Date f_start = new Date(Long.parseLong(parts[1], 10));
			Date f_end = new Date(Long.parseLong(parts[2], 10));
			System.out.println(f.getAbsolutePath() + ": start: " + f_start + ", end: " + f_end);
			boolean b = overlap(f_start, f_end, start, end);
			if (b) {
				// load list and trim it to fit timerange
				result.add(loadFile(f).trim(start, end));
			}
			System.out.println("Overlap: " + b);
			System.out.println("Start r: " + start);
			System.out.println("Start f: " + f_start);
			System.out.println("End   r: " + end);
			System.out.println("End   f: " + f_end);
		}
		System.out.println("Cache result: " + result.size());
		return result;
	}

	/**
	 * Stores the list {@code tweets} into a file.
	 * 
	 * @param tweets
	 *            The list of tweets
	 * @return The file containt the list of tweets
	 * @throws IOException
	 *             If writing fails
	 */
	public static File store(TwitterStatusList tweets) {
		Date start = tweets.getStart();
		Date end = tweets.getEnd();
		System.out.println("Store: start: " + start + ", end: " + end);
		if (tweets.size() == Fetch.MAX_TWEETS) {
			start = tweets.getStart();
			end = tweets.getEnd();
		}
		File outputfile = new File(PATH + tweets.getKeyword() + "_" + start.getTime() + "_" + end.getTime() + ".tmp");

		try {
			FileOutputStream f_out = new FileOutputStream(outputfile);
			ObjectOutputStream obj_out = new ObjectOutputStream(f_out);

			// Write object out to disk
			obj_out.writeObject(tweets);
			obj_out.flush();
		} catch (FileNotFoundException e) {
			System.err.println("Could not find file: " + outputfile + " " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Could not write to file: " + outputfile + " " + e.getMessage());
			e.printStackTrace();
		}

		return outputfile;
	}

	/**
	 * Loads the content of the given file and into a twetter status list.
	 * 
	 * @param file
	 *            The file to read
	 * @return A list of tweets in the file
	 */
	public static TwitterStatusList loadFile(File file) {
		TwitterStatusList result = null;

		try {
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);

			result = (TwitterStatusList) in.readObject();

			in.close();
			fileIn.close();
		} catch (FileNotFoundException e) {
			System.err.println("loadFile: file not found");
		} catch (IOException i) {
			System.err.println("cound not load file");
		} catch (ClassNotFoundException c) {
			System.err.println("TwitterStatusList class not found");
		}

		return result;
	}

	/**
	 * Gets all files for a given keyword.
	 * 
	 * @param keyword
	 *            The keyword
	 * @return A list of files with the keyword
	 */
	public static File[] getFilePaths(final String keyword) {
		File dir = new File(PATH.toString());

		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith(keyword);
			}
		});
		if (files == null) {
			files = new File[0];
		}
		return files;
	}

	/**
	 * Deletes all files.
	 * 
	 * @throws IOException
	 *             If deleting fails
	 */
	public static void clear() throws IOException {
		String dir = PATH.toString();
		File d = new File(dir);
		for (File file: new File(dir).listFiles()) {
			Files.delete(file.toPath());
		}
	}

	/**
	 * Checks if to timeranges overlap.
	 * 
	 * @param f_start
	 *            The start of the first timerange
	 * @param f_end
	 *            The end of the first timerange
	 * @param start
	 *            The start of the second timerange
	 * @param end
	 *            The end of the second timerange
	 * @return If the ranges overlap
	 */
	public static boolean overlap(Date f_start, Date f_end, Date start, Date end) {
		return (start.getTime() <= f_start.getTime() && f_start.getTime() <= end.getTime())
				|| (start.getTime() <= f_end.getTime() && f_end.getTime() <= end.getTime());
	}
}
