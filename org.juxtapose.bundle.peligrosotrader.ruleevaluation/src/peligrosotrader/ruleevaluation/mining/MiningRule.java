package peligrosotrader.ruleevaluation.mining;

import java.util.Vector;

import peligrosotrader.ruleevaluation.rules.RuleTerm;
import peligrosotrader.util.db.Sample;

public abstract class MiningRule implements RuleTerm {

	public void finish() {
		// TODO Auto-generated method stub

	}

	public double leavePos(Vector<Sample> inGraph) {
		// TODO Auto-generated method stub
		return -1;
	}

	public void resetGraph() {
		// TODO Auto-generated method stub

	}

	public double takePos(Vector<Sample> inGraph) {
		// TODO Auto-generated method stub
		return -1;
	}
	
	public boolean changeFirstParam(){
		return false;
	}
	
	public boolean changeSecParam(){
		return false;
	}
	
	public double getFirstParam(){
		return 0;
	}
	public double getSecParam(){
		return 0;
	}
	
	public double getAvgHolding(){
		return 0;
	}

}
