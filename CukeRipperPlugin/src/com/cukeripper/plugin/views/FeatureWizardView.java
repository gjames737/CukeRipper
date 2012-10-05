package com.cukeripper.plugin.views;

import java.util.ArrayList;
import java.util.List;

import northwoods.cukeripper.utils.CommonRips;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class FeatureWizardView extends ViewPart {
	private Text txtFeature;
	private Text txtScenarioTitle;
	private List<Combo> statementCombos;
	private List<Combo> gwtCombos;
	private Combo comboFeatures;
	private String[] gwtItems = new String[] { CommonRips.GIVEN,
			CommonRips.WHEN, CommonRips.THEN };
	private FeatureWizardPresenter presenter;

	public FeatureWizardView() {

		statementCombos = new ArrayList<Combo>();
		gwtCombos = new ArrayList<Combo>();
		presenter = new FeatureWizardPresenter(this);
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FormLayout());

		Composite composite_main = new Composite(parent, SWT.NONE);
		composite_main.setLayout(new FormLayout());
		FormData fd_composite_main = new FormData();
		fd_composite_main.bottom = new FormAttachment(0, 469);
		fd_composite_main.right = new FormAttachment(0, 733);
		fd_composite_main.top = new FormAttachment(0);
		fd_composite_main.left = new FormAttachment(0);
		composite_main.setLayoutData(fd_composite_main);

		Composite composite_feature_text = new Composite(composite_main,
				SWT.NONE);
		FormData fd_composite_feature_text = new FormData();
		fd_composite_feature_text.top = new FormAttachment(0, 10);
		fd_composite_feature_text.right = new FormAttachment(100);
		fd_composite_feature_text.bottom = new FormAttachment(100, -22);
		composite_feature_text.setLayoutData(fd_composite_feature_text);

		txtFeature = new Text(composite_feature_text, SWT.BORDER
				| SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		txtFeature.setBounds(0, 0, 407, 427);

		Composite composite_scenario_drops = new Composite(composite_main,
				SWT.NONE);
		fd_composite_feature_text.left = new FormAttachment(
				composite_scenario_drops, 6);
		FormData fd_composite_scenario_drops = new FormData();
		fd_composite_scenario_drops.top = new FormAttachment(0, 10);
		fd_composite_scenario_drops.left = new FormAttachment(0, 10);
		fd_composite_scenario_drops.right = new FormAttachment(100, -413);
		fd_composite_scenario_drops.bottom = new FormAttachment(100, -10);
		composite_scenario_drops.setLayoutData(fd_composite_scenario_drops);

		Label lblFeature = new Label(composite_scenario_drops, SWT.NONE);
		lblFeature.setBounds(10, 10, 42, 15);
		lblFeature.setText("Feature:");

		Label lblScenarioTitle = new Label(composite_scenario_drops, SWT.WRAP
				| SWT.RIGHT);
		lblScenarioTitle.setBounds(0, 31, 55, 34);
		lblScenarioTitle.setText("Scenario Title:");

		Composite compositeStatementDDs = new Composite(
				composite_scenario_drops, SWT.NONE);
		compositeStatementDDs.setBounds(0, 83, 310, 331);

		Combo comboGWT0 = new Combo(compositeStatementDDs, SWT.READ_ONLY);
		comboGWT0.setLocation(0, 0);
		comboGWT0.setSize(77, 23);

		Combo comboStatement0 = new Combo(compositeStatementDDs, SWT.NONE);
		comboStatement0.setLocation(83, 0);
		comboStatement0.setSize(217, 23);

		Combo comboGWT1 = new Combo(compositeStatementDDs, SWT.READ_ONLY);
		comboGWT1.setBounds(0, 29, 77, 23);

		Combo comboStatement1 = new Combo(compositeStatementDDs, SWT.NONE);
		comboStatement1.setBounds(83, 29, 217, 23);

		Combo comboGWT3 = new Combo(compositeStatementDDs, SWT.READ_ONLY);
		comboGWT3.setBounds(0, 87, 77, 23);

		Combo comboStatement3 = new Combo(compositeStatementDDs, SWT.NONE);
		comboStatement3.setBounds(83, 87, 217, 23);

		Combo comboGWT2 = new Combo(compositeStatementDDs, SWT.READ_ONLY);
		comboGWT2.setBounds(0, 58, 77, 23);

		Combo comboStatement2 = new Combo(compositeStatementDDs, SWT.NONE);
		comboStatement2.setBounds(83, 58, 217, 23);

		Combo comboGWT5 = new Combo(compositeStatementDDs, SWT.READ_ONLY);
		comboGWT5.setBounds(0, 145, 77, 23);

		Combo comboStatement5 = new Combo(compositeStatementDDs, SWT.NONE);
		comboStatement5.setBounds(83, 145, 217, 23);

		Combo comboGWT4 = new Combo(compositeStatementDDs, SWT.READ_ONLY);
		comboGWT4.setBounds(0, 116, 77, 23);

		Combo comboStatement4 = new Combo(compositeStatementDDs, SWT.NONE);
		comboStatement4.setBounds(83, 116, 217, 23);

		Combo comboGWT7 = new Combo(compositeStatementDDs, SWT.READ_ONLY);
		comboGWT7.setBounds(0, 203, 77, 23);

		Combo comboStatement7 = new Combo(compositeStatementDDs, SWT.NONE);
		comboStatement7.setBounds(83, 203, 217, 23);

		Combo comboGWT6 = new Combo(compositeStatementDDs, SWT.READ_ONLY);
		comboGWT6.setBounds(0, 174, 77, 23);

		Combo comboStatement6 = new Combo(compositeStatementDDs, SWT.NONE);
		comboStatement6.setBounds(83, 174, 217, 23);

		Combo comboGWT9 = new Combo(compositeStatementDDs, SWT.READ_ONLY);
		comboGWT9.setBounds(0, 261, 77, 23);

		Combo comboStatement9 = new Combo(compositeStatementDDs, SWT.NONE);
		comboStatement9.setBounds(83, 261, 217, 23);

		Combo comboGWT8 = new Combo(compositeStatementDDs, SWT.READ_ONLY);
		comboGWT8.setBounds(0, 232, 77, 23);

		Combo comboStatement8 = new Combo(compositeStatementDDs, SWT.NONE);
		comboStatement8.setBounds(83, 232, 217, 23);

		Combo comboGWT10 = new Combo(compositeStatementDDs, SWT.READ_ONLY);
		comboGWT10.setBounds(0, 290, 77, 23);

		Combo comboStatement10 = new Combo(compositeStatementDDs, SWT.NONE);
		comboStatement10.setBounds(83, 290, 217, 23);

		comboFeatures = new Combo(composite_scenario_drops, SWT.READ_ONLY);
		comboFeatures.setBounds(61, 7, 249, 23);

		//
		statementCombos.add(comboStatement0);
		statementCombos.add(comboStatement1);
		statementCombos.add(comboStatement2);
		statementCombos.add(comboStatement3);
		statementCombos.add(comboStatement4);
		statementCombos.add(comboStatement5);
		statementCombos.add(comboStatement6);
		statementCombos.add(comboStatement7);
		statementCombos.add(comboStatement8);
		statementCombos.add(comboStatement9);
		statementCombos.add(comboStatement10);
		//
		gwtCombos.add(comboStatement0);
		gwtCombos.add(comboStatement1);
		gwtCombos.add(comboStatement2);
		gwtCombos.add(comboStatement3);
		gwtCombos.add(comboStatement4);
		gwtCombos.add(comboStatement5);
		gwtCombos.add(comboStatement6);
		gwtCombos.add(comboStatement7);
		gwtCombos.add(comboStatement8);
		gwtCombos.add(comboStatement9);
		gwtCombos.add(comboStatement10);
		//
		for (Combo statCombo : statementCombos) {
			statCombo.setItems(gwtItems);
		}

		txtScenarioTitle = new Text(composite_scenario_drops, SWT.BORDER);
		txtScenarioTitle.setBounds(61, 44, 249, 21);
		//

		refresh();
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	void refresh() {
		String[] allPossibleStatementStrings = presenter
				.getAllPossibleStatementStrings();
		for (Combo statCombo : statementCombos) {
			statCombo.setItems(allPossibleStatementStrings);
		}

	}

}
