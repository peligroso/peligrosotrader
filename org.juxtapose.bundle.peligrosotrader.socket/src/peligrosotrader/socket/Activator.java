package peligrosotrader.socket;

import java.awt.Image;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import peligrosotrader.socket.server.ServerConnector;
import peligrosotrader.util.IAnalyzeProvider;

/**
 * @author Peligroso
 *
 */

public class Activator implements BundleActivator, IHTTPConnection {

	ServerConnector m_connector;
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("start socket connection service");
		
		Dictionary<String, String> props = new Hashtable<String, String>();
        props.put("Connection", "HTTP");
		
		context.registerService(IHTTPConnection.class.getName(), this, props);
		
		m_connector = new ServerConnector();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
	}
	
	/* (non-Javadoc)
	 * @see peligrosotrader.socket.IHTTPConnection#setAnalyseProvider(peligrosotrader.socket.IAnalyzeProvider)
	 */
	public void setAnalyseProvider(IAnalyzeProvider inProvider) {
		m_connector.setProvider(inProvider);
	}
	
	public void analyzeResponse(Image inIm, int inWidth, int inHeight){
		m_connector.analyzeResponse(inIm, inWidth, inHeight);
	}

}
