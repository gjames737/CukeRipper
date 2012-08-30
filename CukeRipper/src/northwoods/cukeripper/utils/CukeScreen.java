package northwoods.cukeripper.utils;

import java.util.ArrayList;
import java.util.List;

public class CukeScreen {

	private List<ScreenMethod> methodList;
	private String name;

	public CukeScreen(String _name) {
		this.name = _name;
		this.methodList = new ArrayList<ScreenMethod>();
	}

	public String getName() {
		return name;
	}

	public String getMethod(int methodIndex) {
		if (methodList.size() - 1 < methodIndex || methodIndex < 0)
			return "";
		return methodList.get(methodIndex).getName();
	}

	public void addMethod(ScreenMethod method) {
		methodList.add(method);
	}

}
