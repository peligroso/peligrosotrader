package peligrosotrader.util.ta.trend;

import java.util.Vector;

public class Trend {

	public static int NEGATIVE = 0;
	public static int POSITIVE = 1;
	public static int NOT_SET = 3;
	
	public static int IN_TREND = 0;
	public static int OFF_TREND = 1;
	public static int NEUTRAL = 3;
	
	public static float TREND_FACTOR = 0.34f;
	
	int startIndex;
	private int type;
	private int trendValue=0;
	private int cursor;
	public boolean isDead = false;
	private Trend m_counterTrend = null;
	
	private Vector<Double> m_trend = new Vector<Double>();
	private Vector<Double> m_offValues = new Vector<Double>();
	private TrendAnalyzer m_analyzer;
	private Trend m_parent = null;
	
	public Trend(TrendAnalyzer inAnalyser, double inStartVal, int inStartIndex){
		m_analyzer = inAnalyser;
		m_trend.add(inStartVal);
		type = NOT_SET;
		cursor = 0;
		startIndex = inStartIndex;
	}
	
	public void setTrendType(int inType){
		type = inType;
	}
	
	public void carryOn(double inNewVal){
		//Define trend type
		if(type == NOT_SET)
			if(m_trend.get(cursor) > inNewVal)
				type = NEGATIVE;
			else if(m_trend.get(cursor) < inNewVal)
				type = POSITIVE;
		
		int trendStatus = inTrend(inNewVal);
		if(trendStatus == IN_TREND){
			if(m_counterTrend == null){
				aquireOffvalues();
				m_trend.add(inNewVal);
				cursor++;
				trendValue++;
			} else
				m_offValues.add(inNewVal);
		}
		else if(trendStatus == NEUTRAL){
			if(m_counterTrend != null)
				m_offValues.add(inNewVal);
			else{
				m_trend.add(inNewVal);
				trendValue++;
				cursor++;
			}
		}
		else{
			m_offValues.add(inNewVal);
			if(m_counterTrend == null){
				m_counterTrend = m_analyzer.createNewTrend(m_trend.get(cursor).doubleValue(), inNewVal, this, cursor);
			}
			else
				tryMe();
		}
	}
	
	public int inTrend(double inVal){
		if (type == POSITIVE)
			if (inVal > m_trend.get(cursor))
				return IN_TREND;
			else if (inVal < m_trend.get(cursor))
				return OFF_TREND;
			else 
				return NEUTRAL; 
		else
			if (inVal < m_trend.get(cursor))
				return IN_TREND;
			else if (inVal > m_trend.get(cursor))
				return OFF_TREND;
			else 
				return NEUTRAL; 
	}
	
	public void aquireOffvalues(){
		for(double val : m_offValues){
			m_trend.add(val);
			cursor++;
			trendValue++;
		}
		m_offValues.removeAllElements();
	}
	
	/**
	 * Try if this trend is strong enough to stand against its countertrend 
	 */
	public void tryMe(){
		if(m_counterTrend.getValue()/getValue() > TREND_FACTOR)
			imBroken();
	}
	
	public int getValue(){
		return trendValue;
	}
	
	public void imBroken(){
		if(m_parent != null)
			m_parent.youWinPapa();
		if(m_counterTrend != null)
			m_parent.youWinPapa();
		
		m_analyzer.imFucked(this);
	}
	
	public void setParentTrend(Trend inTrend){
		m_parent = inTrend;
	}
	
	/**
	 * CounterTrend is destroyd
	 * Gives room for this trend to create a new CounterTrend
	 */
	public void youWinPapa(){
		m_counterTrend = null;
	}
	public void youWinSon(){
		m_parent = null;
	}
	
	public TrendData generateTrendData(){
		return new TrendData(startIndex, startIndex+cursor, m_trend);
	}

}
