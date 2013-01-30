package org.juxtapose.bundle.peligrosotrader.gui2.charts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.jfree.chart.axis.CategoryAxis3D;
import org.jfree.chart.plot.PlotOrientation;

public class TestChart extends Composite{
	
	private static float[] m_points = new float[]{105, 106, 107, 107, 105, 104, 
		106, 106, 107, 109, 105, 108, 110, 112, 109, 114, 114, 117, 113, 110, 
		108, 108, 112, 115, 118, 118, 118, 117, 118, 116, 116, 117, 118, 119, 
		120, 119, 122, 119, 119, 119, 125, 127, 129, 133, 135, 130, 130, 135, 
		128, 136, 130, 130, 134, 136, 140, 144, 142, 145, 146, 145, 148, 150, 
		152, 148, 145, 145, 142, 143, 140, 150, 143, 138, 134, 129, 130, 129, 
		127, 123, 122, 118, 117, 124, 125, 125, 125, 126, 127, 127, 124, 125, 
		128, 123, 124, 124, 123, 124, 123, 124, 123, 124, 124, 124, 123, 124, 
		125, 127, 129, 125, 124, 127};
	private JFreeChart m_chart;
	private ChartComposite m_chartComposite;
	
	public TestChart(Composite parent, int style){
		super(parent, style);
		
		setLayout(new RowLayout());
		
		m_chart = createChart();
		m_chartComposite = new ChartComposite(this, SWT.BORDER, m_chart, false);
	}
	
	private JFreeChart createChart(){
		CategoryDataset ds = new DefaultCategoryDataset();
		//ds.add(new Number())
		JFreeChart ret = ChartFactory.createLineChart("Test chart", "procent", "minut", new DefaultStatisticalCategoryDataset(), PlotOrientation.HORIZONTAL, false, false, false);
		return ret;
	}
	public Point computeSize(int vHint, int hHint){
		return new Point(200, 200);
	}
}
