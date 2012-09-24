package com.cukeripper.plugin.views;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class CukeOutlineView extends ViewPart {

	private TreeViewer treeViewer;
	private FeatureTreeContentProvider provider;

	@Override
	public void createPartControl(Composite parent) {
		// provider = new FeatureTreeContentProvider(new File[] {});
		//
		// treeViewer = new TreeViewer(parent);
		// treeViewer.setContentProvider(provider);

		// treeViewer.setInput(input)
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
