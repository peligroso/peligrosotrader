package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;

import peligrosotrader.util.db.Sample;

public interface RuleTerm {
	
	public double takePos(Vector<Sample> inGraph);
	public double leavePos(Vector<Sample> inGraph);
	public void resetGraph();
	public void finish();

}
