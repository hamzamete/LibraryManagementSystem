import java.io.*;
import java.util.Scanner;

public class Library {

    private DynamicArray catalog = new DynamicArray(5);
    private BST bst = new BST();
    private Queue queue = new Queue();
    private Stack stack = new Stack();

    private final String BOOK_FILE = "books.txt";

    public Library() {
        loadBooks();
    }

    public void addBook(int id, String title, String author) {
        Book b = new Book(id, title, author, true);
        catalog.add(b);
        bst.insert(b);
        saveBooks();
    }

    public void removeBook(int id) {
        int index = catalog.findIndexById(id);
        if (index == -1) return;

        Book b = catalog.get(index);
        catalog.remove(index);
        bst.delete(b.title);
        saveBooks();
    }

    public void searchById(int id) {
        Book b = catalog.findById(id);
        System.out.println(b == null ? "Not found" : b);
    }

    public void searchByTitle(String title) {
        Book b = bst.search(title);
        System.out.println(b == null ? "Not found" : b);
    }

    public void listAll() {
        for (int i = 0; i < catalog.size(); i++)
            System.out.println(catalog.get(i));
    }

    public void listAlpha() {
        bst.inOrderTraversal();
    }

    public void requestBorrow(String user, int id) {
        queue.enqueue(user, id);
    }

    public void processRequests() {
        Queue.Request r = queue.dequeue();
        if (r == null) return;

        Book b = catalog.findById(r.bookId);
        if (b != null && b.isAvailable)
            borrowBook(r.bookId);
        else
            queue.enqueue(r.userName, r.bookId);
    }

    public void borrowBook(int id) {
        Book b = catalog.findById(id);
        if (b != null && b.isAvailable) {
            b.isAvailable = false;
            stack.push(Stack.ActionType.BORROW, id);
            saveBooks();
        }
    }

    public void returnBook(int id) {
        Book b = catalog.findById(id);
        if (b != null && !b.isAvailable) {
            b.isAvailable = true;
            stack.push(Stack.ActionType.RETURN, id);
            saveBooks();
        }
    }

    public void undo() {
        Stack.Action a = stack.pop();
        if (a == null) return;

        Book b = catalog.findById(a.bookId);
        if (b == null) return;

        b.isAvailable = a.type == Stack.ActionType.BORROW;
        saveBooks();
    }

    private void loadBooks() {
        try (Scanner sc = new Scanner(new File(BOOK_FILE))) {
            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");
                Book b = new Book(Integer.parseInt(p[0]), p[1], p[2], Boolean.parseBoolean(p[3]));
                catalog.add(b);
                bst.insert(b);
            }
        } catch (Exception e) {}
    }

    private void saveBooks() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(BOOK_FILE))) {
            for (int i = 0; i < catalog.size(); i++) {
                Book b = catalog.get(i);
                pw.println(b.id + "," + b.title + "," + b.author + "," + b.isAvailable);
            }
        } catch (Exception e) {}
    }
}
