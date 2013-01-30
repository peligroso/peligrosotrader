package org.juxtapose.bundle.peligrosotrader.gui2.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;



public class Graf extends Canvas {

	public Graf(Composite parent, int style){
		super(parent, style);
	
		addPaintListener(new PaintListener() {

        public void paintControl(PaintEvent e) {
        	Graf.this.paintControl(e);
        	}
		});


	}
	
	void paintControl(PaintEvent e) {

	     GC gc = e.gc;
	     gc.setBackground( getDisplay().getSystemColor(SWT.COLOR_CYAN));
	     gc.fillRectangle(0, 0, Graf.this.getBounds().width-5, Graf.this.getBounds().height-5);

	  }


}
