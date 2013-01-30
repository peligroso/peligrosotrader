package org.juxtapose.bundle.peligrosotrader.gui2.views;



import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.juxtapose.bundle.peligrosotrader.gui2.util.Timer;

import peligrosotrader.util.feeds.YahooFeed;

public class SamplerView extends ViewPart {

	public static final String ID = "org.juxtapose.bundle.peligrosotrader.gui2.views.samplerview";
	
	public static String STOCKHOLM_START = "9:15";
	public static String STOCKHOLM_STOP = "17:50";
	
	private Text m_tickerText, m_startText, m_stopText;
	private Button m_startButt, m_stopButt;
	private Composite m_parent;
	private Timer m_timer;
	
	private String tickers[];
	
	public Label m_startedLabel, m_stopedLabel, m_stateLabel, m_tickersLabel;
	
	
	@Override
	public void createPartControl(Composite parent) {
		
		m_parent = parent;
		
		GridLayout grid = new GridLayout();
		parent.setLayout(grid);
		
		
		//Ticker controls
		Group tickerGroup = new Group(parent, SWT.NONE);
		tickerGroup.setText("Tickers");
		
		tickerGroup.setLayoutData(new GridData( GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL ));
		
		tickerGroup.setLayout(new RowLayout());
		new Label(tickerGroup, SWT.NONE).setText("Edit tickers:");
		m_tickerText = new Text(tickerGroup, SWT.BORDER);
		RowData rd = new RowData();
		rd.width = 400;
		m_tickerText.setLayoutData(rd);
		
		
		
		//Timer Control
		Group timerGroup = new Group(parent, SWT.NONE);
		timerGroup.setText("Timer");
		
		grid = new GridLayout();
		grid.numColumns = 2;
		timerGroup.setLayout(grid);
		
		new Label(timerGroup, SWT.NONE).setText("start:");
		new Label(timerGroup, SWT.NONE).setText("stop:");
		
		m_startText = new Text(timerGroup, SWT.BORDER);
		m_stopText = new Text(timerGroup, SWT.BORDER);
		
		Button stockholmButt = new Button(timerGroup, SWT.PUSH);
		stockholmButt.setText("Stockholm");
		stockholmButt.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent sev){
				m_startText.setText(STOCKHOLM_START);
				m_stopText.setText(STOCKHOLM_STOP);
			}
		});
		
//		Monitor
		Group monitorGroup = new Group(parent, SWT.NONE);
		monitorGroup.setText("State");
		monitorGroup.setLayoutData(new GridData( GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL ));
		
		FillLayout fl = new FillLayout(SWT.VERTICAL);
		
		monitorGroup.setLayout(fl);
		
		//rd = new RowData();
		//rd.width = 400;
		
		m_startedLabel = new Label(monitorGroup, SWT.NONE);
		m_startedLabel.setText("      ");
		//m_startedLabel.setData(rd);
		
		m_stopedLabel = new Label(monitorGroup, SWT.NONE);
		m_stopedLabel.setText("    ");
		
		m_stateLabel = new Label(monitorGroup, SWT.NONE);
		m_stateLabel.setText("    ");
		m_stateLabel.setForeground(parent.getDisplay().getSystemColor(SWT.COLOR_RED));
		
		m_tickersLabel = new Label(monitorGroup, SWT.NONE);
		m_tickersLabel.setText("    ");
		
		
		m_startButt = new Button(parent, SWT.PUSH);
		m_startButt.setText("Start Sampling");
		m_startButt.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent sev){
				startSampel();
			}
		});
		
		m_stopButt = new Button(parent, SWT.PUSH);
		m_stopButt.setText("Stop Sampling");
		m_stopButt.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent sev){
				m_timer.finish();
			}
		});
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	
	public void startSampel(){
		
		String tickersFull = m_tickerText.getText();
		tickers = tickersFull.split(", ");
		
		String startString = m_startText.getText();
		String stopString = m_stopText.getText();
		String[] start = startString.split(":");
		String[] stop = stopString.split(":");
		
		m_timer = new Timer(Integer.parseInt(start[0]), Integer.parseInt(start[1]), 
				Integer.parseInt(stop[0]), Integer.parseInt(stop[1]), tickers, this);
	}
	
	public synchronized void started(String inTime){
		
		
		m_startedLabel.setText("Started at "+inTime);
	
		m_stateLabel.setText("Running...");
	
		m_tickersLabel.setText(m_tickerText.getText());
		m_stopedLabel.setText("   ");
	}
	
	public void stoped(String inTime){
		
		m_stateLabel.setText("Stoped");
		m_stopedLabel.setText("Stoped at: "+inTime);
	}
	
	public void setState(String inText){
		
		m_stateLabel.setText(inText);
	}
}
