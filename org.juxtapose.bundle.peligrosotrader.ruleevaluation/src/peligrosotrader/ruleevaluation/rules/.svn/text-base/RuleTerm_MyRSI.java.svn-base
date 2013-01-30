package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.RSI;

public class RuleTerm_MyRSI implements RuleTerm {
	
	RSI m_rsi;
	static int PERIOD = 5;
	
	static int HOLDING_PERIOD = 10;
	static int TIME_TO_CROSS = 4;
	
	int m_holdingIndex = 0;
	
	boolean m_first = true;
	
	boolean m_under30 = false;
	boolean m_over60 = false;
	
	int m_timeToCrossIndex = 0;

	public void finish() {
		// TODO Auto-generated method stub

	}

	public double leavePos(Vector<Sample> inGraph) {
		
		Sample lastSamp = inGraph.get(inGraph.size()-1);
		m_rsi.addToRSIGraph(lastSamp);
		
//		double rsiVal = m_rsi.getRSI();
//		if(rsiVal > 50){
//			m_over60 = true;
//			return false;
//		}
//		if(m_over60 && rsiVal < 50){
//			m_over60 = false;
//			m_holdingIndex = 0;
//			return true;
//		}
		
		if(++m_holdingIndex >= HOLDING_PERIOD)
			return inGraph.lastElement().close;
		return -1;
	}

	public void resetGraph() {
		m_first = true;		
		m_timeToCrossIndex = 0;
		m_over60 = false;
		m_under30 = false;
		m_holdingIndex = 0;
	}

	public double takePos(Vector<Sample> inGraph) {
		if(m_first){
			m_rsi = new RSI(inGraph, PERIOD);
			m_first = false;
			return -1;
		}
		Sample lastSamp = inGraph.get(inGraph.size()-1);
		m_rsi.addToRSIGraph(lastSamp);
		if(inGraph.size() < PERIOD){
			return -1;
		}
		double rsiVal = m_rsi.getRSI();

		if(rsiVal < 30){
			m_under30 = true;
			return -1;
		}
		if(m_under30 && rsiVal > 30){
			m_under30 = false;
			m_holdingIndex = 0;
			return inGraph.lastElement().close;
		}
		
		
		return -1;
	}

}
