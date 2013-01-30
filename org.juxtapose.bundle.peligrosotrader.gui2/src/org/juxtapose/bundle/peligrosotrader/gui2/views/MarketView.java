package org.juxtapose.bundle.peligrosotrader.gui2.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.experimental.chart.swt.ChartComposite;

import org.juxtapose.bundle.peligrosotrader.gui2.Activator;
import peligrosotrader.predict.MarketAdapter;
import peligrosotrader.predict.pm.PM;
import peligrosotrader.predict.rules.IRule;
import peligrosotrader.predict.rules.RulesTracker;
import peligrosotrader.util.statistics.PredictionStats;
import peligrosotrader.util.statistics.Stats;

/**
 * 
 * @author Peligroso
 *
 */
public class MarketView extends ViewPart {
	
	public static String ID = "org.juxtapose.bundle.peligrosotrader.gui2.views.marketview";

	MarketAdapter m_market;
	PM m_pm;
	
	Combo m_algoCombo;
	
	ScrolledComposite m_sc;
	ChartComposite m_timeChartWidget, m_posNegChartWidget, m_normDistChartWidget, m_yearChartWidget, m_monthChartWidget;
	JFreeChart m_timeChart, m_posNegChart, m_normDistChart, m_yearChart, m_monthChart;
	
	Button m_avgButt, m_dayAvgButt, m_posButt, m_negButt;
	
	DefaultCategoryDataset m_timeDataset, m_yearDataset, m_monthDataset;
	DefaultXYDataset m_normDataset;
	FilterPane m_fPane;
	
	Composite base;
	
	ArrayList<String> m_normKeys = new ArrayList<String>();
	
	@Override
	public void createPartControl(Composite parent) 
	{
		
		m_sc = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.H_SCROLL);
		
		base = new Composite(m_sc, SWT.NONE);
		m_sc.setContent(base);
		m_sc.setExpandHorizontal(true);
		m_sc.setExpandVertical(true);
		
		
		GridLayout gl = new GridLayout(1, false);
		base.setLayout(gl);
		
		Button m_getStatsButt = new Button(base, SWT.PUSH);
		m_getStatsButt.setText("get Statistics");
		m_getStatsButt.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent sev){
				m_market = Activator.getDefault().getMarketPublisher();
				setMarket();
			}
		});
		

		Composite algoComp = new Composite(base, SWT.NONE);
		algoComp.setLayout(new RowLayout());
		new Label(algoComp, SWT.NONE).setText("Algos: ");
		
		m_algoCombo = new Combo(algoComp, SWT.NONE);
		
		m_algoCombo.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent sev){
				String algoName = m_algoCombo.getText();
				if(!algoName.equals("") && algoName != null)
					setAlgo(algoName);
			}
		});
		
		
		GridData paneData = new GridData();
		paneData.grabExcessHorizontalSpace = true;
		paneData.horizontalAlignment = GridData.FILL;

//		paneData.heightHint = 200;
		
		//FILTER PANE
		
		m_fPane = new FilterPane(base, SWT.BORDER); 
		m_fPane.setLayoutData(paneData);
		
		
		GridData chartPaneData = new GridData();
		chartPaneData.grabExcessHorizontalSpace = true;
		chartPaneData.horizontalAlignment = GridData.FILL;
		//chartPaneData.heightHint = 230;

//		CHART

		Composite chartPane = new Composite(base, SWT.BORDER);

		chartPane.setLayoutData(chartPaneData);
		chartPane.setLayout(new GridLayout(3, false));


		GridData chartData = new GridData();

		chartData.grabExcessHorizontalSpace = true;
		chartData.horizontalAlignment = GridData.FILL;
		chartData.heightHint = 200;
		chartData.widthHint = 390;


		m_timeDataset = new DefaultCategoryDataset();

		m_timeChart = ChartFactory.createLineChart("", "day", "gain", m_timeDataset,
				PlotOrientation.VERTICAL, true, true, true);
		m_timeChart.setBackgroundPaint(java.awt.Color.white);

		m_timeChartWidget = new ChartComposite(chartPane, SWT.NONE, m_timeChart, false);
		m_timeChartWidget.setLayoutData(chartData);


