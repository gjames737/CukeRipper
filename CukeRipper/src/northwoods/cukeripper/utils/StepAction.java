package northwoods.cukeripper.utils;

import static northwoods.cukeripper.utils.CommonRips.ON;

public class StepAction {

	private CukeScreen screen = null;
	private int methodIndex = -1;

	public StepAction(CukeScreen _screen, int _methodIndex) {
		this.screen = _screen;
		this.methodIndex = _methodIndex;
	}

	public String toRuby() {
		String rubyLine = "";
		if (methodIndex != -1 && screen != null) {
			String method = screen.getMethod(methodIndex);
			rubyLine = ON + "(" + screen.getName() + ")" + "." + method;
		}
		return rubyLine;
	}
}
