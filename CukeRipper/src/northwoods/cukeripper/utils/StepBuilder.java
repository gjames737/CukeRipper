package northwoods.cukeripper.utils;

import java.util.ArrayList;
import java.util.List;

public class StepBuilder {

	private List<GWTStatement> givenStatements;

	public StepBuilder() {
		givenStatements = new ArrayList<GWTStatement>();
	}

	public GWTStatement getGivenStatement(int i) {
		return givenStatements.get(i);
	}

	public void createGivenStatement(GWTStatement givenStatement) {
		givenStatements.add(givenStatement);
	}
}
