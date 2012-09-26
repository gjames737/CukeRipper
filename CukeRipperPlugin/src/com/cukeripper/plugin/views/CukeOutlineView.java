package com.cukeripper.plugin.views;

import java.io.File;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;

public class CukeOutlineView extends ViewPart {
	// http://www.eclipse.org/swt/widgets/
	private static final String ROOT_FILE = "C:" + File.separator + "TFSBuild"
			+ File.separator + "CoPilot" + File.separator + "Trunk"
			+ File.separator + "CoPilotCukes";
	private TreeViewer treeViewer;
	private FeatureTreeContentProvider provider;
	private CukeOutlinePresenter presenter;
	private Text txtRootFile;

	public CukeOutlineView() {
		presenter = new CukeOutlinePresenter(this);

	}

	@Override
	public void createPartControl(Composite parent) {

		//
		// // Group group_file_path = new Group(parent, SWT.NONE);
		// // Group group_outline = new Group(parent, SWT.NONE);
		// //
		// // GroupLayout gl_parent = setupGroup_Parent(parent, group_file_path,
		// // group_outline);
		// //
		// // setupTreeViewerOutline(group_outline);
		// // setupRootFileInputField(group_file_path);
		// // setupRefreshButton(group_file_path);
		// //
		// // parent.setLayout(gl_parent);

		parent.setLayout(new GridLayout(1, false));

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
		GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_composite.widthHint = 414;
		composite.setLayoutData(gd_composite);

		txtRootFile = new Text(composite, SWT.BORDER);
		txtRootFile.setLayoutData(new RowData(197, SWT.DEFAULT));
		txtRootFile.setText(ROOT_FILE);

		Button btnRefresh = new Button(composite, SWT.NONE);
		btnRefresh.setText("Refresh");
		btnRefresh.addListener(SWT.Selection,
				new org.eclipse.swt.widgets.Listener() {
					@Override
					public void handleEvent(Event event) {
						// showMessage("!");
						presenter.handleRefreshEvent();
					}
				});

		Composite composite_1 = new Composite(parent, SWT.NONE);
		composite_1.setLayout(null);
		GridData gd_composite_1 = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_composite_1.widthHint = 414;
		gd_composite_1.heightHint = 424;
		composite_1.setLayoutData(gd_composite_1);

		treeViewer = new TreeViewer(composite_1, SWT.BORDER);
		provider = new FeatureTreeContentProvider(presenter.getfeatureFiles(),
				presenter.getFeatureParser());
		treeViewer.setContentProvider(provider);
		treeViewer.setInput(getViewSite());
		Tree tree = treeViewer.getTree();
		tree.setBounds(0, 0, 414, 424);
		presenter.makeActions();

	}

	// private void setupRefreshButton(Group group_file_path) {
	// Button btnRefresh = new Button(group_file_path, SWT.NONE);
	// btnRefresh.setBounds(207, 13, 75, 25);
	// btnRefresh.setText("Refresh");
	// btnRefresh.addListener(SWT.Selection,
	// new org.eclipse.swt.widgets.Listener() {
	// @Override
	// public void handleEvent(Event event) {
	// // showMessage("!");
	// presenter.handleRefreshEvent();
	// }
	// });
	// }

	// private void setupRootFileInputField(Group group_file_path) {
	// txtRootFile = new Text(group_file_path, SWT.BORDER);
	// txtRootFile.setBounds(10, 17, 191, 21);
	// txtRootFile.setText(ROOT_FILE);
	// }
	//
	// private void setupTreeViewerOutline(Group group_outline) {
	// treeViewer = new TreeViewer(group_outline, SWT.BORDER);
	// provider = new FeatureTreeContentProvider(presenter.getfeatureFiles(),
	// presenter.getFeatureParser());
	// treeViewer.setContentProvider(provider);
	// treeViewer.setInput(getViewSite());
	// Tree tree = treeViewer.getTree();
	// tree.setBounds(10, 10, 272, 367);
	// }
	//
	// private GroupLayout setupGroup_Parent(Composite parent,
	// Group group_file_path, Group group_outline) {
	// GroupLayout gl_parent = new GroupLayout(parent);
	// gl_parent.setHorizontalGroup(gl_parent.createParallelGroup(
	// GroupLayout.LEADING).add(
	// GroupLayout.TRAILING,
	// gl_parent
	// .createSequentialGroup()
	// .addContainerGap()
	// .add(gl_parent
	// .createParallelGroup(GroupLayout.TRAILING)
	// .add(GroupLayout.LEADING, group_outline,
	// GroupLayout.DEFAULT_SIZE, 292,
	// Short.MAX_VALUE)
	// .add(GroupLayout.LEADING, group_file_path,
	// GroupLayout.DEFAULT_SIZE, 292,
	// Short.MAX_VALUE)).addContainerGap()));
	// gl_parent.setVerticalGroup(gl_parent.createParallelGroup(
	// GroupLayout.LEADING).add(
	// gl_parent
	// .createSequentialGroup()
	// .addContainerGap()
	// .add(group_file_path, GroupLayout.PREFERRED_SIZE, 48,
	// GroupLayout.PREFERRED_SIZE)
	// .addPreferredGap(LayoutStyle.RELATED)
	// .add(group_outline, GroupLayout.DEFAULT_SIZE, 387,
	// Short.MAX_VALUE).addContainerGap()));
	// return gl_parent;
	// }

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public TreeViewer getFeatureTree() {
		return treeViewer;
	}

	public void showMessage(String message) {
		MessageDialog.openInformation(treeViewer.getControl().getShell(),
				"Sample View", message);
	}

	public void openEditorOnFile(File fileToOpen) {
		if (fileToOpen.exists() && fileToOpen.isFile()) {
			IFileStore fileStore = EFS.getLocalFileSystem().getStore(
					fileToOpen.toURI());
			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();

			try {
				IDE.openEditorOnFileStore(page, fileStore);
			} catch (PartInitException e) {
				// Put your exception handler here if you wish to
			}
		} else {
			// Do something if the file does not exist
		}
	}

	public String getCurrentFileRootPath() {
		if (txtRootFile != null)
			return txtRootFile.getText();
		else
			return "";
	}

	public void refresh() {
		if (treeViewer != null) {
			// System.err.println(Math.random() + "");
			provider = new FeatureTreeContentProvider(
					presenter.getfeatureFiles(), presenter.getFeatureParser());
			treeViewer.setContentProvider(provider);
		}
	}
}
