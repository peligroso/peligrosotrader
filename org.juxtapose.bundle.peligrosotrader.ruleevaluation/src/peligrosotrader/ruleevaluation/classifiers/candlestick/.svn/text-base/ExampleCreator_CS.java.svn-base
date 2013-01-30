package peligrosotrader.ruleevaluation.classifiers.candlestick;

import java.util.List;
import java.util.Vector;

import peligrosotrader.util.db.Sample;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

/**
 * 
 * @author Peligroso
 *
 *Create candleStickExample
 *Format for candlesticks instances are dayspan divided by 3 and group open and close in the divided parts
 *hence creating the format (open:close). As addition a third parameter for the size of the span i added in the form 
 *veryBig, big, medium, low (open:close:big). last attribute is closeprice in relation to the closeingprice from 
 *the day before e.g
 * ___			
 *	|  (1:1:big:bigUp)	 (3:3:low:up)  	   	--- (1:3:medium:down)
 *	| 						|		 		|  |
 *	|		   	   		   ---		 		|  |
 *	|					 			        ----	
 */
public class ExampleCreator_CS {
	
	static int DAYS = 5;
	static int HOLD_DAYS = 15;
	
	static String POS_CLASS ="pos";
	static String NEG_CLASS = "neg";
	
	public static Instances createData(){
		Instances m_data = null;
		
		
		FastVector attributes = new FastVector(DAYS*6);

		for(int i = 0; i < DAYS; i++){
			for(int indayIndex = 1; indayIndex < 6; indayIndex++){
				FastVector attributeValues = null;
				StringBuffer paramName = new StringBuffer("day"+(i+1)+"-");
				
				switch(indayIndex){
				case(1):paramName.append("open");
//				Add class attribute
				attributeValues = new FastVector(3);
				attributeValues.addElement("1");
				attributeValues.addElement("2");
				attributeValues.addElement("3");
				break;
				case(2):paramName.append("close"); 
				//Add class attribute
				attributeValues = new FastVector(3);
				attributeValues.addElement("1");
				attributeValues.addElement("2");
				attributeValues.addElement("3");
				break;
				case(3):paramName.append("span"); 
//				Add class attribute
				attributeValues = new FastVector(4);
				attributeValues.addElement("verybig");
				attributeValues.addElement("big");
				attributeValues.addElement("medium");
				attributeValues.addElement("low");
				break;
				case(4):paramName.append("volume");
//				Add class attribute
				attributeValues = new FastVector(3);
				attributeValues.addElement("high");
				attributeValues.addElement("medium");
				attributeValues.addElement("low");
				break;
				case(5):paramName.append("move");
//				Add class attribute
				attributeValues = new FastVector(2);
				attributeValues.addElement("up");
				attributeValues.addElement("down");
				
				}
				attributes.addElement(new Attribute(paramName.toString(), attributeValues));
			}
		}
		
		FastVector classValues = new FastVector(2);
		classValues.addElement(POS_CLASS);
		classValues.addElement(NEG_CLASS);
		attributes.addElement(new Attribute("Class", classValues));
		
		//Create Dataset with initial capacity of 100, and set index of class
		m_data = new Instances("CandelstickProblem", attributes, 1000);
		m_data.setClassIndex(m_data.numAttributes() -1 );
		
		return m_data;
	}
	
	public static Instances createInstances(Vector<Vector<Sample>> inVec, Instances inData){
		
	
		
		for(Vector<Sample> sampVec : inVec){
			
			double standardSpan = 0;
			double standardVolume = 0;
			
			Object[] sVec = sampVec.toArray();
			for(int i = 0; i < sVec.length; i++){
				
				Sample samp = (Sample)sVec[i];
				double span = (samp.highest / samp.lowest)-1;
				standardSpan = ((standardSpan * i)+span) / (i+1);
				
				double volume = samp.volume;
				standardVolume = ((standardVolume *i)+volume) / (i+1);
				
				if(i < 10)
					continue;
				
				if(i+DAYS+HOLD_DAYS >= sVec.length-1)
					break;
				
				
				List<Sample> subVec = sampVec.subList(i, sampVec.size()-1);
				Instance instance = createInstance(subVec, standardSpan, standardVolume, inData);
				
//				Add instance to traningdata
				inData.add(instance);
				
			}
			
		}
		
		return inData;		
		
	}
	
	public static Instance createInstance(List<Sample> inVec, double inStandardSpan, double inStandardVolume, Instances inData){
		
		Instance instance = new Instance((DAYS * 5)+1);
		double lastClose = 0;
		
		int paramIndex = 0;
		
		instance.setDataset(inData);
		
		for(int dayI = 0; dayI < DAYS; dayI++ ){
			Sample sam = inVec.get(dayI);
			instance = addDayAttribute(paramIndex, sam, instance, inStandardSpan, inStandardVolume);
			if(sam.close < lastClose)
				instance.setValue(paramIndex+4, "down");
			else
				instance.setValue(paramIndex+4, "up");
			paramIndex += 5;
			
			lastClose = sam.close;
		}
		
		
		
		Sample refferenceSamp = inVec.get(DAYS+HOLD_DAYS);
		double reffClose = refferenceSamp.close;
		
		String classValue;
		if(reffClose - (lastClose*1.1) > 0)
			classValue = POS_CLASS;
//			classValue = 1;
		else
			classValue = NEG_CLASS;
//			classValue = 0;
		
		instance.setClassValue(classValue);
		
		return instance;
	}
	
	public static Instance addDayAttribute(int inStartIndex, Sample inSamp, Instance inInstance, double inStandDev, double inStandVol){
		
		
		String paramOpen;
		String paramClose;
		String paramSpan;
		
		double span = inSamp.highest - inSamp.lowest;
		double percentSpan = (inSamp.highest / inSamp.lowest) -1;
		
		double block = span/3;
		
		paramOpen = getBlock(inSamp.lowest, block, inSamp.open);
		paramClose = getBlock(inSamp.lowest, block, inSamp.close);
		
		if(percentSpan < (inStandDev*0.5))
			paramSpan = "low";
		else if(percentSpan < (inStandDev*1.5))
			paramSpan = "medium";
		else if(percentSpan < (inStandDev*2))
			paramSpan = "big";
		else
			paramSpan = "verybig";
		
		String paramVol = null;
		if(inSamp.volume < (inStandVol*0.2))
			paramVol = "low";
		else if(inSamp.volume < (inStandVol*3))
			paramVol = "medium";
		else
			paramVol = "high";
		

		inInstance.setValue(inStartIndex, paramOpen);
		inInstance.setValue(++inStartIndex, paramClose);
		inInstance.setValue(++inStartIndex, paramSpan);
		inInstance.setValue(++inStartIndex, paramVol);
		
		
		return inInstance;
	}
	
	private static String getBlock(double inLow, double inBlock, double inQuote){
		
		if(inQuote < inLow+inBlock)
			return "3";
		else if(inQuote < inLow+(inBlock*2))
			return "2";
		else
			return "1";
	}
}
