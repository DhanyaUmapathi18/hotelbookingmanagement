package com.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.regex.Pattern;

import com.data.Account;
import com.data.Colors;
import com.exception.EmailValidation;
import com.exception.LoginException;
import com.exception.PaaswordValidation;
/*
* The Account Manager class implements an application that 
* simply validate the email,password,phone number and  
* it has login and register page.
* @author Dhanya Umapathi (Expleo)
* @since 20 FEB 2024
*/
class AccountException extends Exception {
    public AccountException(String message) {
        super(message);
    }
}

final public class AccountManager {
	//Email Regex
    private final static Pattern EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);
            
    //Password Rgex
    private final static Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%_*?&]).{8,}$");
    
    private final static Pattern NAME_REGEX = Pattern.compile("^[a-zA-Z]+$");
   // private final static Pattern  PHONENUMBER_REGEX = Pattern.compile("^\\+?\\d+$");
    private final static String LOGIN_QUERY = "SELECT * FROM Account "
            + "INNER JOIN Details ON Account.id = Details.userID "
            + "WHERE email = ? AND password = ? AND type = ?";

    private final static String CUSTOMER_CREATE_ACCOUNT = "INSERT INTO Account VALUES (?,?,?,?) ";
    private final static String CUSTOMER_CREATE_DETAIL = "INSERT INTO Details VALUES (?,?,?,?,?,?,?)";

    //ValidateEmail
    public static boolean validateEmail(String email) {
        return EMAIL_REGEX.matcher(email).matches();
    }

    //Password Validation
    public static boolean validatePassword(String password) {
        return PASSWORD_REGEX.matcher(password).matches();
    }

    //PhoneNumber Validation 
    public static boolean validateNumber(Long number){
        return number.toString().length()==10 && "6789".contains(number.toString().substring(0, 1));
    }
    
    //validation for name
    public static boolean validateName(String name) {
        return NAME_REGEX.matcher(name).matches();
    }

    //generate next id automatically
    private static String getNextId(boolean forAdmin) throws SQLException {
        String user = (forAdmin) ? "A" : "CUS";
        String QUERY = String.format("SELECT COUNT(*) FROM Account WHERE id LIKE '%s%%'", user);
        try (PreparedStatement maxIdStatement = Database.prepareStatement(QUERY);
                ResultSet resultSet = maxIdStatement.executeQuery()) {
            if (resultSet.next()) {
                return user + (resultSet.getInt(1) + 1);
            }
        }
        return user + "1";
    }
    
    //login  validateNumber
    public static Account login(String email, String password, boolean adminLogin) throws  SQLException, EmailValidation, LoginException {
        if (!validateEmail(email)) {

            throw new EmailValidation(Colors.RED+"Invalid Email Format"+Colors.RESET);
        }

        Account account;

        try (PreparedStatement login = Database.prepareStatement(LOGIN_QUERY)) {
            login.setString(1, email);
            login.setString(2, password);
            login.setString(3, (adminLogin) ? "A" : "C");

            ResultSet res = login.executeQuery();

            if (res.next()) {
               account = new Account(
                       res.getString("id"),
                       res.getString("email"),
                       res.getString("firstName"),
                       res.getString("lastName"),
                       res.getString("gender").charAt(0),
                       res.getLong("phone"),
                       res.getDate("dob"),
                       res.getString("address"));
            } else {
                throw new LoginException(Colors.RED+"Invalid Email Or Password"+Colors.RESET);
            }
        }

        return account;
    }

    //customer regestering
    public static boolean customerRegister(Account toRegister, String password)  throws EmailValidation, SQLException, PaaswordValidation {
        if (!validateEmail(toRegister.getEmail()))
            throw new EmailValidation(Colors.RED+"INVALID EMAIL"+Colors.RESET);

        if (!validatePassword(password))
            throw new PaaswordValidation(Colors.RED+"INVALID PASSWORD"+Colors.RESET);

        try (PreparedStatement account = Database.prepareStatement(CUSTOMER_CREATE_ACCOUNT);
                PreparedStatement details = Database.prepareStatement(CUSTOMER_CREATE_DETAIL)) {
            toRegister.setId(getNextId(false));

            account.setString(1, toRegister.getId());
            account.setString(2, toRegister.getEmail());
            account.setString(3, password);
            account.setString(4, "C");

            details.setString(1, toRegister.getId());
            details.setString(2, toRegister.getFirstName());
            details.setString(3, toRegister.getLastName());
            details.setString(4, "" + toRegister.getGender());
            details.setLong(5, toRegister.getPhone());
            details.setDate(6, toRegister.getDob());
            details.setString(7, toRegister.getAddress());

            int accountStatus = account.executeUpdate();
            int detailsStatus = details.executeUpdate();

            return accountStatus > 0 && detailsStatus > 0;
        }
    }

}