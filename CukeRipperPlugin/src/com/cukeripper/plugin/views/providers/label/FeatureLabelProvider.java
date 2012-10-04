package com.cukeripper.plugin.views.providers.label;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.GWTStatement;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.cukeripper.plugin.Activator;
import com.cukeripper.plugin.CommonSettings;

public class FeatureLabelProvider extends LabelProvider implements
		ILabelProvider {
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
			return image;
		}

		return super.getImage(element);
	}

	@Override
	public void dispose() {
		for (Iterator i = imageCache.values().iterator(); i.hasNext();) {
			((Image) i.next()).dispose();
		}
		imageCache.clear();
	}
}
