package com.cukeripper.plugin.views;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class CukeOutlineView extends ViewPart {

	private TreeViewer treeViewer;
	private FeatureTreeContentProvider provider;
	private CukeOutlinePresenter presenter;

	public CukeOutlineView() {
		presenter = new CukeOutlinePresenter();
	}

	@Override
	public void createPartControl(Composite parent) {
		provider = new FeatureTreeContentProvider(presenter.getfeatureFiles(),
				presenter.getFeatureParser());
		treeViewer = new TreeViewer(parent);
		treeViewer.setContentProvider(provider);
		treeViewer.setInput(getViewSite());

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
