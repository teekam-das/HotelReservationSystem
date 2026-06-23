import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    @Test
    void shouldCreateRoomWithValidAttributes() {
        // Arrange
        String roomNumber = "101";
        Room.RoomType type = Room.RoomType.SINGLE;
        double price = 5000;

        // Act
        Room room = new Room(roomNumber, type, price);

        // Assert
        assertEquals(roomNumber, room.getRoomNumber());
        assertEquals(type, room.getRoomType());
        assertEquals(price, room.getPricePerNight());
        assertTrue(room.isAvailable());
    }

    @Test
    void shouldMarkRoomAsUnavailableAfterBooking() {
        // Arrange
        Room room = new Room("102", Room.RoomType.DOUBLE, 8000);

        // Act
        room.book();

        // Assert
        assertFalse(room.isAvailable());
    }

    @Test
    void shouldThrowExceptionWhenBookingAnAlreadyBookedRoom() {
        // Arrange
        Room room = new Room("103", Room.RoomType.SUITE, 15000);
        room.book();

        // Act & Assert
        assertThrows(IllegalStateException.class, room::book);
    }

    @Test
    void shouldBecomeAvailableAgainAfterCheckOut() {
        // Arrange
        Room room = new Room("104", Room.RoomType.SINGLE, 5000);
        room.book();

        // Act
        room.checkOut();

        // Assert
        assertTrue(room.isAvailable());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void shouldThrowExceptionWhenRoomNumberIsNullOrEmpty(String invalidRoomNumber) {
        // Arrange, Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Room(invalidRoomNumber, Room.RoomType.SINGLE, 5000));
    }

    @Test
    void shouldThrowExceptionWhenRoomNumberIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Room(null, Room.RoomType.SINGLE, 5000));
    }

    @Test
    void shouldThrowExceptionWhenRoomTypeIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Room("105", null, 5000));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, -1, -500.5})
    void shouldThrowExceptionWhenPriceIsZeroOrNegative(double invalidPrice) {
        // Arrange, Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Room("106", Room.RoomType.SINGLE, invalidPrice));
    }
}
