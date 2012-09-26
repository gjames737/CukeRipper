package com.cukeripper.plugin.views;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;

public class TestView extends ViewPart {
	private Text txtRootFile;
	private TreeViewer treeViewer;

	public TestView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
		GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_composite.widthHint = 414;
		composite.setLayoutData(gd_composite);

		txtRootFile = new Text(composite, SWT.BORDER);
		txtRootFile.setLayoutData(new RowData(197, SWT.DEFAULT));
		// txtRootFile.setText(ROOT_FILE);

		Button btnRefresh = new Button(composite, SWT.NONE);
		btnRefresh.setText("Refresh");
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
		gd_composite_1.widthHint = 414;
		gd_composite_1.heightHint = 424;
		composite_1.setLayoutData(gd_composite_1);

		treeViewer = new TreeViewer(composite_1, SWT.BORDER);
		// provider = new
		// FeatureTreeContentProvider(presenter.getfeatureFiles(),
		// presenter.getFeatureParser());
		// treeViewer.setContentProvider(provider);
		treeViewer.setInput(getViewSite());
		Tree tree = treeViewer.getTree();
		tree.setBounds(0, 0, 414, 424);

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
}
