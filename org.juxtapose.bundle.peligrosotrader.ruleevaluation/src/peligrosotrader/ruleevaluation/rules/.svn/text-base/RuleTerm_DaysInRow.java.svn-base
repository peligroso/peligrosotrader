package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;
import peligrosotrader.util.db.Sample;

public class RuleTerm_DaysInRow implements RuleTerm{
	
	static int DAYS = 8;
	
	int m_dayIndex = 0;
	double lastLast = 12000000;
	
	public double takePos(Vector<Sample> inGraph){
		lastLast = 2000000;
		if(inGraph.size() < DAYS)
			return -1;
		for( int i = 1; i < DAYS+1; i++){
			double last = new Double(inGraph.get(inGraph.size()-i).last);
			if(last < lastLast){
				lastLast = last;
			}
			else
				return -1;
		}
		return inGraph.lastElement().close;
	}
	
	public double leavePos(Vector<Sample> inGraph){
		return inGraph.lastElement().close;
	}
	
	public void resetGraph(){
		
	}
	
	public void finish(){
		
	}

}
