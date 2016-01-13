package com.pub.comp;


import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.border.*;
/**
 * <p>Title: 日历</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

//
public class NaturalCalendar extends JDialog implements AdjustmentListener {
	static ResourceBundle res = ResourceBundle.getBundle("com.pansoft.pub.comp.Language");
  public GregorianCalendar time = null;
	public Color foreColor = null;
	public Color backColor = null;
	public JLabel currentLabel = null;
	public JLabel[] weeks = null;
	public JLabel[][] days = null;//自定义
	private JPanel ivjJControllerPanel = null;
	private FlowLayout ivjJControllerPanelFlowLayout = null;
	private JPanel ivjJFrameContentPane = null;
	private JLabel ivjJMonthLabel = null;
	private JScrollBar ivjJMonthScrollBar = null;
	private JTextField ivjJMonthTextField = null;
	private JLabel ivjJYearLabel = null;
	private JScrollBar ivjJYearScrollBar = null;
	private JTextField ivjJYearTextField = null;
	private JPanel ivjJDaysPanel = null;
	private GridLayout ivjJDaysPanelGridLayout = null;
	private JPanel ivjJWeeksPanel = null;
	private GridLayout ivjJWeeksPanelGridLayout = null;
	private JPanel ivjJMainPanel = null;
	private JLabel ivjJOkLabel = null;
	private BorderLayout ivjJFrameContentPaneBorderLayout = null;
	private BorderLayout ivjJMainPanelBorderLayout = null;
/**
 * Constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public NaturalCalendar() {
	super();
	initialize();
}
/**
 * TestDialog constructor comment.
 * @param owner java.awt.Frame
 */
public NaturalCalendar(java.awt.Frame owner) {
	super(owner);
	initialize();
}
/**
 * TestDialog constructor comment.
 * @param owner java.awt.Frame
 * @param title java.lang.String
 */
public NaturalCalendar(java.awt.Frame owner, String title) {
	super(owner, title);
	initialize();
}
/**
 * TestDialog constructor comment.
 * @param owner java.awt.Frame
 * @param title java.lang.String
 * @param modal boolean
 */
public NaturalCalendar(java.awt.Frame owner, String title, boolean modal) {
	super(owner, title, modal);
	initialize();
}
/**
 * TestDialog constructor comment.
 * @param owner java.awt.Frame
 * @param modal boolean
 */
