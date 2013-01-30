package peligrosotrader.predict.rules;

import java.util.Hashtable;
import java.util.Map;

public class RulesTracker {

	static Map<Integer, IRule> m_rulesMap = new Hashtable<Integer, IRule>();
	static{
		
		m_rulesMap.put(RSI14TurnO30.ID, new RSI14TurnO30());
		m_rulesMap.put(ATH.ID, new ATH());
		m_rulesMap.put(ATL.ID, new ATL());
		m_rulesMap.put(MASupport.ID, new MASupport());
		m_rulesMap.put(Buy25.ID, new Buy25());
		//m_rulesMap.put(BuyThursday.ID, new BuyThursday());
		m_rulesMap.put(TrendSupport.ID, new TrendSupport());
		m_rulesMap.put(QPipeRule.ID, new QPipeRule());
		m_rulesMap.put(BigUpDay.ID, new BigUpDay());
		m_rulesMap.put(BigDownDay.ID, new BigDownDay());
		m_rulesMap.put(MAResistance.ID, new MAResistance());
		m_rulesMap.put(BigGapUp.ID, new BigGapUp());
		m_rulesMap.put(BigGapDown.ID, new BigGapDown());
		m_rulesMap.put(TrendResistance.ID, new TrendResistance());
		m_rulesMap.put(Sell6.ID, new Sell6());
		m_rulesMap.put(SupportOnVolume.ID, new SupportOnVolume());
		m_rulesMap.put(ResistanceOnVolume.ID, new ResistanceOnVolume());
		
//		try{
//			String name = RSI14TurnO30.class.getName();
//			Class<?> ruleTem = ClassLoader.getSystemClassLoader().getParent().loadClass(name);
//			rsiRule = (Rule)ruleTem.newInstance();
//		}catch(Exception e){
//			e.printStackTrace();
//			System.exit(1);
//		}
	}
	
	public static Map<Integer, IRule> getRuleMap(){
		return m_rulesMap;
	}
}
