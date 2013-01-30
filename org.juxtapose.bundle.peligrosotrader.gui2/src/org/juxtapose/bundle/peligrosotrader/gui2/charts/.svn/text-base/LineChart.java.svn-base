package org.juxtapose.bundle.peligrosotrader.gui2.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
 
 
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.CombinedDataset;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.UnitType;

import peligrosotrader.util.Constants;
 
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
 
public class LineChart
{
	public static final Color ONE = new Color(200, 0, 0);
	public static final Color TWO = new Color(210, 30, 30);
	public static final Color THREE = new Color(220, 60, 60);
	public static final Color FOOR = new Color(230, 90, 90);
	public static final Color FIVE = new Color(240, 120, 120);
	public static final Color SIX = new Color(250, 150, 150);
	public static final Color SEVEN = new Color(255, 180, 180);
	
	public static final Color PROGNOS_ONE = new Color(100, 255, 100);
	public static final Color PROGNOS_TWO = new Color(100, 100, 255);
	
	
	public static final Color GREY = new Color(150, 150, 150);
	public static final Color LIGHT_GREY = new Color(210, 210, 210);
	public static final Color SUPPORT_STRONG = new Color(70, 120, 70);
	public static final Color SUPPORT_MEDIUM = new Color(100, 150, 100);
	public static final Color SUPPORT_LIGHT = new Color(130, 180, 130);
	public static final Color SUPPORT_PT = new Color(100, 180, 100);
	public static final Color RESISTANCE_STRONG = new Color(150, 130, 0);
	public static final Color RESISTANCE_MEDIUM = new Color(200, 180, 0);
	public static final Color RESISTANCE_LIGHT = new Color(230, 210, 0);
	public static final Color RESISTANCE_PT = new Color(230, 210, 0);
	public static final Color MA_1 = new Color(50, 50, 100);
	public static final Color MA_2 = new Color(70, 70, 120);
	public static final Color MA_3 = new Color(90, 90, 140);
	public static final Color MA_4 = new Color(90, 120, 150);
	public static final Color MA_5 = new Color(110, 140, 170);
	public static final Color MA_6 = new Color(150, 170, 190);
	public static final Color CHANNELS = new Color(100, 100, 180);
	public static final Color RED = new Color(200, 100, 100);
	
	public static HashMap<Integer, Color> colorMap = new HashMap<Integer, Color>();
	
	public static HashMap<Integer, Color> swing_colorMap = new HashMap<Integer, Color>();
	
	static{
		swing_colorMap.put(1, ONE);
		swing_colorMap.put(2, GREY);
		swing_colorMap.put(3, GREY);
		swing_colorMap.put(4, GREY);
		swing_colorMap.put(5, GREY);
		swing_colorMap.put(6, CHANNELS);
		swing_colorMap.put(7, SUPPORT_STRONG);
		swing_colorMap.put(8, RESISTANCE_STRONG);
		swing_colorMap.put(9, SUPPORT_PT);
		swing_colorMap.put(10, GREY);
		swing_colorMap.put(11, GREY);
		swing_colorMap.put(12, LIGHT_GREY);
		swing_colorMap.put(13, RED);
		swing_colorMap.put(14, MA_1);
		swing_colorMap.put(15, MA_2);
		swing_colorMap.put(16, MA_3);
		swing_colorMap.put(17, MA_4);
		swing_colorMap.put(18, MA_5);
		swing_colorMap.put(19, MA_6);
		swing_colorMap.put(20, SUPPORT_MEDIUM);
		swing_colorMap.put(21, SUPPORT_LIGHT);
		swing_colorMap.put(22, RESISTANCE_MEDIUM);
		swing_colorMap.put(23, RESISTANCE_LIGHT);
		
	}
     
