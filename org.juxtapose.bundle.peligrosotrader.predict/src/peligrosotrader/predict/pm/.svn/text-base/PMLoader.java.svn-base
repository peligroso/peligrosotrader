package peligrosotrader.predict.pm;

import java.util.ArrayList;


import peligrosotrader.predict.rules.IRule;
import peligrosotrader.predict.rules.RulesTracker;

public class PMLoader {

	public static ArrayList<PM> load(){
		ArrayList<PM> pms = new ArrayList<PM>();
		
		for(int key : RulesTracker.getRuleMap().keySet()){
			IRule r = RulesTracker.getRuleMap().get(key);
			PM pm = new PM();
			pm.setRule(r);
			pm.clear();
			pms.add(pm);
		}
		
		//HibernateUtil.getSessionFactory();
		
		return pms;
	}

}
