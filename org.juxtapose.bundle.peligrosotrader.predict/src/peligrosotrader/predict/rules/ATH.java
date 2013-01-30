package peligrosotrader.predict.rules;

import java.util.Map;
import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class ATH extends Rule {

	public static int ID = 102;
	static String NAME = "ATH"; 
	public static String DESCRIPTION = "Signals when the graph is at least 200 days long and closes at a new high";
	
	static int START_HOLD = 1;
	static int END_HOLD = 40;
	static int INC_HOLD = 5;
	
	public static int MIN_SIZE = 200;
	double m_ath = 0;


	
	public void resetGraph(){
		m_ath = 0;
	}
	public void finish(){
		
	}

	public int getID() {
		return ID;
	}

	public int signal(Vector<Sample> inGraph) {
		
		Sample last = inGraph.lastElement();
		int signal = 0;
		
		if(last.close > m_ath)
		{
			m_ath = last.close;
			signal = 1;
		}
		if(inGraph.size() < MIN_SIZE)
			return 0;
		
		return signal;
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
		return null;
	}
	
	public int getScanSteps(){
		return -1;
	}

}
