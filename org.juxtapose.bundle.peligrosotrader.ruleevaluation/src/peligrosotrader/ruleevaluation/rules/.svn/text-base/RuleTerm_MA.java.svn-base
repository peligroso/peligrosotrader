package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;

import peligrosotrader.ruleevaluation.mining.MiningRule;
import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.MA;

public class RuleTerm_MA extends MiningRule{
	
	public int MA = 20;
	
	private MA m_ma;
	
	boolean isUnder = false;
	
	
	public void resetGraph(){
		
	}
	
	public double leavePos(Vector<Sample> inGraph) {
		
		boolean m1 = addToMA(inGraph);
		
		if( m1 ){
			
			double last = new Double(inGraph.lastElement().last);
			if(m_ma.getLastFromMA() > last){
				endTrade();
				return inGraph.lastElement().close;
			}
			return -1;
		}
		else
			return -1;
	}

	public double takePos(Vector<Sample> inGraph) {
		boolean m1 = addToMA(inGraph);

		if( m1 ){
			
			double last = new Double(inGraph.lastElement().last);
			if(m_ma.getLastFromMA() < last && isUnder){
				
				return inGraph.lastElement().close;				
			}
			else
				isUnder = true;
			return -1;
		}else
			return -1;
	}
	
	private boolean addToMA(Vector<Sample> inGraph){
		
		if(inGraph.size() < MA){
			return false;
		}
		else{
			if(inGraph.size() == MA){
				m_ma = new MA(inGraph, MA);
			}
			else{
				m_ma.addToGraph(inGraph.lastElement());
			}
			return true;
		}
		
	}

	
	
	public void finish(){
		
	}
	
	private void endTrade(){
	}
	
	
	public boolean changeFirstParam(){
		MA += 5;
		if(MA > 30){
			MA = 5;
			return false;
		}
		return true;
	}
	
	public boolean changeSecParam(){
		
			return false;
	
	}
	
	public double getFirstParam(){
		return MA;
	}
	public double getSecParam(){
		return 0;
	}

}
