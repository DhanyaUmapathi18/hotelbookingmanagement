package com.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import com.data.Booking;

/*
* The Book Manager class implements an application that 
* simply have the operations of customer's database 
* @author Dhanya Umapathi (Expleo)
* @since 20 FEB 2024
*
* 
*/
public class BookManager {
	   // Booking
    public static boolean roomBooking(Booking book) {
        String QUERY_INSERT = "INSERT INTO BOOKING VALUES(?,?,?,?,?,?)";
        String QUERY_UPDATE_ROOM = "UPDATE ROOM SET STATUS = 'UNAVAILABLE' WHERE ROOM_NUMBER = ?";
        

        int insert_rows =0 ,update_room_rows = 0;
        try (PreparedStatement insert_statement = Database.prepareStatement(QUERY_INSERT);
                PreparedStatement update_room_statement = Database.prepareStatement(QUERY_UPDATE_ROOM);
            ) {

            insert_statement.setString(1, book.getId());
            insert_statement.setString(2, book.getBookedBy());
            insert_statement.setString(3, book.getRoomId());
            insert_statement.setDate(4, book.getStartDate());
            insert_statement.setDate(5, book.getEndDate());
            insert_statement.setString(6, "RESERVED");
          //  insert_statement.setDouble(7, 0.0);
            update_room_statement.setString(1, book.getRoomId());
            

             insert_rows = insert_statement.executeUpdate();
             update_room_rows = update_room_statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
        }
        return insert_rows>0 &&update_room_rows>0;
    }

    //BookingId
    public static String getBookingId(String cus_Id,String roomNumber) {
        String ID = "";
        String SELECT_QUERY = "SELECT BOOK_ID FROM BOOKING WHERE BOOKEDBY = ?  AND ROOM_NUM = ?";
        try (PreparedStatement statement = Database.prepareStatement(SELECT_QUERY)) {
            statement.setString(1, cus_Id);
            statement.setString(2,roomNumber);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                ID = resultSet.getString("ID");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
        }
        return ID;
    }
    
    
    //cancel Booking
    public static boolean cancelBooking(Booking book,String acc_id) {
        final String QUERY_UPDATE = "UPDATE BOOKING SET STATUS = 'CANCELLED' WHERE BOOK_ID = ?";
        final String QUERY_UPDATE_ROOM = "UPDATE ROOM SET STATUS = 'AVAILABLE' WHERE ROOM_NUMBER = ?";
        final String QUERY_DELETE_PAYMENT = "UPDATE PAYMENT SET STATUS = 'REFUND' WHERE PAYMENT_ID=?";
        try (PreparedStatement statement = Database.prepareStatement(QUERY_UPDATE);
             PreparedStatement room_statement = Database.prepareStatement(QUERY_UPDATE_ROOM);
              PreparedStatement payment_statement_update = Database.prepareStatement(QUERY_DELETE_PAYMENT);

        		) {

            statement.setString(1, book.getId());
            System.out.println(book.getRoomId());
            int update_rows = statement.executeUpdate();

            room_statement.setString(1, book.getRoomId());
            int room_update_rows = room_statement.executeUpdate();
            payment_statement_update.setString(1,acc_id);
            int rows = payment_statement_update.executeUpdate();
            if (update_rows > 0 && room_update_rows > 0 && rows > 0) {
                System.out.println("Booking cancelled, room status updated, payment refunded.");
                return true;
            } else {
                System.out.println("Failed to update");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    //To check room available
    public static boolean isRoomAvailable(String roomId) {
        String QUERY_CHECK = "SELECT COUNT(*) FROM ROOM WHERE ROOM_NUMBER = ?";
 
        try (PreparedStatement statement = Database.prepareStatement(QUERY_CHECK)) {
            statement.setString(1, roomId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
        }
        return false;
    }
    
    //TotalCost
    public static double totalCost(String cus_Id) throws SQLException {
        double rate = 0.0;
        double totalDays = 0.0 , cost = 0.0;
        String SELECT_QUERY = "SELECT RATE FROM ROOM WHERE ROOM_NUMBER = (SELECT ROOM_NUM FROM BOOKING WHERE BOOKEDBY = ? AND STATUS ='RESERVED')";
        String QUERY_SELECT = "SELECT STARTDATE, ENDDATE FROM BOOKING WHERE BOOKEDBY = ?";
       // String QUERY_UPDATE = "UPDATE BOOKING SET AMOUNT = ? WHERE ID = (SELECT ROOMID FROM BOOKING WHERE BOOKEDBY = ?)";
        try (PreparedStatement statement = Database.prepareStatement(SELECT_QUERY);
             PreparedStatement selectStatement = Database.prepareStatement(QUERY_SELECT)) {
            statement.setString(1, cus_Id);
         //   statement.setString(2, endDate);
            selectStatement.setString(1, cus_Id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                rate = resultSet.getDouble("RATE");
            }
            ResultSet selectResultSet = selectStatement.executeQuery();
            if (selectResultSet.next()) {
                LocalDate startDate = selectResultSet.getDate("STARTDATE").toLocalDate();
                LocalDate endDate = selectResultSet.getDate("ENDDATE").toLocalDate();
                totalDays = ChronoUnit.DAYS.between(startDate, endDate);
            }
             cost = rate * (totalDays+1);
        }

        return cost;
    }
    
    //Auto increment the value for reservation
    public static String getNextId() throws SQLException {
        String QUERY = String.format("SELECT COUNT(*) FROM BOOKING");
        try (PreparedStatement maxIdStatement = Database.prepareStatement(QUERY);
                ResultSet resultSet = maxIdStatement.executeQuery()) {
            if (resultSet.next()) {
                return "RES" + (resultSet.getInt(1) + 1);
            }
        }
        return "RES1";
    }

    public static ArrayList<Booking> getBookingsOf(String cusID) throws SQLException{
        ArrayList<Booking> bookings = new ArrayList<>();
        try(PreparedStatement state = Database.prepareStatement("SELECT * FROM BOOKING WHERE BOOKEDBY = ?")){
            state.setString(1,cusID);
            ResultSet resultSet = state.executeQuery();

            while(resultSet.next()){
                bookings.add(new Booking(
                    resultSet.getString("BOOK_ID"),
                    resultSet.getString("BOOKEDBY"),
                    resultSet.getString("ROOM_NUM"),
                    resultSet.getDate("STARTDATE"),
                    resultSet.getDate("ENDDATE"),
                    resultSet.getString("STATUS")                        
                    
                ));
            }
        }
        return bookings;
    }
    
//    public static void main(String[] args) throws Exception{
//			System.out.print("Rate :" + totalCost("CUS3"));
			
	
	}




