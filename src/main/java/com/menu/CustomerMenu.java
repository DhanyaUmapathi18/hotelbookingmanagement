 package com.menu;
import java.util.ArrayList;
import java.util.Scanner;
import com.database.*;
import com.data.*;
import com.exception.CustomDatabaseException;
import com.exception.EmailValidation;
import com.exception.LoginException;
import com.exception.PaaswordValidation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.*;

/*
* The Customer Menu class implements an application that 
* simply have the customer operation of hotel booking management  and 
* print the result to standard output and saves to database
* @author Dhanya Umapathi (Expleo)
* @since 20 FEB 2024
*
* 
*/
public class CustomerMenu {
	Account account;
	private final static String MENU;
	Scanner scanner = new Scanner(System.in);
	short choice;
	static {
		StringBuilder menu = new StringBuilder();
		menu
				.append("-".repeat(100)).append('\n')
				.append("\t1 Check Availability\n")
				.append("\t2 Book Room\n")
				.append("\t3 Cancel Room\n")
				.append("\t4 Payment\n")
				.append("\t5 View History\n")
				.append("\t6 Feedback\n")
				.append("\t7 Return to main Menu\n")
				.append("\t8 Exit\n");
		MENU = menu.toString();
	}

	public void doAuthorization() {
		int retries = 3;

		// User Register
		System.out.println("-".repeat(100));
		System.out.println("\tCustomer ");
		System.out.println("-".repeat(100));
		System.out.println("\t1 Login \n\t2 Registeration \n\t3 Go Back");
		System.out.println("-".repeat(100));
		System.out.print("\nEnter your choice : ");
		int mode = Integer.parseInt(scanner.nextLine());
		System.out.println("-".repeat(100));
		if (mode == 1) {
			while (retries > 0) {
				try {
					System.out.print("Email: ");
					String email = scanner.nextLine();
					System.out.print("Password: ");
					String password = scanner.nextLine();

					account = AccountManager.login(email, password, false);

					showMenu();
					return;

				} catch (LoginException | EmailValidation e) {
					System.out.println(e.getMessage());
					retries--;
					System.out.println("You Have " + retries + " Retries Available");
					System.out.println("-".repeat(1184));
				} catch (SQLException e) {
					System.err.println(e);
					System.out.println("Unexpected Database Error, Try Again");
					System.out.print("-".repeat(184));
				}
//				finally {
//					System.out.println("-".repeat(184));
//					System.out.println(Colors.PURPLE+"Logging Out : Have A Nice Day"+Colors.RESET);
//					System.out.println("=".repeat(184));
//				}
			}
		} else if (mode == 2) {
			Account newAccount = new Account();
			System.out.println("\t\tRegistration");
			System.out.println("-".repeat(184));
			System.out.print("Enter Your Email : ");
			while (true) {
				String email = scanner.nextLine();
				if (AccountManager.validateEmail(email)) {
					newAccount.setEmail(email);
					break;
				} else {
					System.out.println(Colors.RED+"Invalid Email ! Try Again : "+Colors.RESET);
				}
			}

			String password;
			System.out.print("Enter  Password \nNote :(Atleast 1 Lowercase, Uppercase, Number, Special Character From [_@$!%*?&] ): ");
			while (true) {
				password = scanner.nextLine();
				
				if (AccountManager.validatePassword(password)) // Password validation
					break;
				else
					System.out.print(Colors.RED+"Invalid Password (Atleast 1 from each set [a-z][A-Z][0-9][@$!_%*?&])! Try Again : "+Colors.RESET);
			}
			while(true) {
				System.out.print("Enter Your First Name : ");
				String firstName = scanner.next();
				if(AccountManager.validateName(firstName)) {
					newAccount.setFirstName(firstName);
					break;
				}else {
					System.out.println(Colors.RED+"Invalid First Name ! Try Again : "+Colors.RESET);

				}
			}
			while(true) {
				System.out.print("Enter Your Last Name : ");
				String lastName = scanner.next();
				if(AccountManager.validateName(lastName)) {
					newAccount.setLastName(lastName);
					break;
				}else {
					System.out.println(Colors.RED+"Invalid Last Name ! Try Again : "+Colors.RESET);

				}
			}
			scanner.nextLine();
			System.out.print("Enter Your Gender (M/F) : ");
			newAccount.setGender(scanner.nextLine().charAt(0));
			System.out.print("Enter Your Phone Number : ");
			while(true){
				long phone = scanner.nextLong();
				if(AccountManager.validateNumber(phone)) {
					newAccount.setPhone(phone);
					break;
				} else {
					System.out.print(Colors.RED+"Invalid Phone Number, Number must only have 10 digits and starts with '6789' "+Colors.RED);
				}
			}
			// newAccount.setPhone(scanner.nextLong());
			scanner.nextLine();
			System.out.print("Enter Your Address : ");
			newAccount.setAddress(scanner.nextLine());
			System.out.print("Enter Your DOB [DD-MM-YYYY](eg: 01-01-2000) : ");
			while (true) {
				try {
					String date = scanner.nextLine();
					SimpleDateFormat smf = new SimpleDateFormat("dd-MM-yyyy"); // simple date format to get date
					newAccount.setDob(new Date(smf.parse(date).getTime()));
					break;
				} catch (ParseException e) {
					System.out.print(Colors.RED+"Invalid Date Format, ReEnter Correctly :"+Colors.RESET);
				}

			}

			try {
				if (AccountManager.customerRegister(newAccount, password)) {
					account = newAccount;
					System.out.println("-".repeat(184));
					System.out.println("Registration Successfull !!!");
					System.out.println("-".repeat(184));
					doAuthorization();
					return;
				} else {
					System.out.println("Registration Failed, Try Again");

				}
			} catch (SQLException | EmailValidation | PaaswordValidation e) {
				System.err.println(e);
				System.out.println("Database Error, Please Try Again Later");
			} finally {
				System.out.println("Logging Out From Customer");
				System.out.println("-".repeat(184));
			}
		}
		else if (mode == 3)
			doAuthorization();

	}

