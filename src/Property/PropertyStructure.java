package Property;


import java.util.HashSet;

public class PropertyStructure {
	public String airbnb_name;
	public String airbnb_ical_link;
	public String wimdu_name;
	public String wimdu_ical_link;
	public String holidayletting_name;
	
	public HashSet<String> airbnb_availablity = new HashSet<String>();
	public HashSet<String> wimdu_availablity = new HashSet<String>();
	public HashSet<String> holidayletting_availablity = new HashSet<String>();
	
	
	public PropertyStructure() {
	}
	
	public void add_Airbnb_Availablity(String date){
//		System.out.println(date);
		this.airbnb_availablity.add(date);
		
	}
	
	public void add_Wimdu_Availablity(String date){	
//		System.out.println(date);
		this.wimdu_availablity.add(date);
	}
	
	public void add_HolidayLetting_Availablity(String date){
//		System.out.println(date);
		this.holidayletting_availablity.add(date);
	}
}



