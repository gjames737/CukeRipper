package com.cukeripper.plugin.views.providers.label;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import northwoods.cukeripper.utils.CukeScreen;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.cukeripper.plugin.Activator;
import com.cukeripper.plugin.CommonSettings;

public class SupportScreenLabelProvider extends LabelProvider implements
		ILabelProvider {
	private Map<ImageDescriptor, Image> imageCache = new HashMap<ImageDescriptor, Image>(
			11);

	@Override
	public Image getImage(Object element) {
		if (element instanceof CukeScreen) {
			CukeScreen screen = (CukeScreen) element;

			String iconFile = screen.getScreenFile() == null ? CommonSettings.ICON_FN_CUKE_SCREEN_BAD
					: CommonSettings.ICON_FN_CUKE_SCREEN;
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
