package org.juxtapose.bundle.peligrosotrader.gui2.charts;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import org.jfree.data.general.CombinedDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.XYDataset;

import peligrosotrader.util.db.Sample;

public class SwingDatasetCreator {
	
	public static TimeSeries createTimeserie(Vector<Sample> inSamples, String inName){
		
		TimeSeries ts = new TimeSeries(inName, Day.class);
    	int hour = 8;
    	int minute = 50;
		for (Sample sample : inSamples){
    		

    		ts.add(new Day(sample.date), new Double(sample.last));
    	    
		}
        return ts;
    }
	
	public static XYDataset createDataset(Vector<Vector<Sample>> inVectors, ArrayList<String> inGraphNames) {
	
		TimeSeriesCollection dataset = new TimeSeriesCollection();

		int nameIndex = 0;
		for (Vector<Sample> sampVec : inVectors){
			String name = "";
			if(inGraphNames.size() > nameIndex)
				name = inGraphNames.get(nameIndex);

			
			dataset.addSeries(createTimeserie(sampVec, name));
			nameIndex++;
		}
		return dataset;
	}
	
	public static XYDataset createDataset(Vector<Sample> inVectors, String inGraphName) {
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();

		dataset.addSeries(createTimeserie(inVectors, inGraphName));
		
		return dataset;
	}
	
	
	
	public static OHLCDataset createCSDataset(Vector<Vector<Sample>> inVectors){
	
		Vector<Sample> sampVec = inVectors.get(0);
		OHLCDataItem[] items = new OHLCDataItem[sampVec.size()];
		int index = 0;
		for (Sample sample : sampVec){
			OHLCDataItem item = new OHLCDataItem(sample.date, sample.open, sample.highest, sample.lowest, sample.close, sample.volume);
			items[index] = item;
			index++;
		}
		
		DefaultOHLCDataset dataset = new DefaultOHLCDataset(new Double(1), items);
		
		return dataset;
		
	}
	
//	public static CombinedDataset createCombinedDataset(Vector<Vector<Sample>> inVectors){
//		
//		CombinedDataset cDataset = new CombinedDataset();
//		
//		
//		for (int i = 0; i < inVectors.size(); i++){
//			
//			if(i == 0){
//				OHLCDataset csDataset = createCSDataset(inVectors);
//				cDataset.add(csDataset);
//			}
//			else{
//				XYDataset xyDataset = createFlatCsDataSet(inVectors.get(i));
//				cDataset.add(xyDataset);
//			}
//		}
//		
//		return cDataset;
//		
//	}
//	
	public static CombinedDataset createCombinedDataset(Vector<Vector<Sample>> inVectors, ArrayList<String> inNames){
		
		CombinedDataset cDataset = new CombinedDataset();
				
		OHLCDataset csDataset = createCSDataset(inVectors);
		cDataset.add(csDataset);
		
		inVectors.remove(0);
		
		XYDataset xyDataset = createDataset(inVectors, inNames);
		cDataset.add(xyDataset);			
		
		return cDataset;
		
	}
	
	public static OHLCDataset createFlatCsDataSet(Vector<Sample> inVector){
	
		Vector<Sample> sampVec = inVector;
		OHLCDataItem[] items = new OHLCDataItem[sampVec.size()];
		int index = 0;
		for (Sample sample : sampVec){
			Double last = new Double(sample.last);
			OHLCDataItem item = new OHLCDataItem(sample.date, last, last, last, last, 0);
			items[index] = item;
			index++;
		}
		
		DefaultOHLCDataset dataset = new DefaultOHLCDataset(new Double(1), items);
		
		
		return dataset;
		
	}

}
