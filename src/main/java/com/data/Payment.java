package com.data;

/**
 * Payment Class for Payment Details 
 * @author Dhanya Umapathi (Expleo)
 * @since 20 FEB 2024
 */
public class Payment {
    private String id;
    private String type;
    private double amount;
    private String bookingId;
    private String userId;
    private String status;
    
    public Payment() {
    }
	public Payment(String id, String type, double amount, String bookingId, String userId, String status) {
		super();
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.bookingId = bookingId;
		this.userId = userId;
		this.status = status;
	}
	public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getBookingId() {
        return bookingId;
    }
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    } 
//    
//    public abstract void processPayment();
//	public abstract void paymentDetails() ;
}
