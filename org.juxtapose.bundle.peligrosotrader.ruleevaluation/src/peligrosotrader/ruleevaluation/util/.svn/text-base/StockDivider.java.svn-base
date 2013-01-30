package peligrosotrader.ruleevaluation.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.feeds.YahooHistory;
import peligrosotrader.util.ta.EquityKey;

public class StockDivider {

	public static void main(String args[]){
		
		Calendar startCal = Calendar.getInstance();
		startCal.set(Calendar.YEAR, 2000);
		startCal.set(Calendar.MONTH, Calendar.JANUARY);
		startCal.set(Calendar.DAY_OF_MONTH, 1);
		
		Calendar endCal = Calendar.getInstance();
		endCal.set(Calendar.YEAR, 2006);
		endCal.set(Calendar.MONTH, Calendar.JULY);
		endCal.set(Calendar.DAY_OF_MONTH, 1);

		ArrayList<String> omxTickers = OmxFeed.getOmxTickers();
		
		
		for(String ticker : omxTickers){
			try{
//				System.out.println("fetching data for stock "+ticker );
				Vector<Sample> graph = YahooHistory.getHistoryGraph(ticker, startCal, endCal);
				
				checkGraph(graph, ticker);
			}catch(Exception e){/**System.out.println(ticker+" not found");**/}
		}
		System.out.println("Done reading graphs into memory");

	}
	
	public static void checkGraph(Vector<Sample> inGraph, String inTicker){

		EquityKey ek = new EquityKey(inGraph);
		Sample last = inGraph.lastElement();
		if((new Double(ek.getAvg(EquityKey.DAY_VOLUME)).longValue()) > 1000000)
			System.out.println(inTicker);
//		if(ek.getAvg(EquityKey.DAY_VOLUME);
	}
}
