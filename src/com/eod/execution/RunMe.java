package com.eod.execution;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.naming.ConfigurationException;
import com.eod.api.RestApiCall;
import com.eod.result.Result;

public class RunMe {
	
	public static void main(String[] args) throws ConfigurationException, org.apache.commons.configuration2.ex.ConfigurationException {
		// TODO Auto-generated method stub
		
		try {
			String ticker = "AAPL.US";
			
			/*	SimpleDateFormatter has a known "feature" that if the days added are more than in that month 
				it will move the date forward enough months to absorb the excess of days. 
				Validation could be written to handle this in a production project.  
			
			*/
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(RestApiCall.DATE_PATTERN);
			Date fromDt = simpleDateFormat.parse("2019-01-01");
			Date toDt =  simpleDateFormat.parse("2019-12-01");
			
			
			System.out.println("Calculating...");
			Result eodPrices = new RestApiCall().GetEodPrices(ticker, fromDt, toDt);
			
			if (eodPrices.getPrices().size()>0) {
				System.out.println("Average Share Price (in USD cents) based on daily High and Low is: " + eodPrices.getAveragePrice().toString());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			 
			e.printStackTrace();
		}
	}
	
	
	

}
