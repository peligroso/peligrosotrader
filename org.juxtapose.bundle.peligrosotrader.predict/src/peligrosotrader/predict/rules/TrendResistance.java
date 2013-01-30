package peligrosotrader.predict.rules;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import peligrosotrader.util.SystemDateFormat;
import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.trendlines.TrendLine;
import peligrosotrader.util.ta.trendlines.TrendStack;

public class TrendResistance extends Rule {
	
	static int START_HOLD = 5;
	static int END_HOLD = 60;
	static int INC_HOLD = 5;
	
	static int ID = 116;
	
	static String NAME = "Trend resistance";
	
	static double MARGIN = 0.015;
	static int HINDSIGHT = 250;
	
	String m_lastTriggered = null;
	
	public int getEndHoldDay() {
		return END_HOLD;
	}

	public int getHoldDayIncrease() {
		return INC_HOLD;
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return NAME;
	}

	public int getStartHoldDay() {
		return START_HOLD;
	}



	public Map<Integer, String> getRuleParams() {
		return null;
	}

	public int getScanSteps() {
		return -1;
	}


	public void resetGraph() {
		// TODO Auto-generated method stub

	}

	public int signal(Vector<Sample> inGraph) 
	{
		
		if(inGraph.size() < HINDSIGHT)
			return 0;
		
		
		TrendStack trendStack = new TrendStack(inGraph, 20, TrendStack.RESISTANCE);
		trendStack.createTrends();
		trendStack.removeSingles();
		
		Sample lastSample = inGraph.get(inGraph.size()-1);
		
		if(stoppedByTrend(lastSample, trendStack, inGraph.size()-1)){
			return 1;
		}
		return 0;
	}
	
	/**
	 * 
	 * @param inSamp
	 * @param inTS
	 * @param sampIndex
	 * @return
	 */
	private boolean stoppedByTrend(Sample inSamp, TrendStack inTS, int sampIndex)
	{
		Vector<TrendLine> lines = inTS.getTrends();
		
		for(TrendLine tl : lines){
			double trendValueOnIndex = tl.getValueOnIndex(sampIndex);
			if(compare(inSamp.close, trendValueOnIndex)&& !tl.isPositive()){
				System.out.println("stock closes at: "+inSamp.close+" and resistance is at: "+trendValueOnIndex);
				m_lastTriggered = SystemDateFormat.DATE_FORMAT.format(tl.m_pointOne.m_sample.date);
				return true;
			}
			
		}
		return false;
	}
	
	private boolean compare(double inVal1, double inVal2){ 
		
		if(inVal2 >= inVal1  && (inVal1 * (1 + MARGIN)) > inVal2)
			return true;
		return false;
	}
	
	public Map<String, String> getSignalInfo() {
		// TODO Auto-generated method stub
		if(m_lastTriggered != null){
			Map<String, String> params = new Hashtable<String, String>();
			params.put("START_DATE", (m_lastTriggered));
			
			return params;
		}
		return null;
	}
	
	public boolean lazyScan(){
		return true;
	}

}
