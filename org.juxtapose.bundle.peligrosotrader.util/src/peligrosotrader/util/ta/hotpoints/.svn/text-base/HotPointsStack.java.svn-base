package peligrosotrader.util.ta.hotpoints;

import java.util.ArrayList;
import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.db.Sample.Value;
import peligrosotrader.util.ta.EquityKey;
import peligrosotrader.util.ta.TA;

public class HotPointsStack extends TA {
	
	/**types**/
	public static final int SUPPORT = 1;
	public static final int RESISTANCE = 2;
	
	
	
	public static final int START_PERIOD = 10;
	
	private int m_rangeIndex = 0;
	
	public Vector<Sample> m_graph;
	
	public ArrayList<HotPoint> m_hotPoints = new ArrayList<HotPoint>();

	private int m_type;
	
	private EquityKey m_ek;
	
	private boolean m_checkVolume;
	private Value  m_valueType;
	private int m_range = 5;
	
	/**
	 * 
	 * @param inGraph
	 */
	public HotPointsStack(Vector<Sample> inGraph, int inType, boolean inCheckVolume, int inRange)
	{
		Sample bestInRange = null;
		int bestInRangeIndex = 0;
		
		m_graph = inGraph;
		
		m_type = inType;
		
		m_checkVolume = inCheckVolume;
		
		m_ek = new EquityKey(inGraph);
		
		m_range = inRange;
		
		if(m_type == SUPPORT)
			m_valueType = Value.eLowest;
		else
			m_valueType = Value.eHighest;
		
		for(int i = 0; i < inGraph.size(); i++)
		{
			Sample samp = inGraph.get(i);
			
			double value = 0;
			value = samp.getValue(m_valueType);
			
			checkForBreakings (value);
			
			if(m_type == RESISTANCE){
				if (bestInRange == null || value > bestInRange.getValue(m_valueType))
				{
					bestInRange = samp;
					bestInRangeIndex = i;
					m_rangeIndex = 0;
				}
			}else{
				if (bestInRange == null || value < bestInRange.getValue(m_valueType))
				{
					bestInRange = samp;
					bestInRangeIndex = i;
					m_rangeIndex = 0;
				}
			}
			
			if(++m_rangeIndex > m_range){
				
				if(bestInRangeIndex > m_range && bestInRangeIndex < m_graph.size()-(m_range/2) && bestInRangeIndex > 10)
				{
					boolean okToAdd = true;
					
					for(int x = bestInRangeIndex; x > (bestInRangeIndex - m_range); x--){
					
						Sample backTrack = inGraph.get(x);
						if(m_type == RESISTANCE){
							if(backTrack.getValue(m_valueType) > bestInRange.getValue(m_valueType)){
								okToAdd = false;
								break;
							}
						} else{
							if(backTrack.getValue(m_valueType) < bestInRange.getValue(m_valueType)){
								okToAdd = false;
								break;
							}
						}
					}
					
					if(okToAdd){
						if(m_checkVolume){
							if(hasVolume(bestInRange))
								m_hotPoints.add(new HotPoint(bestInRange, false, m_valueType));
						}
						else
							m_hotPoints.add(new HotPoint(bestInRange, false, m_valueType));
					}
				}
				else if(bestInRangeIndex < m_graph.size()-(m_range/2) && bestInRangeIndex > 10)
					if(m_checkVolume){
						if(hasVolume(bestInRange))
							m_hotPoints.add(new HotPoint(bestInRange, false, m_valueType));
					}
					else
						m_hotPoints.add(new HotPoint(bestInRange, false, m_valueType));

				bestInRange = null;
				m_rangeIndex = 0;
			}
			
		}
		
		//Calculate strength for points
		for(Sample samp :  inGraph)
		{
			for(HotPoint hp : m_hotPoints){
				hp.calcSupport(samp.getValue(m_valueType));
			}
		}
		
//		for(HotPoint hp : m_hotPoints){
//			System.out.println(hp.m_samp.close+"  :  "+hp.m_samp.date+"  :  "+hp.m_strength);
//		}
		
	}
	
	public boolean hasVolume(Sample inSamp)
	{
		double avgVol = m_ek.getAvg(EquityKey.DAY_STOCK_VOLUME);
		return inSamp.volume > avgVol * 2;
	}
	
	private void checkForBreakings(double inClose)
	{
		for(HotPoint hp : m_hotPoints){
			if(m_type == RESISTANCE){
				if(inClose * 0.98 > hp.m_samp.getValue(m_valueType)){
					hp.m_isBrooken = true;
				}
			} else{
				if(inClose * 1.02 < hp.m_samp.getValue(m_valueType)){
					hp.m_isBrooken = true;
				}
			}
		}
	}
	
//	public boolean highest(List<Sample> inGraph, Sample test)
//	{
//		for(Sample s : inGraph){
//			if(test.close < s.close)
//				return false;
//		}
//		return true;
//	}
	
	public Vector<Vector<Sample>> getLines(int inStrengthFactor)
	{
		Vector<Vector<Sample>> ret = new Vector<Vector<Sample>>();
		
		for(HotPoint hp : m_hotPoints)
		{
			if(hp.m_strength >= inStrengthFactor && !hp.m_isBrooken){
				Vector<Sample> v = new Vector<Sample>();

				Sample fSamp = new Sample();
				fSamp.close = hp.m_samp.getValue(m_valueType);
				fSamp.last = Double.toString(hp.m_samp.getValue(m_valueType));
				fSamp.date = m_graph.firstElement().date;

				Sample lSamp = new Sample();
				lSamp.close = hp.m_samp.getValue(m_valueType);
				lSamp.last = Double.toString(hp.m_samp.getValue(m_valueType));
				lSamp.date = m_graph.lastElement().date;

				v.add(fSamp);
				v.add(lSamp);

				ret.add(v);
			}
		}
		
		return ret;
	}
	

}
