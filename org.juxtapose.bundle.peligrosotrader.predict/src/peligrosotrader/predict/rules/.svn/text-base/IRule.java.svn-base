package peligrosotrader.predict.rules;

import java.util.Map;
import java.util.Vector;

import peligrosotrader.util.db.Sample;

public interface IRule {
	
	public int signal(Vector<Sample> inGraph);
	public void resetGraph();
	public int getID();
	public int getStartHoldDay();
	public int getEndHoldDay();
	public int getHoldDayIncrease();
	public String getName();
	public Map<Integer, String> getRuleParams();
	public int getScanSteps();
	public Map<String, String> getSignalInfo();
	public boolean lazyScan();

}
