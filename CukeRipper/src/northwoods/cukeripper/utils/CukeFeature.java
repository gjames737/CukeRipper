package northwoods.cukeripper.utils;

import static northwoods.cukeripper.utils.CommonRips.BREAKLINE;
import static northwoods.cukeripper.utils.CommonRips.FEATURE;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CukeFeature {

	private final String name;
	private List<CukeScenario> scenarios;
	private File file;

	// public CukeFeature(String name, CukeFileReader reader) {
	// this.name = name;
	// this.scenarios = new ArrayList<CukeScenario>();
	// reader.getFileWithFeature(this.getName());
	// }

	public CukeFeature(String name, File f) {
		this.name = name;
		this.scenarios = new ArrayList<CukeScenario>();
		this.file = f;
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
			ruby += BREAKLINE + BREAKLINE + cukeScenario.toRuby();
		}

		return ruby;
	}

	@Override
	public String toString() {
		return name;
	}

	public File getFile() {
		return file;
	}
}
