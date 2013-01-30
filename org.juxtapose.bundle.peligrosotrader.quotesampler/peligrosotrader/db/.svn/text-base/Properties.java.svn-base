package peligrosotrader.db;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Properties {

	public static HashMap<String, String> PROPERTIES;
	public static ArrayList<String> TICKERS;
	public static ArrayList<String> TICKER_DESCRIPTION;
	
	public static synchronized void addProp(String inName, String inValue){
		PROPERTIES.put(inName, inValue);
	}
	
	public static synchronized void addTicker(String ticker, String tickerDescription){
		TICKERS.add(ticker);
		TICKER_DESCRIPTION.add(ticker);
	}
	
	public static synchronized void reset(){
		TICKER_DESCRIPTION = new ArrayList<String>();
		TICKERS = new ArrayList<String>();
		PROPERTIES = new HashMap<String, String>();
	}
}
