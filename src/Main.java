import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Library Management System ---");
            System.out.println("1. Add a new book");
            System.out.println("2. Remove a book"); // Artık çalışıyor
            System.out.println("3. Search book by ID (Dynamic Array)");
            System.out.println("4. Search book by title (BST)");
            System.out.println("5. List all books (Dynamic Array)");
            System.out.println("6. List all books alphabetically (BST)");
            System.out.println("7. Request to borrow a book (Queue)");
            System.out.println("8. Process borrow requests (Queue)");
            System.out.println("9. Borrow a book");
            System.out.println("10. Return a book");
            System.out.println("11. Undo last action (Stack)");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            // Kullanıcının sayı girip girmediğini kontrol et (Hata önleme)
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Satır sonu karakterini temizle
            } else {
                choice = -1; // Geçersiz giriş
                scanner.nextLine(); // Hatalı girdiyi temizle
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author: ");
                    String author = scanner.nextLine();
                    library.addBook(id, title, author);
                    break;
                case 2: // EKSİK OLAN KISIM BURASIYDI
                    System.out.print("Enter Book ID to remove: ");
                    int removeId = scanner.nextInt();
                    library.removeBook(removeId);
                    break;
                case 3:
                    System.out.print("Enter ID to search: ");
                    int searchId = scanner.nextInt();
                    library.searchById(searchId);
                    break;
                case 4:
                    System.out.print("Enter Title to search: ");
                    String searchTitle = scanner.nextLine();
                    library.searchByTitle(searchTitle);
                    break;
                case 5:
                    library.listAllBooks();
                    break;
                case 6:
                    library.listBooksAlphabetically();
                    break;
                case 7:
                    System.out.print("Enter User Name: ");
                    String user = scanner.nextLine();
                    System.out.print("Enter Book ID: ");
                    int bid = scanner.nextInt();
                    library.requestBorrow(user, bid);
                    break;
                case 8:
                    library.processRequests();
                    break;
                case 9:
                    System.out.print("Enter Book ID to borrow: ");
                    int borrowId = scanner.nextInt();
                    library.borrowBook(borrowId);
                    break;
                case 10:
                    System.out.print("Enter Book ID to return: ");
                    int returnId = scanner.nextInt();
                    library.returnBook(returnId);
                    break;
                case 11:
                    library.undoLastAction();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }
}