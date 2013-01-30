package peligrosotrader.predict;

import java.util.Hashtable;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import peligrosotrader.util.ta.EquityKey;
import peligrosotrader.util.ta.RSI;

/**
 * 
 * @author Peligroso
 *
 */
public class KeyIDTracker {
	
	static int LARGE_CAP = 1;
	static int MID_CAP = 2;
	static int LOW_CAP = 3;
	
	static long MID_CAP_MIN_VOL = 1000000;
	static long LARGE_CAP_MIN_VOL = 20000000;
	
	static int HIGH_VOLLA = 4;
	static int MID_VOLLA = 5;
	static int LOW_VOLLA = 6;
	
	static double MID_VOLLA_MIN = 0.01;
	static double HIGH_VOLLA_MIN = 0.03;
	
	static int MOMENTUM_LOW = 7;
	static int MOMENTUM_MED = 8;
	static int MOMENTUM_HIGH = 9;
	
	static int MOMENTUM_MED_MIN = 40;
	static int MOMENTUM_HIGH_MIN = 60;
	
	public static Map<String, TreeMap<Integer, String>> KEYS = new Hashtable<String, TreeMap<Integer, String>>();
	
	static{
		KEYS.put("Capitalization", getMap(new String[]{"Large Cap", "Mid Cap", "Low Cap"}, new Integer[]{LARGE_CAP, MID_CAP, LOW_CAP}));
		KEYS.put("Volatility", getMap(new String[]{"High", "Mid", "Low"}, new Integer[]{HIGH_VOLLA, MID_VOLLA, LOW_VOLLA}));
		KEYS.put("Momentum", getMap(new String[]{"High", "Mid", "Low"}, new Integer[]{MOMENTUM_HIGH, MOMENTUM_MED, MOMENTUM_LOW}));
	}
		
	static TreeMap<Integer, String> getMap(String[] inNames, Integer[] inVals){
		TreeMap ret = new TreeMap<Integer, String>();
		for(int i = 0; i < inNames.length; i++){
			ret.put(inVals[i], inNames[i]);
		}
		return ret;
	}
	
	/**
	 * 
	 * @param inKeys
	 * @return
	 */
	public static int getCapID(EquityKey inKeys)
	{
		long avgVolume = new Double(inKeys.getAvg(EquityKey.DAY_VOLUME)).longValue();
		//int testInt = (int)avgVolume;
		if(avgVolume < MID_CAP_MIN_VOL)
			return LOW_CAP;
		else if(avgVolume < LARGE_CAP_MIN_VOL)
			return MID_CAP;
		else
			return LARGE_CAP;
	}
	
	/**
	 * 
	 * @param inKeys
	 * @return
	 */
	public static int getVolatilityID(EquityKey inKeys)
	{
		double avgVolla = inKeys.getAvg(EquityKey.DAY_MOVE);
		
		if(avgVolla < MID_VOLLA_MIN)
			return LOW_VOLLA;
		else if(avgVolla < HIGH_VOLLA_MIN)
			return MID_VOLLA;
		else
			return HIGH_VOLLA;
	}
	
	/**
	 * 
	 * @param inRSI
	 * @return
	 */
	public static int getMomentumID(RSI inRSI){
		
		double rsi = inRSI.getRSI();
		if(rsi < MOMENTUM_MED_MIN)
			return MOMENTUM_LOW;
		else if(rsi < MOMENTUM_HIGH_MIN)
			return MOMENTUM_MED;
		else
			return MOMENTUM_HIGH;
	}

}
