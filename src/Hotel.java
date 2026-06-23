import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a hotel that aggregates a collection of Room objects
 * (1..* aggregation) and acts as the booking manager for those rooms.
 */
public class Hotel implements IBookingManager {

    private final String id;
    private final String name;
    private final String address;
    private final String phone;
    private final List<Room> rooms;
    private int bookingCounter;

    public Hotel(String id, String name, String address, String phone) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Hotel id cannot be null or empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Hotel name cannot be null or empty");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Hotel address cannot be null or empty");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Hotel phone cannot be null or empty");
        }
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.rooms = new ArrayList<>();
        this.bookingCounter = 1;
    }

    /** Adds a room, preventing duplicate room numbers (illegal state). */
    public void addRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        boolean duplicate = rooms.stream()
                .anyMatch(r -> r.getRoomNumber().equals(room.getRoomNumber()));
        if (duplicate) {
            throw new IllegalArgumentException("Room " + room.getRoomNumber()
                    + " already exists in hotel " + name);
        }
        rooms.add(room);
    }

    public List<Room> getAvailableRooms() {
        List<Room> available = new ArrayList<>();
        for (Room r : rooms) {
            if (r.isAvailable()) {
                available.add(r);
            }
        }
        return available;
    }

    public Room findRoomById(String roomNumber) {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be null or empty");
        }
        Optional<Room> found = rooms.stream()
                .filter(r -> r.getRoomNumber().equals(roomNumber))
                .findFirst();
        return found.orElseThrow(() ->
                new IllegalArgumentException("Room " + roomNumber + " not found in hotel " + name));
    }

    @Override
    public Booking bookRoom(Customer customer, Room room, LocalDate checkIn, LocalDate checkOut)
            throws BookingException {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        if (!rooms.contains(room)) {
            throw new BookingException("Room " + room.getRoomNumber() + " does not belong to hotel " + name);
        }
        String bookingId = "BKG-" + bookingCounter;
        bookingCounter++;
        return customer.makeBooking(bookingId, room, checkIn, checkOut);
    }

    @Override
    public void cancelBooking(Booking booking) throws BookingException {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }
        booking.getCustomer().cancelBooking(booking);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public List<Room> getRooms() {
        return new ArrayList<>(rooms);
    }
}
