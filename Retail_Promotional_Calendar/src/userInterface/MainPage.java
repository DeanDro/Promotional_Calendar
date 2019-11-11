package userInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import userInterface.ChannelAccountSelection;

public class MainPage extends HelperInterface{
	
	private ArrayList<String> brandNames;
	private static Font buttonsFont = new Font(Font.SANS_SERIF, Font.BOLD, 15);
	private JFrame frame;
	
	public MainPage() {
		super();
		this.frame = new JFrame();
		this.brandNames = super.getBrandsList();
		this.startMainPage();
	}
	
	//Getter method to take the main frame. We created frame this way in order to have easier access when we want to 
	//dispose current frame for the next page.
	public JFrame getMainFrame() {
		return this.frame;
	}
	
	//Method to initiate the frame with the main page
	private void startMainPage() {
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(600, 600);
		this.frame.setLocationRelativeTo(null);
		this.frame.setBackground(Color.WHITE);
		
		Container content = this.frame.getContentPane();
			
		JPanel top = super.getTop();
		content.add(top, BorderLayout.NORTH);
		
		JPanel butseg = this.buttonsSegment();
		content.add(butseg, BorderLayout.CENTER);
		
		this.frame.setVisible(true);
		
	}
	
	//Method for creating the brand buttons. User first selects the brand, then chooses the channel
	private JPanel buttonsSegment() {
		JPanel butSeg = new JPanel();
		butSeg.setLayout(new BoxLayout(butSeg, BoxLayout.Y_AXIS));
		butSeg.setBackground(Color.WHITE);
		
		double num = this.brandNames.size()/2.0+1;
		int numRows=0;
		if(num%2.0!=0.0) {
			numRows=(int) num/2+1;
		} else {
			numRows = (int) num/2;
		}
		
		int limit=0;
		int i=0;
		while (i<numRows) {
			if(limit+2<brandNames.size()) {
				JPanel row_lim = this.returnButtons(this.brandNames.get(limit), this.brandNames.get(limit+1));
				butSeg.add(row_lim);
				limit=+2;
				i++;
			} else {
				if(limit+1<brandNames.size()) {
					JPanel row_i = this.returnButtons(this.brandNames.get(limit), this.brandNames.get(limit+1));
					butSeg.add(row_i);
					JPanel row_last = this.returnButtons(this.brandNames.get(limit), "Add Brand");
					butSeg.add(row_last);
					limit=+2;
					i=+2;
					break;
				} else {
					JPanel row_s = this.returnButtons(this.brandNames.get(limit), "Add Brand");
					butSeg.add(row_s);
					limit++;
					i++;
					break;
				}
			}
		}
		
		return butSeg;
		
	}
	
	
	//Helper method to create a row of buttons. This will be used to populate the buttons segment. It takes the name of the 
	//brands and returns buttons with that names
	private JPanel returnButtons(String but1, String but2) {
		JPanel row = new JPanel();
		row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
		row.setBackground(Color.WHITE);
		row.setPreferredSize(new Dimension(300,200));
		
		JPanel but1Holder = new JPanel();
		but1Holder.setBackground(Color.WHITE);
		but1Holder.setBorder(BorderFactory.createEmptyBorder(40, 10, 0, 10));
		JButton but_1 = new JButton(but1);
		but_1.setFont(this.buttonsFont);
		but_1.setPreferredSize(new Dimension(100,40));
		but1Holder.add(but_1);
		but_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame currentPage =getMainFrame();
				currentPage.dispose();
				ChannelAccountSelection nextPage = new ChannelAccountSelection(but1);
			}
			
		});
		
		JPanel but2Holder = new JPanel();
		but2Holder.setBackground(Color.WHITE);
		but2Holder.setBorder(BorderFactory.createEmptyBorder(40, 10, 0, 10));
		JButton but_2 = new JButton(but2);
		but_2.setFont(this.buttonsFont);
		but_2.setPreferredSize(new Dimension(100,40));
		but2Holder.add(but_2);
		but_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame currentPage = getMainFrame();
				currentPage.dispose();
				ChannelAccountSelection nextPage = new ChannelAccountSelection(but2);
			}
			
		});
		
		row.add(but1Holder);
		row.add(but2Holder);
		
		return row;
	}
	
	public static void main(String[] args) {
		MainPage start = new MainPage();
	}

}
