package com.tea.custom.utils;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class TeaUtils {

	private static AtomicInteger integer;
	
	public static String getNOCode() {
		if (integer == null)
			integer = new AtomicInteger(100000);
		int number = integer.getAndIncrement();
		if (number > 999997)
			number = integer.getAndSet(100000);
		if (getJavaVersion() >= 1.8) {
			LocalDateTime dateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMdd-HHmm-ss");
			return dateTime.format(formatter) + String.valueOf(number).substring(0, 2) + "-" + String.valueOf(number).substring(2);
		} else {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MMdd-HHmm-ss");
			return df.format(new Date()) + String.valueOf(number).substring(0, 2) + "-" + String.valueOf(number).substring(2);
		}
	}
	
	public static void clear() {
		integer = null;
	}
	
	public static String getCurrentTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		return df.format(new Date());
	}
	
	public static void initGlobalFontSetting(Font font) {
		FontUIResource fontRes = new FontUIResource(font);
		for(Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();){
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if(value instanceof FontUIResource) {
				UIManager.put(key, fontRes);
			}
		}
	}
	
	public static BufferedImage getImage(String name) {
		try {
			return ImageIO.read(TeaUtils.class.getClassLoader().getResourceAsStream(BundleUtils.getBundle("resources/tea").getString(name)));
		} catch (IOException e) {
			return null;
		}
	}
	
	public static Icon getIcon(String name) {
		return new ImageIcon(getImage(name));
	}
	
	public static double getJavaVersion() {
		Double javaVersion = null;
		try {
			String ver = System.getProperties().getProperty("java.version");
			String version = "";
			boolean firstPoint = true;
			for (int i = 0; i < ver.length(); i++) {
				if (ver.charAt(i) == '.') {
					if (firstPoint) {
						version += ver.charAt(i);
					}
					firstPoint = false;
				} else if (Character.isDigit(ver.charAt(i))) {
					version += ver.charAt(i);
				}
			}
			javaVersion = new Double(version);
		} catch (Exception ex) {
			javaVersion = new Double(1.3);
		}
		return javaVersion.doubleValue();
    }
	
}
