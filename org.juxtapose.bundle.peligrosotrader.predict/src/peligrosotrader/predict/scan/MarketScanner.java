package peligrosotrader.predict.scan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import peligrosotrader.predict.KeyIDTracker;
import peligrosotrader.predict.MarketAdapter;
import peligrosotrader.predict.pm.PM;
import peligrosotrader.predict.rules.IRule;
import peligrosotrader.util.SystemDateFormat;
import peligrosotrader.util.db.Sample;
import peligrosotrader.util.feeds.YahooFeed;
import peligrosotrader.util.marketfeeds.IMarketFeed;
import peligrosotrader.util.ta.EquityKey;
import peligrosotrader.util.ta.RSI;

public class MarketScanner {

	MarketAdapter m_adapter;
	IMarketFeed m_feed;
	
	ArrayList<ScanResult> m_results = new ArrayList<ScanResult>();
	
	IScanResult m_scanRes;
	
	ArrayList<Integer> m_algos;
	
	static final long TEN_DAYS = 1000 * 60 * 60 * 24 * 10;
	
	public MarketScanner(MarketAdapter inAdapter, IMarketFeed inFeed, IScanResult inScanRes, ArrayList<Integer> inAlgos)
	{
		m_adapter = inAdapter;
		m_feed = inFeed;
		m_scanRes = inScanRes;
		m_algos = inAlgos;
	}
	
