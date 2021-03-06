package peligrosotrader.util.marketfeeds;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.feeds.YahooFeed;
import peligrosotrader.util.feeds.YahooHistory;

public class SAXFeed implements IMarketFeed{
	
	static String ID = "SAX 2000-01-01 - 2006-07-01";
	
	ArrayList<String> m_tickers = null;
	int m_index = 0;
	
	Calendar m_startCal, m_endCal;
	
	
	public SAXFeed()
	{
		m_tickers = getSAXTickers();
		
	}
	
	public void setDates(Calendar inStart, Calendar inEnd)
	{
		m_startCal = inStart;
		m_endCal = inEnd;
	}
	
	public Vector<Sample> getNextGraph(boolean inAddToday)
	{	
		if(m_index > m_tickers.size()-1)
			return null;
		
		String ticker = m_tickers.get(m_index);
		
//		if(ticker.equals("HIQ.ST"))
//			System.out.println("fing");
		
		try
		{
			System.out.println("fetching data for stock "+ticker );
			Vector<Sample> graph = YahooHistory.getHistoryGraph(ticker, m_startCal, m_endCal);
			if(inAddToday)
			{
				Sample today = YahooFeed.getTodaySample(ticker, false);
				if(today != null)
					graph.add(today);
			}
			m_index++;
			return graph;
		}catch(Exception e)
		{
			m_index++;
			System.out.println(ticker+" not found");
			return new Vector<Sample>();
		}
		
	}

	public Vector<Vector<Sample>> getSampleMatrix() 
	{
		Vector<Vector<Sample>> ret = new Vector<Vector<Sample>>();
		ArrayList<String> tickers = getSAXTickers();
		
		
		
		for(String ticker : tickers){
			try{
				System.out.println("fetching data for stock "+ticker );
				Vector<Sample> graph = YahooHistory.getHistoryGraph(ticker, m_startCal, m_endCal);
				ret.add(graph);
			}catch(Exception e){System.out.println(ticker+" not found");}
		}
		System.out.println("Done reading graphs into memory");
		return ret;
	}
	
	public ArrayList<String> getSAXTickers(){
		ArrayList<String> ret = new ArrayList<String>();
		try{
			BufferedReader buff = new BufferedReader(new FileReader("D:/peligrosotrader/SAX.txt"));
			String line = buff.readLine();
			int i = 0;		
			while(line != null){
				System.out.println(line);
				ret.add(line);
				line = buff.readLine();
				i++;
			}
			System.out.println(i+": st aktier");
		} catch (IOException e){e.printStackTrace();}
		return ret;
	}
	
	public String toString(){
		return ID;
	}

}