public NaturalCalendar(java.awt.Frame owner, boolean modal) {
	super(owner, modal);
	initialize();
}
/**
 * Method to handle events for the AdjustmentListener interface.
 * @param e java.awt.event.AdjustmentEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void adjustmentValueChanged(AdjustmentEvent e) {
	// user code begin {1}
	// user code end
	if ((e.getSource() == getJYearScrollBar()) ) {
		connEtoM2(e);
	}
	if ((e.getSource() == getJMonthScrollBar()) ) {
		connEtoM3(e);
	}
	// user code begin {2}
	// user code end
}
/**
 * connEtoM2:  (JYearScrollBar.adjustment.adjustmentValueChanged(java.awt.event.AdjustmentEvent) --> JYearTextField.repaint()V)
 * @param arg1 java.awt.event.AdjustmentEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoM2(AdjustmentEvent arg1) {
	try {
		// user code begin {1}
		int year = Integer.parseInt(getJYearTextField().getText());
		int value = getJYearScrollBar().getValue();
		getJYearTextField().setText(String.valueOf(value));
		// user code end
		getJYearTextField().repaint();
		// user code begin {2}
		try
		{
			int sub = value - year;
			time.add(time.YEAR,-sub);
			initDATA();
		}
		catch(Exception ex)
		{
			//System.out.println(ex);
		}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoM3:  (JMonthScrollBar.adjustment.adjustmentValueChanged(java.awt.event.AdjustmentEvent) --> JMonthTextField.repaint()V)
 * @param arg1 java.awt.event.AdjustmentEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoM3(AdjustmentEvent arg1) {
	try {
		// user code begin {1}
		int month = Integer.parseInt(getJMonthTextField().getText());
		int value = getJMonthScrollBar().getValue();
		// user code end
		getJMonthTextField().repaint();
		// user code begin {2}
		try
		{
			int sub = value - month;
			time.add(time.MONTH,-sub);
			initDATA();
		}
		catch(Exception ex)
		{
			//System.out.println(ex);
		}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void dispose() {
	super.dispose();
//	System.exit(0);
}
/**
 * Return the JControllerPanel property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private JPanel getJControllerPanel() {
	if (ivjJControllerPanel == null) {
		try {
			ivjJControllerPanel = new javax.swing.JPanel();
			ivjJControllerPanel.setName("JControllerPanel");
			ivjJControllerPanel.setLayout(getJControllerPanelFlowLayout());
			getJControllerPanel().add(getJYearTextField(), getJYearTextField().getName());
			getJControllerPanel().add(getJYearScrollBar(), getJYearScrollBar().getName());
			getJControllerPanel().add(getJYearLabel(), getJYearLabel().getName());
			getJControllerPanel().add(getJMonthTextField(), getJMonthTextField().getName());
			getJControllerPanel().add(getJMonthScrollBar(), getJMonthScrollBar().getName());
			getJControllerPanel().add(getJMonthLabel(), getJMonthLabel().getName());
			getJControllerPanel().add(getJOkLabel(), getJOkLabel().getName());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjJControllerPanel;
}
/**
 * Return the JControllerPanelFlowLayout property value.
 * @return java.awt.FlowLayout
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private FlowLayout getJControllerPanelFlowLayout() {
	java.awt.FlowLayout ivjJControllerPanelFlowLayout = null;
	try {
		/* Create part */
		ivjJControllerPanelFlowLayout = new java.awt.FlowLayout();
		ivjJControllerPanelFlowLayout.setAlignment(java.awt.FlowLayout.CENTER);
		ivjJControllerPanelFlowLayout.setVgap(1);
		ivjJControllerPanelFlowLayout.setHgap(1);
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	};
	return ivjJControllerPanelFlowLayout;
}
/**
 * Return the JDayPanel property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private JPanel getJDaysPanel() {
	if (ivjJDaysPanel == null) {
		try {
			ivjJDaysPanel = new javax.swing.JPanel();
			ivjJDaysPanel.setName("JDaysPanel");
			ivjJDaysPanel.setLayout(getJDaysPanelGridLayout());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjJDaysPanel;
}
/**
 * Return the JDaysPanelGridLayout property value.
 * @return java.awt.GridLayout
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private GridLayout getJDaysPanelGridLayout() {
	java.awt.GridLayout ivjJDaysPanelGridLayout = null;
	try {
		/* Create part */
		ivjJDaysPanelGridLayout = new java.awt.GridLayout(6, 7);
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	};
	return ivjJDaysPanelGridLayout;
}
/**
 * Return the JFrameContentPane property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private JPanel getJFrameContentPane() {
	if (ivjJFrameContentPane == null) {
		try {
			ivjJFrameContentPane = new javax.swing.JPanel();
			ivjJFrameContentPane.setName("JFrameContentPane");
			ivjJFrameContentPane.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
			//ivjJFrameContentPane.setBorder(BorderFactory.createLoweredBevelBorder());
			ivjJFrameContentPane.setLayout(getJFrameContentPaneBorderLayout());
			getJFrameContentPane().add(getJControllerPanel(), "North");
			getJFrameContentPane().add(getJMainPanel(), "Center");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjJFrameContentPane;
}
/**
 * Return the JFrameContentPaneBorderLayout property value.
 * @return java.awt.BorderLayout
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private BorderLayout getJFrameContentPaneBorderLayout() {
	java.awt.BorderLayout ivjJFrameContentPaneBorderLayout = null;
	try {
		/* Create part */
		ivjJFrameContentPaneBorderLayout = new java.awt.BorderLayout();
		ivjJFrameContentPaneBorderLayout.setVgap(4);
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	};
	return ivjJFrameContentPaneBorderLayout;
}
/**
 * Return the JMainPanel property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private JPanel getJMainPanel() {
	if (ivjJMainPanel == null) {
		try {
			ivjJMainPanel = new javax.swing.JPanel();
			ivjJMainPanel.setName("JMainPanel");
			ivjJMainPanel.setLayout(getJMainPanelBorderLayout());
			getJMainPanel().add(getJWeeksPanel(), "North");
			getJMainPanel().add(getJDaysPanel(), "Center");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjJMainPanel;
}
/**
 * Return the JMainPanelBorderLayout property value.
 * @return java.awt.BorderLayout
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private BorderLayout getJMainPanelBorderLayout() {
	java.awt.BorderLayout ivjJMainPanelBorderLayout = null;
	try {
		/* Create part */
		ivjJMainPanelBorderLayout = new java.awt.BorderLayout();
		ivjJMainPanelBorderLayout.setVgap(2);
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	};
	return ivjJMainPanelBorderLayout;
}
/**
 * Return the JMonthLabel property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private JLabel getJMonthLabel() {
	if (ivjJMonthLabel == null) {
		try {
			ivjJMonthLabel = new javax.swing.JLabel();
			ivjJMonthLabel.setName("JMonthLabel");
			ivjJMonthLabel.setPreferredSize(new java.awt.Dimension(30, 20));
			ivjJMonthLabel.setText(res.getString("String_35"));
			ivjJMonthLabel.setMinimumSize(new java.awt.Dimension(30, 20));
			ivjJMonthLabel.setMaximumSize(new java.awt.Dimension(30, 20));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjJMonthLabel;
}
/**
 * Return the JMonthScrollBar property value.
 * @return javax.swing.JScrollBar
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private JScrollBar getJMonthScrollBar() {
	if (ivjJMonthScrollBar == null) {
		try {
			ivjJMonthScrollBar = new javax.swing.JScrollBar();
			ivjJMonthScrollBar.setName("JMonthScrollBar");
			ivjJMonthScrollBar.setBlockIncrement(10);
			ivjJMonthScrollBar.setValue(13);
			ivjJMonthScrollBar.setPreferredSize(new java.awt.Dimension(20, 20));
			ivjJMonthScrollBar.setMaximum(13);
			ivjJMonthScrollBar.setMinimum(0);
			ivjJMonthScrollBar.setMinimumSize(new java.awt.Dimension(20, 20));
			// user code begin {1}
			ivjJMonthScrollBar.setValue(2);
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjJMonthScrollBar;
}
/**
 * Return the JMonthTextField property value.
 * @return javax.swing.JTextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private JTextField getJMonthTextField() {
	if (ivjJMonthTextField == null) {
		try {
			ivjJMonthTextField = new javax.swing.JTextField();
			ivjJMonthTextField.setName("JMonthTextField");
			ivjJMonthTextField.setText("2");
			ivjJMonthTextField.setDisabledTextColor(java.awt.Color.black);
			ivjJMonthTextField.setPreferredSize(new java.awt.Dimension(20, 20));
			ivjJMonthTextField.setFont(new java.awt.Font("dialog", 1, 12));
			ivjJMonthTextField.setEditable(false);
			ivjJMonthTextField.setMinimumSize(new java.awt.Dimension(20, 20));
			ivjJMonthTextField.setEnabled(false);
			ivjJMonthTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjJMonthTextField;
}
/**
 * Return the JOkLabel property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private JLabel getJOkLabel() {
	if (ivjJOkLabel == null) {
		try {
			ivjJOkLabel = new javax.swing.JLabel();
			ivjJOkLabel.setName("JOkLabel");
			ivjJOkLabel.setBorder(BorderFactory.createEtchedBorder());
			ivjJOkLabel.setText(res.getString("String_41"));
			ivjJOkLabel.setMaximumSize(new java.awt.Dimension(30, 20));
			ivjJOkLabel.setPreferredSize(new java.awt.Dimension(30, 20));
			ivjJOkLabel.setMinimumSize(new java.awt.Dimension(30, 20));
			ivjJOkLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			// user code begin {1}
			ivjJOkLabel.addMouseListener(new MouseListener()
			{
				public void mouseReleased(MouseEvent e)
				{
					JLabel label = (JLabel)e.getSource();
					label.setBorder(BorderFactory.createEtchedBorder());
					repaint();
				}
				public void mouseEntered(MouseEvent e)
				{
				}
				public void mousePressed(MouseEvent e)
				{
					if(e.getModifiers() != e.BUTTON1_MASK)
						return;
					JLabel label = (JLabel)e.getSource();
					label.setBorder(BorderFactory.createLoweredBevelBorder());
					repaint();
				}
				public void mouseExited(MouseEvent e)
				{
					JLabel label = (JLabel)e.getSource();
					label.setBorder(BorderFactory.createEtchedBorder());
					repaint();
				}
				public void mouseClicked(MouseEvent e)
				{
					if(e.getModifiers() == e.BUTTON1_MASK)
						dispose();
				}
			});
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjJOkLabel;
}
/**
 * Return the JYearAndMonthPanel property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private JPanel getJWeeksPanel() {
	if (ivjJWeeksPanel == null) {
		try {
			ivjJWeeksPanel = new javax.swing.JPanel();
			ivjJWeeksPanel.setName("JWeeksPanel");
			ivjJWeeksPanel.setLayout(getJWeeksPanelGridLayout());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjJWeeksPanel;
}
/**
 * Return the JWeeksPanelGridLayout property value.
 * @return java.awt.GridLayout
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private GridLayout getJWeeksPanelGridLayout() {
	java.awt.GridLayout ivjJWeeksPanelGridLayout = null;
	try {
		/* Create part */
		ivjJWeeksPanelGridLayout = new java.awt.GridLayout();
		ivjJWeeksPanelGridLayout.setColumns(7);
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	};
	return ivjJWeeksPanelGridLayout;
}
/**
 * Return the JYearLabel property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private JLabel getJYearLabel() {
	if (ivjJYearLabel == null) {
		try {
			ivjJYearLabel = new javax.swing.JLabel();
			ivjJYearLabel.setName("JYearLabel");
			ivjJYearLabel.setPreferredSize(new java.awt.Dimension(20, 20));
			ivjJYearLabel.setText(res.getString("String_44"));
			ivjJYearLabel.setMinimumSize(new java.awt.Dimension(20, 20));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjJYearLabel;
}
/**
 * Return the JYearScrollBar property value.
 * @return javax.swing.JScrollBar
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private JScrollBar getJYearScrollBar() {
	if (ivjJYearScrollBar == null) {
		try {
			ivjJYearScrollBar = new javax.swing.JScrollBar();
			ivjJYearScrollBar.setName("JYearScrollBar");
			ivjJYearScrollBar.setValue(1999);
			ivjJYearScrollBar.setPreferredSize(new java.awt.Dimension(20, 20));
			ivjJYearScrollBar.setMaximum(3000);
			ivjJYearScrollBar.setMinimum(1000);
			ivjJYearScrollBar.setMinimumSize(new java.awt.Dimension(20, 20));
			// user code begin {1}
			ivjJYearScrollBar.setValue(1999);
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjJYearScrollBar;
}
/**
 * Return the JYearTextField property value.
 * @return javax.swing.JTextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private JTextField getJYearTextField() {
	if (ivjJYearTextField == null) {
		try {
			ivjJYearTextField = new javax.swing.JTextField();
			ivjJYearTextField.setName("JYearTextField");
			ivjJYearTextField.setText("1999");
			ivjJYearTextField.setDisabledTextColor(java.awt.Color.black);
			ivjJYearTextField.setPreferredSize(new java.awt.Dimension(40, 20));
			ivjJYearTextField.setFont(new java.awt.Font("dialog", 1, 12));
			ivjJYearTextField.setEditable(false);
			ivjJYearTextField.setMinimumSize(new java.awt.Dimension(40, 20));
			ivjJYearTextField.setEnabled(false);
			ivjJYearTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjJYearTextField;
}
/**
 * Called whenever the part throws an exception.
 * @param exception java.lang.Throwable
 */
