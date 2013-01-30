package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;

import peligrosotrader.ruleevaluation.mining.MiningRule;
import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.Bolinger;

public class RuleTerm_BB extends MiningRule{
	
	private Bolinger m_bb;
	
	int m_days = 2;
	
	int m_holdDays = 20;
	int m_holdIndex = 0;
	
	boolean m_first = true;
	
	boolean m_isOverLow = true;
	
	boolean m_firstMiningRun = true;

	public void finish() {
		// TODO Auto-generated method stub
		
	}

	public double leavePos(Vector<Sample> inGraph) {

		m_bb.addToGraph(inGraph.lastElement());
		checkPosition(inGraph.lastElement());
		
		//if(++m_holdIndex >= m_holdDays){
//			if(m_bb.getHighBand().close < inGraph.lastElement().close){
//				checkPosition(inGraph.lastElement());
//				m_holdIndex = 0;
//				return true;
//			}
		//}

//		String dateString = inGraph.lastElement().date.toString();
//		if(dateString.equals("2002-11-01"))
//			System.out.println(dateString);
		
//		double highBand = m_bb.getHighBand().close;
//		double last = inGraph.lastElement().close;
		
		if(m_bb.getHighBand().close < inGraph.lastElement().close){
			checkPosition(inGraph.lastElement());
			m_holdIndex = 0;
			return inGraph.lastElement().close;
		}
		return -1;
	}

	public void resetGraph() {
		m_first = true;
		
	}

	public double takePos(Vector<Sample> inGraph) {
		
		if(inGraph.size() < m_days){
			return -1;
		}
		else if(m_first){
			m_bb = new Bolinger(inGraph, m_days);
			m_first = false;
		}
		else
			m_bb.addToGraph(inGraph.lastElement());
		
		if(m_bb.getLowBand().close > inGraph.lastElement().close){
			checkPosition(inGraph.lastElement());
			m_holdIndex = 0;
			return inGraph.lastElement().close;
		}
		checkPosition(inGraph.lastElement());
		return -1;
		
	}
	
	private void checkPosition(Sample inSamp)
	{
		m_isOverLow =  m_bb.getLowBand().close < inSamp.close;
	}

	
	public boolean changeFirstParam(){
		m_firstMiningRun = true;
		return ((m_days+=1) < 10);
	}
	
	public boolean changeSecParam(){
		if(m_firstMiningRun){
			m_firstMiningRun = false;
			return true;
		}
		return false;
	}
	
	public double getFirstParam(){
		return m_days;
	}
	public double getSecParam(){
		return 0;
	}
	
	public double getAvgHolding(){
		return m_holdDays;
	}
	
}
