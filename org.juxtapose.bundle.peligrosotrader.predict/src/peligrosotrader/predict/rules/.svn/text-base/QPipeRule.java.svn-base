package peligrosotrader.predict.rules;

import java.util.Map;
import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.Correlation;
import peligrosotrader.util.ta.functions.QPipe;

public class QPipeRule extends Rule{
	

	static int ID = 107;
	static String NAME = "Q-pipe";
	
	static int MIN_TREND_DAYS = 60;
	
	static int START_HOLD = 10;
	static int END_HOLD = 100;
	static int INC_HOLD = 10;
	
	int trendKoff = 0;

	public void finish() {
		// TODO Auto-generated method stub
		
	}

	public void resetGraph() {
		trendKoff = 0;
	}

	public int signal(Vector<Sample> inGraph) {
		if(inGraph.size() < MIN_TREND_DAYS)
			return 0;
		
		Vector<Sample> toTest = new Vector<Sample>();
		for(int i = inGraph.size()-1; i > 10; i-=5){
			toTest.add(0, inGraph.get(i));
			if(toTest.size() < MIN_TREND_DAYS)
				continue;
			
			if(new Double(toTest.get(0).last) >= new Double(toTest.get(toTest.size()-1).last))
				return 0;
			double[] funcVec = QPipe.generateFunction(toTest, 0, 90);
			Correlation corr = new Correlation(funcVec, Sample.extractValues(toTest));
			if(corr.getCorrAll() > 0.98){
				System.out.println("working graph is: "+toTest.get(0).date+" to "+toTest.get(toTest.size()-1).date);
				trendKoff = toTest.size()/10;
				return 1;
			}
		}
		return 0;
	}
	


	public int getID() {
		
		return ID;
	}

	public int getEndHoldDay() {
		// TODO Auto-generated method stub
		return END_HOLD;
	}

	public int getHoldDayIncrease() {
		// TODO Auto-generated method stub
		return INC_HOLD;
	}

	public int getStartHoldDay() {
		// TODO Auto-generated method stub
		return START_HOLD;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return NAME;
	}
	
	public Map<Integer, String> getRuleParams() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getScanSteps(){
		return 1;
	}
	public boolean lazyScan(){
		return true;
	}
}
