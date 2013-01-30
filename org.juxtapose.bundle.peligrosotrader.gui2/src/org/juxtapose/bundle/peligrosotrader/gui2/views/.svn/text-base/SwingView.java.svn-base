package org.juxtapose.bundle.peligrosotrader.gui2.views;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.general.CombinedDataset;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.juxtapose.bundle.peligrosotrader.gui2.util.Translation;
import org.juxtapose.bundle.peligrosotrader.gui2.widgets.PredictionGraf;

import org.juxtapose.bundle.peligrosotrader.gui2.charts.LineChart;
import org.juxtapose.bundle.peligrosotrader.gui2.charts.SwingDatasetCreator;
import peligrosotrader.util.Constants;
import peligrosotrader.util.IAnalyzeProvider;
import peligrosotrader.util.TradeUtil;
import peligrosotrader.util.db.Sample;
import peligrosotrader.util.feeds.MetaFeed;
import peligrosotrader.util.stockloan.LoanIndexFeed;
import peligrosotrader.util.ta.Bolinger;
import peligrosotrader.util.ta.Correlation;
import peligrosotrader.util.ta.HMA;
import peligrosotrader.util.ta.HighLowChannel2;
import peligrosotrader.util.ta.LinearRegression;
import peligrosotrader.util.ta.MA;
import peligrosotrader.util.ta.MultiOscillator;
import peligrosotrader.util.ta.Predict;
import peligrosotrader.util.ta.RSI;
import peligrosotrader.util.ta.Relation;
import peligrosotrader.util.ta.Stochastics;
import peligrosotrader.util.ta.functions.LinearTrend;
import peligrosotrader.util.ta.functions.QPipe;
import peligrosotrader.util.ta.hotpoints.HotPointsStack;
import peligrosotrader.util.ta.trend.SwingTrendFinder;
import peligrosotrader.util.ta.trend.TrendFinder;
import peligrosotrader.util.ta.trendlines.TrendLine;
import peligrosotrader.util.ta.trendlines.TrendStack;

/**
 * @author Peligroso
 *
 */
public class SwingView extends ViewPart implements IAnalyzeProvider{

	public static final String ID = "org.juxtapose.bundle.peligrosotrader.gui2.views.swingview";
	public static final String[] TYPES = new String[]{"LineChart", "BarChart", "CandleSticks"};
	
	private Text m_tickerText;
	private Text m_fromDateText, m_toDateText, m_trendLength, m_trendStrength, m_refferensList, m_corrText, m_periodText, m_followUpText, m_ma, m_rsi, m_bolinger, m_hlChannel;
	private Text m_corrTickerText, m_morphTickerText, m_corrSkewText;
	private Button m_chartButt, m_updateButt, m_procentCheck, m_trend, m_average, m_sameSpace, m_hideGraph, m_staticTrend, m_qPipeTrend, m_hma, m_supportLines, m_resistanceLines, m_supportPoints, m_resistancePoints, m_linearReg;
	private Button m_adaptCharts;
	private ChartComposite m_scriptChart, m_momentumChart, m_stochChart;
	private Composite m_parent, m_pane;
	private Composite predictionPane;
	private GridData m_predData;
	private ScrolledComposite m_scroll;
	private Combo m_typeCombo;
	
	private Text m_shortIndexText; 
	
	private Vector<PredictionGraf> grafList = new Vector<PredictionGraf>();
	
	Vector<Sample> m_samples;
	
	boolean procent;
	
	public static int TYPE_SR = 0;
	public static int TYPE_MA = 1;
	public static int TYPE_BB = 2;
	public static int TYPE_TREND = 3;
	
	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		m_parent = parent;
		parent.setLayout(new FillLayout());
		
		m_scroll = new ScrolledComposite(parent, SWT.V_SCROLL |SWT.BORDER);

		m_pane = new Composite(m_scroll, SWT.BORDER);
		m_scroll.setContent(m_pane);

		m_scroll.setExpandHorizontal(true);
		m_scroll.setExpandVertical(true);
		m_scroll.setMinSize(setMinSize());
		
