package peligrosotrader.ruleevaluation.util;

import java.util.Calendar;
import java.util.Date;

public class FrequenzyTracker {

	Calendar m_first = null;
	Calendar m_last = null;
	
	int m_count = 0;
	
	double m_graphCount = 0;
	double totFreq = 0;
	double avgFreq = 0;
	
	public void track(){
		
		m_count++;
	}
	
	public void setFrequenzy(){
		if(m_last != null && m_first != null){
			double days = (double)(m_last.getTimeInMillis() - m_first.getTimeInMillis())/(1000*60*60*24);
			double years = days/365;
			if(years != 0 && m_graphCount != 0){
				totFreq += m_count/years;
				avgFreq = totFreq/m_graphCount;
			}
		}
	}
	
	public void startNew(Date inStart, Date inEnd){
		
		m_first = Calendar.getInstance();
		m_first.setTimeInMillis(inStart.getTime());
		
		m_last = Calendar.getInstance();
		m_last.setTimeInMillis(inEnd.getTime());
		
		m_graphCount++;
		m_count = 0;
	}
	
	public void finishGraph(){
		setFrequenzy();
	}
	
	public double getFrequenzy(){
		return avgFreq;	
	}
	public double getFrequenzyAll(){
		return totFreq;
	}
}
