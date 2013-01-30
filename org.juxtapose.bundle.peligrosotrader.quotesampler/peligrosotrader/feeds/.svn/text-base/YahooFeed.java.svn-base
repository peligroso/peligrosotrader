package peligrosotrader.feeds;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Vector;

import peligrosotrader.db.DBHandler;
import peligrosotrader.db.Properties;
import peligrosotrader.db.Sample;

public class YahooFeed extends Thread implements Feed{
		
	String[] m_tickers;
	boolean m_running;
	URL m_url;
	int m_sampleCount;
	int m_sampleRate;
	
	static String YAHOO = "http://finance.yahoo.com/d/quotes.csv?"; 
	
	public YahooFeed(){
		try{
			m_tickers = Properties.TICKERS.toArray(new String[0]);
			m_sampleRate = new Integer(Properties.PROPERTIES.get("sample-rate"));
			String tickerString = "";
			for (String s : m_tickers) {
				tickerString +="+"+s;
			}
			
			m_url = new URL(YAHOO+"s="+tickerString+"&f=nl1ab");
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
			
			DBHandler.openConnection();
			Vector<Sample> samples = new Vector<Sample>();
			while (m_running) {
				
				try {
					
					BufferedReader in = new BufferedReader(new InputStreamReader(m_url.openStream()));
					String str = in.readLine();
					samples = new Vector<Sample>();
					int tickerIndex = 0;
					while (str != null) {
						Sample smp = new Sample(str, m_sampleCount, m_tickers[tickerIndex]);		        	
						System.out.println(smp.corp+"  "+smp.last+"  "+smp.ask+"  "+smp.bid+" "+smp.ticker+" "+smp.order);
						DBHandler.saveQuotes(smp);
						samples.add(smp);
						str = in.readLine();
						tickerIndex++;
					}
					in.close();
					m_sampleCount++;
				} catch (Exception e) {
					e.printStackTrace();
					for(Sample samp : samples)
						DBHandler.saveQuotes(samp);
					m_sampleCount++;
				}
				sleep(m_sampleRate);
				
			}
		}catch (Exception e) { e.printStackTrace(); }
	}
}
