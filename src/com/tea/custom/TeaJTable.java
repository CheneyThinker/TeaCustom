package com.tea.custom;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class TeaJTable extends JTable {

	private DefaultTableModel model;
	
	public TeaJTable(Object[][] objects, Object[] columnNames) {
		model = new DefaultTableModel(objects, columnNames);
		getTableHeader().setReorderingAllowed(false);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		setDefaultRenderer(Object.class, renderer);
		setSelectionForeground(Color.LIGHT_GRAY);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setRowSelectionAllowed(true);
		setRowHeight(30);
		setModel(model);
	}
	
	public Integer addRow(Object[] rowData) {
		model.addRow(rowData);
		return model.getRowCount();
	}
	
	public void updateRow(Object[] rowData, int row, int[] columns) {
		for (int i=columns.length-1; i>=0 ; i--) {
			model.setValueAt(rowData[columns[i]], row, columns[i]);
		}
	}
	
	public Integer removeRow(int row) {
		model.removeRow(row);
		return model.getRowCount();
	}

	public void updateUI(Object[][] objects, Object[] columnNames) {
		model.setDataVector(objects, columnNames);
		setModel(model);
	}
	
	public Object[][] getObjects() {
		int rowCount = model.getRowCount();
		int columnCount = model.getColumnCount();
		Object[][] objects = new Object[rowCount][columnCount];
		for (int i = 0; i < rowCount; i++) {
			Object[] objs = new Object[columnCount];
			for (int j = 0; j < columnCount; j++) {
				objs[j] = model.getValueAt(i, j);
			}
			objects[i] = objs;
		}
		return objects;
	}
	
	public void setHideColumn(int columnIndex) {
		getTableHeader().getColumnModel().getColumn(columnIndex).setMaxWidth(0);
		getTableHeader().getColumnModel().getColumn(columnIndex).setMinWidth(0);
		getColumnModel().getColumn(columnIndex).setMaxWidth(0);
		getColumnModel().getColumn(columnIndex).setMinWidth(0);
	}
	
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
}
