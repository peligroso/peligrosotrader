package peligrosotrader.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.Date;
import java.util.Vector;


public class DBHandler {
	
	private static DBHandler m_db = new DBHandler();
	
	Connection m_conn;
	
	private DBHandler(){

	}
	
	public static void openConnection(){
		try{
			Class.forName("com.mysql.jdbc.Driver");		
			m_db.m_conn = DriverManager.getConnection(Properties.PROPERTIES.get("connection"), 
					Properties.PROPERTIES.get("user"), 
					Properties.PROPERTIES.get("passwd"));

		} catch (Exception e) {e.printStackTrace();}
	}
	
	public static void closeConnection(){
		try{
			m_db.m_conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	public static DBHandler getDBHandler(){
		return m_db;
	}
	
	public static void saveQuotes(Sample inSample){
		try{
			  
			Statement stmt = m_db.m_conn.createStatement();
			stmt.executeUpdate("INSERT INTO T_QUOTES VALUES"+
					"('"+inSample.ticker+"', '"+inSample.last+"', '"+inSample.ask+
					"', '"+inSample.bid+"', "+inSample.order+", CURRENT_DATE, CURRENT_TIME)");
			
			stmt.close();
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void cleanUp(String inDate){
		try{
			Statement stmt = m_db.m_conn.createStatement();
			stmt.executeUpdate("DELETE FROM T_QUOTES WHERE COL_DATE < '"+inDate+"'");
						
			stmt.close();
		}catch(Exception e){e.printStackTrace();}
	}
	
	/**
	 * 
	 * @param inTicker
	 * @param date format "yyyy-mm-dd"
	 * @return
	 */
	public static Vector<Sample> getSamples(String inTicker, String date) throws SQLException{
		Vector<Sample> ret = new Vector<Sample>();
		
		PreparedStatement pstmt = m_db.m_conn.prepareStatement("SELECT * FROM T_QUOTES WHERE COL_TICKER = ? AND COL_DATE = ?");
		pstmt.setString(1, inTicker);
		pstmt.setString(2, date);
		ResultSet res = pstmt.executeQuery();
		
		while (res.next()){
			Time timePlusFifteen = res.getTime("COL_TIME");
			if(timePlusFifteen != null)
				timePlusFifteen.setMinutes(timePlusFifteen.getMinutes()-15);
			ret.add(new Sample(res.getString("COL_TICKER"), res.getString("COL_LAST"), res.getString("COL_ASK"), 
					res.getString("COL_BID"), res.getInt("COL_STAMP_ORDER"), res.getDate("COL_DATE"), timePlusFifteen));
		}
		res.close();
		pstmt.close();
		
		return ret;
	}
	
	protected void finalize () {
		try{
			m_conn.close();
		}catch (Exception e) {e.printStackTrace();}
	}
}
