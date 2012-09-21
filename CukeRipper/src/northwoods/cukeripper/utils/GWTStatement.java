package northwoods.cukeripper.utils;

import static northwoods.cukeripper.utils.CommonRips.BREAKLINE;
import static northwoods.cukeripper.utils.CommonRips.DO;
import static northwoods.cukeripper.utils.CommonRips.END;
import static northwoods.cukeripper.utils.CommonRips.TODO;

import java.util.ArrayList;
import java.util.List;

public class GWTStatement {

	private String statement;
	private List<StepAction> actionsList;
	private StatementType type;

	public enum StatementType {
		GIVEN, THEN, WHEN
	}

	public GWTStatement(StatementType _type, String _statement) {
		this.type = _type;
		this.statement = _statement;
		this.actionsList = new ArrayList<StepAction>();
	}

	public String getFormattedStatement(boolean isAnd) {
		if (isAnd)
			return "And " + statement;
		return getTypePrefix() + " " + statement;
	}

	public String getStatement() {
		return statement;
	}

	public String toRuby() {
		return getTypePrefix() + " /^" + statement + "$/ " + DO
				+ createActionStatement() + END;
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

}
