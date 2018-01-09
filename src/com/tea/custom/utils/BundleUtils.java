package com.tea.custom.utils;

import java.util.ResourceBundle;

public class BundleUtils {

	public static ResourceBundle getBundle(String baseName) {
		return ResourceBundle.getBundle(baseName, Locale.CHINA);
	}
	
	public static ResourceBundle[] getBundles(String... baseNames) {
		ResourceBundle[] resourceBundles = new ResourceBundle[baseNames.length];
		for (int i = 0; i < resourceBundles.length; i++)
			resourceBundles[i] = ResourceBundle.getBundle(baseNames[i]);
		return resourceBundles;
	}
	
}
