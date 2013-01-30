package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;

import peligrosotrader.ruleevaluation.util.FrequenzyTracker;
import peligrosotrader.ruleevaluation.util.Result;
import peligrosotrader.util.db.Sample;

public class RuleEval_intraDayScalp implements RuleType{
	
	boolean m_posisionized = false;
	double m_enter;
	double m_exit;
	
	double holding = 0;
	
	RuleTerm m_ruleTerm;
	
	Result m_res = new Result();
	FrequenzyTracker m_freq = new FrequenzyTracker();

	public Result evaluate(Vector<Vector<Sample>> inGraphs, RuleTerm inRuleTerm) {
		// TODO Auto-generated method stub
		m_ruleTerm = inRuleTerm;
		
		for(Vector<Sample> graph : inGraphs){
			try{
				System.gc();
				System.out.println("evaluating "+graph.get(0).ticker);
				m_freq.startNew(graph.get(0).date, graph.get(graph.size()-1).date);
				evaluateGraph(graph);
				m_freq.finishGraph();
				m_posisionized = false;
				m_ruleTerm.resetGraph();
			}catch(Throwable t){
				t.printStackTrace();
				System.exit(0);
			}
		}
		m_ruleTerm.finish();
		m_res.setFrequenzy(m_freq.getFrequenzy(), m_freq.getFrequenzyAll());
		return m_res;
	}
	
	private void evaluateGraph(Vector<Sample> inGraph){
		
		Vector<Sample> tryVec = new Vector<Sample>();
		for(Sample samp : inGraph){
			tryVec.add(samp);
			if(m_posisionized){
				holding++;
				if(m_ruleTerm.leavePos(tryVec) != -1){
					System.out.println("exit trade at for ticker "+samp.ticker+" at: "+samp.last+"  on "+samp.date+"  holding was: "+holding);
					m_exit = new Double(samp.last);
					calcResult();
					m_posisionized = false;					
				}
			}
			else{
				if(m_ruleTerm.takePos(tryVec) != -1){
					holding = 0;
					System.out.println("enter trade at for ticker "+samp.ticker+" at: "+samp.last+"  on "+samp.date);
					m_enter = new Double(samp.last);
					m_posisionized = true;
					m_freq.track();
				}
			}
		}
	}
	
	private void calcResult(){
		
		double diff = m_exit/m_enter;
		double min = diff-1;
		double res = min * 100;
		m_res.addRes(res, holding);
		System.out.println("result was :"+res);
	}

}