	private void showMenu() {
		// Booking book = new Booking();
			do {
				System.out.println("-".repeat(184));
				System.out.println("\nCustomer Menu");
				System.out.println(MENU);
				System.out.println("-".repeat(184));
				System.out.println("Enter Choice : ");
				choice = scanner.nextShort();

				switch (choice) {
					case 1:
						checkAvailability();
						break;
					case 2:
						roomBooking();
						break;
					case 3:
						cancelBooking();
						break;
					case 4:
						calculateTotalCost();
						break;
					case 5:
						viewBookingHistory();
						break;
					case 6: 
						feedBack();
						break;
					case 7 : 
						return;
					case 8:System.out.println(Colors.PURPLE+"Logging Out : Have A Nice Day"+Colors.RESET);
						System.exit(0);
						break;
			}
		} while (choice > 0 && choice < 6);
	}

	private void checkAvailability() {
		System.out.println("-".repeat(184));
		System.out.println("\tRoom Availability Search By ");
		System.out.println("\t1. Price\n\t2. Type");
		System.out.println("-".repeat(184));
		System.out.print("Enter your choice: ");
		choice = scanner.nextShort();
		switch (choice) {
			// Price
			case 1:
				System.out.println("\tRooms Type\n\t1 Single\n\t2 Double\n\t3 Studio\n\t4 Delux  ");
				System.out.println("Select you're preferences ");
				System.out.print("Enter room type : ");
				String type = scanner.next();
				System.out.print("Enter start price : ");
				double startPrice = scanner.nextDouble();
				System.out.print("Enter end price : ");
				double endPrice = scanner.nextDouble();
				ArrayList<Room> rooms = RoomManager.checkAvailabilityByPrice(startPrice, endPrice, type);
				if (rooms.isEmpty()) {
				  System.out.println("No records found.");
				} 
				else {
					System.out.println("Room Details");
					System.out.println("ID	 Type	AC/NON-AC	Rate	Status");
					for (Room room : rooms) {
						System.out.println(String.format("%s\t%s\t%s\t 	%.1f\t%s", room.getId(), room.getType(), room.getAc(),
							room.getRate(), room.getStatus()));

					}
				}
				break;
			// RooomType
			case 2:
				System.out.println("\tRooms Type\n\t1 Single\n\t2 Double\n\t3 Studio\n\t4 Delux  ");
				System.out.println("Enter room type and AC/Non-AC");
				ArrayList<Room> r = RoomManager.checkAvailabilityByRoomType(scanner.next(), scanner.next());
				// ArrayList<Room> r = RoomManager.checkAvailabilityByPrice(roomtype,endPrice);
				System.out.println("Room Details");
				for (Room room : r) {
					System.out.println(String.format("%s\t%s\t%s\t%.1f\t%s", room.getId(), room.getType(), room.getAc(),
							room.getRate(), room.getStatus()));

				}
				break;
			default:
				System.out.println("Invalid Option");
		}
	}



