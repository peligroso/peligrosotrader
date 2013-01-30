package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class RuleTerm_MAVar implements RuleTerm{
	
	public static int MA = 20;
	private int m_underCount = 0;
	private static int daysUnder = 20;
	
	private Vector<Double> m_maVec = new Vector<Double>();
	//private double[] m_avg = new double[MA]; 
	
	public void resetGraph(){
		m_maVec = new Vector<Double>();
		m_underCount = 0;
	}
	
	public double leavePos(Vector<Sample> inGraph) {
		if(addToMA(inGraph)){
			Sample act = inGraph.get(inGraph.size()-1);
			double last = new Double(act.last);
			if(last < m_maVec.get(inGraph.size()-1)){
				m_underCount = 1;
				return inGraph.lastElement().close;
			}
			return -1;
		}
		else
			return -1;
	}

	public double takePos(Vector<Sample> inGraph) {
		
		if(addToMA(inGraph)){
			Sample act = inGraph.get(inGraph.size()-1);
			double last = new Double(act.last);
			if(m_underCount > daysUnder && last > m_maVec.get(inGraph.size()-1)){
				m_underCount = 0;
				return inGraph.lastElement().close;
			}else{
				m_underCount++;
			}
			return -1;
		}else
			return -1;
	}
	
	private boolean addToMA(Vector<Sample> inGraph){
		
		if(inGraph.size() < MA){
			m_maVec.add(0d);
			return false;
		}
		else{
			double all = 0;
			int size = inGraph.size();
			for(int i = 0; i < MA; i++){
				double last = new Double(inGraph.get(size-1-i).last);
				all+=last;
			}
			m_maVec.add(all/MA);
			return true;
		}
		
	}
	
	public void finish(){
		
	}

}
