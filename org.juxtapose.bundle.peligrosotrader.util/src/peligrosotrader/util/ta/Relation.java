package peligrosotrader.util.ta;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

/**
 * @author Sony
 *
 */
public class Relation {

	double[] chart1, chart2;
	
	double stdDev;
	double avg;
	
	/**
	 * @param inChart1
	 * @param inChart2
	 */
	public Relation(double[] inChart1, double[] inChart2){
		chart1 = inChart1;
		chart2 = inChart2;
		
		if(chart1.length < chart2.length)
			chart2 = cropChart(chart2, chart1);
		else if(chart2.length < chart1.length)
			chart1 = cropChart(chart1, chart2);
	}
	
	/**
	 * @param longChart
	 * @param shortChart
	 * @return
	 */
	private double[] cropChart(double[] longChart, double[] shortChart){
		double[] newChart = new double[shortChart.length];
		for(int i = 1; i < shortChart.length; i++){
			newChart[i] = longChart[i];
		}
		return newChart;
	}
	
	/**
	 * 
	 */
	public void calcRelations()
	{
		double[] relation = new double[chart1.length];
		
		for(int i = 0; i < chart1.length; i++){
			
			double d1 = chart1[i];
			if(d1 < 0)
				d1 *= -1;
			double d2 = chart2[i];
			if(d2 < 0)
				d2 *= -1;
			
			double largest = d1;
			double smallest = d2;
			if(smallest > largest){
				smallest = largest;
				largest = d2;
			}
			
			relation[i] = largest / smallest;
			if(smallest == 0)
				relation[i] = 0;
			
		}
		
		
		relation = sortAndCrop(relation);
		avg = avg(relation);
		double[] deviation = deviationVector(relation, avg);
		stdDev = standardDeviation(deviation);
	}
	
	/**
	 * @param inChart
	 * @return
	 */
	private double avg(double[] inChart){
		double sum = 0;
		for(int i=0; i < inChart.length; i++)
			sum += inChart[i];
		
		return sum/inChart.length;
	}
	
	/**
	 * @param inChart
	 * @return
	 */
	private double[] sortAndCrop(double[] inChart){
		
		SortedSet<Double> ss = new TreeSet<Double>();
		
		for(Double d : inChart)
		{	
			ss.add(d);
		}
		
		int cropSize = (int)( ss.size() * 0.1 );
		
		double[] sortedVec = new double[ss.size() - cropSize];
		
		int i = 0;
		for(Double d : ss){
			
			if(i > ss.size() - (cropSize + 1))
				break;
			
			sortedVec[i] = d;
			i++;
		}
		
		return sortedVec;
		
	}
	
	/**
	 * @param inChart
	 * @param inAvg
	 * @return
	 */
	private double[] deviationVector(double[] inChart, double inAvg){
		double[] devVector = new double[inChart.length];
		for(int i=0; i < inChart.length; i++)
			devVector[i] = inChart[i]-inAvg;
		
		return devVector;
	}
	
	/**
	 * @param inDevVector
	 * @return
	 */
	private double standardDeviation(double[] inDevVector){
		double sQSum = 0;
		for(int i=0; i < inDevVector.length; i++)
			sQSum += inDevVector[i]*inDevVector[i];
		
		return Math.sqrt(sQSum/inDevVector.length);
	}
	
	/**
	 * @return
	 */
	public double getStdDev(){
		return stdDev;
	}
	/**
	 * @return
	 */
	public double getAvg(){
		return avg;
	}
}
