package peligrosotrader.util.feeds;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class YahooCleaner {
	
	
	static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	static Map<String, SplitContext> SPLIT_MAP= new HashMap<String, SplitContext>();
	
	static{
		
		try{
			SPLIT_MAP.put("IFSB.ST", new SplitContext(DATE_FORMAT.parse("2008-05-2"), 10d));
			SPLIT_MAP.put("IMPC.ST", new SplitContext(DATE_FORMAT.parse("2007-10-17"), 0.25d));
			SPLIT_MAP.put("NOCMB.ST", new SplitContext(DATE_FORMAT.parse("2008-05-14"), 10d));
			SPLIT_MAP.put("ATCOB.ST", new SplitContext(DATE_FORMAT.parse("2007-05-18"), 0.4d));
			SPLIT_MAP.put("43577.ST", new SplitContext(DATE_FORMAT.parse("2009-05-18"), 50d));
			SPLIT_MAP.put("AOIL-SDB.ST", new SplitContext(DATE_FORMAT.parse("2009-06-08"), 20d));
			SPLIT_MAP.put("SAS.ST", new SplitContext(DATE_FORMAT.parse("2009-03-16"), 0.2d));
			
			
		}catch(ParseException pex){
			pex.printStackTrace();
		}
	}
	
	public static Vector<Sample> clean(String inTicker, Vector<Sample> inGraph){
		
		SplitContext sc = SPLIT_MAP.get(inTicker.toUpperCase());
		if(sc == null)
			return inGraph;
		else
		{
			for(Sample samp : inGraph){
				if(samp.date.getTime() < sc.m_date.getTime()){
					
					samp.ask = Double.toString(Double.parseDouble(samp.ask) * sc.m_factor);
					samp.bid = Double.toString(Double.parseDouble(samp.ask) * sc.m_factor);
					samp.close *= sc.m_factor;
					samp.highest *= sc.m_factor;
					samp.last = Double.toString(Double.parseDouble(samp.last) * sc.m_factor);
					samp.lowest *= sc.m_factor;
					samp.open *= sc.m_factor;
				}
			}
			return inGraph;
		}
	}
}

class SplitContext{
	
	public Date m_date;
	public double m_factor;
	
	SplitContext(Date inDate, Double inFactor){
		m_date = inDate;
		m_factor = inFactor;
	}
}
