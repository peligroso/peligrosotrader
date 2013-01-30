package org.juxtapose.bundle.peligrosotrader.gui2.charts;

import java.util.Vector;

import peligrosotrader.util.db.DBHandler;
import peligrosotrader.util.db.Sample;

public class SampleDataHandler {

	public SampleDataHandler() {
		try{
			Vector<Sample> samples = DBHandler.getSamples("MOD1.ST", "2007-01-28");
			
			for(Sample s : samples) {
				System.out.println(s.order+" sample of \""+s.ticker+"\" was at "+s.last);
			}
		} catch (Exception e){ e.printStackTrace();}
	}
}
