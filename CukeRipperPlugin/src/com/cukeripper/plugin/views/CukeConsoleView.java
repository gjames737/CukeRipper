package com.cukeripper.plugin.views;

import java.util.ArrayList;

import northwoods.cukeripper.utils.parsing.CukeConsole;
import northwoods.cukeripper.utils.parsing.ICukeConsoleListener;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.part.ViewPart;

public class CukeConsoleView extends ViewPart implements ICukeConsoleListener {
	private ListViewer listViewer;
	private java.util.List<String> lineItems;
	private boolean disposed;

	public CukeConsoleView() {
		lineItems = new ArrayList<String>();
		CukeConsole.addListener(this);
		disposed = false;
	}

	// http://www.eclipse.org/swt/widgets/

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FormLayout());

		Composite composite_console = new Composite(parent, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(100);
		fd_composite.left = new FormAttachment(0);
		fd_composite.right = new FormAttachment(100);
		fd_composite.top = new FormAttachment(0, 32);
		composite_console.setLayoutData(fd_composite);
		composite_console.setLayout(new FormLayout());

		listViewer = new ListViewer(composite_console, SWT.BORDER
				| SWT.V_SCROLL);
		List listConsole = listViewer.getList();
		FormData fd_listConsole = new FormData();
		fd_listConsole.bottom = new FormAttachment(100);
		fd_listConsole.right = new FormAttachment(100);
		fd_listConsole.top = new FormAttachment(0);
		fd_listConsole.left = new FormAttachment(0);
		listConsole.setLayoutData(fd_listConsole);

		Composite composite_btns = new Composite(parent, SWT.NONE);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.right = new FormAttachment(0, 594);
		fd_composite_1.top = new FormAttachment(0);
		fd_composite_1.left = new FormAttachment(0);
		composite_btns.setLayoutData(fd_composite_1);

		Button Clear = new Button(composite_btns, SWT.NONE);
		Clear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleClearEvent();
			}

		});
		Clear.setBounds(0, 0, 75, 25);
		Clear.setText("Clear");

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		disposed = true;
		super.dispose();
	}

	@Override
	public void onPrintLn(final String text, boolean error) {

		lineItems.add(text);
		listViewer.add(text);

	}

	private void handleClearEvent() {
		for (String li : lineItems) {
			listViewer.remove(li);
		}
		lineItems.clear();
	}

	@Override
	public boolean isDisposed() {
		return disposed;
	}
}