//		dataset.addValue(0.4, "avg gain", "2");
//		dataset.addValue(0.5, "avg gain", "3");
//		dataset.addValue(0.3, "avg gain", "4");
//		dataset.addValue(0.1, "avg gain", "5");
//		dataset.addValue(-0.3, "avg gain", "6");
//		dataset.addValue(0.2, "avg gain", "7");

		//POS/NEG CHART
//		
//		Composite barChartPane = new Composite(parent, SWT.NONE);
//		barChartPane.setLayoutData(paneData);
//		barChartPane.setLayout(new GridLayout(2, false));
//		
		m_posNegChart = getBarChart();
		m_posNegChart.setBackgroundPaint(java.awt.Color.white);

		GridData barData = new GridData();
		barData.widthHint = 390;
		barData.heightHint = 200;

		m_posNegChartWidget = new ChartComposite(chartPane, SWT.BORDER, m_posNegChart, false);

		m_posNegChartWidget.setLayoutData(barData);
		
//		Entitys

		GridData entityData = new GridData();

		entityData.widthHint = 150;
		entityData.heightHint = 200;


		Group entityGroup = new Group(chartPane, SWT.NONE);

		entityGroup.setText("stats");
		entityGroup.setLayoutData(entityData);
		entityGroup.setLayout(new GridLayout(2, false));

		
		m_avgButt = new Button(entityGroup, SWT.CHECK);
		new Label(entityGroup, SWT.NONE).setText(" Avrage");
		
		m_dayAvgButt = new Button(entityGroup, SWT.CHECK);
		new Label(entityGroup, SWT.NONE).setText(" Daily return");
		
		m_posButt = new Button(entityGroup, SWT.CHECK);
		new Label(entityGroup, SWT.NONE).setText(" Avg Positives");
		
		m_negButt = new Button(entityGroup, SWT.CHECK);
		new Label(entityGroup, SWT.NONE).setText(" Avg Negatives");
	


		
		//NORMAL DISTRIBUTION
		m_normDataset = new DefaultXYDataset();

		m_normDistChart = ChartFactory.createXYLineChart("", "count", " avg result", m_normDataset,
				PlotOrientation.VERTICAL, true, true, true);
		m_normDistChart.setBackgroundPaint(java.awt.Color.white);

		m_normDistChartWidget = new ChartComposite(base, SWT.BORDER, m_normDistChart, false);
		m_normDistChartWidget.setLayoutData(chartData);
		
		
		
		createSessonGraphPane(base);
		
		m_sc.setMinSize(setMinSize());

	}


	public JFreeChart getBarChart() {
		double[][] data = new double[][]
		        {{0, 0, 0, 0},
				{0, 0, 0, 0}};

		CategoryDataset cData = DatasetUtilities.createCategoryDataset(new String[]{"Negatives", "Positives"}, new String[]{"Sample", "Sample2", "Sample3", "Sample4"}, data);
//		CategoryDataset cData = DatasetUtilities.createCategoryDataset("Rowkey", "colKey", data);

		JFreeChart barChart = ChartFactory.createBarChart ("Pos / Negs",
				"",
				"",
				cData, PlotOrientation.VERTICAL,
				true, true, true);
		return barChart;

	}
	
	public void getLineChart(){
		
	}


	public void setMarket(){
		
		if(m_market != null){
			for(PM pm : m_market.getPMs()){
				m_algoCombo.add(pm.getRule().getName());
			}
		}
	}
	/**
	 * 
	 * @param inName
	 */
	public void setAlgo(String inName)
	{	
		
		for(PM pm : m_market.getPMs()){
			if(pm.getRule().getName().equals(inName)){
				if(m_pm != pm){
					if(m_pm != null)
						m_pm.unInit();
					m_pm = pm;
					pm.init();
					
					m_fPane.setParams(pm.getRule().getRuleParams());
				}
				setTimeLinechart();
				setPosNegChart();
				setNormalDistributionChart();
				setYearChart(2000, 2008);
				setMonthChart();
				break;
			}
		}
		saveCharts();
	}
	
	public void setTimeLinechart()
	{
		if(m_pm != null){
			m_timeDataset.clear();
			
			if(m_avgButt.getSelection()){
				for(PredictionStats stat : m_pm.getStats()){
					Stats res = stat.getStats(getKeys());

					m_timeDataset.addValue(res.avgPercent, "avg gain", Integer.toString(stat.m_holdingDays));
				}
			}
			
			if(m_dayAvgButt.getSelection()){
				for(PredictionStats stat : m_pm.getStats()){
					Stats res = stat.getStats(getKeys());

					m_timeDataset.addValue(res.getAvgPerDay(), "daily return", Integer.toString(stat.m_holdingDays));
				}
			}
			
			if(m_posButt.getSelection()){
				for(PredictionStats stat : m_pm.getStats()){
					Stats res = stat.getStats(getKeys());

					m_timeDataset.addValue(res.avgPercentPos, "avg positives", Integer.toString(stat.m_holdingDays));
				}
			}
			
			if(m_negButt.getSelection()){
				for(PredictionStats stat : m_pm.getStats()){
					Stats res = stat.getStats(getKeys());

					m_timeDataset.addValue(res.avgPercentNeg, "avg negatives", Integer.toString(stat.m_holdingDays));
				}
			}
		
			m_timeChartWidget.forceRedraw();
			m_timeChartWidget.update();
		}
	}
	
	public void setNormalDistributionChart(){
		
		if(m_pm != null){

			for(String key : m_normKeys)
				m_normDataset.removeSeries(key);
			
			m_normKeys.clear();
			
			for(PredictionStats stat : m_pm.getStats()){
				Stats res = stat.getStats(getKeys());

				double[] xVals = new double[res.m_normDist.size()];
				double[] yVals = new double[res.m_normDist.size()];
				int index = 0;
				for(Double key : res.m_normDist.keySet()){
					Integer count = res.m_normDist.get(key);
					xVals[index] = key;
					yVals[index] = count;
					//m_normDataset.addValue(count, Integer.toString(stat.m_holdingDays), key);

					index++;
				}
				m_normDataset.addSeries(Integer.toString(stat.m_holdingDays), new double[][]{xVals, yVals});
				m_normKeys.add(Integer.toString(stat.m_holdingDays));
			}
			
			m_normDistChartWidget.forceRedraw();
			m_timeChartWidget.update();
				
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<Integer> getKeys(){
		return m_fPane.getFilterSelections();
		//return new HashSet<Integer>();
	}
	/**
	 * 
	 *
	 */
	public void setPosNegChart(){
		
		double[] positives = new double[m_pm.getStats().size()];
		double[] negatives = new double[m_pm.getStats().size()];
		String[] days = new String[m_pm.getStats().size()];
		
		int i = 0;
		for(PredictionStats stat : m_pm.getStats()){
			Stats res = stat.getStats(getKeys());
			
			positives[i] = res.positives;
			negatives[i] = res.negatives;
			days[i] = ""+res.m_holdingDays;
			i++;
		}
		
		double[][] data = new double[][]
		               		        {negatives,
		               				positives};

		CategoryDataset cData = DatasetUtilities.createCategoryDataset(new String[]{"Negatives", "Positives"}, days, data);
		//CategoryDataset cData = DatasetUtilities.createCategoryDataset("Rowkey", "colKey", data);

		m_posNegChart = ChartFactory.createBarChart ("Pos / Negs",
				"",
				"",
				cData, PlotOrientation.VERTICAL,
				true, true, true);
		m_posNegChart.setBackgroundPaint(java.awt.Color.white);
		
		m_posNegChartWidget.setChart(m_posNegChart);
		m_posNegChartWidget.forceRedraw();
		m_posNegChartWidget.update();
	}


	/**
	 * 
	 * @param inStartYear
	 * @param inEndYear
	 */
	public void setYearChart(int inStartYear, int inEndYear)
	{
	
		m_yearDataset.clear();

		for(PredictionStats stat : m_pm.getStats())
		{
			for(int year = inStartYear; year < inEndYear; year++)
			{

				Set<Integer> conns = getKeys();
				conns.add(year);
				
				System.out.println(conns);
				
				
				Stats res = stat.getStats(conns);

				m_yearDataset.addValue(res.avgPercent, Integer.toString(stat.m_holdingDays), Integer.toString(year));

			}
		}
		
		m_yearChartWidget.forceRedraw();
		m_yearChartWidget.update();
		
	}
	
	private void setMonthChart(){
		
		m_monthDataset.clear();

		for(PredictionStats stat : m_pm.getStats())
		{
			for(int month = 0; month < 12; month++)
			{

				Set<Integer> conns = getKeys();
				conns.add(month+1000);
				Stats res = stat.getStats(conns);

				m_monthDataset.addValue(res.avgPercent, Integer.toString(stat.m_holdingDays), Integer.toString(month+1));

			}
		}
		
		m_monthChartWidget.forceRedraw();
		m_monthChartWidget.update();
		
	}
	
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	

	public Point setMinSize(){
		return base.computeSize(SWT.DEFAULT, SWT.DEFAULT);
	}
	
	/**
	 * 
	 * @param inComp
	 */
	public void createSessonGraphPane(Composite inComp)
	{
		
		Composite seassonComp = new Composite(inComp, SWT.NONE);
		
		GridData seasData = new GridData();
		seasData.horizontalAlignment = GridData.FILL;
		seasData.grabExcessHorizontalSpace = true;
		
		seassonComp.setLayoutData(seasData);
		
		seassonComp.setLayout(new GridLayout(2, false));
		
		GridData seasChartData = new GridData();
//		seasChartData.horizontalAlignment = GridData.FILL;
//		seasChartData.grabExcessHorizontalSpace = true;
		seasChartData.heightHint = 200;
		seasChartData.widthHint = 390;
		
		//YEAR chart
		m_yearDataset = new DefaultCategoryDataset();

		m_yearChart = ChartFactory.createLineChart("", "Year", "avg gain", m_yearDataset,
				PlotOrientation.VERTICAL, true, true, true);
		m_yearChart.setBackgroundPaint(java.awt.Color.white);
		
		m_yearChartWidget = new ChartComposite(seassonComp, SWT.NONE, m_yearChart, false);
		m_yearChartWidget.setLayoutData(seasChartData);
		
		//MONTH chart
		m_monthDataset = new DefaultCategoryDataset();

		m_monthChart = ChartFactory.createLineChart("", "Month", "avg gain", m_monthDataset,
				PlotOrientation.VERTICAL, true, true, true);
		m_monthChart.setBackgroundPaint(java.awt.Color.white);
		
		m_monthChartWidget = new ChartComposite(seassonComp, SWT.NONE, m_monthChart, false);
		m_monthChartWidget.setLayoutData(seasChartData);
		
		
	}
	
	public void setStatsFromSystem(Set<Integer> inConns)
	{
		//find first algo
		String ruleName = null;
		for(Integer con : inConns){
			if(con > 100 && con < 200){
				IRule rule = RulesTracker.getRuleMap().get(con);
				ruleName = rule.getName();
				break;
			}
			else if(con > 10000 && con < 20000){
				int cutCon = con / 100;
				IRule rule = RulesTracker.getRuleMap().get(cutCon);
				ruleName = rule.getName();
				break;
			}
		}
		
		m_fPane.setUpFilters(inConns);
		
		m_avgButt.setSelection(true);
		
		if(ruleName != null)
		{
			m_algoCombo.setText(ruleName);
			setAlgo(ruleName);
			
			m_fPane.setUpFilters(inConns);	
			setAlgo(ruleName);
		}
	}
	
	
	public void saveCharts()
	{
		try{
			String ticker = m_algoCombo.getText();
			ticker = ticker.replaceAll(" ", "_");
			ticker = ticker.toLowerCase();
//			m_timeChartWidget.doSaveAs("C:\\peligroso\\charts\\"+ticker+"_timechart.png");
//			m_posNegChartWidget.doSaveAs("C:\\peligroso\\charts\\"+ticker+"_posnegchart.png");
//			m_normDistChartWidget.doSaveAs("C:\\peligroso\\charts\\"+ticker+"_normdistchart.png");
//			m_yearChartWidget.doSaveAs("C:\\peligroso\\charts\\"+ticker+"_yearchart.png");
//			m_monthChartWidget.doSaveAs("C:\\peligroso\\charts\\"+ticker+"_monthchart.png");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
