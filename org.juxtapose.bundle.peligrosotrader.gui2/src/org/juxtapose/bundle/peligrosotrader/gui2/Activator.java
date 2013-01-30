package org.juxtapose.bundle.peligrosotrader.gui2;

import java.awt.Image;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Properties;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import peligrosotrader.predict.IMarketPublisher;
import peligrosotrader.predict.MarketAdapter;
import peligrosotrader.schedule.ISchedule;
import peligrosotrader.socket.IHTTPConnection;
import peligrosotrader.util.IAnalyzeProvider;
import peligrosotrader.util.IMarketScanner;
import peligrosotrader.util.service.IGUI;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin implements IGUI{

	// The plug-in ID
	public static final String PLUGIN_ID = "org.juxtapose.bundle.peligrosotrader.gui2";

	// The shared instance
	private static Activator plugin;
	
	IMarketPublisher m_market;
	IHTTPConnection m_connection;
	ISchedule m_shedule;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		System.out.println("start gui bundle");
//		
//		Properties props = new Properties();
//        props.put("Market", "SAX");
//		
//		context.registerService(IMarketPublisher.class.getName(), this, props);
		
		Dictionary<String, String> props = new Hashtable<String, String>();
        props.put("GUI", "PELIGROSO");
		
		context.registerService(IGUI.class.getName(), this, props);
		
		ServiceReference[] refs = context.getServiceReferences(IMarketPublisher.class.getName(), "(Market=*)");
		if(refs != null){
			m_market = (IMarketPublisher) context.getService(refs[0]);
			System.out.println("got Market service");
		}
		else
			System.out.println("Couldn´t find Market service");
		
		ServiceReference[] refs2 = context.getServiceReferences(IHTTPConnection.class.getName(), "(Connection=*)");
		if(refs2 != null){
			m_connection = (IHTTPConnection ) context.getService(refs2[0]);
			System.out.println("got HTTP service");
		}
		else
			System.out.println("Couldn´t find HTTP service");
			
		ServiceReference[] refs3 = context.getServiceReferences(ISchedule.class.getName(), "(Schedule=*)");
		if(refs3 != null){
			m_shedule = (ISchedule ) context.getService(refs3[0]);
			System.out.println("got Schedule service");
		}
		else
			System.out.println("Couldn´t find Schedule service");

	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	public MarketAdapter getMarketPublisher(){
		
		return m_market.getMarket();
	}
	
	public void setMarketScanner(IMarketScanner inScanner)
	{
		m_shedule.setMarketScanner(inScanner);
	}
	
	public void setAnalyseProvider(IAnalyzeProvider inProvider){
		m_connection.setAnalyseProvider(inProvider);
	}
	
	public void analyzeResponse(Image inIm, int inWidth, int inHeight){
		m_connection.analyzeResponse(inIm, inWidth, inHeight);
	}
}
