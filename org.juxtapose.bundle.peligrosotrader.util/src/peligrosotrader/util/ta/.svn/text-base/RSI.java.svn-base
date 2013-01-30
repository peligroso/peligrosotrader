package peligrosotrader.util.ta;

import java.util.ArrayList;
import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class RSI extends TA{

	double numbHoldDays = 0;
	double avgHoldDays = 0;
	double numbTrades = 0;
	
	int m_period = 14;
	ArrayList<Double> gains = new ArrayList<Double>();
	ArrayList<Double> losses = new ArrayList<Double>();
	
	double prevAvgGain = 0;
	double prevAvgLoss = 0;
	
	double last = -1;
	
	int index = 0;
	
	Vector<Sample> m_rsiGraph = new Vector<Sample>();
	double m_startValue = -1;
	
	public RSI(Vector<Sample> inGraph, int inPeriod){
		
		m_period = inPeriod;
		
		for(Sample samp : inGraph)
		{
			double rsi = calcRSI(samp.close);
			if(rsi == -1){
				rsi = 0;
			}
			else if(m_startValue == -1)
				m_startValue = rsi;
			
			Sample newSamp = new Sample("rsi", Double.toString(rsi), samp.ask, samp.bid, samp.order, samp.date, samp.time);
			newSamp.close = rsi;
			m_rsiGraph.add(newSamp);
		}
		if(m_rsiGraph.size() > m_period)
			for(int i = 0; i < m_period; i++){
				Sample samp = m_rsiGraph.get(i);
				samp.last = Double.toString(m_startValue);
			}
	}
	
	public double calcRSI(double close){
		
		if(index == 0){
			last = close;
			index++;
			return -1;
		}
		else{
			double res = close - last;
			double thisGain = 0;
			double thisLoss = 0;
			//gainsLosses.add(res);
			if(res > 0){
				if(index <= m_period)
					gains.add(res);
				else
				thisGain = res;
			}
			if(res < 0){
				if(index <= m_period)
					losses.add(res);
				else{
					thisLoss = res;
					thisLoss*=-1;
				}
			}
			last = close;
			
			if(index >= m_period){
				
				double RSI;
				double avgGain;
				double avgLoss;
				if(index == m_period){
					avgGain = avg(gains, m_period);
					avgLoss = avg(losses, m_period);
					avgLoss *= -1;
					
					if(avgLoss == 0)
						RSI = 100;
					else
						RSI = avgGain/avgLoss;
				}															
				else{
					
					avgGain = (prevAvgGain*(m_period-1) + thisGain)/m_period;
					avgLoss = (prevAvgLoss*(m_period-1) + thisLoss)/ m_period;
					
					if(avgLoss == 0)
						RSI = 100;
					else
						RSI = avgGain / avgLoss;
				}

					RSI = 1+RSI;
					RSI = 100/RSI;
					RSI = 100 - RSI;
				
				prevAvgGain = avgGain;
				prevAvgLoss = avgLoss;
				
				index++;
				
				return RSI;				
			}
			index++;
			return -1;
		}
		
	}
	
	public void addToRSIGraph(Sample inSamp){
		double newRSI = calcRSI(inSamp.close);
		String valStr = Double.toString(newRSI);
		Sample newSamp = new Sample("rsi", valStr, valStr, valStr, inSamp.order, inSamp.date, inSamp.time);
		newSamp.close = newRSI;
		m_rsiGraph.add(newSamp);
	}
	
	public static double avg(ArrayList<Double> inList, int inPeriod){
		double tot = 0;
		for(double i : inList){
			tot += i;
		}
		return tot/new Double(inPeriod);
	}
	
	public Vector<Sample> getRSIGraph(){
		return m_rsiGraph;
	}
	
	public double getRSI(){
		Sample last = m_rsiGraph.get(m_rsiGraph.size()-1);
		return last.close;
	}
	
	public Vector<Sample> get30Line(){
		Vector<Sample> line = new Vector<Sample>();
		for(Sample samp : m_rsiGraph){
			Sample newSamp = new Sample("30", "30", "30", "30", samp.order, samp.date, samp.time);
			newSamp.close = 30;
			line.add(newSamp);
		}
		return line;
	}
	public Vector<Sample> get70Line(){
		Vector<Sample> line = new Vector<Sample>();
		for(Sample samp : m_rsiGraph){
			Sample newSamp = new Sample("70", "70", "70", "70", samp.order, samp.date, samp.time);
			newSamp.close = 70;
			line.add(newSamp);
		}
		return line;
	}
}
