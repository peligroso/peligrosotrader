package peligrosotrader.predict.rules;

import java.util.Calendar;
import java.util.Map;
import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class Buy25 extends Rule {
	
	static int START_HOLD = 1;
	static int END_HOLD = 10;
	static int INC_HOLD = 1;
	
	static int ID = 104;
	static String NAME = "Buy 25";
	


	public void resetGraph() {
		// TODO Auto-generated method stub

	}

	public int signal(Vector<Sample> inGraph) {
		Sample last = inGraph.lastElement();
		Calendar cal = Calendar.getInstance();
		cal.setTime(last.date);
		
		if(cal.get(Calendar.DAY_OF_MONTH) == 25){
			return 1;
		}
		return 0;
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

	public int getStartHoldDay() {
		// TODO Auto-generated method stub
		return START_HOLD;
	}

	public Map<Integer, String> getRuleParams() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getScanSteps(){
		return 1;
	}
	
}