private void handleException(Throwable exception) {

	/* Uncomment the following lines to print uncaught exceptions to stdout */
	 //System.out.println("--------- UNCAUGHT EXCEPTION ---------");
	 exception.printStackTrace(System.out);
}
/**
 * Initializes connections
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initConnections() {
	// user code begin {1}
	// user code end
	getJYearScrollBar().addAdjustmentListener(this);
	getJMonthScrollBar().addAdjustmentListener(this);
}
/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void initDATA() {
	try
	{
		if(time == null)
			time = new GregorianCalendar();
		GregorianCalendar dt = (GregorianCalendar)time.clone();
		//
		int cyear = time.get(time.YEAR);
		int cmonth = time.get(time.MONTH);
		int cday = time.get(time.DAY_OF_MONTH);
		int cweek = time.get(time.DAY_OF_WEEK);
/*
//System.out.println("YEAR : "+time.get(time.YEAR));
//System.out.println("MONTH : "+time.get(time.MONTH));
//System.out.println("DATE : "+time.get(time.DATE));
//System.out.println("DAY_OF_MONTH : "+time.get(time.DAY_OF_MONTH));
//System.out.println("DAY_OF_WEEK : "+time.get(time.DAY_OF_WEEK));
//System.out.println("DAY_OF_WEEK_IN_MONTH : "+time.get(time.DAY_OF_WEEK_IN_MONTH));
//System.out.println("DAY_OF_YEAR : "+time.get(time.DAY_OF_YEAR));
		//*/
		getJYearTextField().setText(String.valueOf(cyear));
		getJYearScrollBar().setValue(cyear);
		getJMonthTextField().setText(String.valueOf(cmonth+1));
		getJMonthScrollBar().setValue(cmonth+1);
		this.repaint();
		//
		dt.add(time.DAY_OF_MONTH,-cday);
		int temp = dt.get(time.DAY_OF_WEEK);
		dt.add(time.DAY_OF_WEEK,-(temp-1));
		for(int i = 0;i < 6;i++)
		{
			for(int j = 0;j < 7;j++)
			{
				temp = dt.get(time.DAY_OF_MONTH);
				JLabel label = days[i][j];
				label.setText(String.valueOf(temp));
				label.setForeground(Color.black);
				if(j == 0 || j == 6)
					label.setForeground(Color.red);
				if(dt.get(time.MONTH) != cmonth)
					label.setForeground(Color.gray);
				else if(dt.get(time.YEAR) == cyear && dt.get(time.MONTH) == cmonth && temp == cday)
				{
					if(currentLabel != null)
					{
						currentLabel.setBackground(backColor);
						currentLabel.setForeground(foreColor);
            currentLabel.setFont(new java.awt.Font("Dialog", 0, 12));
						currentLabel.setBorder(BorderFactory.createEtchedBorder());
					}
					currentLabel = label;
					foreColor = currentLabel.getForeground();
					backColor = currentLabel.getBackground();
//					currentLabel.setBackground(Color.gray);
//					currentLabel.setForeground(Color.magenta);
					currentLabel.setBackground(Color.blue);
					currentLabel.setForeground(Color.yellow);
          currentLabel.setFont(new java.awt.Font("Dialog", 1, 12));
					currentLabel.setBorder(BorderFactory.createLoweredBevelBorder());
				}
				dt.add(time.DAY_OF_MONTH,1);
			}
		}
	}
	catch(Exception e)
	{
		//System.out.println("initDATA:"+e);
	}
}
/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void initGUI() {
	MouseListener listener = new MouseListener()
	{
		public void mouseReleased(MouseEvent e)
		{
			JLabel label = (JLabel)e.getSource();
			if(label.equals(currentLabel))
				return;
			label.setBorder(BorderFactory.createEtchedBorder());
			repaint();
		}
		public void mouseEntered(MouseEvent e)
		{
			JLabel label = (JLabel)e.getSource();
			if(label.equals(currentLabel))
				return;
			label.setBorder(BorderFactory.createRaisedBevelBorder());
			repaint();
		}
		public void mousePressed(MouseEvent e)
		{
//			if(e.getModifiers() != e.BUTTON1_MASK)
//				return;
			JLabel label = (JLabel)e.getSource();
			label.setBorder(BorderFactory.createLoweredBevelBorder());
			repaint();
		}
		public void mouseExited(MouseEvent e)
		{
			JLabel label = (JLabel)e.getSource();
			if(label.equals(currentLabel))
				return;
			label.setBorder(BorderFactory.createEtchedBorder());
			repaint();
		}
		public void mouseClicked(MouseEvent e)
		{
			JLabel label = (JLabel)e.getSource();
			try
			{
				int newij = Integer.parseInt(label.getName());
				int oldij = Integer.parseInt(currentLabel.getName());
				int ttsub = newij/10 - oldij/10;
				int tsub = newij%10-oldij%10;
				int sub = (ttsub)*7+(tsub);
				time.add(time.DAY_OF_MONTH,sub);
				if(label.getForeground() == Color.gray)
				{
					initDATA();
				}
				else if(e.getClickCount() == 1)
				{
					if(currentLabel != null)
					{
						currentLabel.setBackground(backColor);
						currentLabel.setForeground(foreColor);
            currentLabel.setFont(new java.awt.Font("Dialog", 0, 12));
						currentLabel.setBorder(BorderFactory.createEtchedBorder());
					}
 					backColor = label.getBackground();
 					foreColor = label.getForeground();
          currentLabel = label;
//					currentLabel.setBackground(Color.gray);
//					currentLabel.setForeground(Color.magenta);
					currentLabel.setBackground(Color.blue);
					currentLabel.setForeground(Color.yellow);
          currentLabel.setFont(new java.awt.Font("Dialog", 1, 12));
					currentLabel.setBorder(BorderFactory.createLoweredBevelBorder());
					repaint();
				}
				else if(e.getClickCount() == 2)
				{
					dispose();
				}
			}
			catch(Exception ex)
			{
				//System.out.println("mouseClicked:"+ex);
			}
		}
	};
	//
	JPanel dayPanel = getJDaysPanel();
	JPanel weekPanel = getJWeeksPanel();
	weeks = new JLabel[7];
	days = new JLabel[6][7];
	String[] name = {res.getString("String_53"),res.getString("String_54"),res.getString("String_55"),res.getString("String_56"),res.getString("String_57"),res.getString("String_58"),res.getString("String_59")};
	//
	//
	int i = 0;
	JLabel label = null;
	for(i = 0;i < 7;i++)
	{
		String str = String.valueOf(i);
		label = new JLabel();
		label.setName(str);
		label.setText(name[i]);
//    label.setFont(new java.awt.Font("Dialog", 1, 12));
		label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		label.setPreferredSize(new java.awt.Dimension(20, 20));
		label.setBorder(BorderFactory.createEtchedBorder());
		label.setOpaque(true);
		label.setBackground(Color.lightGray);
		if(i == 0 || i == 6)
			label.setForeground(Color.red);
		weekPanel.add(label);
		weeks[i] = label;
	}
	int j = 0;
	for(i = 0;i < 6;i++)
	{
		String str_i = String.valueOf(i);
		for(j = 0;j < 7;j++)
		{
			String str_j = str_i+String.valueOf(j);
			label = new JLabel();
			label.setName(str_j);
			label.setText(str_j);
      label.setFont(new java.awt.Font("Dialog", 0, 12));
			label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			label.setPreferredSize(new java.awt.Dimension(20, 20));
			label.setBorder(BorderFactory.createEtchedBorder());
			label.setOpaque(true);
			label.addMouseListener(listener);
			dayPanel.add(label);
			days[i][j] = label;
		}
	}
}
/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	// user code begin {1}
	setLocation(100,100);
	// user code end
	setName("AccountNaturalCalendar");
	setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	setTitle(res.getString("String_62"));
	setSize(220, 200);
	setModal(true);
	setResizable(false);
	setContentPane(getJFrameContentPane());
	initConnections();
	// user code begin {2}
	this.initGUI();
	this.initDATA();
	// user code end
}
/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	//try {
		//NaturalCalendar aNaturalCalendar;
		//aNaturalCalendar = new NaturalCalendar();
		//try {
			//Class aCloserClass = Class.forName("com.ibm.uvm.abt.edit.WindowCloser");
			//Class parmTypes[] = { java.awt.Window.class };
			//Object parms[] = { aNaturalCalendar };
			//java.lang.reflect.Constructor aCtor = aCloserClass.getConstructor(parmTypes);
			//aCtor.newInstance(parms);
		//} catch (java.lang.Throwable exc) {};
		//aNaturalCalendar.setVisible(true);
		////System.out.println(aNaturalCalendar.getJYearTextField().getText()+"-"+aNaturalCalendar.getJMonthTextField().getText()+"-"+aNaturalCalendar.currentLabel.getText());
	//} catch (Throwable exception) {
		//System.err.println("Exception occurred in main() of com.genersoft.erp.gl.GenericFrame");
		//exception.printStackTrace(System.out);
	//}
	//System.out.println(NaturalCalendar.returnStringFromString("2002-03-12"));
}
/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static String returnStringFromString(String str) {
	try
	{
		StringTokenizer st = new StringTokenizer(str,"-",false);
		String syear = (String)st.nextElement();
		String smonth = (String)st.nextElement();
		String sday = (String)st.nextElement();
		int iyear = Integer.parseInt(syear);
		int imonth = Integer.parseInt(smonth);
		int iday = Integer.parseInt(sday);
		GregorianCalendar oldTime = new GregorianCalendar(iyear,imonth-1,iday);

		NaturalCalendar aNaturalCalendar;
		aNaturalCalendar = new NaturalCalendar();
		aNaturalCalendar.time = oldTime;
		aNaturalCalendar.initDATA();
		aNaturalCalendar.setVisible(true);
		str = aNaturalCalendar.getJYearTextField().getText()+"-"+aNaturalCalendar.getJMonthTextField().getText()+"-"+aNaturalCalendar.currentLabel.getText();
		return str;
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of com.genersoft.erp.gl.GenericFrame");
		exception.printStackTrace(System.out);
	}
	return str;
}
/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static String returnStringFromTime(GregorianCalendar oldTime) {
	String str = null;
	try {
		NaturalCalendar aNaturalCalendar;
		aNaturalCalendar = new NaturalCalendar();
		aNaturalCalendar.time = oldTime;
		aNaturalCalendar.initDATA();
		aNaturalCalendar.setVisible(true);
		str = aNaturalCalendar.getJYearTextField().getText()+"-"+aNaturalCalendar.getJMonthTextField().getText()+"-"+aNaturalCalendar.currentLabel.getText();
		return str;
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of com.genersoft.erp.gl.GenericFrame");
		exception.printStackTrace(System.out);
	}
	return str;
}
/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static GregorianCalendar returnTimeFromString(String str) {
	GregorianCalendar oldTime = null;
	try
	{
		StringTokenizer st = new StringTokenizer(str,"-",false);
		String syear = (String)st.nextElement();
		String smonth = (String)st.nextElement();
		String sday = (String)st.nextElement();
		int iyear = Integer.parseInt(syear);
		int imonth = Integer.parseInt(smonth);
		int iday = Integer.parseInt(sday);
		oldTime = new GregorianCalendar(iyear,imonth-1,iday);

		NaturalCalendar aNaturalCalendar;
		aNaturalCalendar = new NaturalCalendar();
		aNaturalCalendar.time = oldTime;
		aNaturalCalendar.initDATA();
		aNaturalCalendar.setVisible(true);
		return aNaturalCalendar.time;
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of com.genersoft.erp.gl.GenericFrame");
		exception.printStackTrace(System.out);
	}
	return oldTime;
}
/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static GregorianCalendar returnTimeFromTime(GregorianCalendar oldTime) {
	try {
		NaturalCalendar aNaturalCalendar;
		aNaturalCalendar = new NaturalCalendar();
		aNaturalCalendar.time = oldTime;
		aNaturalCalendar.initDATA();
		aNaturalCalendar.setVisible(true);
		return aNaturalCalendar.time;
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of com.genersoft.erp.gl.GenericFrame");
		exception.printStackTrace(System.out);
	}
	return oldTime;
}
}
