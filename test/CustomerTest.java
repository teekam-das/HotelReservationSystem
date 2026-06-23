import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void shouldCreateCustomerWithValidAttributes() {
        // Arrange & Act
        Customer customer = new Customer("C001", "Kabir Ali Shah", "kabir@example.com", "0300-1112233");

        // Assert
        assertEquals("C001", customer.getId());
        assertEquals("Kabir Ali Shah", customer.getName());
        assertEquals("kabir@example.com", customer.getEmail());
        assertTrue(customer.getBookings().isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void shouldThrowExceptionWhenNameIsNullOrEmpty(String invalidName) {
        assertThrows(IllegalArgumentException.class,
                () -> new Customer("C002", invalidName, "test@example.com", "0300-1112233"));
    }

    @Test
    void shouldThrowExceptionWhenEmailDoesNotContainAtSymbol() {
        // Arrange, Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Customer("C003", "Muhammad Aun", "invalid-email", "0300-1112233"));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Customer("C004", "Muhammad Aun", null, "0300-1112233"));
    }

    @Test
    void shouldAddBookingWhenCustomerMakesABooking() throws BookingException {
        // Arrange
        Customer customer = new Customer("C005", "Sara Khan", "sara@example.com", "0300-1212121");
        Room room = new Room("301", Room.RoomType.SINGLE, 5000);

        // Act
        Booking booking = customer.makeBooking("BKG-1", room,
                LocalDate.of(2026, 12, 1), LocalDate.of(2026, 12, 3));

        // Assert
        assertEquals(1, customer.getBookings().size());
        assertEquals(Booking.Status.CONFIRMED, booking.getStatus());
    }

    @Test
    void shouldCancelExistingBookingSuccessfully() throws BookingException {
        // Arrange
        Customer customer = new Customer("C006", "Hira Baig", "hira@example.com", "0300-3334455");
        Room room = new Room("302", Room.RoomType.DOUBLE, 8000);
        Booking booking = customer.makeBooking("BKG-2", room,
                LocalDate.of(2026, 12, 5), LocalDate.of(2026, 12, 7));

        // Act
        customer.cancelBooking(booking);

        // Assert
        assertEquals(Booking.Status.CANCELLED, booking.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenCancellingBookingThatDoesNotBelongToCustomer() {
        // Arrange
        Customer customerA = new Customer("C007", "Bilal Ahmed", "bilal@example.com", "0300-7778899");
        Customer customerB = new Customer("C008", "Zara Iqbal", "zara@example.com", "0300-1010101");
        Room room = new Room("303", Room.RoomType.SUITE, 15000);
        Booking foreignBooking = new Booking("BKG-3", customerB, room,
                LocalDate.of(2026, 12, 10), LocalDate.of(2026, 12, 12));

        // Act & Assert
        assertThrows(BookingException.class, () -> customerA.cancelBooking(foreignBooking));
    }

    @Test
    void shouldThrowExceptionWhenCancellingNullBooking() {
        // Arrange
        Customer customer = new Customer("C009", "Omar Farooq", "omar@example.com", "0300-2223344");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> customer.cancelBooking(null));
    }
}
