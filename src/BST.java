/**
 * Binary Search Tree to store books sorted by Title.
 */
public class BST {
    private class Node {
        Book book;
        Node left, right;

        Node(Book book) {
            this.book = book;
            this.left = this.right = null;
        }
    }

    private Node root;

    // Time Complexity: O(h) where h is height. Worst case O(n).
    public void insert(Book book) {
        root = insertRec(root, book);
    }

    private Node insertRec(Node root, Book book) {
        if (root == null) {
            root = new Node(book);
            return root;
        }
        // Alphabetical comparison
        if (book.title.compareToIgnoreCase(root.book.title) < 0)
            root.left = insertRec(root.left, book);
        else if (book.title.compareToIgnoreCase(root.book.title) > 0)
            root.right = insertRec(root.right, book);

        return root;
    }

    // Time Complexity: O(h) where h is height. Worst case O(n).
    public Book search(String title) {
        return searchRec(root, title);
    }

    private Book searchRec(Node root, String title) {
        if (root == null) return null;
        if (root.book.title.equalsIgnoreCase(title)) return root.book;

        if (title.compareToIgnoreCase(root.book.title) < 0)
            return searchRec(root.left, title);
        else
            return searchRec(root.right, title);
    }

    // Time Complexity: O(n) - visits every node
    public void inOrderTraversal() {
        inOrderRec(root);
    }

    private void inOrderRec(Node root) {
        if (root != null) {
            inOrderRec(root.left);
            System.out.println(root.book);
            inOrderRec(root.right);
        }
    }
}
