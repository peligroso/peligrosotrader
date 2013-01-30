package peligrosotrader.db;

import java.sql.Date;
import java.sql.Time;
import java.util.StringTokenizer;
import java.util.Vector;

public class Sample {

	public String bid;
	public String ask;
	public String last;
	public String corp;
	public String ticker;
	public Date date;
	public Time time;
	public int order;
	
	public Sample(String inString, int inOrder, String inTicker){
		StringTokenizer st = new StringTokenizer(inString, ",");
		corp = st.nextToken();
		last = st.nextToken();
		ask = st.nextToken();
		bid = st.nextToken();
		order = inOrder;
		ticker = inTicker;
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
	}
	
	public static double[] extractValues(Vector<Sample> inVec){
		double[] ret = new double[inVec.size()];
		for(int i = 0; i < inVec.size(); i++){
			ret[i] = new Double(inVec.get(i).last);
		}
		return ret;
	}
}
