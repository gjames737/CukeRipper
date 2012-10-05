package com.cukeripper.plugin;

import org.eclipse.swt.graphics.RGB;

public class CommonSettings {

	public static final String ICON_FN_CUKE_SCREEN = "cuke_screen_icon.gif";
	public static final String ICON_FN_CUKE_SCREEN_BAD = "cuke_screen_bad_icon.gif";
	public static final String ICON_FN_CUKE_FEATURE = "cuke_feature_icon.gif";
	public static final String ICON_FN_CUKE_SCENARIO = "cuke_scenario_icon.gif";
	public static final String ICON_FN_GWTSTATEMENT = "gwtstatement_icon.gif";
	public static final RGB RGB_WHITE = new RGB(255, 255, 255);
	public static final RGB RGB_GRAY = new RGB(195, 195, 195);
	public static final RGB RGB_BLUEISH = new RGB(220, 220, 242);
	public static final RGB RGB_REDISH = new RGB(242, 220, 220);
	public static final RGB RGB_GREENISH = new RGB(220, 242, 220);
	public static final RGB RGB_ORANGISH = new RGB(220, 242, 220);
	public static final RGB RGB_PINKISH = new RGB(255, 110, 170);

	public static RGB getFeatureBackgroundColor() {
		return RGB_WHITE;
	}

	public static RGB getScenarioBackgroundColor() {
		return RGB_BLUEISH;
	}

	public static RGB getStatementBackgroundColor() {
		return RGB_REDISH;
	}

	public static RGB getScreenBackgroundColor() {
		return RGB_GRAY;
	}

}
