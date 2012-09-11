package northwoods.cukeripper.utils;

import static northwoods.cukeripper.utils.CommonRips.BREAKLINE;
import static northwoods.cukeripper.utils.CommonRips.FEATURE;
import static northwoods.cukeripper.utils.CommonRips.SCENARIO;

import java.util.ArrayList;
import java.util.List;

public class CukeFeature {

	private final String name;
	private List<CukeScenario> scenarios;

	public CukeFeature(String name) {
		this.name = name;
		this.scenarios = new ArrayList<CukeScenario>();
	}

	public String getName() {
		return name;
	}

	public List<CukeScenario> getScenarios() {
		return scenarios;
	}

	public void addScenario(CukeScenario s) {
		scenarios.add(s);
	}

	public String toRuby() {
		String ruby = FEATURE + ": " + name + BREAKLINE;
		for (int i = 0; i < scenarios.size(); i++) {
			CukeScenario cukeScenario = scenarios.get(i);
			ruby += BREAKLINE + SCENARIO + ": " + cukeScenario.getName()
					+ BREAKLINE;
			List<GWTStatement> statements = cukeScenario.getStatements();
			for (int j = 0; j < statements.size(); j++) {
				ruby += BREAKLINE + statements.get(j).getStatement();
			}
		}

		return ruby;
	}
}
