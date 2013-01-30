package peligrosotrader.predict.rules;

import java.util.Map;
import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class Rule implements IRule {

	public int getEndHoldDay() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getHoldDayIncrease() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<Integer, String> getRuleParams() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getScanSteps() {
		// TODO Auto-generated method stub
		return -1;
	}

	public int getStartHoldDay() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void resetGraph() {
		// TODO Auto-generated method stub

	}

	public int signal(Vector<Sample> inGraph) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Map<String, String> getSignalInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean lazyScan(){
		return false;
	}

}
