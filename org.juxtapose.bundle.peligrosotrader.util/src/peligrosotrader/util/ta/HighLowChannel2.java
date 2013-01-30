package peligrosotrader.util.ta;

import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class HighLowChannel2 extends TA {

	Vector<Sample> m_highVec = new Vector<Sample>();
	Vector<Sample> m_lowVec = new Vector<Sample>();
	
	public HighLowChannel2(Vector<Sample> inGraph)
	{
		
		
		for(Sample samp : inGraph)
		{
			Sample highSamp = samp.getCopy();
			highSamp.setPriceValues(samp.highest);
			
			m_highVec.add(highSamp);
			
			Sample lowSamp = samp.getCopy();
			lowSamp.setPriceValues(samp.lowest);			
			
			m_lowVec.add(lowSamp);
		}
	}
	
	public void addToGraph(Sample inSamp)
	{
		Sample highSamp = inSamp.getCopy();
		highSamp.setPriceValues(inSamp.highest);
		
		m_highVec.add(highSamp);
		
		Sample lowSamp = inSamp.getCopy();
		lowSamp.setPriceValues(inSamp.lowest);			
		
		m_lowVec.add(lowSamp);
	}
	
	public Vector<Sample> getHighBand(){
		return m_highVec;
	}
	
	public Vector<Sample> getLowBand(){
		return m_lowVec;
	}
}
