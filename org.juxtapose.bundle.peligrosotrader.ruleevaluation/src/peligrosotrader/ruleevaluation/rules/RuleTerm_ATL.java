package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.EquityKey;

public class RuleTerm_ATL implements RuleTerm {

	public static int MIN_SIZE = 200;
	double m_atl = 1000000;
	int time = 0;
	EquityKey m_keys;
	boolean m_first = true;
	
	public double leavePos(Vector<Sample> inGraph) {
		// TODO Auto-generated method stub
//		double last = new Double(inGraph.get(inGraph.size()-1).last);
//		if(last > m_ath){
//			m_ath = last;
//			return false;
//		}
		m_keys.addInstance(inGraph.get(inGraph.size()-1));
		if(time > 10)
			return inGraph.lastElement().close;
		else{
			time++;
			return -1;
		}
	}

	public double takePos(Vector<Sample> inGraph) {
		
		if(m_first){
			m_keys = new EquityKey(inGraph);
			m_first = false;
		}
		m_atl = 1000000;
		time = 0;
		
		m_keys.addInstance(inGraph.get(inGraph.size()-1));
		
		if(inGraph.size() < MIN_SIZE)
			return -1;
		double last = -1;
		for( int i = 0; i < inGraph.size(); i++){
			last = new Double(inGraph.get(i).last);
			
			if(last < m_atl ){
				m_atl = last;
			}
		}
		double beforeLast = new Double(inGraph.get(inGraph.size()-2).last);
		if(last == m_atl){
			double avgMove = m_keys.getAvg(EquityKey.DAY_MOVE);
			if((beforeLast / last -1) < avgMove*5)
				return inGraph.lastElement().close;
		}
		return -1;
		
		
//		Random rand = new Random();
//		if(rand.nextInt(250) == 1)
//			return true;
//		
//		return false;
	}
	
	public void resetGraph(){
		m_first = true;
	}
	public void finish(){
		
	}
		

}