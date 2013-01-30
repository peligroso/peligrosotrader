package peligrosotrader.predict.rules;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.EquityKey;

public class BigUpDay extends Rule {
	
	public static int ID = 109;
	static String NAME = "Big Up Day"; 
	public static String DESCRIPTION = "Signals when the intraday move is larger then an average day move * 5 and closes up";
	
	public static int DAY_HIGH_ID = 10901;
	public static int NOT_DAY_HIGH_ID = 10902;
	
	static int START_HOLD = 1;
	static int END_HOLD = 10;
	static int INC_HOLD = 1;
	
	static int FACTOR = 5;

	EquityKey m_keys;
	boolean m_first = true;
	
	static Map<Integer, String> PARAM_MAP;
	
	static
	{		
		PARAM_MAP = new Hashtable<Integer, String>();
		PARAM_MAP.put(DAY_HIGH_ID, "close at day high");
		PARAM_MAP.put(NOT_DAY_HIGH_ID, "don't close at day high");
	}

	public void resetGraph() {

		m_first = true;

	}

	public int signal(Vector<Sample> inGraph) {

		if(m_first){
			m_keys = new EquityKey(inGraph);
			m_first = false;
		}
		else{
			if(inGraph.size() < 40)
				return 0;
			
			Sample lastSamp = inGraph.get(inGraph.size()-1);
			double dayUpMove = (lastSamp.close / lastSamp.open) - 1;
			double avgMove = m_keys.getAvg(EquityKey.DAY_MOVE);
			if(dayUpMove > avgMove * FACTOR){
				if(lastSamp.close == lastSamp.highest)
					return DAY_HIGH_ID;
				else
					return NOT_DAY_HIGH_ID;
			}
			m_keys.addInstance(lastSamp);
		}
		return 0;
	}

	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
	public int getEndHoldDay() {
		// TODO Auto-generated method stub
		return END_HOLD;
	}

	public int getHoldDayIncrease() {
		// TODO Auto-generated method stub
		return INC_HOLD;
	}

	public int getStartHoldDay() {
		// TODO Auto-generated method stub
		return START_HOLD;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return NAME;
	}
	
	public Map<Integer, String> getRuleParams() {
		// TODO Auto-generated method stub
		return PARAM_MAP;
	}
	
	public int getScanSteps(){
		return 50;
	}

}

