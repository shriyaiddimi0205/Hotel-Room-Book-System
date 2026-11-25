
import java.util.*;

class Room {
    int roomNumber;
    String roomType;
    double price;
    boolean isAvailable;

    Room(int roomNumber, String roomType, double price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.isAvailable = true;
    }

    void displayRoom() {
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Room Type: " + roomType);
        System.out.println("Price per Night: Rs." + price);
        System.out.println("Status: " +
                (isAvailable ? "Available" : "Booked"));
        System.out.println("---------------------------");
    }
}

class Booking {
    String bookingId;
    String customerName;
    int roomNumber;
    int nights;
    double totalAmount;

    Booking(String bookingId, String customerName,
            int roomNumber, int nights, double totalAmount) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.nights = nights;
        this.totalAmount = totalAmount;
    }

    void displayBooking() {
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Customer Name: " + customerName);
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Number of Nights: " + nights);
        System.out.println("Total Amount: Rs." + totalAmount);
        System.out.println("---------------------------");
    }
}

public class Main {

    static ArrayList<Room> rooms = new ArrayList<>();
    static HashMap<String, Booking> bookings = new HashMap<>();
    static Scanner sc = new Scanner(System.in);

    static int bookingCounter = 1001;

    // Safe integer input
    static int getInteger(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    // Add rooms
    static void addRooms() {
        rooms.add(new Room(101, "Single", 1000));
        rooms.add(new Room(102, "Double", 1800));
        rooms.add(new Room(103, "Deluxe", 2500));
        rooms.add(new Room(104, "Suite", 4000));
        rooms.add(new Room(105, "Single", 1200));
    }

    // Display all rooms
    static void displayRooms() {
        System.out.println("\n----- HOTEL ROOM DETAILS -----");

        for (Room room : rooms) {
            room.displayRoom();
        }
    }

    // Linear Search
    static Room searchRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.roomNumber == roomNumber) {
                return room;
            }
        }
        return null;
    }

    // Book a room
    static void bookRoom() {

        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Customer name cannot be empty!");
            return;
        }

        int roomNumber = getInteger("Enter Room Number: ");

        Room room = searchRoom(roomNumber);

        if (room == null) {
            System.out.println("Room does not exist!");
            return;
        }

        if (!room.isAvailable) {
            System.out.println("Room is already booked!");
            return;
        }

        int nights = getInteger("Enter Number of Nights: ");

        if (nights <= 0) {
            System.out.println("Invalid number of nights!");
            return;
        }

        double amount = room.price * nights;

        String bookingId = "B" + bookingCounter++;

        Booking booking = new Booking(
                bookingId, name, roomNumber, nights, amount
        );

        bookings.put(bookingId, booking);

        room.isAvailable = false;

        System.out.println("\nRoom Booked Successfully!");
        System.out.println("Your Booking ID: " + bookingId);
        System.out.println("Total Amount: Rs." + amount);
    }

    // Search booking using HashMap
    static void searchBooking() {

        System.out.print("Enter Booking ID: ");
        String id = sc.nextLine().trim();

        Booking booking = bookings.get(id);

        if (booking != null) {
            System.out.println("\nBooking Found!");
            booking.displayBooking();
        } else {
            System.out.println("Booking Not Found!");
        }
    }

    // Cancel booking
    static void cancelBooking() {

        System.out.print("Enter Booking ID: ");
        String id = sc.nextLine().trim();

        Booking booking = bookings.remove(id);

        if (booking != null) {

            Room room = searchRoom(booking.roomNumber);

            if (room != null) {
                room.isAvailable = true;
            }

            System.out.println("Booking Cancelled Successfully!");

        } else {
            System.out.println("Invalid Booking ID!");
        }
    }

    // Sorting rooms by price
    static void sortRoomsByPrice() {

        Collections.sort(rooms,
                Comparator.comparingDouble(room -> room.price));

        System.out.println("\nRooms Sorted By Price:");

        for (Room room : rooms) {
            room.displayRoom();
        }
    }

    // Display all bookings
    static void displayAllBookings() {

        if (bookings.isEmpty()) {
            System.out.println("No Bookings Available!");
            return;
        }

        System.out.println("\n----- ALL BOOKINGS -----");

        for (Booking booking : bookings.values()) {
            booking.displayBooking();
        }
    }

    public static void main(String[] args) {

        addRooms();

        int choice;

        do {
            System.out.println("\n================================");
            System.out.println("     HOTEL ROOM BOOKING SYSTEM");
            System.out.println("================================");
            System.out.println("1. Display All Rooms");
            System.out.println("2. Search Room");
            System.out.println("3. Book Room");
            System.out.println("4. Search Booking");
            System.out.println("5. Cancel Booking");
            System.out.println("6. Sort Rooms By Price");
            System.out.println("7. Display All Bookings");
            System.out.println("8. Exit");

            choice = getInteger("Enter Your Choice: ");

            switch (choice) {

                case 1:
                    displayRooms();
                    break;

                case 2:
                    int number = getInteger("Enter Room Number: ");

                    Room room = searchRoom(number);

                    if (room != null) {
                        room.displayRoom();
                    } else {
                        System.out.println("Room Not Found!");
                    }
                    break;

                case 3:
                    bookRoom();
                    break;

                case 4:
                    searchBooking();
                    break;

                case 5:
                    cancelBooking();
                    break;

                case 6:
                    sortRoomsByPrice();
                    break;

                case 7:
                    displayAllBookings();
                    break;

                case 8:
                    System.out.println(
                        "Thank You For Using Our System!"
                    );
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 8);

        sc.close();
    }
}
