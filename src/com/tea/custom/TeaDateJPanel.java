package com.tea.custom;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.AncestorEvent;

import com.tea.custom.adapter.AncestorAdapter;
import com.tea.custom.constant.TeaConstant;

@SuppressWarnings("serial")
public class TeaDateJPanel extends TeaJPanel {
	
	private TeaDateHeadJPanel dateHeadJPanel;
	private TeaDateTipJPanel dateTipJPanel;
	private TeaDateBodyJPanel dateBodyJPanel;
	private TeaDateFooterJPanel dateFooterJPanel;
	private Date currentDate;
	private Calendar selectCalendar;
	private JPanel dateJPanel;
	private TeaJLabelManager manager;
	private JLabel showDate;
	private SimpleDateFormat sdf;
	private boolean isShow = false;
	private boolean autoClose = false;
	private Popup pop;
	private int width = 450;
	
	public TeaDateJPanel() {
		this(false);
	}
	
	public TeaDateJPanel(boolean autoClose) {
		this(new Date(), autoClose);
	}
	
	public TeaDateJPanel(Date date, boolean autoClose) {
		currentDate = date;
		selectCalendar = Calendar.getInstance();
		selectCalendar.setTime(currentDate);
		manager = new TeaJLabelManager();
		sdf = new SimpleDateFormat("yyyy年MM月dd日");
		this.autoClose = autoClose;
		initJPanel();
		initJLabel();
    }
	
	public void setEnabled(boolean b) {
		super.setEnabled(b);
		showDate.setEnabled(b);
    }
	
	public Date getDate() {
		return selectCalendar.getTime();
    }
	
	private void initJPanel() {
		dateJPanel = new JPanel(new BorderLayout());
		dateJPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		JPanel jPanel = new JPanel(new BorderLayout());
		dateHeadJPanel = new TeaDateHeadJPanel();
		dateTipJPanel = new TeaDateTipJPanel();
		dateBodyJPanel = new TeaDateBodyJPanel();
		dateFooterJPanel = new TeaDateFooterJPanel();
		jPanel.add(dateHeadJPanel,BorderLayout.NORTH);
		jPanel.add(dateTipJPanel, BorderLayout.CENTER);
		dateJPanel.add(jPanel, BorderLayout.NORTH);
		dateJPanel.add(dateBodyJPanel, BorderLayout.CENTER);
		dateJPanel.add(dateFooterJPanel, BorderLayout.SOUTH);
		if (autoClose) {
			addMouseMotionListener(new MouseAdapter() {
				public void mouseMoved(MouseEvent e) {
					Point location = showDate.getLocationOnScreen();
					Point mouse = e.getLocationOnScreen();
			    	if (mouse.x < location.x || mouse.x > location.x + showDate.getWidth() || mouse.getY() < location.y || mouse.getY() > location.y + showDate.getHeight()) {
						if (isShow) {
							try {
								Thread.sleep(1000);
								hideJPanel();
							} catch (InterruptedException exception) {
								hideJPanel();
							}
						}
					}
				}
			});
		}
		addAncestorListener(new AncestorAdapter() {
			public void ancestorMoved(AncestorEvent event) {
                hideJPanel();
            }
        });
    }
	
	private void initJLabel() {
		showDate = new JLabel(sdf.format(currentDate));
		showDate.setRequestFocusEnabled(true);
        showDate.addMouseListener(new MouseAdapter() {
        	public void mousePressed(MouseEvent e) {
        		showDate.requestFocusInWindow();
        	}
        });
        this.setBackground(Color.WHITE);
        this.add(showDate, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(190, 125));
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        showDate.addMouseListener(new MouseAdapter() {
        	public void mouseEntered(MouseEvent e) {
        		if(showDate.isEnabled()){
        			showDate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        			showDate.setForeground(Color.RED);
        		}
        	}
        	public void mouseExited(MouseEvent e) {
        		if(showDate.isEnabled()) {
        			showDate.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        			showDate.setForeground(Color.BLACK);
        		}
        	}
        	public void mousePressed(MouseEvent e) {
        		if(showDate.isEnabled()) {
        			showDate.setForeground(Color.CYAN);
        			if(isShow) {
        				hideJPanel();
        			} else {
        				showPanel(showDate);
        			}
        		}
        	}
        	public void mouseReleased(MouseEvent e) {
        		if(showDate.isEnabled())
        			showDate.setForeground(Color.BLACK);
            }
        });
        showDate.addFocusListener(new FocusAdapter() {
        	public void focusLost(FocusEvent e) {
        		hideJPanel();
        	}
        });
    }
	
