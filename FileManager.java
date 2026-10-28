import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final Path DATA_FILE =
        Path.of("data", "reservations.txt");

    public static List<Reservation> loadReservations() {
        List<Reservation> reservations = new ArrayList<>();

        try {
            Files.createDirectories(DATA_FILE.getParent());

            if (!Files.exists(DATA_FILE)) {
                Files.createFile(DATA_FILE);
                return reservations;
            }

            List<String> lines = Files.readAllLines(DATA_FILE);

            for (String line : lines) {
                if (line.isBlank()) {
                    continue;
                }

                try {
                    reservations.add(Reservation.fromFileString(line));
                } catch (RuntimeException e) {
                    System.out.println(
                        "Warning: Skipped an invalid reservation record."
                    );
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading reservations: " + e.getMessage());
        }

        return reservations;
    }

    public static boolean saveReservations(
            List<Reservation> reservations) {

        try {
            Files.createDirectories(DATA_FILE.getParent());

            List<String> lines = new ArrayList<>();

            for (Reservation reservation : reservations) {
                lines.add(reservation.toFileString());
            }

            Files.write(
                DATA_FILE,
                lines,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            );

            return true;
        } catch (IOException e) {
            System.out.println("Error saving reservations: " + e.getMessage());
            return false;
        }
    }
}
