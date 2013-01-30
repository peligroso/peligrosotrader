package peligrosotrader.util.ta;

import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class HMA {

	Vector<Sample> m_graph;
	Vector<Sample> m_MA = new Vector<Sample>();
	int m_days;
	int m_corrDays;
	double last = 0;
	double lastCorr = 0;
	
	public HMA(Vector<Sample> inGraph, int inDays){
		
		m_graph = new Vector<Sample>();
		for(Sample samp : inGraph){
			m_graph.add(samp);
		}

		m_days = inDays;
		m_corrDays = m_days/2;
		
		int index = 0;
		double all = 0;
		double corrAll = 0;
		
		for(Sample samp : m_graph){
			if(index < m_days-1){
				all += new Double(samp.last);
				if(index >= m_days-m_corrDays)
					corrAll += new Double(samp.last);
			}else if(index == m_days-1){
				all+= new Double(samp.last);
				corrAll += new Double(samp.last);
				last = all/m_days;
				lastCorr = corrAll/m_corrDays;
				double addFact = lastCorr - last;
				double res = lastCorr+addFact;
				Sample maSamp = new Sample(samp.ticker, Double.toString(res), samp.ask, samp.bid, samp.order, samp.date, samp.time);
				m_MA.add(maSamp);
			}
			else{
				last = ((last * (m_days-1)) + new Double(samp.last)) / m_days;
				lastCorr = ((lastCorr * (m_corrDays-1)) + new Double(samp.last)) / m_corrDays;
				double addFact = lastCorr - last;
				double res = lastCorr+addFact;
				Sample maSamp = new Sample(samp.ticker, Double.toString(res), samp.ask, samp.bid, samp.order, samp.date, samp.time);
				m_MA.add(maSamp);
			}
			index++;	
		}
	}
	
	public void addToGraph(Sample inSamp){
		m_graph.add(inSamp);
		last = ((last * (m_days-1)) + new Double(inSamp.last)) / m_days;
		lastCorr = ((lastCorr * (m_corrDays-1)) + new Double(inSamp.last)) / m_corrDays;
		double addFact = lastCorr - last;
		double res = lastCorr+addFact;
		
		Sample maSamp = new Sample(inSamp.ticker, Double.toString(res), inSamp.ask, inSamp.bid, inSamp.order, inSamp.date, inSamp.time);
		m_MA.add(maSamp);
	}
	
	public Vector<Sample> getMA(){
		return m_MA;
		
	}
	
	public double getLastFromMA(){
		return new Double(m_MA.lastElement().last);
	}
	
}
