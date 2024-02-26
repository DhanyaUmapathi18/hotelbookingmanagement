package com.menu;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.data.Colors;
import com.data.Feedback;
import com.data.Room;
import com.database.FeedbackManager;
import com.database.RoomManager;
import com.exception.validateMenuChoiceException;

/*
* The Guest Menu class implements an application that 
* simply have the guest operation has searching of hotel booking management  and 
* print the result to standard output and saves to database
* @author Dhanya Umapathi (Expleo)
* @since 20 FEB 2024
*
* 
*/
public class GuestMenu  {
	private final static String MENU;
	Scanner scanner = new Scanner(System.in);
	static {
		StringBuilder menu = new StringBuilder();
		menu
				.append("-".repeat(80)).append('\n')
				.append("\t1 Check Availability\n")
				.append("\t2 View Feedback\n")
				.append("\t3 Go To Customer Portal\n")
				.append("\t4 Main Menu\n")
				.append("\t5 Exit\n");
		MENU = menu.toString();
	}

	public void guestMenu(){
		short choice;
		System.out.println("-".repeat(100));
		System.out.println("\tGuest Menu");
		System.out.println(MENU);
		System.out.println("-".repeat(100));
		do {
			try {
			System.out.print("Enter chocie : ");
			choice = scanner.nextShort();
			switch (choice) {
				case 1: checkAvailability();
							break;
				case 2:  
					System.out.println("-".repeat(80));
					System.out.println("Feedbacks : ");
					for(Feedback fb : FeedbackManager.getFeddBack()) {
						System.out.println("-".repeat(80));
						System.out.println("Posted By : "+fb.getSubmittedBy());
						System.out.println("Ratings : "+fb.getRate());
						System.out.println("Review : "+fb.getPost());
					}
					System.out.println("-".repeat(80));
					break;
                case 3: new CustomerMenu().doAuthorization();      
				case 4: return;
				case 5: System.exit(0);
				default :throw new validateMenuChoiceException(Colors.RED+"Please enter a number between 1, 2, 3, 4 or 5"+Colors.RESET);
			}
			}catch (InputMismatchException e) {
                System.out.println(Colors.RED+"Invalid input. Please enter a number."+Colors.RESET);
                scanner.nextLine();
            }catch(validateMenuChoiceException  e) {
            	System.out.println(e.getMessage());
            }
		} while (true);
	}
	
	private void checkAvailability() {
		System.out.println("-".repeat(100));
		System.out.println("\tRoom Availability Search By ");
		System.out.println("\t1. Price\n\t2. Type");
		System.out.println("-".repeat(100));
		System.out.print("Enter your choice: ");
		try {
		short choice = scanner.nextShort();
		switch (choice) {
			// Price
			case 1:
				System.out.print("Enter start price : ");
				double startPrice = scanner.nextDouble();
				System.out.print("Enter end price : ");
				double endPrice = scanner.nextDouble();
				System.out.print("Enter type :");
				String type = scanner.next();
				ArrayList<Room> rooms = RoomManager.checkAvailabilityByPrice(startPrice, endPrice,type);
				System.out.println("Room Details");
				System.out.println("ID	 Type	AC/NON-AC	Rate	Status");
				for (Room room : rooms) {
					System.out.println(String.format("%s\t%s\t%s\t%.1f\t%s", room.getId(), room.getType(), room.getAc(),
							room.getRate(), room.getStatus()));

				}
				break;
			// RooomType
			case 2:
				System.out.println("\tRooms Type\n\t1 Single\n\t2 Double\n\t3 Studio\n\t4 Delux  ");
				System.out.println("Enter room type and AC/Non-AC");
				ArrayList<Room> r = RoomManager.checkAvailabilityByRoomType(scanner.next(), scanner.next());
				// ArrayList<Room> r = RoomManager.checkAvailabilityByPrice(roomtype,endPrice);
				System.out.println("-".repeat(100));
				System.out.println("Room Details");
				System.out.println("-".repeat(100));
				for (Room room : r) {
					System.out.println(String.format("%s\t%s\t%s\t%.1f\t%s", room.getId(), room.getType(), room.getAc(),
							room.getRate(), room.getStatus()));

				}
				break;
			default:
                throw new validateMenuChoiceException(Colors.RED+"Please enter a number between 1 or 2"+Colors.RESET);
		}
	}catch (InputMismatchException e) {
        System.out.println(Colors.RED+"Invalid input. Please enter a number."+Colors.RESET);
        scanner.nextLine();
    }catch(validateMenuChoiceException  e) {
    	System.out.println(e.getMessage());
    }
	}
}