package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.EquityKey;

public class RuleTerm_BigDownDay implements RuleTerm {

	static int FACTOR = 5;
	static int HOLD_DAYS = 16;
	
	EquityKey m_keys;
	boolean m_first = true;
	
	int holdDayIndex = 0;
	
	public void finish() {
		// TODO Auto-generated method stub

	}

	public double leavePos(Vector<Sample> inGraph) {
		m_keys.addInstance(inGraph.get(inGraph.size()-1));
		if(++holdDayIndex >= HOLD_DAYS)
			return inGraph.lastElement().close;
		return -1;
	}

	public void resetGraph() {
		m_first = true;
		holdDayIndex = 0;

	}

	public double takePos(Vector<Sample> inGraph) {
		if(m_first){
			m_keys = new EquityKey(inGraph);
			m_first = false;
		}
		else{
			if(inGraph.size() < 40)
				return -1;
			
			Sample lastSamp = inGraph.get(inGraph.size()-1);
			double dayDownMove = (lastSamp.open / lastSamp.close) - 1;
			double avgMove = m_keys.getAvg(EquityKey.DAY_MOVE);
			if(dayDownMove > avgMove * FACTOR){
				holdDayIndex = 0;
				return inGraph.lastElement().close;
			}
			m_keys.addInstance(lastSamp);
		}
		return -1;
	}

}
