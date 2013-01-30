package peligrosotrader.util.ta;

import java.util.List;
import java.util.Vector;
import peligrosotrader.util.db.Sample;

public class Predict extends TA {
	
	public static int STRAY_MARGIN = 5;
	
	public class CloseSample{
		public double corr;
		public String ticker;
		public String entity;
		public String startDate;
		public String endDate;
		public List<Sample> graph = null;
		public List<Sample> followUp = null;
		
		public double calcResult(){
			Sample lastTestSamp = graph.get(graph.size()-1);
			Sample lastFollowUpSamp = followUp.get(followUp.size()-1);
			double res = (1 - (new Double(lastTestSamp.last) / new Double(lastFollowUpSamp.last)))*100;
			int intRes = new Double(res*100).intValue();
			res = new Double(intRes) / 100;

			return res;
		}
	}
	
	Sample best1=null, best2=null, best3=null;
	double bestCorr = -2;
	int bestStart = 0;
	List<CloseSample> bestCorrGraphs = new Vector<CloseSample>();
	CloseSample latest = null;
	int latestIndex = -STRAY_MARGIN-1;
	
	//acuall result
	double outfall = 0;

	public Predict(List<Sample> sampleGraph, List<Sample> testGraph, int followUpLength, double inMinCorrValue){
		
		String ticker = null;
		if(sampleGraph.size() > 0)
			ticker = sampleGraph.get(0).ticker;
		
		for(int i = 0; i < sampleGraph.size()-(testGraph.size()+1+followUpLength); i++){
			
			List<Sample> reff = sampleGraph.subList(i, i+testGraph.size());
			double[] reffArr = Sample.extractValues(reff);
			Correlation corr = new Correlation(reffArr, Sample.extractValues(testGraph));
			
			double corrValue = corr.getCorrAll();
			if(corrValue > inMinCorrValue){
				if(corrValue > bestCorr){
					CloseSample cs = new CloseSample();
					cs.graph = reff;
					cs.followUp = sampleGraph.subList(i+testGraph.size(), i+testGraph.size()+followUpLength);
					cs.corr = corrValue;
					cs.startDate = reff.get(0).date.toString();
					cs.endDate = reff.get(reff.size()-1).date.toString();
					cs.ticker = ticker;
					if(i > latestIndex + STRAY_MARGIN){
						if(latest != null)
							bestCorrGraphs.add(latest);
						latest = cs;
						latestIndex = i;
							
					}
				}
					
			}
		}
		if(latest != null)
			bestCorrGraphs.add(latest);
		//System.out.println(calcPrediction());
		
	}
	
	public List<CloseSample> getPredictionGrafData(){

		return bestCorrGraphs;
	}
	
	public double calcPrediction(){
//		ArrayList<Double> sampRes = new ArrayList<Double>();
//		double tot = 0;
//		for(CloseSample samp : bestCorrGraphs){
//			sampRes.add(samp.calcResult());
//			tot += samp.calcResult();
//		}
//		
//		return tot/sampRes.size();
		
		double bestPred = -10000;
		for(CloseSample samp : bestCorrGraphs){
			double sampRes = samp.calcResult();
			if(sampRes > bestPred){
				bestPred = sampRes;
			}
		}
		
		return bestPred;
	}
	
	public void setOutfall(double inInt){
		outfall = inInt;
	}
	public double getOutfall(){
		return outfall;
	}
}
