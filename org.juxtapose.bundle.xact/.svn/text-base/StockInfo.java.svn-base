import java.util.ArrayList;


public class StockInfo {

	public double last;
	public double ask;
	public double bid;
	public double highest;
	public double lowest;

	ArrayList<Closure> closures = new ArrayList<Closure>();

	public StockInfo(){
		
	}
	public void setValues(double inLast, double inAsk, double inBid, double inHighest, double inLowest){
		last = inLast;
		ask = inAsk;
		bid = inBid;
		highest = inHighest;
		lowest = inLowest;

	}
	
	public void addClosure(double inPrice, String inTime, int inAmount){
		closures.add(new Closure(inPrice, inTime, inAmount));
	}
	public String getLastClosure(){
		if(closures.size() < 1)
			return null;
		return closures.get(0).toString();
	}
	
	public String toString(){
		return ("bid: "+bid+", ask: "+ask+", last: "+last);
	}
}
