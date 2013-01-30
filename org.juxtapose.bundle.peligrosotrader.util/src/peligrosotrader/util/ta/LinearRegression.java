package peligrosotrader.util.ta;


import java.util.Enumeration;
import java.util.Vector;

import peligrosotrader.util.db.Sample;

public class LinearRegression {
	
	int numberPoints =0;
	int sumxx=0;
	int sumyy=0;
	int sumxy=0;
	int sumx = 0;
	int sumy =0 ;
	double Sxx, Sxy, Syy;
	Sample a, b;    	//regression coefficients
	
	Vector v = new Vector();  	//vector of point

	
	public Vector regress(Vector<Sample> inVec) 
	{
		clear();
		
		int i = 0;
		
		
			
		for(Sample samp : inVec){
			addPoint(samp.close, i);
			i++;
		}
		
		a = new Sample();
		b = new Sample();
		
		a.date = inVec.firstElement().date;
		b.date = inVec.lastElement().date;
		
		Vector resid = new Vector();
		//PlotPoint p,q;
		//double currentResidual;
		double n = (double)numberPoints;
		double aP, bP;
		Sxx = sumxx-sumx*sumx/n;
		Syy = sumyy-sumy*sumy/n;
		Sxy = sumxy-sumx*sumy/n;
		bP =Sxy/Sxx;
		aP = (sumy-bP*sumx)/n;
		
		bP =
				(aP+inVec.size()*bP);
		
		a.last = Double.toString(aP);
		b.last = Double.toString(bP);
//		for (Enumeration e= v.elements();
//		e.hasMoreElements(); ) {
//			p = (PlotPoint)e.nextElement();
//			currentResidual = p.y-(a+b*p.x);
//			q = new PlotPoint(p.x, (int)currentResidual);
//			resid.addElement(q);	
//		}
		return resid;

	}
	
	public Vector<Sample> getRegression()
	{
		Vector<Sample> ret = new Vector<Sample>();
		ret.add(a);
		ret.add(b);
		
		return ret;
	}
	
//	public double [] regressionLine(double a, double b) {
//		PlotPoint[] ends = new PlotPoint[2];
//		int y1, y2;
//		//if
//			 //(Math.abs(b) <= 1) {
//				
//				y1=(int)Math.round(a);
//				
//				y2 = (int)Math.round((a+b));
//		 		//}
//		/*else {
//			e1y=r.height;
//			e2y =0;
//			e1x=(int)Math.round(-20*(a/b));
//			e2x=(int)Math.round( 20*(10-2)/b);
//			}*/
//		
//		ends[0] = new PlotPoint( e1x, e1y);
//		ends[1] = new PlotPoint( e2x, e2y);
//		return ends;
//		}
	
	/**
	 * 
	 *
	 */
//	public void calculate() {
//		if (numberPoints >1) {
//			residuals=regress();
//			rcp.update(a,b);
//			resc.update(residuals);
//		}
//	}
	
	public void addPoint(double samp, int day){
		//v.addElement(samp);
		
		if(samp > 1000)
			System.out.println("over");
		numberPoints++;
		sumx += day;
		sumy += samp;
		sumxx += day*day;
		sumyy += samp*samp;
		sumxy += day*samp;
	}
	

	/** 
	 * clear
	 */
	public void clear(){
		v = new Vector();
		numberPoints=0;
		sumxx=0;
		sumyy=0;
		sumxy=0;
		sumx = 0;
		sumy =0 ;
	}
}
