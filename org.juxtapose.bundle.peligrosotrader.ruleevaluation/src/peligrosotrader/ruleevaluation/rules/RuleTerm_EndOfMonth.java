package peligrosotrader.ruleevaluation.rules;

import java.util.Calendar;
import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class RuleTerm_EndOfMonth implements RuleTerm {
	
	int holdIndex = 0;
	int hold = 5;

	public void finish() {
		holdIndex = 0;

	}

	public double leavePos(Vector<Sample> inGraph) {
		Sample last = inGraph.lastElement();
		Calendar cal = Calendar.getInstance();
		cal.setTime(last.date);
		
		int day = cal.get(Calendar.DAY_OF_MONTH);
		if( day > 1 && day < 25 ){
			return inGraph.lastElement().close;
		}
		return -1;
		
//		if(holdIndex++ >= hold){
//			holdIndex = 0;
//			return true;
//		}
//		return false;
	}

	public void resetGraph() {
		// TODO Auto-generated method stub

	}

	public double takePos(Vector<Sample> inGraph) {
		Sample last = inGraph.lastElement();
		Calendar cal = Calendar.getInstance();
		cal.setTime(last.date);
		
		if(cal.get(Calendar.DAY_OF_MONTH) == 25){
			return inGraph.lastElement().close;
		}
		return -1;
	}

}
