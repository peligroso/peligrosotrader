package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.trendlines.TrendLine;
import peligrosotrader.util.ta.trendlines.TrendStack;

public class RuleTerm_TrendResistance implements RuleTerm {

	static int HINDSIGHT = 250;
	static int HOLD_DAYS = 20;
	
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
			if(exitVal / entryVal > 1.02){
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

	public double takePos(Vector<Sample> inGraph) 
	{
		if(inGraph.size() < HINDSIGHT)
			return -1;
		
//		if(stayOut)
//			if(++stayOutIndex < STAY_OUT_FACTOR)
//				return -1;
//			else{
//				stayOutIndex = 0;
//				stayOut = false;
//			}
		
		
		TrendStack trendStack = new TrendStack(inGraph, 20, TrendStack.RESISTANCE);
		trendStack.createTrends();
		trendStack.removeSingles();
		
		Sample lastSample = inGraph.get(inGraph.size()-1);
		
		if(stoppedByTrend(lastSample, trendStack, inGraph.size()-1)){
			entryVal = lastSample.close;
			return inGraph.lastElement().close;
		}
		return -1;
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
				return true;
			}
			
		}
		return false;
	}
	
	private boolean compare(double inVal1, double inVal2){ 
		
		if(inVal2 >= inVal1  && (inVal1 * (1 + MARGIN)) > inVal2)
			return true;
		return false;
//		if(inVal1 > inVal2 * (1 + MARGIN))
//			return false;
//		else if(inVal1 < inVal2)
//			return false;
//		
//		return true;
	}

}
