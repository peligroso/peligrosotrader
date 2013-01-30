package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;

import peligrosotrader.ruleevaluation.mining.MiningRule;
import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.Correlation;
import peligrosotrader.util.ta.functions.LinearTrend;
import peligrosotrader.util.ta.functions.QPipe;

public class RuleTerm_LineTrend extends MiningRule {

	int holdIndex = 0;
	int trendKoff = 0;
	
	int hold = 20;
	int minTrendDays = 40;
	
	boolean m_firstMiningRun = false;

	public void finish() {
		// TODO Auto-generated method stub
		
	}

	public double leavePos(Vector<Sample> inGraph) {
//		if(!takePos(inGraph))
//			return true;
		if(++holdIndex > hold ){
			holdIndex = 0;
			trendKoff = 0;
			return inGraph.lastElement().close;
		}
		return -1;
	}

	public void resetGraph() {
		holdIndex = 0;
		trendKoff = 0;
	}

	public double takePos(Vector<Sample> inGraph) {
		if(inGraph.size() < minTrendDays)
			return -1;
		
		Vector<Sample> toTest = new Vector<Sample>();
		for(int i = inGraph.size()-1; i > 10; i-=5){
			toTest.add(0, inGraph.get(i));
			if(toTest.size() < minTrendDays)
				continue;
			
			//return if negative
//			if(new Double(toTest.get(0).last) >= new Double(toTest.get(toTest.size()-1).last))
//				return -1;
			
			double[] funcVec = LinearTrend.generateFunction(toTest);
			
			double angle = angle(funcVec);
			
			Correlation corr = new Correlation(funcVec, Sample.extractValues(toTest));
			if(corr.getCorrAll() > 0.94 && angle < 0){
				System.out.println(angle);
				System.out.println("working graph for line trend is: "+toTest.get(0).date+" to "+toTest.get(toTest.size()-1).date);
				trendKoff = toTest.size()/10;
				return inGraph.lastElement().close;
			}
		}
		return -1;
	}
	
	private double angle(double inFunction[]){
		if(inFunction.length < 2)
			return 0;
		else{
			double angle = (inFunction[1] / inFunction[0]) - 1;
			return angle;
		}
		
	}
	
	public void setHold(int inHold){
		hold = inHold;
	}
	public void setMinTrendDays(int inDays){
		minTrendDays = inDays;
	}

	
	public boolean changeFirstParam(){
		m_firstMiningRun = true;
		return ((minTrendDays+=20) < 100);
	}
	
	public boolean changeSecParam(){
		if(m_firstMiningRun){
			m_firstMiningRun = false;
			return true;
		}
		return false;
	}
	
	public double getFirstParam(){
		return minTrendDays;
	}
	public double getSecParam(){
		return 0;
	}
}
