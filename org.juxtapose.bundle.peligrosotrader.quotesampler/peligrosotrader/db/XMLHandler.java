package peligrosotrader.db;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler{

	public XMLHandler() {
		super();
	}
	
	public void startElement (String uri, String name,
		      String qName, Attributes atts)
	{
		if (name.equals("ticker")){
		
			
			String tName = atts.getValue("name");
			String tDes = atts.getValue("description");
			
			if(tName != null && tDes != null){
				System.out.println("Adding ticker: "+tName+" "+tDes);
				Properties.addTicker(tName, tDes);
			}
		}
		else if(name.equals("property")){
			String propValue = atts.getValue("value");
			String propName = atts.getValue("name");
			
			if(propValue != null && propName != null){
				System.out.println("Adding property: "+propName+" "+propValue);
				Properties.addProp(propName, propValue);
			}
		}
	}

	public void endElement (String uri, String name, String qName)
	{
		
	}	
}