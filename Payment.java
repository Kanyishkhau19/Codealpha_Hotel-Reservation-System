import java.util.Scanner;

public class Payment {

    public static boolean processPayment(
            Scanner sc, Reservation reservation) {

        if ("CANCELLED".equals(reservation.getStatus())) {
            System.out.println("Cannot pay for a cancelled reservation.");
            return false;
        }

        if ("SUCCESSFUL".equals(reservation.getPaymentStatus())) {
            System.out.println("Payment has already been completed.");
            return false;
        }

        System.out.println("\n========== PAYMENT ==========");
        System.out.println("Reservation ID : "
            + reservation.getReservationId());
        System.out.printf("Amount          : ₹%.2f%n",
            reservation.getTotalAmount());
        System.out.println("1. Simulate successful payment");
        System.out.println("2. Simulate failed payment");
        System.out.println("0. Return");
        System.out.println("==============================");
        System.out.print("Choose an option: ");

        String choice = sc.nextLine().trim();

        switch (choice) {
            case "1":
                reservation.markPaymentSuccessful();
                System.out.println(
                    "Simulated payment successful!"
                );
                return true;

            case "2":
                reservation.markPaymentFailed();
                System.out.println(
                    "Simulated payment failed. You can try again."
                );
                return true;

            case "0":
                System.out.println("Payment cancelled by user.");
                return false;

            default:
                System.out.println("Invalid payment option.");
                return false;
        }
    }
}
