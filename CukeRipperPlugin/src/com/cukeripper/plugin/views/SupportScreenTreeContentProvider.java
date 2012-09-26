package com.cukeripper.plugin.views;

import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.StepAction;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class SupportScreenTreeContentProvider implements ITreeContentProvider {

	private StepAction[] actions;

	public SupportScreenTreeContentProvider(StepAction[] stepActions) {
		this.actions = stepActions;
	}

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
		if (inputElement instanceof GWTStatement) {
			GWTStatement statement = (GWTStatement) inputElement;
			return statement.getAllActions().toArray();
		}
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
