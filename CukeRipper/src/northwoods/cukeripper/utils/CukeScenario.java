package northwoods.cukeripper.utils;

import java.util.ArrayList;
import java.util.List;

public class CukeScenario {

	private List<GWTStatement> statements;
	private String name;

	public CukeScenario(String name) {
		this.name = name;
		statements = new ArrayList<GWTStatement>();
	}

	public GWTStatement getStatement(int i) {
		return statements.get(i);
	}

	public void createStatement(GWTStatement statement) {
		statements.add(statement);
	}

	public String getName() {
		return name;
	}
}
