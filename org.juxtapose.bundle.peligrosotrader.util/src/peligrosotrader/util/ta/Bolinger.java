package peligrosotrader.util.ta;

import java.util.LinkedList;
import java.util.Vector;

import peligrosotrader.util.db.Sample;

/**
 * 
 * @author Peligroso
 *
 */
public class Bolinger extends TA {

	Vector<Sample> m_graph;
	Vector<Sample> m_MA = new Vector<Sample>();
	
	LinkedList<Double> m_avgMASample = new LinkedList<Double>();
	
	Vector<Sample> m_highBand = new Vector<Sample>();
	Vector<Sample> m_lowBand = new Vector<Sample>();
	
	//Vector<Double> m_deviation = new Vector<Double>();
	
	LinkedList<Double> m_avgDeviation = new LinkedList<Double>();
	
	int m_days;
	double last = 0;
	
	//double m_devSum = 0;
	
	
	/**
	 * 
	 * @param inGraph
	 * @param inDays
	 */
	public Bolinger(Vector<Sample> inGraph, int inDays){
		
		m_graph = new Vector<Sample>();
		for(Sample samp : inGraph){
			m_graph.add(samp);
		}

		m_days = inDays;
		
		int index = 0;
		double all = 0;
		
		
		for(Sample samp : m_graph){
			if(index < m_days-1){
				
				addMASample(samp.close);
			
			}else if(index == m_days-1){
				
				addMASample(samp.close);
				
				Sample maSamp = new Sample(samp.ticker, Double.toString(getAvg(m_avgMASample)), samp.ask, samp.bid, samp.order, samp.date, samp.time);
				m_MA.add(maSamp);
				calcDeviation(samp, maSamp);
			}
			else{
				addMASample(samp.close);
				
				Sample maSamp = new Sample(samp.ticker, Double.toString(getAvg(m_avgMASample)), samp.ask, samp.bid, samp.order, samp.date, samp.time);
				m_MA.add(maSamp);
				calcDeviation(samp, maSamp);
			}
			index++;	
		}
	}
	
	/**
	 * 
	 * @param inSamp
	 */
	public void addToGraph(Sample inSamp){
		m_graph.add(inSamp);
		
		addMASample(inSamp.close);
		
		Sample maSamp = new Sample(inSamp.ticker, Double.toString(getAvg(m_avgMASample)), inSamp.ask, inSamp.bid, inSamp.order, inSamp.date, inSamp.time);
		m_MA.add(maSamp);
		
		calcDeviation(inSamp, maSamp);
	}
	
	public void addMASample(double inSamp){
		
		if(m_avgMASample.size() >= m_days)
			m_avgMASample.remove(0);
		
		m_avgMASample.add(inSamp);
		
	}
	
	public double getAvg( LinkedList<Double>  inList)
	{
		double sum = 0;
		for(Double d : inList)
			sum += d;
		
		return sum / inList.size();
	}
	
	public Vector<Sample> getMA(){
		return m_MA;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public double getLastFromMA(){
		return new Double(m_MA.lastElement().last);
	}
	
	/**
	 * 
	 * @param inSamp
	 * @param maSamp
	 */
	public void calcDeviation(Sample inSamp, Sample maSamp)
	{
		double dev = inSamp.close - maSamp.close;
		if(dev < 0)
			dev *= -1;
		
		if(m_avgDeviation.size() >= m_days)
			m_avgDeviation.remove(0);
		
		m_avgDeviation.add(dev);
		

		
		double avgDev = getAvg(m_avgDeviation);
		
		double highBandVal = avgDev + maSamp.close;
		
		Sample highBandSamp = new Sample(inSamp.ticker, Double.toString(highBandVal), "0", "0", inSamp.order, inSamp.date, inSamp.time);

		m_highBand.add(highBandSamp);
		
		double lowBandVal = maSamp.close - avgDev;
		
		Sample lowBandSamp = new Sample(inSamp.ticker, Double.toString(lowBandVal), "0", "0", inSamp.order, inSamp.date, inSamp.time);

		m_lowBand.add(lowBandSamp);
	}
	
	/**
	 * 
	 * @return
	 */
	public Vector<Vector<Sample>> getBolingerBands()
	{
		Vector<Vector<Sample>> ret = new Vector<Vector<Sample>>();
		ret.add(m_MA);
		ret.add(m_highBand);
		ret.add(m_lowBand);
		
		return ret;
	}
	
	public Sample getLowBand(){
		return m_lowBand.lastElement();
	}
	
	public Sample getHighBand(){
		return m_highBand.lastElement();
	}
	
	public Sample getBandMid(){
		return m_MA.lastElement();
	}
		
}
