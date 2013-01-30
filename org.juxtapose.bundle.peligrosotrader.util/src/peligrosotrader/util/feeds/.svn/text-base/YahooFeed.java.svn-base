package peligrosotrader.util.feeds;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Date;
import java.util.Calendar;

import peligrosotrader.util.db.DBHandler;
import peligrosotrader.util.db.Sample;

public class YahooFeed extends Thread implements Feed{
	
	static int SAMPLE_RATE = 300000;
	
	String[] m_tickers;
	boolean m_running;
	URL m_url;
	int m_sampleCount;
	
	static String YAHOO = "http://finance.yahoo.com/d/quotes.csv?"; 
	
	public YahooFeed(String[] inTickers){
		try{
			m_tickers = inTickers;
			String tickerString = "";
			for (String s : inTickers) {
				tickerString +="+"+s;
			}
			
			m_url = new URL(YAHOO+"s="+tickerString+"&f=nl1abhgo");
			m_sampleCount = 1;
			
		}catch (Exception mex){ mex.printStackTrace();}	
		
	}
	public void start(){
		m_running = true;
		super.start();
	}
	
	public void finish() {
		m_running = false;
	}
	
	public void run(){
		try{
			
			while (m_running) {
				BufferedReader in = new BufferedReader(new InputStreamReader(m_url.openStream()));
		        String str = in.readLine();
		        int tickerIndex = 0;
		        while (str != null) {
		        	Sample smp = new Sample(str, m_sampleCount, m_tickers[tickerIndex]);		        	
		        	System.out.println(smp.corp+"  "+smp.last+"  "+smp.ask+"  "+smp.bid+" "+smp.ticker+" "+smp.order);
		        	DBHandler.saveQuotes(smp);
		        	str = in.readLine();
		        	tickerIndex++;
		        }
		        in.close();
		        m_sampleCount++;
				sleep(SAMPLE_RATE);
			}
		}catch (Exception e) { e.printStackTrace(); }
	}
	
	public static Sample getTodaySample(String inTicker, boolean ignoreHoliday)
	{
		try
		{
			URL url = new URL(YAHOO+"s="+inTicker+"&f=nl1abhgo");
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String str = in.readLine();
			 if (str != null) {
		        	Sample smp = new Sample(str, 1, inTicker);
		        	
		        	Calendar today = Calendar.getInstance();
					int year = today.get(Calendar.YEAR);
					int month = today.get(Calendar.MONTH);
					int day = today.get(Calendar.DAY_OF_MONTH);
					int weekDay = today.get(Calendar.DAY_OF_WEEK);
					
					if(!ignoreHoliday){
						if(weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY)
							return null;
					}
					
					today.clear();
					today.set(year, month, day);
		        	
		        	Date date = new Date(today.getTimeInMillis());
		        	
		        	smp.date = date;
		        	return smp;
			 }
		}
		catch(Exception e){
//			e.printStackTrace();
		}
		return null;
	}
}
