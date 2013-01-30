package peligrosotrader.predict.rules;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.MA;

/**
 * 
 * @author Peligroso
 *Find a moving average that supports price
 */
public class MASupport extends Rule{
	
	
	class Supporter{
		public int DAYS = 10;
		public int MIN_BUILDPERIOD = 60;
		double m_support = 0;
		
		MA m_ma;
		int m_daysOutSide = 0;
		boolean inAreaGoTrade = false;
		int inAreaCount = 0;
		int buildPeriod = 0;
		
		public Supporter(int inDays){
			DAYS = inDays;
		}
		
		public boolean addADay(Vector<Sample> inGraph) {
//			if(inGraph.size() > 100 && DAYS == 15){
//				String last = inGraph.lastElement().last;
//				long l = 0;
//			}
			if(inGraph.size() < DAYS)
				return false;
			else if(m_ma == null){
				m_ma = new MA(inGraph, DAYS);
				return true;
			}
			else{
				Sample samp = inGraph.lastElement();
				m_ma.addToGraph(samp);
				double lastPrice = new Double(inGraph.lastElement().last);
				calcSupport(lastPrice);
				return true;
			}			
			
		}
		
		public void calcSupport(double inLastPrice){
			
			double lastMA = m_ma.getLastFromMA();
			double dev = lastMA * 0.02;
//			if(DAYS == 15){
//				long l = 0;
//			}
			if(inLastPrice < lastMA-dev){
				inAreaGoTrade = false;
				//m_support -= 1;
				//if(m_support < 0)
					m_support = 0;
				inAreaCount = 0;
				buildPeriod = 0;
			}
			else if(inLastPrice > lastMA+dev ){
				m_daysOutSide++;
				inAreaGoTrade = false;
				inAreaCount = 0;
				buildPeriod++;
				return;
			}
			else{
//				if(DAYS == 30){
//					long l = 0;
//				}
				inAreaCount++;
				double diff = inLastPrice - lastMA;
				
				double res = diff/dev;
				if(res < 0){
					res *= -1.5;
				}
				double supRes = 1-res;
				m_support += (supRes/inAreaCount);
				if(m_daysOutSide >= MIN_DAYS_OUTSIDE)
					inAreaGoTrade = true;
				m_daysOutSide = 0;
				buildPeriod++;
			}
		}
		
		public double getSupport(){
			return m_support;
		}
		
		public boolean getInArea(){
			return (inAreaGoTrade && (buildPeriod > MIN_BUILDPERIOD));
		}
	
	}
	
	
	
	static int MIN_DAYS_OUTSIDE = 10;
	static double MIN_SUPPORT = 4;
	
	static String NAME = "MA support";
	static int ID = 103;
	
	static int START_HOLD = 5;
	static int END_HOLD = 60;
	static int INC_HOLD = 5;
	
	int holdDays = 0;
	int m_holdingPeriod = 1;
	
	boolean first = true;
	
	int m_lastTriggered = 0;
	
	ArrayList<Supporter> m_supports = new ArrayList<Supporter>();
	
	public MASupport(){
		init();
	}
	public void finish() {
		// TODO Auto-generated method stub
		
	}

	public void resetGraph() {
		
		m_supports = new ArrayList<Supporter>();
		holdDays = 0;
		init();
	}
	
	public void init(){
		int days = 10;
		for(int i = 0; i < 10; i++){
			Supporter sup = new Supporter(days+=5);
			m_supports.add(sup);
		}
	}

	public int signal(Vector<Sample> inGraph) {
		addADay(inGraph);
		
		double bestSupport = 0;
		int bestDays = 0;
		for(Supporter sup : m_supports){
			if(sup.getSupport() > bestSupport && sup.getInArea()){
				bestSupport = sup.getSupport();
				bestDays = sup.DAYS;
			}
		}
		if(bestSupport > MIN_SUPPORT){
			System.out.println("position for MA: "+bestDays);
			m_holdingPeriod = 12;
			holdDays = 0;
			m_lastTriggered = bestDays;
			return 1;
		}
		return 0;
	}
	
	public void addADay(Vector<Sample> inGraph){
		for(Supporter sup : m_supports){
			sup.addADay(inGraph);
		}
	}
	
	
	public int getEndHoldDay() {
		// TODO Auto-generated method stub
		return END_HOLD;
	}
	public int getHoldDayIncrease() {
		// TODO Auto-generated method stub
		return INC_HOLD;
	}
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return NAME;
	}
	public int getStartHoldDay() {
		// TODO Auto-generated method stub
		return START_HOLD;
	}
	
	public Map<Integer, String> getRuleParams() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getScanSteps(){
		return -1;
	}
	
	public Map<String, String> getSignalInfo() {
		// TODO Auto-generated method stub
		if(m_lastTriggered != 0){
			Map<String, String> params = new Hashtable<String, String>();
			params.put("DAYS", Integer.toString(m_lastTriggered));
			
			return params;
		}
		return null;
	}

}
