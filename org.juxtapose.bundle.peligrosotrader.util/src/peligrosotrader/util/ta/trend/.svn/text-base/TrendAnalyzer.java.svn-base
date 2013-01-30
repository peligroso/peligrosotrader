package peligrosotrader.util.ta.trend;

import java.util.Vector;

public class TrendAnalyzer {

	public static int MIN_TREND_VAL = 3;
	private int trendCount = 0;
	private int liveOnes = 0;
	private double[] m_graf;
	private Vector<Trend> m_trends = new Vector<Trend>();
	private Vector<TrendData> m_doneTrends = new Vector<TrendData>();
	
	public TrendAnalyzer(double[] inGraf){
		m_graf = inGraf;
	}
	
	public void analyze(){
		
		for(int i = 0; i < m_graf.length; i++){
			if(liveOnes == 0){
				m_trends.add(new Trend(this, m_graf[i], i));
				trendCount++;
				liveOnes++;
			}
			else{
				int cursor = 0;
				while(cursor < trendCount){
					Trend actTrend = m_trends.get(cursor);
					if (!actTrend.isDead){
						actTrend.carryOn(m_graf[i]);
					}
					cursor++;
				}
												
			}
		}
		
		for(Trend actTrend : m_trends){
			if (!actTrend.isDead){
				if(actTrend.getValue() >= MIN_TREND_VAL){
					TrendData td = actTrend.generateTrendData();
					System.out.println("Trend start at value: "+td.startIndex+
							", end at index: "+td.endIndex+
							", start-value is: "+td.getStartValue()+
							", end-value is: "+td.getEndValue());
					m_doneTrends.add(actTrend.generateTrendData());
				}
			}
		}
	}
	
	public Trend createNewTrend(double inStartValue, double inFollowUpValue, Trend inTrend, int index){
		
		Trend newTrend = new Trend(this, inStartValue, index);
		newTrend.setParentTrend(inTrend);
		//newTrend.carryOn(inFollowUpValue);
		m_trends.add(newTrend);
		trendCount++;
		liveOnes++;
		
		return newTrend;
	}
	
	public void imFucked(Trend sucker){
		if(sucker.getValue() >= MIN_TREND_VAL)
			m_doneTrends.add(sucker.generateTrendData());
		
		sucker.isDead = true;
		liveOnes--;
	}
	
	
	
	
}
