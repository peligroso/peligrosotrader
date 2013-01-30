package peligrosotrader.util.ta;

public class Correlation extends TA {

	double[] chart1, chart2;
	
	public Correlation(double[] inChart1, double[] inChart2){
		chart1 = inChart1;
		chart2 = inChart2;
		
		if(chart1.length < chart2.length)
			chart2 = cropChart(chart2, chart1);
		else if(chart2.length < chart1.length)
			chart1 = cropChart(chart1, chart2);
	}
	
	private double[] cropChart(double[] longChart, double[] shortChart){
		double[] newChart = new double[shortChart.length];
		for(int i = 1; i < shortChart.length; i++){
			newChart[i] = longChart[i];
		}
		return newChart;
	}
	
	public double getCorrAll(){
		double[] deviationVector1 = deviationVector(chart1, avg(chart1));
		double[] deviationVector2 = deviationVector(chart2, avg(chart2));
		
		double SD1 = standardDeviation(deviationVector1);
		double SD2 = standardDeviation(deviationVector2);
		
		double sum = 0;
		for(int i=0; i < deviationVector1.length; i++)
			sum += (deviationVector1[i] * deviationVector2[i]);
		return sum/(SD1*SD2*deviationVector1.length);
	}
	
	public double getCorrSub(int startIndex, int endIndex){
		
		if(startIndex >= endIndex || endIndex > chart1.length)
			return -1;
		
		double[] c1 = new double[endIndex-startIndex];
		double[] c2 = new double[endIndex-startIndex];
		for(int i = 1; i < c1.length; i++){
			c1[i] = chart1[i];
			c2[i] = chart2[i];
		}
		
		double[] deviationVector1 = deviationVector(c1, avg(c1));
		double[] deviationVector2 = deviationVector(c2, avg(c2));
		
		double SD1 = standardDeviation(deviationVector1);
		double SD2 = standardDeviation(deviationVector2);
		
		double sum = 0;
		for(int i=0; i < deviationVector1.length; i++)
			sum += (deviationVector1[i] * deviationVector2[i]);
		return sum/(SD1*SD2*deviationVector1.length);		
		
	}
	
	
	private double avg(double[] inChart){
		double sum = 0;
		for(int i=0; i < inChart.length; i++)
			sum += inChart[i];
		
		return sum/inChart.length;
	}
	
	private double[] deviationVector(double[] inChart, double inAvg){
		double[] devVector = new double[inChart.length];
		for(int i=0; i < inChart.length; i++)
			devVector[i] = inChart[i]-inAvg;
		
		return devVector;
	}
	
	private double standardDeviation(double[] inDevVector){
		double sQSum = 0;
		for(int i=0; i < inDevVector.length; i++)
			sQSum += inDevVector[i]*inDevVector[i];
		
		return Math.sqrt(sQSum/inDevVector.length);
		
	}
	
	public static void main(String args[]){
		double[] c1 = new double[]{21, 21, 22, 23, 24, 24, 23, 25, 23, 22, 23, 23};
		double[] c2 = new double[]{100, 100, 120, 140, 160, 160, 140, 180, 140, 120, 140, 140};
		
		System.out.println(new Correlation(c1, c2).getCorrAll());
	}
	
}
