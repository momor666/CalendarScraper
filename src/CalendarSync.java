import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;

import Scraper.AirbnbScraper;
import Scraper.HolidayLettingScraper;
import Scraper.WimduScraper;
import util.ConfigurationUtil;




public class CalendarSync {
	public static void main(String[] args) throws MalformedURLException, IOException, ParseException{
		//Load Properties
		ConfigurationUtil config = new ConfigurationUtil();
//		config.printConfig();
		
		//Scrape Properties
		for (int i =0; i < config.getPropertyList().size() ; i++){
			//scrape Airbnb
			AirbnbScraper airbnb = new AirbnbScraper(config.getPropertyList().get(i));
			airbnb.scrape();
			//scrape wimdu
			WimduScraper wimdu = new WimduScraper(config.getPropertyList().get(i));
			wimdu.scrape();
			//scrape holidayletting
			HolidayLettingScraper holidayletting = new HolidayLettingScraper(config.getPropertyList().get(i));
		}
	}
}
