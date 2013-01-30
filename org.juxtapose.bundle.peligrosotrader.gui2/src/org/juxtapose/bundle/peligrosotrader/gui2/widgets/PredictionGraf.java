package org.juxtapose.bundle.peligrosotrader.gui2.widgets;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.Predict;

public class PredictionGraf extends Canvas {
	
	public static int WIDTH = 300;
	public static int HEIGHT = 100;
	
	private double high = 0;
	private double low = 1000000;
	private double pixelPip = 0;
	private int pixelWidth = 0;
	private int[] polyLine;
	
	Predict.CloseSample predict;
	List<Sample> graph;

	public PredictionGraf(Composite parent, int style, Predict.CloseSample inPredict){
		super(parent, style);
		
		predict = inPredict;
		graph = new ArrayList<Sample>();
		for(Sample samp : inPredict.graph)
			graph.add(samp);
		for(Sample samp : inPredict.followUp)
			graph.add(samp);
		setHighLow();
		createPolyLine();
		addPaintListener(new PaintListener() {

        public void paintControl(PaintEvent e) {
        	PredictionGraf.this.paintControl(e);
        	}
		});


	}
	
	public PredictionGraf(Composite parent, int style, List<Sample> inGraph){
		super(parent, style);
		
		graph = inGraph;
		setHighLow();
		createPolyLine();
		addPaintListener(new PaintListener() {

        public void paintControl(PaintEvent e) {
        	PredictionGraf.this.paintControl(e);
        	}
		});


	}
	
	private void paintControl(PaintEvent e) {

	     GC gc = e.gc;
	     gc.setBackground( getDisplay().getSystemColor(SWT.COLOR_WHITE));
	     gc.setForeground( getDisplay().getSystemColor(SWT.COLOR_RED));
	     gc.fillRectangle(0, 0,WIDTH, HEIGHT);
	     
	     if(predict != null){
	    	 gc.setBackground( getDisplay().getSystemColor(SWT.COLOR_GRAY));
	    	 gc.fillRectangle(predict.graph.size()*pixelWidth, 0, WIDTH, HEIGHT);
	    	 //predict.graph.size()+pixelWidth
	    	 gc.setBackground( getDisplay().getSystemColor(SWT.COLOR_WHITE));
	     }
	     
	     gc.drawPolyline(polyLine);
	     
	     if(predict != null){
	    	 
	    	 gc.drawString(predict.corr+"", 2, 2);
	    	 gc.drawString(predict.ticker, 2, 20);
	    	 gc.drawString(predict.startDate, 40, 20);
	    	 gc.drawString(predict.endDate, 105, 20);
	    	 gc.drawString(predict.calcResult()+"%", 2, 40);
	     }
	}
	
	private int[] createPolyLine(){
		
		polyLine = new int[graph.size()*2];
		int i = 0;
		int round = 0;
		for(Sample samp : graph){
			double last = new Double(samp.last);
			polyLine[i] = pixelWidth*round;
			polyLine[++i] = HEIGHT - new Double((last - low) * pixelPip).intValue();
			i++;
			round++;

		}
		return polyLine;
	}
	
	private void setHighLow(){
		if(graph.size() > 0){
			for(Sample samp : graph){
				double last = new Double(samp.last);
				if( last > high)
					high = last;
				if( last < low)
					low = last;
			}
		
			pixelPip = new Double(HEIGHT / (high - low));
			pixelWidth = WIDTH / graph.size();
		}
	}
	
	public Point computeSize(int wHint, int wHeight){
		return new Point(WIDTH, HEIGHT);
	}


}
