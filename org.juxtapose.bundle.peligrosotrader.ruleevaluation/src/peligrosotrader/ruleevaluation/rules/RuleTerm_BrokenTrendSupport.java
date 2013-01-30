package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.trendlines.TrendLine;
import peligrosotrader.util.ta.trendlines.TrendStack;

public class RuleTerm_BrokenTrendSupport implements RuleTerm {
	
	static int HINDSIGHT = 250;
	static int HOLD_DAYS = 9;
	
	static double MARGIN = 0.015;
	
	static int STAY_OUT_FACTOR = 40;
	
	int holdIndex = 0;
	int stayOutIndex = 0;
	
	double entryVal = 0;
	double exitVal = 0;
	
	boolean stayOut = false;
	
	public void finish() {
		// TODO Auto-generated method stub
		
	}

	public double leavePos(Vector<Sample> inGraph) {
		if(holdIndex++ >= HOLD_DAYS){
			holdIndex = 0;
			exitVal = inGraph.get(inGraph.size()-1).close;
			if(exitVal / entryVal < 0.98){
				stayOutIndex = 0;
				stayOut = true;
			}
			return inGraph.lastElement().close;
		}
		return -1;
	}

	public void resetGraph() {
		holdIndex = 0;
		entryVal = 0;
		exitVal = 0;
		stayOutIndex = 0;
		stayOut = false;
	}

	public double takePos(Vector<Sample> inGraph) {
		if(inGraph.size() < HINDSIGHT)
			return -1;
		
		if(stayOut)
			if(++stayOutIndex < STAY_OUT_FACTOR)
				return -1;
			else{
				stayOutIndex = 0;
				stayOut = false;
			}
		
		
		TrendStack trendStack = new TrendStack(inGraph, 20, TrendStack.SUPPORT);
		trendStack.createTrends();
		trendStack.removeSingles();
		
		Sample lastSample = inGraph.get(inGraph.size()-1);
		if(supportedByTrend(lastSample, trendStack, inGraph.size()-1)){
			entryVal = lastSample.close;
			return inGraph.lastElement().close;
		}
		return -1;
	}
	
	private boolean supportedByTrend(Sample inSamp, TrendStack inTS, int sampIndex){
		Vector<TrendLine> lines = inTS.getTrends();
		
		for(TrendLine tl : lines){
			double trendValueOnIndex = tl.getValueOnIndex(sampIndex);
			if(compare(inSamp.close, trendValueOnIndex))
				return true;
			
		}
		return false;
	}
	private boolean compare(double inVal1, double inVal2){ 
		
		if(inVal1 < inVal2 * (1 - MARGIN))
			return true;
		
		return false;
	}

}