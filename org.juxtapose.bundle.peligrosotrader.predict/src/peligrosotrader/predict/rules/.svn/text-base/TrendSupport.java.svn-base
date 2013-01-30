package peligrosotrader.predict.rules;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import peligrosotrader.util.SystemDateFormat;
import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.trendlines.TrendLine;
import peligrosotrader.util.ta.trendlines.TrendStack;

public class TrendSupport extends Rule {
	
	static int ID = 106;
	
	static int POSITIV_ID = 10601;
	static int STRONG_ID = 10602;
	static int POS_N_STRONG_ID = 10603;
	
	static String NAME = "Trend support";
	static int HINDSIGHT = 250;

	static int START_HOLD = 5;
	static int END_HOLD = 60;
	static int INC_HOLD = 5;
	
	static Map<Integer, String> PARAM_MAP;
	
	static double MARGIN = 0.015;
	
	static int STAY_OUT_FACTOR = 40;
	
	int stayOutIndex = 0;
	
	double entryVal = 0;
	double exitVal = 0;
	
	boolean stayOut = false;
	
	String m_lastTriggered = null;
	
	static
	{		
		PARAM_MAP = new Hashtable<Integer, String>();
		PARAM_MAP.put(POSITIV_ID, "Positive trend");
		PARAM_MAP.put(STRONG_ID, "Strong trend");
		PARAM_MAP.put(POS_N_STRONG_ID, "Positive/Strong trend");
	}
	
	public void finish() {
		// TODO Auto-generated method stub
		
	}

	public void resetGraph() {

		entryVal = 0;
		exitVal = 0;
		stayOutIndex = 0;
		stayOut = false;
	}

	public int signal(Vector<Sample> inGraph) {
		if(inGraph.size() < HINDSIGHT)
			return 0;
		
		if(stayOut)
			if(++stayOutIndex < STAY_OUT_FACTOR)
				return 0;
			else{
				stayOutIndex = 0;
				stayOut = false;
			}
		
		
		TrendStack trendStack = new TrendStack(inGraph, 20, TrendStack.SUPPORT);
		trendStack.createTrends();
		trendStack.removeSingles();
		
		Sample lastSample = inGraph.get(inGraph.size()-1);
		int supported = supportedByTrend(lastSample, trendStack, inGraph.size()-1);
		if(supported != 0){
			entryVal = lastSample.close;
			return supported;
		}
		return 0;
	}
	
	private int supportedByTrend(Sample inSamp, TrendStack inTS, int sampIndex){
		Vector<TrendLine> lines = inTS.getTrends();
		
		for(TrendLine tl : lines){
			double trendValueOnIndex = tl.getValueOnIndex(sampIndex);
			
			boolean pos = tl.isPositive();
			boolean strong =  tl.m_mergeCount > 4;
			boolean compare = compare(inSamp.close, trendValueOnIndex);
			
			int ret = 0;
			if(compare && pos && strong)
				ret = POS_N_STRONG_ID;
			else if(compare && pos)
				ret = POSITIV_ID;
			else if(compare && strong)
				ret = STRONG_ID;
			else if(compare)
				ret = 1;
			
			if(ret != 0){
				m_lastTriggered = SystemDateFormat.DATE_FORMAT.format(tl.m_pointOne.m_sample.date);
				return ret;
			}
			
		}
		return 0;
	}
	private boolean compare(double inVal1, double inVal2){ 
		
		if(inVal1 > inVal2 * (1 + MARGIN))
			return false;
		else if(inVal1 < inVal2 * (1 - MARGIN))
			return false;
		
		return true;
	}

	public int getEndHoldDay() {
		// TODO Auto-generated method stub
		return END_HOLD;
	}

	public int getHoldDayIncrease() {
		// TODO Auto-generated method stub
		return INC_HOLD;
	}

	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return NAME;
	}

	public int getStartHoldDay() {
		// TODO Auto-generated method stub
		return START_HOLD;
	}
	
	public Map<Integer, String> getRuleParams() {
		// TODO Auto-generated method stub
		return PARAM_MAP;
	}
	public int getScanSteps(){
		return -1;
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
