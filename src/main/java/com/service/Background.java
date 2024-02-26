package com.service;

import com.database.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * The Background class represents a background service for updating booking and room statuses.
 */

public class Background {
    private final ScheduledExecutorService bookingTableUpdate = Executors.newScheduledThreadPool(1);

    public Background(){
        bookingTableUpdate.scheduleAtFixedRate(Background::bookingUpdate, 0, 2, TimeUnit.SECONDS);
    }

    // Updates the booking and room statuses based on certain conditions.
    public static void bookingUpdate(){
        try(PreparedStatement sm = Database.prepareStatement("UPDATE Booking SET STATUS='COMPLETE' WHERE ENDDATE<=SYSDATE AND STATUS <> 'COMPLETE'");
            PreparedStatement rm = Database.prepareStatement("UPDATE Room SET STATUS='AVAILABLE' WHERE ROOM_NUMBER NOT IN (SELECT ROOM_NUM FROM Booking WHERE STATUS='BOOKED' OR STATUS='RESERVED')")){
                sm.executeUpdate();
                rm.executeUpdate();
        } catch (SQLException e){
            System.out.println(e);
        }
    }  


    public static void main(String args[]){
        bookingUpdate();
    }
}