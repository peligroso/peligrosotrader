package peligrosotrader.util.ta;

import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class HighLowChannel extends TA {

	MA m_highBand;
	MA m_lowBand;
	
	public HighLowChannel(Vector<Sample> inGraph, int inDays)
	{
		Vector<Sample> highVec = new Vector<Sample>();
		Vector<Sample> lowVec = new Vector<Sample>();
		
		for(Sample samp : inGraph)
		{
			Sample highSamp = samp.getCopy();
			highSamp.close = highSamp.highest;
			highSamp.last = Double.toString(highSamp.highest);
			highVec.add(highSamp);
			
			Sample lowSamp = samp.getCopy();
			lowSamp.close = lowSamp.lowest;
			lowSamp.last = Double.toString(lowSamp.lowest);
			lowVec.add(lowSamp);
		}
		
		m_highBand = new MA(highVec, inDays);
		m_lowBand = new MA(lowVec, inDays);
	}
	
	public void addToGraph(Sample inSamp)
	{
		Sample highSamp = inSamp.getCopy();
		highSamp.close = highSamp.highest;
		highSamp.last = Double.toString(highSamp.highest);
		m_highBand.addToGraph(highSamp);
		
		Sample lowSamp = inSamp.getCopy();
		lowSamp.close = lowSamp.lowest;
		lowSamp.last = Double.toString(lowSamp.lowest);
		m_lowBand.addToGraph(lowSamp);
	}
	
	public Vector<Sample> getHighBand(){
		return m_highBand.getMA();
	}
	
	public Vector<Sample> getLowBand(){
		return m_lowBand.getMA();
	}
}
