package peligrosotrader.util.marketfeeds;

import java.util.Calendar;
import java.util.Vector;

import peligrosotrader.util.db.Sample;

public interface IMarketFeed {

	public void setDates(Calendar inStart, Calendar inEnd);
	public Vector<Vector<Sample>> getSampleMatrix();
	public Vector<Sample> getNextGraph(boolean inAddToday);

}
