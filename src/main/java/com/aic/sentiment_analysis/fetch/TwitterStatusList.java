package com.aic.sentiment_analysis.fetch;

import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a list of twitter statuses.
 *
 * @see TwitterStatus
 */
/**
 * @author Florian Taus
 *
 */
/**
 * @author Florian Taus
 */
public class TwitterStatusList extends ArrayList<TwitterStatus> implements Comparable<TwitterStatusList> {
	private static final long serialVersionUID = -445662097030771988L;
	private String keyword;
	private Date start;
	private Date end;

	private TwitterStatusList() {
		super();
	}

	/**
	 * Creates a new status list for the given keyword
	 * 
	 * @param keyword
	 *            The keyword
	 */
	public TwitterStatusList(String keyword) {
		this();
		this.keyword = keyword;
	}

	/**
	 * Returns the start date.
	 * 
	 * @return The start date
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * Returns the start ID.
	 * 
	 * @return The start ID
	 */
	public long getStartId() {
		return size() == 0 ? 0 : this.get(0).getId();
	}

	/**
	 * Returns the end ID.
	 * 
	 * @return The end ID
	 */
	public long getEndId() {
		return size() == 0 ? Long.MAX_VALUE : this.get(this.size() - 1).getId();
	}

	/**
	 * Returns the end date.
	 * 
	 * @return The end date
	 */
	public Date getEnd() {
		return end; // to get last
	}

	/**
	 * Returns the keyword.
	 * 
	 * @return The keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * Trims the list to the given timerange.
	 * 
	 * @param start2
	 *            The start of the timerange
	 * @param end2
	 *            The end of the timrenaged
	 * @return The trimmed status list
	 */
	public TwitterStatusList trim(Date start2, Date end2) {

		if (start2.getTime() <= start.getTime() && end.getTime() <= end2.getTime()) {
			return this;
		}

		TwitterStatusList result = new TwitterStatusList(this.keyword);

		for (TwitterStatus ts: this) {
			if (start2.getTime() <= ts.getTimestamp() && ts.getTimestamp() <= end2.getTime()) {
				result.add(ts);
			}
		}

		if (result.size() == 0) {
			return result;
		}

		Date newStart = result.get(0).getDate();
		Date newEnd = result.get(result.size() - 1).getDate();
		if (start.getTime() <= start2.getTime()) {
			newStart = start2;
		}
		if (end2.getTime() <= end.getTime()) {
			newEnd = end2;
		}
		result.setDates(newStart, newEnd);
		return result;
	}

	/**
	 * Override add and update start/end date if necessary.
	 * 
	 * @param status
	 *            TwitterStatus to add
	 * @return True, if adding was successful
	 */
	@Override
	public boolean add(TwitterStatus status) {

		if (start == null) {
			start = status.getDate();
		}

		if (end == null) {
			end = status.getDate();
		}

		if (start.getTime() >= status.getDate().getTime()) {
			start = status.getDate();
		}

		if (end.getTime() <= status.getDate().getTime()) {
			end = status.getDate();
		}

		return super.add(status);
	}

	/**
	 * Compares the start and end dates of the twitter lists.
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(TwitterStatusList other) {
		int c = getStart().compareTo(other.getStart());
		if (c == 0) {
			c = getEnd().compareTo(getEnd());
		}
		return c;
	}

	/**
	 * Sets the start and end date.
	 * 
	 * @param start
	 *            The new start date
	 * @param end
	 *            The new end date
	 */
	public void setDates(Date start, Date end) {
		this.start = start;
		this.end = end;
	}

	/**
	 * @see java.util.AbstractList#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	/**
	 * @see java.util.AbstractList#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		System.out.println(this.toString() + "=" + obj.toString());
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		TwitterStatusList other = (TwitterStatusList) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (keyword == null) {
			if (other.keyword != null)
				return false;
		} else if (!keyword.equals(other.keyword))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (size() != other.size())
			return false;
		for (int i = 0; i < size(); i++) {
			if (!get(i).equals(other.get(i)))
				return false;
		}
		return true;
	}
}
