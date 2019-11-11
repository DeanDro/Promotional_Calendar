package dataAnalysisAndStorage;

import java.util.*;

public class PromoData {
	
	//Instance variables to track key measures of a promotion
	private String account;
	private String channel;
	private String brand;
	private String productSize;
	private String seasonal;
	private String promotionalType;
	private Date promoStartingDate;
	private Date promoEndDate;
	private boolean repeatedPromoYA;
	private String comments;
	
	public PromoData(String account, String channel, String brand, String productSize, String seasonal, 
					String promoType, String comments, Date promoStart, Date promoEnds, boolean change) {
		this.account = account;
		this.channel = channel;
		this.brand = brand;
		this.productSize = productSize;
		this.seasonal = seasonal;
		this.promotionalType = promoType;
		this.comments = comments;
		this.promoStartingDate = promoStart;
		this.promoEndDate = promoEnds;
		this.repeatedPromoYA = change;
	}
		
	//Getter methods to extract data for each promotion
	public String getPromoData(String promoMeasure) {
		switch(promoMeasure) {
		case "account": return this.account;
		case "channel": return this.channel;
		case "brand": return this.brand;
		case "productSize": return this.productSize;
		case "season": return this.seasonal;
		case "promoType": return this.promotionalType;
		case "comments": return this.comments;
		default: return "You input invalid input";
		}
	}
		
	public boolean isPromoRepeatedLY() {
		return this.repeatedPromoYA;
	}
		
	public Date getPromoDate(String time) {
		if(time.equals("start")) {
			return this.promoStartingDate;
		} else {
			return this.promoEndDate;
		}
	}
		
	//Setter methods to set new data in an instance variable for when
	//you want to edit a promotion
	public void setPromoData(String promoMeasure, String input) {
		switch (promoMeasure) {
		case "account": this.account=input;
		case "channel": this.channel=input;
		case "brand": this.brand=input;
		case "productSize": this.productSize=input;
		case "season": this.seasonal = input;
		case "promoType": this.promotionalType = input;
		case "comments": this.comments = input;
		}
	}
		
	public void setNewDate(String time, Date newDate) {
		if(time.equals("start")) {
			this.promoStartingDate = newDate;
		} else {
			this.promoEndDate = newDate;
		}
	}
		
	public void setRepeatingPromo(boolean input) {
		this.repeatedPromoYA = input;
	}
	
}
