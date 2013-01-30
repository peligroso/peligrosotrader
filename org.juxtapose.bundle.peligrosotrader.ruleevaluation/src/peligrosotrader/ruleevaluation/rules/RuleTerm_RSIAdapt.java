package peligrosotrader.ruleevaluation.rules;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import peligrosotrader.ruleevaluation.mining.MiningRule;
import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.RSI;

/**
 * 
 * @author Peligroso
 *
 */
public class RuleTerm_RSIAdapt extends MiningRule
{
	
	public static double[] LEVELS = new double[]{40};
	public static int[] RSI_DAYS = new int[]{5, 15, 40};
	
	public static int PERIOD = 5;
	
	public static int MIN_TEST_INSTANCES = 4;
	public static double MIN_KOFF = 0.6d;
	
	/**
	 * 
	 * @author Peligroso
	 *
	 */
	class LevelPrediction
	{
		double m_level;
		
		boolean m_positionized = false;
		double m_position;
		int m_holdingCount = 0;
		
		int m_pos = 0;
		int m_negs = 0;
		
		LinkedList<Boolean> m_results = new LinkedList<Boolean>();
		
		Boolean isBelow = null;
		/**
		 * 
		 * @param inLevel
		 */
		LevelPrediction(double inLevel)
		{
			m_level = inLevel;
			
		}
		/**
		 * 
		 * @return
		 */
		public double getLevel(){
			return m_level;
		}
		/**
		 * 
		 * @param inSample
		 * @return
		 */
		public boolean checkInstance(double inRsi, Sample inSample)
		{		
			if(m_positionized){
				
				if(m_holdingCount-- == 0){
					double result = 1 - (m_position / inSample.close);
					if(result < 0){
						m_negs++;
						m_results.add(new Boolean(false));
					}else{
						m_pos++;
						m_results.add(new Boolean(true));
					}
					m_positionized = false;
					
					if(m_results.size() > MIN_TEST_INSTANCES)
						m_results.removeFirst();
				}
				
			}
			else{
				if(isBelow == null)
				{
					isBelow = inRsi < m_level;
					return false;
				}

				if(isBelow == false && inRsi < m_level)
				{
					isBelow = true;
					m_positionized = true;
					m_position = inSample.close;
					m_holdingCount = PERIOD;
					
					int resPos = 0;
					int resNegs = 0;
					
					for(Boolean res : m_results){
						if(res)
							resPos++;
						else
							resNegs++;
					}
					
					m_pos = resPos;
					m_negs = resNegs;
					
					double koff = 0;
					if(m_pos != 0){
						double diff = m_pos - m_negs;
						koff = diff / (double)m_pos;
					}
					if((m_pos != 0) && koff > MIN_KOFF){
						if((m_pos+m_negs) >= MIN_TEST_INSTANCES){
							System.out.print("position found for level "+m_level);
							return true;
						}
					}
				}
			}
			isBelow = inRsi < m_level;
			
			return false;						
		}
	}
	/**
	 * 
	 * @author Peligroso
	 *
	 */
	class RSIPrediction{
		
		int m_days = 0;
		RSI m_rsi;
		
		List<LevelPrediction> m_levelPreds = new ArrayList<LevelPrediction>();
		/**
		 * 
		 * @param inGraph
		 * @param inDays
		 */
		RSIPrediction(Vector<Sample> inGraph, int inDays)
		{
			m_days = inDays;
			
			for(double level : LEVELS){
				m_levelPreds.add(new LevelPrediction(level));
			}
			
			m_rsi = new RSI(new Vector<Sample>(), inDays);
			
			for(Sample samp : inGraph)
			{
				m_rsi.addToRSIGraph(samp);
				if(m_rsi.getRSI() != -1){
					for(LevelPrediction level: m_levelPreds){

						level.checkInstance(m_rsi.getRSI(), samp);
					}
				}
			}
		}
		/**
		 * 
		 * @param inSamp
		 */
		public boolean checkInstance(Sample inSamp)
		{
			boolean ret = false;
			for(LevelPrediction level: m_levelPreds){
				m_rsi.addToRSIGraph(inSamp);
				if(m_rsi.getRSI() != -1){
					if(level.checkInstance(m_rsi.getRSI(), inSamp)){
						System.out.println(" on RSI "+m_days+" rsi is: "+m_rsi.getRSI());
						ret = true;
					}
				}
			}
			return ret;
		}
	}
	
	List<RSIPrediction> m_rsiPreds;
	
	int m_holdingCount = 0;
	
	boolean m_first = true;

	/**
	 * 
	 */
	public void finish() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 
	 */
	public double leavePos(Vector<Sample> inGraph) 
	{
		Sample lastSamp = inGraph.lastElement();
		for(RSIPrediction rsiPred : m_rsiPreds)
			rsiPred.checkInstance(lastSamp);
				
		if(m_holdingCount++ == PERIOD){
			m_holdingCount = 0;
			return inGraph.lastElement().close;
		}
		
		return -1;
	}
	/**
	 * 
	 */
	public void resetGraph() 
	{
		m_first = true;
		m_holdingCount = 0;

	}
	/**
	 * 
	 */
	public double takePos(Vector<Sample> inGraph) 
	{
		if(m_first)
		{
			m_rsiPreds = new ArrayList<RSIPrediction>();
			
			for(int day : RSI_DAYS)
			{
				m_rsiPreds.add(new RSIPrediction(inGraph, day));
			}
			m_first = false;
		}
		else
		{
			double signal = -1;
			
			Sample lastSamp = inGraph.lastElement();
			for(RSIPrediction rsiPred : m_rsiPreds){
				if(rsiPred.checkInstance(lastSamp))
					signal = lastSamp.close;
			}
			return signal;
		}
		
		return -1;
	}
	
	public boolean changeFirstParam(){
		MIN_TEST_INSTANCES += 2;
		if(MIN_TEST_INSTANCES >= 20){
			MIN_TEST_INSTANCES = 2;
			return false;
		}
		return true;
	}
	
	public boolean changeSecParam(){
		MIN_KOFF += 0.2;
		if(MIN_KOFF > 1){
			MIN_KOFF = 0.2;
			return false;
		}
		return true;
	}
	
	public double getFirstParam(){
		return MIN_TEST_INSTANCES;
	}
	public double getSecParam(){
		return MIN_KOFF;
	}
}