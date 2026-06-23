# Hotel Reservation System

A console-based Hotel Reservation System built in Java for the Software
Construction & Development (SC&D) course project. The system models a
typical hotel booking workflow (Hotel, Room, Customer, Booking) using
clean object-oriented design, defensive programming, and a full JUnit 5
test suite.

**Group Members**
- Kabir Ali Shah (63650)
- Muhammad Aun (59709)

## Project Structure

```
HotelReservationSystem/
├── src/
│   ├── Hotel.java
│   ├── Room.java
│   ├── Customer.java
│   ├── Booking.java
│   ├── IBookingManager.java
│   ├── BookingException.java
│   └── Main.java
├── test/
│   ├── HotelTest.java
│   ├── RoomTest.java
│   ├── CustomerTest.java
│   └── BookingTest.java
└── README.md
```

## Prerequisites

- Java Development Kit (JDK) 11 or later
- JUnit 5 (the JUnit Platform Console Standalone jar is the simplest
  option for beginners; download `junit-platform-console-standalone.jar`
  from the official Maven Central repository)

## Build & Run Instructions

### 1. Compile and run the application

```bash
cd HotelReservationSystem
javac -d out src/*.java
java -cp out Main
```

### 2. Compile and run the tests

Place `junit-platform-console-standalone.jar` in the project root, then:

```bash
javac -d out -cp junit-platform-console-standalone.jar src/*.java test/*.java
java -jar junit-platform-console-standalone.jar -cp out --scan-classpath
```

All test classes (`HotelTest`, `RoomTest`, `CustomerTest`, `BookingTest`)
will be discovered and executed automatically, and a summary of
passed/failed tests will be printed to the console.

### 3. (Optional) Build with Maven

If you prefer Maven, create a standard `pom.xml` declaring the
`junit-jupiter` dependency (version 5.10+) with `maven-surefire-plugin`,
place these same files under `src/main/java` and `src/test/java`
respectively, and run `mvn test` / `mvn exec:java`.

## Design Overview

- **Hotel** aggregates a list of `Room` objects (1..* aggregation) and
  implements `IBookingManager`, acting as the entry point for booking
  and cancellation operations.
- **Room** owns its own availability state and refuses to be booked
  twice (`IllegalStateException`).
- **Customer** is associated with 1..* `Booking` objects and can create
  or cancel its own bookings.
- **Booking** links exactly one `Customer` to exactly one `Room`,
  validates its date range, and calculates total cost.
- **IBookingManager** is an interface (`bookRoom`, `cancelBooking`)
  that `Hotel` implements, demonstrating programming to an abstraction.
- **BookingException** is a custom checked exception for business-rule
  violations (e.g. booking an unavailable room).

## Defensive Programming

- All constructors validate their parameters (non-null, non-empty,
  positive values, valid email format, valid date ranges) and throw
  `IllegalArgumentException` on invalid input.
- Illegal state transitions are prevented: a room cannot be booked
  twice, a booking cannot be confirmed or cancelled twice.
- Business-rule failures (e.g. booking a room that belongs to another
  hotel, or one that is already taken) raise the checked
  `BookingException`, forcing callers to handle the failure explicitly
  rather than crash silently.

## Running Individual Use Cases

`Main.java` demonstrates, in order: hotel initialisation, an
availability check, a successful booking, a failed re-booking attempt,
availability after booking, a cancellation, availability after
cancellation, and finally two defensive-programming exception
scenarios. See the project report for full sample console output.