	private void refresh() {
		dateHeadJPanel.updateDate();
		dateBodyJPanel.updateDate();
		SwingUtilities.updateComponentTreeUI(this);
    }
	
	private void commit() {
		showDate.setText(sdf.format(selectCalendar.getTime()));
		hideJPanel();
    }
	
	private void hideJPanel() {
		if(pop!=null) {
			isShow=false;
			pop.hide();
			pop=null;
		}
    }
	
    private void showPanel(Component owner) {
    	if(pop!=null)
    		pop.hide();
    	Point show = new Point(0, showDate.getHeight());
    	SwingUtilities.convertPointToScreen(show, showDate);
    	Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    	int x = show.x;
    	int y = show.y;
    	if(x<0) x = 10;
        if(x>size.width-width) x = size.width - width - 10;
        if (y<0) y = 10;
        if(y>=size.height-220) y -= 250;
        pop=PopupFactory.getSharedInstance().getPopup(owner, dateJPanel, x, y);
        pop.show();
        isShow=true;
    }
    
    private class TeaDateHeadJPanel extends JPanel {
    	JLabel left,right,center;
    	public TeaDateHeadJPanel() {
    		super(new BorderLayout());
    		setBackground(new Color(160, 185, 215));
    		left = new JLabel("  <<", JLabel.CENTER);
    		left.setToolTipText("上一月");
    		right=new JLabel(">>  ", JLabel.CENTER);
    		right.setToolTipText("下一月");
    		left.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    		right.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    		center=new JLabel("", JLabel.CENTER);
    		updateDate();
    		add(left, BorderLayout.WEST);
    		add(center, BorderLayout.CENTER);
    		add(right, BorderLayout.EAST);
    		setPreferredSize(new Dimension(width, 30));
            left.addMouseListener(new TeaMouseAdapter(left));
            right.addMouseListener(new TeaMouseAdapter(right));
        }
        private void updateDate() {
            center.setText(selectCalendar.get(Calendar.YEAR) + "年" + (selectCalendar.get(Calendar.MONTH) + 1) + "月");
        }
        private class TeaMouseAdapter extends MouseAdapter {
        	private JLabel jLabel;
        	public TeaMouseAdapter(JLabel jLabel) {
        		this.jLabel = jLabel;
			}
        	public void mouseEntered(MouseEvent e) {
        		jLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                jLabel.setForeground(Color.RED);
            }
            public void mouseExited(MouseEvent e) {
            	jLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            	jLabel.setForeground(Color.BLACK);
            }
            public void mousePressed(MouseEvent e) {
            	selectCalendar.add(Calendar.MONTH, jLabel == left ? -1 : 1);
                left.setForeground(Color.WHITE);
                refresh();
            }
            public void mouseReleased(MouseEvent e) {
                left.setForeground(Color.BLACK);
            }
        }
    }
    
    private class TeaDateTipJPanel extends JPanel {
    	public TeaDateTipJPanel() {
    		setPreferredSize(new Dimension(width, 30));
    	}
    	protected void paintComponent(Graphics g) {
    		g.drawLine(0, 25, width, 25);
    		Graphics2D g2d = (Graphics2D) g;
    		g2d.setFont(TeaConstant.DATE_JPANEL_FONT);
			BasicStroke bs = new BasicStroke(1f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER);
			g2d.setStroke(bs);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    		g2d.drawString("星期日 星期一 星期二 星期三  星期四 星期五 星期六", 5, 20);
        }
    }
    
