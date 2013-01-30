package peligrosotrader.util.ta;

import java.util.List;
import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class GraphInfo extends TA {

	double m_std = 0;
	
	double totInc = 0;
	double avgInc = 0;
	
	double tot = 0;
	double avg = 0;
	
	public GraphInfo(List<Sample> inGraph)
	{
		double last = -1;
		for(Sample samp : inGraph){
			
			if(last == -1)
				last = samp.close;
			else{		
				totInc += 1 - (samp.close / last);
			}
			
			tot += samp.close;
		}
		
		avgInc = totInc / inGraph.size();
		avg = tot / inGraph.size();
		
		double totDev = 0;
		
		for(Sample samp : inGraph)
		{
			double dev = 1 - (samp.close / avg);
			totDev += dev; 
		}
		m_std = totDev / inGraph.size();
	}
	
	public double getAvg(){
		return avg;
	}
	
	public double getAvgInc(){
		return avgInc;
	}
	
	public double getSTD(){
		return m_std;
	}
}
