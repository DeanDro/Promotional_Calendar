package userInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import userInterface.CalendarPage;

import javax.swing.*;
import java.util.*;

public class ChannelAccountSelection extends HelperInterface{
	
	private String brand, channel;
	private JFrame frame;
	private static String [] channelsList = {"Select Channel", "Grocery", "Drug", "Mass Market", "Club", "E-commerce", "Discount", "Convenience"};
	private ArrayList<String> groceryRetailers, drugRetailers, massRetailers, clubRetailers, digitalRetailers, discountRetailers, convenienceRetailers;
	private boolean statusDropdown;
	
	
	public ChannelAccountSelection(String brandName) {
		super();
		this.frame = new JFrame();
		this.brand = brandName;
		this.channel = "";
		this.statusDropdown = false;
		this.groceryRetailers = this.initiateChannelList("Grocery");
		this.drugRetailers = this.initiateChannelList("Drug");
		this.massRetailers = this.initiateChannelList("Mass Market");
		this.clubRetailers = this.initiateChannelList("Club");
		this.digitalRetailers = this.initiateChannelList("E-commerce");
		this.discountRetailers = this.initiateChannelList("Discount");
		this.convenienceRetailers = this.initiateChannelList("Convenience");
		this.mainFrame();
	}
	
	//Getter method for the main frame
	public JFrame getMainFrame() {
		return this.frame;
	}
	
	//Setter method to set a new value for the channel
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	//Second getter method that will look what channel you want and return the correct arrayList of retailers
	public ArrayList<String> getRetailersList(String channel){
		switch (channel) {
		case "Grocery": return this.groceryRetailers;
		case "Drug": return this.drugRetailers;
		case "Mass Market": return this.massRetailers;
		case "E-commerce": return this.digitalRetailers;
		case "Club": return this.clubRetailers;
		case "Discount": return this.discountRetailers;
		case "Convenience": return this.convenienceRetailers;
		default: return new ArrayList<String>();
		}
	}
	
	//Method to initiate the mainframe that will hold all the buttons and dropdowns
	private void mainFrame() {
		this.frame.setSize(600,600);
		this.frame.setLocationRelativeTo(null);
		this.frame.setBackground(Color.WHITE);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container content = frame.getContentPane();
		
		JPanel header = super.getTop();
		content.add(header, BorderLayout.NORTH);
		content.add(this.getMainPagePanel(), BorderLayout.CENTER);
		
		this.frame.setVisible(true);
	}
	
	//Helper method to create the main segment of the page with the drop down for the user to choose a channel and 
	//the box with all the retailers in the channel
	private JPanel getMainPagePanel() {
		JPanel main = new JPanel();
		main.setBackground(Color.WHITE);
		main.setLayout(new BoxLayout(main, BoxLayout.X_AXIS));
		
		JPanel channelsOptions = this.getChannelsAndRetailers();
		main.add(channelsOptions);
		
		return main;
	}
	
	//Helper method to create the drop down with each channel. When the user chooses a channel a second dropdown will appear with
	//the retailers for each channel. 
	private JPanel getChannelsAndRetailers() {
		JPanel channelsBox = new JPanel();
		channelsBox.setBackground(Color.WHITE);
		channelsBox.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));
		
		JComboBox combo = new JComboBox();
		for(String channel: this.channelsList) {
			combo.addItem(channel);
		}
		channelsBox.add(combo);
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String selection = String.valueOf(combo.getSelectedItem());
				setChannel(selection);
				if(statusDropdown==false) {
					JPanel box = secondDropDown(selection);
					box.setBackground(Color.WHITE);
					channelsBox.add(box);
					statusDropdown=true;
					frame.revalidate();
					frame.repaint();
				} else {
					channelsBox.removeAll();
					channelsBox.add(combo);
					combo.setSelectedItem(selection);
					JPanel box2 = secondDropDown(selection);
					box2.setBackground(Color.WHITE);
					channelsBox.add(box2);
					frame.revalidate();
					frame.repaint();
				}
			}
			
		});
		return channelsBox;
		
	}
	
	//Method to look which channel has been selected and return a panel with the correct dropdown values. When the user chooses the
	//retailer, it will automatically take him to the calendar page for the combination brand-channel-retailer
	private JPanel secondDropDown(String chan) {
		JPanel secondBox = new JPanel();
		JComboBox box = this.getRetailersSelectionForPanel(chan);
		box.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String retailer = String.valueOf(box.getSelectedItem());
				JFrame currentPage = getMainFrame();
				currentPage.dispose();
				CalendarPage calendar = new CalendarPage(brand, channel, retailer);
			}
			
		});
		secondBox.add(box);
		return secondBox;
	}
	
	//Helper method to create a combo box with the retailers in a channel
	private JComboBox getRetailersSelectionForPanel(String chan) {
		JComboBox box = new JComboBox();
		for(String item: this.getRetailersList(chan)) {
			box.addItem(item);
		}
		return box;
	}

	
	//Helper method to initiate the retailer lists for each channel. We made the lists ArrayList because we want to give the 
	//flexibility to the user to adjust the retailers list.
	private ArrayList<String> initiateChannelList(String channel){
		ArrayList<String> channelList = new ArrayList<String>();
		switch(channel) {
		case "Grocery": String [] groceries = {"", "Kroger", "Albertsons/Safeway", "HEB", "Ahold", "Publix"};
		for(String grocery: groceries) {
			channelList.add(grocery);
		}
		break;
		case "Drug": String[] pharmacies = {"", "CVS", "Walgreens", "Rite Aid"};
		for(String pharmacy: pharmacies) {
			channelList.add(pharmacy);
		}
		break;
		case "Mass Market": 
			channelList.add("");
			channelList.add("Walmart");
			channelList.add("Target");
			break;
		case "Club": 
			channelList.add("");
			channelList.add("Sam's Club");
			channelList.add("BJ's");
			channelList.add("Costco");
			break;
		case "E-commerce": String[] digitals = {"", "Amazon", "Jet.com", "Kroger.com", "Shoprite From Home", "Peapot", "Shopify", "Target.com"};
		for(String digital: digitals) {
			channelList.add(digital);
		}
		break;
		case "Discount": 
		channelList.add("");
		channelList.add("Dollar Tree");
		channelList.add("Dollar General");
		channelList.add("5 Bellow");
		break;
		case "Convenience": 
		channelList.add("");
		channelList.add("Wawa");
		channelList.add("Quick Check");
		break;
		}
		return channelList;
	}

}
