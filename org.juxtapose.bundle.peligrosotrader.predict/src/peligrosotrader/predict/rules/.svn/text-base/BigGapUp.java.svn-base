package peligrosotrader.predict.rules;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.EquityKey;

public class BigGapUp extends Rule {
	
	public static int ID = 112;
	static String NAME = "Big gap up"; 
	public static String DESCRIPTION = "Signals when open far over yesterdays close with the parameters set from the intraday movement.";
	
	static int START_HOLD = 1;
	static int END_HOLD = 30;
	static int INC_HOLD = 3;
	
	static int FACTOR = 5;
	
	/**PARAMS**/
	static int FAINTH_MOVE = 11201;
	static int UP_MOVE = 11202;
	static int DOWN_MOVE = 11203;
	
	EquityKey m_keys;
	boolean m_first = true;
	
	static Map<Integer, String> PARAM_MAP;
	
	static
	{		
		PARAM_MAP = new Hashtable<Integer, String>();
		PARAM_MAP.put(FAINTH_MOVE, "Faint move");
		PARAM_MAP.put(UP_MOVE, "Big up move");
		PARAM_MAP.put(DOWN_MOVE, "Big down move");
	}

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
		return PARAM_MAP;
	}

	public int getStartHoldDay() {
		// TODO Auto-generated method stub
		return START_HOLD;
	}

	public void resetGraph() {
		// TODO Auto-generated method stub

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
			Sample beforeLastSamp = inGraph.get(inGraph.size()-2);
			
			double closeOpenDiff = (lastSamp.open / beforeLastSamp.close) - 1;
			double avgMove = m_keys.getAvg(EquityKey.DAY_MOVE);
			
			//boolean equals = lastSamp.close == lastSamp.open;
			boolean goingUp = true;
			
			double daySpan = (lastSamp.close / lastSamp.open)-1;
			if(daySpan < 0){
				goingUp = false;
				daySpan *= -1;
			}
			
			m_keys.addInstance(lastSamp);
			
			boolean fainthMove = daySpan < avgMove;
			
			if(closeOpenDiff > avgMove * FACTOR)
				if(fainthMove)
					return FAINTH_MOVE;
				else if(goingUp)
					return UP_MOVE;
				else
					return DOWN_MOVE;
			
		}
		return 0;
	}
	
	public int getScanSteps(){
		return 50;
	}

}
