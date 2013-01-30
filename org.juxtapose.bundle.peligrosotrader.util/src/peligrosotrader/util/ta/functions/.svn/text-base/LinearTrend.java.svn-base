package peligrosotrader.util.ta.functions;

import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.TA;

public class LinearTrend extends TA {

	public static double[] generateFunction(Vector<Sample> inGraph){
		
		double start = inGraph.firstElement().close;
		double end = inGraph.lastElement().close;
		
		double avgInc = (end - start) / inGraph.size();
		
		double[] ret = new double[inGraph.size()];
		
		for(int i = 0; i < inGraph.size(); i++){
			ret[i] = start + (avgInc * i); 
		}
		
		return ret;
	}
}
