package com.tea.custom;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class TeaJTextField extends JTextField {

	public TeaJTextField(String prompt) {
		setText(prompt);
		addFocusListener(new FocusAdapter() {
			
			public void focusGained(FocusEvent e) {
				if (getText().equals(prompt))
					setText(null);
			}
			
			public void focusLost(FocusEvent e) {
				if (getText().trim().isEmpty())
					setText(prompt);
			}
		});
	}

}
