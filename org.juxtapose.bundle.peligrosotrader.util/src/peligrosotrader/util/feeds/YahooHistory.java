package peligrosotrader.util.feeds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.Vector;

import peligrosotrader.util.db.Sample;


public class YahooHistory
{
	 static final String YAHOO_URL = "http://ichart.finance.yahoo.com/table.csv?";


	 public static Vector<Sample> getHistoryGraph(String inInstrument, Calendar inStart, Calendar inEnd) throws Exception
	 {
		 StringBuffer sb = new StringBuffer(64);
		 sb.append(YAHOO_URL);
		 sb.append("s=");
		 sb.append(inInstrument);
		 sb.append("&a=");
		 sb.append(inStart.get(Calendar.MONTH));
		 sb.append("&b=");
		 sb.append(inStart.get(Calendar.DAY_OF_MONTH));
		 sb.append("&c=");
		 sb.append(inStart.get(Calendar.YEAR));

		 sb.append("&d=");
		 sb.append(inEnd.get(Calendar.MONTH));
		 sb.append("&e=");
		 sb.append(inEnd.get(Calendar.DAY_OF_MONTH));
		 sb.append("&f=");
		 sb.append(inEnd.get(Calendar.YEAR));
	     sb.append("&ignore=.csv");
	     String strURL = sb.toString();

		 URL url = new URL(strURL);

		 URLConnection connection = url.openConnection();
		 connection.setDoOutput(false);

		 InputStream is = connection.getInputStream();
		 BufferedReader in = new BufferedReader( new InputStreamReader(is));
		 String headers = null;

		 headers = in.readLine();
//		 System.out.println(headers);
		 
		 Vector<Sample> ret = new Vector<Sample>();

		 do
		 {

			 String data = null;

			 try
			 {
				 data = in.readLine();
			 }catch( IOException e)
			 {
				 break;
			 }
			 if( data == null )
				 break;
//			 System.out.println(data);
			 StringTokenizer strTok = new StringTokenizer(data,",");

			 Vector<String>dataArr = new Vector<String>();

			 while( strTok.hasMoreElements() )
			 {
				 String dataVal = strTok.nextToken();
				 dataArr.add(dataVal);
			 }
			 Sample samp = new Sample(inInstrument, dataArr.get(4), "0", "0", 1, Date.valueOf(dataArr.get(0)), null);
			 samp.open = new Double(dataArr.get(1));
			 samp.highest = new Double(dataArr.get(2));
			 samp.lowest = new Double(dataArr.get(3));
			 samp.close = new Double(dataArr.get(4));
			 samp.volume = new Double(dataArr.get(5));
			 
			 ret.add(samp);

		 }
		 while( true );

		 in.close();
		 
		 Vector<Sample> reverse = new Vector<Sample>();
			for(int i = ret.size()-1; i > -1; i--)
				reverse.add(ret.get(i));
			ret = reverse;

		 return YahooCleaner.clean(inInstrument, ret);
	 }

	 
}
