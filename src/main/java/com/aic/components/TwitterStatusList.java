package com.aic.components;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

import com.aic.fetch.Fetch;

// TODO javadoc	
public class TwitterStatusList extends ArrayList<TwitterStatus> implements Comparable<TwitterStatusList>
{
	private static final long serialVersionUID = -445662097030771988L;
	private String keyword;
	private Date start;
	private Date end;

	private TwitterStatusList()
		{
			super();
		}

	public TwitterStatusList(String keyword)
		{
			this();
			this.keyword = keyword;
		}

	/**
	 * return starting timestamp
	 */
	public Date getStart()
	{
		return start;
	}

	public long getStartId()
	{
		return size() == 0 ? 0 : this.get(0).getId();
	}

	public long getEndId()
	{
		return size() == 0 ? Long.MAX_VALUE : this.get(this.size() - 1).getId();
	}

	/**
	 * return ending timestamp
	 */
	public Date getEnd()
	{
		return end; // to get last
	}

	public String getKeyword()
	{
		return keyword;
	}

	public TwitterStatusList trim(Date start2, Date end2)
	{
		if (start2.getTime() <= start.getTime() && end.getTime() <= end2.getTime())
		{
			return this;
		}
		TwitterStatusList result = new TwitterStatusList(this.keyword);
		for (TwitterStatus ts: this)
		{
			if (start2.getTime() <= ts.getTimestamp() && ts.getTimestamp() <= end2.getTime())
			{
				result.add(ts);
			}
		}

		Date newStart = result.get(0).getDate();
		Date newEnd = result.get(result.size() - 1).getDate();
		if (start.getTime() <= start2.getTime())
		{
			newStart = start2;
		}
		if (end2.getTime() <= end.getTime())
		{
			newEnd = end2;
		}
		result.setDates(newStart, newEnd);
		return result;
	}

	@Override
	public int compareTo(TwitterStatusList other)
	{
		int c = getStart().compareTo(other.getStart());
		if (c == 0)
		{
			c = getEnd().compareTo(getEnd());
		}
		return c;
	}

	public void setDates(Date start, Date end)
	{
		this.start = start;
		this.end = end;
	}

}
