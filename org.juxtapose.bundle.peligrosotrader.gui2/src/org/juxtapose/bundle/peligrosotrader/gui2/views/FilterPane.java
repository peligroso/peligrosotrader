package org.juxtapose.bundle.peligrosotrader.gui2.views;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import peligrosotrader.predict.KeyIDTracker;
import peligrosotrader.predict.rules.IRule;
import peligrosotrader.predict.rules.RulesTracker;

public class FilterPane extends Composite{
	
	static int NONE = -1;
	private Hashtable<Button, Integer> buttonToVal = new Hashtable<Button, Integer>();
	private Hashtable<Integer, Button> valToButton = new Hashtable<Integer, Button>();
	
	private ArrayList<Button> m_paramButts = new ArrayList<Button>();
	private ArrayList<Label> m_paramLabels = new ArrayList<Label>();
	
	Group m_paramFilterGroup;

	FilterPane(Composite inComposite, int inStyle) {
		super(inComposite, inStyle);
		
		setLayout(new GridLayout());
		
		GridData paneData = new GridData();
		paneData.grabExcessHorizontalSpace = true;
		paneData.horizontalAlignment = GridData.FILL;
		
		Group filterGroup = new Group(this, SWT.None);
		RowLayout rl = new RowLayout();
		rl.justify = true;
		filterGroup.setLayout(rl);
		filterGroup.setLayoutData(paneData);
		filterGroup.setText("Filter");
		
		for(String descript : KeyIDTracker.KEYS.keySet()){
			Composite comp = new Composite(filterGroup, SWT.NONE);
			comp.setLayout(new RowLayout());
			new Label(comp, SWT.NONE).setText(descript+": ");
			TreeMap<Integer, String> valMap = KeyIDTracker.KEYS.get(descript);
			
			new Label(comp, SWT.NONE).setText("none");
			new Button(comp, SWT.RADIO);
			
			for(Integer val : valMap.keySet()){
				new Label(comp, SWT.NONE).setText(" "+valMap.get(val));
				
				Button butt = new Button(comp, SWT.RADIO);
				buttonToVal.put(butt, val);
				valToButton.put(val, butt);
			}
			new Label(comp, SWT.NONE).setText("     ");
		}
		
		Group algoFilterGroup = new Group(this, SWT.None);
		RowLayout rla = new RowLayout();
		rla.justify = true;
		algoFilterGroup.setLayout(rla);
		algoFilterGroup.setLayoutData(paneData);
		algoFilterGroup.setText("Algos");
		
		for(Integer algoID : RulesTracker.getRuleMap().keySet()){
			IRule r = RulesTracker.getRuleMap().get(algoID);
			Composite comp = new Composite(algoFilterGroup, SWT.NONE);
			comp.setLayout(new RowLayout());
			new Label(comp, SWT.NONE).setText(r.getName()+": ");
			
			Button  butt = new Button(comp, SWT.CHECK);
			buttonToVal.put(butt, algoID);
			valToButton.put(algoID, butt);
		}
		
		m_paramFilterGroup = new Group(this, SWT.None);
//		RowLayout prl = new RowLayout();
//		prl.justify = true;
		m_paramFilterGroup.setLayout(new RowLayout());
		m_paramFilterGroup.setLayoutData(paneData);
		m_paramFilterGroup.setText("Params");
		
	}
	
	public Set<Integer> getFilterSelections(){
		Set ret = new HashSet<Integer>();
		for(Button butt : buttonToVal.keySet())
			if(butt.getSelection())
				ret.add(buttonToVal.get(butt));
		return ret;
	}
	
	public void setParams(Map<Integer, String> inMap)
	{
		try{
			clearParams();

			if(inMap != null){
				for(Integer key : inMap.keySet())
				{
					String desc = inMap.get(key);

					Label l = new Label(m_paramFilterGroup, SWT.NONE);
					l.setText("  "+desc+":");
					m_paramLabels.add(l);

					Button b = new Button(m_paramFilterGroup, SWT.CHECK);			
					buttonToVal.put(b, key);
					valToButton.put(key, b);
					m_paramButts.add(b);

				}
			}
			m_paramFilterGroup.layout();
		}catch(Exception e){e.printStackTrace();}
	}
	
	private void clearParams()
	{
		for(Button b : m_paramButts){
			int con = buttonToVal.get(b);
			buttonToVal.remove(b);
			valToButton.remove(con);
			
			b.dispose();
		}
		m_paramButts.clear();
		
		for(Label l : m_paramLabels)
			l.dispose();
		m_paramLabels.clear();
	}
	
	public void setUpFilters(Set<Integer> inConns)
	{
		for(Button b : buttonToVal.keySet()){
			b.setSelection(false);
		}
		
		for(Integer con : inConns)
		{
			Button b = valToButton.get(con);
			
			if(b != null && !b.isDisposed())
				b.setSelection(true);
		}
	}

}
