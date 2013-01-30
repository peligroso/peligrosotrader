package peligrosotrader.schedule;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import peligrosotrader.util.IMarketScanner;
import peligrosotrader.util.service.IGUI;

public class Activator implements BundleActivator, ISchedule {

	IMarketScanner m_scanner;
	Timer m_timer;
	
	public void start(BundleContext context) throws Exception {
		
		Dictionary<String, String> props = new Hashtable<String, String>();
        props.put("Schedule", "PELIGROSO");
		
		context.registerService(ISchedule.class.getName(), this, props);
		
		System.out.println("Schedule service is registred");
		
		m_timer = new Timer(this);
		Thread t = new Thread(m_timer);
		t.start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
	}

	public void setMarketScanner(IMarketScanner inMarketScanner) {

		m_scanner = inMarketScanner;
	}
	
	public void execute(){
		
		if(m_scanner != null){
			m_scanner.scan();
		}
	}

}
