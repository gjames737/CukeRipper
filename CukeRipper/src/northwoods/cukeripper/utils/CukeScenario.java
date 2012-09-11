package northwoods.cukeripper.utils;

import static northwoods.cukeripper.utils.CommonRips.BREAKLINE;

import java.util.ArrayList;
import java.util.List;

import northwoods.cukeripper.utils.GWTStatement.StatementType;

public class CukeScenario {

	private List<GWTStatement> statements;
	private String name;

	public CukeScenario(String name) {
		this.name = name;
		statements = new ArrayList<GWTStatement>();
	}

	public GWTStatement getStatement(int i) {
		return getStatements().get(i);
	}

	public List<GWTStatement> getStatements() {
		return statements;
	}

	public void createStatement(GWTStatement statement) {
		statements.add(statement);
	}

	public String getName() {
		return name;
	}

	public String toRuby() {
		String ruby = CommonRips.SCENARIO + ": " + name;
		int size = statements.size();
		StatementType lastType = null;
		for (int i = 0; i < size; i++) {
			StatementType thisType = statements.get(i).getType();
			boolean isAnd = thisType == lastType;
			ruby += BREAKLINE + statements.get(i).getFormattedStatement(isAnd);
			lastType = thisType;
		}
		return ruby;
	}
}
