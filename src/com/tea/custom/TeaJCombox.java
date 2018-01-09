package com.tea.custom;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class TeaJCombox extends JComboBox<Object> {

	public TeaJCombox() {
	}
	
	public TeaJCombox(Object[] itmes) {
		super(itmes);
	}

	public void fillTeaJCombox(String promptText, Object[] itmes) {
		if (!promptText.trim().isEmpty() || promptText != null)
			addItem(promptText);
		for(int i=0; i<itmes.length; i++)
			addItem(itmes[i]);
	}
	
	public void fillTeaJCombox(String promptText, Object[][] itmes, Integer index) {
		if (!promptText.trim().isEmpty() || promptText != null)
			addItem(promptText);
		for(int i=0; i<itmes.length; i++)
			addItem(itmes[i][index]);
	}
	
}
