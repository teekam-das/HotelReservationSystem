import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookingTest {

    @Test
    void shouldCreateBookingWithPendingStatus() {
        // Arrange
        Customer customer = new Customer("C001", "Kabir Ali Shah", "kabir@example.com", "0300-1112233");
        Room room = new Room("101", Room.RoomType.SINGLE, 5000);

        // Act
        Booking booking = new Booking("BKG-1", customer, room,
                LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 4));

        // Assert
        assertEquals(Booking.Status.PENDING, booking.getStatus());
    }

    @ParameterizedTest
    @CsvSource({
            "2026-07-01, 2026-07-04, 5000, 15000",
            "2026-07-01, 2026-07-02, 5000, 5000",
            "2026-08-10, 2026-08-20, 1000, 10000"
    })
    void shouldCalculateTotalCostBasedOnNightsAndPrice(String checkIn, String checkOut,
                                                         double price, double expectedCost) {
        // Arrange
        Customer customer = new Customer("C002", "Muhammad Aun", "aun@example.com", "0300-9998877");
        Room room = new Room("110", Room.RoomType.DOUBLE, price);
        Booking booking = new Booking("BKG-2", customer, room,
                LocalDate.parse(checkIn), LocalDate.parse(checkOut));

        // Act
        double actualCost = booking.calculateTotalCost();

        // Assert
        assertEquals(expectedCost, actualCost);
    }

    @Test
    void shouldConfirmBookingAndMarkRoomUnavailable() throws BookingException {
        // Arrange
        Customer customer = new Customer("C003", "Sara Khan", "sara@example.com", "0300-1212121");
        Room room = new Room("201", Room.RoomType.SUITE, 15000);
        Booking booking = new Booking("BKG-3", customer, room,
                LocalDate.of(2026, 9, 1), LocalDate.of(2026, 9, 3));

        // Act
        booking.confirm();

        // Assert
        assertEquals(Booking.Status.CONFIRMED, booking.getStatus());
        assertFalse(room.isAvailable());
    }

    @Test
    void shouldThrowExceptionWhenConfirmingAnAlreadyConfirmedBooking() throws BookingException {
        // Arrange
        Customer customer = new Customer("C004", "Hira Baig", "hira@example.com", "0300-3334455");
        Room room = new Room("202", Room.RoomType.DOUBLE, 8000);
        Booking booking = new Booking("BKG-4", customer, room,
                LocalDate.of(2026, 9, 5), LocalDate.of(2026, 9, 7));
        booking.confirm();

        // Act & Assert
        assertThrows(BookingException.class, booking::confirm);
    }

    @Test
    void shouldThrowExceptionWhenConfirmingBookingForUnavailableRoom() throws BookingException {
        // Arrange
        Room room = new Room("203", Room.RoomType.SINGLE, 5000);
        room.book(); // room already booked elsewhere
        Customer customer = new Customer("C005", "Bilal Ahmed", "bilal@example.com", "0300-7778899");
        Booking booking = new Booking("BKG-5", customer, room,
                LocalDate.of(2026, 9, 10), LocalDate.of(2026, 9, 12));

        // Act & Assert
        assertThrows(BookingException.class, booking::confirm);
    }

    @Test
    void shouldCancelConfirmedBookingAndFreeRoom() throws BookingException {
        // Arrange
        Customer customer = new Customer("C006", "Zara Iqbal", "zara@example.com", "0300-1010101");
        Room room = new Room("204", Room.RoomType.SINGLE, 5000);
        Booking booking = new Booking("BKG-6", customer, room,
                LocalDate.of(2026, 10, 1), LocalDate.of(2026, 10, 2));
        booking.confirm();

        // Act
        booking.cancel();

        // Assert
        assertEquals(Booking.Status.CANCELLED, booking.getStatus());
        assertTrue(room.isAvailable());
    }

    @Test
    void shouldThrowExceptionWhenCancellingAnAlreadyCancelledBooking() throws BookingException {
        // Arrange
        Customer customer = new Customer("C007", "Omar Farooq", "omar@example.com", "0300-2223344");
        Room room = new Room("205", Room.RoomType.DOUBLE, 8000);
        Booking booking = new Booking("BKG-7", customer, room,
                LocalDate.of(2026, 10, 5), LocalDate.of(2026, 10, 7));
        booking.confirm();
        booking.cancel();

        // Act & Assert
        assertThrows(BookingException.class, booking::cancel);
    }

    @Test
    void shouldThrowExceptionWhenCheckOutDateIsNotAfterCheckInDate() {
        // Arrange
        Customer customer = new Customer("C008", "Nida Hassan", "nida@example.com", "0300-5556677");
        Room room = new Room("206", Room.RoomType.SUITE, 15000);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new Booking("BKG-8", customer, room,
                        LocalDate.of(2026, 11, 10), LocalDate.of(2026, 11, 5)));
    }

    @Test
    void shouldThrowExceptionWhenCustomerIsNull() {
        // Arrange
        Room room = new Room("207", Room.RoomType.SINGLE, 5000);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new Booking("BKG-9", null, room,
                        LocalDate.of(2026, 11, 1), LocalDate.of(2026, 11, 3)));
    }

    @Test
    void shouldThrowExceptionWhenRoomIsNull() {
        // Arrange
        Customer customer = new Customer("C009", "Areeb Malik", "areeb@example.com", "0300-9991122");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new Booking("BKG-10", customer, null,
                        LocalDate.of(2026, 11, 1), LocalDate.of(2026, 11, 3)));
    }
}
