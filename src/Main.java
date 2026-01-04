import java.util.Scanner;

/**
 * Main class is the entry point of the Library Management System.
 *
 * This class is responsible ONLY for:
 *  - Displaying the menu to the user
 *  - Reading user input from the console
 *  - Calling the appropriate methods in the Library class
 *
 
 */
public class Main {

    /**
     * Program execution starts here.
     */
    public static void main(String[] args) {

        /*
         * Create the Library object.
         * The Library constructor automatically loads existing books
         * from the text file into memory.
         */
        Library lib = new Library();

        /*
         * Scanner is used to read user input from the console.
         * It handles both numeric and string-based input.
         */
        Scanner sc = new Scanner(System.in);

        // Stores the user's menu choice
        int c;

        /*
         * Main application loop.
         * The menu is shown repeatedly until the user selects option 0 (Exit).
         */
        do {
            // Display menu options to the user
            System.out.println("\n--- Library Management System ---");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Search Book by ID");
            System.out.println("4. Search Book by Title");
            System.out.println("5. List All Books");
            System.out.println("6. List Books Alphabetically");
            System.out.println("7. Request to Borrow");
            System.out.println("8. Process Borrow Requests");
            System.out.println("9. Borrow Book (Direct)");
            System.out.println("10. Return Book");
            System.out.println("11. Undo Last Action");
            System.out.println("12. Register New User");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            /*
             * INPUT VALIDATION:
             * We first check whether the user entered a valid integer.
             * This prevents the program from crashing due to invalid input.
             */
            if (sc.hasNextInt()) {
                c = sc.nextInt();

                /*
                 * After reading an integer, we must clear the input buffer.
                 * This removes the leftover newline character ('\n'),
                 * which could otherwise break subsequent nextLine() calls.
                 */
                sc.nextLine();
            } else {
                /*
                 * If the input is not an integer, the invalid token is discarded
                 * and the menu is shown again.
                 */
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
                c = -1;
            }

            /*
             * SWITCH-CASE STRUCTURE:
             * Each case corresponds to one menu option.
             * Based on the user's choice, the appropriate Library method is called.
             */
            switch (c) {

                case 1 -> {
                    /*
                     * OPTION 1: Add a new book to the library.
                     * The user provides book ID, title, and author.
                     */
                    System.out.print("Enter Book ID: ");
                    int id = sc.nextInt();
                    sc.nextLine(); // Clear buffer after integer input

                    System.out.print("Enter Title: ");
                    // nextLine() allows titles with spaces (e.g., "Clean Code")
                    String title = sc.nextLine();

                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();

                    lib.addBook(id, title, author);
                    System.out.println("Book added successfully.");
                }

                case 2 -> {
                    /*
                     * OPTION 2: Remove a book using its unique ID.
                     */
                    System.out.print("Enter Book ID to remove: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    lib.removeBook(id);
                    System.out.println("Operation completed.");
                }

                case 3 -> {
                    /*
                     * OPTION 3: Search for a book by ID.
                     * Uses DynamicArray inside the Library class.
                     */
                    System.out.print("Enter Book ID to search: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    lib.searchById(id);
                }

                case 4 -> {
                    /*
                     * OPTION 4: Search for a book by its title.
                     * Uses Binary Search Tree (BST) for efficient lookup.
                     */
                    System.out.print("Enter Title to search: ");
                    String title = sc.nextLine();

                    lib.searchByTitle(title);
                }

                case 5 -> {
                    /*
                     * OPTION 5: List all books in the order they were added.
                     * Uses DynamicArray.
                     */
                    System.out.println("--- Catalog ---");
                    lib.listAll();
                }

                case 6 -> {
                    /*
                     * OPTION 6: List all books in alphabetical order.
                     * Uses BST in-order traversal.
                     */
                    System.out.println("--- Alphabetical List ---");
                    lib.listAlpha();
                }

                case 7 -> {/*
                 * OPTION 7: Add a borrow request to the waiting list.
                 * * Step 1: Validate the user against users.txt
                 * Step 2: Validate the input format for Book ID
                 * Step 3: Add to queue if valid
                 */
                    System.out.print("Enter User Name: ");
                    String user = sc.nextLine().trim();

                    // Check if the user is registered in the system
                    if (lib.isValidUser(user)) {
                        System.out.print("Enter Book ID: ");

                        // Input validation for integer ID
                        if (sc.hasNextInt()) {
                            int id = sc.nextInt();
                            sc.nextLine(); // Consume newline

                            lib.requestBorrow(user, id);
                            System.out.println("Request added to queue.");
                        } else {
                            System.out.println("Invalid input! Book ID must be a number.");
                            sc.nextLine(); // Clear invalid input
                        }
                    } else {
                        // Error message if user is not in users.txt
                        System.out.println("Error: User '" + user + "' is not registered in the system.");
                    }
                }

                case 8 -> {
                    /*
                     * OPTION 8: Process the next borrow request in the queue.
                     * The earliest request is handled first.
                     */
                    System.out.println("Processing next request...");
                    lib.processRequests();
                }

                case 9 -> {/*
                 * OPTION 9: Borrow a book directly by ID.
                 * Updated to include user validation before processing.
                 */
                    System.out.print("Enter User Name: ");
                    String user = sc.nextLine().trim();

                    // Step 1: Validate if the user exists in users.txt
                    if (lib.isValidUser(user)) {
                        System.out.print("Enter Book ID to borrow: ");

                        // Step 2: Input validation for Book ID
                        if (sc.hasNextInt()) {
                            int id = sc.nextInt();
                            sc.nextLine(); // Clear buffer after integer input

                            // Step 3: Perform the borrow operation
                            // The user name is validated, so we allow the transaction.
                            lib.borrowBook(id);
                        } else {
                            System.out.println("Invalid input! Book ID must be a number.");
                            sc.nextLine(); // Clear invalid input
                        }
                    } else {
                        // Error message if user is not registered
                        System.out.println("Error: User '" + user + "' is not registered in the system.");
                    }}

                case 10 -> {
                    /*
                     * OPTION 10: Return a borrowed book by ID.
                     * This action is also pushed onto the undo stack.
                     */
                    System.out.print("Enter Book ID to return: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    lib.returnBook(id);
                    System.out.println("Operation completed.");
                }

                case 11 -> {
                    /*
                     * OPTION 11: Undo the last borrow or return operation.
                     * Uses Stack (Last-In-First-Out).
                     */
                    System.out.println("Undoing last action...");
                    lib.undo();
                    System.out.println("Undo completed.");
                }

                case 12 -> {/*
                 * OPTION 12: Register a new user.
                 * Takes a name input and adds it to the system database.
                 */
                    System.out.print("Enter Name to Register: ");
                    String name = sc.nextLine().trim();

                    if (!name.isEmpty()) {
                        lib.registerUser(name);
                    } else {
                        System.out.println("Error: Name cannot be empty.");
                    }
                }

                case 0 -> {
                    /*
                     * OPTION 0: Exit the program.
                     */
                    System.out.println("Exiting System...");
                }

                default -> {
                    /*
                     * Handles invalid menu choices.
                     */
                    System.out.println("Invalid choice! Try again.");
                }
            }

        } while (c != 0);

        /*
         * Close the scanner before terminating the program
         * to release system resources.
         */
        sc.close();
    }
}