	//Room Booking
	private void roomBooking() {
		Booking book = new Booking();
		System.out.println("Room Details");
		try {
			System.out.println("ROOM_NUMBER	 Type	AC/NON-AC	Rate	Status");

			for (Room room : RoomManager.getRooms()) {
				if (room.getStatus().equalsIgnoreCase("available")) {
					System.out.println(String.format("%s \t\t%s\t%s\t	%.1f\t%s", room.getId(), room.getType(), room.getAc(),
							room.getRate(), room.getStatus()));
				}

			}
			book.setId(BookManager.getNextId());
			book.setBookedBy(account.getId());

			Date startSqlDate,endSqlDate;
			LocalDate startLocalDate,endLocalDate;
			scanner.nextLine();
			while (true) {
				System.out.print("Check-IN Date (DD-MM-YYYY): ");
				String startDate = scanner.nextLine();
				startLocalDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				if (LocalDate.now().isBefore(startLocalDate)) {
					startSqlDate = Date.valueOf(startLocalDate);
					book.setStartDate(startSqlDate);
					break;
				} else
					System.out.println("Give the valid date");
			}
			while(true) {
				System.out.print("Check-OUT Date (DD-MM-YYYY): ");
				String endDate = scanner.nextLine();
				endLocalDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				if (LocalDate.now().isBefore(endLocalDate) || LocalDate.now().isEqual(endLocalDate)) {
					endSqlDate = Date.valueOf(endLocalDate);
					book.setEndDate(endSqlDate);
					break;
				} else
					System.out.println("Give the valid date");
			}
			System.out.print("Choose room type : ");
			String roomType = scanner.next();
			System.out.print("Choose room with AC/NON-AC : ");
			String roomAc = scanner.next();
			String room_Number = RoomManager.getroomNumber(roomType,roomAc);
			book.setRoomId(room_Number);
			
			if (!RoomManager.isRoomAvailable(room_Number)) {
				System.out.println("Error: Room with ID " + room_Number + " does not exist or is not available.");
			}
			
			
			if(BookManager.roomBooking(book)){
				System.out.println("Room booked successfully");
				System.out.print("Do you want to continue to payment (yes/no) : ");
				String choice = scanner.next();
				if(choice.equalsIgnoreCase("yes")){
					calculateTotalCost();
				}
				else
					return;
			}
			else
				System.out.println("Room has not booked");
				
		} catch (SQLException e) {
			System.out.println("Database Error: Try Again");
		}

	}
	
	// cancelBooking
	private void cancelBooking() {
		Booking book = new Booking();
		// System.out.print("Enter Booking ID : ");
		book.setBookedBy(account.getId());
		System.out.print("Enter Room Number : ");
		book.setRoomId(scanner.next());
		String acc_id = scanner.next();
		book.setId(BookManager.getBookingId(book.getBookedBy(), book.getRoomId()));
		if (BookManager.cancelBooking(book,acc_id))
			System.out.println("Cancelled Successfully");
		else
			System.out.println("Failed to Cancel ");

	}

	// calculateTotalCost
	private void calculateTotalCost()  {
		String status = PaymentManager.getStatus(account.getId());
		if("PAID".equals(status)) {
			System.exit(0);
		}
		else {
		double rate = 0.0;
		try {
			
			System.out.println("Bill Details");
			System.out.println("AccountID = " + account.getId());
			rate = BookManager.totalCost(account.getId());
			System.out.println("Total Amount: " + rate);
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		}
		Payment payment = new Payment();
		payment.setAmount(rate);
		payment.setBookingId(PaymentManager.toFindBookingId(account.getId()));
		payment.setUserId(account.getId());
		payment.setStatus("PENDING");
		try{
			initiatePayment(payment);
		}catch(SQLException | CustomDatabaseException e){
			System.out.println(e);
		}
		}
	}

