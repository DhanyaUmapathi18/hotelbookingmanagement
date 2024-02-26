package com.data;

import com.menu.*;

import java.util.InputMismatchException;
import java.util.Scanner;
import com.exception.validateMenuChoiceException;
import com.menu.AdminMenu;
import com.menu.CustomerMenu;
import com.service.Background;
/*
* The Driver class implements an application that 


* simply have the main menu operation of hotel booking management  and 
* print the result to standard output
* @author Dhanya Umapathi (Expleo)
* @since 20 FEB 2024
*
* 
*/
public class DriverHotelManagement {

    public static void main(String[] args)
{	
    	Background bg = new Background();
        mainMenu();

    }

    public static void mainMenu()  {
        Scanner scanner = new Scanner(System.in);
        do {
        	int consoleWidth = 50;
            System.out.println("-".repeat(184));
            System.out.println(" ".repeat(consoleWidth) +"WELCOME TO HOTEL BOOKING MANAGEMENT");
            System.out.println("-".repeat(184));
            System.out.println("\t\tUsers ");
            System.out.println("\t\t1. Admin\n\t\t2. Customer\n\t\t3. Guest\n\t\t4. Exit");
            System.out.println("-".repeat(184));
            try { 
                System.out.print("Enter your choice: ");
                short choice = scanner.nextShort();
                switch (choice) {
                    case 1:
                        new AdminMenu().doAuthorization();
                        break;
                    case 2:
                         new CustomerMenu().doAuthorization();
                     
                        break;
                    case 3:
                        new GuestMenu().guestMenu();
                        break;
                    case 4:
                        System.out.print("Are you sure want to exit out : ");
                        String exit = scanner.next();
                        if (exit.equalsIgnoreCase("yes")) {
                            System.out.println("Loged out");
                            scanner.close();
                            System.exit(0);
                        } else
                            mainMenu();
                        break;
                    default:
                        throw new validateMenuChoiceException(Colors.RED+"Please enter a number between 1, 2, 3, or 4"+Colors.RESET);
                }
            } catch (InputMismatchException e) {
                System.out.println(Colors.RED+"Invalid input. Please enter a number."+Colors.RESET);
                scanner.nextLine();
            }catch(validateMenuChoiceException  e) {
            	System.out.println(e.getMessage());
            }
        } while (true);

    }


}
