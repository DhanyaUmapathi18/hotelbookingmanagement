package com.menu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import com.data.*;
import com.database.*;
import com.exception.CustomDatabaseException;
import com.exception.EmailValidation;
import com.exception.LoginException;
import com.exception.validateMenuChoiceException;
/*
* The Admin Menu class implements an application that 
* simply have the admin operation of hotel booking management  and 
* print the result to standard output and saves to database
* @author Dhanya Umapathi (Expleo)
* @since 20 FEB 2024
*
* 
*/
public class AdminMenu {
	private final static String MENU;
	Account account;
	DriverHotelManagement hm = new DriverHotelManagement();
	Scanner scan = new Scanner(System.in);

	static {
		StringBuilder menu = new StringBuilder();
		menu
				.append("-".repeat(184)).append('\n')
				.append("\t1 Add Room\n")
				.append("\t2 Update Room\n")
				.append("\t3 Delete Room\n")
				.append("\t4 View Rooms\n")
				.append("\t5 Main Menu\n")
				.append("\t6 Log out\n")
				.append("-".repeat(184)).append('\n');

		MENU = menu.toString();
	}

	public void doAuthorization() {
		int retries = 3;
		System.out.println("-".repeat(184));
		System.out.println("\t\t\tLogin for Admin");
		System.out.println("-".repeat(184));
		while (retries > 0) {
			try {
				System.out.print("Email: ");
				String email = scan.nextLine();
				System.out.print("Password: ");
				String password = scan.nextLine();
				account = AccountManager.login(email, password, true);
				System.out.println("-".repeat(184));
				showMenu();
				return;
			} catch (LoginException | EmailValidation e) {
				System.out.println(e.getMessage());
				retries--;
				System.out.println("You Have " + retries + " Retries Available");
				System.out.println("-".repeat(184));
			} catch (SQLException e) {
				System.err.println(e);
				System.out.println("Unexpected Database Error, Try Again");
				System.out.print("-".repeat(184));
			}
		}
	}

	// admin operations
	private void showMenu() {
		short choice;

		do {
			System.out.println("\t\t\tAdmin DashBoard ");
			System.out.print(MENU);
			try {
			System.out.print("Enter Your Choice : ");
			choice = scan.nextShort();
			switch (choice) {
				case 1:
					addRoom();
					break;
				case 2:
					updateRoom();
					break;
				case 3:
					deleteRoom();
					break;
				case 4:
					viewRoom();
					break;
				case 5:
					return;
				case 6:
					System.out.print("Are you sure want to exit out : ");
					String exit = scan.next();
					if (exit.equalsIgnoreCase("yes")) {
						System.out.println("Loged out");
						System.exit(0);
					} else
						DriverHotelManagement.mainMenu();
					break;
				default : throw new validateMenuChoiceException(Colors.RED+"Please enter a number between 1, 2, 3, 4, 5 or 6"+Colors.RESET);
				}
			}catch (InputMismatchException e) {
                System.out.println(Colors.RED+"Invalid input. Please enter a number."+Colors.RESET);
                scan.nextLine();
            }catch(validateMenuChoiceException  e) {
            	System.out.println(e.getMessage());
		}
		} while (true);
	}

	private void addRoom() {
		Room room = new Room();
		System.out.println("-".repeat(184));
		System.out.println("Room Expansion Hub");
		System.out.println("-".repeat(184));
		while(true){
			System.out.print("Enter RoomNumber : ");
			String roomNumber = scan.next();
			if(RoomManager.validateRoomNumber(roomNumber)) {
				room.setId(roomNumber);
				break;
			} else {
				System.out.print(Colors.RED+"Invalid Room Number"+Colors.RESET);
			}
		}

		System.out.print("Enter RoomType : ");
		room.setType(scan.next());
		System.out.print("Room has AC (1) or Non-AC (2): ");
		short num = scan.nextShort();
		if(num==1)
			room.setAc("AC");
		else if(num == 2)
			room.setAc("NON-AC"); 
		scan.nextLine();
		System.out.print("Enter Price : ");
		room.setRate(scan.nextFloat());
//		scan.nextLine();
//		System.out.print("Enter Room Status : ");
//		room.setStatus(scan.nextLine());

		try {
			if(RoomManager.addRoom(room))
				System.out.println("Room added Sucessfully");
			else
				System.out.println("Room not added.Invalid Data");

		} catch (SQLException e) {
			System.err.println(e);
			System.out.println("Database Error, Try Again");
		}

	}

