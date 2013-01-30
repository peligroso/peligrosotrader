package jfreechart;
/**
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import org.aphelion.bundle.bo.common.fx.AttribMissingException;
import org.aphelion.bundle.bo.common.fx.FxPriceSpot;
import org.aphelion.bundle.bo.common.fx.Price;
import org.aphelion.bundle.bo.common.fx.StaticPrice;
import org.aphelion.bundle.bo.common.fx.Price.FXPriceType;
import org.aphelion.bundle.bo.fx.price.AssociationAttributes;
import org.aphelion.bundle.bo.fx.price.AssociationList;
import org.aphelion.bundle.bo.fx.price.AssociationRange;
import org.aphelion.bundle.bo.fx.price.AssociationRangeItem;
import org.aphelion.bundle.bo.fx.price.AssociationSet;
import org.aphelion.bundle.bo.fx.price.FXMarginPrice;
import org.aphelion.bundle.platform.client.httpclient.views.Activator;
import 
org.aphelion.bundle.platform.client.httpclient.views.pe.ScriptControl.AmountRange;
import 
org.aphelion.bundle.platform.client.httpclient.views.pe.valuewidgets.ControlContainer;
import org.aphelion.bundle.platform.client.ui.JXViewPart;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EcmaError;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.Scriptable;

import com.my.jseditor.widget.JSSourceViewerWidget;

final public class JavascriptEditView extends JXViewPart {

	private class VariablesContentProvider implements ITreeContentProvider {
		protected TreeViewer viewer;

		public Object[] getChildren(Object element) {
			if (element instanceof ScriptControl)
				return ((ScriptControl)element).getChildren();
			else if (element instanceof Tuple)
				return ((Tuple)element).getChildren();
			else
				return m_scriptControl.getChildren(element);
		}

		public Object getParent(Object element) {
			if (element instanceof ScriptControl)
				return null;
			if(element instanceof Tuple)
				return ((Tuple)element).getParent();
			else
				return m_scriptControl;
		}

		public boolean hasChildren(Object element) {
			if (element instanceof ScriptControl)
				return ((ScriptControl)element).hasChildren();
			else if (element instanceof Tuple)
				return ((Tuple)element).hasChildren();
			else
				return m_scriptControl.hasChildren(element);
		}

		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) 
{
			this.viewer = (TreeViewer)viewer;
		}
	}

	private class VariablesTreeLabelProvider extends LabelProvider {
		public Image getImage(Object element) {
//			ImageDescriptor descriptor = null;
//			if (element instanceof Model)
//				descriptor = ((Model)element).getImageDescriptor();
//
//			if (descriptor == null)
//				return null;
//
//			Image image = (Image)images.get(descriptor);
//			if (image == null) {
//				image = descriptor.createImage();
//				images.put(descriptor, image);
//			}
//			return image;
			return null;
		}

		public String getText(Object element) {
			if (element == null)
				return "";

			if (element instanceof String)
				return (String)element;
			if (element instanceof AssociationRange)
				return "AssociationRange";
			if (element instanceof AssociationRangeItem)
//				return new 
Long(((AssociationRangeItem)element).getEndRange()).toString();
				return "Amount: " + ((AssociationRangeItem)element).getEndRange();
			if (element instanceof AmountRange) {
				AmountRange range = (AmountRange) element;
				NumberFormat format = NumberFormat.getInstance();
				return new String(format.format(range.low) + " .. " + 
format.format(range.high));
			}
			if (element instanceof AssociationList) {
				if (((AssociationList)element).getValue() != null)
					return ((AssociationList)element).getValue();
				if (((AssociationList)element).getGroupName() != null)
					return ((AssociationList)element).getGroupName();
			}
			if (element instanceof Tuple)
				return ((Tuple)element).name;
			return element.toString();

		}
	}

	public static int EVENT_REFRESH_SCRIPT = 1;
	public static int EVENT_REFRESH_VARIABLES = 2;
	public static int EVENT_REFRESH_VALUEWIDGETS = 4;
	public static int EVENT_REFRESH_SCRIPTS_LIST = 8;

	private static float[] m_points = new float[]{105, 106, 107, 107, 105, 104, 
106, 106, 107, 109, 105, 108,
		  110, 112, 109, 114, 114, 117, 113, 110, 108, 108, 112, 115, 118, 118, 
118, 117, 118,
		  116, 116, 117, 118, 119, 120, 119, 122, 119, 119, 119, 125, 127, 129, 
133, 135, 130, 130, 135,
		  128, 136, 130, 130, 134, 136, 140, 144, 142, 145, 146, 145, 148, 150, 
152, 148, 145, 145, 142,
		  143, 140, 150, 143, 138, 134, 129, 130, 129, 127, 123, 122, 118, 117, 
124, 125, 125,
		  125, 126, 127, 127, 124, 125, 128, 123, 124, 124, 123, 124, 123, 124, 
123, 124, 124, 124, 123,
		  124, 125, 127, 129, 125, 124, 127};

	private JSSourceViewerWidget m_editorView;
	private Action m_evalAction;
	private TextViewer m_evalView;
	private Map<ImageDescriptor,Image> m_images = new 
HashMap<ImageDescriptor,Image>();
	private ScriptControl m_scriptControl;
	private FormToolkit m_toolkit;
	private TreeViewer m_variableViewer;
	private ControlContainer m_controlContainer;
	private VariablesContentProvider m_variablesContentProvider;
	private VariablesTreeLabelProvider m_variablesTreeLabelProvider;
	private ScrolledForm m_editorForm;
	private ScrolledForm m_controlForm;
	private FXMarginPrice m_testPrice;
	private Text m_askInput;
	private Text m_askCalc;
	private Text m_bidInput;
	private Text m_bidCalc;
	private Text m_skew;
	private Button m_autoSpread;
	private Object[] m_variables;
	private Table m_variablesTable;
	private List m_scriptsList;
	private Vector<Object[]> m_scriptsTuples;
	private List m_currenciesList;
	private JFreeChart m_resultsChart;
	private ChartComposite m_scriptChart;
	private ChartControl m_chartControl;
	// Target currencies
//	private Combo m_ccy1;
//	private Combo m_ccy2;
//	private Combo m_type;
	private Button m_addPriceButton;

/*
	public void refreshView() {
		m_variables = m_scriptControl.getAssociationSet().getKeyOrder().toArray();
		boolean same = false;
		for (int i = 0; i < m_variables.length; i++) {
			TableItem[] items = m_variablesTable.getItems();
			for (int j = 0; j < items.length; j++)
				if (items[j].getText(0).equals(m_variables[i].toString()))
					same = true;
			if (same)
				continue;
			TableItem item = new TableItem(m_variablesTable, SWT.NONE);
			item.setText(new String[]{m_variables[i].toString(), ""});
		}

		String script = m_scriptControl.getScript();
		m_editorView.getDocument().set(script);
		m_variableViewer.refresh();

		m_controlForm.reflow(true);
		m_editorForm.reflow(true);
	}
*//**

	public JavascriptEditView() {
		m_chartControl = new ChartControl();
		m_scriptControl = new ScriptControl(getMicroOrb());
		m_evalAction = new Action() {
			public void run() {
				IDocument doc = m_evalView.getDocument();
				Context cx = Context.enter();
				Scriptable scope = cx.initStandardObjects(null);

				try {
					Object result = cx.evaluateString(scope, 
m_editorView.getDocument().get(), "<EditorView>", 0, null);
					doc.set(doc.get() + Context.toString(result) + "\n");
				} catch (EcmaError e) {
					doc.set(doc.get() + e.toString() + "\n");
				} catch (EvaluatorException e) {
					doc.set(doc.get() + e.toString() + "\n");
				} catch (JavaScriptException e) {
					doc.set(doc.get() + e.toString() + "\n");
				} finally {
					Context.exit();
				}
			}
		};
		ImageDescriptor descriptor = 
Activator.getImageDescriptor("icons/eval.gif");
		if (descriptor != null) {
			Image image = (Image)m_images.get(descriptor);
			if (image == null) {
				image = descriptor.createImage();
				m_images.put(descriptor, image);
			}
		}
		m_evalAction.setImageDescriptor(descriptor);
		m_evalAction.setToolTipText("Evaluate script");

		m_variablesContentProvider = new VariablesContentProvider();
		m_variablesTreeLabelProvider = new VariablesTreeLabelProvider();

		m_scriptControl.setEditView(this);
	}

	@Override
	public void createPartControl(Composite parent) {
		m_toolkit = new FormToolkit(parent.getDisplay());

		TabFolder tabFolder = new TabFolder(parent, SWT.TOP);

//		tabFolder.addMouseListener(new MouseListener() {
//			public void mouseDoubleClick(MouseEvent e) {}
//			public void mouseDown(MouseEvent e) {
//				refreshView();
//			}
//			public void mouseUp(MouseEvent e) {}
//		});
		TabItem controlTab = new TabItem(tabFolder, SWT.NONE);
		controlTab.setText("Script Control");
		controlTab.setControl(createScriptControlView(tabFolder));

		TabItem editorTab = new TabItem(tabFolder, SWT.NONE);
		editorTab.setText("Script Editor");
		editorTab.setControl(createEditorView(tabFolder));

		TabItem valueWidgetsTab = new TabItem(tabFolder, SWT.NONE);
		valueWidgetsTab.setText("Value Widgets");
		valueWidgetsTab.setControl(createValueWidgetsView(tabFolder));

		m_variableViewer.setInput(m_scriptControl);
	}

	@Override
	public void setFocus() {
	}

	public void dispose() {
		Iterator it = m_images.values().iterator();
		while (it.hasNext())
			((Image)it.next()).dispose();

		m_toolkit.dispose();
		m_scriptControl.dispose();

		super.dispose();
	}

	public void handleEvent(Event event) {
		handleEvent(event, null);
	}

	public void handleEvent(Event event, Object input) {
		if ((EVENT_REFRESH_SCRIPT & event.type) == EVENT_REFRESH_SCRIPT) {
			if (!(input instanceof String))
				return;

			m_testPrice = new FXMarginPrice(Price.FXPriceType.FXPriceSpot, "");
			try {
				m_testPrice.initialize(getViewObject(), getMicroOrb(), null, null, 
"EURSEK");
			} catch (AttribMissingException e) {}

			m_editorView.getDocument().set((String)input);
			m_editorForm.reflow(true);
		}

		if ((EVENT_REFRESH_VALUEWIDGETS & event.type) == 
EVENT_REFRESH_VALUEWIDGETS) {
			m_variableViewer.refresh();
		}

		if ((EVENT_REFRESH_VARIABLES & event.type) == EVENT_REFRESH_VARIABLES) {
			if (!(input instanceof java.util.List))
				return;

/*
			boolean same = false;
			for (int i = 0; i < m_variables.length; i++) {
				TableItem[] items = m_variablesTable.getItems();
				for (int j = 0; j < items.length; j++)
					if (items[j].getText(0).equals(m_variables[i].toString()))
						same = true;
				if (same)
					continue;
				TableItem item = new TableItem(m_variablesTable, SWT.NONE);
				item.setText(new String[]{m_variables[i].toString(), ""});
			}
*//**
			m_variablesTable.removeAll();
			for (Iterator<String> i = ((java.util.List<String>)input).iterator(); 
i.hasNext();) {
				String e = i.next();
				TableItem item = new TableItem(m_variablesTable, SWT.NONE);
				item.setText(new String[]{e, ""});
			}

			m_controlForm.reflow(true);
		}

		if ((EVENT_REFRESH_SCRIPTS_LIST & event.type) == 
EVENT_REFRESH_SCRIPTS_LIST) {
			if (!(input instanceof Vector))
				return;

			m_scriptsTuples = (Vector<Object[]>)input;
			m_scriptsList.removeAll();

			for (int i = 0; i < m_scriptsTuples.size(); i++) {
				Object[] e = m_scriptsTuples.get(i);
				String script = (String)e[0];
				boolean found = false;
				for (int j = 0; j < m_scriptsList.getItemCount(); j++) {
					if (m_scriptsList.getItem(j).equals(script)) {
						found = true;
						break;
					}
				}
				if (!found)
					m_scriptsList.add(script);
			}
		}
	}

	private Control createEditorView(TabFolder folder) {
		m_editorForm = m_toolkit.createScrolledForm(folder);
		m_editorForm.setText("Javascript Editor");

		TableWrapLayout layout = new TableWrapLayout();
//		ColumnLayout layout = new ColumnLayout();
//		GridLayout layout = new GridLayout(2, false);
		layout.numColumns = 2;

		m_editorForm.getBody().setLayout(layout);

		// Source Editor
		Section section = m_toolkit.createSection(m_editorForm.getBody(), 
Section.TITLE_BAR | Section.DESCRIPTION | Section.EXPANDED | 
Section.TWISTIE);
		TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB, 
TableWrapData.FILL_GRAB, 1, 1);

		section.setLayoutData(td);
//		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
//		section.setLayoutData(gd);
		section.setText("Source editor");
		section.setDescription("Section description");
		Composite sectionClient = m_toolkit.createComposite(section);
		sectionClient.setLayout(new GridLayout());

/*
		Button button = new Button(sectionClient, SWT.PUSH);
		button.setText("Save script");
		button.addMouseListener(new MouseListener() {
			public void mouseDoubleClick(MouseEvent e) {}
			public void mouseDown(MouseEvent e) {}
			public void mouseUp(MouseEvent e) {
				m_scriptControl.setScript(m_editorView.getDocument().get());
			}
		});
*//**

		m_editorView = new JSSourceViewerWidget(sectionClient, SWT.V_SCROLL | 
SWT.H_SCROLL | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION, false);
		m_editorView.createDocument("");
		m_editorView.setEnabled(true);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 300;
		m_editorView.getControl().setLayoutData(gd);

		section.setClient(sectionClient);

		// Script Variables
		section = m_toolkit.createSection(m_editorForm.getBody(), 
Section.TITLE_BAR | Section.DESCRIPTION | Section.EXPANDED | 
Section.TWISTIE);
		td = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB);
		section.setLayoutData(td);
//		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
//		section.setLayoutData(gd);
		section.setText("Script variables");
		section.setDescription("Section description");
		sectionClient = m_toolkit.createComposite(section);
		sectionClient.setLayout(new FillLayout());

		m_variableViewer = new TreeViewer(sectionClient);
		m_variableViewer.setContentProvider(m_variablesContentProvider);
		m_variableViewer.setLabelProvider(m_variablesTreeLabelProvider);
		m_variableViewer.setSorter(null);

		section.setClient(sectionClient);

		return m_editorForm;
	}

	private Control createValueWidgetsView(TabFolder folder) {
//		Composite parent = new Composite(tabFolder, SWT.NONE);

		m_controlContainer = new ControlContainer(folder, SWT.NONE, 
m_scriptControl);
		return m_controlContainer;
	}

	private Control createScriptControlView(TabFolder folder) {
		m_controlForm = m_toolkit.createScrolledForm(folder);
		m_controlForm.setText("Script Control");

		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;

		m_controlForm.getBody().setLayout(layout);

		// Script Control
		Section section = m_toolkit.createSection(m_controlForm.getBody(), 
Section.TITLE_BAR | Section.DESCRIPTION | Section.EXPANDED | 
Section.TWISTIE);
		TableWrapData td = new TableWrapData(TableWrapData.LEFT);
		section.setLayoutData(td);
//		GridData gd = new GridData(SWT.FILL, SWT.DEFAULT, true, false);
//		section.setLayoutData(gd);
		section.setText("Script control");
		section.setDescription("Select a script to associate currency triplets 
with it. Or fetch a script to modify it in the Script Editor tab.");
		Composite sectionClient = m_toolkit.createComposite(section);
		sectionClient.setLayout(new GridLayout(3, false));

		m_scriptsList = new List(sectionClient, SWT.BORDER | SWT.SINGLE | 
SWT.H_SCROLL);
		GridData gd = new GridData(SWT.FILL, SWT.DEFAULT, false, false);
		gd.heightHint = 100;
		m_scriptsList.setLayoutData(gd);
		m_scriptsList.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				if (m_scriptsTuples != null && m_currenciesList != null) {
					String selection = m_scriptsList.getSelection()[0];
					Vector<StaticPrice> prices = new Vector<StaticPrice>();
					for (int i = 0; i < m_scriptsTuples.size(); i++) {
						Object[] o = m_scriptsTuples.get(i);
						if (selection.equals(o[0]))
							prices.add((StaticPrice)o[1]);
					}

					m_currenciesList.removeAll();
					for (int i = 0; i < prices.size(); i++) {
						StaticPrice price = prices.get(i);
						String type = "Unknown";
						if (price.getType().equals(Price.FXPriceType.FXPriceSpot))
							type = "SPOT";
						m_currenciesList.add(new String(price.getCcy1() + " " + 
price.getCcy2() + " " + type));
					}
				}
			}
		});

		m_currenciesList = new List(sectionClient, SWT.BORDER | SWT.H_SCROLL | 
SWT.MULTI);
		m_currenciesList.setLayoutData(gd);

		// Toolbar
		ToolBar toolbar = new ToolBar(sectionClient, SWT.VERTICAL);
		gd = new GridData(SWT.DEFAULT, SWT.FILL, false, false);
		toolbar.setLayoutData(gd);
		toolbar.setLayout(new RowLayout(SWT.VERTICAL));



		Button button = m_toolkit.createButton(sectionClient, "Download selected 
script", SWT.PUSH);
		button.addMouseListener(new MouseListener() {
			public void mouseDoubleClick(MouseEvent e) {}
			public void mouseDown(MouseEvent e) {}
			public void mouseUp(MouseEvent e) {
				String script = m_scriptsList.getSelection()[0];
				m_scriptControl.fetchScript(script);
			}
		});

		// CCY selection area
		Composite temp = m_toolkit.createComposite(sectionClient);
		temp.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, false, false));
		temp.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Combo ccy1 = new Combo(temp, SWT.DEFAULT);
		ccy1.setItems(new String[]{"EUR"});
		ccy1.setText("EUR");

		final Combo ccy2 = new Combo(temp, SWT.DEFAULT);
		ccy2.setItems(new String[]{"*", "SEK"});
		ccy2.setText("SEK");

		final Combo type = new Combo(temp, SWT.DEFAULT);
		type.setItems(new String[] {"SPOT"});
		type.setText("SPOT");

		m_addPriceButton = m_toolkit.createButton(temp, "Add price", SWT.PUSH);
		m_addPriceButton.addMouseListener(new MouseListener() {
			public void mouseDoubleClick(MouseEvent e) {}
			public void mouseDown(MouseEvent e) {}
			public void mouseUp(MouseEvent e) {
				String selection = m_scriptsList.getSelection()[0];
				StaticPrice price = new 
StaticPrice(Price.getPriceTypeFromString(type.getText()), null);
				price.setCcy1(ccy1.getText());
				price.setCcy2(ccy2.getText());
				m_scriptsTuples.add(new Object[]{selection, price});
				m_currenciesList.add(ccy1.getText() + " " + ccy2.getText() + " " + 
type.getText());
			}
		});
/*
		// Upload download controls
		temp = m_toolkit.createComposite(sectionClient);
		temp.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		temp.setLayout(new RowLayout(SWT.HORIZONTAL));

		Button button = new Button(temp, SWT.PUSH);
		button.setText("Download script");
		button.addMouseListener(new MouseListener() {
			public void mouseDoubleClick(MouseEvent e) {}
			public void mouseDown(MouseEvent e) {}
			public void mouseUp(MouseEvent e) {
				m_priceModel.fetchScript();
//				m_controlContainer.removeAllWidgets();
			}
		});
*//**
		section.setClient(sectionClient);

		// Evaluation view
		section = m_toolkit.createSection(m_controlForm.getBody(), 
Section.TITLE_BAR | Section.DESCRIPTION | Section.EXPANDED | 
Section.TWISTIE);
		td = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB, 
1, 1);

		section.setLayoutData(td);
//		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
//		section.setLayoutData(gd);
		section.setText("Evaluation view");
//		section.setDescription("Section description");
		sectionClient = m_toolkit.createComposite(section);
		GridLayout gl = new GridLayout(2, false);
		sectionClient.setLayout(gl);

		//  _   _
		// |X|_|_|
		// |_____|
		//
		Composite composite = m_toolkit.createComposite(sectionClient);
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(SWT.DEFAULT, SWT.DEFAULT, false, 
false));

		Label label = m_toolkit.createLabel(composite, "");
		label = m_toolkit.createLabel(composite, "Input");
		label = m_toolkit.createLabel(composite, "Result");

		gd = new GridData(SWT.LEAD, SWT.DEFAULT, false, false);
		label = m_toolkit.createLabel(composite, "Ask");
		m_askInput = m_toolkit.createText(composite, "");
		m_askCalc = m_toolkit.createText(composite, "");
		m_askCalc.setEnabled(false);
		m_askCalc.setEditable(false);

		label = m_toolkit.createLabel(composite, "Bid");
		m_bidInput = m_toolkit.createText(composite, "");
		m_bidCalc = m_toolkit.createText(composite, "");
		m_bidCalc.setEnabled(false);
		m_bidCalc.setEditable(false);

		label = m_toolkit.createLabel(composite, "Skew");
		m_skew = m_toolkit.createText(composite, "");

		gd = new GridData(GridData.FILL_HORIZONTAL);
		m_autoSpread = m_toolkit.createButton(composite, "Auto-spread", 
SWT.CHECK);
		gd.horizontalSpan = 3;
		m_autoSpread.setLayoutData(gd);

		//  _   _
		// |_|_|X|
		// |_____|
		//
		// Variables Table
		m_variablesTable = m_toolkit.createTable(sectionClient, SWT.FULL_SELECTION 
| SWT.HIDE_SELECTION);
		m_variablesTable.setLayoutData(new GridData(SWT.DEFAULT, SWT.FILL, true, 
true));
		m_variablesTable.setHeaderVisible(true);
		m_variablesTable.setLinesVisible(true);

		final TableEditor editor = new TableEditor(m_variablesTable);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		editor.minimumWidth = 50;
		m_variablesTable.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Control oldEditor = editor.getEditor();
				if (oldEditor != null)
					oldEditor.dispose();

				TableItem item = (TableItem)e.item;
				if (item == null)
					return;

				Text newEditor = new Text(m_variablesTable, SWT.NONE);
				newEditor.setText(item.getText(1));
				newEditor.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						Text text = (Text)editor.getEditor();
						editor.getItem().setText(1, text.getText());
					}
				});

				newEditor.selectAll();
				newEditor.setFocus();
				editor.setEditor(newEditor, item, 1);
			}
		});

		TableColumn column = new TableColumn(m_variablesTable, SWT.LEFT);
		column.setWidth(120);
		column.setText("Variable");
		column = new TableColumn(m_variablesTable, SWT.LEFT);
		column.setWidth(120);
		column.setText("Value");

		//  _   _
		// |_|_|_|
		// |__X__|
		//
		// Chart
		JFreeChart chart = m_chartControl.getChart();
		XYPlot plot = (XYPlot)chart.getPlot();
		plot.setRenderer(new 
StandardXYItemRenderer(StandardXYItemRenderer.LINES));

		plot.setNoDataMessage("No data available");
		plot.setDomainAxis(new DateAxis());

		m_scriptChart = new ChartComposite(sectionClient, SWT.NONE, chart, false);
		gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd.horizontalSpan = 2;
		gd.heightHint = 200;
		m_scriptChart.setLayoutData(gd);
		m_chartControl.setChartWidget(m_scriptChart);
		m_chartControl.setJavascriptEditView(this);

		m_scriptChart.addControlListener(new ControlListener() {
			public void controlMoved(ControlEvent e) {}
			public void controlResized(ControlEvent e) {
				if (m_scriptChart != null)
					m_scriptChart.forceRedraw();
			}
		});

		button = new Button(sectionClient, SWT.PUSH);
		button.setText("Evaluate script");
		button.addMouseListener(new MouseListener() {
			public void mouseDoubleClick(MouseEvent e) {}
			public void mouseDown(MouseEvent e) {}
			public void mouseUp(MouseEvent e) {
				try {
					String script = new String();

					TableItem[] variables = m_variablesTable.getItems();
					Hashtable<String, String> hash = new Hashtable<String, String>();
					for (int i = 0; i < variables.length; i++) {
						String var = variables[i].getText(0);
						if (variables[i].getText(1).length() == 0)
							continue;
						String input = variables[i].getText(1);

						hash.put(var, input);
					}

					AssociationAttributes attributes = 
m_scriptControl.getAssociationSet().resolveAssociationAttributes(hash);

					m_testPrice.setAttributes(attributes);
					script = script.concat(m_editorView.getDocument().get());
					m_evalView.getDocument().set(script);
					m_testPrice.setScript(script);

					StaticPrice price = new StaticPrice(Price.FXPriceType.FXPriceSpot, 
null);
					price.setBid(new Double(m_bidInput.getText()));
					price.setAsk(new Double(m_askInput.getText()));

					m_testPrice.recalc(price);

					m_askCalc.setText(m_testPrice.getCalcAskStr());
					m_bidCalc.setText(m_testPrice.getCalcBidStr());

					m_chartControl.setRedraw(false);
					m_chartControl.clearChart();

					// XXX
					Long time = System.currentTimeMillis() - m_points.length;
					Random rand = new Random(666);
					for (int i = 0; i < m_points.length; i++) {
						Double ask = new Double(m_points[i]);
						Double bid = ask+(rand.nextDouble()*10.0)+2;
						price.setBid(bid);
						price.setAsk(ask);
						m_chartControl.addValue("Ask", ask, time);
						m_chartControl.addValue("Bid", bid, time);

						m_testPrice.recalc(price);

						m_chartControl.addValue("Calc Ask", m_testPrice.getCalcAsk(), time);
						m_chartControl.addValue("Calc Bid", m_testPrice.getCalcBid(), time);
						time++;
					}

					m_chartControl.setRedraw(true);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		m_evalView = new TextViewer(sectionClient, SWT.BORDER | SWT.V_SCROLL | 
SWT.H_SCROLL);
		m_evalView.setDocument(new Document());
		m_evalView.setEditable(false);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 3;
		gd.heightHint = 200;
		m_evalView.getTextWidget().setLayoutData(gd);
		section.setClient(sectionClient);

		return m_controlForm;
	}

}*/


