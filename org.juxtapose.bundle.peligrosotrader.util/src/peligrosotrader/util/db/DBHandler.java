package peligrosotrader.util.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Vector;

public class DBHandler {
	
	private static DBHandler m_db = new DBHandler();
	
	Connection m_conn;
	
	private DBHandler(){
		try{
			Class.forName("com.mysql.jdbc.Driver");		
			m_conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/peligrosoTrader", "root","hanshans");
	
		} catch (Exception e) {e.printStackTrace();}
	}
	
	protected void finalize () {
		try{
			m_conn.close();
		}catch (Exception e) {e.printStackTrace();}
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
			System.out.println("Trying to re:open connection ");
			try{
				Class.forName("com.mysql.jdbc.Driver");		
				m_db.m_conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/peligrosoTrader", "root","hanshans");
		
				Statement stmt = m_db.m_conn.createStatement();
				stmt.executeUpdate("INSERT INTO T_QUOTES VALUES"+
						"('"+inSample.ticker+"', '"+inSample.last+"', '"+inSample.ask+
						"', '"+inSample.bid+"', "+inSample.order+", CURRENT_DATE, CURRENT_TIME)");
				
				stmt.close();
			} catch (Exception ex) {ex.printStackTrace();}
		}
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
	
	public Connection getConnection(){
		return m_conn;
	}
	
	public static Vector<Sample> getKhamiAlpha()
	{
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/khami","root", "hanshans");

			String queryText = "SELECT * FROM ALPHA";

			PreparedStatement stmt = con.prepareStatement(queryText);

			ResultSet rs = stmt.executeQuery();

			Vector<Sample> samples = new Vector<Sample>();

			while (rs.next()) 
			{
				String dateStr = rs.getString("date");
				Double nav = rs.getDouble("nav");

				Sample samp = new Sample();
				samp.setPriceValues(nav);

				Date date = Date.valueOf(dateStr);

				samp.date = date;

				samples.add(samp);
			}

			return samples;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
}
