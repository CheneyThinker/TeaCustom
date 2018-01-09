package com.tea.custom;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

import com.tea.custom.constant.TeaConstant;
import com.tea.custom.ui.TeaTabbedPaneUI;

@SuppressWarnings("serial")
public class TeaJTabbedPane extends JTabbedPane {

	private Vector<Boolean> closeables;
	private Vector<String> titles;
	private Color northColor = new Color(57, 181, 215);
	private Color southColor = new Color(145, 232, 255);
	private Color borderColor = new Color(90, 154, 179);
	
	public TeaJTabbedPane() {
		super();
		init();
	}
	
	private void init() {
		setFont(TeaConstant.TITLE_FONT);
		setForeground(TeaConstant.DARK_GRAY);
		closeables = new Vector<Boolean>(0);
		titles = new Vector<String>(0);
		setUI(new TeaTabbedPaneUI(this));
	}
	
	public void addTab(String title, Component component) {
		addTab(title, null, component, null, true);
	}
	
	public void addTabNoClose(String title, Component component) {
		addTab(title, null, component, null, false);
	}
	
	public void addTab(String title, Icon icon, Component component) {
		addTab(title, icon, component, null, true);
	}
	
	public void addTabNoClose(String title, Icon icon, Component component) {
		addTab(title, icon, component, null, false);
	}
	
	public void addTab(String title, Component component, String tip) {
		addTab(title, null, component, tip, true);
	}
	
	public void addTabNoClose(String title, Component component, String tip) {
		addTab(title, null, component, tip, false);
	}
	
	public void addTab(String title, Icon icon, Component component, String tip, boolean closeable) {
		if (!titles.contains(title)) {
			titles.add(title);
			closeables.add(closeable);
			addTab(title, icon, component, tip);
			setSelectedComponent(component);
		} else {
			setSelectedIndex(titles.indexOf(title));
		}
	}

	public Vector<Boolean> getCloseables() {
		return closeables;
	}

	public Vector<String> getTitles() {
		return titles;
	}
	
	public Color getNorthColor() {
		return northColor;
	}

	public void setNorthColor(Color northColor) {
		this.northColor = northColor;
	}

	public Color getSouthColor() {
		return southColor;
	}

	public void setSouthColor(Color southColor) {
		this.southColor = southColor;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

}
