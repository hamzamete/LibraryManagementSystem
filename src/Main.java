import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library lib = new Library();
        Scanner sc = new Scanner(System.in);
        int c;

        do {
            System.out.println("1 Add\n2 Remove\n3 Search ID\n4 Search Title\n5 List\n6 List Alpha\n7 Request\n8 Process\n9 Borrow\n10 Return\n11 Undo\n0 Exit");
            c = sc.nextInt();
            sc.nextLine();

            switch (c) {
                case 1 -> lib.addBook(sc.nextInt(), sc.next(), sc.next());
                case 2 -> lib.removeBook(sc.nextInt());
                case 3 -> lib.searchById(sc.nextInt());
                case 4 -> lib.searchByTitle(sc.nextLine());
                case 5 -> lib.listAll();
                case 6 -> lib.listAlpha();
                case 7 -> lib.requestBorrow(sc.next(), sc.nextInt());
                case 8 -> lib.processRequests();
                case 9 -> lib.borrowBook(sc.nextInt());
                case 10 -> lib.returnBook(sc.nextInt());
                case 11 -> lib.undo();
            }
        } while (c != 0);
    }
}
