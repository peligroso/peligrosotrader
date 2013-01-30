package peligrosotrader.predict;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import peligrosotrader.predict.db.PMPersistenceHandler;
import peligrosotrader.predict.pm.PM;
import peligrosotrader.predict.pm.PMLoader;
import peligrosotrader.util.TradeUtil;
import peligrosotrader.util.db.Sample;
import peligrosotrader.util.marketfeeds.IMarketFeed;
import peligrosotrader.util.ta.EquityKey;
import peligrosotrader.util.ta.RSI;

/**
 * 
 * @author Peligroso
 *
 *Keys 1-100
 *Rules 101-200
 *Month 1000-1012
 *Years 1900-2010
 *Rule parameter 10000-20000
 *
 */
public class MarketAdapter {

	ArrayList<PM> m_pms = new ArrayList<PM>();
	
	
	public void init()
	{
		m_pms = PMLoader.load();
	}
	/**
	 * 
	 * @param inFeed
	 */
	public void adaptToMarket(IMarketFeed inFeed)
	{
		PMPersistenceHandler.deleteMarket();
		Vector<Sample> graph;
		
		while((graph = inFeed.getNextGraph(false)) != null){
			if(!graph.isEmpty()){
				evaluateGraph(graph);
				for(PM pm : m_pms)
					pm.resetGraph();
			}
		}
		for(PM pm : m_pms)
			pm.finish();
		printStat();	
		
	}
	
	/**
	 * 
	 * @param inGraph
	 */
	private void evaluateGraph(Vector<Sample> inGraph){
		Vector<Sample> tryVec = new Vector<Sample>();
		
		EquityKey eKey = new EquityKey(null);
		RSI momentum = new RSI(tryVec, 14);
	
		for(Sample samp : inGraph){
			tryVec.add(samp);
			
			eKey.addInstance(samp);
			momentum.addToRSIGraph(samp);
			
			Set<Integer> conns = new HashSet<Integer>();
			
			//Add connection id's
			if(tryVec.size() > 10){
				conns.add(KeyIDTracker.getCapID(eKey));
				conns.add(KeyIDTracker.getVolatilityID(eKey));
				if(tryVec.size() > 14)
					conns.add(KeyIDTracker.getMomentumID(momentum));
			}
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(samp.date);
			
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			
			conns.add(year);
			conns.add(month+1000);
			
			for(PM pm : m_pms){
				int signal = pm.evaluate(tryVec);
				if(signal != 0){
					conns.add(pm.getRuleID());
					if(signal != 1)
						conns.add(signal);
					if(pm.getRuleID() == 106)
						System.out.println(signal);
				}
			}
			HashSet<Integer> testSet = new HashSet<Integer>();
			testSet.add(103);
//			if(conns.containsAll(testSet))
//				System.out.println("tyes");
			
			for(PM pm : m_pms)
				pm.log(samp, conns);
		}
	}
	
	public void printStat(){
		for(PM pm : m_pms)
			pm.printResult();
	}
	
	public ArrayList<PM> getPMs(){
		return m_pms;
	}
}
