package peligrosotrader.util.ta.functions;

import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class QPipe {
	
	public static double[] generateFunction(Vector<Sample> inGraph, int inStartAngle, int inEndAngle){
				
		double start = new Double(inGraph.get(0).last);
		double end = new Double(inGraph.get(inGraph.size()-1).last);
		int size = inGraph.size();
		
		double[] function = new double[size];
//		if(start >= end)
//			return null;
		
		double diff = end -start;
		double angleDiff = inEndAngle - inStartAngle;
		double sizeExAn = size-2;
		double angleIncrease = angleDiff/ sizeExAn;
		
		double avgIncrease = diff/(size-1);
		
		double angleInPips = avgIncrease / 45;
		
		double increase = angleIncrease * angleInPips;
		
		double last = start;
		double angle = inStartAngle;
		for(int i = 0; i < size; i++){
			function[i] = last;
			
			angle += angleIncrease;
			increase = angle * angleInPips;
			last += increase;
		}
		
		return function;
	}

}
