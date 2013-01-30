package peligrosotrader.ruleevaluation.rules;

import java.util.Random;
import java.util.Vector;

import peligrosotrader.ruleevaluation.mining.MiningRule;
import peligrosotrader.util.db.Sample;

public class RuleTerm_Random extends MiningRule {

	static int HOLD_DAYS = 0;
	static int INDEX = 0;
	
	int holdIndex = 0;
	int stayOutIndex = 0;
	
	Random m_rand = new Random();
	
	public void finish() {
		// TODO Auto-generated method stub

	}

	public double leavePos(Vector<Sample> inGraph) 
	{
		if(holdIndex++ >= HOLD_DAYS){
			holdIndex = 0;
			return inGraph.lastElement().close;
		}
		return -1;
	}

	public void resetGraph() {
		holdIndex = 0;

	}

	public double takePos(Vector<Sample> inGraph) 
	{
		if(m_rand.nextInt(150) == 99)
			return inGraph.lastElement().close;
		return -1;
	}
	boolean firstSec = true;
	
	public boolean changeFirstParam(){
		boolean ret = HOLD_DAYS < 40;
		if(ret){
			HOLD_DAYS+=5;
			firstSec = true;
			return true;
		}
		else return false;
	}
	
	
	public boolean changeSecParam(){
		if(firstSec){
			firstSec = false;
			return true;
		}
		else return false;
	}
	
	public double getFirstParam(){
		return HOLD_DAYS;
	}
	public double getSecParam(){
		return 0;
	}
	
	public double getAvgHolding(){
		return 0;
	}

}
