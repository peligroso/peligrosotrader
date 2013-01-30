package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.EquityKey;

public class RuleTerm_ATH implements RuleTerm {

	public static int MIN_SIZE = 200;
	double m_ath = 0;
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
		if(time > 16)
			return inGraph.lastElement().close;
		else{
			time++;
			return -1;
		}
	}

	public double takePos(Vector<Sample> inGraph) {
		m_ath = 0;
		time = 0;
		if(m_first){
			m_keys = new EquityKey(inGraph);
			m_first = false;
		}
		
		m_keys.addInstance(inGraph.get(inGraph.size()-1));
		
		if(inGraph.size() < MIN_SIZE)
			return -1;
		double last = -1;
		for( int i = 0; i < inGraph.size(); i++){
			last = new Double(inGraph.get(i).last);
			if(last > m_ath){
				m_ath = last;
			}
		}
		double beforeLast = new Double(inGraph.get(inGraph.size()-2).last);
		if(last == m_ath){
			double avgMove = m_keys.getAvg(EquityKey.DAY_MOVE);
			if((1 - (beforeLast / last)) < avgMove*5)
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
		
	}
	public void finish(){
		
	}
		

}