     /**
      * Creates a chart.
      * 
      * @param dataset a dataset.
      * 
      * @return A chart.
      */
     public static JFreeChart createChart(String title, XYDataset dataset, int inDateCount) {
    	 
    	 if(colorMap.isEmpty()){
    		 colorMap = new HashMap<Integer, Color>();
    		 colorMap.put(1, ONE);
    		 colorMap.put(2, TWO);
    		 colorMap.put(3, THREE);
    		 colorMap.put(4, FOOR);
    		 colorMap.put(5, FIVE);
    		 colorMap.put(6, SIX);
    		 colorMap.put(7, SEVEN);
    		 colorMap.put(8, PROGNOS_ONE);
    		 colorMap.put(9, PROGNOS_TWO);
    	 }
    	 
    	 
 
         JFreeChart chart = ChartFactory.createTimeSeriesChart(
             title, // title
             "time", // x-axis label
             "price", // y-axis label
             dataset, // data
             true, // create legend?
             true, // generate tooltips?
             false // generate URLs?
         );
 
         chart.setBackgroundPaint(Color.lightGray);
         XYPlot plot = (XYPlot) chart.getPlot();
//         plot.setBackgroundPaint(Color.white);
         plot.setDomainGridlinePaint(Color.lightGray);
         plot.setRangeGridlinePaint(Color.lightGray);
         
         plot.setBackgroundImage(Constants.GRAPH_BACK);
         
         //upgrade Jfreechart and jcommon to use Rectangle instead of spacer
         plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
         //plot.setAxisOffset(new RectangleInsets(UnitType.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
 
         plot.setDomainCrosshairVisible(true);
         plot.setRangeCrosshairVisible(true);
         
         XYItemRenderer r = plot.getRenderer();
         if (r instanceof XYLineAndShapeRenderer) {
             XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
             renderer.setBaseShapesVisible(false);
             renderer.setBaseShapesFilled(false);
         }
         
         int i;
         int colorIndex = 1;
         for(i = 0; i < inDateCount; i++ ){
        	 r.setSeriesPaint(i, colorMap.get(colorIndex));
        	 if(inDateCount < 5)
        		 colorIndex++;
        	 colorIndex++;
         }
         
         int prognosColorStart = 8;
         for(int x = 0; x < 2; x++){
        	 r.setSeriesPaint(i, colorMap.get(prognosColorStart));
        	 i++;
        	 prognosColorStart++;
         }
         
         DateAxis axis = (DateAxis) plot.getDomainAxis();
         //axis.setDateFormatOverride(new SimpleDateFormat("DDD-MM-yy"));
         axis.setDateFormatOverride(null);
         return chart;
 
     }
     
 
     /**
98      * Creates a dataset, consisting of two series of monthly data.
99      *
100      * @return the dataset.
101      */
    public static XYDataset createDataset(Vector<double[]> inValues, String inName, int offSetMinute, int offSetHour, int offSetDay, int offSetMonth, int offSetYear) {

    	TimeSeriesCollection dataset = new TimeSeriesCollection();
    	for (double[] valVec : inValues){
    		TimeSeries s1 = new TimeSeries(inName, Minute.class);
        
    		int hour = 8;
    		int minute = 50;
    		for (int i = 0; i < valVec.length; i++){
    			s1.add(new Minute(minute, hour, 7, 2, 2007), valVec[i]);
    			//System.out.println(hour+":"+minute+" "+valVec[i]+"   "+i);
    			minute += 5;
    			if (minute > 60){
    				minute = 5;
    				hour++;
    			}
    		}

    		dataset.addSeries(s1);
            //dataset.addSeries(s2);
            dataset.setDomainIsPointsInTime(true);
        }                    
 		
        return dataset;

     }
    
