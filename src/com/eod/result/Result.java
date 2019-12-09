package com.eod.result;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Result {

	private ArrayList<EodPrice> prices = new ArrayList<EodPrice>();

	public ArrayList<EodPrice> getPrices() {
		return prices;
	}

	public void setPrices(ArrayList<EodPrice> prices) {
		this.prices = prices;
	}
	
	public void addPrice(EodPrice price) {
		this.prices.add(price);
	}
	
	public BigInteger getAveragePrice() {
		
		BigDecimal totalAverage = new BigDecimal(0);
		for (EodPrice temp : getPrices()) {
			totalAverage =  totalAverage.add(BigDecimal.valueOf((temp.getHigh() + temp.getLow())/2));
		}
		
		
		if(totalAverage.intValue()!=0) {
			totalAverage = totalAverage.multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(getPrices().size()), 0, RoundingMode.HALF_UP);
		}
		
		
		return totalAverage.toBigInteger();

	}

	
}
