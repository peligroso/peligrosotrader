import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class XACT_TRACKER extends Thread{

	public static String XACT_FILE = "D:\\peligroso\\test-workspace\\peligroso\\WebContent\\xact.html";
	double bullEntry = 0;
	double bearEntry = 0;

	double bullOrder = 0;
	double bearOrder = 0;

	String lastBullClosure =  "0";
	String lastBearClosure =  "0";

	boolean running = false;

	ArrayList<Result> results = new ArrayList<Result>();

	public void act(){
		double bullValuePair[] = XACT_Calculator_AV.calculate(XACT_Calculator_AV.BULL);

		double bearValuePair[] = XACT_Calculator_AV.calculate(XACT_Calculator_AV.BEAR);

		if(bearValuePair[0] == 0 || bullValuePair[0] == 0 || bearValuePair[1] == 0 || bullValuePair[1] == 0)
			return;

		try
		{
			FileWriter writer = new FileWriter(XACT_FILE);
			Date now = new Date(System.currentTimeMillis() - (1000 * 60 * 15));
			
			
			
//			bearValuePair[0] = XACT_Calculator_AV.roundToPip(bearValuePair[0]);
//			bearValuePair[1] = XACT_Calculator_AV.roundToPip(bearValuePair[1]);
			
//			bullValuePair[0] = XACT_Calculator_AV.roundToPip(bullValuePair[0]);
//			bullValuePair[1] = XACT_Calculator_AV.roundToPip(bullValuePair[1]);
			
			writer.write(valueToString(bearValuePair[0])+"/"+valueToString(bearValuePair[1])+" "+valueToString(bullValuePair[0])+"/"+valueToString(bullValuePair[1])+"  "+now.toString());
			writer.close();
		
		}catch(IOException e){
			e.printStackTrace();
		}

	}
	
	public String valueToString(Double inValue)
	{
		inValue = XACT_Calculator_AV.roundToPip(inValue);
		String valStr = inValue.toString();
		String[] split = valStr.split("\\.");
		
		if(split.length < 2)
			valStr+=".00";
		else if(split[1].length() < 2)
			valStr+="0";
		
		return valStr;
	}


	public void run(){

		int startHour = 9;
		int startMinute = 30;
		int stopHour = 17;
		int stopMinute = 35;
		
		XACT_Calculator_AV.updateXactParams(XACT_Calculator_AV.BEAR);
		XACT_Calculator_AV.updateXactParams(XACT_Calculator_AV.BULL);
		
		long startTime = System.currentTimeMillis();
		
		System.out.println("xact tracker started...");
		
		boolean started = false;

		while( true ){

			
			try{
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(System.currentTimeMillis());

				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
					
					sleep(60000);
					continue;
				}
					
				final int actHour = cal.get(Calendar.HOUR_OF_DAY);
				final int actMinute = cal.get(Calendar.MINUTE);

				if ( ( (actHour == startHour && actMinute >= startMinute) || actHour > startHour ) && !( (actHour == stopHour && actMinute >= stopMinute) || actHour > stopHour) ){
				
					if(started == false){
						
						System.out.println("updating Xact params at: "+new Date(System.currentTimeMillis()).toString());
						XACT_Calculator_AV.updateXactParams(XACT_Calculator_AV.BEAR);
						XACT_Calculator_AV.updateXactParams(XACT_Calculator_AV.BULL);
						
						started = true;
					}
					act();
				}
				else{
					started = false;
				}
				sleep(60000);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]){
		XACT_TRACKER trader = new XACT_TRACKER();
		trader.start();
	}
}