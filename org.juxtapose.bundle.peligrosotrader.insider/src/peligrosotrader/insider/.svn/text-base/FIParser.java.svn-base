package peligrosotrader.insider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

public class FIParser {

	static String URL = "http://www.fi.se/insiderlistan/insynslistan.asp?d=";
	
	static String INNEHAVARE = "Innehavare";
	
	
	static void parseFI(){
		
		String date = "20071025";
		
		try
		{
			URL url = new URL(URL+date);

			URLConnection connection = url.openConnection();
			connection.setDoOutput(false);

			InputStream is = connection.getInputStream();
			BufferedReader in = new BufferedReader( new InputStreamReader(is));

			String line;

			int index = 1;
			line = in.readLine();
			
			boolean startParsing = false;
			boolean serchInstr = false;
			do{	
				if(line.contains("Ändring av innehav"))
					startParsing = true;
				if(startParsing){
					if(line.contains("Aktiemarknadsbolag"))
					{
						line = in.readLine();
						line = in.readLine();
						line = in.readLine();
						System.out.println(parseOutCompany(line));
					}
					if(line.contains("Anm&auml;lare, befattning"))
					{
						line = in.readLine();
						line = in.readLine();
						line = in.readLine();
						line = in.readLine();
						String[] insiderInfo = parseOutInsiderInfo(line);
						System.out.print("buyer :"+insiderInfo[0]+" "+insiderInfo[1]);
						serchInstr = true;
					}
					if(serchInstr && line.contains("<TR valign=\"top\">"))
					{
						String innehavare = parseOutValue(in.readLine());
						String art = parseOutValue(in.readLine());
						String type = parseOutValue(in.readLine());
						String datum = parseOutValue(in.readLine());
						String change = parseOutValue(in.readLine());
						
						System.out.println("buys "+change+" "+type);
					}
					if(line.contains("Totalt anm&auml;lt innehav "))
						serchInstr = false;
				}
				line = in.readLine();
			}while(line != null);

			in.close();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static String parseOutCompany(String inLine){
		String[] splits = inLine.split("[<>]");
		return splits[2];
	}
	
	private static String parseOutValue(String inLine){
		String[] splits = inLine.split("[<>]");
		return splits[2];
	}
	
	private static String[] parseOutInsiderInfo(String inLine){
		String[] splits = inLine.split(",");
		for(int i = 0; i < splits.length; i++){
			splits[i] = splits[i].replaceAll("[ \t]", "");
		}
		return splits;
	}
	public static void main(String[] args) {
		parseFI();
	}
}
