import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HotelTest {

    private Hotel hotel;
    private Room room101;
    private Room room102;

    @BeforeEach
    void setUp() {
        // Arrange (shared fixture for every test in this class)
        hotel = new Hotel("H001", "Grand Plaza Hotel", "123 Business Avenue, Karachi", "021-1234567");
        room101 = new Room("101", Room.RoomType.SINGLE, 5000);
        room102 = new Room("102", Room.RoomType.DOUBLE, 8000);
    }

    @Test
    void shouldAddRoomToHotelSuccessfully() {
        // Act
        hotel.addRoom(room101);

        // Assert
        assertEquals(1, hotel.getRooms().size());
    }

    @Test
    void shouldThrowExceptionWhenAddingNullRoom() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> hotel.addRoom(null));
    }

    @Test
    void shouldThrowExceptionWhenAddingDuplicateRoomNumber() {
        // Arrange
        hotel.addRoom(room101);
        Room duplicateRoom = new Room("101", Room.RoomType.SUITE, 20000);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> hotel.addRoom(duplicateRoom));
    }

    @Test
    void shouldReturnOnlyAvailableRooms() {
        // Arrange
        hotel.addRoom(room101);
        hotel.addRoom(room102);
        room101.book();

        // Act
        List<Room> available = hotel.getAvailableRooms();

        // Assert
        assertEquals(1, available.size());
        assertEquals("102", available.get(0).getRoomNumber());
    }

    @Test
    void shouldFindRoomByValidRoomNumber() {
        // Arrange
        hotel.addRoom(room101);

        // Act
        Room found = hotel.findRoomById("101");

        // Assert
        assertEquals(room101, found);
    }

    @Test
    void shouldThrowExceptionWhenRoomIdNotFound() {
        // Arrange
        hotel.addRoom(room101);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> hotel.findRoomById("999"));
    }

    @Test
    void shouldBookRoomSuccessfullyThroughHotel() throws BookingException {
        // Arrange
        hotel.addRoom(room101);
        Customer customer = new Customer("C001", "Kabir Ali Shah", "kabir@example.com", "0300-1112233");

        // Act
        Booking booking = hotel.bookRoom(customer, room101,
                LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 4));

        // Assert
        assertEquals(Booking.Status.CONFIRMED, booking.getStatus());
        assertFalse(room101.isAvailable());
    }

    @Test
    void shouldThrowExceptionWhenBookingRoomThatDoesNotBelongToHotel() {
        // Arrange
        Room foreignRoom = new Room("999", Room.RoomType.SINGLE, 5000);
        Customer customer = new Customer("C002", "Muhammad Aun", "aun@example.com", "0300-9998877");

        // Act & Assert
        assertThrows(BookingException.class, () -> hotel.bookRoom(customer, foreignRoom,
                LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 4)));
    }

    @Test
    void shouldThrowExceptionWhenBookingAlreadyBookedRoomThroughHotel() throws BookingException {
        // Arrange
        hotel.addRoom(room101);
        Customer customer = new Customer("C003", "Sara Khan", "sara@example.com", "0300-1212121");
        hotel.bookRoom(customer, room101, LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 4));

        // Act & Assert
        assertThrows(BookingException.class, () -> hotel.bookRoom(customer, room101,
                LocalDate.of(2026, 7, 10), LocalDate.of(2026, 7, 12)));
    }

    @Test
    void shouldCancelBookingThroughHotel() throws BookingException {
        // Arrange
        hotel.addRoom(room101);
        Customer customer = new Customer("C004", "Hira Baig", "hira@example.com", "0300-3334455");
        Booking booking = hotel.bookRoom(customer, room101,
                LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 4));

        // Act
        hotel.cancelBooking(booking);

        // Assert
        assertEquals(Booking.Status.CANCELLED, booking.getStatus());
        assertTrue(room101.isAvailable());
    }
}
