package com.cukeripper.plugin.views.providers.content;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.LoadedCukes;
import northwoods.cukeripper.utils.StepAction;
import northwoods.cukeripper.utils.parsing.StepFileParser;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.cukeripper.plugin.views.ICukeParsingListener;

public class SupportScreenTreeContentProvider implements ITreeContentProvider {

	private CukeFileReader reader;
	private StepFileParser stepFileParser;
	private File[] allStepFiles;
	private File[] allScreenFiles;
	private StepAction[] actions;
	private ICukeParsingListener listener;

	public SupportScreenTreeContentProvider(ICukeParsingListener _listener,
			CukeFileReader _reader) {
		this.listener = _listener;
		this.reader = _reader;
		this.stepFileParser = new StepFileParser(this.reader);
		this.allStepFiles = reader.getAllStepDefinitionFiles();
		this.allScreenFiles = reader.getAllScreenFiles();
		loadActions();
	}

	private void loadActions() {
		try {
			if (allStepFiles == null) {
				actions = new StepAction[] {};
				return;
			}
			List<StepAction> actions_list = new ArrayList<StepAction>();
			for (int i = 0; i < allStepFiles.length; i++) {
				List<GWTStatement> statements_list = stepFileParser
						.getGWTStatementsFromFile(allStepFiles[i]);
				for (int j = 0; j < statements_list.size(); j++) {
					GWTStatement statement = statements_list.get(j);
					List<StepAction> actions_small_list = statement
							.getAllActions();
					for (StepAction stepAction : actions_small_list) {
						actions_list.add(stepAction);
					}
				}
			}

			actions = new StepAction[actions_list.size()];
			for (int i = 0; i < actions.length; i++) {
				actions[i] = actions_list.get(i);
			}
		} catch (Exception e) {
			listener.onStepFileParserException(e);
		}

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

		return LoadedCukes.getScreens().toArray();

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
