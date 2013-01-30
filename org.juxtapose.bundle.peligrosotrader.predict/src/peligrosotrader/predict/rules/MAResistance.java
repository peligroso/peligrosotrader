package peligrosotrader.predict.rules;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.MA;

public class MAResistance extends Rule{

	public static int ID = 110;
	static String NAME = "MAResistance"; 
	public static String DESCRIPTION = "Signals when the graph is touching a resisting Moving average";
	
	static int START_HOLD = 5;
	static int END_HOLD = 40;
	static int INC_HOLD = 5;
	
	class Resister
	{
		public int DAYS = 10;
		public int MIN_BUILDPERIOD = 60;
		double m_resist = 0;
		
		MA m_ma;
		int m_daysOutSide = 0;
		boolean inAreaGoTrade = false;
		int inAreaCount = 0;
		int buildPeriod = 0;
		
		public Resister(int inDays){
			DAYS = inDays;
		}
		
		/**
		 * 
		 * @param inGraph
		 * @return
		 */
		public boolean addADay(Vector<Sample> inGraph) {

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
		
		/**
		 * 
		 * @param inLastPrice
		 */
		public void calcSupport(double inLastPrice){
			
			double lastMA = m_ma.getLastFromMA();
			double dev = lastMA * 0.02;

			if(inLastPrice > lastMA+dev){
				inAreaGoTrade = false;
				
				m_resist = 0;
				inAreaCount = 0;
				buildPeriod = 0;
			}
			else if(inLastPrice < lastMA-dev ){
				m_daysOutSide++;
				inAreaGoTrade = false;
				inAreaCount = 0;
				buildPeriod++;
				return;
			}
			else{

				inAreaCount++;
				double diff = lastMA - inLastPrice;
				
				double res = diff/dev;
				if(res < 0){
					res *= -1.5;
				}
				double supRes = 1-res;
				m_resist += (supRes/inAreaCount);
				if(m_daysOutSide >= MIN_DAYS_OUTSIDE)
					inAreaGoTrade = true;
				m_daysOutSide = 0;
				buildPeriod++;
			}
		}
		
		public double getResist(){
			return m_resist;
		}
		
		public boolean getInArea(){
			return (inAreaGoTrade && (buildPeriod > MIN_BUILDPERIOD));
		}
	
	}
	
	static int MIN_DAYS_OUTSIDE = 10;
	static double MIN_SUPPORT = 4;
	
	boolean first = true;
	
	int m_lastTriggered = 0;
	
	ArrayList<Resister> m_resists = new ArrayList<Resister>();
	
	public MAResistance(){
		init();
	}
	
	public void resetGraph() {
		
		m_resists = new ArrayList<Resister>();
		init();
	}
	
	public void init(){
		int days = 10;
		for(int i = 0; i < 10; i++){
			Resister sup = new Resister(days+=5);
			m_resists.add(sup);
		}
	}
	
	/**
	 * 
	 */
	public int signal(Vector<Sample> inGraph) {
		addADay(inGraph);
		
		double bestResist = 0;
		int bestDays = 0;
		for(Resister sup : m_resists){
			if(sup.getResist() > bestResist && sup.getInArea()){
				bestResist = sup.getResist();
				bestDays = sup.DAYS;
			}
		}
		if(bestResist > MIN_SUPPORT){
			System.out.println("position for MA: "+bestDays);
			m_lastTriggered = bestDays;
			return 1;
		}
		return 0;
	}
	
	public void addADay(Vector<Sample> inGraph){
		for(Resister sup : m_resists){
			sup.addADay(inGraph);
		}
	}
	
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
	public int getEndHoldDay() {
		// TODO Auto-generated method stub
		return END_HOLD;
	}

	public int getHoldDayIncrease() {
		// TODO Auto-generated method stub
		return INC_HOLD;
	}

	public int getStartHoldDay() {
		// TODO Auto-generated method stub
		return START_HOLD;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return NAME;
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
