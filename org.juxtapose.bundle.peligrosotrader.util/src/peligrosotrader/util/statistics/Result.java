package peligrosotrader.util.statistics;

import java.util.Set;

public class Result {

	double m_value = 0;
	Set<Integer> m_connections = null;
	
	public Result(double inValue, Set<Integer> inConns){
		m_connections = inConns;
		m_value = inValue;
	}
	
	public double getValue(){
		return m_value;
	}
	
	public Set<Integer> getConnections(){
		return m_connections;
	}
}
