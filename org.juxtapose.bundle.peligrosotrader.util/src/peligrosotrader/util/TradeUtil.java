package peligrosotrader.util;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class TradeUtil {

	public static Vector<Sample> convertToProcent(Vector<Sample> inArr){
		Vector<Sample> ret = new Vector<Sample>();
		
		Double zeroIndex = new Double(0);
		if(inArr.size() > 0)
			zeroIndex = new Double(inArr.get(0).last);
		
		double percentFactor = zeroIndex/100;
		
		//convert formula = (number/percentfactor)-100
		
		for(Sample samp : inArr){

			Double proc = new Double(new Double(samp.last)/percentFactor-100);
			ret.add(new Sample(samp.ticker, proc.toString(), samp.ask, 
			samp.bid, samp.order, samp.date, samp.time));
		}
		return ret;
	}
	
	/**
	 * @param inDateString
	 * @return
	 */
	public static Calendar parseDateString(String inDateString){
		
		Calendar retCal = Calendar.getInstance();
		String[] dels = inDateString.split("-");
		
		if(dels.length < 3)
			return null;
		
		retCal.set(Calendar.YEAR, new Integer(dels[0]));
		retCal.set(Calendar.MONTH, new Integer(dels[1])-1);
		retCal.set(Calendar.DAY_OF_MONTH, new Integer(dels[2]));

		return retCal;
	}
	
	public static Vector<Sample> createSampleVec(double[] inValues, Vector<Sample> inOrg){
		Vector<Sample> ret = new Vector<Sample>();
		for(int i = 0; i < inValues.length; i++){
			String d = Double.toString(inValues[i]);
			Sample samp = inOrg.get(i);
			
			ret.add(new Sample("ticker", d, d, d, samp.order, samp.date, samp.time));
		}
		return ret;
	}
	
	/**
	 * @param inLength
	 * @param fromCal
	 * @param toCal
	 * @return
	 */
	public static Vector<Sample> getRandomChart(int inLength, Calendar fromCal, Calendar toCal)
	{
		double start = 100;
		Random rand = new Random();
		
		Calendar cal = fromCal;
		
		Vector<Sample> retVec = new Vector<Sample>();
		
		while(cal.getTimeInMillis() < toCal.getTimeInMillis())
		{
			Date date = new Date(cal.getTimeInMillis());
			Sample samp = new Sample(date, new Time(cal.getTimeInMillis()));
			
			double newVal = rand.nextGaussian();
			start += newVal;
			samp.setPriceValues(start);
			
			retVec.add(samp);
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) +1);
			while(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) +1);
		}
		return retVec;
	}
	
	/**
	 * @param fromCal
	 * @param toCal
	 * @param inMean
	 * @param inStdDev
	 * @return
	 */
	public static Vector<Sample> getRandomChart( Calendar fromCal, Calendar toCal, double inMean, double inStdDev )
	{
		double mean = inMean;
		Random rand = new Random();
		
		Calendar cal = fromCal;
		
		Vector<Sample> retVec = new Vector<Sample>();
		
		while(cal.getTimeInMillis() < toCal.getTimeInMillis())
		{
			Date date = new Date(cal.getTimeInMillis());
			Sample samp = new Sample(date, new Time(cal.getTimeInMillis()));
			
			double newVal = rand.nextGaussian();
			newVal *= inStdDev;
			newVal += 1;
			newVal *= mean;
			mean = newVal;
			samp.setPriceValues(mean);
			
			retVec.add(samp);
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) +1);
			while(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) +1);
		}
		return retVec;
	}
	
	
	/**
	 * @param inVal
	 * @param decimals
	 * @return
	 */
	public static Double formatDouble(double inVal, int decimals)
	{
		double power = Math.pow(10, decimals);
		inVal *= power;
		Long rounded = Math.round(inVal);
		inVal = rounded.doubleValue() / power;
		
		return inVal;
	}
	
	/**
	 * @param inGraph1
	 * @param inGraph2
	 * @return
	 */
	public static Vector<Sample> mergeGraphs(Vector<Sample> inGraph1, Vector<Sample>inGraph2)
	{
		SortedMap<Long, Sample> sampleMap = new TreeMap<Long, Sample>();
		for( Sample samp : inGraph1 )
		{
			sampleMap.put(samp.date.getTime(), samp);
		}
		
		Vector<Sample> merged = new Vector<Sample>();
		
		for( Sample samp : inGraph2 )
		{
			Sample samp1 = sampleMap.get(samp.date.getTime());
			if(samp1 != null){
				
				samp1.setPriceValues(samp1.close + samp.close);
				merged.add(samp1);
				sampleMap.remove(samp1.date.getTime());
			}
		}
		
		return merged;
	}
	
	/**
	 * @param inSample
	 * @return
	 */
	public static double[] getCloseArray(Vector<Sample> inSample)
	{
		double[] ret = new double[inSample.size()];
		
		int i = 0;
		for( Sample samp : inSample ){
			
			ret[i] = samp.close;
			i++;
		}
		return ret;
	}
	
}
