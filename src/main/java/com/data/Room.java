package com.data;

/**
 * Room Class for Room Details
 * @author Dhanya Umapathi (Expleo)
 * @since 20 FEB 2024
 */
public class Room {
    private String id;
    private String type;
    private String ac;
    private double rate;
    private String status;
    
    
    public Room(){;} //No Para Constructor
    
    public Room(String id, String type, String ac, double rate, String status) {
        this.id = id;
        this.type = type;
        this.ac = ac;
        this.rate = rate;
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
    public String getAc() {
        return ac;
    }
    public void setAc(String ac) {
        this.ac = ac;
    }
    public double getRate() {
        return rate;
    }
    public void setRate(double rate) {
        this.rate = rate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
