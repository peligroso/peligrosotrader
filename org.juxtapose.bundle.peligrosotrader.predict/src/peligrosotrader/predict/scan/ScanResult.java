package peligrosotrader.predict.scan;

import java.util.Map;
import java.util.Set;

public class ScanResult {

	public Set<Integer> m_conns;
	public String m_corp;
	public String m_ticker;
	public Map<String, String> m_properties;
	
	public ScanResult(Set<Integer> inConns, String inCorp, String inTicker, Map<String, String> inProps)
	{
		m_conns = inConns;
		m_corp = inCorp;
		m_ticker = inTicker;
		m_properties = inProps;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(m_corp);
		sb.append("("+m_ticker+") with connections: ");
		for(Integer conn :m_conns){
			sb.append(conn.toString()+", ");
		}
		return sb.toString();
	}
}
