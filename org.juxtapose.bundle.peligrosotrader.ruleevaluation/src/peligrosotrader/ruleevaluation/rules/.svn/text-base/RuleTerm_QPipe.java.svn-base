package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.Correlation;
import peligrosotrader.util.ta.functions.QPipe;

public class RuleTerm_QPipe implements RuleTerm{
	
	int holdIndex = 0;
	int trendKoff = 0;
	
	int hold = 60;
	int minTrendDays = 60;

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
		for(int i = inGraph.size()-1; i > 10; i--){
			toTest.add(0, inGraph.get(i));
			if(toTest.size() < minTrendDays)
				continue;
			
			if(new Double(toTest.get(0).last) >= new Double(toTest.get(toTest.size()-1).last))
				return -1;
			double[] funcVec = QPipe.generateFunction(toTest, 0, 90);
			Correlation corr = new Correlation(funcVec, Sample.extractValues(toTest));
			if(corr.getCorrAll() > 0.98){
				System.out.println("working graph is: "+toTest.get(0).date+" to "+toTest.get(toTest.size()-1).date);
				trendKoff = toTest.size()/10;
				return inGraph.lastElement().close;
			}
		}
		return -1;
	}
	
	public void setHold(int inHold){
		hold = inHold;
	}
	public void setMinTrendDays(int inDays){
		minTrendDays = inDays;
	}

	
}
