package com.tea.custom;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.tea.custom.constant.TeaConstant;
import com.tea.custom.factory.TeaJComponentFactory;

@SuppressWarnings("serial")
public class TeaJFrame extends JFrame {

	private TeaJMenuBar jMenuBar;
	private TeaJToolBar jToolBar;
	private CardLayout cardLayout;
	private TeaJPanel rightPanel, rightContentPanel, jToolBarJPanel;
	private String jLabelStates = "初始化";
	private TeaJLabel currentJLabel;
	private static boolean isFront = true;
	private boolean horizontal = true;
	
	public TeaJFrame(boolean resizable, boolean floatable, TeaJComponentFactory factory) {
		setLayout(new BorderLayout());
		cardLayout = new CardLayout();
		rightPanel = new TeaJPanel(cardLayout);
		jMenuBar = new TeaJMenuBar(cardLayout, rightPanel, factory);
		jToolBar = new TeaJToolBar(floatable);
		jToolBarJPanel = new TeaJPanel(new BorderLayout());
		int row = isFront ? TeaConstant.jLabelFrontTexts.length : TeaConstant.jLabelTexts.length;
		rightContentPanel = new TeaJPanel(new GridLayout(row, 1, 0, 40));
		for (int i = 0; i < row; i++) {
			TeaJLabel jLabel = new TeaJLabel();
			jLabel.setText(isFront ? TeaConstant.jLabelFrontTexts[i] : TeaConstant.jLabelTexts[i]);
			jLabel.setIcon(isFront ? TeaConstant.jLabelFrontIcons[i] : TeaConstant.jLabelIcons[i]);
			jLabel.setForeground(TeaConstant.DARK_GRAY);
			rightContentPanel.add(jLabel);
			jLabel.addMouseListener(new MouseAdapter() {
				
				public void mouseClicked(MouseEvent e) {
					if (!jLabelStates.equals(jLabel.getText())) {
						rightPanel.removeAll();
						rightPanel.add(factory.createComponent(jLabel.getText()), "rightPanel");
						cardLayout.show(rightPanel, "rightPanel");
						currentJLabel.setText(jLabel.getText());
					}
				}
				
				public void mousePressed(MouseEvent e) {
					jLabelStates = currentJLabel.getText();
				}
				
				public void mouseReleased(MouseEvent e) {
					jLabelStates = currentJLabel.getText();
				}
			});
		}

		setJMenuBar(jMenuBar);
		jToolBarJPanel.add(jToolBar, BorderLayout.WEST);
		add(jToolBarJPanel, BorderLayout.NORTH);
		rightPanel.add(new TeaJPanel(), "rightPanel");
		JPanel jPanel = new JPanel(new BorderLayout());
		jPanel.add(new JScrollPane(rightContentPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		add(new TeaJSplitPane(horizontal, jPanel, new JScrollPane(rightPanel), 220));

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(TeaConstant.SYSTEM_WIDTH, TeaConstant.SYSTEM_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(TeaConstant.SYSTEM_TITLE);
		setLocation((screenSize.width - TeaConstant.SYSTEM_WIDTH) >> 1, (screenSize.height - TeaConstant.SYSTEM_HEIGHT) >> 1);
		setResizable(resizable);
		setIconImage(TeaConstant.SYSTEM_IMAGE);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setVisible(true);
	}
	
}
