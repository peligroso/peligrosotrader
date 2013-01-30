package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.hotpoints.HotPoint;
import peligrosotrader.util.ta.hotpoints.HotPointsStack;

public class RuleTerm_SupportPointsOnVolume implements RuleTerm{

	public static int MIN_LENGT_SIZE = 20;
	public static double MIN_STRENGTH = 4;
	
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
		
		HotPointsStack stack = new HotPointsStack(inGraph, HotPointsStack.SUPPORT, false, 20);
		
		Sample last = inGraph.lastElement();
		
		for(HotPoint hp : stack.m_hotPoints)
		{
			if(isAtHotSpot(hp.m_samp, last) && !hp.m_isBrooken && hp.m_strength > MIN_STRENGTH)
				if(hasBeenUpBow(hp.m_samp, inGraph))
					return inGraph.lastElement().close;
		}
		return -1;
	}
	
	boolean isAtHotSpot(Sample hotSpot, Sample test)
	{
		if(test.close * 0.98 > hotSpot.lowest)
			return false;
		else if(test.close * 1.02 < hotSpot.lowest)
			return false;
		
		return true;
	}
	
	public boolean hasBeenUpBow(Sample hotSpot, Vector<Sample> inGraph)
	{
		int size = inGraph.size();
		
		for(int i = 1; i < MIN_LENGT_SIZE; i++)
		{
			Sample s = inGraph.get(size-i);
			
			if(s.lowest * 1.02 < hotSpot.lowest)
				return false;
		}
		
		return true;
	}
}
