package northwoods.cukeripper.utils;

import static northwoods.cukeripper.utils.CommonRips.BREAKLINE;
import static northwoods.cukeripper.utils.CommonRips.CLASS;
import static northwoods.cukeripper.utils.CommonRips.END;
import static northwoods.cukeripper.utils.CommonRips.TODO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CukeScreen {

	private List<ScreenMethod> methodList;
	private String name;
	private File screenFile;

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

	public List<ScreenMethod> getMethods() {
		return methodList;
	}

	public String toRuby() {
		String ruby = "";
		ruby += CLASS + " " + name;
		ruby += BREAKLINE;
		if (methodList.size() == 0) {
			ruby += TODO;
			ruby += BREAKLINE;
		} else {
			for (int i = 0; i < methodList.size(); i++) {
				ruby += CommonRips.DEF + " ";
				ruby += methodList.get(i).getName();
				ruby += BREAKLINE;
				ruby += methodList.get(i).getBody();
				ruby += BREAKLINE;
			}
		}

		ruby += END;
		return ruby;
	}

	@Override
	public String toString() {
		return getName();
	}

	public File getScreenFile() {
		return screenFile;
	}

	public void setScreenFile(File _screenFile) {
		this.screenFile = _screenFile;
	}
}
