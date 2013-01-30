package peligrosotrader.util.feeds;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import peligrosotrader.util.db.Sample;


/**

 * @author Pontus

 *

 */

public class OandaHttpTimeseries

{

	public static String FX_URL = "http://www.oanda.com/convert/fxhistory";
	
	public static Long DAYS_500 = 1000l * 60l * 60l * 24l * 450l;


	public static DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yy");

	/**
	 * @param inTicker
	 * @param inStart
	 * @param inEnd
	 * @return
	 */
	public static Vector<Sample> getTimeSerie(String inTicker, Calendar inStart, Calendar inEnd)
	{
		String ccy1 = inTicker.substring(0, 3);
		String ccy2 = inTicker.substring(3, 6);
		
		return getTimeSerie( ccy1, ccy2, inStart, inEnd ); 
	}
		

	/**
	 * @param inCcy1
	 * @param inCcy2
	 * @param inStart
	 * @param inEnd
	 * @return
	 */
	public static Vector<Sample> getTimeSerie(String inCcy1, String inCcy2, Calendar inStart, Calendar inEnd){


		URL url;

		URLConnection urlConn;


		DataOutputStream printout;

		DataInputStream input;


		Vector<Sample>timeserieMatrix = new Vector<Sample>();
		
		if(inEnd.getTimeInMillis() - inStart.getTimeInMillis() > DAYS_500){
			
			Calendar chunkDelim = Calendar.getInstance();
			chunkDelim.setTimeInMillis(inEnd.getTimeInMillis() - DAYS_500);
			
			Vector<Sample> firstSeries = getTimeSerie( inCcy1, inCcy2, inStart, chunkDelim );
			
			timeserieMatrix.addAll(firstSeries);
			
			inStart.setTimeInMillis(chunkDelim.getTimeInMillis() + (1000 * 60 * 60 * 24));
		}


		String startDate = DATE_FORMAT.format(new Date(inStart.getTimeInMillis()));

		String endDate = DATE_FORMAT.format(new Date(inEnd.getTimeInMillis()));


		try{

			url = new URL (FX_URL);

//			URL connection channel.

			urlConn = url.openConnection();

//			Let the run-time system (RTS) know that we want input.

			urlConn.setDoInput (true);

//			Let the RTS know that we want to do output.

			urlConn.setDoOutput (true);

//			No caching, we want the real thing.

			urlConn.setUseCaches (false);

//			Specify the content type.

			urlConn.setRequestProperty

			("Content-Type", "application/x-www-form-urlencoded");

//			Send POST output.

			printout = new DataOutputStream (urlConn.getOutputStream ());


			String content = "lang=" + URLEncoder.encode ("en") +

			"&result=" + URLEncoder.encode ("1") +

			"&date1=" + URLEncoder.encode ( startDate ) +

			"&date=" + URLEncoder.encode ( endDate ) +

			"&date_fmt=" + URLEncoder.encode ("us") +

			"&exch=" + URLEncoder.encode (inCcy1) +

			"&expr=" + URLEncoder.encode (inCcy2) +

			"&margin_fixed=" + URLEncoder.encode ("0") +

			"&format=" + URLEncoder.encode ("CSV") +

			"&SUBMIT=" + URLEncoder.encode ("Get Table");






			printout.writeBytes (content);

			printout.flush ();

			printout.close ();

//			Get response data.

			input = new DataInputStream (urlConn.getInputStream ());

			String str;

			boolean inTable = false;


			while (null != ((str = input.readLine())))

			{

				if( str.contains("<PRE>") || str.contains("</PRE>") ){

					inTable = !inTable;

					continue;

				}


				if(inTable){

					String split[] = str.split(",");

					Sample samp = new Sample();
					try{
						Double value = Double.parseDouble(split[1]);
						Date date = DATE_FORMAT.parse(split[0]);
						
						samp.setPriceValues(value);
						samp.date = new java.sql.Date(date.getTime());
					}
					catch(Exception e)
					{
						continue;
					}
					
					timeserieMatrix.add(samp);

				}

			}


		}catch(Exception e){

			e.printStackTrace();

		}


		return timeserieMatrix;


	}


	public static void printTimeSerie(Vector<Sample> inSerie){


		for(Sample samp : inSerie){

			System.out.println(DATE_FORMAT.format(samp.date)+", "+samp.close);

		}


	}


	public static void main(String[] args)

	{

		GregorianCalendar end = new GregorianCalendar(); 

		GregorianCalendar start = new GregorianCalendar(2007, Calendar.JANUARY, 1); 

		printTimeSerie(getTimeSerie("EURSEK=", start, end));
	}


}
