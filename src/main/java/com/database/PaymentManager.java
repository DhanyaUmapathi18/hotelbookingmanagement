package com.database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.exception.CustomDatabaseException;
import com.data.Payment;

/*
* The Payment Mangaer class implements an application that 
* simply implements the payment functionalities and 
* saves to database about our payment details
* @author Dhanya Umapathi (Expleo)
* @since 20 FEB 2024
*
* 
*/
public class PaymentManager {
	
	//RoomID
	public static String toFindBookingId(String user_Id) {
		String booking_Id ="";
		String SELECT_QUERY = "SELECT BOOK_ID FROM BOOKING WHERE BOOKEDBY = ?";
		 try (PreparedStatement statement = Database.prepareStatement(SELECT_QUERY)) {
	            statement.setString(1, user_Id);
	            ResultSet resultSet = statement.executeQuery();

	            if(resultSet.next()) {
	            	booking_Id = resultSet.getString("BOOK_ID");
	            }

	        } catch (SQLException e) {
	            System.out.println("Error: " + e.toString());
	        }
		return booking_Id;
	}
	
	//updatePayment
	public static void updatePaymentStatus(String paymentId, String newStatus) {
	    String UPDATE_QUERY = "UPDATE PAYMENT SET STATUS = ?, PAYMENT_DATE=SYSDATE WHERE ID = ?";
	    try (PreparedStatement statement = Database.prepareStatement(UPDATE_QUERY)) {
	        statement.setString(1, newStatus);
	        statement.setString(2, paymentId);

	        int rowsAffected = statement.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("Payment status updated successfully.");
	        } else {
	            System.out.println("Payment ID " + paymentId + " not found.");
	        }

	    } catch (SQLException e) {
	        System.out.println("Error: " + e.toString());
	    }
	}

	//Payment
	public static boolean paymentDetails(Payment payment) throws SQLException {
		String QUERY = "INSERT INTO PAYMENT VALUES(?,?,?,?,SYSDATE,?,?)";  
		String QUERY_UPDATE = "UPDATE BOOKING SET STATUS = 'BOOKED'  WHERE BOOKEDBY = ?";
		int rowsAffected=0;
		 try (PreparedStatement statement = Database.prepareStatement(QUERY);
				 PreparedStatement update_statement = Database.prepareStatement(QUERY_UPDATE)) {
		        statement.setString(1, payment.getId());
		        statement.setString(2, payment.getType());
		        statement.setDouble(3, payment.getAmount());
		        statement.setString(4, payment.getBookingId());
		        statement.setString(5, payment.getUserId());
		        statement.setString(6, payment.getStatus());
		        update_statement.setString(1, payment.getUserId());
				rowsAffected = statement.executeUpdate();
				rowsAffected = update_statement.executeUpdate();
		    } 
		 return rowsAffected>0;
	}

	//To get auto increment of id
	public static String getNextId() throws CustomDatabaseException{
        try (PreparedStatement maxIdStatement = Database.prepareStatement("SELECT COUNT(*) FROM Payment");
                ResultSet resultSet = maxIdStatement.executeQuery()) {
            if (resultSet.next()) {
                return "PAY"+(resultSet.getInt(1) + 1);
            }
        }catch (SQLException e) {
            throw new CustomDatabaseException("Error fetching next ID", e);
        
	}
        return "PAY1";
	}
	
	public static String getStatus(String acc_id) {
	    String status = "";

	    try (PreparedStatement statement = Database.prepareStatement("SELECT STATUS FROM Payment WHERE USERID = ? ");) {
	        
	        statement.setString(1, acc_id);
    	    try (ResultSet resultSet = statement.executeQuery()) {
	            if (resultSet.next()) {
	                 status = resultSet.getString("STATUS");
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }

	    return status;
	}
//	public static String toFindType(String booking_Id) {
//		String type ="";
//		String SELECT_QUERY = "SELECT TYPE FROM ROOM WHERE ID = (SELECT ROOMID FROM BOOKING WHERE ROOMID = ?)";
//		 try (PreparedStatement statement = Database.prepareStatement(SELECT_QUERY)) {
//	            statement.setString(1, booking_Id);
//	            ResultSet resultSet = statement.executeQuery();
//
//	            if(resultSet.next()) {
//	            	type = resultSet.getString("type");
//	            }
//
//	        } catch (SQLException e) {
//	            System.out.println("Error: " + e.toString());
//	        }
//		return type;
//	}
	


	    
}
