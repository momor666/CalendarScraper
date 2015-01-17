package ConflictChecker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

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
		while (it.hasNext()){
			String s = (String) it.next();
			if (property.airbnb_availablity.contains(s) && property.wimdu_availablity.contains(s)){
				property.airbnb_availablity.remove(s);
				property.wimdu_availablity.remove(s);
			}
		}
		if (property.airbnb_availablity.size() != 0 || property.wimdu_availablity.size() !=0){
		    html.addToHTML("<div class=\"alert alert-danger\" role=\"alert\">");
		    if (property.airbnb_availablity.size() != 0){
//		    	html.addToHTML("<button class=\"btn btn-small btn-success\" type=\"button\">Airbnb </button>");
		    	html.addToHTML("<button class=\"btn btn-xs	 btn-danger\" type=\"button\">Airbnb</button> " + property.airbnb_availablity.toString().replace(",", "<br>").replace("[", "<br>").replace("]", "") +"<br>");
		    } else {
//		    	html.addToHTML("<button class=\"btn btn-small btn-danger\" type=\"button\">Airbnb </button>");
		    	html.addToHTML("<button class=\"btn btn-xs	 btn-success\" type=\"button\">Airbnb</button> <br>");
		    }
		    if (property.wimdu_availablity.size() !=0){
//		    	html.addToHTML("<button class=\"btn btn-small btn-success\" type=\"button\">Wimdu</button>");
		    	html.addToHTML("<button class=\"btn btn-xs	 btn-danger\" type=\"button\">Wimdu</button> " + property.wimdu_availablity.toString().replace(",", "<br>").replace("[", "<br>").replace("]", "")+"");
		    } else {
//		    	html.addToHTML("<button class=\"btn btn-small btn-danger\" type=\"button\">Wimdu</button>");
		    	html.addToHTML("<button class=\"btn btn-xs	 btn-success\" type=\"button\">Wimdu</button> ");
		    }
		    html.addToHTML("<br>");
		    
//			html.addToHTML("Wimdu" + property.wimdu_availablity +"<br>");
			html.addToHTML("</div>");
			
		} else {
			html.addToHTML("<div class=\"alert alert-success\">");
			html.addToHTML("<button class=\"btn btn-xs	 btn-success\" type=\"button\">Airbnb</button>");
		    html.addToHTML("<button class=\"btn btn-xs btn-success\" type=\"button\">Wimdu</button>");
			html.addToHTML("</div>");
			
		}
	}
}