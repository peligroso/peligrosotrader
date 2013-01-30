package peligrosotrader.ruleevaluation.rules;

import java.util.List;
import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.EquityKey;
import peligrosotrader.util.ta.GraphInfo;
import peligrosotrader.util.ta.HighLowChannel;

public class RuleTerm_Scalp implements RuleTerm {

	static int m_days = 10;
	
	Double m_order = null;
	
	
	public void finish() {
		// TODO Auto-generated method stub

	}

	public double leavePos(Vector<Sample> inGraph) {
		
		
		Sample lastSamp = inGraph.lastElement();
		
		return lastSamp.close;
		
//		GraphInfo highInfo = new GraphInfo(m_hlC.getHighBand());
//		GraphInfo lowInfo = new GraphInfo(m_hlC.getLowBand());
//		
//		return goodSpan(highInfo, lowInfo) ? -1 : lastSamp.close;
	}

	public void resetGraph() {

	}

	public double takePos(Vector<Sample> inGraph) {
		
		
		List<Sample> subGraph = inGraph.subList(inGraph.size()-11 , inGraph.size()-1);
		
		EquityKey ek = new EquityKey(subGraph);
		double avgMove = ek.getAvg(EquityKey.DAY_MOVE);
		double avgTurnOv = ek.getAvg(EquityKey.DAY_VOLUME);
		
		if(avgMove > 0.02 && avgTurnOv > 1000000)
			return inGraph.lastElement().close;
			
		return -1;
	}
	
	public boolean goodSpan(GraphInfo highInfo, GraphInfo lowInfo)
	{
		if(highInfo.getAvgInc() < 0.004 && highInfo.getAvgInc() > -0.002 &&
				lowInfo.getAvgInc() < 0.004 && lowInfo.getAvgInc() > -0.002 &&
				highInfo.getAvg() / lowInfo.getAvg() > 0.01){
			
			return true;
		}
		else
			return false;
	}

}
