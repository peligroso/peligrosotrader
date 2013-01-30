package peligrosotrader.db;

import java.io.FileReader;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class PropReader {
	
	public static void readProps(){
		try{
			XMLReader xr = XMLReaderFactory.createXMLReader();
			XMLHandler handler = new XMLHandler();
			xr.setContentHandler(handler);
			xr.setErrorHandler(handler);


			FileReader r = new FileReader("props.xml");
			xr.parse(new InputSource(r));

		}catch (Exception e){e.printStackTrace();}
	}

}
