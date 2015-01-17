package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import Property.PropertyStructure;

public class ConfigurationUtil {	
	private ArrayList<PropertyStructure> propertyList = new ArrayList<PropertyStructure>();
	private int numberOfProperties;
	private String html_output_file;
	private String email_from;
	private String email_password;
	private String email_to;
	private String web;
	
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
			
			html_output_file = properties.getProperty("html_output_file");
			email_from = properties.getProperty("email_from");
			email_password = properties.getProperty("email_password");
			email_to = properties.getProperty("email_to");
			web = properties.getProperty("web");
		}
	}
	
	public String getWeb() {
		return web;
	}

	public String getEmail_from() {
		return email_from;
	}

	public String getEmail_password() {
		return email_password;
	}

	public String getEmail_to() {
		return email_to;
	}

	public String getHtml_output_file() {
		return html_output_file;
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
