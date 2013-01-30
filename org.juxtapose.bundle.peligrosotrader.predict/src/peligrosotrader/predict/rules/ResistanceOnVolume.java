package peligrosotrader.predict.rules;

import java.util.Map;
import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.hotpoints.HotPoint;
import peligrosotrader.util.ta.hotpoints.HotPointsStack;

public class ResistanceOnVolume extends Rule{

	public static int ID = 117;
	static String NAME = "Resistance on Volume"; 
	public static String DESCRIPTION = "Resistance is calculated with 2 day span, volume and no strength-check";
	
	static int START_HOLD = 1;
	static int END_HOLD = 40;
	static int INC_HOLD = 5;
	
	public static int MIN_LENGTH_SIZE = 20;
	
	public static double MIN_STRENGTH = 0;


	
	public void resetGraph(){
	
	}
	public void finish(){
		
	}

	public int getID() {
		return ID;
	}

	public int signal(Vector<Sample> inGraph) {
		
		if(inGraph.size() < MIN_LENGTH_SIZE)
			return 0;
		
		HotPointsStack stack = new HotPointsStack(inGraph, HotPointsStack.RESISTANCE, true, 2);
		
		Sample last = inGraph.lastElement();
		
		for(HotPoint hp : stack.m_hotPoints)
		{
	
			if(isAtHotSpot(hp.m_samp, last) && !hp.m_isBrooken && hp.m_strength > MIN_STRENGTH)
				if(hasBeenBelow(hp.m_samp, inGraph)){
					System.out.println("daily close: "+inGraph.lastElement().close+" is at hotPoint "+hp.m_samp.close);
					return 1;
				}
		}
		return 0;
	}
	
	boolean isAtHotSpot(Sample hotSpot, Sample test)
	{
		if(test.close * 0.98 > hotSpot.highest)
			return false;
		else if(test.close < hotSpot.highest)
			return false;
		
		return true;
	}
	
	public boolean hasBeenBelow(Sample hotSpot, Vector<Sample> inGraph)
	{
		int size = inGraph.size();
		
		for(int i = 2; i < MIN_LENGTH_SIZE; i++)
		{
			Sample s = inGraph.get(size-i);
						
			if(s.highest * 1.02 > hotSpot.highest)
				return false;
		}
		
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

	public int getStartHoldDay() {
		// TODO Auto-generated method stub
		return START_HOLD;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return NAME;
	}
	
	public Map<Integer, String> getRuleParams() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getScanSteps(){
		return -1;
	}
	
	public boolean lazyScan(){
		return true;
	}


}
