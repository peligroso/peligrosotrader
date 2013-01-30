package peligrosotrader.predict.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import peligrosotrader.util.statistics.Result;

public class PMPersistenceHandler {
	;
	
	static Connection m_conn;
	
	static{
		try{
			PMPersistenceHandler.class.getClassLoader().loadClass("com.mysql.jdbc.Driver").newInstance();
			//Class.forName("com.mysql.jdbc.Driver");		
			m_conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/predict", "root","hanshans");
	
		} catch (Exception e) {e.printStackTrace();}
	}
	
	/**
	 * 
	 * @param inMarketID
	 * @param inRuleID
	 * @param inPSDays
	 * @param inResults
	 */
	public static void saveResults(int inMarketID, int inRuleID, int inPSDays, ArrayList<Result> inResults)
	{
		
		try{
			
			if(m_conn.isClosed())
			{
				m_conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/predict", "root","hanshans");
			}
	
			
			StringBuffer sb = new StringBuffer();
			
			for(Result res : inResults)
			{
				sb.append(res.getValue());
				sb.append(",");
				sb.append(parseConnsString(res.getConnections()));
				sb.append("\n");
			}
			
			Statement stmt = m_conn.createStatement();
	
			stmt.executeUpdate("INSERT INTO T_RESULTS(COL_MID, COL_RULE_ID, COL_PSDAYS, COL_RESULT_CLOB)  " +
					"VALUES"+
					"("+inMarketID+", "+inRuleID+", "+inPSDays+
					", '"+sb.toString()+"')");
		
				
			stmt.close();
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	public static void deleteMarket()
	{
		try
		{
			Statement stmt = m_conn.createStatement();

			stmt.executeUpdate("DELETE FROM T_RESULTS");

			stmt.close();
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	
	}
	
	/**
	 * 
	 * @param inMarketID
	 * @param inRuleID
	 * @param inPSDays
	 * @return
	 */
	public static ArrayList<Result> loadResults(int inMarketID, int inRuleID, int inPSDays)
	{
		ArrayList<Result> results = new ArrayList<Result>();
		
		try
		{
			if(m_conn.isClosed())
			{
				m_conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/predict", "root","hanshans");
			}
			
			PreparedStatement pstmt = m_conn.prepareStatement("SELECT * FROM T_RESULTS WHERE COL_MID = ? AND COL_RULE_ID = ? AND COL_PSDAYS = ?");
			pstmt.setInt(1, inMarketID);
			pstmt.setInt(2, inRuleID);
			pstmt.setInt(3, inPSDays);
			ResultSet res = pstmt.executeQuery();
//			pstmt.close();
			
			while (res.next())
			{
				String resClob = res.getString("COL_RESULT_CLOB");
				ArrayList<Result> clobArr = parseResultFromClob(resClob);
				
				results.addAll(clobArr);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return results;

	}
	
	/**
	 * 
	 * @param inConns
	 * @return
	 */
	public static String parseConnsString(Set<Integer> inConns)
	{
		StringBuffer sb = new StringBuffer();
		
		for(Integer i : inConns){
			sb.append(i);
			sb.append(",");
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param inClob
	 * @return
	 */
	public static ArrayList<Result> parseResultFromClob(String inClob)
	{
		ArrayList<Result> results = new ArrayList<Result>();
		String[] rows = inClob.split("\n");
		
		if(!inClob.equals("")){
			for(String row : rows)
			{
				String[] resNConns = row.split(",");
				Set<Integer> conns = new HashSet<Integer>();

				for(int i = 1; i < resNConns.length; i++){
					conns.add(Integer.parseInt(resNConns[i]));
				}


				Result res = new Result(Double.parseDouble(resNConns[0]), conns);
				results.add(res);
			}
		}
		return results;
	}
	
	public static void main(String[] args){
		
		try
		{
			
//			Statement stmt = m_conn.createStatement();
//			
//			stmt.executeUpdate(" CREATE TABLE T_RESULTS(ID INTEGER PRIMARY KEY AUTO_INCREMENT, COL_MID INTEGER, COL_RULE_ID INTEGER, COL_PSDAYS INTEGER, COL_RESULT_CLOB LONGTEXT)");
//		
//			stmt.close();
//			
//			m_conn.close();
			
			ArrayList<Result> results = new ArrayList<Result>();
			Set<Integer> m_connections = new HashSet<Integer>();
			m_connections.add(1);
			m_connections.add(2);
			results.add(new Result(3.5, m_connections));
			saveResults(1, 103, 10, results);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
