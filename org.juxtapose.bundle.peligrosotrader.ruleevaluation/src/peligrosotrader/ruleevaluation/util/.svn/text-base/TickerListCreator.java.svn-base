package peligrosotrader.ruleevaluation.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import peligrosotrader.util.Constants;
import peligrosotrader.util.db.Sample;
import peligrosotrader.util.feeds.YahooFeed;
import peligrosotrader.util.feeds.YahooHistory;

public class TickerListCreator {

	static Map<String, String> m_map = new TreeMap<String, String>();
	
	public static void main(String args[]){
		
//		Calendar startCal = Calendar.getInstance();
//		startCal.set(Calendar.YEAR, 2008);
//		startCal.set(Calendar.MONTH, Calendar.JANUARY);
//		startCal.set(Calendar.DAY_OF_MONTH, 1);
//		
//		Calendar endCal = Calendar.getInstance();
//		endCal.set(Calendar.YEAR, 2008);
//		endCal.set(Calendar.MONTH, Calendar.JANUARY);
//		endCal.set(Calendar.DAY_OF_MONTH, 3);
//
//		ArrayList<String> omxTickers = OmxFeed.getOmxTickers();
//		
//		
//		for(String ticker : omxTickers){
//			try{
//				Sample graph = YahooFeed.getTodaySample(ticker, true);
//				
//				checkGraph(graph);
//			}catch(Exception e){
//				e.printStackTrace();
//				System.out.println(ticker);
//			}
//		}
//		
//		printIt();
		
		doItFromFile();
	}
	
	public static void checkGraph(Sample inSample){
		
		String corp = inSample.corp;
		String ticker = inSample.ticker;
		
		corp = corp.replaceAll("\"", "");
		
		m_map.put(corp, ticker);
		
	}
	
	public static void printIt()
	{
		for(String corp : m_map.keySet())
		{
			String ticker = m_map.get(corp);
			
			
//			{value:"102", text:"Cairo"},
			
		}
	}
	
	public static void doItFromFile(){
		
		try{
			BufferedReader in = new BufferedReader(new FileReader(Constants.WEB_CONTENT+"indexes.jsp"));
			
			String line;
			int i = 0;
			while( (line = in.readLine()) != null){
				
				String[] split = line.split("[<>\"]");
				String ticker = split[2].trim();
				String name = split[4].trim();
				System.out.println("{value:\""+ticker+"\", text:\""+name+"\"},");
				i++;
			}
		}catch(Exception fe){
			fe.printStackTrace();
		}
	}
}