    private class TeaDateBodyJPanel extends JPanel {
    	public TeaDateBodyJPanel() {
    		super(new GridLayout(6, 7));
    		setPreferredSize(new Dimension(width, 120));
    		updateDate();
        }
        public void updateDate() {
            this.removeAll();
            manager.clear();
            Date temp = selectCalendar.getTime();
            Calendar select = Calendar.getInstance();
            select.setTime(temp);
            select.set(Calendar.DAY_OF_MONTH, 1);
            int index=select.get(Calendar.DAY_OF_WEEK);
            int sum=(index==1?8:index);
            select.add(Calendar.DAY_OF_MONTH, 0-sum);
            for(int i=0;i<42;i++){
                select.add(Calendar.DAY_OF_MONTH, 1);
                manager.addLabel(new TeaJLabel(select.get(Calendar.YEAR), select.get(Calendar.MONTH),select.get(Calendar.DAY_OF_MONTH)));
            }
            for(TeaJLabel jLabel : manager.getTeaJLabels()) {
                jLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            	this.add(jLabel);
            }
            select.setTime(temp);
        }
    }
    
    private class TeaJLabel extends JLabel implements Comparator<TeaJLabel>, MouseListener, MouseMotionListener {
    	private int year,month,day;
    	private boolean isSelected;
    	public TeaJLabel(int year, int month, int day) {
    		super(String.valueOf(day), JLabel.CENTER);
    		this.year=year;
    		this.day=day;
    		this.month=month;
    		this.addMouseListener(this);
    		this.addMouseMotionListener(this);
    		this.setFont(TeaConstant.DATE_JPANEL_FONT);
    		if(month == selectCalendar.get(Calendar.MONTH)) {
    			this.setForeground(Color.BLACK);
    		} else {
    			this.setForeground(Color.LIGHT_GRAY);
    		}
    		if(day==selectCalendar.get(Calendar.DAY_OF_MONTH)) {
    			this.setBackground(new Color(160,185,215));
    		} else {
    			this.setBackground(Color.WHITE);
    		}
    	}
    	public boolean getIsSelected() {
    		return isSelected;
    	}
    	public void setSelected(boolean b, boolean isDrag) {
    		isSelected=b;
    		if(b&&!isDrag) {
    			selectCalendar.set(year, month, day);
    			if(selectCalendar.get(Calendar.MONTH) == month) {
    				SwingUtilities.updateComponentTreeUI(dateBodyJPanel);
    			} else {
    				refresh();
    			}
    		}
    		this.repaint();
        }
        protected void paintComponent(Graphics g){
            if(day==selectCalendar.get(Calendar.DAY_OF_MONTH)&&month==selectCalendar.get(Calendar.MONTH)) {
            	g.setColor(new Color(160, 185, 215));
            	g.fillRect(0,0,getWidth(),getHeight());
            }
            Calendar calendar = Calendar.getInstance();
            if(year==calendar.get(Calendar.YEAR)&&month==calendar.get(Calendar.MONTH)&&day==calendar.get(Calendar.DAY_OF_MONTH)) {
            	Graphics2D gd=(Graphics2D)g;
            	gd.setColor(Color.RED);
            	Polygon p=new Polygon();
            	p.addPoint(0,0);
            	p.addPoint(getWidth()-1,0);
            	p.addPoint(getWidth()-1,getHeight()-1);
            	p.addPoint(0,getHeight()-1);
            	gd.drawPolygon(p);
            }
            if(isSelected) {
            	Stroke s=new BasicStroke(1.0f,BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL,1.0f,new float[]{2.0f,2.0f},1.0f);
            	Graphics2D gd=(Graphics2D)g;
            	gd.setStroke(s);
            	gd.setColor(Color.BLACK);
            	Polygon p=new Polygon();
            	p.addPoint(0,0);
            	p.addPoint(getWidth()-1,0);
            	p.addPoint(getWidth()-1,getHeight()-1);
            	p.addPoint(0,getHeight()-1);
            	gd.drawPolygon(p);
            }
            super.paintComponent(g);
        }
        public boolean contains(Point p) {
        	return this.getBounds().contains(p);
        }
        private void update() {
        	repaint();
        }
        public void mouseClicked(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {
        	isSelected=true;
        	update();
        }
        public void mouseReleased(MouseEvent e) {
            manager.setSelect(SwingUtilities.convertPoint(this, e.getPoint(), dateBodyJPanel), false);
            commit();
        }
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseDragged(MouseEvent e) {
        	manager.setSelect(SwingUtilities.convertPoint(this, e.getPoint(), dateBodyJPanel), true);
        }
        public void mouseMoved(MouseEvent e) {}
        public int compare(TeaJLabel o1, TeaJLabel o2) {
        	Calendar c1 = Calendar.getInstance();
        	c1.set(o1.year, o1.month, o1.day);
        	Calendar c2=Calendar.getInstance();
        	c2.set(o2.year, o2.month, o2.day);
        	return c1.compareTo(c2);
        }
    }
	
