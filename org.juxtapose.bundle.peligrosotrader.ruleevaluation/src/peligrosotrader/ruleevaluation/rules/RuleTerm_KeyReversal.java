package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class RuleTerm_KeyReversal implements RuleTerm {

	int hold = 0;
	public void finish() {
		// TODO Auto-generated method stub

	}

	public double leavePos(Vector<Sample> inGraph) {
		
		if(++hold > 10 ){
			hold = 0;
			return inGraph.lastElement().close;
		}
		return -1;

	}
	
	public boolean lowest(Vector<Sample> inGraph){
		if(inGraph.size() < 21){
			return false;
		}
		Sample lastSample = inGraph.get(inGraph.size()-1);
//		for(int i = inGraph.size()-2; i > inGraph.size()-21; i--){
//			if(lastSample.open > new Double(inGraph.get(i).lowest))
//				return false;
//		}
//		
//		if(lastSample.open == lastSample.lowest)
//			return false;
		
		Sample yesterDay = inGraph.get(inGraph.size()-2);
		if(new Double(lastSample.last) <= yesterDay.highest){
			return false;
		}
		return true;
		
		
	}

	public void resetGraph() {
		hold = 0;
		// TODO Auto-generated method stub

	}

	public double takePos(Vector<Sample> inGraph) {
		if(lowest(inGraph)){
			return inGraph.lastElement().close;
		}
		return -1;
	}

}
