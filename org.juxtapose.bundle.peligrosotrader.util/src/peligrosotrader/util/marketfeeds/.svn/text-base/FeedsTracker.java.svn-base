package peligrosotrader.util.marketfeeds;

public class FeedsTracker {

	public static String SAX = "peligrosotrader.util.marketfeeds.SAXFeed";
	public static String REUTERS_STHLM = "peligrosotrader.util.marketfeeds.ReutersSthlmFeed";
	public static String[] feeds = new String[]{SAX, REUTERS_STHLM};
	
	public static IMarketFeed getFeed(String inFeedName){
		
		if(contains(inFeedName))
		{
			try
			{
				IMarketFeed mf = (IMarketFeed)FeedsTracker.class.getClassLoader().loadClass(inFeedName).newInstance();
				return mf;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static boolean contains(String inFeedName)
	{
		for(String s : feeds){
			if(s.equals(inFeedName))
				return true;
		}
		return false;
	}
}
