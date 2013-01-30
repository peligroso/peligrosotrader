package peligrosotrader.socket.server;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.imageio.ImageReader;
import javax.swing.ImageIcon;

import com.sun.corba.se.impl.orbutil.ObjectWriter;

import peligrosotrader.util.IAnalyzeProvider;
import peligrosotrader.util.db.DBHandler;
import peligrosotrader.util.db.Sample;

/**
 * @author Peligroso
 *
 */
public class ServerConnector implements Runnable{

	ServerSocket m_socket;
	Thread m_thread;
	
	BufferedReader m_in = null;
	ObjectOutputStream m_out = null;
	
	IAnalyzeProvider m_provider;
	
	Socket m_inSocket;
	
	String NOT_AVALIBLE = "D:\\peligrosotrader\\not_avalible.jpg";

	
	boolean m_running = true;
	
	/**
	 * 
	 */
	public ServerConnector(){

		try {
			m_socket = new ServerSocket(4444);
			
			System.out.println("ServerSocket initialized");
			
			m_thread = new Thread(this);
			m_thread.start();
			
		} catch (IOException e) {
			System.out.println("Could not listen on port: 4444");
		}

	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() 
	{
		while(m_running){
			
			try {
				m_inSocket = m_socket.accept();
				
				System.out.println("Got acceptance");
				
				m_in = new BufferedReader(new InputStreamReader(m_inSocket.getInputStream()));
				m_out = new ObjectOutputStream(m_inSocket.getOutputStream());
				
				String line;
				
				line = m_in.readLine();
				
				
				String[] split = line.split("\\|");

				if( split[0].equals("khami"))
				{
					int i = 1;
					
					Vector<Sample> samples = DBHandler.getKhamiAlpha();
					
					m_provider.createChart( samples );
					
				}
				else
				{
					long time = System.currentTimeMillis();
					System.out.println(split[0]+"    "+split[1]+"    "+split[2]+"   "+split[3]);

					Integer type = Integer.parseInt(split[3]);

					boolean rsi = split.length > 4;
					if(m_provider != null)
						m_provider.createAnalyze(split[0], split[1], split[2], type, rsi);

					long endTime = System.currentTimeMillis();
					
					System.out.println("analysis took "+(endTime-time)+" milliSec");
					System.out.println(line);
					System.out.println("sending response");

				}
				
			} catch (Exception e) {
				System.out.println("accept failed");
			}
		}
	}
	
	/**
	 * @param inProvider
	 */
	public void setProvider(IAnalyzeProvider inProvider)
	{
		m_provider = inProvider;
	}
	
	/**
	 * @param inAnalyze
	 */
	public void analyzeResponse(Image inIm, int inWidth, int inHeight)
	{
		try{

			if(m_out != null){
//				
				int[] pixels;
				
				if(inWidth == 0 && inHeight == 0)
				{
					ImageIcon imc = new ImageIcon(NOT_AVALIBLE);
					int width = imc.getIconWidth();
					int height = imc.getIconHeight();
					
					Image im = imc.getImage();
					
					pixels = getArrayFromImage(im, width, height);
				}
				else
					pixels = getArrayFromImage(inIm, inWidth, inHeight);
				
				m_out.writeObject(pixels);
			}
//			m_out.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private int[] getArrayFromImage(Image img, int width, int height) throws InterruptedException {
		int[] pixels = new int[width * height];
		PixelGrabber pg = new PixelGrabber(img, 0, 0, width, height, pixels, 0, width);
		pg.grabPixels();
		
		int[] imageArray = new int[width * height + 2];
		imageArray[0] = width;
		imageArray[1] = height;
		
		for(int i = 0; i < pixels.length; i++)
			imageArray[i+2] = pixels[i];
		                           
		return imageArray;
	}  //  private int[] getArrayFromImage()

	
}
