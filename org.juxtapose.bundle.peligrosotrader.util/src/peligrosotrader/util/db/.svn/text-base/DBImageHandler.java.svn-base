package peligrosotrader.util.db;

import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;

public class DBImageHandler {

	static Connection m_conn;

	static{
		try{
			DBImageHandler.class.getClassLoader().loadClass("com.mysql.jdbc.Driver").newInstance();
			//Class.forName("com.mysql.jdbc.Driver");		
			m_conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/imageDB", "root","hanshans");

		} catch (Exception e) {e.printStackTrace();}
	}

	public static void saveImage(String imageName, Image inImage, int inWidth, int inHeight){

		try {
			int[] pixels = getArrayFromImage(inImage, inWidth, inHeight);
			byte[] bytes ;
			ByteArrayOutputStream out = new ByteArrayOutputStream() ;
			ObjectOutputStream objOut = new ObjectOutputStream(out) ;

			objOut.writeObject(pixels) ;
			objOut.flush() ;
			bytes = out.toByteArray() ;
			objOut.close() ;

			PreparedStatement ps = m_conn.prepareStatement 
			("INSERT INTO T_IMAGES(COL_NAME, COL_BYTES) values ( ?, ?)") ; 

			ps.setString(1, imageName) ;
			ps.setBytes(2, bytes) ; 
			ps.executeUpdate() ; 
			ps.close() ; 

			m_conn.commit() ;
		} catch (Exception e) { 
			System.out.println("Exception: "+e+" :: "+e.getMessage());
			e.printStackTrace() ;
		}

	}
	
	public static void loadImage(String inImageName)
	{
		try{
			PreparedStatement ps = m_conn.prepareStatement("SELECT * FROM T_IMAGES WHERE COL_NAME = ?");
			ps.setString(1, inImageName) ;
			
			ResultSet res = ps.executeQuery();
			ps.close() ;
			
			while (res.next())
			{
				Blob resBlob = res.getBlob("COL_BYTES");
				byte[] byteArr = resBlob.getBytes(0l, (int)resBlob.length());
				
				ByteArrayInputStream in = new ByteArrayInputStream(byteArr);
				ObjectInputStream ois = new ObjectInputStream(in);
				
				Object o = ois.readObject();
				
			}

			m_conn.commit() ;
			
		} catch (Exception e) { 
			System.out.println("Exception: "+e+" :: "+e.getMessage());
			e.printStackTrace() ;
		}
		
	}

	private static int[] getArrayFromImage(Image img, int width, int height) throws InterruptedException {
		int[] pixels = new int[width * height];
		PixelGrabber pg = new PixelGrabber(img, 0, 0, width, height, pixels, 0, width);
		pg.grabPixels();
		
		int[] imageArray = new int[width * height + 2];
		imageArray[0] = width;
		imageArray[1] = height;
		
		for(int i = 0; i < pixels.length; i++)
			imageArray[i+2] = pixels[i];
		                           
		return imageArray;
	}
	
	public static void main(String[] args){

		try
		{

//			Statement stmt = m_conn.createStatement();
//
//			stmt.executeUpdate(" CREATE TABLE T_IMAGES(COL_NAME VARCHAR(50) PRIMARY KEY, COL_BYTES LONGBLOB)");
//
//			stmt.close();
//
//			m_conn.close();
			
			ImageIcon imc = new ImageIcon("D:\\peligroso\\not_avalible.jpg");
			int width = imc.getIconWidth();
			int height = imc.getIconHeight();
//			
			Image im = imc.getImage();
			
			saveImage("test", im, width, height);


		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
