package org.juxtapose.bundle.peligrosotrader.gui2;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.juxtapose.bundle.peligrosotrader.gui2.views.AnalyzeView;
import org.juxtapose.bundle.peligrosotrader.gui2.views.MarketView;
import org.juxtapose.bundle.peligrosotrader.gui2.views.SamplerView;
import org.juxtapose.bundle.peligrosotrader.gui2.views.SwingView;
import org.juxtapose.bundle.peligrosotrader.gui2.views.adapt.AdaptView;
import org.juxtapose.bundle.peligrosotrader.gui2.views.scan.ScanView;


public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
//		Top left: Resource Navigator view and Bookmarks view placeholder
		 IFolderLayout main = layout.createFolder("main", IPageLayout.TOP, 1.0f, layout.getEditorArea());
		 main.addView(AnalyzeView.ID);
		 main.addView(SamplerView.ID);
		 main.addView(SwingView.ID);		 
		 main.addView(MarketView.ID);
		 main.addView(ScanView.ID);
		 main.addView(AdaptView.ID);
		 
		 layout.setEditorAreaVisible(false);
		 //main.addPlaceholder(IPageLayout.ID_EDITOR_AREA);
	}
}
