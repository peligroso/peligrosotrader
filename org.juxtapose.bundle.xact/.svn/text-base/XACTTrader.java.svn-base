import java.util.ArrayList;
import java.util.Calendar;


public class XACTTrader extends Thread{
	
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
		StockInfo bullInfo = BearBull_Feed.getActVal(BearBull_Feed.BULL);
		
		double bearValuePair[] = XACT_Calculator_AV.calculate(XACT_Calculator_AV.BEAR);
		StockInfo bearInfo = BearBull_Feed.getActVal(BearBull_Feed.BEAR);
		
		double bullValue = (bullValuePair[0] + bullValuePair[1]) / 2;
		double bearValue = (bearValuePair[0] + bearValuePair[1]) / 2;
		
		if(bearValue == 0 || bullValue == 0)
			return;
		
		System.out.println("true bull is: "+bullValue+",  true bear is: "+bearValue);
		System.out.println("BULL: "+bullInfo);
		System.out.println("BEAR: "+bearInfo);
		
		if(bullEntry == 0)
			System.out.println("my Bull-order is bid at: "+bullOrder);
		else
			System.out.println("my Bull-order is ask at: "+bullOrder+"   holding at: "+bullEntry);
		if(bearEntry == 0)
			System.out.println("my Bear-order is bid at: "+bearOrder);
		else
			System.out.println("my Bear-order is ask at: "+bearOrder+"   holding at: "+bearEntry);
		
		wasFilled(bullInfo, bearInfo);
		
		System.out.println("closures");
		System.out.println("last Bull closure was: "+lastBullClosure);
		bullOrder = createOrder(bullValue, bullEntry);
		
		System.out.println("last Bear closure was: "+lastBearClosure);
		bearOrder = createOrder(bearValue, bearEntry);
		
	}
	
	private boolean wasFilled(StockInfo bullInfo, StockInfo bearInfo){
		
		if(bullOrder == 0 || bearOrder == 0){
			lastBullClosure = bullInfo.getLastClosure();
			lastBearClosure = bearInfo.getLastClosure();
			return false;
		}
		if(bullEntry == 0){
			//order is of type bid
			double compare = bullInfo.last;
			if (bullInfo.ask < compare)
				compare = bullInfo.ask;
			if(bullOrder >= compare){
				bullEntry = bullOrder;
				System.out.println("Buy XACT-BULL at: "+bullEntry);
			}
//			if(checkClosures(bullInfo, bullOrder, true, lastBullClosure)){
//				bullEntry = bullOrder;
//				System.out.println("Buy XACT-BULL at: "+bullEntry);
//			}			
		}else{
//			order is of type ask
			double compare = bullInfo.last;
			if (bullInfo.bid > compare)
				compare = bullInfo.bid;
			if(bullOrder <= compare){
				results.add(new Result(bullEntry, bullOrder));
				bullEntry = 0;
				System.out.println("Sell XACT-BULL at: "+bullOrder);
			}
			
//			if(checkClosures(bullInfo, bullOrder, false, lastBullClosure)){
//				
//				System.out.println("Sell XACT-BULL at: "+bullOrder);
//				results.add(new Result(bullEntry, bullOrder));
//				bullEntry = 0;
//			}	
		}
		
		
		if(bearEntry == 0){
			//order is of type bid
			if(bearOrder >= bearInfo.last){
				bearEntry = bearOrder;
				System.out.println("Buy XACT-BEAR at: "+bearEntry);
			}
//			if(checkClosures(bearInfo, bearOrder, true, lastBearClosure)){
//				bearEntry = bearOrder;
//				System.out.println("Buy XACT-BEAR at: "+bearEntry);
//			}			
		}else{
//			order is of type ask
			if(bearOrder <= bearInfo.last){
				results.add(new Result(bearEntry, bearOrder));
				bearEntry = 0;
				System.out.println("Sell XACT-BEAR at: "+bearOrder);
			}
//			if(checkClosures(bearInfo, bearOrder, false, lastBearClosure)){
//				
//				System.out.println("Sell XACT-BEAR at: "+bearOrder);
//				results.add(new Result(bearEntry, bearOrder));
//				bearEntry = 0;
//			}	
		}
		lastBullClosure = bullInfo.getLastClosure();
		lastBearClosure = bearInfo.getLastClosure();
		return false;
	}
	
	private boolean checkClosures(StockInfo inInfo, double inOrder, boolean bid, String lastClose){
		
		for(Closure c : inInfo.closures){
			if(lastClose == null || c.toString().equals(lastClose.toString()))
				break;
			System.out.println(c);
			if(bid && c.price <= inOrder)
				return true;
			else if(!bid && c.price >= inOrder)
				return true;
		}
		
		if(bid && inInfo.ask <= inOrder)
			return true;
		else if(!bid && inInfo.bid >= inOrder)
			return true;
		
		return false;		
		
	}
	

	public double createOrder(double inValue, double inEntry){
		
		if(inEntry == 0){
			//create bidOrder
			double bidPrice = inValue * 0.997;
			bidPrice = roundToPip(bidPrice);
			
			return bidPrice;
		}
		else{
			//create askOrder
			double askPrice = inValue * 1.003;
			askPrice = roundToPip(askPrice);
			
			return askPrice;
		}
		
	}
	
	public double roundToPip(double inVal){
		
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
	
	public void run(){
		
		int startHour = 9;
		int startMinute = 30;
		int stopHour = 17;
		int stopMinute = 15;
		
		running = true;
		
		boolean started = false;
		
		while(running){
			
			try{
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());

			final int actHour = cal.get(Calendar.HOUR_OF_DAY);
			final int actMinute = cal.get(Calendar.MINUTE);
			
			System.out.println(actHour+":"+actMinute);
			
			if (actHour >= startHour && actMinute >= startMinute)
				started = true;
			if(started)
				act();
			if (actHour >= stopHour && actMinute >= stopMinute){
				printResult();
				running = false;
			}
			sleep(60000);
			}catch(Exception e){
				e.printStackTrace();
			}
			System.out.println();
		}
	}
	
	public void printResult(){
		System.out.println("*************RESULT************");
		double avg = 0;
		double all = 0;
		for(Result res : results){
			res.printResult();
			all += res.gainP;
		}
		avg = all/results.size();
		System.out.println();
		System.out.println("Avg is: "+avg+" of "+results.size()+" trades");
		
	}
	
	public void stopIt(){
		running = false;
	}
	public static void main(String args[]){
		XACTTrader trader = new XACTTrader();
		trader.start();
	}
}
