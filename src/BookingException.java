/**
 * Custom checked exception used for business-rule violations in the
 * reservation workflow (e.g. booking an unavailable room, cancelling
 * a booking that does not belong to a customer).
 *
 * It is deliberately a checked exception so that callers are forced
 * to handle booking failures explicitly, rather than letting them
 * fail silently at runtime.
 */
public class BookingException extends Exception {

    public BookingException(String message) {
        super(message);
    }

    public BookingException(String message, Throwable cause) {
        super(message, cause);
    }
}
