package com.tea.custom;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import com.tea.custom.utils.TeaUtils;

@SuppressWarnings("serial")
public class TeaJPanel extends JPanel {
	
	private TeaJPanelDecorate decorate;
	private Color simpleColor;
	private Color firstColor;
	private Color lastColor;
	private String imageKey;
	
	public TeaJPanel() {
		super();
	}
	
	public TeaJPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}
	
	public TeaJPanel(LayoutManager manager) {
		super(manager);
	}
	
	public TeaJPanel(LayoutManager manager, boolean isDoubleBuffered) {
		super(manager, isDoubleBuffered);
	}
	
	public TeaJPanelDecorate getDecorate() {
		return decorate == null ? TeaJPanelDecorate.BACKAGE_IMAGE : decorate;
	}

	public void setDecorate(TeaJPanelDecorate decorate) {
		this.decorate = decorate;
	}
	
	public Color getSimpleColor() {
		return simpleColor == null ? getLastColor() : simpleColor;
	}

	public void setSimpleColor(Color simpleColor) {
		this.simpleColor = simpleColor;
	}

	public Color getFirstColor() {
		return firstColor == null ? new Color(57, 181, 215) : firstColor;
	}

	public void setFirstColor(Color firstColor) {
		this.firstColor = firstColor;
	}

	public Color getLastColor() {
		return lastColor == null ? new Color(145, 232, 255) : lastColor;
	}

	public void setLastColor(Color lastColor) {
		this.lastColor = lastColor;
	}

	public String getImageKey() {
		return imageKey == null ? "background" : imageKey;
	}

	public void setImageKey(String imageKey) {
		this.imageKey = imageKey;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		switch (getDecorate()) {
		case BACKAGE_IMAGE:
			g2d.drawImage(TeaUtils.getImage(getImageKey()), 0, 0, getWidth(), getHeight(), null);
			break;
		case SIMPLE_COLOR:
			g2d.setColor(getSimpleColor());
			g2d.fillRect(0, 0, getWidth(), getHeight());
			break;
		case GRADIENT_CYCLIC_COLOR:
			GradientPaint gradient = new GradientPaint(0, 0, getFirstColor(), getWidth()>>1, getHeight()>>1, getLastColor(), true);
			g2d.setPaint(gradient);
			g2d.fillRect(0, 0, getWidth(), getHeight());
			break;
		case GRADIENT_NO_CYCLIC_COLOR:
			gradient = new GradientPaint(0, 0, getFirstColor(), getWidth()>>1, getHeight()>>1, getLastColor(), false);
			g2d.setPaint(gradient);
			g2d.fillRect(0, 0, getWidth(), getHeight());
			break;
		}
	}
	
}
