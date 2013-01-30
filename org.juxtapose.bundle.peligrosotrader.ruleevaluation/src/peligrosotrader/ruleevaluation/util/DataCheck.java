package peligrosotrader.ruleevaluation.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.feeds.YahooHistory;

public class DataCheck {

	
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
				System.out.println("fetching data for stock "+ticker );
				Vector<Sample> graph = YahooHistory.getHistoryGraph(ticker, startCal, endCal);
				
				checkGraph(graph);
			}catch(Exception e){System.out.println(ticker+" not found");}
		}
		System.out.println("Done reading graphs into memory");

	}
	
	public static void checkGraph(Vector<Sample> inGraph){
		
		Double lastClose = null;
		int sameInRow = 0;
		
		
		for(Sample samp : inGraph)
		{
			if(lastClose == null){
				System.out.println("Checking "+samp.ticker);
				lastClose = samp.close;
				continue;
			}
			else if(samp.close > lastClose * 1.7)
				
					System.out.println(samp.ticker+", big day on "+samp.date);
			
			else if(samp.close < lastClose * 0.2)
				
				System.out.println(samp.ticker+", small day on "+samp.date);
			
			if(samp.close == lastClose){
				if(++sameInRow > 20)
					System.out.println(samp.ticker+", stale period on "+samp.date);
			}else
				sameInRow = 0;
			
			lastClose = samp.close;
		}
	}
}
