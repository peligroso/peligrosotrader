package peligrosotrader.util.statistics;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import peligrosotrader.util.TradeUtil;
import peligrosotrader.util.db.Sample;

public class PredictionStats {
	

	
	public static int DIST_COUNT = 20;

	
	public int m_holdingDays = 0;
	public int m_hold = 0;
	public double m_holdingAt = 0;
	
	Set<Integer> m_holdingConns;
	
	
	public boolean trendedBias = true;
	
	public boolean m_positionized = false; 
	

	public ArrayList<Result> m_results = new ArrayList<Result>();
	
	/**
	 * 
	 * @param inHoldingDays
	 */
	public PredictionStats(int inHoldingDays)
	{
		m_holdingDays = inHoldingDays;
	}

	public void reset(){
		m_positionized = false;
		m_hold = 0;
	}
	

	
	/**
	 * 
	 * @param inSample
	 * @param inSignal
	 */
	public void log(Sample inSample, int inSignal, Set<Integer> inConns)
	{
		if(m_positionized)
		{
			if(++m_hold >= m_holdingDays){
				double diff = inSample.close/m_holdingAt;
				double min = diff-1;
				double res = min * 100;
				addRes(res, m_holdingConns);
				m_hold = 0;
				m_positionized = false;
				m_holdingConns = null;
				
				//System.out.println("leave trade at for ticker "+inSample.ticker+" at: "+inSample.last+"  on "+inSample.date+" result "+res);
			}
		}
		else
		{
			if(inSignal != 0){
				m_holdingAt = inSample.close;
				m_holdingConns = inConns;
				m_positionized = true;
			}
		}
	}
	
	/**
	 * 
	 * @param res
	 * @param inHoldingDays
	 */
	public void addRes(double res, Set<Integer> inConns)
	{
		
		m_results.add(new Result(res, inConns));
		
	}
	

	
	public Stats getStats(Set<Integer> inConnections){
		Stats myStats = new Stats();
		
		myStats.m_holdingDays = m_holdingDays;
		
		ArrayList<Double> normSamples = new ArrayList<Double>();
		
//		System.out.println("going into big loop for");
		
		for(Result result : m_results){
			
			if(result.getConnections().containsAll(inConnections))
			{

				double res = result.getValue();

				if(res > 0){
					myStats.positives++;
					myStats.totPos += res;
					if(myStats.totPos != 0 && myStats.positives != 0)
						myStats.avgPercentPos = myStats.totPos/myStats.positives; 
				}
				else{
					myStats.negatives++;
					myStats.totNeg += res;
					if(myStats.totPos != 0 && myStats.positives != 0)
						myStats.avgPercentNeg = myStats.totNeg/myStats.negatives; 
				}

				myStats.totAll += res;

				if(myStats.totAll != 0 && (myStats.positives+myStats.negatives) != 0)
					myStats.avgPercent = myStats.totAll/(myStats.positives+myStats.negatives);

				if(myStats.best == 1000000)
					myStats.best = res;
				if(myStats.worst == 1000000)
					myStats.worst = res;

				if(res > myStats.best)
					myStats.best = res;
				if(res < myStats.worst)
					myStats.worst = res;

				normSamples.add(res);

				
			}
			
		}
		myStats.initiateDist(normSamples);
		return myStats;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<Result> getResults()
	{
		return m_results;
	}
	
	/**
	 * 
	 * @param inResults
	 */
	public void setResults(ArrayList<Result> inResults)
	{
		m_results = inResults;
	}
	
	/**
	 * 
	 */
	public void clearResults()
	{
		m_results.clear();
	}
}
