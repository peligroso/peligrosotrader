package peligrosotrader.util.statistics;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Stats{
	
	public int positives = 0;
	public int negatives = 0;
	
	public double avgPercent = 0;
	public double avgPercentPos = 0;
	public double avgPercentNeg = 0;
	
	public double signalsPerYearGraph = 0;
	public double signalsPerYearAll = 0;
	
	public double totAll = 0;
	public double totPos = 0;
	public double totNeg = 0;
	
	public double m_holdingDays = 0;
	
	public double best = 1000000;
	public double worst = 1000000;
	
	public Map<Double, Integer> m_normDist = new TreeMap<Double, Integer>();
	
	public void printResult(){
		
		System.out.println("****************Result**************");
		System.out.println("number of positives was:" +positives);
		System.out.println("number of negatives was:" +negatives);
		System.out.println("average return was:" +avgPercent+"%");
		System.out.println("average holding period was:" +m_holdingDays);
		System.out.println("average return per day was:" +getAvgPerDay());
		System.out.println("average return of positives was:" +avgPercentPos+"%");
		System.out.println("average return of negatives was:" +avgPercentNeg+"%");
		System.out.println("best result was: "+best+"%");
		System.out.println("worst result was: "+worst+"%");
		System.out.println("the rule generated:" +signalsPerYearGraph+" signals per year and Graph");
		System.out.println("the rule generated:" +signalsPerYearAll+" signals per year from testSample");
		System.out.println();
		System.out.println("Expected yearly return per comodity would be: "+avgPercent*signalsPerYearGraph+"%");
		System.out.println("Expected yearly returnon testsample would be: "+avgPercent*signalsPerYearAll+"%");
		
		StringBuffer sb = new StringBuffer("Normal Distribution: ");
		for(Double perc : m_normDist.keySet()){
			sb.append(perc+":"+m_normDist.get(perc)+", ");
		}
		System.out.println(sb);
		System.out.println();

	}
	
	public double getAvgPerDay(){
		return avgPercent / m_holdingDays;
	}
	
	public void initiateDist(ArrayList<Double> inSamples){
		
		double allSpan = best - worst;
		double span = allSpan / 20;
		for(double i = worst; i < best; i+=span){
			
			m_normDist.put(i, 0);
		}
		
		for(Double d : inSamples)
		{
			addToDist(d);
		}
	}
	
	/**
	 * 
	 * @param res
	 */
	private void addToDist(double res)
	{
		Double place = null;
		for(Double percent : m_normDist.keySet())
		{
			place = percent;
			if( res < percent){
				break;
			}
		}
		
		Integer count = m_normDist.get(place);
		if(count != null){
			count++;
			m_normDist.put(place, count);
		}
	}
}
