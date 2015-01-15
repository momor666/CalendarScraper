package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationUtil {
	private String airbnb_username;
	private String airbnb_password;
	
	{
		Properties properties = loadProperties();
		if (properties != null){
			airbnb_username = properties.getProperty("airbnb_username");
			airbnb_password = properties.getProperty("airbnb_password");
		}
	}
	
	
	
	public String getAirbnb_username() {
		return airbnb_username;
	}



	public String getAirbnb_password() {
		return airbnb_password;
	}



	private static Properties loadProperties() {
		Properties properties = new Properties();
		try {
			FileInputStream is = new FileInputStream("~/Desktop/config.properties");
			properties.load(is);
			is.close();
		} catch (IOException io) {
			properties = null;
		}
		return properties;
	}

	
	
}