	public void scanMarket()
	{
		new Thread(new Runnable(){
			public void run(){
				Vector<Sample> graph;

				Calendar today = Calendar.getInstance();
//				today.setTimeInMillis(System.currentTimeMillis());
				
				boolean addToday = true;
				if(today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
					addToday = false;
					
				try{
					while((graph = m_feed.getNextGraph(addToday)) != null)
					{
						if(!graph.isEmpty())
							scanGraph(graph);
					}
				}catch(Throwable t){
					t.printStackTrace();
				}
				for(ScanResult res : m_results){
					System.out.println(res);
				}
				m_scanRes.setScanResults(m_results);

			}
		}).start();
		//return m_results;

	}
	
	/**
	 * 
	 * @param inGraph
	 */
	private void scanGraph(Vector<Sample> inGraph)
	{
		Set<Integer> conns = new HashSet<Integer>();
		Set<PM> activePMs = new HashSet<PM>();
		Map<String, String> params = new Hashtable<String, String>();
		
		String tickerName = inGraph.firstElement().ticker;
		//Investigate Rules


		for(PM pm : m_adapter.getPMs())
		{
			//TODO cut graph
			Vector<Sample> tryVec = new Vector<Sample>();

			IRule rule = pm.getRule();
			if(m_algos.contains(rule.getID()))
			{
				rule.resetGraph();

				int signal = 0;

				int backTrace = pm.getRule().getScanSteps();

				int startIndex = 0;
				if(backTrace == -1)
					startIndex = 0;			
				else{
					startIndex = inGraph.size() - backTrace;
				}

				if(startIndex > -1){
					if(rule.lazyScan()){
						signal = rule.signal(inGraph);
						if(signal == 1)
							signal = rule.getID();
					}
					else{
						for(int i = startIndex; i < inGraph.size(); i++)
						{
							Sample samp = inGraph.get(i);
//							if(samp == inGraph.lastElement())
//							System.out.println("last");
							tryVec.add(samp);
							int sig = rule.signal(tryVec);


							if(samp == inGraph.lastElement()){
								signal = sig;
								if(signal == 1)
									signal = rule.getID();
							}
						}
					}
				}

				if(signal != 0){
					Map<String, String> info = rule.getSignalInfo();
					if(info != null){
						String newSDStr = info.get("START_DATE");
						String oldSDStr = params.get("START_DATE");
						
						if(newSDStr != null && oldSDStr != null)
						{
							try{
								Date newDate = SystemDateFormat.DATE_FORMAT.parse(newSDStr);
								Date oldDate = SystemDateFormat.DATE_FORMAT.parse(oldSDStr);
								
								if(oldDate.getTime() < newDate.getTime())
									info.remove("START_DATE");
							}catch(Exception e){

							}
						}
							
						params.putAll(info);
					}
					conns.add(signal);
					activePMs.add(pm);
				}
			}
		}

		if(!activePMs.isEmpty())
		{
			//Investigate Keys
			Vector<Sample> tryVec = new Vector<Sample>();

			EquityKey eKey = new EquityKey(null);
			RSI momentum = new RSI(tryVec, 14);

			for(Sample samp : inGraph){
				tryVec.add(samp);

				eKey.addInstance(samp);
				momentum.addToRSIGraph(samp);
			}

			conns.add(KeyIDTracker.getCapID(eKey));
			conns.add(KeyIDTracker.getVolatilityID(eKey));

			if(tryVec.size() > 14)
				conns.add(KeyIDTracker.getMomentumID(momentum));

			//Add date info
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			conns.add(year);
			conns.add(month+1000);
			
			

			//And we have all the info we need to calculate a prediction


			StringBuffer sb = new StringBuffer();
			String corp = inGraph.lastElement().corp;
			if(corp == null)
				corp = getCorp(tickerName);
			if(corp != null)
				corp = corp.replace("\"", "");
			else
				corp = tickerName;
			
			sb.append("Predicting "+corp+" ("+tickerName+") with connections");

			for(Integer i :conns)
				sb.append(Integer.toString(i)+", ");

			if(validateGraph(inGraph, cal.getTime(), activePMs)){
				m_results.add(new ScanResult(conns, corp, tickerName, params));
//				System.out.println(sb.toString());
			}
			else{
				System.out.println(tickerName+" was not valid");
			}
		}
		
	}
	
	public boolean validateGraph(Vector<Sample> inGraph, Date inToday, Set<PM> inActivePMs)
	{
		String lastGraphDate = SystemDateFormat.DATE_FORMAT.format(inGraph.lastElement().date);
		
		Calendar todayCal = Calendar.getInstance();
		todayCal.setTime(inToday);
		
		while( todayCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || todayCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
			todayCal.set(Calendar.DAY_OF_MONTH, todayCal.get(Calendar.DAY_OF_MONTH) - 1);
		
		String lastMarketDateStr = SystemDateFormat.DATE_FORMAT.format(todayCal.getTime());
		if(!lastGraphDate.equals(lastMarketDateStr))
			return false;
		
		for(PM pm : inActivePMs){
			int rid = pm.getRuleID();
			
			if(rid == 108 || rid == 109 || rid == 112 || rid == 113){
				
				
				todayCal.set(Calendar.DAY_OF_MONTH, todayCal.get(Calendar.DAY_OF_MONTH) - 1);
				while( todayCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || todayCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
					todayCal.set(Calendar.DAY_OF_MONTH, todayCal.get(Calendar.DAY_OF_MONTH) - 1);
				
				String dayBeforLastMarketDateStr = SystemDateFormat.DATE_FORMAT.format(todayCal.getTime());
				
				String beforeLastGraphDateStr = SystemDateFormat.DATE_FORMAT.format(inGraph.get(inGraph.size()-2).date);
				
				if(!dayBeforLastMarketDateStr.equals(beforeLastGraphDateStr))
					return false;
			}
		}
		
		//Check graph so that there is no gaps
		Long lastTime = null;
		for( Sample samp : inGraph )
		{
			long time = samp.date.getTime();
			if(lastTime == null){
				lastTime = time;
				continue;
			}
			long diff = time - lastTime; 
			if(diff > TEN_DAYS)
				return false;
			lastTime = time;
		}
		
		return true;
	}
	
	public String getCorp(String inTicker)
	{
		Sample samp = YahooFeed.getTodaySample(inTicker, true);
		if(samp != null)
			return samp.corp;
		return null;
	}

	
}
