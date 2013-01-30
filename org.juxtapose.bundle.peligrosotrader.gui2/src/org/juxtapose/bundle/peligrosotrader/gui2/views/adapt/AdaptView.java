package org.juxtapose.bundle.peligrosotrader.gui2.views.adapt;

import java.util.Calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import org.juxtapose.bundle.peligrosotrader.gui2.Activator;
import peligrosotrader.predict.MarketAdapter;
import peligrosotrader.util.TradeUtil;
import peligrosotrader.util.marketfeeds.FeedsTracker;
import peligrosotrader.util.marketfeeds.IMarketFeed;

public class AdaptView extends ViewPart{

	public static String ID = "org.juxtapose.bundle.peligrosotrader.gui2.views.adapt.adaptview";

	private Composite m_base;
	MarketAdapter m_market;
	Combo m_feedCombo;
	
	Text m_startText, m_endText;
	
	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		
		m_market = Activator.getDefault().getMarketPublisher();
		
		m_base = new Composite(parent, SWT.NONE);
		
		GridLayout gl = new GridLayout(1, false);
		m_base.setLayout(gl);
		
		createFeedCombo(m_base);
		createCalPane(m_base);
		
		Button m_getStatsButt = new Button(m_base, SWT.PUSH);
		m_getStatsButt.setText("Adapt");
		m_getStatsButt.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent sev){
				m_market = Activator.getDefault().getMarketPublisher();
				
				IMarketFeed feed = FeedsTracker.getFeed(m_feedCombo.getText());
				
				Calendar startCal = TradeUtil.parseDateString(m_startText.getText());
				Calendar endCal = TradeUtil.parseDateString(m_endText.getText());
				
				if(feed != null && startCal != null && endCal != null){
					
					feed.setDates(startCal, endCal);
					m_market.adaptToMarket(feed);
				}
			}
		});
		
	}
	
	private void createFeedCombo(Composite inComposite)
	{
		m_feedCombo = new Combo(inComposite, SWT.NONE);
		
		for(String feed : FeedsTracker.feeds){
			m_feedCombo.add(feed);
		}
	}
	
	
	private void createCalPane(Composite inComposite)
	{
		Composite calComp = new Composite(inComposite, SWT.NONE);
		
		calComp.setLayout(new RowLayout());
		
		m_startText = new Text(calComp, SWT.BORDER);
		m_startText.setText("2000-01-01");
		
		m_endText = new Text(calComp, SWT.BORDER);
		m_endText.setText("2006-12-31");
	}


	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
}
