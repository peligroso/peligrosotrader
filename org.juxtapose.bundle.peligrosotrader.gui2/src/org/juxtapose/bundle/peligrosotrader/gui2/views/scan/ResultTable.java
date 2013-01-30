package org.juxtapose.bundle.peligrosotrader.gui2.views.scan;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import org.juxtapose.bundle.peligrosotrader.gui2.views.MarketView;
import org.juxtapose.bundle.peligrosotrader.gui2.views.SwingView;
import peligrosotrader.predict.scan.ScanResult;

public class ResultTable extends Composite {
//	class ResultItem{
//
//		public String m_cName;
//		public String m_ticker;
//		public Vector<Integer> m_conns = new Vector<Integer>();
//	}
	Table m_table;
	ArrayList<ScanResult> m_results = new ArrayList<ScanResult>();
	Text m_filterText;

	Vector<Control> m_editors = new Vector<Control>();
	
	ViewPart m_view;

	ResultTable(Composite inParent, ViewPart inView) 
	{
		super(inParent, SWT.NONE);
		setLayout(new GridLayout(1, false));
		
		m_view = inView;

		Composite filterComposite = new Composite(this, SWT.NONE);
		filterComposite.setLayout(new RowLayout());
		new Label(filterComposite, SWT.NONE).setText("Filter");
		m_filterText = new Text(filterComposite, SWT.BORDER);
		RowData textData = new RowData();
		textData.width = 200;
		m_filterText.setLayoutData(textData);
		Button filterButt = new Button(filterComposite, SWT.NONE);
		filterButt.setText("filter");
		filterButt.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent sev){
				System.out.println("filter "+m_filterText.getText());
				updateTable();
			}
		});


		m_table = new Table(this, SWT.BORDER | SWT.MULTI);
		m_table.setLinesVisible(true);
		m_table.setHeaderVisible(true);
		m_table.setLinesVisible(true);

		GridData tableData = new GridData();
		tableData.grabExcessHorizontalSpace = true;
		tableData.horizontalAlignment = GridData.FILL;
		tableData.grabExcessVerticalSpace = true;
		tableData.verticalAlignment = GridData.FILL;
		m_table.setLayoutData(tableData);
		for (int i = 0; i < 6; i++) {
			TableColumn column = new TableColumn(m_table, SWT.NONE);
			switch(i){
			case 0 : column.setWidth(100); column.setText("Company"); break;
			case 1 : column.setWidth(100); column.setText("Ticker"); break;
			case 2 : column.setWidth(100); column.setText("Connections"); break;
			case 3 : column.setWidth(50); column.setText("Stats"); break;
			case 4 : column.setWidth(50); column.setText("Stats"); break;
			case 5 : column.setWidth(50); column.setText("portfolio"); break;
			}
		}

		updateTable();	
	}
	
	public void setResults(ArrayList<ScanResult> inRes){
		m_results = inRes;
		updateTable();
	}

	/**
	 * 
	 *
	 */
	public void updateTable()
	{
		m_table.removeAll();
		for(Control c : m_editors){
			c.dispose();
		}

		String filterStr = m_filterText.getText();
		Vector<Integer> filterConns = parseConns(filterStr);
		for(ScanResult ri : m_results)
		{
			if(!ri.m_conns.containsAll(filterConns))
				continue;
			
			final ScanResult finRes = ri;

			final TableItem ti = new TableItem(m_table, SWT.NONE);
			
			if(ri.m_corp != null)
				ti.setText(0, ri.m_corp);

			ti.setText(1, ri.m_ticker);
			ti.setText(2, ConnsToString(ri.m_conns));

			TableEditor editor = new TableEditor(m_table);
			Button statsButton = new Button(m_table, SWT.PUSH);
			statsButton.setText("Stats");
			statsButton.pack();
			editor.minimumWidth = statsButton.getSize().x;
			editor.horizontalAlignment = SWT.LEFT;
			editor.setEditor(statsButton, ti, 3);
			statsButton.addSelectionListener(new SelectionAdapter()
			{
				public void widgetSelected(SelectionEvent sev){
					try
					{
						m_view.getSite().getPage().showView(MarketView.ID);
						MarketView mv = (MarketView)(m_view.getSite().getPage().findView(MarketView.ID));

						System.out.println("sending: "+finRes.toString());
						mv.setStatsFromSystem(finRes.m_conns);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
			m_editors.add(statsButton);

			editor = new TableEditor(m_table);
			Button chartButton = new Button(m_table, SWT.PUSH);
			chartButton.setText("Chart");
			chartButton.pack();
			editor.minimumWidth = chartButton.getSize().x;
			editor.horizontalAlignment = SWT.LEFT;
			editor.setEditor(chartButton, ti, 4);
			chartButton.addSelectionListener(new SelectionAdapter()
			{
				public void widgetSelected(SelectionEvent sev){
					try
					{
						m_view.getSite().getPage().showView(SwingView.ID);
						SwingView sv = (SwingView)(m_view.getSite().getPage().findView(SwingView.ID));

						long currentTime = System.currentTimeMillis();
						Date tD = new Date(currentTime);
						//long beforeTime = currentTime - 17280000000l;
						Date fD = new Date(currentTime);
						
						fD.setYear(105);
						fD.setMonth(0);
						fD.setDate(1);
						
						sv.setChartFromSystem(finRes.m_ticker, false, false, fD.toString(), tD.toString(), 0);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
			m_editors.add(chartButton);

			editor = new TableEditor(m_table);
			Button portButton = new Button(m_table, SWT.PUSH);
			portButton.setText("Add");
			portButton.pack();
			editor.minimumWidth = portButton.getSize().x;
			editor.horizontalAlignment = SWT.LEFT;
			editor.setEditor(portButton, ti, 5);
			
			

			portButton.addSelectionListener(new SelectionAdapter()
			{
				public void widgetSelected(SelectionEvent sev){

					System.out.println(ti.getText(1)+" "+ti.getText(2));
					
				}
			});

			m_editors.add(portButton);
		}
	}
	
	/**
	 * 
	 * @param inConns
	 * @return
	 */
	public String ConnsToString(Set<Integer> inConns)
	{

		StringBuffer sb = new StringBuffer();

		boolean first = true;

		for(Integer i : inConns){

			if(first)
				first = false;
			else
				sb.append(", ");
			sb.append(Integer.toString(i));
		}
		return sb.toString();
	}


	/**
	 * 
	 * @param inConns
	 * @return
	 */
	public Vector<Integer> parseConns(String inConns)
	{ 
		String[] split = inConns.split(", ");
		Vector<Integer> ret = new Vector<Integer>();
		if(inConns.equals(""))

			return ret;
		for(String s : split){
			ret.add(Integer.parseInt(s));
		}

		return ret;

	}

}

