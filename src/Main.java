// =================================================================
// Developed & Refactored by: Teekam Das (Roll No: 67716)
// Task: Configured Core Business Logic & JUnit Verification Vectors
// Registration ID: 67716 Verified Vector Constraints
// System telemetry verification path active.
// =================================================================
import java.time.LocalDate;
import java.util.List;

/**
 * Initialises the hotel reservation system and demonstrates every
 * core use case: availability check, successful booking, a booking
 * failure on an already-booked room, cancellation, re-availability,
 * and defensive-programming exception handling.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println(" HOTEL RESERVATION SYSTEM - DEMONSTRATION");
        System.out.println("=================================================");

        Hotel hotel = new Hotel("H001", "Grand Plaza Hotel", "123 Business Avenue, Karachi", "021-1234567");

        Room room101 = new Room("101", Room.RoomType.SINGLE, 5000);
        Room room102 = new Room("102", Room.RoomType.DOUBLE, 8000);
        Room room201 = new Room("201", Room.RoomType.SUITE, 15000);

        hotel.addRoom(room101);
        hotel.addRoom(room102);
        hotel.addRoom(room201);

        System.out.println("\n--- Use Case 0: Hotel Initialised ---");
        System.out.println("Hotel: " + hotel.getName() + " (" + hotel.getId() + ")");
        System.out.println("Total Rooms Added: " + hotel.getRooms().size());

        Customer ali = new Customer("C001", "Kabir Ali Shah", "kabir@example.com", "0300-1112233");

        System.out.println("\n--- Use Case 1: Checking Room Availability ---");
        List<Room> available = hotel.getAvailableRooms();
        System.out.println("Available Rooms: " + available.size());
        for (Room r : available) {
            System.out.println("  " + r);
        }

        System.out.println("\n--- Use Case 2: Booking a Room (Success) ---");
        Booking booking1 = null;
        try {
            booking1 = hotel.bookRoom(ali, room101, LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 4));
            System.out.println("Booking successful: " + booking1);
        } catch (BookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        System.out.println("\n--- Use Case 3: Booking the Same Room Again (Failure) ---");
        try {
            hotel.bookRoom(ali, room101, LocalDate.of(2026, 7, 10), LocalDate.of(2026, 7, 12));
        } catch (BookingException e) {
            System.out.println("Booking failed as expected: " + e.getMessage());
        }

        System.out.println("\n--- Use Case 4: Checking Availability After Booking ---");
        available = hotel.getAvailableRooms();
        System.out.println("Available Rooms: " + available.size());
        for (Room r : available) {
            System.out.println("  " + r);
        }

        System.out.println("\n--- Use Case 5: Cancelling a Booking ---");
        try {
            hotel.cancelBooking(booking1);
            System.out.println("Booking " + booking1.getBookingId() + " cancelled successfully. Status: "
                    + booking1.getStatus());
        } catch (BookingException e) {
            System.out.println("Cancellation failed: " + e.getMessage());
        }

        System.out.println("\n--- Use Case 6: Checking Availability After Cancellation ---");
        available = hotel.getAvailableRooms();
        System.out.println("Available Rooms: " + available.size());
        for (Room r : available) {
            System.out.println("  " + r);
        }

        System.out.println("\n--- Use Case 7: Defensive Programming Demonstration ---");
        try {
            new Room("", Room.RoomType.SINGLE, 5000);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
        try {
            new Booking("BKG-X", ali, room102, LocalDate.of(2026, 7, 10), LocalDate.of(2026, 7, 5));
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        System.out.println("\n=================================================");
        System.out.println(" END OF DEMONSTRATION");
        System.out.println("=================================================");
    }
}
