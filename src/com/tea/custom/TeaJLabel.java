package com.tea.custom;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class TeaJLabel extends JLabel {

	public TeaJLabel() {
		this("", false);
	}
	
	public TeaJLabel(String text) {
		this(text, false);
	}
	
	public TeaJLabel(Icon defaultIcon) {
		super(defaultIcon, SwingUtilities.CENTER);
	}
	
	public TeaJLabel(String text, boolean isFront) {
		super(text, SwingUtilities.CENTER);
		if (isFront) {
			setFont(new Font("华文楷体", Font.BOLD, 20));
			setForeground(Color.WHITE);
			setVerticalAlignment(SwingConstants.CENTER);
			setVerticalTextPosition(SwingConstants.CENTER);
			setHorizontalAlignment(SwingConstants.CENTER);
			setHorizontalTextPosition(SwingConstants.CENTER);
		} else {
			setForeground(Color.BLUE);
		}
	}
	
}
