package peligrosotrader.util.ta.trendlines;

import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class TrendLine {

	static int ON_TREND = 0;
	static int BELOW_TREND = -1;
	static int UP_BOW_TREND = 1;
	
	static double ON_TREND_MARGIN = 0.04;
	static double INCREASE_MARGIN = 0.1;

	public TrendPoint m_pointOne;
	public TrendPoint m_pointTwo;
	
	public int m_mergeCount = 0;

	public double m_increase;
	
	int m_type;

	/**
	 * 
	 * @param inPointOne
	 * @param inPointTwo
	 * @param inType
	 */
	public TrendLine(TrendPoint inPointOne, TrendPoint inPointTwo, int inType){

		m_type = inType;
		m_pointOne = inPointOne;
		m_pointTwo = inPointTwo;
		
		m_increase = (new Double(m_pointTwo.m_sample.last) - new Double(m_pointOne.m_sample.last)) / (m_pointTwo.m_index - m_pointOne.m_index); 
		
	}
	
	/**
	 * 
	 * @param inPoints
	 * @return
	 */
	public boolean isBroken(Vector<TrendPoint> inPoints, TrendPoint inLast){
		
		for(TrendPoint tp : inPoints){
			if(tp.m_index <= m_pointOne.m_index)
				continue;
			
			if(m_type == TrendStack.SUPPORT){
				if(trendRelation(tp.m_index, tp.m_sample.close) == BELOW_TREND)
					return true;
			}
			else if(m_type == TrendStack.RESISTANCE){
				if(trendRelation(tp.m_index, tp.m_sample.close) == UP_BOW_TREND)
					return true;
			}
		}
		
		if(inLast != null){
			if(m_type == TrendStack.SUPPORT){
				if(trendRelation(inLast.m_index, inLast.m_sample.close) == BELOW_TREND)
					return true;
			}
			else if(m_type == TrendStack.RESISTANCE){
				if(trendRelation(inLast.m_index, inLast.m_sample.close) == UP_BOW_TREND)
					return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param inTrend
	 * @return
	 */
	public TrendLine merge(TrendLine inTrend){
		
		
		TrendPoint tpFirst = null; 
		if( m_pointOne.m_index <inTrend.m_pointOne.m_index ){
			
			double startVal2 = inTrend.getValueOnIndex(m_pointOne.m_index);
			double medium = (startVal2 + m_pointOne.m_sample.close)/2;
			Sample newSamp = new Sample(m_pointOne.m_sample.ticker, Double.toString(medium), "", 
					"", 0, m_pointOne.m_sample.date, m_pointOne.m_sample.time);
			newSamp.close = medium;
			tpFirst = new TrendPoint(newSamp, m_pointOne.m_index);
		}
		else{
			double startVal2 = getValueOnIndex(inTrend.m_pointOne.m_index);
			double medium = (startVal2 + inTrend.m_pointOne.m_sample.close)/2;
			Sample newSamp = new Sample(inTrend.m_pointOne.m_sample.ticker, Double.toString(medium), "", 
					"", 0, inTrend.m_pointOne.m_sample.date, inTrend.m_pointOne.m_sample.time);
			newSamp.close = medium;
			tpFirst = new TrendPoint(newSamp, inTrend.m_pointOne.m_index);
		}
		TrendPoint tpSec = null;
		
		if( m_pointTwo.m_index > inTrend.m_pointTwo.m_index ){
			
			double endVal2 = inTrend.getValueOnIndex(m_pointTwo.m_index);
			double medium = (endVal2 + m_pointTwo.m_sample.close)/2;
			Sample newSamp = new Sample(m_pointTwo.m_sample.ticker, Double.toString(medium), "", 
					"", 0, m_pointTwo.m_sample.date, m_pointTwo.m_sample.time);
			newSamp.close = medium;
			tpSec = new TrendPoint(newSamp, m_pointTwo.m_index);
		}
		else{
			double endVal2 = getValueOnIndex(inTrend.m_pointTwo.m_index);
			double medium = (endVal2 + inTrend.m_pointTwo.m_sample.close)/2;
			Sample newSamp = new Sample(inTrend.m_pointTwo.m_sample.ticker, Double.toString(medium), "", 
					"", 0, inTrend.m_pointTwo.m_sample.date, inTrend.m_pointTwo.m_sample.time);
			newSamp.close = medium;
			tpSec = new TrendPoint(newSamp, inTrend.m_pointTwo.m_index);
		}
		
		TrendLine retTrendLine = new TrendLine(tpFirst, tpSec, m_type);
		return retTrendLine;
	}
	
	/**
	 * 
	 * @param inIndex
	 * @param val
	 * @return
	 */
	public int trendRelation(int inIndex, double val){
		
		double indexDiff = inIndex - m_pointOne.m_index;
		double trendValueOnIndex = m_pointOne.m_sample.close + (indexDiff * m_increase);
		
		if(val > trendValueOnIndex * (1 + ON_TREND_MARGIN) )
			return UP_BOW_TREND;
		else if(val < trendValueOnIndex * (1 - ON_TREND_MARGIN) )
			return BELOW_TREND;
		else
			return ON_TREND;
	}
	
	/**
	 * 
	 * @param inCompare
	 * @return
	 */
	public boolean compare(TrendLine inCompare){
		
		//Check angle
		
		double myAngle = m_increase;
		double compAngle = inCompare.m_increase;
		
		if((myAngle < 0 && compAngle > 0) || (myAngle > 0 && compAngle < 0))
			return false;
			
		if(myAngle < 0)
			myAngle *= -1; 		
		if(compAngle < 0)
			compAngle *= -1;
		
		if(myAngle > compAngle * (1 + INCREASE_MARGIN))
			return false;
		else if(myAngle < compAngle * (1 - INCREASE_MARGIN))
			return false;
		
		//Check paralell;
		return trendRelation(inCompare.m_pointOne.m_index, inCompare.m_pointOne.m_sample.close) == ON_TREND;
	}
	
	public double getValueOnIndex(int inIndex){
		double value = m_pointOne.m_sample.close + (m_increase * (inIndex - m_pointOne.m_index));
		return value;
	}
	
	public Vector<Sample> getFullLine(Sample inFirstSample, Sample inLastSample, int lastIndex, Vector<Sample> inGraph){
		
		double lowestPoint = 1000000;
		double highestPoint = 0;
		for(Sample samp : inGraph){
			if(samp.close > highestPoint)
				highestPoint = samp.close;
			if(samp.close < lowestPoint)
				lowestPoint = samp.close;
		}
		
		double start = new Double(m_pointOne.m_sample.last);
		double firstClose = start - (m_increase * (m_pointOne.m_index));
		Sample pointZero = new Sample(inFirstSample.ticker, Double.toString(firstClose), "1", null, 0, inFirstSample.date, inFirstSample.time);


		double lastClose = start + (m_increase * (lastIndex - m_pointOne.m_index));
//		if(lastClose > lastSampleClose * 1.5 || lastClose < lastSampleClose * 0.5)
//			return null;
		
		
		
//		Sample lastSample = inLastSample;
		
//		if(lastClose < lowestPoint){
//			int stepsBack = new Double((lastClose - lowestPoint) / m_increase).intValue();
//			lastSample = inGraph.get(inGraph.size()-1-stepsBack);
//			lastClose -= stepsBack*m_increase;
//		}
		
		Sample pointLast = new Sample(inLastSample.ticker, Double.toString(lastClose), "2", null, 0, inLastSample.date, inLastSample.time);
		
		
		//Cut line if its to long
		lowestPoint *= 0.2;
		highestPoint *= 1.2;
		if(firstClose < lowestPoint){
			double pointsUnder = lowestPoint - firstClose;
			int stepsForwardIndex = new Double(pointsUnder/m_increase).intValue();
			Sample sampOnFIndex = inGraph.get(stepsForwardIndex);
			Double newValue = getValueOnIndex(stepsForwardIndex);
			
			pointZero.close = newValue;
			pointZero.date = sampOnFIndex.date;
			pointZero.last = newValue.toString();
		}
		
		else if(firstClose > highestPoint){
			double pointsOver = firstClose - highestPoint;
			int stepsForwardIndex = new Double(pointsOver/m_increase).intValue();
			if(stepsForwardIndex < 0)
				stepsForwardIndex *= -1;
			Sample sampOnFIndex = inGraph.get(stepsForwardIndex);
			Double newValue = getValueOnIndex(stepsForwardIndex);

			pointZero.close = newValue;
			pointZero.date = sampOnFIndex.date;
			pointZero.last = newValue.toString();
			
		}
		
		if(lastClose < lowestPoint){
			double pointsUnder = lowestPoint - lastClose;
			int stepsBackwardIndex = new Double(pointsUnder/m_increase).intValue();
			if(stepsBackwardIndex < 0)
				stepsBackwardIndex *= -1;
			stepsBackwardIndex = inGraph.size()-1-stepsBackwardIndex;
			Sample sampOnBIndex = inGraph.get(stepsBackwardIndex);
			Double newValue = getValueOnIndex(stepsBackwardIndex);
			
			pointLast.close = newValue;
			pointLast.date = sampOnBIndex.date;
			pointLast.last = newValue.toString();			
		}
		
		else if(lastClose > highestPoint){
			double pointsOver = lastClose - highestPoint;
			int stepsBackwardIndex = new Double(pointsOver/m_increase).intValue();
			if(stepsBackwardIndex < 0)
				stepsBackwardIndex *= -1;
			stepsBackwardIndex = inGraph.size()-1-stepsBackwardIndex;
			Sample sampOnBIndex = inGraph.get(stepsBackwardIndex);
			Double newValue = getValueOnIndex(stepsBackwardIndex);
			
			pointLast.close = newValue;
			pointLast.date = sampOnBIndex.date;
			pointLast.last = newValue.toString();			
		}
		
		
		Vector<Sample> ret = new Vector<Sample>();
		ret.add(pointZero);
		ret.add(pointLast);
		
		return ret;
	}
	
	
	public boolean isPositive()
	{
		return m_pointOne.m_sample.close < m_pointTwo.m_sample.close;
	}


}
