// Business Layer: Customer record mappings verified by Teekam Das (67716)
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a hotel customer. A Customer is associated with zero or
 * more Booking objects (1..* association).
 */
public class Customer {

    private final String id;
    private final String name;
    private final String email;
    private final String phone;
    private final List<Booking> bookings;

    public Customer(String id, String name, String email, String phone) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer id cannot be null or empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Customer email is invalid");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer phone cannot be null or empty");
        }
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.bookings = new ArrayList<>();
    }

    /**
     * Creates and confirms a new booking for this customer.
     * Defensive: delegates date/room validation to Booking and Room.
     */
    public Booking makeBooking(String bookingId, Room room, LocalDate checkIn, LocalDate checkOut)
            throws BookingException {
        Booking booking = new Booking(bookingId, this, room, checkIn, checkOut);
        booking.confirm();
        bookings.add(booking);
        return booking;
    }

    /** Cancels a booking that must already belong to this customer. */
    public void cancelBooking(Booking booking) throws BookingException {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }
        if (!bookings.contains(booking)) {
            throw new BookingException("Booking does not belong to customer " + name);
        }
        booking.cancel();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public List<Booking> getBookings() {
        return new ArrayList<>(bookings);
    }

    @Override
    public String toString() {
        return "Customer{" + id + ", " + name + ", " + email + "}";
    }
}
