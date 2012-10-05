package com.cukeripper.plugin.views.providers.label;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.GWTStatement;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.cukeripper.plugin.Activator;
import com.cukeripper.plugin.CommonSettings;

public class FeatureLabelProvider extends ColumnLabelProvider {
	private Map<ImageDescriptor, Image> imageCache = new HashMap<ImageDescriptor, Image>(
			11);

	@Override
	public Image getImage(Object element) {
		String iconFile = null;
		if (element instanceof CukeFeature) {
			iconFile = CommonSettings.ICON_FN_CUKE_FEATURE;
		} else if (element instanceof CukeScenario) {
			iconFile = CommonSettings.ICON_FN_CUKE_SCENARIO;
		} else if (element instanceof GWTStatement) {
			iconFile = CommonSettings.ICON_FN_GWTSTATEMENT;
		}

		if (iconFile != null) {
			ImageDescriptor descriptor = Activator
					.getImageDescriptorByName(iconFile);
			// obtain the cached image corresponding to the descriptor
			Image image = (Image) imageCache.get(descriptor);
			if (image == null) {
				image = descriptor.createImage();
				imageCache.put(descriptor, image);
			}
			//

			return image;
		}

		return super.getImage(element);
	}

	@Override
	public Color getBackground(Object element) {
		Color c = new Color(Display.getCurrent(), CommonSettings.RGB_WHITE);
		if (element instanceof CukeFeature) {
			c = new Color(Display.getCurrent(),
					CommonSettings.getFeatureBackgroundColor());
		} else if (element instanceof CukeScenario) {
			c = new Color(Display.getCurrent(),
					CommonSettings.getScenarioBackgroundColor());
		} else if (element instanceof GWTStatement) {
			c = new Color(Display.getCurrent(),
					CommonSettings.getStatementBackgroundColor());
		}

		return c;
	}

	@Override
	public void dispose() {
		for (Iterator<Image> i = imageCache.values().iterator(); i.hasNext();) {
			((Image) i.next()).dispose();
		}
		imageCache.clear();
	}
}
