package com.cukeripper.plugin.views;

import java.io.File;

import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.StepAction;
import northwoods.cukeripper.utils.parsing.StepFileParser;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class SupportScreenTreeContentProvider implements ITreeContentProvider {

	private CukeFileReader reader;
	private StepFileParser stepFileParser;
	private File[] allStepFiles;
	private File[] allScreenFiles;

	public SupportScreenTreeContentProvider(CukeFileReader _reader) {
		this.reader = _reader;
		this.stepFileParser = new StepFileParser(this.reader);
		this.allStepFiles = reader.getAllStepDefinitionFiles();
		this.allScreenFiles = reader.getAllScreenFiles();
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
		return new StepAction[] {};
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