		m_pane.setSize(m_pane.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		m_pane.setBackground(m_parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		m_pane.setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		GridLayout myLayout = new GridLayout();
		m_pane.setLayout(myLayout);				
		
		m_predData = new GridData();
		m_predData.widthHint = PredictionGraf.WIDTH;
		m_predData.heightHint = PredictionGraf.HEIGHT;
		
		Group controlGroup = new Group(m_pane, SWT.NONE);
		controlGroup.setLayoutData(new GridData( GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL ));
		controlGroup.setText("Controls");
		
		controlGroup.setLayout(new RowLayout());
		new Label(controlGroup, SWT.NONE).setText("Ticker:");
		m_tickerText = new Text(controlGroup, SWT.BORDER);
		
		RowData rd = new RowData();
		rd.width = 70;
		m_tickerText.setLayoutData(rd);
		
		
		new Label(controlGroup, SWT.NONE).setText("From Dates:");
		m_fromDateText = new Text(controlGroup, SWT.BORDER);
		
		new Label(controlGroup, SWT.NONE).setText("To Dates:");
		m_toDateText = new Text(controlGroup, SWT.BORDER);
		
		m_fromDateText.setLayoutData(rd);
		m_toDateText.setLayoutData(rd);
		
		new Label(controlGroup, SWT.NONE).setText("   Procent:");
		m_procentCheck = new Button(controlGroup, SWT.CHECK);
		
		m_procentCheck.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent sev){
				procent = m_procentCheck.getSelection();
			}
		});
		
		m_typeCombo = new Combo(controlGroup, SWT.NONE);
		m_typeCombo.setItems(TYPES);
		m_typeCombo.select(0);
		
		m_chartButt = new Button(controlGroup, SWT.PUSH);
		m_chartButt.setText("Create chart");
		
		m_chartButt.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent sev){
				createChart(true, false, false, 0);
			}
		});
		
		m_shortIndexText = new Text(controlGroup, SWT.BORDER);
		
		m_updateButt = new Button(controlGroup, SWT.PUSH);
		m_updateButt.setText("Update chart");
		
		m_updateButt.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent sev){
				createChart(false, false, false, 0);
			}
		});
				
		Group proformasGroup = new Group(m_pane, SWT.NONE);
		proformasGroup.setLayoutData(new GridData( GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL ));
		proformasGroup.setText("Proformas");
		
		GridLayout profLayout = new GridLayout(1, false);
		
		proformasGroup.setLayout(profLayout);
		
		Composite profComp1 = new Composite(proformasGroup, SWT.BORDER);
		profComp1.setLayoutData(new GridData( GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL ));
		profComp1.setLayout(new RowLayout());
		
		Composite profComp2 = new Composite(proformasGroup, SWT.BORDER);
		profComp2.setLayoutData(new GridData( GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL ));
		profComp2.setLayout(new RowLayout());
		
		
		new Label(profComp1, SWT.NONE).setText("Average Line:");
		m_average = new Button(profComp1, SWT.CHECK);
		new Label(profComp1, SWT.NONE).setText("    Trend analyze:");
		m_trend = new Button(profComp1, SWT.CHECK);
		new Label(profComp1, SWT.NONE).setText("    Same space:");
		m_sameSpace = new Button(profComp1, SWT.CHECK);
		m_sameSpace.setSelection(true);
		
		new Label(profComp1, SWT.NONE).setText("    Hide Graph:");
		m_hideGraph = new Button(profComp1, SWT.CHECK);
		m_hideGraph.setSelection(false);
		
		new Label(profComp1, SWT.NONE).setText("    Trend length:");
		m_trendLength = new Text(profComp1, SWT.BORDER);
		new Label(profComp1, SWT.NONE).setText("    Trend strength:");
		m_trendStrength = new Text(profComp1, SWT.BORDER);
		
		rd = new RowData();
		rd.width = 20;
		
		m_trendLength.setLayoutData(rd);
		m_trendStrength.setLayoutData(rd);
		
		new Label(profComp2, SWT.NONE).setText("   Static trend:");
		m_staticTrend = new Button(profComp2, SWT.CHECK);
		
		new Label(profComp2, SWT.NONE).setText("   Support lines:");
		m_supportLines = new Button(profComp2, SWT.CHECK);	
		
		new Label(profComp2, SWT.NONE).setText("   Resistance lines:");
		m_resistanceLines = new Button(profComp2, SWT.CHECK);
		
		new Label(profComp2, SWT.NONE).setText("   Support points:");
		m_supportPoints = new Button(profComp2, SWT.CHECK);
		
		new Label(profComp2, SWT.NONE).setText("   Resistance points:");
		m_resistancePoints = new Button(profComp2, SWT.CHECK);	
		
		new Label(profComp2, SWT.NONE).setText("   qPipe:");
		m_qPipeTrend = new Button(profComp2, SWT.CHECK);
		
		new Label(profComp2, SWT.NONE).setText("   MA:");
		m_ma = new Text(profComp2, SWT.BORDER);
		
		new Label(profComp2, SWT.NONE).setText("   HMA:");
		m_hma = new Button(profComp2, SWT.CHECK);
		
		new Label(profComp2, SWT.NONE).setText("   RSI:");
		m_rsi = new Text(profComp2, SWT.BORDER);
		
		new Label(profComp2, SWT.NONE).setText("   Bolinger Band:");
		m_bolinger = new Text(profComp2, SWT.BORDER);
		
		new Label(profComp2, SWT.NONE).setText("   HL Channel:");
		m_hlChannel = new Text(profComp2, SWT.BORDER);
		
		new Label(profComp2, SWT.NONE).setText("   Liner regression:");
		m_linearReg = new Button(profComp2, SWT.CHECK);
		
		Group predictionGroup = new Group(m_pane, SWT.NONE);
		predictionGroup.setLayoutData(new GridData( GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL ));
		predictionGroup.setText("Prediction");
		predictionGroup.setLayout(new RowLayout());
		
		new Label(predictionGroup, SWT.NONE).setText("Number of days: ");
		m_periodText = new Text(predictionGroup, SWT.BORDER);
		m_periodText.setText("30");
		new Label(predictionGroup, SWT.NONE).setText("Min correlation: ");
		m_corrText = new Text(predictionGroup, SWT.BORDER);
		m_corrText.setText("0.85");
		new Label(predictionGroup, SWT.NONE).setText("Follow up: ");
		m_followUpText = new Text(predictionGroup, SWT.BORDER);
		m_followUpText.setText("10");
		new Label(predictionGroup, SWT.NONE).setText("Tickers of refferences: ");
		m_refferensList = new Text(predictionGroup, SWT.BORDER);
		RowData reffData = new RowData();
		reffData.width = 300;
		m_refferensList.setLayoutData(reffData);
		
		//Correlation 
		Group correlationGroup = new Group(m_pane, SWT.NONE);
		correlationGroup.setLayoutData(new GridData( GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL ));
		correlationGroup.setLayout(new RowLayout());
		correlationGroup.setText("Correlation");
		
		
		new Label(correlationGroup, SWT.NONE).setText("Pair with: ");
		m_corrTickerText = new Text(correlationGroup, SWT.BORDER);
		RowData rd1 = new RowData();
		rd1.width = 50;
		m_corrTickerText.setLayoutData(rd1);
		
		new Label(correlationGroup, SWT.NONE).setText("Morph on: ");
		m_morphTickerText = new Text(correlationGroup, SWT.BORDER);
		RowData rd2 = new RowData();
		rd2.width = 50;
		m_morphTickerText.setLayoutData(rd2);
		
		new Label(correlationGroup, SWT.NONE).setText("Skew days: ");
		m_corrSkewText = new Text(correlationGroup, SWT.BORDER);
		RowData rd3 = new RowData();
		rd3.width = 50;
		m_corrSkewText.setLayoutData(rd3);
		
		new Label(correlationGroup, SWT.NONE).setText("Scale charts: ");
		m_adaptCharts = new Button(correlationGroup, SWT.CHECK);
		
		
		try{
		
			Vector<double[]> dataValues = new Vector<double[]>(); 
			XYDataset dataset = LineChart.createDataset(dataValues, "---", 0, 9, 07, 02, 2007);
			JFreeChart chart = LineChart.createChart("chart", dataset, 0);
			
			chart.setBackgroundPaint(java.awt.Color.white); 
			
			
			m_scriptChart = new ChartComposite(m_pane, 
															SWT.NONE, 
															chart,
															680,
															700,
															ChartComposite.DEFAULT_MINIMUM_DRAW_WIDTH,
															ChartComposite.DEFAULT_MINIMUM_DRAW_HEIGHT,
															ChartComposite.DEFAULT_MAXIMUM_DRAW_WIDTH,
															ChartComposite.DEFAULT_MAXIMUM_DRAW_HEIGHT,
											                false,
											                true,
											                true,
											                true,
											                true,
											                true);
			
			
			GridData gd = new GridData();
//			GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false);
			gd.horizontalSpan = 2;
			gd.heightHint = 400;
			gd.widthHint = 720;
			m_scriptChart.setLayoutData(gd);
			
			Vector<double[]> dataValues2 = new Vector<double[]>(); 
			XYDataset dataset2 = LineChart.createDataset(dataValues2, "---", 0, 9, 07, 02, 2007);
			JFreeChart chart2 = LineChart.createChart("momentum", dataset2, 0);
			
			
			//RSI Chart
			m_momentumChart = new ChartComposite(m_pane, SWT.NONE, chart2, false);
			m_momentumChart.setBackground(m_momentumChart.getDisplay().getSystemColor(SWT.COLOR_WHITE));
			m_momentumChart.setForeground(m_momentumChart.getDisplay().getSystemColor(SWT.COLOR_WHITE));

			GridData md = new GridData(SWT.FILL, SWT.FILL, true, false);
			md.horizontalSpan = 2;
			md.heightHint = 200;
			m_momentumChart.setLayoutData(md);
			
			m_momentumChart.setVisible(false);
			
			//Stoch Chart
			m_stochChart = new ChartComposite(m_pane, SWT.NONE, chart2, false);
//			m_stochChart.setBackground(m_momentumChart.getDisplay().getSystemColor(SWT.COLOR_GRAY));
//			m_stochChart.setForeground(m_momentumChart.getDisplay().getSystemColor(SWT.COLOR_GRAY));
			
			GridData sd = new GridData(SWT.FILL, SWT.FILL, true, false);
			sd.horizontalSpan = 2;
			sd.heightHint = 200;
			m_stochChart.setLayoutData(sd);
			
			m_stochChart.setVisible(false);
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		createPredictionPane(m_pane);
		
		createHTTPConnection();
		
	}
	
	/**
	 * @param inComposite
	 */
	public void createPredictionPane(Composite inComposite)
	{
		GridData horFill = new GridData();
		horFill.grabExcessHorizontalSpace = true;
		horFill.horizontalAlignment = GridData.FILL;
		
		GridLayout predGrid = new GridLayout();
		predGrid.numColumns = 3;
		predictionPane = new Composite(inComposite, SWT.BORDER);
		predictionPane.setLayout(predGrid);
		predictionPane.setLayoutData(horFill);
//		PredictionGraf pred = new PredictionGraf(predictionPane, SWT.BORDER, new Vector<Sample>());
//		pred.setLayoutData(m_predData);
	}
	
	/**
	 * @param inNewChart
	 */
	public void createChart(boolean inNewChart, boolean inSave, boolean inNotify, int inType){
		
//		String ticker = m_tickerText.getText();
		String[] dates= m_fromDateText.getText().split(", ");
		
		Vector<Vector<Sample>> sampVec = new Vector<Vector<Sample>>();
		
		ArrayList<Integer> typeCount = new ArrayList<Integer>();
		
		ArrayList<String> graphNames = new ArrayList<String>();

		//int i = 0;
		Date constDate = null;
		try{

			//Vector<Sample> samples = FileHandler.getFileQuotes(m_tickerText.getText(), m_fromDateText.getText(), m_toDateText.getText());
			Calendar fromCal = Calendar.getInstance();
			fromCal.setTimeInMillis(Date.valueOf(m_fromDateText.getText()).getTime());
			Calendar toCal = Calendar.getInstance();
			String toDateStr = m_toDateText.getText();
			toCal.setTimeInMillis(Date.valueOf(m_toDateText.getText()).getTime());
			
			//Get graph
//			Vector<Sample> samples;
			if(inNewChart || m_samples == null)
			{
				
				m_samples = MetaFeed.getGraph(m_tickerText.getText(), fromCal, toCal);
				
//				if(m_tickerText.getText().equals("random"))
//				{
//					m_samples = TradeUtil.getRandomChart(300, fromCal, toCal);
//				}
//				else if(m_tickerText.getText().endsWith("="))
//				{
//					m_samples = OandaHttpTimeseries.getTimeSerie(m_tickerText.getText(), fromCal, toCal);
//				}
//				else{
//					m_samples = YahooHistory.getHistoryGraph(m_tickerText.getText(), fromCal, toCal);
//
//
//					Calendar today = Calendar.getInstance();
//					int year = today.get(Calendar.YEAR);
//					int month = today.get(Calendar.MONTH);
//					int day = today.get(Calendar.DAY_OF_MONTH);
//
//					today.clear();
//					today.set(year, month, day);
//
//					if(toCal.equals(today)){
//						System.out.println("today");
//						Sample todaySamp = YahooFeed.getTodaySample(m_tickerText.getText(), false);
//						if(todaySamp != null)
//							m_samples.add(todaySamp);
//					}
//				}
			}
			/***Prediction***/
//			predict(m_samples);
			/****End prediction***/
			
			if(m_samples.size() > 0)
				constDate = m_samples.get(0).date;

			
			if (procent){
				Vector<Sample> procentSamples = TradeUtil.convertToProcent(m_samples);
				sampVec.add(procentSamples);
			}
			else
				sampVec.add(m_samples);
			//#0
			typeCount.add(1);
			graphNames.add(m_tickerText.getText());
			
			if(!m_shortIndexText.getText().equals("")){
				sampVec.add(LoanIndexFeed.createShortIndex(m_shortIndexText.getText()));
			}
			
			//RSI
			String rsiText = m_rsi.getText();
			if(!rsiText.equals("") && ! sampVec.isEmpty()){
				m_momentumChart.setVisible(true);
				GridData showData = new GridData(SWT.FILL, SWT.FILL, true, false);
				showData.horizontalSpan = 2;
				showData.heightHint = 200;
				m_momentumChart.setLayoutData(showData);
				m_pane.layout();
				updateRSIChart(sampVec.get(0), new Integer(rsiText));
				m_scroll.setMinSize(setMinSize());
			} else
			{
				m_momentumChart.setVisible(false);
				GridData hideData = new GridData();
				hideData.heightHint = 0;
				m_momentumChart.setLayoutData(hideData);
				m_pane.layout();
				m_scroll.setMinSize(setMinSize());
			}

			
			//Stochastics
			if(!rsiText.equals("") && ! sampVec.isEmpty()){
				m_stochChart.setVisible(true);
				GridData showData = new GridData(SWT.FILL, SWT.FILL, true, false);
				showData.horizontalSpan = 2;
				showData.heightHint = 200;
				m_stochChart.setLayoutData(showData);
				m_pane.layout();
				updateMultiOsc(sampVec.get(0), new Integer(rsiText));
				m_scroll.setMinSize(setMinSize());
			} else
			{
				m_stochChart.setVisible(false);
				GridData hideData = new GridData();
				hideData.heightHint = 0;
				m_stochChart.setLayoutData(hideData);
				m_pane.layout();
				m_scroll.setMinSize(setMinSize());
			}
			
			
			if(m_trend.getSelection() && !sampVec.isEmpty()){				
				SwingTrendFinder tf = new SwingTrendFinder(sampVec.get(0), new Integer(m_trendStrength.getText()), new Integer(m_trendLength.getText()));
				tf.analyze();
				sampVec.add(tf.getTrendSamples());
				//#1
				typeCount.add(1);
				graphNames.add("Trend");
			}
			
			if(m_staticTrend.getSelection() && ! sampVec.isEmpty()){
				TrendFinder tf = new TrendFinder(sampVec.get(0));
				tf.analyze();
				if(tf.inTrend())
					System.out.println("inTrend");
				
				Vector<Vector<Sample>> trends = tf.getTrend();
				int trendIndex = 0;
				for(Vector<Sample> trend : trends){
					sampVec.add(trend);
					int type = tf.getTrendType(trendIndex);
					if(type == 1)
						typeCount.add(7);
					else
						typeCount.add(13);
					
					graphNames.add("Trend");
					trendIndex++;
				}
//				sampVec.add(tf.getTrendSamples());
//				#2
//				typeCount.add(1);
//				graphNames.add("Trend");
			}
			
			
			if(m_qPipeTrend.getSelection() && ! sampVec.isEmpty()){
				double[] qPipe = QPipe.generateFunction(sampVec.get(0), 0, 90);
				
				if(qPipe != null){
					Correlation corr = new Correlation(qPipe, Sample.extractValues(sampVec.get(0)));
					Double correlation = corr.getCorrAll();
					System.out.println(correlation);
					sampVec.add(TradeUtil.createSampleVec(qPipe, sampVec.get(0)));
//					#3
					typeCount.add(3);
					correlation *= 1000;
					Integer intCorr = correlation.intValue();
					double roundedCorr = intCorr.doubleValue() / 1000d; 
					graphNames.add("QPipe:"+roundedCorr);
				}
			}
			
			if(false)
			{
				double[] line = LinearTrend.generateFunction(sampVec.get(0));
				
				Correlation corr = new Correlation(line, Sample.extractValues(sampVec.get(0)));
				System.out.println(corr.getCorrAll());
				sampVec.add(TradeUtil.createSampleVec(line, sampVec.get(0)));
			}
			
			if(m_linearReg.getSelection())
			{
				LinearRegression lr = new LinearRegression();
				lr.regress(sampVec.get(0));

				sampVec.add(lr.getRegression());
//				#4
				typeCount.add(3);
				graphNames.add("Lin reg");
			}
			
			
			String bbText = m_bolinger.getText();
			if(!bbText.equals("") && ! sampVec.isEmpty()){
				Bolinger bb = new Bolinger(sampVec.get(0), Integer.parseInt(bbText));
				sampVec.addAll(bb.getBolingerBands());
//				#5
				typeCount.add(3);
				typeCount.add(3);
				typeCount.add(3);
				graphNames.add("BB");
				graphNames.add("BB");
				graphNames.add("BB");
			}
			
			
			String hlCText = m_hlChannel.getText();
			if(!hlCText.equals("") && ! sampVec.isEmpty()){
				HighLowChannel2 hlc = new HighLowChannel2(sampVec.get(0));
				sampVec.add(hlc.getHighBand());
				sampVec.add(hlc.getLowBand());
//				#6
				typeCount.add(2);
				typeCount.add(2);
				graphNames.add("HL");
				graphNames.add("HL");
			}
			
			if(m_supportLines.getSelection()){
				
				TrendStack tl = new TrendStack(m_samples, 20, TrendStack.SUPPORT);
				tl.createTrends();
				tl.removeSingles();
				tl.createTrendLines();
				
				Vector<TrendLine> trendDatas = tl.getTrends();
				Vector<Vector<Sample>> trends = tl.getTrendLines();
				//sampVec.add(tl.getBaseLine());
				int index = 0;
				for(Vector<Sample> trend : trends){
					//if(index % 10 == 0)
					sampVec.add(trend);
					
					int strength = trendDatas.get(index).m_mergeCount;
					int colorIndex = 21;
					if(strength > 3)
						colorIndex = 7;
					else if(strength > 1)
						colorIndex = 20;
					
					double trendClose = trend.lastElement().close;
					
					index++;
					graphNames.add("support:"+TradeUtil.formatDouble(trendClose, 2));
					typeCount.add(colorIndex);

				}
				
			}
			
			
			if(m_resistanceLines.getSelection())
			{
				TrendStack tl = new TrendStack(m_samples, 20, TrendStack.RESISTANCE);
				tl.createTrends();
				tl.removeSingles();
				tl.createTrendLines();
				
				Vector<TrendLine> trendDatas = tl.getTrends();
				Vector<Vector<Sample>> trends = tl.getTrendLines();
				
				//sampVec.add(tl.getBaseLine());
				int index = 0;
				for(Vector<Sample> trend : trends){
					//if(index % 10 == 0)
					sampVec.add(trend);
					
					int strength = trendDatas.get(index).m_mergeCount;
					int colorIndex = 23;
					if(strength > 3)
						colorIndex = 8;
					else if(strength > 1)
						colorIndex = 22;
					
					double trendClose = trend.lastElement().close;
					
					graphNames.add("resist:"+TradeUtil.formatDouble(trendClose, 2));
					typeCount.add(colorIndex);
					
					index++;
				}

			}
			
			
			if(m_supportPoints.getSelection())
			{
				HotPointsStack hotPoints = new HotPointsStack(m_samples, HotPointsStack.SUPPORT, true, 2);
				Vector<Vector<Sample>> points = hotPoints.getLines(-1);
				
				for(Vector<Sample> p : points){
					sampVec.add(p);
					double val = p.lastElement().close;
					graphNames.add("support:"+val);
					typeCount.add(9);
				}				
			}
			
			if(m_resistancePoints.getSelection())
			{
				HotPointsStack hotPoints = new HotPointsStack(m_samples, HotPointsStack.RESISTANCE, true, 2);
				Vector<Vector<Sample>> points = hotPoints.getLines(0);
				
				for(Vector<Sample> p : points){
					sampVec.add(p);
					double val = p.lastElement().close;
					graphNames.add("resist:"+val);
					typeCount.add(8);
				}				
			}

			
			String maText = m_ma.getText();
			if(!maText.equals("") && ! sampVec.isEmpty()){
				String[] mas = maText.split(" ");
				
				int colorIndex = 14;
				for(String maString : mas){
					MA ma = new MA(sampVec.get(0), new Integer(maString));

					sampVec.add(ma.getMA());
					graphNames.add("MA"+maString);
					typeCount.add(colorIndex);
					colorIndex++;
				}
				
			}

			
			if(m_hma.getSelection() && ! sampVec.isEmpty()){
				HMA hma = new HMA(sampVec.get(0), 20);
				
				sampVec.add(hma.getMA());
				typeCount.add(10);
				graphNames.add("HMA");
			}
			
			/*******Correlation*********/
			String corrTicker = m_corrTickerText.getText();
			Vector<Sample> corrSamples = null;
			if(corrTicker != null && !"".equals(corrTicker))
			{
				corrSamples = MetaFeed.getGraph(corrTicker, fromCal, toCal);
				
				if( m_morphTickerText.getText() != null && !m_morphTickerText.getText().equals("") ) 
				{
					Vector<Sample> morphSamples = MetaFeed.getGraph(m_morphTickerText.getText(), fromCal, toCal);
					corrSamples = TradeUtil.mergeGraphs(corrSamples, morphSamples);
				}
				if( !m_adaptCharts.getSelection() ){
				
					sampVec.add(corrSamples);
					graphNames.add(corrTicker);
					corrSamples = null;
				}
				
				double[] graph1 = TradeUtil.getCloseArray(sampVec.get(0));
				double[] graph2 = TradeUtil.getCloseArray(sampVec.get(sampVec.size()-1));
				Correlation corr = new Correlation(graph1, graph2);
				System.out.println("Correlation: "+corr.getCorrAll());
				
				Relation rel = new Relation(graph1, graph2);
				rel.calcRelations();
				System.out.println("Standard avvikelse: "+rel.getStdDev());
				System.out.println("Avg: "+rel.getAvg());
				
			}
			
			
			if(m_hideGraph.getSelection() && ! sampVec.isEmpty()){
				sampVec.remove(0);
				typeCount.remove(0);
				graphNames.remove(0);
			}
		
			
			if (!m_sameSpace.getSelection())
				constDate = null;
			
			JFreeChart chart = null;
			SegmentedTimeline timeline = null;
			timeline = SegmentedTimeline.newMondayThroughFridayTimeline();
			
			if(m_typeCombo.getText().equals(TYPES[0])){
				XYDataset dataset = SwingDatasetCreator.createDataset(sampVec, graphNames);
				chart = LineChart.createChart("Line chart", dataset, dates.length);
				
				final NumberAxis vaxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
		        vaxis.setAutoRangeIncludesZero(false);
		        
		        DateAxis dateAxis = new DateAxis("dateAxisLabel");
		        dateAxis.setTimeline(timeline);
		        
		        chart.getXYPlot().setDomainAxis(dateAxis);
		        
		        if( corrSamples != null )
		        {
		        	final XYPlot plot = chart.getXYPlot();
		            final NumberAxis axis2 = new NumberAxis("Secondary");
		            axis2.setAutoRangeIncludesZero(false);
		            plot.setRangeAxis(1, axis2);
		            plot.setDataset(1, SwingDatasetCreator.createDataset(corrSamples, corrTicker));
		            plot.mapDatasetToRangeAxis(1, 1);
		            
		            final XYItemRenderer renderer = plot.getRenderer();
		            renderer.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
		            if (renderer instanceof StandardXYItemRenderer) {
		                final StandardXYItemRenderer rr = (StandardXYItemRenderer) renderer;
		                rr.setShapesFilled(true);
		            }
		            
		            final StandardXYItemRenderer renderer2 = new StandardXYItemRenderer();
		            renderer2.setSeriesPaint(0, Color.black);
		            renderer.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
		            plot.setRenderer(1, renderer2);

		        }
				
			}
			else if(m_typeCombo.getText().equals(TYPES[1])){
				CombinedDataset dataset = SwingDatasetCreator.createCombinedDataset(sampVec, graphNames);
//				OHLCDataset dataset = SwingDatasetCreator.createCSDataset(sampVec);
				chart = ChartFactory.createHighLowChart("Bar chart", "time", "Value", dataset, timeline, true);

			}
			else if(m_typeCombo.getText().equals(TYPES[2]))
			{
				OHLCDataset dataset = SwingDatasetCreator.createCSDataset(sampVec);
				
				ArrayList<XYDataset> momentumData = new ArrayList<XYDataset>();
				if(!rsiText.equals("") && ! sampVec.isEmpty()){
					
					XYDataset rsiDataset = getRSIData(sampVec.get(0), new Integer(rsiText));
					momentumData.add(rsiDataset);
				}
				
				sampVec.remove(0);
				graphNames.remove(0);
				
				XYDataset dataset2 = SwingDatasetCreator.createDataset(sampVec, graphNames);

//				chart = LineChart.createCandleStickChart(dataset, dataset2,typeCount);
				
				String title = Translation.getCompany(m_tickerText.getText());
				chart = LineChart.createCSChartWithRSI(title, dataset, dataset2, momentumData, typeCount, inType, inSave || inNotify);

			}

			chart.setBackgroundPaint(java.awt.Color.white);
			
			m_scriptChart.setChart(chart);

			
			m_scriptChart.forceRedraw();
			m_scriptChart.update();
			
			m_parent.redraw();
			m_parent.update();
			m_parent.getDisplay().update();	
			
			if(inSave)
			{
				String[] tickerSplit = m_tickerText.getText().split("\\.");
				String ticker = tickerSplit[0];
				
				m_scriptChart.doSaveAs(Constants.IMAGE_MAP+"\\analysis\\"+ticker+".png");
				
			}
			if(inNotify)
			{
				String[] tickerSplit = m_tickerText.getText().split("\\.");
				String ticker = tickerSplit[0];
				
				BufferedImage im = m_scriptChart.getChart().createBufferedImage(m_scriptChart.getBounds().width, m_scriptChart.getBounds().height);
				analyzeResponse(im, m_scriptChart.getBounds().width, m_scriptChart.getBounds().height);
			}
			
		}catch (FileNotFoundException iex)
		{
			iex.printStackTrace();
			System.out.println("Not found");
			if(inNotify)
				analyzeResponse(null, 0, 0);
		}catch (IllegalArgumentException iex)
		{
			iex.printStackTrace();
			System.out.println("Not found");
			if(inNotify)
				analyzeResponse(null, 0, 0);
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	/**
	 * @param samples
	 */
	private void predict(Vector<Sample> samples){
		for(PredictionGraf p : grafList)
			p.dispose();
		grafList.clear();
		
		Double minCorrValue = new Double(m_corrText.getText());
		int numbDays = new Integer(m_periodText.getText());
		int followUp = new Integer(m_followUpText.getText());
		
		if(numbDays < samples.size()){
			List<Sample> graphToTest = samples.subList(samples.size()-numbDays, samples.size());
			PredictionGraf testPred = new PredictionGraf(predictionPane, SWT.BORDER, graphToTest);
			testPred.setLayoutData(m_predData);
			grafList.add(testPred);

			Predict predict = new Predict(samples.subList(0, samples.size()-numbDays-1), samples.subList(samples.size()-numbDays, samples.size()), followUp, minCorrValue);		

			testPred.setLayoutData(m_predData);
			if(predict.getPredictionGrafData() != null){
				List<Predict.CloseSample> predictions = predict.getPredictionGrafData();
				for(Predict.CloseSample cs : predictions){
					PredictionGraf pred = new PredictionGraf(predictionPane, SWT.BORDER, cs);
					pred.setLayoutData(m_predData);
					grafList.add(pred);
				}
			}
		}
		
		m_pane.layout();
		m_pane.update();
		predictionPane.layout();
		predictionPane.update();
		predictionPane.redraw();
		m_scroll.setMinSize(setMinSize());
	}
	
	/**
	 * @param inVec
	 */
	private void createAverage(Vector<Vector<Sample>> inVec){
	
		int minLength = 0;
		if(inVec.size()!= 0)
			minLength = inVec.get(0).size();
		
		for (Vector<Sample> actDataset : inVec){
			if (actDataset.size() < minLength)
				minLength = actDataset.size();
		}
		
		Vector<Sample> avgVec = new Vector<Sample>();
		
		for(int i = 0; i < minLength; i++){
			double total = 0;
			Date date = null; 
			Time time = null;
			boolean first = true;
			for(Vector<Sample> actDataset : inVec){
				total += new Double(actDataset.get(i).last);
				if (first){
					date = actDataset.get(i).date;
					time = actDataset.get(i).time;
					first = false;
				}
			}
			avgVec.add(new Sample("Avrege", new Double(total / inVec.size()).toString(), "0", 
					"0", 0, date, time));
		}
		inVec.add(avgVec);
	}
	
	/**
	 * @param inVec
	 * @param inPeriod
	 */
	public void updateRSIChart(Vector<Sample> inVec, int inPeriod){
		
		
		/**RSI**/
		String[] dates= m_fromDateText.getText().split(", ");
		
		RSI rsi = new RSI(inVec, inPeriod);
		Vector<Sample> RSIGraph = rsi.getRSIGraph();
		ArrayList<String> graphNames = new ArrayList<String>();
		
		graphNames.add("RSI");
		graphNames.add("30");
		graphNames.add("70");
		
		Vector<Vector<Sample>> rsiCollection = new Vector<Vector<Sample>>();
		rsiCollection.add(RSIGraph);
		
		rsiCollection.add(rsi.get30Line());
		rsiCollection.add(rsi.get70Line());
		
		XYDataset rsiDataset = SwingDatasetCreator.createDataset(rsiCollection, graphNames);
		JFreeChart RSIChart = LineChart.createChart("", rsiDataset, dates.length);
		
		SegmentedTimeline rsiTimeline = null;
		rsiTimeline = SegmentedTimeline.newMondayThroughFridayTimeline();
		
		
		final NumberAxis rsiVaxis = (NumberAxis) RSIChart.getXYPlot().getRangeAxis();
        rsiVaxis.setAutoRangeIncludesZero(false);
//        rsiVaxis.setLowerBound(0);
//        rsiVaxis.setUpperBound(100);
        
        DateAxis rsiDateAxis = new DateAxis("dateAxisLabel");
        rsiDateAxis.setTimeline(rsiTimeline);
        
        RSIChart.getXYPlot().setDomainAxis(rsiDateAxis);
        
		
		m_momentumChart.setChart(RSIChart);

		
		m_momentumChart.forceRedraw();
		m_momentumChart.update();
		
		/**END RSI**/
	}
	
	public XYDataset getRSIData(Vector<Sample> inVec, int inPeriod){
		
		/**RSI dataset**/
		
		RSI rsi = new RSI(inVec, inPeriod);
		Vector<Sample> RSIGraph = rsi.getRSIGraph();
		ArrayList<String> rSIgraphNames = new ArrayList<String>();
		
		rSIgraphNames.add("RSI");
		rSIgraphNames.add("30");
		rSIgraphNames.add("70");
		
		Vector<Vector<Sample>> rsiCollection = new Vector<Vector<Sample>>();
		rsiCollection.add(RSIGraph);
		
		rsiCollection.add(rsi.get30Line());
		rsiCollection.add(rsi.get70Line());
		
		XYDataset rsiDataset = SwingDatasetCreator.createDataset(rsiCollection, rSIgraphNames);
		
		return rsiDataset;
		/**END RSI**/
	}
	
	/**
	 * @param inVec
	 * @param inPeriod
	 */
	public void updateStochastics(Vector<Sample> inVec, int inPeriod)
	{
		String[] dates= m_fromDateText.getText().split(", ");
		
		Stochastics stoch = new Stochastics(inVec, inPeriod, true);
		Vector<Sample> StochGraph = stoch.getSlowStochGraph();
		
		ArrayList<String> graphNames = new ArrayList<String>();
		
		graphNames.add("STOCH");
		graphNames.add("20");
		graphNames.add("80");
		
		Vector<Vector<Sample>> stochCollection = new Vector<Vector<Sample>>();
		stochCollection.add(StochGraph);
		
		stochCollection.add(stoch.get20Line());
		stochCollection.add(stoch.get80Line());
		
		XYDataset stochDataset = SwingDatasetCreator.createDataset(stochCollection, graphNames);
		JFreeChart StochChart = LineChart.createChart("", stochDataset, dates.length);
		
		SegmentedTimeline stochTimeline = null;
		stochTimeline = SegmentedTimeline.newMondayThroughFridayTimeline();
		
		
		final NumberAxis rsiVaxis = (NumberAxis) StochChart.getXYPlot().getRangeAxis();
        rsiVaxis.setAutoRangeIncludesZero(false);
        
        DateAxis rsiDateAxis = new DateAxis("dateAxisLabel");
        rsiDateAxis.setTimeline(stochTimeline);
        
        StochChart.getXYPlot().setDomainAxis(rsiDateAxis);
        
		m_stochChart.setChart(StochChart);

		m_stochChart.forceRedraw();
		m_stochChart.update();
	}
	
	/**
	 * @param inVec
	 * @param inPeriod
	 */
	public void updateMultiOsc(Vector<Sample> inVec, int inPeriod)
	{
		String[] dates= m_fromDateText.getText().split(", ");
		
		MultiOscillator mult = new MultiOscillator(inVec);
		Vector<Sample> StochGraph = mult.getMultiOscGraph();
		
		ArrayList<String> graphNames = new ArrayList<String>();
		
		graphNames.add("MO");
		graphNames.add("20");
		graphNames.add("80");
		
		Vector<Vector<Sample>> stochCollection = new Vector<Vector<Sample>>();
		stochCollection.add(StochGraph);
		
		stochCollection.add(mult.get20Line());
		stochCollection.add(mult.get80Line());
		
		XYDataset stochDataset = SwingDatasetCreator.createDataset(stochCollection, graphNames);
		JFreeChart StochChart = LineChart.createChart("", stochDataset, dates.length);
		
		SegmentedTimeline stochTimeline = null;
		stochTimeline = SegmentedTimeline.newMondayThroughFridayTimeline();
		
		
		final NumberAxis rsiVaxis = (NumberAxis) StochChart.getXYPlot().getRangeAxis();
        rsiVaxis.setAutoRangeIncludesZero(false);
        
        DateAxis rsiDateAxis = new DateAxis("dateAxisLabel");
        rsiDateAxis.setTimeline(stochTimeline);
        
        StochChart.getXYPlot().setDomainAxis(rsiDateAxis);
        
		m_stochChart.setChart(StochChart);

		m_stochChart.forceRedraw();
		m_stochChart.update();
	}
	
	public void setParameters(int inType, boolean inRSI, int inChartType)
	{
		reset();
		
		m_typeCombo.select(inChartType);
		
		if(inType == TYPE_SR)
		{
			m_supportLines.setSelection(true);
			m_resistanceLines.setSelection(true);
			m_supportPoints.setSelection(true);
			m_resistancePoints.setSelection(true);
		}
		else if(inType == TYPE_MA)
		{
			m_ma.setText("15 20 25 30 35 40 45 50 55 60");
			m_hma.setSelection(true);
		}
		else if(inType == TYPE_TREND)
		{
			m_staticTrend.setSelection(true);
//			m_linearReg.setSelection(true);
			m_qPipeTrend.setSelection(true);
		}
		
		if(inRSI)
			m_rsi.setText("14");
	}
	
	public void setParamsFromConnections(Set<Integer> inConns, String inMA, boolean inSupportP, boolean inTrendSupport, boolean inResistanceP, boolean inTrendRes)
	{
		reset();
		
		m_supportLines.setSelection(inTrendSupport);
		m_resistanceLines.setSelection(inTrendRes);
		m_supportPoints.setSelection(inSupportP);
		m_resistancePoints.setSelection(inResistanceP);
		
		if(inMA != null)
			m_ma.setText(inMA);
	}
	
	public void reset(){
		
		m_trendLength.setText("");
		m_trendStrength.setText("");
		m_ma.setText("");
		m_rsi.setText("");
		m_bolinger.setText("");
		m_hlChannel.setText("");
		m_procentCheck.setSelection(false);
		m_trend.setSelection(false);
		m_sameSpace.setSelection(true);
		m_hideGraph.setSelection(false);
		m_staticTrend.setSelection(false);
		m_qPipeTrend.setSelection(false);
		m_hma.setSelection(false);
		m_supportLines.setSelection(false);
		m_resistanceLines.setSelection(false);
		m_supportPoints.setSelection(false);
		m_resistancePoints.setSelection(false);
		m_linearReg.setSelection(false);
	}
	
	public void setChartFromSystem(String inTicker, boolean inSave, boolean inNotify, String inFromDate, String inToDate, int inType, boolean inRSI)
	{
		setParameters(inType, inRSI, 2);
		setChartFromSystem(inTicker, inSave, inNotify, inFromDate, inToDate, inType);
	}
	
	
	
	/**
	 * @param inSamp
	 */
	public void setChartFromSystem( Vector<Sample> inSamp )
	{
		setParameters(100, false, 0);
		
		Vector<Vector<Sample>> sampVec = new Vector<Vector<Sample>>();
		sampVec.add( inSamp );
		
		ArrayList<String> names = new ArrayList<String>();
		names.add("Chart");
		
		JFreeChart chart = null;
		SegmentedTimeline timeline = null;
		timeline = SegmentedTimeline.newMondayThroughFridayTimeline();
		
		XYDataset dataset = SwingDatasetCreator.createDataset(sampVec, names);
		chart = LineChart.createChart("Line chart", dataset, 1);
		
		final NumberAxis vaxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
        vaxis.setAutoRangeIncludesZero(false);
        
        DateAxis dateAxis = new DateAxis("dateAxisLabel");
        dateAxis.setTimeline(timeline);
        
        chart.getXYPlot().setDomainAxis(dateAxis);
        
        chart.setBackgroundPaint(java.awt.Color.white);
		
		m_scriptChart.setChart(chart);

		m_scriptChart.forceRedraw();
		m_scriptChart.update();
		
		m_parent.redraw();
		m_parent.update();
		m_parent.getDisplay().update();	
		
		
		BufferedImage im = m_scriptChart.getChart().createBufferedImage(m_scriptChart.getBounds().width, m_scriptChart.getBounds().height);
		analyzeResponse(im, m_scriptChart.getBounds().width, m_scriptChart.getBounds().height);
		
		
	}
	
	/**
	 * @param inTicker
	 */
	public void setChartFromSystem(String inTicker, boolean inSave, boolean inNotify, String inFromDate, String inToDate, int inType)
	{
		m_tickerText.setText(inTicker);
		
//		long currentTime = System.currentTimeMillis();
//		Date tD = new Date(currentTime);
//		//long beforeTime = currentTime - 17280000000l;
//		Date fD = new Date(currentTime);
//		
//		fD.setYear(105);
//		fD.setMonth(0);
//		fD.setDate(1);
		
		
		m_fromDateText.setText(inFromDate);
		m_toDateText.setText(inToDate);
		
		setParameters(inType, false, 2);
		createChart(true, inSave, inNotify, inType);
		
	}
	
	/**
	 * @return
	 */
	private Point setMinSize(){
		return m_pane.computeSize(SWT.DEFAULT, SWT.DEFAULT);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 
	 */
	public void createHTTPConnection()
	{
		org.juxtapose.bundle.peligrosotrader.gui2.Activator.getDefault().setAnalyseProvider(this);
	}
	
	
	/**
	 * @param inAnalyze
	 */
	public void analyzeResponse(Image inIm, int inWidth, int inHeight)
	{
		org.juxtapose.bundle.peligrosotrader.gui2.Activator.getDefault().analyzeResponse(inIm, inWidth, inHeight);
	}
	
	public void createAnalyze(final String inString, final String inFromDate, final String inToDate, final int inType, final boolean inRSI)
	{
		m_pane.getDisplay().syncExec(new Runnable(){

			public void run()
			{
				setChartFromSystem(inString, false, true, inFromDate, inToDate, inType, inRSI);
				
			}
		});
	}
	
	public void createChart(final Vector<Sample> inSamp)
	{
		m_pane.getDisplay().syncExec(new Runnable(){

			public void run()
			{
				setChartFromSystem( inSamp );
			}
		});
	}

}
