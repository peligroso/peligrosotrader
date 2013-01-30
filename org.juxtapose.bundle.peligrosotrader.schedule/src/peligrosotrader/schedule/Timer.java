package peligrosotrader.schedule;

import java.util.Calendar;

public class Timer implements Runnable{

	ISchedule m_schedule;
	
	int MINUTE = 1000 * 60;
	
	public Timer(ISchedule inSchedule)
	{
		m_schedule = inSchedule;
	}
	
	public void run() 
	{		
		while(true)
		{
//			System.out.println("checking for scan");
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			
			if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
				
				int hour = cal.get(Calendar.HOUR_OF_DAY);
				int minute = cal.get(Calendar.MINUTE);
				if(hour == 12 || hour == 17){
					
					
					if(minute == 45){
						doit();
					}
				}
//				System.out.println(hour+" "+minute);
			}
			try{
				Thread.sleep(MINUTE);
			}catch(InterruptedException inEx){
				inEx.printStackTrace();
			}
			
		}
		
	}
	
	public void doit()
	{
		m_schedule.execute();
	}

}
