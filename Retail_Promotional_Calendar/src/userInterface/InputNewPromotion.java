package userInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;

import javax.swing.*;
import org.jdesktop.swingx.JXDatePicker;
import java.util.*;
import dataAnalysisAndStorage.DataFiles;

public class InputNewPromotion extends HelperInterface{
	
	private String brand, retailer, channel, productPack, promotion, productSeasonality, commenting;
	private Date startingDate, endingDate;
	private boolean repeatingPromo;
	private JFrame mainFrame;
	private static Font attributeFont = new Font(Font.SANS_SERIF, Font.BOLD, 12);
	
	public InputNewPromotion(String brandName, String retailerName, String channelChoice) {
		super();
		this.brand = brandName;
		this.retailer = retailerName;
		this.channel = channelChoice;
		this.mainFrame = new JFrame();
		this.newPromoWindow();
	}
	
	
	//Method to create the secondary window for adding a new promotion
	private void newPromoWindow() {
		this.mainFrame.setSize(600,400);
		this.mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.mainFrame.setLocationRelativeTo(null);
		this.mainFrame.setBackground(Color.WHITE);
		
		Container content = mainFrame.getContentPane();
		JPanel optionsContainer = new JPanel();
		optionsContainer.setLayout(new BoxLayout(optionsContainer, BoxLayout.Y_AXIS));
		optionsContainer.setBackground(Color.WHITE);
		JPanel mainContainer = new JPanel();
		mainContainer.setBackground(Color.WHITE);
		mainContainer.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		GridLayout grid = new GridLayout(6,1);
		mainContainer.setLayout(grid);
		JPanel productSelection = this.dropdownPanel("Please select product.", "products");
		JPanel promoSelection = this.dropdownPanel("Please select promo type.", "promoType");
		JPanel seasonSelection = this.dropdownPanel("Please select seasonality.", "seasonal");
		JPanel repeatingSelection = this.dropdownPanel("Is this a repeating promotion.", "repeat");
		JPanel startPromoSelection = this.getDateDropdown("Please indicate the date promotion starts.");
		JPanel endPromoSelection = this.getDateDropdown("Please indicate the date promotion is ending.");
		
		mainContainer.add(productSelection);
		mainContainer.add(promoSelection);
		mainContainer.add(seasonSelection);
		mainContainer.add(repeatingSelection);
		mainContainer.add(startPromoSelection);
		mainContainer.add(endPromoSelection);
		optionsContainer.add(mainContainer);
		
		JPanel comments = this.commentsSection("Comments:");
		optionsContainer.add(comments);
		
		JPanel buttons = this.buttonsSection(mainFrame);
		optionsContainer.add(buttons);
		
		content.add(optionsContainer);
		mainFrame.setVisible(true);
	}
	
	//Helper method to create the drop down
	private JPanel dropdownPanel(String name, String type) {
		JPanel dropdown = new JPanel();
		dropdown.setLayout(new BoxLayout(dropdown, BoxLayout.X_AXIS));
		dropdown.setBackground(Color.WHITE);
		
		JLabel labelName = new JLabel(name);
		labelName.setFont(attributeFont);
		dropdown.add(labelName);
		
		String [] items = this.getDropdownContent(type);
		JComboBox box = new JComboBox();
		for(String item: items) {
			box.addItem(item);
		}
		box.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				String selection = String.valueOf(box.getSelectedItem());
				initiateGlobalVariables(type, selection);
			}
			
		});
		dropdown.add(box);
		
		return dropdown;
	}
	
	//Get the product list for the brand you are currently working on 
	private String[] getProductListArray(){
		HashMap<String, ArrayList<String>> brandsInfo = super.returnBrandDictionary();
		ArrayList<String> productList = brandsInfo.get(this.brand);
		
		if(productList.size()>0) {
			String [] products = new String[productList.size()];
			for(int i=0; i<productList.size(); i++) {
				products[i] = productList.get(i);
			}
			return products;
		} else {
			String [] empty = {"Empty1", "Empty2"};
			return empty;
		}
	}
	
	//Helper method to look at the type of dropdown and allocate the value to the correct variable
	private void initiateGlobalVariables(String type, String value) {
		switch(type) {
		case "promoType": this.promotion = value;
		break;
		case "seasonal": this.productSeasonality = value;
		break;
		case "products": this.productPack = value;
		break;
		case "repeat": if(value.equals("Yes")) {
							this.repeatingPromo=true;
						}else {
							this.repeatingPromo=false;
						}
		break;
		default: this.commenting = value;
		break;
		}
	}
	
	//Helper method to get a list of options for the new promo dropdown
	private String[] getDropdownContent(String type){
		switch (type) {
		case "promoType": String[] promoTypes = {"In-store Display", "Print Feature", "Print Feature & In-store Display", 
				"In-Store Price Discount", "Digital Feature", "Email Ad", "Digital Coupon", "Digital Discount"};
						return promoTypes;
		case "seasonal": String [] seasons = {"Everyday", "Holiday Specific"};
						return seasons;
		case "products": String [] products = this.getProductListArray();
						return products;
		case "repeat": String [] repeat = {"Yes", "No"};
						return repeat;
		default: String [] empty = {};
						return empty;
		}
	}
	
	//Helper method to return a row with a date dropdown 
	private JPanel getDateDropdown(String context) {
		JPanel datePick = new JPanel();
		datePick.setBackground(Color.WHITE);
		datePick.setLayout(new BoxLayout(datePick, BoxLayout.X_AXIS));
		
		JLabel dateLabel = new JLabel(context);
		dateLabel.setFont(attributeFont);
		datePick.add(dateLabel);
		
		JXDatePicker pick = new JXDatePicker();
		pick.setDate(Calendar.getInstance().getTime());
		pick.setFormats(new SimpleDateFormat("MM.dd.yyyy"));
		datePick.add(pick);
		
		return datePick;
	}
	
	//Helper method to create the text area box for comments
	private JPanel commentsSection(String label) {
		JPanel textPanel = new JPanel();
		textPanel.setBackground(Color.WHITE);
		
		JTextArea text = new JTextArea();
		text.setEditable(true);
		text.setLineWrap(true);
		text.setBackground(new Color(247, 242, 241));
		text.setText(label);
		text.setPreferredSize(new Dimension(450,150));
		JScrollPane scroll = new JScrollPane(text);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(450,150));
		textPanel.add(scroll);
		
		return textPanel;
	}
	
	//Helper method to create the command buttons
	private JPanel buttonsSection(JFrame miniFrame) {
		JPanel buttonsContainer = new JPanel();
		buttonsContainer.setBackground(Color.WHITE);
		buttonsContainer.setLayout(new BoxLayout(buttonsContainer, BoxLayout.X_AXIS));
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				miniFrame.dispose();
			}
			
		});
		JButton submit = new JButton("Submit");
		
		
		buttonsContainer.add(cancel);
		buttonsContainer.add(submit);
		return buttonsContainer;
	}
	
	//Helper method to add new data in the linked list class
	private void addNewDataInFile() {
		
	}

}
