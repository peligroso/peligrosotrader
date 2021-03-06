package peligrosotrader.predict;

import java.util.Calendar;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import peligrosotrader.socket.Dummy;
import peligrosotrader.socket.server.ServerConnector;
import peligrosotrader.util.marketfeeds.SAXFeed;

public class Activator implements BundleActivator, IMarketPublisher, Runnable{

	MarketAdapter m_ma;
	boolean m_online = false;
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception{
		
		System.out.println("start bundle");
		Dictionary<String, String> props = new Hashtable<String, String>();
        props.put("Market", "SAX");
		
		context.registerService(IMarketPublisher.class.getName(), this, props);

		m_ma = new MarketAdapter();
		Thread thread = new Thread(this);
		thread.start();
		
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
	}

	public MarketAdapter getMarket() {
		if(m_online)
			return m_ma;
		else{
			System.out.println("Market publisher not online");
			return null;
		}
	}

	public void run() {
		// TODO Auto-generated method stub
		Calendar startCal = Calendar.getInstance();
		startCal.set(Calendar.YEAR, 2000);
		startCal.set(Calendar.MONTH, Calendar.JANUARY);
		startCal.set(Calendar.DAY_OF_MONTH, 1);
		
		Calendar endCal = Calendar.getInstance();
		endCal.set(Calendar.YEAR, 2006);
		endCal.set(Calendar.MONTH, Calendar.JULY);
		endCal.set(Calendar.DAY_OF_MONTH, 1);
		
		m_ma.init();
		m_online = true;
	}
	
	public boolean isOnline(){
		return m_online;
	}

}
