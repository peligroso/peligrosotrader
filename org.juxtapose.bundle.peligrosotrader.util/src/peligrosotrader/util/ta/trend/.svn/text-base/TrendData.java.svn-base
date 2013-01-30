package peligrosotrader.util.ta.trend;

import java.util.Vector;

public class TrendData {

		int startIndex;
		int endIndex;
		Vector<Double> m_trendValues;
		
		public TrendData(int inStartIndex, int inEndIndex, Vector<Double> inTrend){
			startIndex = inStartIndex;
			endIndex = inEndIndex;
			m_trendValues = inTrend;
		}
		
		public double[] getTrendLine(){
			
			double[] ret = new double[m_trendValues.size()];
			double lastValue = m_trendValues.get(m_trendValues.size()-1);
			double firstValue = m_trendValues.get(0);
			double span = lastValue/firstValue;
			double factor = span/ret.length;
			double incVal = lastValue;
			for (int i = 0; i < ret.length; i++){
				ret[i] = incVal;
				incVal += factor;
			}
			
			return ret;
		}
		
		public double getStartValue(){
			return m_trendValues.get(0);
		}
		public double getEndValue(){
			return m_trendValues.get(m_trendValues.size()-1);
		}
		
		public int getStartIndex(){
			return startIndex;
		}
		public int getEndIndex(){
			return endIndex;
		}
}
