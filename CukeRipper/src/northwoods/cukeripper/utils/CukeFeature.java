package northwoods.cukeripper.utils;

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
}
