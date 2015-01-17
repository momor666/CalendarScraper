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
		Iterator it = clone.iterator();
//		System.out.println("p:["+ property.holidayletting_name +"]");
		while (it.hasNext()){
			String s = (String) it.next();
			if (property.airbnb_availablity.contains(s) && property.wimdu_availablity.contains(s) && (property.holidayletting_name==null || property.holidayletting_name.equals("") || property.holidayletting_availablity.contains(s))){
				property.airbnb_availablity.remove(s);
				property.wimdu_availablity.remove(s);
				property.holidayletting_availablity.remove(s);
			} else {
//				System.out.println(s+":" + Boolean.valueOf(property.airbnb_availablity.contains(s)).toString() + " "+ Boolean.valueOf(property.wimdu_availablity.contains(s)).toString() + " "+ Boolean.valueOf(property.holidayletting_name==null).toString() + " "+ Boolean.valueOf(property.holidayletting_name.equals("")).toString() + " "+ Boolean.valueOf(property.holidayletting_availablity.contains(s)).toString());
			}
		}
		if (property.airbnb_availablity.size() != 0 || property.wimdu_availablity.size() !=0 || property.holidayletting_availablity.size()!=0 ){
		    html.addToHTML("<div class=\"alert alert-danger\" role=\"alert\">");
		    if (property.airbnb_availablity.size() != 0){
		    	List sortedList = new ArrayList(property.airbnb_availablity);
		    	Collections.sort(sortedList);
		    	html.addToHTML("<button class=\"btn btn-xs	 btn-danger\" type=\"button\">Airbnb</button> " + sortedList.toString().toString().replace(",", "<br>").replace("[", "<br>").replace("]", "") +"<br>");
		    } else {
		    	html.addToHTML("<button class=\"btn btn-xs	 btn-success\" type=\"button\">Airbnb</button> <br>");
		    }
		    if (property.wimdu_availablity.size() !=0){
		    	List sortedList = new ArrayList(property.wimdu_availablity);
		    	Collections.sort(sortedList);
		    	html.addToHTML("<button class=\"btn btn-xs	 btn-danger\" type=\"button\">Wimdu</button> " + sortedList.toString().replace(",", "<br>").replace("[", "<br>").replace("]", "")+"<br>");
		    } else {
		    	html.addToHTML("<button class=\"btn btn-xs	 btn-success\" type=\"button\">Wimdu</button> <br>");
		    }
		    if (property.holidayletting_availablity.size() !=0){
		    	List sortedList = new ArrayList(property.holidayletting_availablity);
		    	Collections.sort(sortedList);
		    	html.addToHTML("<button class=\"btn btn-xs	 btn-danger\" type=\"button\">Holiday Letting</button> " + sortedList.toString().replace(",", "<br>").replace("[", "<br>").replace("]", "")+"");
		    } else {
		    	html.addToHTML("<button class=\"btn btn-xs	 btn-success\" type=\"button\">Holiday Letting</button> ");
		    }
		    html.addToHTML("<br>");
			html.addToHTML("</div>");
			
		} else {
			html.addToHTML("<div class=\"alert alert-success\">");
			html.addToHTML("<button class=\"btn btn-xs	 btn-success\" type=\"button\">Airbnb</button>");
		    html.addToHTML("<button class=\"btn btn-xs btn-success\" type=\"button\">Wimdu</button>");
		    html.addToHTML("<button class=\"btn btn-xs btn-success\" type=\"button\">Holiday Letting</button>");
			html.addToHTML("</div>");
			
		}
	}
}