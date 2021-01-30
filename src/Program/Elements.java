package Program;

import org.openqa.selenium.By;

public interface Elements {
	
	public static final String s = "s";
	
	//
	// TelephoneDirectories.us 
	
	public static final String td_search_button = "//*[@class='fas fa-search']";
	
	public static final String td_phoneNumber_text = "//*[@name='phone']";
	
	public static final String td_submit_button = "//*[@onclick='submit()']";
	
	public static final String td_caller_1 = "//*[@title='Go to full profile']";
	public static final String td_caller_1_location = "//*[contains(text(),'Locations')]//span";
	
	public static final String td_caller_2 = "(//*[@title='Go to full profile'])[2]";
	public static final String td_caller_2_location = "(//*[contains(text(),'Locations')]//span)[2]";

	public static final String td_caller_3 = "(//*[@title='Go to full profile'])[3]";
	public static final String td_caller_3_location = "(//*[contains(text(),'Locations')]//span)[3]";


	// FastPeopleSearch
	
	
	public static final String fps_phoneNumber_text = "//*[@type='tel']";
	
	public static final String fps_submit_button = "//*[@type='submit']";
	
	public static final String fps_caller = "//*[@class='card-title']//*[@class='larger']";
	
	public static final String fps_caller_location = "//*[@class='card-title']//*[@class='grey']";

	
	
	
	
	
	
	
	
}