    /**
     * @param inDataset
     * @param inDataset2
     * @param inTypeCount
     * @return
     */
    public static JFreeChart createCandleStickChart(OHLCDataset inDataset, XYDataset inDataset2, ArrayList<Integer> inTypeCount){
    	
    	JFreeChart chart = ChartFactory.createCandlestickChart("Candle Sticks", "time", "Value", inDataset, true);
    	
    	chart.setBackgroundPaint(Color.lightGray);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        
        plot.setBackgroundImage(Constants.GRAPH_BACK);
        
        
        //upgrade Jfreechart and jcommon to use Rectangle instead of spacer
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        //plot.setAxisOffset(new RectangleInsets(UnitType.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));

        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(false);
            renderer.setBaseShapesFilled(false);
        }
        
        
        XYLineAndShapeRenderer lineRenderer = new XYLineAndShapeRenderer(true, false);
        plot.setRenderer(1, lineRenderer); 
        plot.setDataset(1, inDataset2); 

        
//        int chartCount = 0;
        for(int i = 0; i < inTypeCount.size(); i++)
        {
        	int colorIndex = inTypeCount.get(i);
        	lineRenderer.setSeriesPaint(i, swing_colorMap.get(colorIndex));
//        	for(int countIndex = 0; countIndex < inTypeCount.get(i); countIndex++){
//        		lineRenderer.setSeriesPaint(chartCount, swing_colorMap.get(i));
//        		chartCount++;
//        	}
        }
    	return chart;
    }
    
    
    
    /**
     * Creates a combined chart.
     *
     * @return The combined chart.
     */
    public static JFreeChart createCombinedChart() {

        // create subplot 1...
        final XYDataset data1 = createDataset1();
        final XYItemRenderer renderer1 = new StandardXYItemRenderer();
        final NumberAxis rangeAxis1 = new NumberAxis("Range 1");
        final XYPlot subplot1 = new XYPlot(data1, null, rangeAxis1, renderer1);
        subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

        // add secondary axis
        subplot1.setDataset(1, createDataset2());
        final NumberAxis axis2 = new NumberAxis("Range Axis 2");
        axis2.setAutoRangeIncludesZero(false);
        subplot1.setRangeAxis(1, axis2);
        subplot1.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_RIGHT);
        subplot1.setRenderer(1, new StandardXYItemRenderer());       
        subplot1.mapDatasetToRangeAxis(1, 1);

        final XYTextAnnotation annotation = new XYTextAnnotation("Hello!", 50.0, 10000.0);
        annotation.setFont(new Font("SansSerif", Font.PLAIN, 9));
        annotation.setRotationAngle(Math.PI / 4.0);
        subplot1.addAnnotation(annotation);
        
        // create subplot 2...
        final XYDataset data2 = createDataset2();
        final XYItemRenderer renderer2 = new StandardXYItemRenderer();
        final NumberAxis rangeAxis2 = new NumberAxis("Range 2");
