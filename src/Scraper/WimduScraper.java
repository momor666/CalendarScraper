package Scraper;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;

import Property.PropertyStructure;

public class WimduScraper {
	
	PropertyStructure property;
	private ArrayList<String> tmp = new ArrayList<String>();

	
	public WimduScraper(PropertyStructure property){
		this.property = property;
	}
	
	public void scrape() throws MalformedURLException, IOException, ParseException{
		//download calendar from airbnb
		downloadFromUrl(new URL(property.wimdu_ical_link),"/tmp/wimdu.ics");
//		System.out.println("Downlaod wimdu complete");
		loadIcsFile("/tmp/wimdu.ics");
//		System.out.println("Wimdu file parsed");
		updateAvailableDates();
//		System.out.println("Wimdu availablity updated");
	}
	
	void downloadFromUrl(URL url, String localFilename) throws IOException {
		
		StringBuffer output = new StringBuffer();
		FileWriter fw = new FileWriter (localFilename);
		PrintWriter pw = new PrintWriter(fw);
		Process p;
		try {
			p = Runtime.getRuntime().exec("curl " + url.toString());
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        String line = "";			
			while ((line = reader.readLine())!= null) {
				pw.println(line);
			}
			pw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
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
//		System.out.println("Here:"+tmp);
		
	}
	
	void updateAvailableDates() throws ParseException{
		for (int i = tmp.size()-1; i >= 0; i=i-2){
			SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd"); 			
			Date sDate = dt.parse(tmp.get(i));
			Date eDate = dt.parse(tmp.get(i-1));
			DateTime startDate = new DateTime(sDate);
			DateTime endDate = new DateTime(eDate);
			if (!(endDate.plusDays(2)).isBefore(System.currentTimeMillis())){
//				System.out.println("START:"+startDate);
//				System.out.println("END:"+endDate);
				if (startDate.withTimeAtStartOfDay().equals(endDate.withTimeAtStartOfDay())){
					property.add_Wimdu_Availablity(startDate.toString().split("T")[0]);
//					System.out.println(startDate.toString().split("T")[0]);
				} else {
					while(!startDate.withTimeAtStartOfDay().equals(endDate.withTimeAtStartOfDay())){
//						System.out.println(startDate);
						if (!(startDate.plusDays(2)).isBefore(System.currentTimeMillis())){
							property.add_Wimdu_Availablity(startDate.toString().split("T")[0]);
//							System.out.println(startDate.toString().split("T")[0]);	
						}
						startDate = startDate.plusDays(1);
					}
				}
			}
		}
	}
	
	
}
