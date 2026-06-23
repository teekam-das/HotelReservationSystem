import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Represents a reservation linking exactly one Customer to exactly
 * one Room for a date range. Owns the total-cost calculation and the
 * booking lifecycle (PENDING -> CONFIRMED -> CANCELLED).
 */
public class Booking {

    public enum Status {
        PENDING, CONFIRMED, CANCELLED
    }

    private final String bookingId;
    private final Customer customer;
    private final Room room;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
    private double totalCost;
    private Status status;

    public Booking(String bookingId, Customer customer, Room room,
                    LocalDate checkInDate, LocalDate checkOutDate) {
        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new IllegalArgumentException("Booking id cannot be null or empty");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Check-in and check-out dates cannot be null");
        }
        if (!checkOutDate.isAfter(checkInDate)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
        this.bookingId = bookingId;
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = Status.PENDING;
        this.totalCost = calculateTotalCost();
    }

    /** Number of nights multiplied by the room's nightly price. */
    public double calculateTotalCost() {
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return nights * room.getPricePerNight();
    }

    /** Confirms the booking and marks the underlying room as booked. */
    public void confirm() throws BookingException {
        if (status == Status.CONFIRMED) {
            throw new BookingException("Booking " + bookingId + " is already confirmed");
        }
        if (!room.isAvailable()) {
            throw new BookingException("Room " + room.getRoomNumber() + " is not available");
        }
        room.book();
        this.status = Status.CONFIRMED;
        this.totalCost = calculateTotalCost();
    }

    /** Cancels the booking and frees the underlying room. */
    public void cancel() throws BookingException {
        if (status == Status.CANCELLED) {
            throw new BookingException("Booking " + bookingId + " is already cancelled");
        }
        room.checkOut();
        this.status = Status.CANCELLED;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Booking{" + bookingId + ", room=" + room.getRoomNumber()
                + ", customer=" + customer.getName() + ", " + checkInDate + " to " + checkOutDate
                + ", cost=Rs." + totalCost + ", status=" + status + "}";
    }
}
