package com.cukeripper.plugin.views;

import java.io.File;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
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
	private Button btnStop;
	private Button btnRefresh;

	// private Job job_refresh;

	public CukeOutlineView() {
		presenter = new CukeOutlinePresenter(this);

	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FormLayout());
		//
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FormLayout());
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0, 5);
		fd_composite.left = new FormAttachment(0, 5);
		composite.setLayoutData(fd_composite);
		//
		txtRootFile = new Text(composite, SWT.BORDER);
		FormData fd_txtRootFile = new FormData();
		fd_txtRootFile.right = new FormAttachment(0, 977);
		fd_txtRootFile.top = new FormAttachment(0, 3);
		fd_txtRootFile.left = new FormAttachment(0, 195);
		txtRootFile.setLayoutData(fd_txtRootFile);
		txtRootFile.setText(ROOT_FILE);

		btnStop = new Button(composite, SWT.NONE);
		btnStop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.handleStopBtnEvent();
			}
		});
		btnStop.setLayoutData(new FormData());
		btnStop.setText("Stop");
		//
		btnRefresh = new Button(composite, SWT.NONE);
		FormData fd_btnRefresh = new FormData();
		fd_btnRefresh.left = new FormAttachment(btnStop, 6);
		fd_btnRefresh.right = new FormAttachment(0, 189);
		fd_btnRefresh.top = new FormAttachment(0);
		btnRefresh.setLayoutData(fd_btnRefresh);
		btnRefresh.setText("Refresh");
		btnRefresh.addListener(SWT.Selection,
				new org.eclipse.swt.widgets.Listener() {
					@Override
					public void handleEvent(Event event) {
						presenter.handleRefreshEvent(false);
					}
				});
		//
		Composite composite_1 = new Composite(parent, SWT.NONE);
		fd_composite.right = new FormAttachment(composite_1, 0, SWT.RIGHT);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.right = new FormAttachment(100, -10);
		fd_composite_1.left = new FormAttachment(0, 5);
		fd_composite_1.bottom = new FormAttachment(100, -8);
		fd_composite_1.top = new FormAttachment(0, 41);
		composite_1.setLayoutData(fd_composite_1);
		composite_1.setLayout(new FormLayout());
		//
		treeViewer = new TreeViewer(composite_1, SWT.BORDER);
		FeatureTreeContentProvider feature_provider = presenter
				.getFeatureTreeContentProvider();

		treeViewer.setContentProvider(feature_provider);
		treeViewer.setInput(getViewSite());
		Tree tree = treeViewer.getTree();
		FormData fd_tree = new FormData();
		fd_tree.left = new FormAttachment(0);
		fd_tree.top = new FormAttachment(0);
		fd_tree.bottom = new FormAttachment(100);
		tree.setLayoutData(fd_tree);

		//
		treeViewer_SupportScreens = new TreeViewer(composite_1, SWT.BORDER);
		SupportScreenTreeContentProvider supportScreensProvider = presenter
				.getSupportScreensTreeContentProvider();
		treeViewer_SupportScreens.setContentProvider(supportScreensProvider);
		treeViewer_SupportScreens.setInput(getViewSite());
		Tree tree_supportScreens = treeViewer_SupportScreens.getTree();
		fd_tree.right = new FormAttachment(tree_supportScreens, -6);
		FormData fd_tree_supportScreens = new FormData();
		fd_tree_supportScreens.top = new FormAttachment(0);
		fd_tree_supportScreens.bottom = new FormAttachment(100);
		fd_tree_supportScreens.right = new FormAttachment(0, 977);
		fd_tree_supportScreens.left = new FormAttachment(0, 451);
		tree_supportScreens.setLayoutData(fd_tree_supportScreens);
		GridData gd_tree_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_tree_1.widthHint = 494;
		setToRefreshableState();
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

	public TreeViewer getSupportScreensTree() {
		return treeViewer_SupportScreens;
	}

	public void showMessage(String message) {
		MessageDialog.openInformation(treeViewer.getControl().getShell(),
				"Sample View", message);
	}

	public void openEditorOnFile(File fileToOpen) {
		System.err.println("######### canWrite: " + fileToOpen.canWrite());
		if (fileToOpen.exists() && fileToOpen.isFile()) {
			IFileStore fileStore = EFS.getLocalFileSystem().getStore(
					fileToOpen.toURI());
			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();

			try {
				IDE.openEditorOnFileStore(page, fileStore);
				page = null;
				fileStore = null;
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

			final FeatureTreeContentProvider provider = presenter
					.getFeatureTreeContentProvider();
			final SupportScreenTreeContentProvider supportScreensProvider = presenter
					.getSupportScreensTreeContentProvider();

			// If you want to update the UI
			Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {
					// Do something in the user interface
					treeViewer.setContentProvider(provider);
					treeViewer_SupportScreens
							.setContentProvider(supportScreensProvider);
					setToRefreshableState();
				}
			});
		}
	}

	public void setCurrentFileRootPath(String string) {
		txtRootFile.setText(string);
	}

	public void setToStoppableState() {
		btnRefresh.setEnabled(false);
		btnStop.setEnabled(true);
	}

	public void setToRefreshableState() {
		btnRefresh.setEnabled(true);
		btnStop.setEnabled(false);
	}

	public void setToDisabledState() {
		btnRefresh.setEnabled(false);
		btnStop.setEnabled(false);
	}

}
