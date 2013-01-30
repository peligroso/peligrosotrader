package peligrosotrader.util.ta.trendlines;

import java.util.Vector;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.TA;



public class TrendStack extends TA{
	
	public static int SUPPORT = 1;
	public static int RESISTANCE = 2;
	
	int m_type;
	
	Vector<TrendPoint> m_trendPoints = new Vector<TrendPoint>();
	Vector<Sample> m_graph;
	Vector<Vector<Sample>> m_trendLinesFull = new Vector<Vector<Sample>>();
	Vector<TrendLine> m_trends = new Vector<TrendLine>();
	
	TrendPoint m_lastLowest;

	public TrendStack(Vector<Sample> inGraph, int inSpan, int inType){
		
		//inSpan = inGraph.size()/25;
		m_type = inType;
		
		TrendPoint lowestInSpan = null;
		int spanIndex = 0;
		
		m_graph = inGraph;
		
		for(int i = 0; i < inGraph.size(); i++){
			
			Sample samp = inGraph.get(i);
			
			if(m_type == SUPPORT){
				try{
				if(lowestInSpan == null || new Double(samp.last) < new Double(lowestInSpan.m_sample.last)){

					lowestInSpan = new TrendPoint(samp, i);

					spanIndex = 0;
				}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			else if(m_type == RESISTANCE){
				if(lowestInSpan == null || new Double(samp.last) > new Double(lowestInSpan.m_sample.last)){
					
					lowestInSpan = new TrendPoint(samp, i);
					
					spanIndex = 0;
				}
			}
				
			spanIndex++;
			if(spanIndex >= inSpan){
				m_trendPoints.add(lowestInSpan);
				lowestInSpan = null;
			}
		}
		if(lowestInSpan != null)
			m_lastLowest = lowestInSpan;
	}
	
	public void createTrends(){
		for(TrendPoint tp1 : m_trendPoints){
			for(TrendPoint tp2 : m_trendPoints){
				if(tp1.m_index < tp2.m_index ){
					TrendLine tl = new TrendLine(tp1, tp2, m_type);
					if(!tl.isBroken(m_trendPoints, m_lastLowest))
						fitTrendToStack(tl);
				}
			}
		}
		
		//TEST DATA
//		TrendPoint tp1 = m_trendPoints.get(13);
//		TrendPoint tp2 = m_trendPoints.get(15);
//		System.out.println(m_trendPoints.get(4).m_sample.last);
//		System.out.println(m_trendPoints.get(13).m_sample.last);
//		TrendLine tl = new TrendLine(tp1, tp2);
//		m_trends.add(tl);
//		System.out.println(tl.isBroken(m_trendPoints));
//		
//		TrendPoint tp3 = m_trendPoints.get(12);
//		TrendPoint tp4 = m_trendPoints.get(15);
//		TrendLine tl2 = new TrendLine(tp3, tp4);
//		m_trends.add(tl2);
		
		
		
	}
	
	public void fitTrendToStack(TrendLine inTrend){
		TrendLine toBeRemoved = null;
		TrendLine newTrend = null;
		for(TrendLine tl : m_trends){
			if(tl.compare(inTrend)){
				newTrend = tl.merge(inTrend);
				toBeRemoved = tl;
				break;
			}
		}
		if(toBeRemoved == null)
			m_trends.add(inTrend);
		else{
			newTrend.m_mergeCount = ++toBeRemoved.m_mergeCount;
			m_trends.remove(toBeRemoved);
			m_trends.add(newTrend);
		}
	}
	
	public void createTrendLines(){
		for(TrendLine tl : m_trends){
			Vector<Sample> fullLine = tl.getFullLine(m_graph.get(0), m_graph.get(m_graph.size()-1), m_graph.size()-1, m_graph);
			if(fullLine != null)
				m_trendLinesFull.add(fullLine);
		}
	}
	
	public void mergeTrends(){
		
		Vector<TrendLine> mergedTrends = new Vector<TrendLine>();
		Vector<TrendLine> deleted = new Vector<TrendLine>();
		int trendOneIndex = 0;
		for(TrendLine tl1 : m_trends){
			int trendTwoIndex = 0;
			for(TrendLine tl2 : m_trends){
				if((trendOneIndex < trendTwoIndex))
					if(tl1.compare(tl2)){
						if(!deleted.contains(tl1) && !deleted.contains(tl2)){
							TrendLine merged = tl1.merge(tl2);
							mergedTrends.add(merged);
							deleted.add(tl1);
							deleted.add(tl2);
						}
					}
				trendTwoIndex++;
			}
			trendOneIndex++;
		}
		
	
		
		for(TrendLine del : deleted){
			m_trends.remove(del);
		}
		for(TrendLine mer : mergedTrends){
			m_trends.add(mer);
		}
			
	}
	
	public Vector<Vector<Sample>> getTrendLines(){
		return m_trendLinesFull;
	}
	
	public Vector<Sample> getBaseLine(){
//		TrendLine tl = new TrendLine(m_trendPoints.get(0), m_trendPoints.get(2));
//		return tl.getFullLine(m_graph.get(0), m_graph.get(m_graph.size()-1), m_graph.size()-1);
		Vector<Sample> baseLine = new Vector<Sample>();
		for(TrendPoint tp : m_trendPoints)
			baseLine.add(tp.m_sample);
		return baseLine;
	}
	
	public void removeSingles(){
		Vector<TrendLine> newTrends = new Vector<TrendLine>();
		for(TrendLine tl : m_trends){
			if(tl.m_mergeCount > 0)
				newTrends.add(tl);
		}
		m_trends = newTrends;
	}
	
	public Vector<TrendLine> getTrends(){
		return m_trends;
	}
	
}
