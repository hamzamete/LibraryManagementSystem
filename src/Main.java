import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library lib = new Library();
        Scanner sc = new Scanner(System.in);
        int c;

        do {
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
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            if (sc.hasNextInt()) {
                c = sc.nextInt();
                sc.nextLine(); // int girdisinden sonraki 'enter' karakterini temizle (ÖNEMLİ)
            } else {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine(); // Hatalı girdiyi temizle
                c = -1;
            }

            switch (c) {
                case 1 -> {
                    System.out.print("Enter Book ID: ");
                    int id = sc.nextInt();
                    sc.nextLine(); // ID sonrası temizlik
                    System.out.print("Enter Title: ");
                    String title = sc.nextLine(); // Boşluklu isimleri okur (örn: Clean Code)
                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();
                    lib.addBook(id, title, author);
                    System.out.println("Book added successfully.");
                }
                case 2 -> {
                    System.out.print("Enter Book ID to remove: ");
                    lib.removeBook(sc.nextInt());
                    System.out.println("Operation completed.");
                }
                case 3 -> {
                    System.out.print("Enter Book ID to search: ");
                    lib.searchById(sc.nextInt());
                }
                case 4 -> {
                    System.out.print("Enter Title to search: ");
                    lib.searchByTitle(sc.nextLine());
                }
                case 5 -> {
                    System.out.println("--- Catalog ---");
                    lib.listAll();
                }
                case 6 -> {
                    System.out.println("--- Alphabetical List ---");
                    lib.listAlpha();
                }
                case 7 -> {
                    System.out.print("Enter User Name: ");
                    String user = sc.nextLine();
                    System.out.print("Enter Book ID: ");
                    int id = sc.nextInt();
                    lib.requestBorrow(user, id);
                    System.out.println("Request added to queue.");
                }
                case 8 -> {
                    System.out.println("Processing next request...");
                    lib.processRequests();
                }
                case 9 -> {
                    System.out.print("Enter Book ID to borrow: ");
                    lib.borrowBook(sc.nextInt());
                    System.out.println("Operation completed.");
                }
                case 10 -> {
                    System.out.print("Enter Book ID to return: ");
                    lib.returnBook(sc.nextInt());
                    System.out.println("Operation completed.");
                }
                case 11 -> {
                    System.out.println("Undoing last action...");
                    lib.undo();
                    System.out.println("Undo completed.");
                }
                case 0 -> System.out.println("Exiting System...");
                default -> System.out.println("Invalid choice! Try again.");
            }
        } while (c != 0);
        
        sc.close();
    }
}
