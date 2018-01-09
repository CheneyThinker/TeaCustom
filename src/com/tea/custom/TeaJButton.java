package com.tea.custom;

import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import com.tea.custom.constant.TeaConstant;
import com.tea.custom.utils.TeaUtils;

@SuppressWarnings("serial")
public class TeaJButton extends JButton {

	public TeaJButton(String text) {
		super(text);
		setFont(TeaConstant.TITLE_FONT);
		setForeground(TeaConstant.DARK_GRAY);
		setHorizontalTextPosition(SwingUtilities.CENTER);
		setVerticalTextPosition(SwingUtilities.CENTER);
		setFocusPainted(false);
		setMargin(new Insets(0, 0, 0, 0));
		setContentAreaFilled(false);
		setBorderPainted(false);
		setIcon(TeaUtils.getIcon("btn_default"));
		setRolloverIcon(TeaUtils.getIcon("btn_rollover"));
		setPressedIcon(TeaUtils.getIcon("btn_pressed"));
	}

}
