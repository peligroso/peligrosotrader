package peligrosotrader.ruleevaluation.rules;

import java.util.ArrayList;
import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class RuleTerm_RSI implements RuleTerm {
	
	double numbHoldDays = 0;
	double avgHoldDays = 0;
	double numbTrades = 0;
	
	static int PERIOD = 14;
	ArrayList<Double> gains = new ArrayList<Double>();
	ArrayList<Double> losses = new ArrayList<Double>();
	
	double prevAvgGain = 0;
	double prevAvgLoss = 0;
	
	double last = -1;
	
	int index = 0;

	public double leavePos(Vector<Sample> inGraph) {
		numbHoldDays++;
		
		if(numbHoldDays == 10){
			endTrade();
			return inGraph.lastElement().close;
		}
//		double rsi = calcRSI(new Double(inGraph.get(inGraph.size()-1).last));
//		if(rsi == -1)
//			return false;
//		if(rsi > 70){
//			endTrade();
//			return true;
//		}
		return -1;
	}

	public void resetGraph() {

		gains = new ArrayList<Double>();
		losses = new ArrayList<Double>();
		
		prevAvgGain = 0;
		prevAvgLoss = 0;
		
		last = -1;
		index = 0;
		
		numbHoldDays = 0;

	}
	public void finish(){
		System.out.println("Avrage hold period is: "+avgHoldDays);
	}
	
	public double takePos(Vector<Sample> inGraph) {
		
		double rsi = calcRSI(new Double(inGraph.get(inGraph.size()-1).last));
		if(rsi == -1)
			return -1;
		if( rsi < 30){
			return inGraph.lastElement().close;			
		}
		return -1;
	}
	
	private double calcRSI(double close){
		
		if(index == 0){
			last = close;
			index++;
			return -1;
		}
		else{
			double res = close - last;
			double thisGain = 0;
			double thisLoss = 0;
			//gainsLosses.add(res);
			if(res > 0){
				if(index <= PERIOD)
					gains.add(res);
				else
				thisGain = res;
			}
			if(res < 0){
				if(index <= PERIOD)
					losses.add(res);
				else{
					thisLoss = res;
					thisLoss*=-1;
				}
			}
			last = close;
			
			if(index >= PERIOD){
				
				double RSI;
				double avgGain;
				double avgLoss;
				if(index == PERIOD){
					avgGain = avg(gains);
					avgLoss = avg(losses);
					avgLoss *= -1;
					
					if(avgLoss == 0)
						RSI = 100;
					else
						RSI = avgGain/avgLoss;
				}															
				else{
					
					avgGain = (prevAvgGain*(PERIOD-1) + thisGain)/PERIOD;
					avgLoss = (prevAvgLoss*(PERIOD-1) + thisLoss)/ PERIOD;
					
					if(avgLoss == 0)
						RSI = 100;
					else
						RSI = avgGain / avgLoss;
				}

					RSI = 1+RSI;
					RSI = 100/RSI;
					RSI = 100 - RSI;
				
				prevAvgGain = avgGain;
				prevAvgLoss = avgLoss;
				
				index++;
				
				return RSI;				
			}
			index++;
			return -1;
		}
		
	}
	
	public static double avg(ArrayList<Double> inList){
		double tot = 0;
		for(double i : inList){
			tot += i;
		}
		return tot/new Double(PERIOD);
	}
	
	private void endTrade(){
		avgHoldDays = (avgHoldDays * numbTrades + (numbHoldDays))/++numbTrades;
		numbHoldDays = 0;
	}
	
	
	public static void main(String[] args){
		double[] graph = new double[]{46.125, 47.125, 46.4375, 46.9375, 44.9375, 44.2500, 44.6250, 45.7500, 47.8125, 47.5625, 47.0000, 44.5625, 46.3120, 47.6875, 46.6875, 45.6875, 43.0625, 43.5625, 44.8750, 43.6875};
		
		RuleTerm_RSI term = new RuleTerm_RSI();
		
		Vector<Sample> vec = new Vector<Sample>();
		
		for(double d : graph){
			vec.add(new Sample("0, "+d+", 0, 0, 0, 0, 44", 1, "dd"));
			term.takePos(vec);
		}
	}
	
	


}
