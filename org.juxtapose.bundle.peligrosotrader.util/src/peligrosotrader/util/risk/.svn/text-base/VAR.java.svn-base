package peligrosotrader.util.risk;

import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

public class VAR 
{
	public static int ITERATIONS = 10000;

	SortedMap<Double, Integer> m_percentToSamples = new TreeMap<Double, Integer>();


	/**
	 * 
	 */
	public VAR()
	{
		m_percentToSamples.put(10d, 0);
		m_percentToSamples.put(20d, 0);
		m_percentToSamples.put(30d, 0);
		m_percentToSamples.put(40d, 0);
		m_percentToSamples.put(50d, 0);
		m_percentToSamples.put(60d, 0);
		m_percentToSamples.put(70d, 0);
		m_percentToSamples.put(80d, 0);
		m_percentToSamples.put(90d, 0);
		m_percentToSamples.put(100d, 0);
	}

	/**
	 * @param inStdDev
	 * @param inMean
	 * @param inSteps
	 */
	public void calcVAR( double inStdDev, double inMean, int inSteps )
	{
		for( int i = 0; i < ITERATIONS; i++ )
		{
			double iter = randomIter( inStdDev, inMean, inSteps );

			double percentRes = 1 - (iter / inMean);
			percentRes *= 100;

			for( Double threshold : m_percentToSamples.keySet() )
			{
				if( percentRes < threshold )
				{
					Integer samples = m_percentToSamples.get( threshold );
					samples++;
					m_percentToSamples.put( threshold, samples);
					break;
				}
			}
		}

	}
	
	public SortedMap<Double, Integer> getDistribution(){
		
		return m_percentToSamples;
	}

	/**
	 * @param inStdDev
	 * @param inMean
	 * @param inSteps
	 * @return
	 */
	public double randomIter(double inStdDev, double inMean, int inSteps)
	{
		double mean = inMean;
		Random rand = new Random();

		for( int i = 0; i < inSteps; i++ )
		{
			double newVal = rand.nextGaussian();
			newVal *= inStdDev;
			newVal += 1;
			newVal *= mean;
			mean = newVal;
		}

		return mean;
	}
}
