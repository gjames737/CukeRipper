package com.cukeripper.plugin.views.providers.label;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import northwoods.cukeripper.utils.CukeScreen;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.cukeripper.plugin.Activator;
import com.cukeripper.plugin.CommonSettings;

public class SupportScreenLabelProvider extends ColumnLabelProvider implements
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
	public Color getBackground(Object element) {
		Color c = new Color(Display.getCurrent(), CommonSettings.RGB_WHITE);
		if (element instanceof CukeScreen) {
			c = new Color(Display.getCurrent(),
					CommonSettings.getScreenBackgroundColor());
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
