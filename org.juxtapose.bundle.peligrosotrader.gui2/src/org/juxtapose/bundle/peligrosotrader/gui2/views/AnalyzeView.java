package org.juxtapose.bundle.peligrosotrader.gui2.views;

import java.sql.Date;
import java.sql.Time;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.ui.part.ViewPart;

import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.experimental.chart.swt.ChartComposite;

import org.juxtapose.bundle.peligrosotrader.gui2.charts.DataSetCreator;
import org.juxtapose.bundle.peligrosotrader.gui2.charts.LineChart;
import peligrosotrader.util.db.DBHandler;
import peligrosotrader.util.db.Sample;
import peligrosotrader.util.ta.trend.TrendFinder;
import peligrosotrader.util.TradeUtil;


public class AnalyzeView extends ViewPart {
	
	public static final String ID = "org.juxtapose.bundle.peligrosotrader.gui2.views.analyzeview";
	
	private Text m_tickerText;
	private Text m_dateText;
	private Button m_chartButt, m_procentCheck, m_trend, m_average, m_sameSpace;
	private ChartComposite m_scriptChart;
	private Composite m_parent;

	//Vector<double[]> m_datasetValues;
	
	boolean procent;
	
	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		GridLayout myLayout = new GridLayout();
		parent.setLayout(myLayout);
		
		m_parent = parent;
		
		Group controlGroup = new Group(parent, SWT.NONE);
		controlGroup.setLayoutData(new GridData( GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL ));
		controlGroup.setText("Controls");
		
		controlGroup.setLayout(new RowLayout());
		new Label(controlGroup, SWT.NONE).setText("Ticker:");
		m_tickerText = new Text(controlGroup, SWT.BORDER);
		
		RowData rd = new RowData();
		rd.width = 50;
		m_tickerText.setLayoutData(rd);
		
		
		new Label(controlGroup, SWT.NONE).setText("   Dates (yyyy-mm-dd):");
		m_dateText = new Text(controlGroup, SWT.BORDER);
		
		rd = new RowData();
		rd.width = 200;
		m_dateText.setLayoutData(rd);
		
		new Label(controlGroup, SWT.NONE).setText("   Procent:");
		m_procentCheck = new Button(controlGroup, SWT.CHECK);
		
		m_procentCheck.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent sev){
				procent = m_procentCheck.getSelection();
			}
		});
		
		m_chartButt = new Button(controlGroup, SWT.PUSH);
		m_chartButt.setText("Create chart");
		
		m_chartButt.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent sev){
				createChart();
			}
		});
				
		Group proformasGroup = new Group(parent, SWT.NONE);
		proformasGroup.setLayoutData(new GridData( GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL ));
		proformasGroup.setText("Proformas");
		
		proformasGroup.setLayout(new RowLayout());
		new Label(proformasGroup, SWT.NONE).setText("Average Line:");
		m_average = new Button(proformasGroup, SWT.CHECK);
		new Label(proformasGroup, SWT.NONE).setText("    Trend analyze:");
		m_trend = new Button(proformasGroup, SWT.CHECK);
		new Label(proformasGroup, SWT.NONE).setText("    Same space:");
		m_sameSpace = new Button(proformasGroup, SWT.CHECK);
		m_sameSpace.setSelection(true);
		
		
		try{
		
			Vector<double[]> dataValues = new Vector<double[]>(); 
			XYDataset dataset = LineChart.createDataset(dataValues, "---", 0, 9, 07, 02, 2007);
			JFreeChart chart = LineChart.createChart("chart", dataset, 0);
			
			m_scriptChart = new ChartComposite(parent, 
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
			GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false);
			gd.horizontalSpan = 2;
			gd.heightHint = 400;
			m_scriptChart.setLayoutData(gd);
		}catch (Exception e){
			e.printStackTrace();
		}
 
	}
	
	
	public void createChart(){
		
		String ticker = m_tickerText.getText();
		String[] dates= m_dateText.getText().split(", ");
		
		//m_datasetValues = new Vector<double[]>();
		Vector<Vector<Sample>> sampVec = new Vector<Vector<Sample>>();

		//int i = 0;
		Date constDate = null;
		try{
			for (String date : dates){
				Vector<Sample> samples = DBHandler.getSamples(ticker, date);
				if(samples.size() > 0)
					constDate = samples.get(0).date;
//				double[] dValues = Sample.extractValues(samples);
//				double[] percentValues = TradeUtil.convertToProcent(dValues);
				Vector<Sample> procentSamples = TradeUtil.convertToProcent(samples);
				
				if (procent)
					sampVec.add(procentSamples);
				else
					sampVec.add(samples);
				//i++;
			}
			
			if(m_average.getSelection())
				createAverage(sampVec);
			
			if(m_trend.getSelection() && !sampVec.isEmpty()){				
				TrendFinder tf = new TrendFinder(sampVec.get(0));
				tf.analyze();
				sampVec.add(tf.getTrendSamples());
			}
			

		
			
			if (!m_sameSpace.getSelection())
				constDate = null;
			XYDataset dataset = DataSetCreator.createDataset(sampVec, constDate);
			JFreeChart chart = LineChart.createChart("Chart", dataset, dates.length);
			
			m_scriptChart.setChart(chart);

			m_scriptChart.forceRedraw();
			m_scriptChart.update();
			m_parent.redraw();
			m_parent.update();
			m_parent.getDisplay().update();	
			
			
		}catch (Exception e) {e.printStackTrace();}
	}
	
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

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
