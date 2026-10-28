import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {
    private final String reservationId;
    private final String guestName;
    private final String phone;
    private final int roomNumber;
    private final String category;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final double totalAmount;

    private String status;
    private String paymentStatus;

    public Reservation(
            String reservationId,
            String guestName,
            String phone,
            int roomNumber,
            String category,
            LocalDate checkIn,
            LocalDate checkOut,
            double totalAmount,
            String status,
            String paymentStatus) {

        this.reservationId = reservationId;
        this.guestName = guestName;
        this.phone = phone;
        this.roomNumber = roomNumber;
        this.category = category;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalAmount = totalAmount;
        this.status = status;
        this.paymentStatus = paymentStatus;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getPhone() {
        return phone;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public long getNumberOfNights() {
        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    public void cancel() {
        status = "CANCELLED";
        if ("SUCCESSFUL".equals(paymentStatus)) {
            paymentStatus = "REFUND_PENDING";
        }
    }

    public void markPaymentSuccessful() {
        paymentStatus = "SUCCESSFUL";
    }

    public void markPaymentFailed() {
        paymentStatus = "FAILED";
    }

    @Override
    public String toString() {
        return "\n----------------------------------------"
            + "\nReservation ID : " + reservationId
            + "\nGuest Name     : " + guestName
            + "\nPhone          : " + phone
            + "\nRoom Number    : " + roomNumber
            + "\nRoom Category  : " + category
            + "\nCheck-in       : " + checkIn
            + "\nCheck-out      : " + checkOut
            + "\nNights         : " + getNumberOfNights()
            + String.format("\nTotal Amount   : ₹%.2f", totalAmount)
            + "\nBooking Status : " + status
            + "\nPayment Status : " + paymentStatus
            + "\n----------------------------------------";
    }

    // Format one reservation as a pipe-separated record for file storage.
    public String toFileString() {
        return String.join("|",
            reservationId,
            guestName,
            phone,
            String.valueOf(roomNumber),
            category,
            checkIn.toString(),
            checkOut.toString(),
            String.valueOf(totalAmount),
            status,
            paymentStatus
        );
    }

    public static Reservation fromFileString(String line) {
        String[] fields = line.split("\\|", -1);

        if (fields.length != 10) {
            throw new IllegalArgumentException("Invalid reservation record");
        }

        return new Reservation(
            fields[0],
            fields[1],
            fields[2],
            Integer.parseInt(fields[3]),
            fields[4],
            LocalDate.parse(fields[5]),
            LocalDate.parse(fields[6]),
            Double.parseDouble(fields[7]),
            fields[8],
            fields[9]
        );
    }
}
