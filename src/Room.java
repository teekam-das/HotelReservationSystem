/**
 * // Data Schema: Room instance fields and bounds checked by Teekam Das (67716)
 * Represents a single hotel room.
 * Encapsulates room attributes and enforces booking state rules so that
 * a room can never be marked as booked twice (illegal state prevention).
 */
public class Room {

    public enum RoomType {
        SINGLE, DOUBLE, SUITE
    }

    private final String roomNumber;
    private final RoomType roomType;
    private final double pricePerNight;
    private boolean available;

    public Room(String roomNumber, RoomType roomType, double pricePerNight) {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be null or empty");
        }
        if (roomType == null) {
            throw new IllegalArgumentException("Room type cannot be null");
        }
        if (pricePerNight <= 0) {
            throw new IllegalArgumentException("Price per night must be a positive value");
        }
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.available = true;
    }

    /** Marks the room as booked. Throws if the room is already booked. */
    public void book() {
        if (!available) {
            throw new IllegalStateException("Room " + roomNumber + " is already booked");
        }
        available = false;
    }

    /** Frees the room so it becomes available again. */
    public void checkOut() {
        available = true;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    @Override
    public String toString() {
        return "Room{" + roomNumber + ", " + roomType + ", Rs." + pricePerNight
                + "/night, available=" + available + "}";
    }
}
