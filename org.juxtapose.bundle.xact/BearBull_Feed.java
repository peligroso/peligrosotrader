import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;




public class BearBull_Feed {
	


	static String base_URL = "http://www.avanza.se/aza/aktieroptioner/kurslistor/aktie.jsp";
	static String BEAR = "?orderbookId=12251";
	static String BULL = "?orderbookId=12252";
	
	static String ID = "<table class='shareTable' width=\"734\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr class='shareTableToolbar'>";
	static String ORDER_ID = "<tr bgcolor=\"#efefef\"><td class=\"lneutral\">";
	
	public static StockInfo getActVal(String inUrl){
		
		try{
			URL url = new URL(base_URL+inUrl);

			URLConnection connection = url.openConnection();
			connection.setDoOutput(false);

			InputStream is = connection.getInputStream();
			BufferedReader in = new BufferedReader( new InputStreamReader(is));

			String kop = "--";
			String salj = "--";
			String senast = "--";
			String hogst = "--";
			String lagst = "--";

			StockInfo retInfo = new StockInfo();
			
			String line = in.readLine();
			do{	
				//System.out.println(line);
				if(line.startsWith(ID)){
					
					String[] split = line.split("[<>]");
					
					kop = split[92];
					kop = kop.replace(',', '.');
					salj = split[96];
					salj = salj.replace(',', '.');
					senast = split[100];
					senast = senast.replace(',', '.');					
					hogst = split[104];
					hogst = hogst.replace(',', '.');
					lagst = split[108];
					lagst = lagst.replace(',', '.');
					
					retInfo.setValues(new Double(senast), new Double(salj), new Double(kop), new Double(hogst), new Double(lagst));
					//System.out.println(kop+" "+salj+" "+senast+" "+hogst+" "+lagst);
						
				}
				else if(line.startsWith(ORDER_ID)){
					String[] split = line.split("[<>]");
					String time = split[20];
					String price = split[24];
					price = price.replace(',', '.');
					String ant = split[28];
//					for(String s : split)
//						System.out.println(s);
					retInfo.addClosure(new Double(price), time, new Integer(ant));
					//System.out.println(time+"  "+price+"  "+ant);
				}
				line = in.readLine();
			}while(line != null);
			
			return retInfo;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public static void main(String[] args){
		StockInfo info = getActVal(BULL);
		
		System.out.println("Köp: "+info.bid);
		System.out.println("Sälj: "+info.ask);
		System.out.println("Senast: "+info.last);
		System.out.println("Högst: "+info.highest);
		System.out.println("Lägst: "+info.lowest);
		System.out.println();
		for(Closure close : info.closures)
			System.out.println("time: "+close.time+" price: "+close.price+" amount: "+close.amount);
			
	}
}
