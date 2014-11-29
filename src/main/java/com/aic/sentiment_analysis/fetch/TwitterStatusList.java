package com.aic.sentiment_analysis.fetch;

import java.util.ArrayList;
import java.util.Date;

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

        if(result.size() == 0){
            return result;
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

	/**
	 * Override add and update start/end - Date, if necessary
	 * @param status TwitterStatus to add
	 * @return true, if adding was successful
	 */
	@Override
	public boolean add(TwitterStatus status){

		if(start == null){
			start = status.getDate();
		}

		if(end == null){
			end = status.getDate();
		}

		if(start.getTime() >= status.getDate().getTime()){
			start = status.getDate();
		}

		if(end.getTime() <= status.getDate().getTime()){
			end = status.getDate();
		}




		return super.add(status);
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

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		System.out.println(this.toString() +"="+obj.toString());
		if (this == obj) return true;
		if (getClass() != obj.getClass()) return false;
		TwitterStatusList other = (TwitterStatusList) obj;
		if (end == null)
		{
			if (other.end != null) return false;
		}
		else if (!end.equals(other.end)) return false;
		if (keyword == null)
		{
			if (other.keyword != null) return false;
		}
		else if (!keyword.equals(other.keyword)) return false;
		if (start == null)
		{
			if (other.start != null) return false;
		}
		else if (!start.equals(other.start)) return false;
		if (size() != other.size()) return false;
		for (int i = 0; i < size(); i++)
		{
			if (!get(i).equals(other.get(i)))
				return false;
		}
		return true;
	}
}
