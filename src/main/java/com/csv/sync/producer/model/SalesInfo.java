package com.csv.sync.producer.model;

public class SalesInfo {
	private String timestamp;
    private String ver;
    private String productFamily;
    private String country;
    private String deviceType;
    private String os;
    private String checkoutFailureCount;
    private String paymentApiFailureCount;
    private String purchaseCount;
    private String revenue;
    
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getProductFamily() {
		return productFamily;
	}
	public void setProductFamily(String productFamily) {
		this.productFamily = productFamily;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getCheckoutFailureCount() {
		return checkoutFailureCount;
	}
	public void setCheckoutFailureCount(String checkoutFailureCount) {
		this.checkoutFailureCount = checkoutFailureCount;
	}
	public String getPaymentApiFailureCount() {
		return paymentApiFailureCount;
	}
	public void setPaymentApiFailureCount(String paymentApiFailureCount) {
		this.paymentApiFailureCount = paymentApiFailureCount;
	}
	public String getPurchaseCount() {
		return purchaseCount;
	}
	public void setPurchaseCount(String purchaseCount) {
		this.purchaseCount = purchaseCount;
	}
	public String getRevenue() {
		return revenue;
	}
	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}
	
	@Override
	public String toString() {
		return "SalesInfo [timestamp=" + timestamp + ", ver=" + ver + ", productFamily=" + productFamily + ", country="
				+ country + ", deviceType=" + deviceType + ", os=" + os + ", checkoutFailureCount="
				+ checkoutFailureCount + ", paymentApiFailureCount=" + paymentApiFailureCount + ", purchaseCount="
				+ purchaseCount + ", revenue=" + revenue + "]";
	}
	
	
    
}
