package northwoods.cukeripper.utils;

import static northwoods.cukeripper.utils.CommonRips.BREAKLINE;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import northwoods.cukeripper.utils.GWTStatement.StatementType;

public class CukeScenario {

	private List<GWTStatement> statements;
	private String name;
	private File file;

	public CukeScenario(String name, File f) {
		this.name = name;
		this.file = f;
		statements = new ArrayList<GWTStatement>();
	}

	public GWTStatement getStatement(int i) {
		return getStatements().get(i);
	}

	public List<GWTStatement> getStatements() {
		return statements;
	}

	public GWTStatement[] getStatementsArray() {
		GWTStatement[] statementsArray = new GWTStatement[statements.size()];
		for (int i = 0; i < statementsArray.length; i++) {
			statementsArray[i] = statements.get(i);
		}
		return statementsArray;
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

	@Override
	public String toString() {
		return getName();
	}

	public File getFile() {
		return file;
	}
}
