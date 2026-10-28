import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Hotel {
    private final List<Room> rooms = new ArrayList<>();
    private final List<Reservation> reservations;

    public Hotel() {
        initializeRooms();
        reservations = FileManager.loadReservations();
    }

    private void initializeRooms() {
        rooms.add(new Room(101, "Standard", 2000));
        rooms.add(new Room(102, "Standard", 2000));
        rooms.add(new Room(103, "Standard", 2000));

        rooms.add(new Room(201, "Deluxe", 3500));
        rooms.add(new Room(202, "Deluxe", 3500));
        rooms.add(new Room(203, "Deluxe", 3500));

        rooms.add(new Room(301, "Suite", 6000));
        rooms.add(new Room(302, "Suite", 6000));
    }

    public void displayAllRooms() {
        System.out.println("\n========== HOTEL ROOMS ==========");

        for (Room room : rooms) {
            System.out.println(room);
        }

        System.out.println("=================================");
    }

    private boolean isRoomAvailable(
            int roomNumber,
            LocalDate checkIn,
            LocalDate checkOut) {

        for (Reservation reservation : reservations) {
            if (reservation.getRoomNumber() == roomNumber
                    && !"CANCELLED".equals(reservation.getStatus())) {

                // Two stays conflict if their date ranges overlap.
                boolean overlaps =
                    checkIn.isBefore(reservation.getCheckOut())
                    && checkOut.isAfter(reservation.getCheckIn());

                if (overlaps) {
                    return false;
                }
            }
        }

        return true;
    }

    public List<Room> searchAvailableRooms(
            String category,
            LocalDate checkIn,
            LocalDate checkOut) {

        List<Room> availableRooms = new ArrayList<>();

        for (Room room : rooms) {
            boolean categoryMatches =
                category.equalsIgnoreCase("All")
                || room.getCategory().equalsIgnoreCase(category);

            if (categoryMatches && isRoomAvailable(
                    room.getRoomNumber(), checkIn, checkOut)) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }

    public void displayAvailableRooms(
            String category,
            LocalDate checkIn,
            LocalDate checkOut) {

        List<Room> availableRooms =
            searchAvailableRooms(category, checkIn, checkOut);

        System.out.println("\nAvailable rooms from "
            + checkIn + " to " + checkOut + ":");

        if (availableRooms.isEmpty()) {
            System.out.println("No rooms available for these dates.");
            return;
        }

        for (Room room : availableRooms) {
            System.out.println(room);
        }
    }

    public Reservation makeReservation(
            String guestName,
            String phone,
            int roomNumber,
            LocalDate checkIn,
            LocalDate checkOut) {

        if (guestName == null || guestName.isBlank()
                || phone == null || phone.isBlank()) {
            System.out.println("Guest name and phone are required.");
            return null;
        }

        if (checkIn == null || checkOut == null
                || !checkIn.isBefore(checkOut)
                || checkIn.isBefore(LocalDate.now())) {
            System.out.println("Invalid dates. Check-in must be today or later, "
                + "and check-out must be after check-in.");
            return null;
        }

        Room selectedRoom = null;

        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Room number not found.");
            return null;
        }

        if (!isRoomAvailable(roomNumber, checkIn, checkOut)) {
            System.out.println("This room is not available for those dates.");
            return null;
        }

        long nights = java.time.temporal.ChronoUnit.DAYS.between(
            checkIn, checkOut
        );

        double total = nights * selectedRoom.getPricePerNight();

        if (!Double.isFinite(total) || total <= 0) {
            System.out.println("Could not calculate the booking amount.");
            return null;
        }

        String id = "HTL-" + UUID.randomUUID()
            .toString().substring(0, 8).toUpperCase();

        Reservation reservation = new Reservation(
            id,
            cleanField(guestName),
            cleanField(phone),
            roomNumber,
            selectedRoom.getCategory(),
            checkIn,
            checkOut,
            total,
            "CONFIRMED",
            "PENDING"
        );

        reservations.add(reservation);

        if (!FileManager.saveReservations(reservations)) {
            reservations.remove(reservation);
            System.out.println("Booking was not saved. Please try again.");
            return null;
        }

        System.out.println("\nReservation created successfully!");
        System.out.println("Your reservation ID is: " + id);
        System.out.println("Payment status: PENDING");

        return reservation;
    }

    private String cleanField(String value) {
        // Prevent the file separator from corrupting stored records.
        return value.replace("|", "/")
                    .replace("\n", " ")
                    .replace("\r", " ")
                    .trim();
    }

    public Reservation findReservation(String id) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId().equalsIgnoreCase(id.trim())) {
                return reservation;
            }
        }

        return null;
    }

    public void viewBookingDetails(String id) {
        Reservation reservation = findReservation(id);

        if (reservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        System.out.println(reservation);
    }

    public void cancelReservation(String id) {
        Reservation reservation = findReservation(id);

        if (reservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        if ("CANCELLED".equals(reservation.getStatus())) {
            System.out.println("This reservation is already cancelled.");
            return;
        }

        reservation.cancel();

        if (!FileManager.saveReservations(reservations)) {
            System.out.println("Cancellation could not be saved.");
            return;
        }

        System.out.println("Reservation cancelled successfully.");

        if ("REFUND_PENDING".equals(reservation.getPaymentStatus())) {
            System.out.println(
                "Refund status: REFUND_PENDING. "
                + "This is only a simulation; no actual refund was issued."
            );
        }
    }

    public void processReservationPayment(
            java.util.Scanner sc, String id) {

        Reservation reservation = findReservation(id);

        if (reservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        boolean changed = Payment.processPayment(sc, reservation);

        if (changed && !FileManager.saveReservations(reservations)) {
            System.out.println(
                "Warning: Payment status could not be saved to file."
            );
        }
    }

    public void displayAllReservations() {
        System.out.println("\n========== ALL RESERVATIONS ==========");

        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        for (Reservation reservation : reservations) {
            System.out.println(
                reservation.getReservationId()
                + " | Guest: " + reservation.getGuestName()
                + " | Room: " + reservation.getRoomNumber()
                + " | " + reservation.getCheckIn()
                + " to " + reservation.getCheckOut()
                + " | Status: " + reservation.getStatus()
                + " | Payment: " + reservation.getPaymentStatus()
            );
        }
    }
}
