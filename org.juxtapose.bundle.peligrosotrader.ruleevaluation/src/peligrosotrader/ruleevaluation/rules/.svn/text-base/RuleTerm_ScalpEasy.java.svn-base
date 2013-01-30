package peligrosotrader.ruleevaluation.rules;

import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.GraphInfo;
import peligrosotrader.util.ta.HighLowChannel;

public class RuleTerm_ScalpEasy implements RuleTerm {

	static int m_days = 10;
	
	HighLowChannel m_hlC = null;
	
	Double m_order = null;
	
	
	public void finish() {
		// TODO Auto-generated method stub

	}

	public double leavePos(Vector<Sample> inGraph) {
		Sample lastSamp = inGraph.lastElement();
		
		if(m_order != null){
			
			if(lastSamp.highest <= m_order){
				double ret = m_order;
				m_order = null;
				return ret;
				
			}
		}
						
		m_order = m_hlC.getHighBand().lastElement().close;
		return -1;
	}

	public void resetGraph() {
		m_hlC = null;
		m_order = null;

	}

	public double takePos(Vector<Sample> inGraph) {
		
		if(inGraph.size() < m_days * 2){
			return -1;
		}
		else if(m_hlC == null){
			m_hlC = new HighLowChannel(inGraph, m_days);
		}
		else
		{
			Sample lastSamp = inGraph.lastElement();
			
			
			m_hlC.addToGraph(lastSamp);
			
			if(m_order != null){
				
				if(lastSamp.lowest >= m_order){
					double ret = m_order;
					m_order = m_hlC.getHighBand().lastElement().close;
					return ret;
					
				}
			}
							
			m_order = m_hlC.getLowBand().lastElement().close;
			
	
		}
		return -1;
	}

}
