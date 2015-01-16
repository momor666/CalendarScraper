package ConflictChecker;

import java.util.HashSet;
import java.util.Iterator;

import Property.PropertyStructure;

public class ConflictChecker {

	PropertyStructure property;
	
	public ConflictChecker(PropertyStructure p){
		this.property = p;
	}
	
	public void checkConflicts(){
		System.out.println(property.airbnb_availablity.size());
		System.out.println(property.wimdu_availablity.size());
		HashSet<String> clone = new HashSet<String>(property.airbnb_availablity);
		Iterator it = clone.iterator();
		while (it.hasNext()){
			String s = (String) it.next();
			if (property.airbnb_availablity.contains(s) && property.wimdu_availablity.contains(s)){
				property.airbnb_availablity.remove(s);
				property.wimdu_availablity.remove(s);
			}
		}
		
		System.out.println(property.airbnb_availablity);
		System.out.println(property.wimdu_availablity);	
	}
}
