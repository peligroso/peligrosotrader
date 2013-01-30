package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.hotpoints.HotPoint;
import peligrosotrader.util.ta.hotpoints.HotPointsStack;

public class RuleTerm_StrongResistancePoints implements RuleTerm {

	public static int MIN_LENGT_SIZE = 20;
	public static double MIN_STRENGTH = 0;
	
	public static final int HOLDING_DAYS = 5;
	private int m_holdIndex = 0;

	public void finish() {
		// TODO Auto-generated method stub
		
	}

	public double leavePos(Vector<Sample> inGraph) {

		if(++m_holdIndex >= HOLDING_DAYS){
			m_holdIndex = 0;
			return inGraph.lastElement().close;
		}
		return -1;
	}

	public void resetGraph() {
		m_holdIndex = 0;
		
	}

	public double takePos(Vector<Sample> inGraph) {

		if(inGraph.size() < MIN_LENGT_SIZE)
			return -1;
		
		HotPointsStack stack = new HotPointsStack(inGraph, HotPointsStack.RESISTANCE, true, 2);
		
		Sample last = inGraph.lastElement();
		
		for(HotPoint hp : stack.m_hotPoints)
		{
			/**new inplementation test**/
//			if(hasBeenBelow(hp.m_samp, inGraph) && !hp.m_isBrooken && hp.m_strength > MIN_STRENGTH){
//				if(hp.m_samp.highest < last.highest){
//					return hp.m_samp.highest;
//				}
//			}
			/** end test**/
			if(isAtHotSpot(hp.m_samp, last) && !hp.m_isBrooken && hp.m_strength > MIN_STRENGTH)
				if(hasBeenBelow(hp.m_samp, inGraph)){
					System.out.println("daily close: "+inGraph.lastElement().close+" is at hotPoint "+hp.m_samp.close);
					return inGraph.lastElement().close;
				}
		}
		return -1;
	}
	
	boolean isAtHotSpot(Sample hotSpot, Sample test)
	{
		if(test.close * 0.98 > hotSpot.highest)
			return false;
		else if(test.close /*** 1.01**/ < hotSpot.highest)
			return false;
		
		return true;
	}
	
	public boolean hasBeenBelow(Sample hotSpot, Vector<Sample> inGraph)
	{
		int size = inGraph.size();
		
		for(int i = 2; i < MIN_LENGT_SIZE; i++)
		{
			Sample s = inGraph.get(size-i);
			
//			if(s.lowest < hotSpot.lowest)
//				return false;
			
			if(s.highest * 1.02 > hotSpot.highest)
				return false;
		}
		
		return true;
	}
}
