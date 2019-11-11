package userInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import javax.swing.*;

abstract public class HelperInterface {
	
	private static String logoAddress = "/Users/Konstantine/Desktop/Programs/Portfolio/Retail_Promotional_Calendar/freeLogo.jpg";
	private static Font setbut = new Font(Font.SANS_SERIF, Font.ITALIC, 10);
	private static Font topInfo = new Font(Font.SANS_SERIF, Font.BOLD, 15);
	private ArrayList<String> brandsList;
	private HashMap<String, ArrayList<String>> brandsDict;
	
	public HelperInterface() {
		super();
		this.brandsList= new ArrayList<String>();
		this.initiateValues("Brand1", "Brand2", "Brand3");
		this.brandsDict = new HashMap<String, ArrayList<String>>();
		this.populateBrandsDictionary();
	}
	
	//Getter of the header
	public JPanel getTop() {
		return this.getTopBar();
	}
	
	//Helper method to set the values in the middle part to show the retailer and brand
	public void setMidPartValues(String brand, String retailer) {
		JLabel brandLabel = new JLabel(brand);
		brandLabel.setFont(topInfo);
		brandLabel.setBackground(Color.WHITE);
		
		JLabel retailerLabel = new JLabel(retailer);
		retailerLabel.setFont(topInfo);
		retailerLabel.setBackground(Color.WHITE);
	}
	
	
	//Helper method to create the top bar. It has 3 parts, one for the logo at West, one for the Settings button at right and 
	//a middle section that is a separate panel. This panel will hold the retailer and brand information once user is 
	// in the calendar page.
	private JPanel getTopBar() {
		JPanel topBar = new JPanel();
		topBar.setBackground(Color.WHITE);
		topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
		topBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		Image icon = this.adjustPictAnalysis(this.logoAddress);
		ImageIcon img = new ImageIcon(icon);
		JLabel imgLabel = new JLabel(img);
		topBar.add(imgLabel, BorderLayout.WEST);
		
		JPanel mid = new JPanel();
		mid.setBackground(Color.WHITE);
		topBar.add(mid, BorderLayout.CENTER);
		
		JButton settings = new JButton("Settings");
		settings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				settingsButtonAction();
			}
			
		});
		settings.setFont(setbut);
		settings.setForeground(new Color(42, 118, 178));
		topBar.add(settings, BorderLayout.EAST);
		
		return topBar;
	}
	
	//Helper method to adjust analysis from a picture
	private Image adjustPictAnalysis(String sourcePath) {
		ImageIcon photo = new ImageIcon(sourcePath);
		Image pct = photo.getImage().getScaledInstance(100, 80, Image.SCALE_DEFAULT);
		return pct;
	}
	
	//Helper method to add an action to the "Settings" button. A new window will open were the user can adjust the brand names and
	//add additional brands
	private void settingsButtonAction() {
		JFrame smallWindow = new JFrame();
		smallWindow.setSize(300,300);
		smallWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		smallWindow.setLocationRelativeTo(null);
		smallWindow.setBackground(Color.WHITE);
		
		Container smallContent = smallWindow.getContentPane();
		JPanel content = new JPanel();
		GridLayout grid = new GridLayout(5,1);
		content.setLayout(grid);
		content.setBackground(Color.WHITE);
		
		JButton addBrand = this.createOptionButton("Add Brand");
		JButton editBrand = this.createOptionButton("Edit Brand");
		JButton deleteBrand = this.createOptionButton("Delete Brand");
		deleteBrand.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				adjustBrandsList("delete");
			}
			
		});
		JButton addGraphs = this.createOptionButton("Add Graphics");
		JButton changeLogo = this.createOptionButton("Change Logo");
		
		content.add(addBrand);
		content.add(editBrand);
		content.add(deleteBrand);
		content.add(addGraphs);
		content.add(changeLogo);
		
		smallContent.add(content);
		smallWindow.setVisible(true);
	}
	
	//Method that takes a command from the user if he wants to add/edit/delete brand and adjust brand arraylist
	public void adjustBrandsList(String command){
		switch(command) {
		case "add":
		case "edit":
		case "delete": this.deleteBrandFromList();
						break;
		}
	}
	
	//Helper method connected with the "adjustBrandsList" method to delete a brand
	private void deleteBrandFromList(){
		JFrame brandsWindow = new JFrame();
		brandsWindow.setSize(200,200);
		brandsWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		brandsWindow.setLocationRelativeTo(null);
		Container content = brandsWindow.getContentPane();
		
		String [] list = new String[this.brandsList.size()+1];
		list[0]="Select Brand";
		for(int i=1; i<=this.brandsList.size(); i++) {
			list[i]=this.brandsList.get(i-1);
		}
		JComboBox brands = new JComboBox(list);
		brands.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String option = String.valueOf(brands.getSelectedItem());
				adjustBrands("delete", option);
				brandsWindow.dispose();
			}
		});
		content.add(brands, BorderLayout.CENTER);

		brandsWindow.setVisible(true);
	}
	
	//Helper method connected with "settingsButtonAction". This method will create a button and assigned the appropriate
	//action connected to the method.
	private JButton createOptionButton(String tag) {
		JButton but = new JButton(tag);
		return but;
	}
	
	//Helper method to add/remove element in brandsList
	public void adjustBrands(String measure, String targetBrand) {
		if (measure.equals("add")) {
			this.brandsList.add(targetBrand);
		} else {
			for(int k=0; k<this.brandsList.size(); k++) {
				if (this.brandsList.get(k).equals(targetBrand)) {
					this.brandsList.remove(k);
				}
			}
		}
	}
	
	//Helper method to get ArrayList
	public ArrayList<String> getBrandsList(){
		return this.brandsList;
	}
	
	//Initiate values in brandsList
	public void initiateValues(String brand1, String brand2, String brand3){
		brandsList.add(brand1);
		brandsList.add(brand2);
		brandsList.add(brand3);
		
	}
	
	//Method to initialize the ogiginal brands hashmap and arraylists
	public void populateBrandsDictionary() {
		for(int i=0; i<3; i++) {
			ArrayList<String> brandInfo = new ArrayList<String>();
			
			//The lines between the comments are simply to add some testing data to create a sample of the program. In the
			// actual program these lines will be removed
			if(i==0) {
				brandInfo.add("Super Duper Cleaner 4oz UPC: 202020202");
				brandInfo.add("Super Duper Cleaner 5oz UPC: 123412512");
				brandInfo.add("The best cleaner you have ever seen! 20oz UPC: 212312312");
				this.brandsDict.put("Brand1", brandInfo);
			} else if (i==1) {
				brandInfo.add("Pens for hard readers 20pk UPC: 123123123");
				brandInfo.add("Pencils for strong writers 100pk UPC: 12121221");
				this.brandsDict.put("Brand2", brandInfo);
			} else {
				brandInfo.add("Tough Drink 7fl oz UPC: 123131111");
				brandInfo.add("Healthy Drink 20fl oz UPC: 119292929");
				this.brandsDict.put("Brand3", brandInfo);
			}
			//This is the end of the testing data!
		}
	}
	
	//Getter method to retrive the brands Dictionary
	public HashMap<String, ArrayList<String>> returnBrandDictionary(){
		return this.brandsDict;
	}

}
