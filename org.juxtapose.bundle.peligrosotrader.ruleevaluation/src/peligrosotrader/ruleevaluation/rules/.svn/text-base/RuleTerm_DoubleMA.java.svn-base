package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;

import peligrosotrader.ruleevaluation.mining.MiningRule;
import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.MA;

public class RuleTerm_DoubleMA extends MiningRule{

	public int MA1 = 0;
	public int MA2 = 0;
	//private int m_count = 0;
	
	private MA m_ma1;
	private MA m_ma2;
	
	
	//statistics
	double numbHoldDays = 0;
	double avgHoldDays = 0;
	double numbTrades = 0;
	
	boolean isUnder = false;
	
	
	public void resetGraph(){
		numbHoldDays = 0;
	}
	
	public double leavePos(Vector<Sample> inGraph) {
		numbHoldDays++;
		
		boolean m1 = addToMA1(inGraph);
		boolean m2 = addToMA2(inGraph);
		
		
		if( m1 && m2){
			
			if(m_ma1.getLastFromMA() < m_ma2.getLastFromMA()){
				endTrade();
				return inGraph.lastElement().close;
			}
			return -1;
		}
		else
			return -1;
	}

	public double takePos(Vector<Sample> inGraph) {
		boolean m1 = addToMA1(inGraph);
		boolean m2 = addToMA2(inGraph);

		if( m1 && m2){
			
			if(m_ma1.getLastFromMA() > m_ma2.getLastFromMA() && isUnder){
				
				return inGraph.lastElement().close;				
			}
			else
				isUnder = true;
			return -1;
		}else
			return -1;
	}
	
	private boolean addToMA1(Vector<Sample> inGraph){
		
		if(inGraph.size() < MA1){
			return false;
		}
		else{
			if(inGraph.size() == MA1){
				m_ma1 = new MA(inGraph, MA1);
			}
			else{
				m_ma1.addToGraph(inGraph.lastElement());
			}
			return true;
		}
		
	}
	private boolean addToMA2(Vector<Sample> inGraph){
		
		if(inGraph.size() < MA2){
			return false;
		}
		else{
			if(inGraph.size() == MA2){
				m_ma2 = new MA(inGraph, MA2);
			}
			else{
				m_ma2.addToGraph(inGraph.lastElement());
			}
			return true;
		}
		
	}
	
	
	public void finish(){
		System.out.println("Avrage hold period is: "+avgHoldDays);
	}
	
	private void endTrade(){
		avgHoldDays = (avgHoldDays * numbTrades + (numbHoldDays))/++numbTrades;
		numbHoldDays = 0;
	}
	
	
	public boolean changeFirstParam(){
		MA1 += 5;
		if(MA1 > 30){
			MA1 = 5;
			return false;
		}
		return true;
	}
	
	public boolean changeSecParam(){
		MA2 += 10;
		if(MA2 > 40){
			MA2 = 10;
			return false;
		}
		return true;
	}
	
	public double getFirstParam(){
		return MA1;
	}
	public double getSecParam(){
		return MA2;
	}
	
	public double getAvgHolding(){
		return avgHoldDays;
	}
}
