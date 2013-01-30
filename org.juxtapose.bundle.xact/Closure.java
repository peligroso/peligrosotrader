
public class Closure {


	public double price;
	public String time;
	public int amount;

	public Closure(double inPrice, String inTime, int inAmount){
		price = inPrice;
		time = inTime;
		amount = inAmount;
	}
	
	public String toString(){
		return time+" "+price+" "+amount;
	}

}
