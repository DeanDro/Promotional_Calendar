package userInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import userInterface.CalendarAccountBox;
import userInterface.InputNewPromotion;

public class CalendarPage extends HelperInterface{
	
	private String brandName, channelChoice, retailerStore;
	private JFrame frame;
	private static Font buttonsFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
	private static Color dashboardBack = new Color(51, 153,51);
	private CalendarAccountBox calendarBox;
	
	public CalendarPage(String brand, String channel, String retailer) {
		super();
		this.frame = new JFrame();
		this.brandName = brand;
		this.channelChoice = channel;
		this.retailerStore = retailer;
		this.calendarBox = new CalendarAccountBox(this.retailerStore, this.brandName);
		this.populateMainFrame();
	}
	
	//Getter method to get the main frame
	public JFrame getMainFrame() {
		return this.frame;
	}
	
	//Method to populate the main page with its parts
	private void populateMainFrame() {
		this.frame.setSize(1200,800);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setBackground(Color.WHITE);
		this.frame.setLocationRelativeTo(null);
		
		Container content = this.frame.getContentPane();
		
		JPanel header = super.getTop();
		content.add(header, BorderLayout.NORTH);
		
		JPanel dashboard = this.dashboardPanel();
		content.add(dashboard, BorderLayout.WEST);
		
		JPanel yearCal = this.calendarBox.getMainBox();
		JScrollPane scroll = new JScrollPane(yearCal);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		content.add(scroll, BorderLayout.CENTER);
		
		this.frame.setVisible(true);
		
	}
	
	//Helper method to create the dashboard on the left side of the page with the control buttons. We used the GridLayout to create
	//the column with the same size buttons for each command
	private JPanel dashboardPanel() {
		JPanel board = new JPanel();
		board.setBackground(this.dashboardBack);
		
		JPanel ins = new JPanel();
		GridLayout grid = new GridLayout(5,1);
		ins.setLayout(grid);
		ins.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		ins.setBackground(this.dashboardBack);
		JButton insert = this.createButton("Insert");
		JButton delete = this.createButton("Delete");
		JButton edit = this.createButton("Edit");
		JButton changeBrand = this.createButton("Brand");
		JButton changeRetailer = this.createButton("Retailer");
		
		ins.add(insert);
		ins.add(delete);
		ins.add(edit);
		ins.add(changeBrand);
		ins.add(changeRetailer);
		
		board.add(ins);
		return board;
	}
	
	//Helper method to create the buttons that will be used in the dashboard
	private JButton createButton(String but) {
		JButton button = new JButton(but);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				actionButton(but);
			}
			
		});
		return button;
	}
	
	//Helper method connected with the "createButton" method. It will look the tag of the button and give the appropriate 
	//window to open. For example if tag is "Retailer" it will take you back to the previous page to choose a new 
	//channel and retailer
	private void actionButton(String tag) {
		JFrame currentFrame = this.getMainFrame();
		switch(tag) {
		case "New":
			currentFrame.dispose();
			break;
		case "Brand":
			currentFrame.dispose();
			MainPage home = new MainPage();
			break;
		case "Retailer":
			currentFrame.dispose();
			ChannelAccountSelection store = new ChannelAccountSelection(this.brandName);
			break;
		case "Insert": InputNewPromotion newPromo = new InputNewPromotion(this.brandName, this.retailerStore, this.channelChoice);
			break;
		}
	}
	
	public static void main (String[] args) {
		CalendarPage test = new CalendarPage("Brand1", "Drug", "Walgreens");
	}

}
