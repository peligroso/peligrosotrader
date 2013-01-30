package peligrosotrader.util.feeds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import peligrosotrader.util.Constants;
import peligrosotrader.util.db.Sample;

public class ReutersSthlm {

	static SimpleDateFormat m_dateFormat = new SimpleDateFormat("yyyyMMdd");
	
	public static Vector<Sample> parseFile( String inFileName, Calendar inStart, Calendar inEnd )
	{
		try
		{
			File file = new File(Constants.REUTERS_DIR+"\\"+inFileName);
			BufferedReader br = new BufferedReader( new FileReader(file));
		
			Vector<Sample> samps = new Vector<Sample>();
			
			String line = br.readLine();
			
//			<TICKER>,<PER>,<DTYYYYMMDD>,<TIME>,<OPEN>,<HIGH>,<LOW>,<CLOSE>,<VOL>,<OPENINT>
//			BEGR.ST,D,20061124,000000,66.7500,67.2500,64.5000,65.0000,77919,0
			
			while( line != null )
			{
				int i = 0;
				if(!line.contains("<TICKER>"))
				{
					String split[] = line.split(",");
					
					Date date = new Date(m_dateFormat.parse(split[2]).getTime());
					
					if( date.getTime() > inEnd.getTimeInMillis() )
						return samps;
					
					if( inStart.getTimeInMillis() < date.getTime())
					{
						Sample samp = new Sample(split[0], split[7], split[7], split[7], i, date, new Time(date.getTime()) );
						samp.highest = new Double(split[5]);
						samp.lowest = new Double(split[6]);
						samp.open = new Double(split[4]);
						
						samps.add(samp);
						i++;
					}
				}
				line = br.readLine();
			}
			
			return samps;
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
