import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;


public class XACT_Calculator_AV {

	static String base_URL = "http://www.xact.se/public/uploads/boFiles/";
	static String BEAR = "xactbear_current.txt";
	static String BULL = "xactbull_current.txt";
	
	static String FUTURE_URL = "http://www.avanza.se/aza/aktieroptioner/optioner/optioner.jsp?instrtype=6&underlyingInstrumentId=9270&operation=disp";

	
	static String bull_nav="--";
	static String bull_cpb="--";
	static String bull_fundD="--";
	static String bull_fClose="--";
	static String bull_nom="--";
	static String bull_future="--";
	
	static String bear_nav="--";
	static String bear_cpb="--";
	static String bear_fundD="--";
	static String bear_fClose="--";
	static String bear_nom="--";
	static String bear_future="--";
	
	public static void updateXactParams(String inUrl)
	{
		try{
			URL url = new URL(base_URL+inUrl);

			URLConnection connection = url.openConnection();
			connection.setDoOutput(false);

			InputStream is = connection.getInputStream();
			BufferedReader in = new BufferedReader( new InputStreamReader(is));

			String line;

			int index = 1;
			line = in.readLine();
			do{	

				String[] tokenz = line.split("\t");
				
				if(BEAR.equals(inUrl)){
					switch(index){
					case 2: bear_nav = tokenz[1]; break;
					case 3: bear_cpb = tokenz[1]; break;
					case 7: bear_fundD = tokenz[1]; break;
					case 13: bear_future = tokenz[1]; break;
					case 16: bear_nom = tokenz[1]; break;
					case 17: bear_fClose = tokenz[1]; 

					}
				}
				else if(BULL.equals(inUrl)){
					switch(index){
					case 2: bull_nav = tokenz[1]; break;
					case 3: bull_cpb = tokenz[1]; break;
					case 7: bull_fundD = tokenz[1]; break;
					case 13: bull_future = tokenz[1]; break;
					case 16: bull_nom = tokenz[1]; break;
					case 17: bull_fClose = tokenz[1]; 

					}
				}

//				System.out.println(line);
				index++;
				line = in.readLine();
			}while(line != null);

			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static double[] calculateXact(String inUrl, double[] inFutureVal){

		try{
			
			double endValBid = 0;
			double endValAsk = 0;
			if(inUrl.equals(BULL))
			{
				endValBid = calcValue(new Double(bull_cpb), inFutureVal[0], new Double(bull_fClose), new Double(bull_nom), new Double(bull_fundD));
				endValAsk = calcValue(new Double(bull_cpb), inFutureVal[1], new Double(bull_fClose), new Double(bull_nom), new Double(bull_fundD));
			}
			else if(inUrl.equals(BEAR))
			{
				endValBid = calcValue(new Double(bear_cpb), inFutureVal[1], new Double(bear_fClose), new Double(bear_nom), new Double(bear_fundD));
				endValAsk = calcValue(new Double(bear_cpb), inFutureVal[0], new Double(bear_fClose), new Double(bear_nom), new Double(bear_fundD));
			}
			
			return new double[]{ endValBid, endValAsk};
		}catch(Exception e){
			e.printStackTrace();
		}
		return new double[]{0, 0};
	}
	
	
	public static double[] getFutureValue(){
		
		try{
			URL url = new URL(FUTURE_URL);

			URLConnection connection = url.openConnection();
			connection.setDoOutput(false);

			InputStream is = connection.getInputStream();
			BufferedReader in = new BufferedReader( new InputStreamReader(is));
			
			String futureBuy = "--";
			String futureSell = "--";
			String futureLast = "--";
			
			String line = in.readLine();
			if(line != null)
				do{	
					//System.out.println(line);
					if(line.contains(bear_future)){


						String[] divOnFut = line.split(bear_future);
						String[] splits = divOnFut[2].split("[<>]");
						futureBuy = splits[14];
						futureBuy = futureBuy.replace(',', '.');
						futureSell = splits[18];
						futureSell = futureSell.replace(',', '.');
						futureLast = splits[22];
						futureLast = futureLast.replace(',', '.');
						break;
					}
					line = in.readLine();
				}while(line != null);
//			
//			System.out.println("buy is: "+futureBuy);
//			System.out.println("sell is: "+futureSell);
//			System.out.println("last is: "+futureLast);
			
//			double midValue = (new Double(futureBuy) + new Double(futureSell))/2;
			
			return new double[]{ Double.parseDouble(futureBuy), Double.parseDouble(futureSell) };
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return new double[]{ 0d, 0d };
	}
	
	private static String parseOutValue(String inString){
		String[] split = inString.split("[<>]");
		return split[2];
	}
	
	public static double calcValue(double inCPB, double inFuture, double inFutureClose, double inNominal, double inDiv){
		return (inCPB + (inFuture - inFutureClose) * (inNominal *100)) / inDiv;

	}
	
	
	public static String getLastDate(){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
			cal.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH - 3);
		else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
			cal.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH - 3);
		return "";
	}
	
	public static  double[] calculate(String inType){
		double[] futureVal = getFutureValue();
		if(futureVal[0] == 0 || futureVal[1] == 0)
			return new double[]{0, 0};
		return calculateXact(inType, futureVal);
	}
	
	
	
	public static double roundToPip(double inVal){
		
		inVal = inVal * 10;
		double toRound = inVal % 1;
		
		int lastPip = 0;
		if(toRound > 0.24)
			lastPip = 5;
		
		inVal = (int)inVal;
		
		inVal = inVal * 10;
		inVal += lastPip;
		
		return inVal / 100;
	}
	
	public static void main(String[] args){
		
		updateXactParams(BEAR);
		updateXactParams(BULL);
		
		double[] futureVal = getFutureValue();
		
		double bearWorth[] = calculateXact(BEAR, futureVal);
		double bullWorth[] = calculateXact(BULL, futureVal);
		
		double bearBid = bearWorth[0];
		bearBid = roundToPip(bearBid);
		
		double bearAsk = bearWorth[1];
		bearAsk = roundToPip(bearAsk);
		
		double bullBid = bullWorth[0];
		bullBid = roundToPip(bullBid);
		
		double bullAsk = bullWorth[1];
		bullAsk = roundToPip(bullAsk);
		
		System.out.println("XACT-BEAR is worth: "+bearWorth+" marginized spread: "+bearBid+" : "+bearAsk);
		System.out.println("XACT-BULL is worth: "+bullWorth+" marginized spread: "+bullBid+" : "+bullAsk);
		
	}
}
