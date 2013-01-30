package peligrosotrader.predict.rules;

import java.util.Map;
import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.EquityKey;

public class ATL extends Rule {
	
	public static int ID = 111;
	static String NAME = "ATL"; 
	public static String DESCRIPTION = "Signals when the graph is at least 200 days long and closes at a new low appart from big moves";
	
	static int START_HOLD = 1;
	static int END_HOLD = 40;
	static int INC_HOLD = 5;
	
	public static int MIN_SIZE = 200;

	double m_atl = 1000000;
	int time = 0;
	EquityKey m_keys;
	boolean m_first = true;

	public int getEndHoldDay() {
		// TODO Auto-generated method stub
		return END_HOLD;
	}

	public int getHoldDayIncrease() {
		// TODO Auto-generated method stub
		return INC_HOLD;
	}

	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return NAME;
	}

	public Map<Integer, String> getRuleParams() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getStartHoldDay() {
		// TODO Auto-generated method stub
		return START_HOLD;
	}

	public void resetGraph() {
		m_first = true;

	}

	public int signal(Vector<Sample> inGraph) {
		if(m_first){
			m_keys = new EquityKey(inGraph);
			m_first = false;
		}
		m_atl = 1000000;
		time = 0;
		
		m_keys.addInstance(inGraph.get(inGraph.size()-1));
		
		if(inGraph.size() < MIN_SIZE)
			return 0;
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
				return ID;
		}
		return 0;
	}
	
	public int getScanSteps(){
		return -1;
	}

}
