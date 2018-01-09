package com.tea.custom.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.text.View;

import com.tea.custom.TeaJTabbedPane;

public class TeaTabbedPaneUI extends BasicTabbedPaneUI {

	private Rectangle[] closeRectangles = new Rectangle[0];
	private int currentIndex = -1;
	private int oldIndex = -1;
	private TeaJTabbedPane jTabbedPane;
	
	public TeaTabbedPaneUI(TeaJTabbedPane jTabbedPane) {
		super();
		this.jTabbedPane = jTabbedPane;
		init();
	}
	
	private void init() {
		UIManager.put("TabbedPane.contentAreaColor", jTabbedPane.getSouthColor());
		jTabbedPane.addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) {
				for (int i = 0; i < jTabbedPane.getTabCount(); i++) {
					if (closeRectangles[i].contains(e.getPoint()) && jTabbedPane.getCloseables().get(i)) {
						jTabbedPane.removeTabAt(i);
						jTabbedPane.getTitles().remove(i);
						jTabbedPane.getCloseables().remove(i);
					}
				}
			}
		});
		jTabbedPane.addMouseMotionListener(new MouseAdapter() {
			
			public void mouseMoved(MouseEvent e) {
				currentIndex = -1;
				for (int i = 0; i < jTabbedPane.getTabCount(); i++) {
					if (closeRectangles[i].contains(e.getPoint()) && jTabbedPane.getCloseables().get(i)) {
						currentIndex = i;
						break;
					}
				}
				if (oldIndex != currentIndex) {
					if (currentIndex != -1) {
						jTabbedPane.repaint(closeRectangles[currentIndex]);
					} else {
						if (oldIndex < jTabbedPane.getTabCount()) {
							jTabbedPane.repaint(closeRectangles[oldIndex]);
						}
					}
					oldIndex = currentIndex;
				}
			}
		});
	}
	
	protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
		Rectangle rectangle = selectedIndex < 0 ? null : getTabBounds(selectedIndex, calcRect);
		g.setColor(jTabbedPane.getBorderColor());
		if (tabPlacement != TOP || selectedIndex < 0 || rectangle.y + rectangle.height + 1 < y || (rectangle.x < x || rectangle.x > x + w)) {
			g.drawLine(x, y, x + w - 2, y);
		} else {
			g.drawLine(x, y, rectangle.x - 1, y);
			if (rectangle.x + rectangle.width < x + w - 2) {
				g.drawLine(rectangle.x + rectangle.width, y, x + w - 2, y);
			} else {
				g.drawLine(x + w - 2, y, x + w - 2, y);
			}
		}
	}

	protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
		Rectangle rectangle = selectedIndex < 0 ? null : getTabBounds(selectedIndex, calcRect);
		g.setColor(jTabbedPane.getBorderColor());
		if (tabPlacement != LEFT || selectedIndex < 0 || rectangle.x + rectangle.width + 1 < x || (rectangle.y < y || rectangle.y > y + h)) {
			g.drawLine(x, y, x, y + h - 2);
		} else {
			g.drawLine(x, y, x, rectangle.y - 1);
			if (rectangle.y + rectangle.height < y + h - 2) {
				g.drawLine(x, rectangle.y + rectangle.height, x, y + h - 2);
			}
		}
	}
	
	protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
		Rectangle rectangle = selectedIndex < 0 ? null : getTabBounds(selectedIndex, calcRect);
		g.setColor(jTabbedPane.getBorderColor());
		if (tabPlacement != BOTTOM || selectedIndex < 0 || rectangle.y - 1 > h || (rectangle.x < x || rectangle.x > x + w)) {
			g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
		} else {
			g.drawLine(x, y + h - 1, rectangle.x - 1, y + h - 1);
			if (rectangle.x + rectangle.width < x + w - 2) {
				g.drawLine(rectangle.x + rectangle.width, y + h - 1, x + w - 1, y + h - 1);
			}
		}
	}
	
	protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
		Rectangle rectangle = selectedIndex < 0 ? null : getTabBounds(selectedIndex, calcRect);
		g.setColor(jTabbedPane.getBorderColor());
		if (tabPlacement != RIGHT || selectedIndex < 0 || rectangle.x - 1 > w || rectangle.y < y || rectangle.y > y + h) {
			g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
		} else {
			g.drawLine(x + w - 1, y, x + w - 1, rectangle.y - 1);
			if (rectangle.y + rectangle.height < y + h - 2) {
				g.drawLine(x + w - 1, rectangle.y + rectangle.height, x + w - 1, y + h - 2);
			}
		}
	}
	
	protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect) {
		super.paintTab(g, tabPlacement, rects, tabIndex, iconRect, textRect);
		if (jTabbedPane.getCloseables().get(tabIndex) && tabIndex == jTabbedPane.getSelectedIndex()) {
			paintCloseIcon(g, tabIndex, tabIndex == currentIndex);
		}
	}
	
	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
		g.setColor(Color.GRAY);
		switch (tabPlacement) {
		case LEFT:
			g.drawLine(x, y, x, y + h - 1);
			g.drawLine(x, y, x + w - 1, y);
			g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
			break;
		case RIGHT:
			g.drawLine(x, y, x + w - 1, y);
			g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
			g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
			break;
		case BOTTOM:
			g.drawLine(x, y, x, y + h - 1);
			g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
			g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
			break;
		case TOP:
			g.drawLine(x, y, x, y + h - 1);
			g.drawLine(x, y, x + w - 1, y);
			g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
			break;
		}
	}
	
	protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
		GradientPaint gradient;
		Graphics2D g2d = (Graphics2D) g;
		switch (tabPlacement) {
		case LEFT:
			if (isSelected) {
				gradient = new GradientPaint(x + 1, y, jTabbedPane.getNorthColor(), x + w, y, jTabbedPane.getSouthColor(), true);
			} else {
				gradient = new GradientPaint(x + 1, y, Color.LIGHT_GRAY, x + w, y, Color.WHITE, true);
			}
			g2d.setPaint(gradient);
			g.fillRect(x + 1, y + 1, w - 1, h - 2);
			break;
		case RIGHT:
			if (isSelected) {
				gradient = new GradientPaint(x + w, y, jTabbedPane.getNorthColor(), x + 1, y, jTabbedPane.getSouthColor(), true);
			} else {
				gradient = new GradientPaint(x + w, y, Color.LIGHT_GRAY, x + 1, y, Color.WHITE, true);
			}
			g2d.setPaint(gradient);
			g.fillRect(x, y + 1, w - 1, h - 2);
			break;
		case BOTTOM:
			if (isSelected) {
				gradient = new GradientPaint(x + 1, y + h, jTabbedPane.getNorthColor(), x + 1, y, jTabbedPane.getSouthColor(), true);
			} else {
				gradient = new GradientPaint(x + 1, y + h, Color.LIGHT_GRAY, x + 1, y, Color.WHITE, true);
			}
			g2d.setPaint(gradient);
			g.fillRect(x + 1, y, w - 2, h - 1);
			break;
		case TOP:
		default:
			if (isSelected) {
				gradient = new GradientPaint(x + 1, y, jTabbedPane.getNorthColor(), x + 1, y + h, jTabbedPane.getSouthColor(), true);
			} else {
				gradient = new GradientPaint(x + 1, y, Color.LIGHT_GRAY, x + 1, y + h, Color.WHITE, true);
			}
			g2d.setPaint(gradient);
			g2d.fillRect(x + 1, y + 1, w - 2, h - 1);
		}
	}
	
	private void paintCloseIcon(Graphics g, int tabIndex, boolean entered) {
		Rectangle rect = closeRectangles[tabIndex];
		int x = rect.x;
		int y = rect.y;
		int[] xs = { x, x + 2, x + 4, x + 5, x + 7, x + 9, x + 9, x + 7, x + 7, x + 9, x + 9, x + 7, x + 5, x + 4, x + 2, x, x, x + 2, x + 2, x };
		int[] ys = { y, y, y + 2, y + 2, y, y, y + 2, y + 4, y + 5, y + 7, y + 9, y + 9, y + 7, y + 7, y + 9, y + 9, y + 7, y + 5, y + 4, y + 2 };
		if (entered) {
			g.setColor(new Color(252, 160, 160));
		} else {
			g.setColor(Color.WHITE);
		}
		g.fillPolygon(xs, ys, 20);
		g.setColor(Color.DARK_GRAY);
		g.drawPolygon(xs, ys, 20);
	}

	protected void layoutLabel(int tabPlacement, FontMetrics metrics, int tabIndex, String title, Icon icon, Rectangle tabRect, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
		textRect.x = textRect.y = iconRect.x = iconRect.y = 0;
		View v = getTextViewForTab(tabIndex);
		if (v != null) {
			tabPane.putClientProperty("html", v);
		}
		SwingUtilities.layoutCompoundLabel((JComponent) tabPane, metrics, title, icon, SwingUtilities.CENTER, SwingUtilities.CENTER, SwingUtilities.CENTER, SwingUtilities.TRAILING, tabRect, iconRect, textRect, textIconGap);
		tabPane.putClientProperty("html", null);
	}

	protected LayoutManager createLayoutManager() {
		return new TabbedPaneLayout();
	}

	protected void assureRectsCreated(int tabCount) {
		super.assureRectsCreated(tabCount);
		int rectArrayLen = closeRectangles.length;
		if (tabCount != rectArrayLen) {
			Rectangle[] tempRectArray = new Rectangle[tabCount];
			System.arraycopy(closeRectangles, 0, tempRectArray, 0, Math.min(rectArrayLen, tabCount));
			closeRectangles = tempRectArray;
			for (int rectIndex = rectArrayLen; rectIndex < tabCount; rectIndex++) {
				closeRectangles[rectIndex] = new Rectangle();
			}
		}
	}

	private class TabbedPaneLayout extends BasicTabbedPaneUI.TabbedPaneLayout {
		
		protected void calculateTabRects(int tabPlacement, int tabCount) {
			FontMetrics metrics = getFontMetrics();
			Dimension size = tabPane.getSize();
			Insets insets = tabPane.getInsets();
			Insets tabAreaInsets = getTabAreaInsets(tabPlacement);
			int fontHeight = metrics.getHeight();
			int selectedIndex = tabPane.getSelectedIndex();
			int tabRunOverlay;
			int i, j;
			int x, y;
			int returnAt;
			boolean verticalTabRuns = (tabPlacement == LEFT || tabPlacement == RIGHT);
			boolean leftToRight = true;
			switch (tabPlacement) {
			case LEFT:
				maxTabWidth = calculateMaxTabWidth(tabPlacement) + 20;
				x = insets.left + tabAreaInsets.left;
				y = insets.top + tabAreaInsets.top;
				returnAt = size.height - (insets.bottom + tabAreaInsets.bottom);
				break;
			case RIGHT:
				maxTabWidth = calculateMaxTabWidth(tabPlacement) + 20;
				x = size.width - insets.right - tabAreaInsets.right - maxTabWidth;
				y = insets.top + tabAreaInsets.top;
				returnAt = size.height - (insets.bottom + tabAreaInsets.bottom);
				break;
			case BOTTOM:
				maxTabHeight = calculateMaxTabHeight(tabPlacement);
				x = insets.left + tabAreaInsets.left;
				y = size.height - insets.bottom - tabAreaInsets.bottom - maxTabHeight;
				returnAt = size.width - (insets.right + tabAreaInsets.right);
				break;
			case TOP:
			default:
				maxTabHeight = calculateMaxTabHeight(tabPlacement);
				x = insets.left + tabAreaInsets.left;
				y = insets.top + tabAreaInsets.top;
				returnAt = size.width - (insets.right + tabAreaInsets.right);
				break;
			}
			tabRunOverlay = getTabRunOverlay(tabPlacement);
			runCount = 0;
			selectedRun = -1;
			if (tabCount == 0) {
				return;
			}
			selectedRun = 0;
			runCount = 1;
			Rectangle rect;
			for (i = 0; i < tabCount; i++) {
				rect = rects[i];
				if (!verticalTabRuns) {
					if (i > 0) {
						rect.x = rects[i - 1].x + rects[i - 1].width;
					} else {
						tabRuns[0] = 0;
						runCount = 1;
						maxTabWidth = 0;
						rect.x = x;
					}
					rect.width = calculateTabWidth(tabPlacement, i, metrics) + 20;
					maxTabWidth = Math.max(maxTabWidth, rect.width);
					if (rect.x != 2 + insets.left && rect.x + rect.width > returnAt) {
						if (runCount > tabRuns.length - 1) {
							expandTabRunsArray();
						}
						tabRuns[runCount] = i;
						runCount++;
						rect.x = x;
					}
					rect.y = y;
					rect.height = maxTabHeight;
				} else {
					if (i > 0) {
						rect.y = rects[i - 1].y + rects[i - 1].height;
					} else {
						tabRuns[0] = 0;
						runCount = 1;
						maxTabHeight = 0;
						rect.y = y;
					}
					rect.height = calculateTabHeight(tabPlacement, i, fontHeight);
					maxTabHeight = Math.max(maxTabHeight, rect.height);
					if (rect.y != 2 + insets.top && rect.y + rect.height > returnAt) {
						if (runCount > tabRuns.length - 1) {
							expandTabRunsArray();
						}
						tabRuns[runCount] = i;
						runCount++;
						rect.y = y;
					}
					rect.x = x;
					rect.width = maxTabWidth/* - 2 */;
				}
				if (i == selectedIndex) {
					selectedRun = runCount - 1;
				}
			}
			if (runCount > 1) {
				normalizeTabRuns(tabPlacement, tabCount, verticalTabRuns ? y : x, returnAt);
				selectedRun = getRunForTab(tabCount, selectedIndex);
				if (shouldRotateTabRuns(tabPlacement)) {
					rotateTabRuns(tabPlacement, selectedRun);
				}
			}
			for (i = runCount - 1; i >= 0; i--) {
				int start = tabRuns[i];
				int next = tabRuns[i == (runCount - 1) ? 0 : i + 1];
				int end = (next != 0 ? next - 1 : tabCount - 1);
				if (!verticalTabRuns) {
					for (j = start; j <= end; j++) {
						rect = rects[j];
						rect.y = y;
						rect.x += getTabRunIndent(tabPlacement, i);
					}
					if (shouldPadTabRun(tabPlacement, i)) {
						padTabRun(tabPlacement, start, end, returnAt);
					}
					if (tabPlacement == BOTTOM) {
						y -= (maxTabHeight - tabRunOverlay);
					} else {
						y += (maxTabHeight - tabRunOverlay);
					}
				} else {
					for (j = start; j <= end; j++) {
						rect = rects[j];
						rect.x = x;
						rect.y += getTabRunIndent(tabPlacement, i);
					}
					if (shouldPadTabRun(tabPlacement, i)) {
						padTabRun(tabPlacement, start, end, returnAt);
					}
					if (tabPlacement == RIGHT) {
						x -= (maxTabWidth - tabRunOverlay);
					} else {
						x += (maxTabWidth - tabRunOverlay);
					}
				}
			}
			if (!leftToRight && !verticalTabRuns) {
				int rightMargin = size.width
						- (insets.right + tabAreaInsets.right);
				for (i = 0; i < tabCount; i++) {
					rects[i].x = rightMargin - rects[i].x - rects[i].width;
				}
			}
			for (i = 0; i < tabCount; i++) {
				closeRectangles[i].x = rects[i].x + rects[i].width - 14;
				closeRectangles[i].y = rects[i].y + 6;
				closeRectangles[i].width = 10;
				closeRectangles[i].height = 10;
			}
		}
	}

}
