package peligrosotrader.util.ta;

import java.sql.Date;
import java.sql.Time;
import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class MultiOscillator extends TA {
	
	static final int[] PERIODS = new int[]{5, 10, 15, 20};

	Vector<RSI> m_rsiVec = new Vector<RSI>();
	Vector<Stochastics> m_stochVec = new Vector<Stochastics>();
	
	Vector<Sample> m_multiOsc = new Vector<Sample>();
	
	/**
	 * 
	 * @param inGraph
	 */
	public MultiOscillator(Vector<Sample> inGraph){
		
		for(int p : PERIODS)
		{
			RSI rsi = new RSI(inGraph, p);
			m_rsiVec.add(rsi);
			Stochastics stoch = new Stochastics(inGraph, p, false);
			m_stochVec.add(stoch);
		}
		
		for(int i = 0; i < inGraph.size(); i++)
		{
			double multiVal = 0;
			
			for(RSI rsi : m_rsiVec)
			{
				Sample samp = rsi.getRSIGraph().get(i);
				multiVal += samp.close;
			}
			for(Stochastics stoch : m_stochVec)
			{
				Sample samp = stoch.getStochGraph().get(i);
				multiVal += samp.close;
			}
			
			multiVal /= (PERIODS.length * 2);
			String strVal = Double.toString(multiVal);
			
			Sample gSamp = inGraph.get(i);
			Sample multiSamp = new Sample(gSamp.ticker, strVal, strVal, strVal, gSamp.order, gSamp.date, gSamp.time);
			m_multiOsc.add(multiSamp);
		}
	}
	
	public void addToGraph(Sample inSamp)
	{
		double multiVal = 0;
		
		for(RSI rsi : m_rsiVec)
		{
			rsi.addToRSIGraph(inSamp);
			Sample samp = rsi.getRSIGraph().lastElement();
			multiVal += samp.close;
		}
		for(Stochastics stoch : m_stochVec)
		{
			stoch.addToStochGraph(inSamp, false);
			Sample samp = stoch.getStochGraph().lastElement();
			multiVal += samp.close;
		}
		
		multiVal /= (PERIODS.length * 2);
		String strVal = Double.toString(multiVal);
		
		Sample multiSamp = new Sample(inSamp.ticker, strVal, strVal, strVal, inSamp.order, inSamp.date, inSamp.time);
		m_multiOsc.add(multiSamp);
	}
	
	public Vector<Sample> getMultiOscGraph()
	{
		return m_multiOsc;
	}
	
	/**
	 * 
	 * @return
	 */
	public Vector<Sample> get20Line(){
		Vector<Sample> line = new Vector<Sample>();
		for(Sample samp : m_multiOsc){
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
		for(Sample samp : m_multiOsc){
			Sample newSamp = new Sample("80", "80", "80", "80", samp.order, samp.date, samp.time);
			newSamp.close = 80;
			line.add(newSamp);
		}
		return line;
	}
}
