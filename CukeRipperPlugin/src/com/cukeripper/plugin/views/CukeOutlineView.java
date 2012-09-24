package com.cukeripper.plugin.views;

import java.io.File;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;

public class CukeOutlineView extends ViewPart {

	private static final String ROOT_FILE = "C:" + File.separator + "TFSBuild"
			+ File.separator + "CoPilot" + File.separator + "Trunk"
			+ File.separator + "CoPilotCukes";
	private TreeViewer treeViewer;
	private FeatureTreeContentProvider provider;
	private CukeOutlinePresenter presenter;

	public CukeOutlineView() {
		presenter = new CukeOutlinePresenter(this);

	}

	@Override
	public void createPartControl(Composite parent) {
		// Layout layout = new CukeOutlineLayout();
		// parent.setLayout(layout);
		provider = new FeatureTreeContentProvider(presenter.getfeatureFiles(),
				presenter.getFeatureParser());
		treeViewer = new TreeViewer(parent);
		treeViewer.setContentProvider(provider);
		treeViewer.setInput(getViewSite());
		presenter.makeActions();

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

	public String getCurrentFileRoot() {
		return ROOT_FILE;
	}
}
