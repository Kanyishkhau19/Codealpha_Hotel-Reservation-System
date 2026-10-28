import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        boolean running = true;

        System.out.println("========================================");
        System.out.println("       HOTEL RESERVATION SYSTEM");
        System.out.println("========================================");
        System.out.println("Welcome to our hotel booking service!");

        while (running) {
            displayMenu();
            String choice = readText("Enter your choice: ");

            switch (choice) {
                case "1":
                    hotel.displayAllRooms();
                    break;

                case "2":
                    searchRooms(hotel);
                    break;

                case "3":
                    bookRoom(hotel);
                    break;

                case "4":
                    cancelBooking(hotel);
                    break;

                case "5":
                    viewBooking(hotel);
                    break;

                case "6":
                    makePayment(hotel);
                    break;

                case "7":
                    hotel.displayAllReservations();
                    break;

                case "0":
                    running = false;
                    System.out.println("Thank you for using our system!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        sc.close();
    }

    private static void displayMenu() {
        System.out.println("\n============== MAIN MENU ==============");
        System.out.println("1. View All Rooms");
        System.out.println("2. Search Available Rooms");
        System.out.println("3. Make a Reservation");
        System.out.println("4. Cancel a Reservation");
        System.out.println("5. View Booking Details");
        System.out.println("6. Simulate Payment");
        System.out.println("7. View All Reservations");
        System.out.println("0. Exit");
        System.out.println("=======================================");
    }

    private static void searchRooms(Hotel hotel) {
        LocalDate[] dates = readBookingDates();

        if (dates == null) {
            return;
        }

        String category = readText(
            "Enter category (Standard/Deluxe/Suite/All): "
        );

        if (!category.equalsIgnoreCase("All")
                && !category.equalsIgnoreCase("Standard")
                && !category.equalsIgnoreCase("Deluxe")
                && !category.equalsIgnoreCase("Suite")) {
            System.out.println("Invalid room category.");
            return;
        }

        hotel.displayAvailableRooms(category, dates[0], dates[1]);
    }

    private static void bookRoom(Hotel hotel) {
        System.out.println("\n========== MAKE A RESERVATION ==========");

        String name = readText("Enter guest name: ");
        String phone = readText("Enter phone number: ");

        LocalDate[] dates = readBookingDates();

        if (dates == null) {
            return;
        }

        List<Room> availableRooms = hotel.searchAvailableRooms(
            "All", dates[0], dates[1]
        );

        if (availableRooms.isEmpty()) {
            System.out.println("Sorry, no rooms are available for those dates.");
            return;
        }

        System.out.println("\nAvailable rooms:");

        for (Room room : availableRooms) {
            System.out.println(room);
        }

        int roomNumber = readInt("Enter the room number to book: ");

        Reservation reservation = hotel.makeReservation(
            name, phone, roomNumber, dates[0], dates[1]
        );

        if (reservation != null) {
            System.out.print("Would you like to pay now? (yes/no): ");

            String answer = readText("").toLowerCase();

            if (answer.equals("yes") || answer.equals("y")) {
                hotel.processReservationPayment(
                    sc, reservation.getReservationId()
                );
            }

            System.out.println("\nYour booking summary:");
            hotel.viewBookingDetails(reservation.getReservationId());
        }
    }

    private static void cancelBooking(Hotel hotel) {
        String id = readText("Enter reservation ID to cancel: ");
        hotel.cancelReservation(id);
    }

    private static void viewBooking(Hotel hotel) {
        String id = readText("Enter reservation ID: ");
        hotel.viewBookingDetails(id);
    }

    private static void makePayment(Hotel hotel) {
        String id = readText("Enter reservation ID for payment: ");
        hotel.processReservationPayment(sc, id);
    }

    private static LocalDate[] readBookingDates() {
        try {
            LocalDate checkIn = LocalDate.parse(
                readText("Enter check-in date (YYYY-MM-DD): ")
            );

            LocalDate checkOut = LocalDate.parse(
                readText("Enter check-out date (YYYY-MM-DD): ")
            );

            if (checkIn.isBefore(LocalDate.now())) {
                System.out.println("Check-in cannot be in the past.");
                return null;
            }

            if (!checkOut.isAfter(checkIn)) {
                System.out.println(
                    "Check-out must be after check-in."
                );
                return null;
            }

            return new LocalDate[]{checkIn, checkOut};

        } catch (DateTimeParseException e) {
            System.out.println(
                "Invalid date. Use the format YYYY-MM-DD, "
                + "for example 2026-11-15."
            );
            return null;
        }
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }
}
