package peligrosotrader.ruleevaluation.mining;

import java.io.FileWriter;
import java.util.Calendar;
import java.util.Vector;

import peligrosotrader.ruleevaluation.rules.RuleEval_inOut;
import peligrosotrader.ruleevaluation.rules.RuleStarter;
import peligrosotrader.ruleevaluation.util.OmxFeed;
import peligrosotrader.ruleevaluation.util.Result;
import peligrosotrader.util.db.Sample;



public class RuleMiningStarter implements RuleMining{
	
	double bestAvgPD = 0;
	double bestFP = 0;
	double bestSP = 0;
	
	double worstAvgPD = 100000;
	double worstFP = 0;
	double worstSP = 0;
	
	Calendar startCal;
	Calendar endCal;
	MiningRule m_miningRule;
	
	Vector<ResultInfo> resses = new Vector<ResultInfo>();
	
	public RuleMiningStarter(Calendar inStart, Calendar inEnd, MiningRule inMiningRule){
		startCal = inStart;
		endCal = inEnd;
		m_miningRule = inMiningRule;
	}

	public void startMining() {
		RuleStarter rs = new RuleStarter();
		rs.setCals(startCal, endCal);
		Vector<Vector<Sample>> sampleData = rs.getSampleMatrix(OmxFeed.getOmxTickers());
		
		
		
		while(m_miningRule.changeFirstParam()){
			while(m_miningRule.changeSecParam()){

				Result res = new Result();
				RuleEval_inOut ruleEval = new RuleEval_inOut();
				
				Result newRes = ruleEval.evaluate(sampleData, m_miningRule);
				res.avgPercent = newRes.avgPercent;
				res.avgPercentNeg = newRes.avgPercentNeg;
				res.avgPercentPos = newRes.avgPercentPos;
				res.best = newRes.best;
				res.negatives = newRes.negatives;
				res.positives = newRes.positives;
				res.signalsPerYearAll = newRes.signalsPerYearAll;
				res.signalsPerYearGraph = newRes.signalsPerYearGraph;
				res.totHoldingDays = newRes.totHoldingDays;
				
				ResultInfo resInfo = new ResultInfo();
				resInfo.res = res;
				resInfo.param1 = m_miningRule.getFirstParam();
				resInfo.param2 = m_miningRule.getSecParam();
				
				resses.add(resInfo);
				
				if(res.getAvgPerDay() > bestAvgPD){
					bestAvgPD = res.getAvgPerDay();
					bestFP = resInfo.param1;
					bestSP = resInfo.param2;
				}
				
				if(res.getAvgPerDay() < worstAvgPD){
					worstAvgPD = res.getAvgPerDay();
					worstFP = resInfo.param1;
					worstSP = resInfo.param2;
				}
			}
		}
		
		try{
			FileWriter file = new FileWriter("C:\\mining_result.txt");

			for(ResultInfo resInfo : resses){
				file.write("*****************************\n");
				file.write("param 1 was: "+resInfo.param1+"\n");
				file.write("param 2 was: "+resInfo.param2+"\n");

				Result res = resInfo.res;

				file.write("avrege: "+res.avgPercent+" positives: "+res.positives+", negatives: "+res.negatives);
				file.write("avg holding was: "+res.getAvgHolding()+"\n");
				file.write("avg per day was :"+res.getAvgPerDay()+"\n");
				file.write("\n\n");
			}
			file.write("Best was param1: "+bestFP+" and param2: "+bestSP+" with avg per day: "+bestAvgPD);
			file.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		for(ResultInfo resInfo : resses){
			System.out.println("*****************************");
			System.out.println("param1 was: "+resInfo.param1);
			System.out.println("param2 was for: "+resInfo.param2);
			
			Result res = resInfo.res;
			
			System.out.println("avrege: "+res.avgPercent+" positives: "+res.positives+", negatives: "+res.negatives);
			System.out.println("avg holding was: "+res.getAvgHolding());
			System.out.println("avg per day was :"+res.getAvgPerDay());
			System.out.println();
		}
		System.out.println("Best was param1: "+bestFP+" and param2: "+bestSP+" with avg per day: "+bestAvgPD);
		System.out.println("Worst was param1: "+worstFP+" and param2: "+worstSP+" with avg per day: "+worstAvgPD);

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

