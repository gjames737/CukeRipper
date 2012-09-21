package northwoods.cukeripper.sparkview;

import java.io.File;
import java.util.List;

import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.StepAction;
import northwoods.cukeripper.utils.parsing.FeatureFileParser;
import northwoods.cukeripper.utils.parsing.StepFileParser;

public class SparkView {

	private static CukeFileReader reader;
	private static FeatureFileParser featureParser;
	private static StepFileParser stepParser;

	public static void main(String[] args) {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		reader = new CukeFileReader("C:" + File.separator + "TFSBuild"
				+ File.separator + "CoPilot" + File.separator + "Trunk"
				+ File.separator + "CoPilotCukes");
		featureParser = new FeatureFileParser(reader);
		stepParser = new StepFileParser(reader);

		// showFeatures();

		showStepDefs();

	}

	private static void showStepDefs() {
		File[] stepFiles = reader.getAllStepDefinitionFiles();
		System.err.println("\nStep def files:");
		for (File file : stepFiles) {
			System.out.println(file.getName());
		}

		System.out.println("-----------------------------------------------");
		for (File file : stepFiles) {
			System.err.println("\nStep [" + file.getName() + "]");

			List<GWTStatement> statements = stepParser
					.getGWTStatementsFromFile(file);
			for (GWTStatement gwtStatement : statements) {
				System.out.println("        " + "[" + gwtStatement.getType()
						+ "] " + gwtStatement.getStatement());
				List<StepAction> actions = gwtStatement.getAllActions();
				if (actions.isEmpty())
					System.out.println("           no actions found");
				for (StepAction stepAction : actions) {
					System.out.println("           " + stepAction.toRuby());
				}
			}
		}

	}

	private static void showFeatures() {

		File[] featureFiles = reader.getAllFeatureFiles();

		// System.err.println("\nFeature files:");
		// for (File file : featureFiles) {
		// System.out.println(file.getName());
		// }

		System.err.println("\nFeatures:");

		for (int i = 0; i < featureFiles.length; i++) {
			CukeFeature feature = featureParser
					.getFeatureFromFile(featureFiles[i]);
			List<CukeScenario> scenarios = feature.getScenarios();
			System.out.println("Feature " + (i + 1) + " is: "
					+ feature.getName());
			System.err.println("\n   Scenarios:");
			for (CukeScenario cukeScenario : scenarios) {
				System.out.println("   " + cukeScenario.getName());

				List<GWTStatement> statements = cukeScenario.getStatements();
				// for (GWTStatement gwtStatement : statements) {
				// System.out.println("        " + "["
				// + gwtStatement.getType() + "] "
				// + gwtStatement.getStatement());
				//
				// // CukeScreen _screen = new CukeScreen("SomePage");
				// // ScreenMethod method = new ScreenMethod("do_this",
				// // "#ruby code!");
				// // _screen.addMethod(method);
				//
				// // gwtStatement.addStepAction(new StepAction(_screen, 0));
				//
				// // System.out.println("        " + gwtStatement.toRuby());
				//
				// }
			}

			System.out
					.println("----------------------------------------------");
		}

		// for (int i = 0; i < featureFiles.length; i++) {
		// CukeFeature feature = featureParser
		// .getFeatureFromFile(featureFiles[i]);
		//
		// System.out.println(feature.getName());
		// }
	}
}
