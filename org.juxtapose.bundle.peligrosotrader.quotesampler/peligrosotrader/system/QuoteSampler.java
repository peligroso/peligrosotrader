package peligrosotrader.system;

import peligrosotrader.db.PropReader;
import peligrosotrader.db.Properties;
import peligrosotrader.feeds.Timer;

public class QuoteSampler {

	public static void main(String args[]){
		Properties.reset();
		PropReader.readProps();
		new Timer();
	}
}
