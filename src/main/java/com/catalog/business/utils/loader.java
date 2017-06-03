package com.catalog.business.utils;

import java.util.Properties;

public class loader {

	public static Properties properties;

        public static String host;
        public static String username;
        public static String password;
        public static String database;
	public void init() {
		try {

                    //System.out.println( getClass().getResource(getClass().getSimpleName() + ".class") );
			if (properties == null) {
				properties = new Properties();
                                properties.load(getClass().getResourceAsStream("/newproperties.properties")); 
                                host=properties.getProperty("host");
                                username=properties.getProperty("username");
                                password=properties.getProperty("password");
                                database=properties.getProperty("databaseName");
				//System.out.println(username + password);
			}
		} catch (Exception e) {
                    System.out.println("Fajl nije nadjen!");
			e.printStackTrace();
                }

	}
    public static void main(String[] args)
    {
        new loader().init();
    }

}