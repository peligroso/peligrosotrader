package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class RuleTerm_CS implements RuleTerm{
	
	static int DAYS = 5;
	static int HOLD_DAYS = 15;
	
	int holding = 0;
	
	double standardSpan = 0;
	double standardVolume = 0;
	
	int numAnalyzed = 0;

	public void finish() {
		
	}

	public double leavePos(Vector<Sample> inGraph) {
		analyzeSample(inGraph.get(inGraph.size()-1));
		
		holding++;
		if(holding >= 15){
			holding = 0;
			return inGraph.lastElement().close;
		}
		
		return -1;
	}

	public void resetGraph() {
		
		numAnalyzed = 0;
		standardSpan = 0;
		standardVolume = 0;
		holding = 0;
		
	}

	public double takePos(Vector<Sample> inGraph) {
		
		analyzeSample(inGraph.get(inGraph.size()-1));
		if(inGraph.size() < DAYS)
			return -1;
		
		Sample lastSamp = inGraph.get(inGraph.size()-1);
		Sample day4 = inGraph.get(inGraph.size()-2);
		Sample day1 = inGraph.get(inGraph.size()-5);
		
		double percentSpanDay1 = (day1.highest / day1.lowest) -1;
		
		if(percentSpanDay1 > (standardSpan*2)){
			
			if(day4.volume > standardVolume){
				
				double span = lastSamp.highest - lastSamp.lowest;
				double block = span/3;
				
				int paramOpen = getBlock(lastSamp.lowest, block, lastSamp.open);
				
				if(paramOpen == 2)
					return inGraph.lastElement().close;
			}
		}
		
		
		return -1;
	}
	
	private void analyzeSample(Sample inSample){
		
		double span = (inSample.highest / inSample.lowest)-1;
		standardSpan = ((standardSpan * numAnalyzed)+span) / (numAnalyzed+1);
		
		double volume = inSample.volume;
		standardVolume = ((standardVolume *numAnalyzed)+volume) / (numAnalyzed+1);
		
		numAnalyzed++;
	}
	
	private static int getBlock(double inLow, double inBlock, double inQuote){

		if(inQuote < inLow+inBlock)
			return 3;
		else if(inQuote < inLow+(inBlock*2))
			return 2;
		else
			return 1;
	}

}
