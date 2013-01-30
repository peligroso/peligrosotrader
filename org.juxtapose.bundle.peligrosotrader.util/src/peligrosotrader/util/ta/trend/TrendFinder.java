package peligrosotrader.util.ta.trend;

import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class TrendFinder {
	
	public static int NEGATIVE = 0;
	public static int POSITIVE = 1;
	public static int NOT_SET = 3;
	public int MIN_TREND_VAL = 5;
	public int MIN_TREND_LENGTH = 5;
	public static int MAX_STRAY_LENGTH = 100;
	public static double BREAK_FACTOR = 0.9;
	public static double REVERSE_FACTOR = 1;

	//private Vector<Double> m_trend = new Vector<Double>();
	private Vector<Sample> m_original;
	private Vector<TrendData> m_doneTrends = new Vector<TrendData>();
	private double[] m_graf;
	private int trendStartIndex, trendEndIndex, grafCursor; 
	double breakPoints;
	private int trendType;
	private boolean newTrend;
	private double unit;
	
	public TrendFinder(Vector<Sample> inVec){
		initiate(inVec);
	}
	
	public TrendFinder(Vector<Sample> inVec, int inStrength, int inLength){
		MIN_TREND_VAL = inStrength;
		MIN_TREND_LENGTH = inLength;
		initiate(inVec);
	}
	
	private void initiate(Vector<Sample> inVec){
		m_original = inVec;
		double[] inGraf = new double[inVec.size()];
		int i = 0;
		for(Sample samp : inVec){
			inGraf[i] = new Double(samp.last);
			i++;
		}
			
		m_graf = inGraf;
		trendEndIndex = 0;
		grafCursor = 0;
		newTrend = true;
		calcUnit();
	}
	
	public void analyze(){
		
		
		while (grafCursor < m_graf.length) {
			if (newTrend) {
				//Start a new Ternd!
				breakPoints = 0;
				trendType = NOT_SET;
				trendStartIndex = grafCursor;
				trendEndIndex = grafCursor;
				grafCursor++;
				

				if (grafCursor < m_graf.length) {
					//Establish tend type based on next point
					if (m_graf[grafCursor] < m_graf[trendEndIndex]) {
						trendType = NEGATIVE;
						trendEndIndex = grafCursor;
						grafCursor++;
						newTrend = false;
					} else if (m_graf[grafCursor] > m_graf[trendEndIndex]) {
						trendType = POSITIVE;
						trendEndIndex = grafCursor;
						grafCursor++;
						newTrend = false;
					} else {
						//kill the trend instantly
						trendEndIndex = grafCursor;
						saveTrend(false);
					}
				}				
			}
			//Follow up the trend
			//If the trend wasn´t killed instantly
			if(!(grafCursor < m_graf.length))
				continue;
			if (!newTrend) {
				if (trendType == NEGATIVE) {
					if (m_graf[grafCursor] < m_graf[trendEndIndex]) {
						trendEndIndex = grafCursor;
						grafCursor++;
					}
					else if(m_graf[grafCursor] > m_graf[trendEndIndex]){
						breakPoints++;
						tryTrend(false);
					}
					//Special case if cursorstray turn towards trend
					else if(m_graf[grafCursor] == m_graf[trendEndIndex] && movingTowardsTrend()){
						breakPoints -= REVERSE_FACTOR;
						tryTrend(false);
					}
					else
						tryTrend(false);
				} else if (trendType == POSITIVE) {
					if (m_graf[grafCursor] > m_graf[trendEndIndex]) {
						trendEndIndex = grafCursor;
						grafCursor++;
					}
					else if(m_graf[grafCursor] < m_graf[trendEndIndex]){
						breakPoints++;
						tryTrend(false);
					}
//					Special case if cursorstray turn towards trend
					else if(m_graf[grafCursor] == m_graf[trendEndIndex] && movingTowardsTrend()){
						breakPoints -= REVERSE_FACTOR;
						tryTrend(false);
					}
					else
						tryTrend(false);
					
				} else
					tryTrend(false);

			}
		}
		saveTrend(true);
		print();
	}
	
	public boolean inTrend(){
		if(m_doneTrends.size() < 1)
			return false;
		
		TrendData lastTrend = m_doneTrends.get(m_doneTrends.size()-1);
		if(m_original.size()-1 == lastTrend.endIndex)
			return true;
		return false;
	}
	public boolean movingTowardsTrend(){
		if(trendType == POSITIVE && m_graf[grafCursor] > m_graf[grafCursor-1])
			return true;
		if(trendType == NEGATIVE && m_graf[grafCursor] < m_graf[grafCursor-1])
			return true;
		return false;
	}
	
	public void tryTrend(boolean isLast){
		
		double strayStrength = strayStrength();
		double trendStrength = trendStrength();
		if( strayStrength / trendStrength > BREAK_FACTOR || strayLength() > MAX_STRAY_LENGTH ){
			saveTrend(isLast);
		}else
			grafCursor++;
	}
	
	public void saveTrend(boolean isLast){

		grafCursor = trendEndIndex;
		
		if (trendStrength() >= MIN_TREND_VAL && trendLength() >= MIN_TREND_LENGTH){
			
			if(isLast)
				trendEndIndex = m_graf.length-1;
			
			Vector<Double> trend = new Vector<Double>();
			int tempGrafCursor = trendStartIndex;
			for(int i=0; i <= trendLength(); i++){
				trend.add(m_graf[tempGrafCursor]);
				tempGrafCursor++;
			}
			m_doneTrends.add(new TrendData(trendStartIndex, trendEndIndex, trend));
		}
		newTrend = true;
	}
	
	public void calcUnit(){
		unit = Double.MAX_VALUE;
		for(int i = 1; i < m_graf.length; i++){
			if(i == 0)
				continue;
			double testUnit = m_graf[i-1] - m_graf[i];
			if(testUnit < 0)
				testUnit *= -1;
			if (testUnit < unit && testUnit != 0)
				unit = testUnit;			
		}
	}
	
	public int trendLength(){
		return trendEndIndex -trendStartIndex;
	}
	public int strayLength(){
		return grafCursor - trendEndIndex; 
	}
	public double trendStrength(){
	
			double spred = m_graf[trendStartIndex] - m_graf[trendEndIndex];
			if(spred < 0)
				spred *= -1;
			spred /= unit;
			return trendLength()*spred;
	}
	public double strayStrength(){
		if(grafCursor > m_graf.length-1)
			grafCursor = m_graf.length-1;
		
		double spred = m_graf[trendEndIndex] - m_graf[grafCursor];
		if(spred < 0)
			spred *= -1;
		spred /= unit;
		return breakPoints*spred;
	}
	
	public int cursorStray(){
		return grafCursor -trendEndIndex;
	}
	
	public void print(){
		for(TrendData td : m_doneTrends){

			System.out.println("Trend start at value: "+td.startIndex+
							", end at index: "+td.endIndex+
							", start-value is: "+td.getStartValue()+
							", end-value is: "+td.getEndValue());
		}
	}
	
	
	
	
	
	
	/*****************************************************************
	 * Methods to Create graf
	 * **/
	public double[] getTrendLine(){
		double[] ret = new double[m_graf.length];
		int index = 0;
		ret[index] = m_graf[index];
		index++;
		for(TrendData td : m_doneTrends){
			if(index < td.startIndex)
				plotLine(ret, index, td.startIndex, m_graf[td.startIndex]);
				index = td.startIndex;
			
				
			if(index > td.startIndex)
				plotLine(ret, index, td.endIndex, m_graf[td.endIndex]);
			
			else if(index == td.startIndex)
				plotLine(ret, index, td.endIndex, m_graf[td.endIndex]);
				
			index = td.endIndex;
		
		}
		if(index < m_graf.length-1)
			plotLine(ret, index, m_graf.length-1, m_graf[m_graf.length-1]);
		
		return ret;
	}
	
	public Vector<Sample> getTrendSamples(){
		double[] line = getTrendLine();
		Vector<Sample> ret = new Vector<Sample>();
		
		for(int i = 0; i < line.length; i++)
			ret.add(new Sample("Trend", ""+line[i], "0", 
					"0", 0, m_original.get(i).date, m_original.get(i).time));
		
		return ret;
	}
	
	public void plotLine(double[] inArray, int fromIndex, int toIndex, double endValue){
		double averageIncrease = (endValue - inArray[fromIndex-1]) / (toIndex - fromIndex);
		double incVal = inArray[fromIndex-1];
		int cursor = fromIndex;
		while(cursor <= toIndex){
	
			inArray[cursor] = incVal;
			incVal += averageIncrease;
//			/****/
//			if(m_original.get(cursor).date.getDay() == 5){
//				incVal += averageIncrease;
//				incVal += averageIncrease;
//			}
			/**/		
			cursor++;
		}
	}
	
	/**
	 * @return
	 */
	public Vector<TrendData> getTrends(){
		return m_doneTrends;
	}
	
	/**
	 * @return
	 */
	public Vector<Vector<Sample>> getTrend(){
		
		Vector<Vector<Sample>> ret = new Vector<Vector<Sample>>();
		for(TrendData td : m_doneTrends)
		{
			Vector<Sample> sampVec = new Vector<Sample>();
			
			Sample start = new Sample("Trend", ""+td.m_trendValues.firstElement(), "0", "0", 0, m_original.get(td.startIndex).date,
					m_original.get(td.startIndex).time);
			
			Sample end = new Sample("Trend", ""+td.m_trendValues.lastElement(), "0", "0", 0, m_original.get(td.endIndex).date,
					m_original.get(td.endIndex).time);
					
			sampVec.add(start);
			sampVec.add(end);
			
			ret.add(sampVec);
		}
		return ret;
	}
	
	public int getTrendType(int inIndex)
	{
		TrendData td = getTrends().get(inIndex);
		if(td.m_trendValues.firstElement() > td.m_trendValues.lastElement())
			return -1;
		else
			return 1;
	}
}
