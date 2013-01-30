package peligrosotrader.ruleevaluation.rules;

import java.util.Calendar;
import java.util.Vector;

import peligrosotrader.ruleevaluation.mining.MiningRule;
import peligrosotrader.util.db.Sample;

public class RuleTerm_DayOfMonth extends MiningRule {
	
	int holdIndex = 0;

	boolean m_firstMiningRun = true;
	
	int m_startEntryDay = 1;
	int m_startExitDay = 1;
	
	int m_holdDay = 7;
	
	public void finish() {
		holdIndex = 0;

	}

	public double leavePos(Vector<Sample> inGraph) {
//		Sample last = inGraph.lastElement();
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(last.date);
//		
//		int day = cal.get(Calendar.DAY_OF_MONTH);
//		if( day > 6 && day < 27 ){
//			return true;
//		}
//		return false;
		
		if(holdIndex++ >= m_holdDay){
			holdIndex = 0;
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
		
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int weekDay = cal.get(Calendar.DAY_OF_WEEK);
		
		if(day == 25 || (day == 24 && weekDay == Calendar.FRIDAY) || (day == 23 && weekDay == Calendar.THURSDAY)){
			return inGraph.lastElement().close;
		}
		
		return -1;
	}
	
	public boolean changeFirstParam(){
		m_holdDay = 5;
		boolean ret = m_startEntryDay++ < 20;		
		return (ret);
	}
	
	public boolean changeSecParam(){
		
		return (( m_holdDay++) < 10);
	}
	
	public double getFirstParam(){
		return m_startEntryDay;
	}
	public double getSecParam(){
		return m_holdDay;
	}
	
	public double getAvgHolding(){
		return 0;
	}

}

