package peligrosotrader.util.ta;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import peligrosotrader.util.db.Sample;

/**
 * 
 * @author Peligroso
 *
 */
public class EquityKey {
	
	/**
	 * 
	 * @author Peligroso
	 *
	 */
	class Key{
		
		final static int HIND_SIGHT = 60;
		
		double m_total = 0;
		int m_instances = 0;
		
		public void calculateInstance(double inVal){
			if(m_instances >= HIND_SIGHT){
				m_total -= getAvg();
				m_total += inVal;
			}
			else{
				m_total += inVal;
				m_instances ++;
			}
		}
		
		public double getAvg(){

			return m_total / (double)m_instances;
			
		}
		
	}
	
	public static String DAY_SPAN = "day_span";
	public static String DAY_MOVE = "day_move";
	public static String DAY_VOLUME = "day_volume";
	public static String DAY_STOCK_VOLUME = "day_stock_volume";
	
	String[] keys = new String[]{DAY_MOVE, DAY_SPAN, DAY_VOLUME, DAY_STOCK_VOLUME};
	
	Map<String, Key> m_keyMap = new Hashtable<String, Key>();
	
	/**
	 * 
	 * @param inSamples
	 */
	public EquityKey(List<Sample> inSamples){
		
		for(String key : keys)
			m_keyMap.put(key, new Key());
		
		if(inSamples != null)
			for(Sample samp : inSamples){
				addInstance(samp);
			}
	}
	
	public void addInstance(Sample inSamp){
		
		//SPAN daily span in %
		double span = (inSamp.highest / inSamp.lowest) -1; 
		m_keyMap.get(DAY_SPAN).calculateInstance(span);
		
		//VOLATILITY based on daily move in %
		double move;
		if(inSamp.open > inSamp.close)
			move = inSamp.open / inSamp.close -1;
		else
			move = inSamp.close / inSamp.open -1;
		m_keyMap.get(DAY_MOVE).calculateInstance(move);
		
		//VOLUME in market currency
		m_keyMap.get(DAY_VOLUME).calculateInstance(inSamp.volume*inSamp.close);
		
		//STOCK_VOLUME
		m_keyMap.get(DAY_STOCK_VOLUME).calculateInstance(inSamp.volume);
	}
	
	/**
	 * 
	 * @param inKey
	 * @return
	 */
	public double getAvg(String inKey){
		return m_keyMap.get(inKey).getAvg();
	}
	


}
