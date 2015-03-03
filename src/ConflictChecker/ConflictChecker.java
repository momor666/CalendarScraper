package ConflictChecker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import Email.SendMailTLS;
import HTMLWriter.HTMLWriter;
import Property.PropertyStructure;
import Scraper.AirbnbScraper;

public class ConflictChecker {

	PropertyStructure property;
	HTMLWriter html;
	
	public ConflictChecker(PropertyStructure p,HTMLWriter html){
		this.property = p;
		this.html = html;
	}
	
	public void checkConflicts(){
//		System.out.println(property.airbnb_availablity.size());
//		System.out.println(property.wimdu_availablity.size());
		HashSet<String> clone = new HashSet<String>(property.airbnb_availablity);
		
		//Resetting Conflicted Status
		property.conflict_detected = false;
		
		Iterator it = clone.iterator();
//		System.out.println("p:["+ property.holidayletting_name +"]");
		ArrayList<String> matchedList = new ArrayList<String>();
		while (it.hasNext()){
			String s = (String) it.next();
			if ( (property.airbnb_ihk_ical_link == null || property.airbnb_ihk_ical_link.equals("") || property.airbnb_ihk_availability.contains(s)) && (property.airbnb_ical_link == null || property.airbnb_ical_link.equals("") || property.airbnb_availablity.contains(s)) && (property.wimdu_ical_link == null || property.wimdu_ical_link.equals("") || property.wimdu_availablity.contains(s)) && (property.holidayletting_name==null || property.holidayletting_name.equals("") || property.holidayletting_availablity.contains(s)) && (property.bookingDotComPropertyId==null || property.bookingDotComPropertyId.equals("") || property.bookingdotcom_availablity.contains(s))){
				property.airbnb_availablity.remove(s);
				property.wimdu_availablity.remove(s);
				property.holidayletting_availablity.remove(s);
				property.bookingdotcom_availablity.remove(s);
				property.airbnb_ihk_availability.remove(s);
				matchedList.add(s);
			} else {
//				System.out.println(s+":" + Boolean.valueOf(property.airbnb_availablity.contains(s)).toString() + " "+ Boolean.valueOf(property.wimdu_availablity.contains(s)).toString() + " "+ Boolean.valueOf(property.holidayletting_name==null).toString() + " "+ Boolean.valueOf(property.holidayletting_name.equals("")).toString() + " "+ Boolean.valueOf(property.holidayletting_availablity.contains(s)).toString());
			}
		}
		
		
		html.addToHTML("<table class=\"table\">");
		html.addToHTML("	<div class=\"row\">");

		if ( property.airbnb_ihk_availability.size() !=0 || property.airbnb_availablity.size() != 0 || property.wimdu_availablity.size() !=0 || property.holidayletting_availablity.size()!=0  || property.bookingdotcom_availablity.size() != 0){
			property.conflict_detected = true;
		}
		
//		html.addToHTML("<div class=\"col-md-1\">");
//		html.addToHTML("<div class='text-center'>");
//		html.addToHTML("</div>");
//	    html.addToHTML("</div>");
//		
		html.addToHTML("<div class=\"col-md-2\">");
		html.addToHTML("<div class='text-center'>");
		html.addToHTML("<div class=\"alert alert-info\" role=\"alert\">");
		List matchedSortedList = new ArrayList(matchedList);
		Collections.sort(matchedSortedList);
	   	html.addToHTML("<button class=\"btn btn-xs	 btn-info\" type=\"button\">Reserved</button> " + matchedSortedList.toString().toString().replace(",", "<br>").replace("[", "<br>").replace("]", "") +"<br>");
	   	html.addToHTML("</div>");
		html.addToHTML("</div>");
	    html.addToHTML("</div>");

		html.addToHTML("<div class=\"col-md-2\">");
		html.addToHTML("<div class='text-center'>");
		if (property.airbnb_availablity.size() != 0){
		   	List sortedList = new ArrayList(property.airbnb_availablity);
		   	Collections.sort(sortedList);
		   	html.addToHTML("<div class=\"alert alert-danger\" role=\"alert\">");
		   	html.addToHTML("<button class=\"btn btn-xs	 btn-danger\" type=\"button\">Airbnb_Sam</button> " + sortedList.toString().toString().replace(",", "<br>").replace("[", "<br>").replace("]", "") +"<br>");
		   	html.addToHTML("</div>");
		} else if (property.airbnb_ical_link != null && !property.airbnb_ical_link.equals("")) {
		    	html.addToHTML("<div class=\"alert alert-success\" role=\"alert\">");
		    	html.addToHTML("<button class=\"btn btn-xs	 btn-success\" type=\"button\">Airbnb_Sam</button> <br>");
				html.addToHTML("</div>");
		}
	    html.addToHTML("</div>");
	    html.addToHTML("</div>");
	    
	    
	    html.addToHTML("<div class=\"col-md-2\">");
		html.addToHTML("<div class='text-center'>");
		if (property.airbnb_ihk_availability.size() != 0){
		   	List sortedList = new ArrayList(property.airbnb_ihk_availability);
		   	Collections.sort(sortedList);
		   	html.addToHTML("<div class=\"alert alert-danger\" role=\"alert\">");
		   	html.addToHTML("<button class=\"btn btn-xs	 btn-danger\" type=\"button\">Airbnb_IHK</button> " + sortedList.toString().toString().replace(",", "<br>").replace("[", "<br>").replace("]", "") +"<br>");
		   	html.addToHTML("</div>");
		} else if (property.airbnb_ihk_availability != null && !property.airbnb_ihk_availability.equals("")) {
		    	html.addToHTML("<div class=\"alert alert-success\" role=\"alert\">");
		    	html.addToHTML("<button class=\"btn btn-xs	 btn-success\" type=\"button\">Airbnb_IHK</button> <br>");
				html.addToHTML("</div>");
		}
	    html.addToHTML("</div>");
	    html.addToHTML("</div>");
	    
	    
	    
	    
	    html.addToHTML("<div class=\"col-md-2\">");
	    html.addToHTML("<div class='text-center'>");
	    if (property.wimdu_availablity.size() !=0){
	    	List sortedList = new ArrayList(property.wimdu_availablity);
	    	Collections.sort(sortedList);
	    	html.addToHTML("<div class=\"alert alert-danger\" role=\"alert\">");
	    	html.addToHTML("<button class=\"btn btn-xs	 btn-danger\" type=\"button\">Wimdu</button> " + sortedList.toString().replace(",", "<br>").replace("[", "<br>").replace("]", "")+"<br>");
	    	html.addToHTML("</div>");
	    } else if ( property.wimdu_ical_link != null && !property.wimdu_ical_link.equals("") ) {
	    	html.addToHTML("<div class=\"alert alert-success\" role=\"alert\">");
	    	html.addToHTML("<button class=\"btn btn-xs	 btn-success\" type=\"button\">Wimdu</button> <br>");
			html.addToHTML("</div>");
	    }
	    html.addToHTML("</div>");
	    html.addToHTML("</div>");
	    
	    
	    
	    html.addToHTML("<div class=\"col-md-2\">");
	    html.addToHTML("<div class='text-center'>");
	    if (property.holidayletting_availablity.size() !=0){
	    	List sortedList = new ArrayList(property.holidayletting_availablity);
	    	Collections.sort(sortedList);
	    	html.addToHTML("<div class=\"alert alert-danger\" role=\"alert\">");
	    	html.addToHTML("<button class=\"btn btn-xs	 btn-danger\" type=\"button\">Holiday Letting</button> " + sortedList.toString().replace(",", "<br>").replace("[", "<br>").replace("]", "")+"");
	    	html.addToHTML("</div>");
	    } else if (property.holidayletting_name!=null && !property.holidayletting_name.equals("")) {
	    	html.addToHTML("<div class=\"alert alert-success\" role=\"alert\">");
	    	html.addToHTML("<button class=\"btn btn-xs	 btn-success\" type=\"button\">Holiday Letting</button> ");
	    	html.addToHTML("</div>");
	    }
	    html.addToHTML("</div>");
	    html.addToHTML("</div>");
		    
	    
	    html.addToHTML("<div class=\"col-md-2\">");
	    html.addToHTML("<div class='text-center'>");
	    if (property.bookingdotcom_availablity.size() !=0){
	    	List sortedList = new ArrayList(property.bookingdotcom_availablity);
	    	Collections.sort(sortedList);
	    	html.addToHTML("<div class=\"alert alert-danger\" role=\"alert\">");
	    	html.addToHTML("<button class=\"btn btn-xs	 btn-danger\" type=\"button\">Booking.com</button> " + sortedList.toString().replace(",", "<br>").replace("[", "<br>").replace("]", "")+"");
	    	html.addToHTML("</div>");
	    } else if (property.bookingDotComPropertyId!=null && !property.bookingDotComPropertyId.equals("")){
	    	html.addToHTML("<div class=\"alert alert-success\" role=\"alert\">");
	    	html.addToHTML("<button class=\"btn btn-xs	 btn-success\" type=\"button\">Booking.com</button> ");
	    	html.addToHTML("</div>");
	    }
	    html.addToHTML("</div>");
	    html.addToHTML("</div>");
	    
	    
		html.addToHTML("</tr>");
		html.addToHTML("</table>");
		
		//Reset the availability for the property so that it is not carried over to the next cycle.
		property.airbnb_availablity.clear();
		property.wimdu_availablity.clear();
		property.holidayletting_availablity.clear();
		property.bookingdotcom_availablity.clear();
		property.airbnb_ihk_availability.clear();
		  
	}
}