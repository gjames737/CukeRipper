package com.cukeripper.plugin.views;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;

public class TestView extends ViewPart {
	private Text txtRootFile;
	private TreeViewer treeViewer;
	private TreeViewer treeViewer_SupportScreens;

	public TestView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
		GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_composite.widthHint = 874;
		composite.setLayoutData(gd_composite);

		txtRootFile = new Text(composite, SWT.BORDER);
		txtRootFile.setLayoutData(new RowData(492, SWT.DEFAULT));
		// txtRootFile.setText(ROOT_FILE);

		Button btnRefresh = new Button(composite, SWT.NONE);
		btnRefresh.setLayoutData(new RowData(268, SWT.DEFAULT));
		btnRefresh.setText("Refresh");
		new Label(parent, SWT.NONE);
		// btnRefresh.addListener(SWT.Selection,
		// new org.eclipse.swt.widgets.Listener() {
		// @Override
		// public void handleEvent(Event event) {
		// // showMessage("!");
		// presenter.handleRefreshEvent();
		// }
		// });

		Composite composite_1 = new Composite(parent, SWT.NONE);
		composite_1.setLayout(null);
		GridData gd_composite_1 = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_composite_1.widthHint = 873;
		gd_composite_1.heightHint = 378;
		composite_1.setLayoutData(gd_composite_1);

		treeViewer = new TreeViewer(composite_1, SWT.BORDER);
		// FeatureTreeContentProvider provider = presenter
		// .getFeatureTreeContentProvider();
		// treeViewer.setContentProvider(provider);
		treeViewer.setInput(getViewSite());
		Tree tree = treeViewer.getTree();
		tree.setBounds(0, 0, 332, 378);

		treeViewer_SupportScreens = new TreeViewer(composite_1, SWT.BORDER);
		Tree tree_supportScreens = treeViewer_SupportScreens.getTree();
		tree_supportScreens.setBounds(338, 0, 525, 378);
		GridData gd_tree_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_tree_1.widthHint = 494;
		new Label(parent, SWT.NONE);

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
}
