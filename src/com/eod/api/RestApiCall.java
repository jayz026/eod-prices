package com.eod.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.ConfigurationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eod.config.Config;
import com.eod.result.EodPrice;
import com.eod.result.Result;

public class RestApiCall {
	public 	static final String DATE_PATTERN = "yyyy-MM-dd";
	
	public RestApiCall() throws ConfigurationException, org.apache.commons.configuration2.ex.ConfigurationException{
		Config.init();
	}
	

	public synchronized Result GetEodPrices(String pIn_Ticker, Date pIn_FromDt, Date pIn_ToDt) throws IOException {
		
		Result response = new Result();
		
		
		String tmpResponse = MyGETRequest(pIn_Ticker, pIn_FromDt, pIn_ToDt);
		
		if(tmpResponse!=null) {
			response = GetEodPrices(tmpResponse);
		}
		
		return response;
	
	}
	
	private String MyGETRequest(String pIn_Ticker, Date pIn_FromDt, Date pIn_ToDt) throws IOException {
		
        StringBuffer response = new StringBuffer();
		
		String dateRangeCriteria = "";
		//I only add the date range criteria if both dates are populated. (the site supports both)
		if((pIn_FromDt != null) && (pIn_ToDt != null)) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
			
			dateRangeCriteria = "&period=d.&from=" + simpleDateFormat.format(pIn_FromDt) + "&to=" + simpleDateFormat.format(pIn_ToDt);
		}
		
		//I would put proper validation around this using enums or somethin in a production environment, 
		//but for the demo, this is probably fine.
		if(pIn_Ticker==null||pIn_Ticker.isEmpty()||pIn_Ticker.equals("")) {
			pIn_Ticker = Config.defaultTicker;
		}
			
	    URL urlForGetRequest = new URL(Config.baseUrl + pIn_Ticker + "?api_token=" + Config.token + dateRangeCriteria + "&fmt=json");
	    //System.out.println(urlForGetRequest.toString());
	    String readLine = null;
	    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
	    conection.setRequestMethod("GET");
	    int responseCode = conection.getResponseCode();
	    if (responseCode == HttpURLConnection.HTTP_OK) {
	        BufferedReader in = new BufferedReader(
	            new InputStreamReader(conection.getInputStream()));
	        while ((readLine = in .readLine()) != null) {
	            response.append(readLine);
	        } in .close();
	    } else {
	    	try {
	    		System.out.println("Problem calling eodhistoricaldata.com API. Response Code: " + conection.getResponseCode() + ", Response Message: " + conection.getResponseMessage());
	    		return null;
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    }
	    return response.toString();
	}
	
	private Result GetEodPrices(String pIn_Results) {
		
		JSONArray  jsonResponseArr = new JSONArray ();
		try {
			jsonResponseArr = new JSONArray (pIn_Results);
		} catch (JSONException e) {
			System.out.println("Problem trying to case the response String to a JSONArray in " + Class.class.getName());
			e.printStackTrace();
		}
		
		Result response = new Result();
		
		try {
		    for (int i = 0; i < jsonResponseArr.length(); i++) {
		    	EodPrice tmpEodPrice = GetEodPriceFromJson(jsonResponseArr.getJSONObject(i));
		    	/*	if there are any problems populating the the high and low price 
		    		I will just exclude them to prevent a distortion from incorrect data.
		    		If the price during the day was a 0, this will ignore the record. A business decision would have to be made on this.
		    	*/
		    	if(tmpEodPrice!=null&tmpEodPrice.getHigh()!=0&tmpEodPrice.getLow()!=0) {
		    		response.addPrice(tmpEodPrice);
		    	}
		    }
		} catch (JSONException e) {
		    e.printStackTrace();
		}
		
		return response;
	
	}
	
	private EodPrice GetEodPriceFromJson(JSONObject pIn_JsonPrice) {
		
		EodPrice eodPrice = new EodPrice();
		
		try {
			eodPrice.setHigh(pIn_JsonPrice.getBigDecimal("high").floatValue()); 
			eodPrice.setLow(pIn_JsonPrice.getBigDecimal("low").floatValue());
			
		} catch (JSONException e) {
			System.out.println("Problem trying parse the JSONObject element into a specific EodPrice Object" + Class.class.getName());
			e.printStackTrace();
			return null;
		}
		
		return eodPrice;
	
	}

}
