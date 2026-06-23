import java.time.LocalDate;

/**
 * Demonstrates interface-based design (program to an interface,
 * not an implementation). Hotel implements this interface so that
 * any class needing booking behaviour could depend on the
 * abstraction instead of the concrete Hotel class.
 */
public interface IBookingManager {

    Booking bookRoom(Customer customer, Room room, LocalDate checkIn, LocalDate checkOut)
            throws BookingException;

    void cancelBooking(Booking booking) throws BookingException;
}
