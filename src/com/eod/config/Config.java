package com.eod.config;

import javax.naming.ConfigurationException;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;

public class Config {
	
	public static String baseUrl = null;
	public static String token = null;
	public static String defaultTicker = null;
	

	public static synchronized void init() throws org.apache.commons.configuration2.ex.ConfigurationException, ConfigurationException {
		
		Parameters params = new Parameters();
	    FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
	            new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
	                    .configure(params.properties()
	                            .setFileName("file.properties"));
	    Configuration conf = builder.getConfiguration();
	    try {
	    	baseUrl = conf.getProperty("baseUrl").toString();
	    }catch(NullPointerException e) {
	    	throw new NullPointerException("'baseUrl' must be set in the file.properties in the resources folder.");
	    }
	    try {
	    	token = conf.getProperty("token").toString();
	    }catch(NullPointerException e) {
	    	throw new NullPointerException("'token' must be set in the file.properties in the resources folder.");
	    }
	    try {
	    	defaultTicker = conf.getProperty("defaultTicker").toString();
	    }catch(NullPointerException e) {
	    	throw new NullPointerException("'defaultTicker' must be set in the file.properties in the resources folder.");
	    }
	    builder.save();
		

	}

}