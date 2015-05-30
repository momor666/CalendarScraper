package IgnoreListProcessor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Property.PropertyStructure;

public class ProcessIgnoreList {

	public static void processIgnoreList(PropertyStructure p, int propertyNumber){
		
		
		//Load ignore list file.
		try {
			FileReader fr = new FileReader ("/tmp/ignore.list");
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			
			while ((line = br.readLine()) != null){
				String data[]  = line.split(",");
				if (data[0].equals(((Integer)propertyNumber).toString())){
					switch (data[1]){
					case "Airbnb_Sam":
						p.airbnb_availablity.remove(data[2]);
						break;
					case "Airbnb_IHK":
						p.airbnb_ihk_availability.remove(data[2]);
						break;
					case "Airbnb_SAR":
						p.airbnb_sar_availability.remove(data[2]);
						break;
					case "Wimdu":
						p.wimdu_availablity.remove(data[2]);
						break;
					case "Holiday_Letting":
						p.holidayletting_availablity.remove(data[2]);
						break;
					case "Booking.com":
						p.bookingdotcom_availablity.remove(data[2]);
						break;
					}	
				}
			}
			
			br.close();
			fr.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	
}
