package com.cukeripper.plugin.views;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;

public class TestView extends ViewPart {
	private DataBindingContext m_bindingContext;
	private Text txtRootFile;
	private TreeViewer treeViewer;
	private TreeViewer treeViewer_SupportScreens;
	private Tree tree;
	private Composite composite_1;

	public TestView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FormLayout());

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FormLayout());
		FormData fd_composite = new FormData();
		fd_composite.right = new FormAttachment(100, -7);
		fd_composite.left = new FormAttachment(0, 4);
		fd_composite.top = new FormAttachment(0, 10);
		composite.setLayoutData(fd_composite);

		txtRootFile = new Text(composite, SWT.BORDER);
		FormData fd_txtRootFile = new FormData();
		fd_txtRootFile.left = new FormAttachment(0, 10);
		fd_txtRootFile.top = new FormAttachment(0, 3);
		txtRootFile.setLayoutData(fd_txtRootFile);
		// txtRootFile.setText(ROOT_FILE);

		Button btnRefresh = new Button(composite, SWT.NONE);
		fd_txtRootFile.right = new FormAttachment(btnRefresh, -6);
		FormData fd_btnRefresh = new FormData();
		fd_btnRefresh.top = new FormAttachment(0, 1);
		fd_btnRefresh.right = new FormAttachment(100, -10);
		fd_btnRefresh.left = new FormAttachment(0, 513);
		btnRefresh.setLayoutData(fd_btnRefresh);
		btnRefresh.setText("Refresh");
		// btnRefresh.addListener(SWT.Selection,
		// new org.eclipse.swt.widgets.Listener() {
		// @Override
		// public void handleEvent(Event event) {
		// // showMessage("!");
		// presenter.handleRefreshEvent();
		// }
		// });

		composite_1 = new Composite(parent, SWT.NONE);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.top = new FormAttachment(composite, 6);
		fd_composite_1.bottom = new FormAttachment(100, -10);
		fd_composite_1.left = new FormAttachment(0, 5);
		composite_1.setLayoutData(fd_composite_1);
		composite_1.setLayout(new FormLayout());

		treeViewer = new TreeViewer(composite_1, SWT.BORDER);
		// FeatureTreeContentProvider provider = presenter
		// .getFeatureTreeContentProvider();
		// treeViewer.setContentProvider(provider);
		treeViewer.setInput(getViewSite());
		tree = treeViewer.getTree();
		FormData fd_tree = new FormData();
		fd_tree.top = new FormAttachment(0);
		fd_tree.bottom = new FormAttachment(100);
		fd_tree.right = new FormAttachment(0, 436);
		fd_tree.left = new FormAttachment(0);
		tree.setLayoutData(fd_tree);

		treeViewer_SupportScreens = new TreeViewer(composite_1, SWT.BORDER);
		Tree tree_supportScreens = treeViewer_SupportScreens.getTree();
		FormData fd_tree_supportScreens = new FormData();
		fd_tree_supportScreens.bottom = new FormAttachment(tree, 0, SWT.BOTTOM);
		fd_tree_supportScreens.right = new FormAttachment(0, 873);
		fd_tree_supportScreens.top = new FormAttachment(0);
		fd_tree_supportScreens.left = new FormAttachment(0, 436);
		tree_supportScreens.setLayoutData(fd_tree_supportScreens);
		GridData gd_tree_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_tree_1.widthHint = 494;
		m_bindingContext = initDataBindings();

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		return bindingContext;
	}
}
