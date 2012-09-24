package com.cukeripper.plugin.views;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class CukeOutlineView extends ViewPart {

	private TreeViewer treeViewer;
	private IContentProvider provider;

	class CukeOutlineContentProvider implements ITreeContentProvider {

		@Override
		public void dispose() {
			// TODO Auto-generated method stub

		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub

		}

		@Override
		public Object[] getElements(Object inputElement) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getParent(Object element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			// TODO Auto-generated method stub
			return false;
		}

	}

	@Override
	public void createPartControl(Composite parent) {
		provider = new CukeOutlineContentProvider();

		treeViewer = new TreeViewer(parent);
		treeViewer.setContentProvider(provider);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
