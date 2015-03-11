package Scraper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.bcel.generic.DDIV;
import org.joda.time.DateTime;

import Property.PropertyStructure;

	 
	

public class AirbnbScraper3 {
	PropertyStructure property;
	private ArrayList<String> tmp = new ArrayList<String>();
	
	public AirbnbScraper3(PropertyStructure p){
		this.property = p;
	}
	
	public void scrape() throws MalformedURLException, IOException, ParseException{
		if (property.airbnb_sar_ical_link == null || property.airbnb_sar_ical_link.equals(""))
			return;
		
//		System.out.println("does not get here");
		
		//download calendar from airbnb
		downloadFromUrl(new URL(property.airbnb_sar_ical_link),"/tmp/airbnb3.ics");
//		System.out.println("Downlaod airbnb complete");
		loadIcsFile("/tmp/airbnb3.ics");
//		System.out.println("Airbnb file parsed");
		updateAvailableDates();
//		System.out.println("Airbnb availablity updated");
		
	}
	
	
	void downloadFromUrl(URL url, String localFilename) throws IOException {
		File f = new File(localFilename);
		if (f.exists()) f.delete();
	    InputStream is = null;
	    FileOutputStream fos = null;

	    try {
	        URLConnection urlConn = url.openConnection();//connect

	        is = urlConn.getInputStream();               //get connection inputstream
	        fos = new FileOutputStream(localFilename);   //open outputstream to local file

	        byte[] buffer = new byte[4096];              //declare 4KB buffer
	        int len;

	        //while we have availble data, continue downloading and storing to local file
	        while ((len = is.read(buffer)) > 0) {  
	            fos.write(buffer, 0, len);
	        }
	    } finally {
	        try {
	            if (is != null) {
	                is.close();
	            }
	        } finally {
	            if (fos != null) {
	                fos.close();
	            }
	        }
	    }
	}
	
	void loadIcsFile(String file) throws IOException{
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line ="";
		while((line= br.readLine())!=null){
			if (line.contains("DTEND") || line.contains("DTSTART")){
				line = line.split(":")[1];
				tmp.add(line);
			}
		}
		br.close();
		fr.close();
	}
	
	void updateAvailableDates() throws ParseException{
		for (int i = tmp.size()-1; i >= 0; i=i-2){
			SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd"); 			
			Date sDate = dt.parse(tmp.get(i));
			Date eDate = dt.parse(tmp.get(i-1));
			DateTime startDate = new DateTime(sDate);
			DateTime endDate = new DateTime(eDate);
			if (!(endDate.plusDays(2)).isBefore(System.currentTimeMillis())){
//				System.out.println("looking:"+startDate);
//				System.out.println("looking:"+endDate);
				while(!startDate.withTimeAtStartOfDay().equals(endDate.withTimeAtStartOfDay())){
//					System.out.println(startDate);
					if (!(startDate.plusDays(1)).isBefore(System.currentTimeMillis()))
						property.add_Airbnb_sar_Availability(startDate.toString().split("T")[0]);
					startDate = startDate.plusDays(1);
				}
			}
		}
	}
}