//        rangeAxis2.setAutoRangeIncludesZero(false);
        final XYPlot subplot2 = new XYPlot(data2, null, rangeAxis2, renderer2);
        subplot2.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);

        // parent plot...
        final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new NumberAxis("Domain"));
        plot.setGap(10.0);
        
        // add the subplots...
        plot.add(subplot1, 1);
        plot.add(subplot2, 1);
        plot.setOrientation(PlotOrientation.VERTICAL);

        // return a new chart containing the overlaid plot...
        return new JFreeChart("CombinedDomainXYPlot Demo",
                              JFreeChart.DEFAULT_TITLE_FONT, plot, true);

    }
    
    public static JFreeChart createCSChartWithRSI(String inChartName, OHLCDataset inDataset, XYDataset inDataset2, ArrayList<XYDataset> inMomentumDatasets, ArrayList<Integer> inTypeCount, int inType, boolean inUseLoggo)
    {
    	// create subplot 1...
    	SegmentedTimeline timeline = SegmentedTimeline.newMondayThroughFridayTimeline();
    	DateAxis dateAxis = new DateAxis("dateAxisLabel");
    	dateAxis.setTimeline(timeline);

    	final CandlestickRenderer renderer1 = new CandlestickRenderer();

    	if(inType == 3){
    		renderer1.setSeriesPaint(0, swing_colorMap.get(12));
    		renderer1.setUpPaint(swing_colorMap.get(12));
    		renderer1.setDownPaint(swing_colorMap.get(12));
    	}

    	final NumberAxis rangeAxis1 = new NumberAxis("Range 1");
    	rangeAxis1.setAutoRangeIncludesZero(false);
    	final XYPlot subplot1 = new XYPlot(inDataset, dateAxis, rangeAxis1, renderer1);

    	XYLineAndShapeRenderer lineRenderer = new XYLineAndShapeRenderer(true, false);
    	subplot1.setRenderer(1, lineRenderer); 
    	subplot1.setDataset(1, inDataset2);
    	
    	subplot1.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
    	
    	if(inUseLoggo)
    		subplot1.setBackgroundImage(Constants.GRAPH_BACK);

    	for(int i = 0; i < inTypeCount.size(); i++)
    	{
    		if(i == 0)
    			continue;
    		int colorIndex = inTypeCount.get(i);
    		lineRenderer.setSeriesPaint(i-1, swing_colorMap.get(colorIndex));
    	}
        
        final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(dateAxis);
        plot.setGap(10.0);
        
        plot.add(subplot1, 3);
        
        //RSI part
        if(inMomentumDatasets != null)
        	for(XYDataset momDataset : inMomentumDatasets)
        	{
        		final XYItemRenderer renderer2 = new StandardXYItemRenderer();
        		final NumberAxis rangeAxis2 = new NumberAxis("RSI");
        		final XYPlot subplot2 = new XYPlot(momDataset, null, rangeAxis2, renderer2);
        		subplot2.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);

        		renderer2.setSeriesPaint(1, swing_colorMap.get(2));
        		renderer2.setSeriesPaint(2, swing_colorMap.get(2));
        		
        		plot.add(subplot2, 1);
        	}
        plot.setOrientation(PlotOrientation.VERTICAL);
        
       

        // return a new chart containing the overlaid plot...
        return new JFreeChart(inChartName,
                              JFreeChart.DEFAULT_TITLE_FONT, plot, true);
    }
    
    public static JFreeChart createCombinedChartFromCharts(JFreeChart inChart1, JFreeChart inChart2)
    {
    	final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new NumberAxis("Domain"));
        plot.setGap(10.0);
        
        // add the subplots...
        plot.add(inChart1.getXYPlot(), 1);
        plot.add(inChart2.getXYPlot(), 1);
        plot.setOrientation(PlotOrientation.VERTICAL);

        // return a new chart containing the overlaid plot...
        return new JFreeChart("CombinedDomainXYPlot Demo",
                              JFreeChart.DEFAULT_TITLE_FONT, plot, true);
    }

    /**
     * Creates a sample dataset.
     *
     * @return Series 1.
     */
    public static XYDataset createDataset1() {

        // create dataset 1...
        final XYSeries series1 = new XYSeries("Series 1a");
        series1.add(10.0, 12353.3);
        series1.add(20.0, 13734.4);
        series1.add(30.0, 14525.3);
        series1.add(40.0, 13984.3);
        series1.add(50.0, 12999.4);
        series1.add(60.0, 14274.3);
        series1.add(70.0, 15943.5);
        series1.add(80.0, 14845.3);
        series1.add(90.0, 14645.4);
        series1.add(100.0, 16234.6);
        series1.add(110.0, 17232.3);
        series1.add(120.0, 14232.2);
        series1.add(130.0, 13102.2);
        series1.add(140.0, 14230.2);
        series1.add(150.0, 11235.2);

        final XYSeries series1b = new XYSeries("Series 1b");
        series1b.add(10.0, 15000.3);
        series1b.add(20.0, 11000.4);
        series1b.add(30.0, 17000.3);
        series1b.add(40.0, 15000.3);
        series1b.add(50.0, 14000.4);
        series1b.add(60.0, 12000.3);
        series1b.add(70.0, 11000.5);
        series1b.add(80.0, 12000.3);
        series1b.add(90.0, 13000.4);
        series1b.add(100.0, 12000.6);
        series1b.add(110.0, 13000.3);
        series1b.add(120.0, 17000.2);
        series1b.add(130.0, 18000.2);
        series1b.add(140.0, 16000.2);
        series1b.add(150.0, 17000.2);

        final XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(series1);
        collection.addSeries(series1b);
        return collection;

    }

    /**
     * Creates a sample dataset.
     *
     * @return A sample dataset.
     */
    public static XYDataset createDataset2() {

        // create dataset 2...
        final XYSeries series2 = new XYSeries("Series 2");

        series2.add(10.0, 16853.2);
        series2.add(20.0, 19642.3);
        series2.add(30.0, 18253.5);
        series2.add(40.0, 15352.3);
        series2.add(50.0, 13532.0);
        series2.add(100.0, 12635.3);
        series2.add(110.0, 13998.2);
        series2.add(120.0, 11943.2);
        series2.add(130.0, 16943.9);
        series2.add(140.0, 17843.2);
        series2.add(150.0, 16495.3);
        series2.add(160.0, 17943.6);
        series2.add(170.0, 18500.7);
        series2.add(180.0, 19595.9);

        return new XYSeriesCollection(series2);

    }


    
 }

