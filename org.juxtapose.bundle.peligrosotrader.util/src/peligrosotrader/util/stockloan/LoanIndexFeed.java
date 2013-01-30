package peligrosotrader.util.stockloan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class LoanIndexFeed {
	
	static SimpleDateFormat DATE_FORMAT;

	static{
		DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	}

	public static Vector<Sample> createShortIndex(String inEquity)
	{
		Vector<Sample> graph = new Vector<Sample>();
		try{
			for(int x = 1; x < 33; x++){
				BufferedReader buff = new BufferedReader(new FileReader("C:/peligroso/stock_loan/wkr0009-"+x+".csv"));
				String line = buff.readLine();


				int i = 0;	

				String dateStr = null;

				while(line != null)
				{

					if(i == 0)
					{
						String split[] = line.split("\"");
						String split2[] = split[1].split(" ");
						dateStr = split2[1];
						System.out.println(dateStr);
					}
					else
					{
						String commaSplit[] = line.split(",");
						if(commaSplit.length > 0)
						{
							String name = commaSplit[0].replace("\"", "");
							if(name.equals(inEquity))
							{
								String numberOfStocksStr = commaSplit[3];
								String valueStr = commaSplit[5];
								numberOfStocksStr = numberOfStocksStr.replaceAll("[^0-9]", "");
								valueStr = valueStr.replaceAll("[^0-9]", "");
								Long value = Long.parseLong(valueStr);
								Long numberofStocks = Long.parseLong(numberOfStocksStr);
								numberofStocks /= 300000;
								value /= 10000;
								System.out.println(numberofStocks);
								java.util.Date date = DATE_FORMAT.parse(dateStr);
								Sample samp = new Sample();
								samp.date = new Date(date.getTime());
								samp.setPriceValues(numberofStocks);
								graph.add(samp);
							}
						}
					}


					line = buff.readLine();
					i++;
				}
			}
		}catch(Exception e){e.printStackTrace();}
		return graph;
	}
	
	public static void main(String args[])
	{
		createShortIndex("Total no of shares:");		
	}
}
