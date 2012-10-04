package northwoods.cukeripper.utils.parsing;

import java.util.Comparator;

import northwoods.cukeripper.utils.CukeFeature;

public class CukeFeatureComparator implements Comparator<CukeFeature> {

	@Override
	public int compare(CukeFeature arg0, CukeFeature arg1) {

		return arg0.getName().compareTo(arg1.getName());
	}

}
