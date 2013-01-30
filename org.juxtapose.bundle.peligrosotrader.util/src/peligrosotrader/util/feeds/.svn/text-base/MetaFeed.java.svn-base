package peligrosotrader.util.feeds;

import java.util.Calendar;
import java.util.Vector;

import peligrosotrader.util.Constants;
import peligrosotrader.util.TradeUtil;
import peligrosotrader.util.db.Sample;
import peligrosotrader.util.marketfeeds.ReutersSthlmFeed;
import peligrosotrader.util.risk.VAR;
import peligrosotrader.util.ta.MathFunctions;

public class MetaFeed {

	public static Vector<Sample> getGraph(String inInstrument, Calendar inStart, Calendar inEnd) throws Exception
	{
		boolean percent = inInstrument.startsWith("%");
		if(percent)
			inInstrument = inInstrument.substring(1);
		
		Vector<Sample> ret = null;
		
		if(inInstrument.equals("random"))
		{
			ret = TradeUtil.getRandomChart(300, inStart, inEnd);
		}
		else if(inInstrument.endsWith("="))
		{
			ret = OandaHttpTimeseries.getTimeSerie(inInstrument, inStart, inEnd);
		}
		else{
			
			String randomPart = null;
			if(inInstrument.contains(Constants.RAND))
			{
				String split[] = inInstrument.split(Constants.RAND);
				
				inInstrument = split[0];
				randomPart = split[1];
			}
			
			Vector<Sample> vec = YahooHistory.getHistoryGraph(inInstrument, inStart, inEnd);
//			Vector<Sample> vec = ReutersSthlm.parseFile(inInstrument, inStart, inEnd);
			
			Calendar today = Calendar.getInstance();
			int year = today.get(Calendar.YEAR);
			int month = today.get(Calendar.MONTH);
			int day = today.get(Calendar.DAY_OF_MONTH);

			today.clear();
			today.set(year, month, day);

			if(inEnd.equals(today)){
				System.out.println("today");
				Sample todaySamp = YahooFeed.getTodaySample(inInstrument, false);
				if(todaySamp != null)
					vec.add(todaySamp);
			}
			
			ret = vec;
			
			if(randomPart != null)
			{
				double[] chart = Sample.extractValues(ret);
				Double stdDev = MathFunctions.getAvgPercentMove(chart);
				
				Integer randomDays = Integer.parseInt(randomPart);
				
				Sample last = ret.lastElement();
				Double mean = last.close;
				
				/**TEST**/
				VAR var = new VAR();
				var.calcVAR(stdDev, mean, randomDays);
				
				for(Double per : var.getDistribution().keySet() )
				{
					Integer samples = var.getDistribution().get( per );
					System.out.println("less than "+per+" : "+samples);
				}
				/**END TEST**/
				
				Calendar randStart = Calendar.getInstance();
				randStart.setTimeInMillis(inEnd.getTimeInMillis());
				randStart.set(Calendar.DAY_OF_MONTH, randStart.get(Calendar.DAY_OF_MONTH)+1);
				
				Calendar randEnd = Calendar.getInstance();
				randEnd.setTimeInMillis(randStart.getTimeInMillis());
				randEnd.set(Calendar.DAY_OF_MONTH, randEnd.get(Calendar.DAY_OF_MONTH)+randomDays);
				
				Vector<Sample> randVec = TradeUtil.getRandomChart(randStart, randEnd, mean, stdDev);
				
				ret.addAll(randVec);
				
			}
		}
		
		if(percent && ret != null){
			
			return TradeUtil.convertToProcent(ret);
		}
		
		return ret;

	}
}
