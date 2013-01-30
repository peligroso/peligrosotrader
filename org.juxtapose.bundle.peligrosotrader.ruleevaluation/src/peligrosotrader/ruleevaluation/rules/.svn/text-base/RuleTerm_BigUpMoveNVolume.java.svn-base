package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.EquityKey;

public class RuleTerm_BigUpMoveNVolume implements RuleTerm {

	static int FACTOR = 4;
	static int VOLUME_FACTOR = 4;
	static int HOLD_DAYS = 2;
	
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
			
			//changed to downMove
			Sample lastSamp = inGraph.get(inGraph.size()-1);
			double dayUpMove = (lastSamp.open / lastSamp.close) - 1;
			double avgMove = m_keys.getAvg(EquityKey.DAY_MOVE);
			double avgVolume = m_keys.getAvg(EquityKey.DAY_VOLUME);
			if((dayUpMove > avgMove * FACTOR)  && (lastSamp.volume > avgVolume * VOLUME_FACTOR)){
				holdDayIndex = 0;
				return inGraph.lastElement().close;
			}
			m_keys.addInstance(lastSamp);
		}
		return -1;
	}


}
