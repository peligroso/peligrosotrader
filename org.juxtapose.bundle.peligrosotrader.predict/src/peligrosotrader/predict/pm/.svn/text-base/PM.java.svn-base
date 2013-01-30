package peligrosotrader.predict.pm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import peligrosotrader.predict.db.PMPersistenceHandler;
import peligrosotrader.predict.rules.IRule;
import peligrosotrader.util.db.Sample;
import peligrosotrader.util.statistics.PredictionStats;
import peligrosotrader.util.statistics.Result;

/**
 * 
 * @author Peligroso
 *Prediction module, containing a signal giving rule and statistics
 */
public class PM{
	
	IRule m_rule;
	
//	int m_startHoldDay;
//	int m_holdDayIncrease;
//	int m_endHoldDay;
	
	ArrayList<PredictionStats> m_stats;
	
	int m_signal = 0;

	public PM(){
		
	}
	
	public void clear()
	{
		m_stats = new ArrayList<PredictionStats>();
		
		for(int i = m_rule.getStartHoldDay(); i <= m_rule.getEndHoldDay(); i += m_rule.getHoldDayIncrease()){
			PredictionStats ps = new PredictionStats(i);
			m_stats.add(ps);
		}
		
	}
	
	/**
	 * 
	 * @param inGraph
	 */
	public int evaluate(Vector<Sample> inGraph)
	{
		Sample samp = inGraph.lastElement();
		m_signal = m_rule.signal(inGraph);
		
		if(m_signal != 0)
			System.out.println("signal at for ticker "+samp.ticker+" at: "+samp.last+"  on "+samp.date+" on rule "+m_rule);

		return m_signal;
		
	}
	
	/**
	 * 
	 * @param inSamp
	 * @param inConnections
	 */
	public void log(Sample inSamp, Set<Integer> inConnections)
	{
		for(PredictionStats ps : m_stats)
		{
			ps.log(inSamp, m_signal, inConnections);
			if(ps.getResults().size() > 500)
			{
				long startMilli = System.currentTimeMillis();
				System.out.println("startSaving results for rule: "+m_rule.getID()+" for holdingdays "+ps.m_holdingDays);
				saveResults(ps.getResults(), ps.m_holdingDays);
				ps.clearResults();
				long endMilli = System.currentTimeMillis();
				System.out.println("done in "+(endMilli - startMilli));
			}
		}
	}
	
	public int getRuleID(){
		return m_rule.getID();
	}
	/**
	 * 
	 *
	 */
	public void resetGraph(){
		for(PredictionStats ps : m_stats){
			ps.reset();
		}
		m_rule.resetGraph();
	}
	
	public void finish()
	{
		for(PredictionStats ps : m_stats)
		{
			saveResults(ps.getResults(), ps.m_holdingDays);
			ps.clearResults();
		}
	}
	
	public void printResult(){
		for(PredictionStats ps : m_stats)
			ps.getStats(new HashSet<Integer>()).printResult();

	}
	
	/**
	 * 
	 * @param inRule
	 */
	public void setRule(IRule inRule){
		m_rule = inRule;
	}
	
	public IRule getRule(){
		return m_rule;
	}
	
	public ArrayList<PredictionStats> getStats(){
		return m_stats;
	}

	public void saveResults(ArrayList<Result> inResults, int inPSDays)
	{
		PMPersistenceHandler.saveResults(1, m_rule.getID(), inPSDays, inResults);
	}
	
	public void loadResults(PredictionStats inPS)
	{
		inPS.setResults(PMPersistenceHandler.loadResults(1, m_rule.getID(), inPS.m_holdingDays));
	}
	
	/**
	 * initiate from DB ready to use
	 *
	 */
	public void init()
	{
		for(PredictionStats ps : m_stats)
		{
			loadResults(ps);
		}
	}
	
	/**
	 * free results to save memory temporary
	 *
	 */
	public void unInit()
	{
		for(PredictionStats ps : m_stats)
		{
			ps.clearResults();
		}	
		
	}
}
