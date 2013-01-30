package peligrosotrader.ruleevaluation.rules;

import java.util.ArrayList;
import java.util.Vector;

import peligrosotrader.ruleevaluation.mining.MiningRule;
import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.MA;

/**
 * 
 * @author Peligroso
 *Find a moving average that supports price
 */
public class RuleTerm_MAResistance extends MiningRule{
	
	
	class Resister{
		public int DAYS = 10;
		public int MIN_BUILDPERIOD = 60;
		double m_support = 0;
		
		MA m_ma;
		int m_daysOutSide = 0;
		boolean inAreaGoTrade = false;
		int inAreaCount = 0;
		int buildPeriod = 0;
		
		public Resister(int inDays){
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
			if(inLastPrice > lastMA+dev){
				inAreaGoTrade = false;
				//m_support -= 1;
				//if(m_support < 0)
					m_support = 0;
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
//				if(DAYS == 30){
//					long l = 0;
//				}
				inAreaCount++;
				double diff = lastMA - inLastPrice;
				
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
		
		public double getResist(){
			return m_support;
		}
		
		public boolean getInArea(){
			return (inAreaGoTrade && (buildPeriod > MIN_BUILDPERIOD));
		}
	
	}
	
	
	
	static int MIN_DAYS_OUTSIDE = 10;
	static double MIN_SUPPORT = 4;
	
	int holdDays = 0;
	int m_holdingPeriod = 1;
	
	boolean first = true;
	
	ArrayList<Resister> m_resists = new ArrayList<Resister>();
	
	public RuleTerm_MAResistance(){
		init();
	}
	public void finish() {
		// TODO Auto-generated method stub
		
	}

	public double leavePos(Vector<Sample> inGraph) {
		holdDays++;
		addADay(inGraph);
		
		if(holdDays > m_holdingPeriod){
			holdDays = 0;
			return inGraph.lastElement().close;
		}
		
		return -1;
	}

	public void resetGraph() {
		
		m_resists = new ArrayList<Resister>();
		holdDays = 0;
		init();
	}
	
	public void init(){
		int days = 10;
		for(int i = 0; i < 10; i++){
			Resister sup = new Resister(days+=5);
			m_resists.add(sup);
		}
	}

	public double takePos(Vector<Sample> inGraph) {
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
			m_holdingPeriod = 14;
			holdDays = 0;
			return inGraph.lastElement().close;
		}
		return -1;
	}
	
	public void addADay(Vector<Sample> inGraph){
		for(Resister sup : m_resists){
			sup.addADay(inGraph);
		}
	}
	

	public boolean changeSecParam(){
		m_holdingPeriod += 1;
		if(m_holdingPeriod > 50){
			return false;
		}
		return true;
	}
	
	public boolean changeFirstParam(){
		if(first){
			first = false;
			return true;
		}
		return false;
	}
	
	public double getFirstParam(){
		return m_holdingPeriod;
	}


}