	private class TeaJLabelManager {
		private List<TeaJLabel> lists;
		public TeaJLabelManager() {
			lists = new ArrayList<TeaJLabel>();
		}
		public List<TeaJLabel> getTeaJLabels() {
			return lists;
		}
		public void addLabel(TeaJLabel jLabel) {
			lists.add(jLabel);
		}
		public void clear() {
			lists.clear();
		}
		@SuppressWarnings("unused")
		public void setSelect(TeaJLabel jLabel, boolean b) {
			for(TeaJLabel teaJLabel : lists) {
				if(teaJLabel.equals(jLabel)) {
					teaJLabel.setSelected(true, b);
				} else {
					teaJLabel.setSelected(false, b);
				}
			}
		}
		public void setSelect(Point point, boolean b) {
			if(b) {
				boolean findPrevious = false,findNext = false;
				for(TeaJLabel teaJLabel : lists) {
					if(teaJLabel.contains(point)) {
						findNext = true;
						if(teaJLabel.getIsSelected()) {
							findPrevious = true;
						} else {
							teaJLabel.setSelected(true, b);
                        }
					} else if(teaJLabel.getIsSelected()) {
						findPrevious = true;
						teaJLabel.setSelected(false, b);
					}
					if(findPrevious && findNext)
						return;
				}
			} else {
				TeaJLabel temp = null;
				for(TeaJLabel teaJLabel : lists) {
					if(teaJLabel.contains(point)) {
						temp = teaJLabel;
					} else if(teaJLabel.getIsSelected()) {
						teaJLabel.setSelected(false, b);
					}
				}
				if(temp != null) {
					temp.setSelected(true, b);
				}
			}
		}
	}
    
    private class TeaDateFooterJPanel extends JPanel {
    	public TeaDateFooterJPanel() {
    		setPreferredSize(new Dimension(width, 40));
    		setBackground(new Color(160, 185, 215));
    		JLabel jLabel = new JLabel("今天: " + sdf.format(new Date()), SwingConstants.CENTER);
    		jLabel.setToolTipText("点击回到今天");
    		add(jLabel);
    		jLabel.addMouseListener(new MouseAdapter() {
    			public void mouseEntered(MouseEvent e) {
    				jLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    				jLabel.setForeground(Color.RED);
    			}
    			public void mouseExited(MouseEvent e) {
    				jLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    				jLabel.setForeground(Color.BLACK);
    			}
    			public void mousePressed(MouseEvent e) {
    				jLabel.setForeground(Color.WHITE);
    				selectCalendar.setTime(new Date());
    				refresh();
    				commit();
    			}
    			public void mouseReleased(MouseEvent e) {
    				jLabel.setForeground(Color.BLACK);
    			}
    		});
    	}
    }
}