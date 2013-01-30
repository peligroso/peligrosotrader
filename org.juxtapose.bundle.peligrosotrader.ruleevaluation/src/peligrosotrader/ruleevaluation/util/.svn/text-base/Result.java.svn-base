package peligrosotrader.ruleevaluation.util;

public class Result {

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
	
	public double totHoldingDays = 0;
	
	public double best = 1000000;
	public double worst = 1000000;
	
	
	public boolean trendedBias = true;
	
	public void addRes(double res, double inHoldingDays){
		
		
//		if(res < 100 && res > -99){
			if(res > 0){
				positives++;
				totPos += res;
				if(totPos != 0 && positives != 0)
					avgPercentPos = totPos/positives; 
			}
			else{
				negatives++;
				totNeg += res;
				if(totPos != 0 && positives != 0)
					avgPercentNeg = totNeg/negatives; 
			}

			totAll += res;
			totHoldingDays+= inHoldingDays;
			
			if(totAll != 0 && (positives+negatives) != 0)
				avgPercent = totAll/(positives+negatives);
			
			if(best == 1000000)
				best = res;
			if(worst == 1000000)
				worst = res;
			
			if(res > best)
				best = res;
			if(res < worst)
				worst = res;
//		}
//		else
//			System.out.println("wrong");
	}
	
	
	public void setFrequenzy(double inFreqG, double inFreqA){
		signalsPerYearGraph = inFreqG;
		signalsPerYearAll = inFreqA;
	}
	
	public double getAvgHolding(){
		return totHoldingDays / (positives+negatives);
	}
	
	public double getAvgPerDay(){
		return avgPercent / getAvgHolding();
	}
	
	public void printResult(){
		
		System.out.println("****************Result**************");
		System.out.println("number of positives was:" +positives);
		System.out.println("number of negatives was:" +negatives);
		System.out.println("average return was:" +avgPercent+"%");
		System.out.println("average holding period was:" +getAvgHolding());
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

	}
}
