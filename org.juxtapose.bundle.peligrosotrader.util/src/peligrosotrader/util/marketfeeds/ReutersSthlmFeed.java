package peligrosotrader.util.marketfeeds;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import peligrosotrader.util.Constants;
import peligrosotrader.util.db.Sample;
import peligrosotrader.util.feeds.ReutersSthlm;
import peligrosotrader.util.feeds.YahooFeed;
import peligrosotrader.util.feeds.YahooHistory;

public class ReutersSthlmFeed implements IMarketFeed {

	Calendar m_startCal, m_endCal;
	
	int m_index = 0;
	
	ArrayList<String> m_tickers;
	
	public ReutersSthlmFeed()
	{
		m_tickers = getReutersTickers();
	}
	
	public Vector<Sample> getNextGraph(boolean inAddToday) 
	{
		if(m_index > m_tickers.size()-1)
			return null;
		
		String ticker = m_tickers.get(m_index);
		
		try
		{
			System.out.println("fetching data for stock "+ticker );
			Vector<Sample> graph = ReutersSthlm.parseFile(ticker, m_startCal, m_endCal);
			
			m_index++;
			return graph;
		}
		catch(Exception e)
		{
			m_index++;
			System.out.println(ticker+" not found");
			return new Vector<Sample>();
		}
	}
	

	/* (non-Javadoc)
	 * @see peligrosotrader.util.marketfeeds.IMarketFeed#getSampleMatrix()
	 */
	public Vector<Vector<Sample>> getSampleMatrix() {

		Vector<Vector<Sample>> ret = new Vector<Vector<Sample>>();
		ArrayList<String> tickers = getReutersTickers();
		
		
		for(String ticker : tickers){
			try{
				System.out.println("fetching data for stock "+ticker );
				Vector<Sample> graph = ReutersSthlm.parseFile(ticker, m_startCal, m_endCal);
				ret.add(graph);
			}catch(Exception e){System.out.println(ticker+" not found");}
		}
		System.out.println("Done reading graphs into memory");
		return ret;
	}

	/* (non-Javadoc)
	 * @see peligrosotrader.util.marketfeeds.IMarketFeed#setDates(java.util.Calendar, java.util.Calendar)
	 */
	public void setDates(Calendar inStart, Calendar inEnd) 
	{
		m_startCal = inStart;
		m_endCal = inEnd;
	}
	
	/**
	 * @return
	 */
	public ArrayList<String> getReutersTickers()
	{
		ArrayList<String> tickers = new ArrayList<String>();
		
		File dir = new File(Constants.REUTERS_DIR);
	    
	    String[] children = dir.list();
	    
	    for( String s : children )
	    {
	    	tickers.add( s );
	    }

		return tickers;
	}

}
