package com.cukeripper.plugin.views;

import java.io.File;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
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
	private Text rootText;
	private Composite parent;

	public CukeOutlineView() {
		presenter = new CukeOutlinePresenter(this);

	}

	@Override
	public void createPartControl(Composite parent) {
		setupLayout(parent);
		setupRefreshButton(parent);
		rootText = new Text(parent, SWT.SINGLE);
		rootText.setText("");
		setupFeatureTree(parent);

		presenter.makeActions();

	}

	private void setupRefreshButton(Composite parent) {
		Button refreshButton = new Button(parent, SWT.PUSH);
		refreshButton.setText("Refresh");
		refreshButton.addListener(SWT.Selection,
				new org.eclipse.swt.widgets.Listener() {
					@Override
					public void handleEvent(Event event) {
						// showMessage("!");
						presenter.handleRefreshEvent();
					}
				});
	}

	private void setupLayout(Composite parent) {
		// GridLayout layout = new GridLayout();
		// layout.numColumns = 1;
		// parent.setLayout(layout);
		RowLayout rowLayout = new RowLayout();
		parent.setLayout(rowLayout);
	}

	private void setupFeatureTree(Composite parent) {
		provider = new FeatureTreeContentProvider(presenter.getfeatureFiles(),
				presenter.getFeatureParser());
		treeViewer = new TreeViewer(parent);
		treeViewer.setContentProvider(provider);
		treeViewer.setInput(getViewSite());
		this.parent = parent;
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
		if (rootText != null)
			return rootText.getText();
		else
			return "";
	}

	public void refresh() {
		if (treeViewer != null) {
			provider = new FeatureTreeContentProvider(
					presenter.getfeatureFiles(), presenter.getFeatureParser());
			treeViewer.setContentProvider(provider);

		}
	}
}