	// update Room
	private void updateRoom() {
		do {
			Room room = new Room();
			System.out.println("-".repeat(100));
			System.out.println("UPDATE ROOM DETAILS BY");
			System.out.println("-".repeat(100));
			System.out.println("1. Price");
			System.out.println("2. Id");
			System.out.println("3. Go back to Main Menu");
			System.out.println("4. Exit");
			System.out.println("-".repeat(100));
			System.out.print("Enter Your Choice : ");
			//short choice = scan.nextShort();
			try {
				short choice = scan.nextShort();
				scan.nextLine();
				switch (choice) {
					case 1:
						System.out.println("-".repeat(100));
						System.out.print("Enter revised price : ");
						room.setRate(scan.nextFloat());
						scan.nextLine();
						System.out.print("Enter Type to which revised price updated : ");
						room.setType(scan.nextLine());
						System.out.print("Enter AC or Non-AC: ");
						room.setAc(scan.next());
						if(RoomManager.updateByPrice(room))
							System.out.println("Updated Successfully");
						else
							System.out.println("Room has not updated");
						System.out.println("-".repeat(100));

						break;
					case 2:
						System.out.println("-".repeat(100));
						System.out.print("Enter Your Room ID :");
						String id = scan.nextLine();
						try{
							room = RoomManager.getRoom(id);
							System.out.println("-- Press Enter For Details To Remain Same -- ");
							String input;
							System.out.print(String.format("Type [%s] :",room.getType()));
							input=scan.nextLine();
							if(!input.equals("")) room.setType(input);
							System.out.print(String.format("Ac [%s] :",room.getAc()));
							input=scan.nextLine();
							if(!input.equals("")) room.setAc(input);
							System.out.print(String.format("Rate [%.2f] :",room.getRate()));
							input=scan.nextLine();
							if(!input.equals("")) room.setRate(Double.parseDouble(input));
							// System.out.print(String.format("Status [{}] :",room.getStatus()));
							// input=scan.nextLine();
							// if(!input.equals("")) room.setStatus(input);

							if(RoomManager.updateByID(room))
								System.out.println("Updated Successfully");
							else
								System.out.println("Room has not updated");
							System.out.println("-".repeat(100));
							break;
						} catch (CustomDatabaseException e) {
							System.out.println(Colors.RED+e.getMessage()+Colors.RESET);
						} catch (SQLException e) {
							System.out.println(Colors.RED+"Database Error : "+e.getMessage()+Colors.RESET);
						}
						
					case 3:
						return;
					case 4:
						System.out.println("Admin operation ends");
						System.exit(0);
						break;
					default:
                        throw new validateMenuChoiceException(Colors.RED+"Please enter a number between 1, 2, 3, or 4"+Colors.RESET);
				}
			} catch (SQLException e) {
				System.err.println(e);
				System.out.println("Database Error : Try Again");
			} catch (InputMismatchException e) {
                System.out.println(Colors.RED+"Invalid input. Please enter a number."+Colors.RESET);
                scan.nextLine();
			} catch(validateMenuChoiceException  e) {
            	System.out.println(e.getMessage());
            }
		} while (true);
	}

	private void deleteRoom() {
		Room room = new Room();
		System.out.println("-".repeat(100));
		System.out.println("Delete Room");
		System.out.println("Delete Details By Room-Id :");
		room.setId(scan.next());
		System.out.print("Enter the status : ");
		room.setStatus(scan.next());
		try {
			if(RoomManager.deleteRoom(room))
				System.out.println("Room deleted successfully");
			else
				System.out.println("Room not deleted ");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private void viewRoom() {
		System.out.println("-".repeat(100));
		System.out.println("View Room");
		try {
			ArrayList<Room> rooms = RoomManager.getRooms();
			System.out.println("\nRoom Details");
			System.out.printf("%-5s    %-10s%-9s%-12s%s%n","ROOM_NUMBER","Type", "AC/NON-AC","Rate","Status");
			for (Room room : rooms) {
			    System.out.printf("%-5s%-10s%-9s%-17s%s%n",
			            room.getId(),
			            room.getType(),
			            room.getAc(),
			            String.format("%.1f", room.getRate()),
			            room.getStatus());
			}
			System.out.println("-".repeat(100));
		} catch (SQLException e) {
			System.err.println(e);
			System.out.println("Database Error : Try Again");
		}
	}
	// calculate total cost
	// private void Billing() throws SQLException, CustomDatabaseException {
	// double rate = 0.0;
	// try {
	// rate = BookManager.totalCost(account.getId());
	// System.out.println("Total Amount: " + rate);
	// } catch (SQLException e) {
	// System.out.println("Error: " + e.getMessage());
	// }
	// Payment payment = new Payment();
	// payment.setAmount(rate);
	// payment.setBookingId(PaymentManager.toFindBookingId(account.getId()));
	// payment.setUserId(account.getId());
	// payment.setStatus("PENDING");
	//
	// initiatePayment(payment);
	// }
	//
	// //initiatePayment
	// private void initiatePayment(Payment payment) throws SQLException,
	// CustomDatabaseException {
	// System.out.print("Enter option for Payment credit card (C) and Debit card (D)
	// : ");
	// char choice = scan.next().charAt(0);
	// Card card = new Card(payment);
	// switch (choice) {
	// case 'c':
	// case 'C':
	// card.setType("CREDIT_CARD");
	// break;
	// case 'D':
	// case 'd':
	// card.setType("DEBIT_CARD");
	// break;
	// default:
	// System.out.println("Enter 'c','C','d','D'");
	// }
	// card.setId(PaymentManager.getNextId());
	// System.out.print("Enter card number: ");
	// card.setCardNumber(scan.nextLine());
	// scan.nextLine();
	// System.out.print("Enter card holder name: ");
	// card.setCardHolderName(scan.nextLine());
	// scan.nextLine();
	// System.out.print("Enter card expiry date (MM/YY): ");
	// card.setExpirationDate(scan.next());
	// System.out.print("Enter card CVV: ");
	// card.setCvv( scan.nextInt());
	// if (PaymentManager.paymentDetails(card)) {
	// System.out.println("Payment Successfull");
	// } else
	// System.out.println("Payment failed");
	// }
}
