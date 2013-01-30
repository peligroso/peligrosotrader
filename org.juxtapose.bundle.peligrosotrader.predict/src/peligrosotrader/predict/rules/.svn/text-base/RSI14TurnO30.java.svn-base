package peligrosotrader.predict.rules;

import java.util.Map;
import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.RSI;

/**
 * 
 * @author Peligroso
 *
 */
public class RSI14TurnO30 extends Rule {
	
	static int ID = 101;
	static String NAME = "RSI14 turn over 30";
	
	static int START_HOLD = 1;
	static int END_HOLD = 40;
	static int INC_HOLD = 5;

	RSI m_rsi;
	static int PERIOD = 14;
	static int TIME_TO_CROSS = 4;
	
	
	boolean m_first = true;
	
	boolean m_under30 = false;
	boolean m_over60 = false;
	
	

	public void resetGraph() {
		m_first = true;		
		m_over60 = false;
		m_under30 = false;
	}

	/**
	 * 
	 */
	public int signal(Vector<Sample> inGraph) {
		if(m_first){
			m_rsi = new RSI(inGraph, PERIOD);
			m_first = false;
			return 0;
		}
		Sample lastSamp = inGraph.get(inGraph.size()-1);
		m_rsi.addToRSIGraph(lastSamp);
		if(inGraph.size() < PERIOD){
			return 0;
		}
		double rsiVal = m_rsi.getRSI();

		if(rsiVal < 30){
			m_under30 = true;
			return 0;
		}
		if(m_under30 && rsiVal > 30){
			m_under30 = false;
			return 1;
		}
		
		
		return 0;
	}
	

	public int getID() {
		
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
		return 20;
	}
}
