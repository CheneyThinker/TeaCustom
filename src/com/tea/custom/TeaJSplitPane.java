package com.tea.custom;

import java.awt.Component;

import javax.swing.JSplitPane;

@SuppressWarnings("serial")
public class TeaJSplitPane extends JSplitPane {

	public TeaJSplitPane(boolean horizontal, Component leftOrTopComponent, Component rightOrBottomComponent, int dividerLocation) {
		super(horizontal == true ? JSplitPane.HORIZONTAL_SPLIT : JSplitPane.VERTICAL_SPLIT, true, leftOrTopComponent, rightOrBottomComponent);
		setDividerLocation(dividerLocation);
	}
	
}
