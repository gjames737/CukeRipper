package northwoods.cukeripper.utils;

import static northwoods.cukeripper.utils.CommonRips.ON;

public class StepAction {

	// private CukeScreen screen = null;
	private int methodIndex = -1;
	private int screenIndex;

	public StepAction(String screenName, int _methodIndex) {
		screenIndex = LoadedCukes.addScreen(screenName);
		this.methodIndex = _methodIndex;
	}

	public String toRuby() {
		String rubyLine = "";
		CukeScreen screen = LoadedCukes.getScreens().get(screenIndex);
		if (methodIndex != -1 && screen != null) {
			String method = screen.getMethod(methodIndex);
			rubyLine = ON + "(" + screen.getName() + ")" + "." + method;
		}
		return rubyLine;
	}

	public int getScreenIndex() {
		return screenIndex;
	}
}
