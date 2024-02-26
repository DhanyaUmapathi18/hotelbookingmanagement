package com.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

import com.data.Room;
import com.exception.CustomDatabaseException;

/*
* The Room Manager class implements an application that 

* simply have the admin operation of hotel booking management and 
* it saves to database
* @author Dhanya Umapathi (Expleo)
* @since 20 FEB 2024
*
* 
*/
public class RoomManager {
    static Scanner scanner = new Scanner(System.in);
    private final static Pattern ROOMNUMBER_REGEX = Pattern.compile("^\\d{3}+$");

    //validation for room number
    public static boolean validateRoomNumber(String roomNumber) {
        return ROOMNUMBER_REGEX.matcher(roomNumber).matches();
    }

    //View rooms
    public static ArrayList<Room> getRooms() throws SQLException {
        ArrayList<Room> rooms = new ArrayList<Room>();
        try (PreparedStatement query = Database.prepareStatement("SELECT * FROM Room ORDER BY ROOM_NUMBER");
                ResultSet rs = query.executeQuery()) {
            while (rs.next()) {
                rooms.add(new Room(
                        rs.getString("ROOM_NUMBER"),
                        rs.getString("ROOM_TYPE"),
                        rs.getString("ac"),
                        rs.getDouble("rate"),
                        rs.getString("status")));
            }
        }
        return rooms;
    }

    //add Rooms
    public static boolean addRoom(Room room) throws SQLException {
        String QUERY_INSERT = "INSERT INTO ROOM VALUES(?,?,?,?,?)";

        try (PreparedStatement statement = Database.prepareStatement(QUERY_INSERT);) {
            statement.setString(1, room.getId());
            statement.setString(2, room.getType());
            statement.setString(3, room.getAc());
            statement.setDouble(4, room.getRate());
            statement.setString(5, "AVAILABLE");
            int rows = statement.executeUpdate();
            return rows > 0;
        }

    }

    //updation
    public static boolean updateByPrice(Room room) throws SQLException {
        String QUERY_UPDATE = "UPDATE ROOM SET RATE= ? WHERE ROOM_TYPE = ? AND AC = ?";
        try (PreparedStatement statement = Database.prepareStatement(QUERY_UPDATE)) {
            statement.setDouble(1, room.getRate());
            statement.setString(2, room.getType());
            statement.setString(3, room.getAc());
            int rows = statement.executeUpdate();
            return rows > 0;
        }
    }

    
    public static boolean updateByID(Room room) throws SQLException {
        String QUERY_UPDATE = "UPDATE ROOM SET ROOM_TYPE= ?, AC = ?, RATE = ? WHERE ROOM_NUMBER = ?";
        try (PreparedStatement statement = Database.prepareStatement(QUERY_UPDATE)) {
            statement.setString(1, room.getType());
            statement.setString(2, room.getAc());
            statement.setDouble(3, room.getRate());
            statement.setString(4, room.getId());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }

    }

    //delete
    public static boolean deleteRoom(Room room) throws SQLException {
        String QUERY_DELETE = "DELETE FROM ROOM WHERE ROOM_NUMBER = ?";
        try (PreparedStatement statement = Database.prepareStatement(QUERY_DELETE);) {
            statement.setString(1, room.getId());
            int rows = statement.executeUpdate();
            return rows > 0;
        }
    }


    public static ArrayList<Room> checkAvailabilityByRoomType(String type, String ac) {
        ArrayList<Room> rooms = new ArrayList<>();
        String QUERY_CHECK = "SELECT * FROM ROOM WHERE ROOM_TYPE = ? AND AC = ? AND STATUS = 'AVAILABLE' ORDER BY ROOM_NUMBER";
        try (PreparedStatement statement = Database.prepareStatement(QUERY_CHECK)) {
            statement.setString(1, type);
            statement.setString(2, ac);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                rooms.add(new Room(
                        resultSet.getString("ROOM_NUMBER"),
                        resultSet.getString("ROOM_TYPE"),
                        resultSet.getString("AC"),
                        resultSet.getDouble("RATE"),
                        resultSet.getString("STATUS")));

            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
        }

        return rooms;
    }

    public static ArrayList<Room> checkAvailabilityByPrice(double startRate, double endRate,String type) {
        ArrayList<Room> rooms = new ArrayList<>();
        final String QUERY_CHECK = "SELECT * FROM ROOM WHERE RATE BETWEEN ? AND ? AND STATUS = 'AVAILABLE' AND ROOM_TYPE =?";
        try (PreparedStatement statement = Database.prepareStatement(QUERY_CHECK)) {
            statement.setDouble(1, startRate);
            statement.setDouble(2, endRate);
            statement.setString(3, type);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                rooms.add(new Room(resultSet.getString("ROOM_NUMBER"),
                        resultSet.getString("ROOM_TYPE"),
                        resultSet.getString("AC"),
                        resultSet.getDouble("RATE"),
                        resultSet.getString("STATUS")));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
        }
        return rooms;
    }

    public static String getId(long number) {
        String customerID = "";
        String SELECT_QUERY = "SELECT USERID FROM DETAILS WHERE PHONE = ?";
        
        try (PreparedStatement statement = Database.prepareStatement(SELECT_QUERY)) {
            statement.setLong(1, number);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                customerID = resultSet.getString("USERID");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
        }
        return customerID;
    }

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

    public static Room getRoom(String id) throws SQLException, CustomDatabaseException{
    	try(PreparedStatement st = Database.prepareStatement("SELECT * FROM ROOM WHERE ROOM_NUMBER = ?")){
    		st.setString(1,id);
    		ResultSet rs = st.executeQuery();
    		if(rs.next()) {
    			return new Room(
    					rs.getString(1),
    					rs.getString(2),
    					rs.getString(3),
    					rs.getDouble(4),
    					rs.getString(5)
    					);
    		} 
    		else {
    			throw new CustomDatabaseException("No Such Room Exists");
    		}
    	}
    }

    public static String getroomNumber(String roomType,String roomAc) {
        Stack<String> room = new Stack<String>();
        String query = "select ROOM_NUMBER from ROOM where room_type= ? AND AC = ?";
        try (PreparedStatement select_statement = Database.prepareStatement(query)) {
            select_statement.setString(1, roomType);
            select_statement.setString(2, roomAc);

            ResultSet rs = select_statement.executeQuery();  
            while (rs.next()) {
                room.push(rs.getString("ROOM_NUMBER"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
        }
        return room.pop();
    }

 

}