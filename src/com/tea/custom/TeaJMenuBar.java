package com.tea.custom;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JMenuBar;

import com.tea.custom.factory.TeaJComponentFactory;

@SuppressWarnings("serial")
public class TeaJMenuBar extends JMenuBar implements ActionListener {
	
	private CardLayout cardLayout;
	private JComponent jComponent;
	private TeaJComponentFactory factory;
	
	public TeaJMenuBar(CardLayout cardLayout, JComponent jComponent, TeaJComponentFactory factory) {
		this.cardLayout = cardLayout;
		this.jComponent = jComponent;
		this.factory = factory;
	}

	public void actionPerformed(ActionEvent e) {
		JComponent component = factory.createComponent("CheneyThinker");
		jComponent.add(component, component.getName());
		cardLayout.show(jComponent, component.getName());
	}

}
