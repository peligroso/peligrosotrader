package peligrosotrader.util.ta;


public class MathFunctions {

	/**
	 * @param inChart
	 * @return
	 */
	public static double getStdDev(double[] inChart)
	{
		double[] deviationVector1 = deviationVector(inChart, avg(inChart));
		
		double sd = standardDeviation(deviationVector1);
		
		return sd;
	}
	
	/**
	 * @param inChart
	 * @return
	 */
	public static double avg(double[] inChart)
	{
		double sum = 0;
		for(int i=0; i < inChart.length; i++)
			sum += inChart[i];
		
		return sum/inChart.length;
	}
	
	/**
	 * @param inChart
	 * @param inAvg
	 * @return
	 */
	public static double[] deviationVector(double[] inChart, double inAvg)
	{
		double[] devVector = new double[inChart.length];
		for(int i=0; i < inChart.length; i++)
			devVector[i] = inChart[i]-inAvg;
		
		return devVector;
	}
	
	/**
	 * @param inDevVector
	 * @return
	 */
	public static double standardDeviation(double[] inDevVector)
	{
		double sQSum = 0;
		for(int i=0; i < inDevVector.length; i++)
			sQSum += inDevVector[i]*inDevVector[i];
		
		return Math.sqrt(sQSum/(inDevVector.length - 1));
		
	}
	
	/**
	 * @param inVector
	 * @return
	 */
	public static double getAvgPercentMove(double[] inVector)
	{
		Double last = inVector[0];
		Double totMove = 0d;
		
		for(int i = 1; i < inVector.length; i++)
		{
			Double move = ( inVector[i] / last ) - 1;
			
			if( move < 0 )
				move *= -1;
			
			totMove += move;
			last = inVector[i];
		}
		
		Double avgMove = totMove / ( inVector.length - 1 );
		
		return avgMove;
	}
	

}
