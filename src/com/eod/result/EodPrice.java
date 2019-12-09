package com.eod.result;

import java.math.BigInteger;
import java.util.Date;

public class EodPrice {
	private Date date;
	private Float open;
	private Float high;
	private Float low;
	private Float close;
	private Float adjustedClose;
	private BigInteger volume;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Float getOpen() {
		return open;
	}
	public void setOpen(Float open) {
		this.open = open;
	}
	public Float getHigh() {
		return high;
	}
	public void setHigh(Float high) {
		this.high = high;
	}
	public Float getLow() {
		return low;
	}
	public void setLow(Float low) {
		this.low = low;
	}
	public Float getClose() {
		return close;
	}
	public void setClose(Float close) {
		this.close = close;
	}
	public Float getAdjustedClose() {
		return adjustedClose;
	}
	public void setAdjustedClose(Float adjustedClose) {
		this.adjustedClose = adjustedClose;
	}
	public BigInteger getVolume() {
		return volume;
	}
	public void setVolume(BigInteger volume) {
		this.volume = volume;
	}

}
