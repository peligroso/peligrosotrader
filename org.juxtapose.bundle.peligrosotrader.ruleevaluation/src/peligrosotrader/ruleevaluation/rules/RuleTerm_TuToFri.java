package peligrosotrader.ruleevaluation.rules;

import java.util.Calendar;
import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class RuleTerm_TuToFri implements RuleTerm {

	public void finish() {
		// TODO Auto-generated method stub

	}

	public double leavePos(Vector<Sample> inGraph) {
		Sample last = inGraph.lastElement();
		Calendar cal = Calendar.getInstance();
		cal.setTime(last.date);
		
		if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
			return inGraph.lastElement().close;
		}
		return -1;
	}

	public void resetGraph() {
		// TODO Auto-generated method stub

	}

	public double takePos(Vector<Sample> inGraph) {
		Sample last = inGraph.lastElement();
		Calendar cal = Calendar.getInstance();
		cal.setTime(last.date);
		
		if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY){
			return inGraph.lastElement().close;
		}
		return -1;
	}

}
