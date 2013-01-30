package peligrosotrader.util.db;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.StringTokenizer;

public class Sample {
	
	public enum Value
	{
		eHighest,
		eLowest,
		eClose;
	}
	
	public static Value HIGHEST = Value.eHighest;
	public static Value LOWEST = Value.eLowest;
	public static Value CLOSE = Value.eClose;

	public String bid;
	public String ask;
	public String last;
	public String corp;
	public String ticker;
	public Date date;
	public Time time;
	public int order;
	
	public double highest;
	public double lowest;
	public double open;
	public double close;
	public double volume;
	
	public Sample(){
		
		ticker = "";
		last = "0";
		ask = "0";
		bid = "0";
		order = 0;
		date = null;
		time = null;
		
		highest = 0;
		lowest = 0;
		open = 0;
		close = 0;
		volume = 0;
	}
	
	public Sample(Date inDate, Time inTime){
		
		ticker = "";
		last = "0";
		ask = "0";
		bid = "0";
		order = 0;
		date = inDate;
		time = inTime;
		
		highest = 0;
		lowest = 0;
		open = 0;
		close = 0;
		volume = 0;
	}
	
	public Sample(String inString, int inOrder, String inTicker){
		StringTokenizer st = new StringTokenizer(inString, ",");
		corp = st.nextToken();
		last = st.nextToken();
		ask = st.nextToken();
		bid = st.nextToken();
		highest = Double.parseDouble(st.nextToken());
		lowest = Double.parseDouble(st.nextToken());
		open = Double.parseDouble(st.nextToken());
		order = inOrder;
		ticker = inTicker;
		
		close = Double.parseDouble(last);
	}
	
	public Sample(String inTicker, String inLast, String inAsk, 
			String inBid, int inOrder, Date inDate, Time inTime){
		
		ticker = inTicker;
		last = inLast;
		ask = inAsk;
		bid = inBid;
		order = inOrder;
		date = inDate;
		time = inTime;
		
		close = Double.parseDouble(last);
	}
	
	/**
	 * 
	 * @param inVec
	 * @return
	 */
	public static double[] extractValues(List<Sample> inVec){
		double[] ret = new double[inVec.size()];
		for(int i = 0; i < inVec.size(); i++){
			ret[i] = new Double(inVec.get(i).last);
		}
		return ret;
	}
	
	/**
	 * 
	 * @return
	 */
	public Sample getCopy()
	{
		Sample copy = new Sample(ticker, last, ask, bid, order, date, time);
		copy.close = close;
		copy.highest = highest;
		copy.lowest = lowest;
		copy.open = open;
		copy.corp = corp;
		copy.volume = volume;
		
		return copy;
	}
	
	/**
	 * 
	 * @param inValue
	 */
	public void setPriceValues(double inValue)
	{
		close = inValue;
		highest = inValue;
		lowest = inValue;
		open = inValue;
		
		String strVal = Double.toString(inValue);
		last = strVal;
		ask = strVal;
		bid = strVal;
	}
	
	public double getValue(Value inValue){
		switch(inValue){
		case eHighest : return highest;
		case eLowest : return lowest;
		case eClose : return close;
		}
		return close;
	}
}
