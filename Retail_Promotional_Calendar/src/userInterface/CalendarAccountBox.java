package userInterface;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import org.apache.poi.ss.usermodel.Cell;

import java.util.*;

public class CalendarAccountBox {
	
	private String account, brandName;
	private static Font labelFont = new Font(Font.SANS_SERIF, Font.BOLD, 15);
	private static String[] daysWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	private static String[] monthsYear = {"January", "February", "March", "April", "May", "June", "July", "August", 
			"September", "October", "November", "December"};
	
	public CalendarAccountBox(String accountName, String brandName) {
		this.account = accountName;
		this.brandName = brandName;
	}
	
	//Getter method to get back the JPanel with the year dates
	public JPanel getMainBox() {
		return this.returnMainBox();
	}
	
	//Method to create and populate the main panel that will inclose two boxes one with the calendar for 
	//each year and also the top box with account details
	private JPanel returnMainBox() {
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
		int lastYear = currentYear-1;
		
		JPanel year = this.annualCalendar(currentYear);
		JPanel lastCal = this.annualCalendar(lastYear);
		content.add(year);
		content.add(lastCal);
		
		return content;
	}
	
	//Helper method to return a box with months and dates for a year
	private JPanel annualCalendar(int year) {
		JPanel annualBox = new JPanel();
		annualBox.setBackground(Color.WHITE);
		annualBox.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		
		JPanel monthBox = new JPanel();
		monthBox.setLayout(new BoxLayout(monthBox, BoxLayout.Y_AXIS));
		JLabel yearLabel = new JLabel(String.valueOf(year));
		yearLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		monthBox.add(yearLabel);
		for(int i=0; i<12; i++) {
			JLabel monthLabel = new JLabel(this.monthsYear[i]);
			monthLabel.setFont(labelFont);
			JTable monthTable = this.monthDates(i, year);
			monthBox.add(monthLabel);
			monthBox.add(monthTable);
		}
		annualBox.add(monthBox);
		return annualBox;
		
	}
	
	//Helper method to create the table with the month data
	private JTable monthDates(int mon, int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, mon, 1);
		Object[][] data = this.createDataInput(this.daysWeek, cal);
		JTable month = new JTable(7,7);
		month.setModel(new MyTable(7,7, data));
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		month.setDefaultRenderer(int.class, render);
		
		return month;
	}
	
	//Helper method to create an object with the days and dates for each month
	private Object[][] createDataInput(String [] weekDays, Calendar cal){
		Object[][] data = new Object[7][7];
		for(int i=0; i<7; i++) {
			data[0][i] = weekDays[i];
		}
		int maxDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int day = cal.get(Calendar.DAY_OF_WEEK)-1;
		int week = 1;
		for(int j=1; j<maxDate; j++) {
			if(day>6) {
				day=0;
				week++;
			}
			data[week][day] = j;
			day++;
		}
		return data;
	}
	
	//We create a custom AbstractTableModel because we want to prevent the user from editing the data manually 
	// in the table
	private class MyTable extends AbstractTableModel{
		
		private int rows;
		private int cols;
		private Object[][] data;
		
		public MyTable(int row, int col, Object[][] input) {
			super();
			this.rows=row;
			this.cols=col;
			this.data = input;
		}
		
		//This will prevent user from editing the data by clicking on a box
		public boolean isCellEditable() {
			return false;
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return this.rows;
		}

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return this.cols;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return this.data[rowIndex][columnIndex];
		}
	}
}
