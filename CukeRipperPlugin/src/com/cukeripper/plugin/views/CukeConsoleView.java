package com.cukeripper.plugin.views;

import northwoods.cukeripper.utils.parsing.CukeConsole;
import northwoods.cukeripper.utils.parsing.ICukeConsoleListener;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.part.ViewPart;

public class CukeConsoleView extends ViewPart implements ICukeConsoleListener {
	private ListViewer listViewer;

	public CukeConsoleView() {
		CukeConsole.addListener(this);
	}

	// http://www.eclipse.org/swt/widgets/

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FormLayout());

		Composite composite = new Composite(parent, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(100);
		fd_composite.right = new FormAttachment(100);
		fd_composite.top = new FormAttachment(0);
		fd_composite.left = new FormAttachment(0);
		composite.setLayoutData(fd_composite);
		composite.setLayout(new FormLayout());

		listViewer = new ListViewer(composite, SWT.BORDER | SWT.V_SCROLL);
		List listConsole = listViewer.getList();
		FormData fd_listConsole = new FormData();
		fd_listConsole.bottom = new FormAttachment(100);
		fd_listConsole.right = new FormAttachment(100);
		fd_listConsole.top = new FormAttachment(0);
		fd_listConsole.left = new FormAttachment(0);
		listConsole.setLayoutData(fd_listConsole);

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPrintLn(final String text, boolean error) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				listViewer.add(text);
			}
		});
	}
}
