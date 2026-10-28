# Hotel Reservation System

A console-based Hotel Reservation System developed using Java to simplify hotel room searching, booking, cancellation, and reservation management. The system allows users to explore different room categories, check availability, simulate payments, and view their booking details.

## Project Overview

The Hotel Reservation System is a Java application designed to demonstrate object-oriented programming, file handling, data management, and basic reservation workflows.

The application provides a simple interface through which users can search for rooms, make reservations, cancel existing bookings, and simulate payments. File handling can be used to store booking information and preserve reservation records between application sessions.

## Features

- **Room Search:** Search for available rooms based on room category and booking dates.
- **Room Categorization:** Organize rooms into Standard, Deluxe, and Suite categories.
- **Room Booking:** Reserve available rooms by providing guest details and stay dates.
- **Reservation Cancellation:** Cancel existing reservations and update room availability.
- **Payment Simulation:** Simulate payments and display payment confirmation details.
- **Booking Details:** View reservation ID, guest information, room category, dates, total cost, and payment status.
- **Availability Management:** Track room availability and prevent overlapping bookings.
- **Data Persistence:** Store and retrieve reservation details using file handling.
- **Input Validation:** Validate dates, guest information, room availability, and booking requests.

## Technologies Used

- **Programming Language:** Java
- **User Interface:** Command-Line Interface (CLI)
- **Programming Concepts:** Object-Oriented Programming (OOP)
- **Core Concepts:** Classes, objects, encapsulation, inheritance, and polymorphism where appropriate
- **Collections:** `ArrayList`, `HashMap`, and other Java Collections
- **Date Handling:** Java Date and Time API
- **File Handling:** Java I/O and file operations
- **Development Environment:** Visual Studio Code
- **Database:** Optional future enhancement using MySQL or SQLite

## Room Categories

| Category | Description | Sample Price per Night |
|---|---|---:|
| Standard | Comfortable room with essential amenities | ₹2,000 |
| Deluxe | Spacious room with additional amenities | ₹3,500 |
| Suite | Premium room with enhanced facilities | ₹6,000 |

*Note: These prices are illustrative and can be modified in the application.*

## Project Structure

```text
HotelReservationSystem/
│
├── src/
│   ├── Main.java
│   ├── Room.java
│   ├── Guest.java
│   ├── Reservation.java
│   ├── Hotel.java
│   ├── Payment.java
│   └── FileManager.java
│
├── data/
│   └── reservations.txt
│
├── out/
└── README.md
```

### Description of Java Files

| File | Responsibility |
|---|---|
| `Main.java` | Provides the main menu and handles user input. |
| `Room.java` | Represents a hotel room, its category, price, and room number. |
| `Guest.java` | Stores guest information. |
| `Reservation.java` | Manages reservation details, dates, status, and reservation ID. |
| `Hotel.java` | Handles room searching, availability, booking, and cancellation. |
| `Payment.java` | Simulates payments and maintains payment status. |
| `FileManager.java` | Saves and loads reservation information using file handling. |

## Prerequisites

Before running the project, install:

1. Java Development Kit (JDK 17 or later recommended).
2. Visual Studio Code or another Java-compatible IDE.
3. A terminal such as Windows PowerShell or Command Prompt.

Verify your Java installation:

```powershell
java -version
javac -version
```

## How to Run the Project

### Step 1: Open the Project

Open the `HotelReservationSystem` folder in Visual Studio Code.

### Step 2: Open the Terminal

Select **Terminal → New Terminal** and ensure that the terminal is in the project root folder.

### Step 3: Compile the Source Files

```powershell
javac -d out src\*.java
```

### Step 4: Run the Application

```powershell
java -cp out Main
```

## Application Menu

The application can provide the following menu options:

```text
========================================
       HOTEL RESERVATION SYSTEM
========================================
1. View All Rooms
2. Search Available Rooms
3. Make a Reservation
4. Cancel a Reservation
5. View Booking Details
6. Simulate Payment
7. View All Reservations
0. Exit
========================================
```

## Booking Workflow

1. The user searches for rooms by category and check-in/check-out dates.
2. The system checks availability for the requested period.
3. The user enters guest details and selects a room.
4. The system generates a unique reservation ID and calculates the total cost.
5. The user completes a simulated payment.
6. The reservation details and status are stored in a file.
7. The user can retrieve booking details or cancel the reservation later.

## Payment Simulation

The system demonstrates a simplified payment workflow without connecting to a real payment gateway.

Possible payment statuses include:

- Pending
- Successful
- Failed
- Refunded or cancelled, depending on the cancellation workflow

A successful simulated payment updates the booking's payment status. The system must not collect real card details or claim to process actual payments.

## Data Storage

The project uses file handling to store reservation records in a local data file.

The application should support:

- Saving new reservations.
- Loading saved reservations when the application starts.
- Updating reservation status after cancellation.
- Preserving booking information across application restarts.
- Checking saved bookings when determining room availability.

Room availability should be calculated using reservation dates rather than only a permanent `isAvailable` flag, because a room may be booked for one date range and available for another.

## Key Concepts Demonstrated

- Object-oriented programming and modular design.
- Encapsulation of room, guest, reservation, and payment data.
- Collections for managing rooms and bookings.
- Date validation and reservation conflict detection.
- File input/output and persistent data storage.
- Unique reservation ID generation.
- Payment simulation and booking status management.
- Exception handling and input validation.

## Limitations

- Payments are simulated and do not involve real money.
- The application does not connect to an external hotel booking service.
- File-based storage is suitable for a learning project but is less reliable for simultaneous users than a database.
- The application uses predefined room inventory and sample prices.
- Advanced features such as authentication, online payments, and email confirmations are not included unless implemented separately.

## Future Enhancements

- Integrate MySQL or SQLite for structured data storage.
- Add user registration and login.
- Develop a graphical interface using Java Swing or JavaFX.
- Generate printable booking receipts.
- Add seasonal pricing, taxes, and discounts.
- Send booking confirmation notifications.
- Integrate a real payment gateway in a properly secured production environment.
- Add an administrator dashboard for managing room inventory and pricing.

## Learning Outcomes

This project provides practical experience in Java programming, object-oriented design, date handling, file I/O, data persistence, and reservation management. It demonstrates how real-world booking workflows can be represented in a modular application.

## Disclaimer

This project is intended for educational and demonstration purposes only. Payment processing is simulated, and the application is not connected to a real hotel booking platform.
