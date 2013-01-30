package peligrosotrader.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class FileHandler {
	
	public static String PATH = "D:/kurser/";
	
	public static Vector<Sample> getFileQuotes(String inTicker, String inFromDate, String inToDate){
		
		Calendar fromDate = TradeUtil.parseDateString(inFromDate);
		Calendar toDate = TradeUtil.parseDateString(inToDate);
		Vector<Sample> ret = new Vector<Sample>();
		try{
			BufferedReader buff = new BufferedReader(new FileReader(PATH+inTicker+".csv"));
			String line = buff.readLine();
			int i = 0;
			Calendar cal = Calendar.getInstance();			
			while(line != null){
	
				line = line.replace("\"", "");
				String cells[] = line.split(";");
				String dateFormats[] = cells[0].split("-");
				cal.set(Calendar.YEAR, new Integer(dateFormats[0]));
				cal.set(Calendar.MONTH, new Integer(dateFormats[1])-1);
				cal.set(Calendar.DAY_OF_MONTH, new Integer(dateFormats[2]));
				String last = cells[1];
//				long now = cal.getTimeInMillis();
//				long from = fromDate.getTimeInMillis();
				if(cal.getTimeInMillis() > fromDate.getTimeInMillis() && cal.getTimeInMillis() < toDate.getTimeInMillis())
					ret.add(new Sample("ericb", last, last, last, i, new Date(cal.getTimeInMillis()), new Time(cal.getTimeInMillis())));
				line = buff.readLine();
				i++;
			}
		} catch (IOException e){e.printStackTrace();}
		return ret;
	}
}
