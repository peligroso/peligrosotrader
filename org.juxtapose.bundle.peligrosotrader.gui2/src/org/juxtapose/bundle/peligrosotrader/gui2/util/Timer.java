package org.juxtapose.bundle.peligrosotrader.gui2.util;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.widgets.Display;
import org.juxtapose.bundle.peligrosotrader.gui2.views.SamplerView;

import peligrosotrader.util.feeds.Feed;
import peligrosotrader.util.feeds.YahooFeed;

public class Timer extends Thread {

	static int STOCKHOLM = 1;
	static int TIMECHECK = 5000;
	Calendar m_cal;
	Feed m_thread;
	SamplerView m_view;
	String[] m_tickers;
	
	boolean running;
	boolean started;
	int startHour, startMinute, stopHour, stopMinute;
	
	public Timer(int inStartHour, int inStartMinute, int inStopHour, int inStopMinute, String[] inTickers, SamplerView inView){
		
		startHour = inStartHour;
		startMinute = inStartMinute;
		stopHour = inStopHour;
		stopMinute = inStopMinute;
		
		m_view = inView;
		m_tickers = inTickers;
		
		m_cal = Calendar.getInstance();
		running = true;
		started = false;
		start();
	}
	
	public void run(){
		
		try{
			Display.getDefault().asyncExec(new Runnable() {
	               public void run() {
	                  m_view.setState("Waiting to Start");
	               }
	            });
			while (running) {
				Date d = new Date();
				final int actHour = d.getHours();
				final int actMinute = d.getMinutes();
				
				//System.out.println(Calendar.HOUR_OF_DAY+":"+Calendar.MINUTE);
				if (!started){
					if ((actHour >= startHour && actMinute >= startMinute && actHour <= stopHour && actMinute < stopMinute )){
						
							System.out.println("Starting thread at: "+actHour+":"+actMinute);
							Display.getDefault().asyncExec(new Runnable() {
					               public void run() {
					                  m_view.started(actHour+":"+actMinute);
					               }
					            });

							started = true;
							m_thread = new YahooFeed(m_tickers);
							m_thread.start();
						
					}
				} else {

					if (actHour >= stopHour && actMinute >= stopMinute){
						
							System.out.println("Stopping thread at: "+actHour+":"+actMinute);
							started = false;
							m_thread.finish();
							Display.getDefault().asyncExec(new Runnable() {
					               public void run() {
					                  m_view.stoped(actHour+":"+actMinute);
					               }
					            });
						
					}
				}
				sleep(1000);
			}
		} catch (Exception e){ e.printStackTrace();}
	}
	
	public void finish(){
		running = false;
		m_thread.finish();
		System.out.println("Thread interupted");
	}
}
