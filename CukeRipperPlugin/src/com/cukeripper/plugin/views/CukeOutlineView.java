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
	private TreeViewer treeViewer_SupportScreens;
	private CukeOutlinePresenter presenter;
	private Text txtRootFile;

	public CukeOutlineView() {
		presenter = new CukeOutlinePresenter(this);

	}

	@Override
	public void createPartControl(Composite parent) {

		parent.setLayout(new GridLayout(1, false));
		//
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
		GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_composite.widthHint = 414;
		composite.setLayoutData(gd_composite);
		//
		txtRootFile = new Text(composite, SWT.BORDER);
		txtRootFile.setLayoutData(new RowData(197, SWT.DEFAULT));
		txtRootFile.setText(ROOT_FILE);
		//
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
		//
		Composite composite_1 = new Composite(parent, SWT.NONE);
		composite_1.setLayout(null);
		GridData gd_composite_1 = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_composite_1.widthHint = 873;
		gd_composite_1.heightHint = 200;
		composite_1.setLayoutData(gd_composite_1);
		//
		treeViewer = new TreeViewer(composite_1, SWT.BORDER);
		FeatureTreeContentProvider feature_provider = presenter
				.getFeatureTreeContentProvider();

		treeViewer.setContentProvider(feature_provider);
		treeViewer.setInput(getViewSite());
		Tree tree = treeViewer.getTree();
		tree.setBounds(0, 0, 332, 200);

		//
		treeViewer_SupportScreens = new TreeViewer(composite_1, SWT.BORDER);
		SupportScreenTreeContentProvider supportScreensProvider = presenter
				.getSupportScreensTreeContentProvider();
		treeViewer_SupportScreens.setContentProvider(supportScreensProvider);
		treeViewer_SupportScreens.setInput(getViewSite());
		Tree tree_supportScreens = treeViewer_SupportScreens.getTree();
		tree_supportScreens.setBounds(338, 0, 525, 200);
		GridData gd_tree_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_tree_1.widthHint = 494;

		//

		presenter.makeActions();
		presenter.loadPluginSettings();
	}

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
			FeatureTreeContentProvider provider = presenter
					.getFeatureTreeContentProvider();
			SupportScreenTreeContentProvider supportScreensProvider = presenter
					.getSupportScreensTreeContentProvider();
			treeViewer.setContentProvider(provider);
			treeViewer_SupportScreens
					.setContentProvider(supportScreensProvider);
		}
	}

	public void setCurrentFileRootPath(String string) {
		txtRootFile.setText(string);
	}
}
