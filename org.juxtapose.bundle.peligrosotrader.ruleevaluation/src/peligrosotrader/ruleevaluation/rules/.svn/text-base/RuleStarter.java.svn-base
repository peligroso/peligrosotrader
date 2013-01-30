package peligrosotrader.ruleevaluation.rules;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import peligrosotrader.ruleevaluation.util.OmxFeed;
import peligrosotrader.ruleevaluation.util.Result;
import peligrosotrader.util.db.Sample;
import peligrosotrader.util.feeds.YahooHistory;


public class RuleStarter {
	
	RuleType m_testRule = null;
	Calendar m_start = null;
	Calendar m_end = null;

	public void startRuleEval(RuleType inRule, RuleTerm inRuleTerm, Calendar inStart, Calendar inEnd){
		
		m_testRule = inRule;
		m_start = inStart;
		m_end = inEnd;
		ArrayList<String> omxTickers = OmxFeed.getOmxTickers();
		Vector<Vector<Sample>> sampleMatrix = getSampleMatrix(omxTickers);
		
		Result result = m_testRule.evaluate(sampleMatrix, inRuleTerm);
		
		result.printResult();
	}
	
	public Vector<Vector<Sample>> getSampleMatrix(ArrayList<String> inTickers){
	
		Vector<Vector<Sample>> ret = new Vector<Vector<Sample>>();
		for(String ticker : inTickers){
			try{
				System.out.println("fetching data for stock "+ticker );
				Vector<Sample> graph = YahooHistory.getHistoryGraph(ticker, m_start, m_end);
				ret.add(graph);
			}catch(Exception e){System.out.println(ticker+" not found");}
		}
		System.out.println("Done reading graphs into memory");
		return ret;
	}
	
	public void setCals(Calendar inStart, Calendar inEnd){
		m_start = inStart;
		m_end = inEnd;
	}
}
