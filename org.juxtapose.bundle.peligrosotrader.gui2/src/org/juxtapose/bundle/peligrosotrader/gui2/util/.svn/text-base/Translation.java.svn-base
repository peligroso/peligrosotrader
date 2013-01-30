package org.juxtapose.bundle.peligrosotrader.gui2.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translation {
	
	static Map<String, String> m_tickerToCompany = new HashMap<String, String>();

	static {

		try{
			
			BufferedReader buff = new BufferedReader(new FileReader("D:/peligroso/tickers2.txt"));
			String line = buff.readLine();

			while(line != null){

				String[] split = line.split(":");
				if(split.length == 2)
					m_tickerToCompany.put(split[0], split[1]);
				
				line = buff.readLine();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String getCompany(String inTicker){
		return m_tickerToCompany.get(inTicker);
	}

	public static void main(String[] args){

		try{
			BufferedReader buff = new BufferedReader(new FileReader("D:/peligroso/tickers.txt"));
			String line = buff.readLine();
//			StringBuffer sb = new StringBuffer();

			FileWriter fw = new FileWriter("D:/peligroso/tickers2.txt");
			while(line != null){
				System.out.println(line);


				if(line.contains("WSIB"))
					System.out.println("wsib");
				Pattern pattern = Pattern.compile(".*\"(.*)\".*>(.*)<.*");
				Matcher m = pattern.matcher(line);
				if(m.matches()){
					String ticker = m.group(1);
					String company = m.group(2);

					fw.write(ticker+":"+company+"\n");
				}
				line = buff.readLine();

			}
			fw.close();
		}catch(Exception e){
			e.printStackTrace();
		}



	}

}


