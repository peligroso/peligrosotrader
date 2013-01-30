package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.EquityKey;

public class RuleTerm_HighVolume implements RuleTerm {

	EquityKey ek;
	boolean m_first = true;
	
	int m_holding = 0;
	
	public void finish() {
		// TODO Auto-generated method stub
		
	}

	public double leavePos(Vector<Sample> inGraph) {
		// TODO Auto-generated method stub
		if(m_holding++ ==3){
			m_holding = 0;
			return inGraph.lastElement().close;
		}
		return -1;
	}

	public void resetGraph() {
		m_first = true;
		m_holding = 0;
	}

	public double takePos(Vector<Sample> inGraph) {
		if(m_first){
			ek = new EquityKey(inGraph);
			m_first = false;
			
			return -1;
			
		}
		else
		{
			Sample lastSamp = inGraph.lastElement();
			double avgVol = ek.getAvg(EquityKey.DAY_STOCK_VOLUME);
			ek.addInstance(lastSamp);
			if( avgVol * 6 < lastSamp.volume){
				System.out.println("avgVol is "+avgVol+", lastSamp vol is "+lastSamp.volume);
				return lastSamp.close; 
			}
		}
		return -1;
	}
	
	

}
