package org.juxtapose.bundle.peligrosotrader.gui2.charts;

import java.sql.Date;
import java.util.Calendar;
import java.util.Vector;

import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import peligrosotrader.util.db.Sample;

public class DataSetCreator {

	public static TimeSeries createTimeserie(Vector<Sample> inSamples, String inName, Date inDate){
		
		TimeSeries ts = new TimeSeries(inName, Minute.class);
    	int hour = 8;
    	int minute = 50;
		for (Sample sample : inSamples){
    		
    		Calendar cal = Calendar.getInstance();
    		cal.setTimeInMillis(inDate.getTime());
    		Calendar time = Calendar.getInstance();
    		if(sample.time != null)
    			time.setTimeInMillis(sample.time.getTime());
    		else{
    			time.set(Calendar.HOUR_OF_DAY, hour);
    			time.set(Calendar.MINUTE, minute);
    		}
    		
    		hour = time.get(Calendar.HOUR_OF_DAY);
    		minute = time.get(Calendar.MINUTE);
    		
    	
    		ts.add(new Minute(minute, hour, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR)), new Double(sample.last));
    		minute += 5;
			if (minute > 60){
				minute = 5;
				hour++;
			}
		}
        return ts;
    }
	
	public static XYDataset createDataset(Vector<Vector<Sample>> inVectors, Date inDate) {
	
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		Date actDate = null;
		for (Vector<Sample> sampVec : inVectors){
			String name = " ";
			if(!sampVec.isEmpty()){
				actDate = sampVec.get(0).date;
				name = sampVec.get(0).date.toString();
				if(name == null)
					name = " ";
			}
			if(inDate == null)
				dataset.addSeries(createTimeserie(sampVec, name, actDate));
			else
				dataset.addSeries(createTimeserie(sampVec, name, inDate));
		}
		return dataset;
	}
}
