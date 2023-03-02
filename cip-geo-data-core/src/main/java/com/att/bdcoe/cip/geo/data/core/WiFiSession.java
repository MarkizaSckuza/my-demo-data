package com.att.bdcoe.cip.geo.data.core;


import java.text.SimpleDateFormat;
import java.util.Date;

public class WiFiSession {

    private String sessionID;
    private String venueCode;
    private String clientMAC;
    private Date startDate;
    private Date stopDate;
    private long bytesTransmitted;
    private long bytesReceived;
    private String paymentMethod;
    private String realm;
    private String userAgent;
    private String paymentIdentifier;
    private String phoneNumber;
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
    public static SimpleDateFormat DS_FORMAT = new SimpleDateFormat("yyyyMMddHH");

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getVenueCode() {
        return venueCode;
    }

    public void setVenueCode(String venueCode) {
        this.venueCode = venueCode;
    }

    public String getClientMAC() {
        return clientMAC;
    }

    public void setClientMAC(String clientMAC) {
        this.clientMAC = clientMAC;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public long getBytesTransmitted() {
        return bytesTransmitted;
    }

    public void setBytesTransmitted(long bytesTransmitted) {
        this.bytesTransmitted = bytesTransmitted;
    }

    public long getBytesReceived() {
        return bytesReceived;
    }

    public void setBytesReceived(long bytesReceived) {
        this.bytesReceived = bytesReceived;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getPaymentIdentifier() {
        return paymentIdentifier;
    }

    public void setPaymentIdentifier(String paymentIdentifier) {
        this.paymentIdentifier = paymentIdentifier;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString(){
        StringBuffer output = new StringBuffer();
        output.append(this.getSessionID()+"|");
        output.append(this.getVenueCode()+"|");
        output.append(this.getClientMAC()+"|");
        output.append(DATE_FORMAT.format(this.getStartDate())+"|");
        output.append(DATE_FORMAT.format(this.getStopDate())+"|");
        output.append(this.getBytesTransmitted()+"|");
        output.append(this.getBytesReceived()+"|");
        output.append(this.getPaymentMethod()+"|");
        output.append(this.getRealm()+"|");
        output.append(this.getUserAgent()+"|");
        output.append(this.getPaymentIdentifier()+"|");
        output.append(this.getPhoneNumber()+"|");
        output.append(DS_FORMAT.format(this.getStopDate())+"\n");
        return output.toString();
    }
}
