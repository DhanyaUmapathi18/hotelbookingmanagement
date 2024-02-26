package com.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.data.Feedback;
import com.exception.CustomDatabaseException;

/*
* The Feedback manager class implements an application that 
* simply saves the feedback todb and retrives whenever needed
* @author Dhanya Umapathi (Expleo)
* @since 20 FEB 2024
*
* 
*/
public class FeedbackManager{
    //generate automatic feedback id
	public static String getNextId() throws CustomDatabaseException{
        try (PreparedStatement maxIdStatement = Database.prepareStatement("SELECT COUNT(*) FROM Payment");
                ResultSet resultSet = maxIdStatement.executeQuery()) {
            if (resultSet.next()) {
                return "FEEDBACK"+(resultSet.getInt(1) + 1);
            }
        }catch (SQLException e) {
            throw new CustomDatabaseException("Error fetching next ID", e);
	}
        return "FEEDBACK1";
	}
	
	//database upload
	public static boolean feedBack(Feedback feedback) {
	    final String QUERY_INSERT = "INSERT INTO FEEDBACK VALUES (?, ?, SYSDATE, ?, ?)";
	    int rowsAffected = 0;
	    try (PreparedStatement statement = Database.prepareStatement(QUERY_INSERT)) {
	        statement.setString(1, feedback.getFeedback_Id());
	        statement.setString(2, feedback.getSubmittedBy());
	        statement.setInt(3, feedback.getRate());
	        statement.setString(4, feedback.getPost());
	        rowsAffected = statement.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    return rowsAffected > 0;
	}
	public static ArrayList<Feedback> getFeddBack() {
		ArrayList<Feedback> feedbacks = new ArrayList<>();
		String QUERY = "SELECT * FROM FEEDBACK ";
		try (PreparedStatement statement = Database.prepareStatement(QUERY)) {
	        ResultSet set = statement.executeQuery();
	        while(set.next()) {
	        	feedbacks.add(new Feedback(
	        			set.getString("Feedback_Id"),set.getString("SubmittedBy"),set.getString("Post"),set.getInt("rating")));
	        }
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
		return feedbacks;
	}

}