package northwoods.cukeripper.utils;

import static northwoods.cukeripper.utils.CommonRips.BREAKLINE;
import static northwoods.cukeripper.utils.CommonRips.DO;
import static northwoods.cukeripper.utils.CommonRips.END;
import static northwoods.cukeripper.utils.CommonRips.TODO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GWTStatement {

	private String statement;
	private List<StepAction> actionsList;
	private StatementType type;
	private File step_file;
	private File feature_file;

	public enum StatementType {
		GIVEN, THEN, WHEN
	}

	public GWTStatement(File _step_file, File _feature_file,
			StatementType _type, String _statement) {
		this.type = _type;
		this.statement = _statement;
		this.actionsList = new ArrayList<StepAction>();

		this.feature_file = _feature_file;
		setStepFile(_step_file);
	}

	public String getFormattedStatement(boolean isAnd) {
		if (isAnd)
			return "And " + statement;
		return getTypePrefix() + " " + statement;
	}

	public String getStatement() {
		return statement;
	}

	@Override
	public String toString() {
		return getStatement();
	}

	public String toRuby() {
		return getTypePrefix() + " " + CommonRips.SLASH_POINT + statement
				+ CommonRips.DOLLAR_SLASH + " " + DO + createActionStatement()
				+ END;
	}

	public String getTypePrefix() {
		String s = type.name().toLowerCase();
		s = s.substring(0, 1).toUpperCase() + s.substring(1);
		return s;
	}

	private String createActionStatement() {
		String actionStatement = "";
		if (actionsList.size() == 0)
			return BREAKLINE + TODO + BREAKLINE;
		for (int i = 0; i < actionsList.size(); i++) {
			actionStatement += BREAKLINE + actionsList.get(i).toRuby();
		}
		return actionStatement + BREAKLINE;
	}

	public void addStepAction(StepAction stepAction) {
		actionsList.add(stepAction);

	}

	public void setStepActions(List<StepAction> _actionsList) {
		this.actionsList = _actionsList;
	}

	public StatementType getType() {
		return type;
	}

	public List<StepAction> getAllActions() {
		return actionsList;
	}

	public File getStepFile() {
		return step_file;
	}

	public File getFeatureFile() {
		return feature_file;
	}

	public void setStepFile(File stepFile) {
		this.step_file = stepFile;
		// if (this.step_file != null) {
		// LoadedCukes.addStateFileToMap(this, stepFile);
		// }
	}

	public String slashToSlashStatement() {
		return CommonRips.SLASH_POINT + statement + CommonRips.DOLLAR_SLASH;
	}

}
