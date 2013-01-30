
public class Result {

	public double entry;
	public double exit;
	public double gainP;
	
	public Result(double inEntry, double inExit){
		entry = inEntry;
		exit = inExit;
		gainP = ((inExit/inEntry) -1) * 100 ;
	}
	
	public void printResult(){
		System.out.println("entry at: "+entry+"  Exit at: "+exit+" result in "+gainP+"% gain");
	}
}
