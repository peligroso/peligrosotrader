package peligrosotrader.ruleevaluation.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.feeds.YahooHistory;

public class OmxFeed {

	public static ArrayList<String> getOmxTickers(){
		ArrayList<String> ret = new ArrayList<String>();
		try{
			BufferedReader buff = new BufferedReader(new FileReader("C:/peligrosotrader/SAX.txt"));
			String line = buff.readLine();
			int i = 0;		
			while(line != null){
				System.out.println(line);
				ret.add(line);
				line = buff.readLine();
				i++;
			}
			System.out.println(i+": st aktier");
		} catch (IOException e){e.printStackTrace();}
		return ret;
	}
	
	public static Vector<Vector<Sample>> getSampleMatrix(ArrayList<String> inTickers, Calendar inStart, Calendar inEnd){
		
		Vector<Vector<Sample>> ret = new Vector<Vector<Sample>>();
		for(String ticker : inTickers){
			try{
				System.out.println("fetching data for stock "+ticker );
				Vector<Sample> graph = YahooHistory.getHistoryGraph(ticker, inStart, inEnd);
				ret.add(graph);
			}catch(Exception e){System.out.println(ticker+" not found");}
		}
		System.out.println("Done reading graphs into memory");
		return ret;
	}
}
