package peligrosotrader.util.ta;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

import peligrosotrader.util.db.Sample;

/**
 * 
 * @author Peligroso
 *
 */
public class Stochastics extends TA{

//	double numbHoldDays = 0;
//	double avgHoldDays = 0;
//	double numbTrades = 0;
	
	int m_period = 14;
	
	LinkedList<Sample> m_workingStoch = new LinkedList<Sample>();
	Vector<Sample> m_stochGraph = new Vector<Sample>();
	
	MA m_slowStoch;
	boolean m_useSlow;
	
	double m_startValue = -1;
	
	/**
	 * 
	 * @param inGraph
	 * @param inPeriod
	 */
	public Stochastics(Vector<Sample> inGraph, int inPeriod, boolean inSlow){
		
		m_period = inPeriod;
		m_useSlow = inSlow;
		
		for(Sample samp : inGraph)
		{	
			addToStochGraph(samp, false);
		}
		
		if(m_useSlow)
		{
			m_slowStoch = new MA(m_stochGraph, 3);
		}
	}
	
	/**
	 * 
	 * @param inGraph
	 * @return
	 * @throws TAException
	 */
	public double calcStochastics(LinkedList<Sample> inGraph) throws TAException{
		
		if(inGraph.size() !=  m_period)
			throw new TAException("Stochastics graph does not match period");
		
		double lowest = - 1;
		double highest = - 1;
		double lastClose = inGraph.get(inGraph.size()-1).close;
		
		for(Sample samp : inGraph){
			if(lowest == -1 || samp.lowest < lowest)
				lowest = samp.lowest;
			if(highest == -1 || samp.highest > highest)
				highest = samp.highest;
		}
		
		double stoch = 100 * ((lastClose - lowest) / (highest - lowest));
		
		return stoch;
		
	}
	
	/**
	 * 
	 * @param inSamp
	 */
	public void addToStochGraph(Sample inSamp, boolean inSlow)
	{
		if(m_workingStoch.size() >= m_period)
		{
			m_workingStoch.remove(0);
			m_workingStoch.add(inSamp);
			
			try
			{
				double stoch = calcStochastics(m_workingStoch);
				Sample stochSamp = inSamp.getCopy();					
				stochSamp.setPriceValues(stoch);
				m_stochGraph.add(stochSamp);
				
			}
			catch(TAException e)
			{
				e.printStackTrace();
			}
		}
		else{
			m_workingStoch.add(inSamp);
			m_stochGraph.add(new Sample(inSamp.date, inSamp.time));
		}
		
		if(inSlow)
			m_slowStoch.addToGraph(inSamp);
	}
	
	/**
	 * 
	 * @return
	 */
	public Vector<Sample> getStochGraph(){
		return m_stochGraph;
	}
	
	public Vector<Sample> getSlowStochGraph(){
		return m_slowStoch.getMA();
	}
	
	/**
	 * 
	 * @return
	 */
	public double getStochastic(){
		Sample last = m_stochGraph.lastElement();
		return last.close;
	}
	
	/**
	 * 
	 * @return
	 */
	public Vector<Sample> get20Line(){
		Vector<Sample> line = new Vector<Sample>();
		for(Sample samp : m_stochGraph){
			Sample newSamp = new Sample("20", "20", "20", "20", samp.order, samp.date, samp.time);
			newSamp.close = 20;
			line.add(newSamp);
		}
		return line;
	}
	/**
	 * 
	 * @return
	 */
	public Vector<Sample> get80Line(){
		Vector<Sample> line = new Vector<Sample>();
		for(Sample samp : m_stochGraph){
			Sample newSamp = new Sample("80", "80", "80", "80", samp.order, samp.date, samp.time);
			newSamp.close = 80;
			line.add(newSamp);
		}
		return line;
	}
}

