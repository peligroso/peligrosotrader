import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;


public class XACT_Calculator {
	
	static String base_URL = "http://www.xact.se/public/uploads/boFiles/";
	static String BEAR = "xactbear_current.txt";
	static String BULL = "xactbull_current.txt";
	
	static String FUTURE_URL = "http://www.omxgroup.com/nordicexchange/marknaden/kursinformation/microsite/Options_and_Futures_Index/?InstrumentId=SSESE0000337842";
	static String FUTURE = "OMXS307I";
	
	public static double calculateXact(String inUrl, double inFutureVal){

		try{
			URL url = new URL(base_URL+inUrl);

			URLConnection connection = url.openConnection();
			connection.setDoOutput(false);

			InputStream is = connection.getInputStream();
			BufferedReader in = new BufferedReader( new InputStreamReader(is));
			
			String line;
			String nav="--";
			String cpb="--";;
			String fundD="--";;
			String fClose="--";;
			String nom="--";;
			
			int index = 1;
			line = in.readLine();
			do{				
				String[] tokenz = line.split("\t");
				
				switch(index){
				case 2: nav = tokenz[1]; break;
				case 3: cpb = tokenz[1]; break;
				case 7: fundD = tokenz[1]; break;
				case 13: fClose = tokenz[6]; 
				nom = tokenz[5];
				}
				
//				System.out.println(line);
				index++;
				line = in.readLine();
			}while(line != null);
			
			in.close();
			
//			System.out.println("NAV : "+nav);
//			System.out.println("Cash per block : "+cpb);
//			System.out.println("fund divisor : "+fundD);
//			System.out.println("fClose : "+fClose);
//			System.out.println("Nominal : "+nom);
			

			double endVal = calcValue(new Double(cpb), inFutureVal, new Double(fClose), new Double(nom), new Double(fundD));
			
			return endVal;
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	
	public static double getFutureValue(){
		
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
			do{	
				//System.out.println(line);
				if(line.endsWith(FUTURE+"</td>")){
					in.readLine();
					futureBuy = parseOutValue(in.readLine());
					futureSell = parseOutValue(in.readLine());
					futureLast = parseOutValue(in.readLine());
				}
				line = in.readLine();
			}while(line != null);
//			
//			System.out.println("buy is: "+futureBuy);
//			System.out.println("sell is: "+futureSell);
//			System.out.println("last is: "+futureLast);
			
			double midValue = (new Double(futureBuy) + new Double(futureSell))/2;
			return midValue;
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
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
	
	public static  double calculate(String inType){
		double futureVal = getFutureValue();
		if(futureVal == 0)
			return 0;
		return calculateXact(inType, futureVal);
	}
	
	public static void main(String[] args){
		double futureVal = getFutureValue();
		System.out.println("XACT-BEAR is worth: "+calculateXact(BEAR, futureVal));
		System.out.println("XACT-BULL is worth: "+calculateXact(BULL, futureVal));
	}

}
