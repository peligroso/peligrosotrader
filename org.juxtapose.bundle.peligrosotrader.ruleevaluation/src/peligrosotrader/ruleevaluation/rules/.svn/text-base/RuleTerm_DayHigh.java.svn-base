package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class RuleTerm_DayHigh implements RuleTerm{

	public double leavePos(Vector<Sample> inGraph) {
		// TODO Auto-generated method stub
		return inGraph.lastElement().close;
	}

	public double takePos(Vector<Sample> inGraph) {
		Sample act = inGraph.get(inGraph.size()-1);
		if(new Double(act.last) == act.lowest && (act.highest > act.lowest)){
			
			return inGraph.lastElement().close;
		}
		return -1;
	}
	
	public void resetGraph(){
		
	}
	
	public void finish(){
		
	}

}
