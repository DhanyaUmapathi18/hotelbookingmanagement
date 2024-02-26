package com.data;

import java.sql.Date;

/**
 * Booking Class for Booking Details
 * @author Dhanya Umapathi (Expleo)
 * @since 20 FEB 2024
 */
public class Booking {
    private String id;
    private String bookedBy;
    private String roomId;
    private Date startDate;
    private Date endDate;
    private String status;

    public Booking(){;}

    public Booking(String id, String bookedBy, String roomId, Date startDate, Date endDate, String status) {
        this.id = id;
        this.bookedBy = bookedBy;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }
    private int no_of_days;
    private double amount;
    
    public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getBookedBy() {
        return bookedBy;
    }
    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }
    public String getRoomId() {
        return roomId;
    }
    public int getNo_of_days() {
		return no_of_days;
	}
	public void setNo_of_days(int no_of_days) {
		this.no_of_days = no_of_days;
	}
	public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    } 
}
