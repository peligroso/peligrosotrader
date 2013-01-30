package peligrosotrader.ruleevaluation.mining;

import java.io.FileWriter;
import java.util.Calendar;
import java.util.Vector;

import peligrosotrader.ruleevaluation.rules.RuleEval_inOut;
import peligrosotrader.ruleevaluation.rules.RuleStarter;
import peligrosotrader.ruleevaluation.rules.RuleTerm;
import peligrosotrader.ruleevaluation.rules.RuleTerm_QPipe;
import peligrosotrader.ruleevaluation.util.OmxFeed;
import peligrosotrader.ruleevaluation.util.Result;
import peligrosotrader.util.db.Sample;



public class RuleMining_QPipe implements RuleMining{
	
	int startDay = 60;
	int dayIncrease = 10;
	int endDay = 400;
	
	int dayHold = 10;
	int dayHoldIncrease = 10;
	int dayHoldTo = 400;
	
	double bestAvg = 0;
	
	Calendar startCal;
	Calendar endCal;
	
	Vector<ResultInfo> resses = new Vector<ResultInfo>();
	
	public RuleMining_QPipe(Calendar inStart, Calendar inEnd){
		startCal = inStart;
		endCal = inEnd;
	}

	public void startMining() {
		RuleStarter rs = new RuleStarter();
		rs.setCals(startCal, endCal);
		Vector<Vector<Sample>> sampleData = rs.getSampleMatrix(OmxFeed.getOmxTickers());
		
		RuleEval_inOut ruleEval = new RuleEval_inOut();
		
		while(startDay < endDay){
			while(dayHold < dayHoldTo){
				System.out.println("mining for "+startDay+", "+dayHold);
				RuleTerm_QPipe rule = new RuleTerm_QPipe();
				rule.setHold(dayHold);
				rule.setMinTrendDays(startDay);
				
				Result res = ruleEval.evaluate(sampleData, rule);
				ResultInfo resInfo = new ResultInfo();
				resInfo.res = res;
//				resInfo.holdDays = dayHold;
//				resInfo.minTrend = startDay;
				
				resses.add(resInfo);
				
				if(res.avgPercent > bestAvg){
					bestAvg = res.avgPercent;
					System.out.println("best avg is now: "+bestAvg+" on minTrend: "+startDay+" and hold: "+dayHold);
				}
				System.out.println("best avg is now: "+bestAvg+" on minTrend: "+startDay+" and hold: "+dayHold);
				
				dayHold+=dayHoldIncrease;
				
				if(dayHold > startDay)
					break;
			}
			dayHold = 10;
			startDay+=dayIncrease;
		}
		
		try{
			FileWriter file = new FileWriter("C:\\qPipe_result.txt");

			for(ResultInfo resInfo : resses){
				file.write("*****************************\n");
//				file.write("min trend: "+resInfo.minTrend+"\n");
//				file.write("hold for: "+resInfo.holdDays+"\n");

				Result res = resInfo.res;

				file.write("avrege: "+res.avgPercent+" positives: "+res.positives+", negatives: "+res.negatives);
				file.write("\n\n");
			}
			file.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		for(ResultInfo resInfo : resses){
			System.out.println("*****************************");
//			System.out.println("min trend: "+resInfo.minTrend);
//			System.out.println("hold for: "+resInfo.holdDays);
			
			Result res = resInfo.res;
			
			System.out.println("avrege: "+res.avgPercent+" positives: "+res.positives+", negatives: "+res.negatives);
			System.out.println();
		}
		
	}
	public static void main(String[] args){
		System.out.println("ok");
		try{
			FileWriter file = new FileWriter("C:\\qPipe_result.txt");
			file.write("tjoo\ntjo");
			file.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