	// initiatePayment
	private void initiatePayment(Payment payment) throws SQLException, CustomDatabaseException {
		System.out.print("Enter option for Payment credit card (C) and Debit card (D) : ");
		char choice = scanner.next().charAt(0);
		Card card = new Card(payment);

		switch (choice) {
			case 'c':
			case 'C':
				card.setType("CREDIT");
				break;
			case 'D':
			case 'd':
				card.setType("DEBIT");
				break;
			default:
				System.out.println("Enter 'c','C','d','D'");
				return;
		}

		card.setId(PaymentManager.getNextId());
		do {
			System.out.print("Enter card number: ");
			String number = scanner.next();
			if (number.length() == 16) {
				card.setCardNumber(number);
				break;
			} else {
				System.out.println("Invalid Card number. Length Must Be 16 digits");
			}

		} while (true);
		System.out.print("Enter card holder name: ");
		card.setCardHolderName(scanner.nextLine());
		scanner.nextLine();
		System.out.print("Enter card expiry date (MM/YY): ");
		card.setExpirationDate(scanner.next());

		do {
			System.out.print("Enter card CVV: ");
			String ccv = scanner.next();
			if (ccv.length() == 3) {
				card.setCvv(Integer.parseInt(ccv));
				break;
			} else {
				System.out.println("Invalid CVV number. Length Must Be 3 digits");
			}

		} while (true);

		scanner.nextLine();
		System.out.println("-".repeat(184));
		card.setStatus("PAID");
		if (PaymentManager.paymentDetails(card)) {
			System.out.println("Payment Successful");
			System.out.println("-".repeat(184));
			notification();
		} else {
			System.out.println("Payment failed");
			System.out.println("-".repeat(184));
		}
		System.out.println("-".repeat(184));
//		System.out.print("Do you like to provide feedback? ");
//		String option = scanner.nextLine();
//
//		if (option.equalsIgnoreCase("yes")) {
//			feedBack();
//		} else {
//			System.exit(0);
//		}
	}
	
	private void notification() {
		System.out.println("-".repeat(184));
		System.out.println("You have booked you're room and payment has done successfully");
		System.out.println("Payment has done on " + LocalDate.now());
		System.out.println("-".repeat(184));

	}

	private void feedBack() {
		Feedback feedback = new Feedback();
		try {
			feedback.setFeedback_Id(FeedbackManager.getNextId());
			feedback.setSubmittedBy(account.getId());
			System.out.print("Rate between (1-5) :");
			feedback.setRate(scanner.nextInt());
			scanner.nextLine();
			System.out.print("Post Your FeedBack :");
			feedback.setPost(scanner.nextLine());
			if (FeedbackManager.feedBack(feedback)) {
				System.out.println("-".repeat(184));
				System.out.println("Feedback has posted");
				System.out.println("-".repeat(184));

			}
			else
				System.out.print("Your feedback has not recorded");
		} catch (CustomDatabaseException e) {
			System.out.println(e);
		}
	}

	private void viewBookingHistory(){
		try{
			ArrayList<Booking> bookings;
			bookings = BookManager.getBookingsOf(account.getId());
			for(Booking book : bookings){
				System.out.println("-".repeat(184));
				//System.out.println("BOOKING ID\tUSER ID\tROOM ID\tSTART DATE\t END DATE\tSTATUS");

				LocalDate sd = book.getStartDate().toLocalDate();
				LocalDate ed = book.getEndDate().toLocalDate();
				if(sd.isAfter(LocalDate.now())){
					book.setStatus("UPCOMING");
				} else if (ed.isBefore(LocalDate.now())){
					book.setStatus("COMPLETE");
				} else {
					book.setStatus("ONGOING");
				}
				System.out.println("BOOKING ID\tUSER_ID\t\t ROOM_ID\tSTART_DATE\t  END_DATE\t   STATUS");

				System.out.printf("  %-12s    %-12s    %-6s        %-16s  %-16s %s%n",
				        book.getId(),
				        book.getBookedBy(),
				        book.getRoomId(),
				        book.getStartDate(),
				        book.getEndDate(),
				        book.getStatus());
				//System.out.println(book.getId()+ "\t"+book.getBookedBy()+ "\t" + book.getRoomId() + "\t" + book.getStartDate() + "\t\t" +book.getEndDate()+"\t\t"+book.getStatus());
			}
		}catch(SQLException e){
			System.out.println("No Booking History");
		}
	}
	

//	}
}
