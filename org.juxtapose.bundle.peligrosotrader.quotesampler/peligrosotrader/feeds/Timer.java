package peligrosotrader.feeds;

import java.util.Calendar;

import peligrosotrader.db.DBHandler;
import peligrosotrader.db.PropReader;
import peligrosotrader.db.Properties;


public class Timer extends Thread {

	static int STOCKHOLM = 1;
	static int TIMECHECK = 5000;
	Calendar m_cal;
	Feed m_thread;
	String[] m_tickers;
	
	boolean running;
	boolean started;
	int startHour, startMinute, stopHour, stopMinute;
	
	public Timer(){
		
		startHour = new Integer(Properties.PROPERTIES.get("start-hour"));
		startMinute = new Integer(Properties.PROPERTIES.get("start-minute"));
		stopHour = new Integer(Properties.PROPERTIES.get("stop-hour"));
		stopMinute = new Integer(Properties.PROPERTIES.get("stop-minute"));
		
		m_cal = Calendar.getInstance();
		running = true;
		started = false;
		start();
	}
	
	public void run(){
		
		try{
			
			System.out.println("Waiting to Start");
	         
			while (running) {
				
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(System.currentTimeMillis());

				final int actHour = cal.get(Calendar.HOUR_OF_DAY);
				final int actMinute = cal.get(Calendar.MINUTE);
				
				//System.out.println(Calendar.HOUR_OF_DAY+":"+Calendar.MINUTE);
				if (!started){
					if (actHour >= startHour && actMinute >= startMinute && actHour <= stopHour && actMinute < stopMinute && notWeekEnd(cal)){
						
							setUp();
							System.out.println("Starting thread at: "+actHour+":"+actMinute);

							started = true;
							m_thread = new YahooFeed();
							m_thread.start();
						
					}
				} else {

					if (actHour >= stopHour && actMinute >= stopMinute){
						
							System.out.println("Stopping thread at: "+actHour+":"+actMinute);
							cleanUp();
							started = false;
							m_thread.finish();
						
					}
				}
				sleep(1000);
			}
		} catch (Exception e){ e.printStackTrace();}
	}
	
	private void cleanUp(){
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)-new Integer(Properties.PROPERTIES.get("old-days")));
		System.out.println("Deleting until: "+cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH));
		DBHandler.cleanUp(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH));
	}
	
	private void setUp(){
		Properties.reset();
		PropReader.readProps();
	}
	
	private boolean notWeekEnd(Calendar inCal){
		if(inCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || inCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY )
			return false;
		return true;
	}
	
	public void finish(){
		running = false;
		m_thread.finish();
		System.out.println("Thread interupted");
	}
}
