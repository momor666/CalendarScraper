package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import Property.PropertyStructure;

public class ConfigurationUtil {	
	private ArrayList<PropertyStructure> propertyList = new ArrayList<PropertyStructure>();
	private int numberOfProperties;
	
	{
		Properties properties = loadProperties();
		if (properties != null){
			numberOfProperties = Integer.parseInt(properties.getProperty("number_of_properties"));
			for (int i =0; i < numberOfProperties;i++){
				PropertyStructure property = new PropertyStructure();
				property.airbnb_name = properties.getProperty("p"+(i+1)+"_airbnb_name");
				property.airbnb_ical_link = properties.getProperty("p"+(i+1)+"_airbnb_ical_link");
				property.wimdu_name = properties.getProperty("p"+(i+1)+"_wimdu_name");
				property.wimdu_ical_link = properties.getProperty("p"+(i+1)+"_wimdu_ical_link");
				property.holidayletting_name = properties.getProperty("p"+(i+1)+"_holidayletting_name");
				propertyList.add(property);
			}
			
				
		}
	}
	

	public ArrayList<PropertyStructure> getPropertyList() {
		return propertyList;
	}

	private static Properties loadProperties() {
		Properties properties = new Properties();
		try {
			FileInputStream is = new FileInputStream("/tmp/config.properties");
			properties.load(is);
			is.close();
		} catch (IOException io) {
			properties = null;
		}
		return properties;
	}

	public void printConfig(){
		System.out.println("Number of Properties:"+numberOfProperties);
		for (int i =0; i < propertyList.size();i++){
			System.out.println("Property"+(i+1));
			System.out.println(propertyList.get(i).toString());
		}
	}
	
	
}