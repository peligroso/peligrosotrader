package peligrosotrader.util.ta.hotpoints;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.db.Sample.Value;

public class HotPoint {

	public Sample m_samp;
	public boolean m_isBrooken;
	public double m_strength = 0;
	
	public int inAreaCount = 0;
	
	public HotPoint(Sample inSamp, boolean inBrooken, Value  inValueType)
	{
		m_samp = new Sample();
		m_samp.setPriceValues(inSamp.getValue(inValueType));
//		m_samp = inSamp;
		m_isBrooken = inBrooken;
	}
	
	public void calcSupport(double inPrice){
		
		double dev = m_samp.close * 0.02;

		if(inPrice < m_samp.close-dev){
//			inAreaGoTrade = false;
			
			m_strength = 0;
			inAreaCount = 0;
//			buildPeriod = 0;
		}
		else if(inPrice > m_samp.close+dev ){
//			m_daysOutSide++;
//			inAreaGoTrade = false;
			inAreaCount = 0;
			return;
		}
		else{

			inAreaCount++;
			double diff = inPrice - m_samp.close;
			
			double res = diff/dev;
			if(res < 0){
				res *= -1.5;
			}
			double supRes = 1-res;
			m_strength += (supRes/inAreaCount);
		}
	}
}
