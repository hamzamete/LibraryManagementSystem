public class BST {

    private class Node {
        Book book;
        Node left, right;

        Node(Book book) {
            this.book = book;
        }
    }

    private Node root;

    // O(h) worst O(n)
    public void insert(Book book) {
        root = insertRec(root, book);
    }

    private Node insertRec(Node root, Book book) {
        if (root == null) return new Node(book);

        if (book.title.compareToIgnoreCase(root.book.title) < 0)
            root.left = insertRec(root.left, book);
        else
            root.right = insertRec(root.right, book);

        return root;
    }

    // O(h)
    public Book search(String title) {
        return searchRec(root, title);
    }

    private Book searchRec(Node root, String title) {
        if (root == null) return null;
        if (root.book.title.equalsIgnoreCase(title)) return root.book;

        if (title.compareToIgnoreCase(root.book.title) < 0)
            return searchRec(root.left, title);
        return searchRec(root.right, title);
    }

    // O(n)
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

    // O(h)
    public void delete(String title) {
        root = deleteRec(root, title);
    }

    private Node deleteRec(Node root, String title) {
        if (root == null) return null;

        if (title.compareToIgnoreCase(root.book.title) < 0)
            root.left = deleteRec(root.left, title);
        else if (title.compareToIgnoreCase(root.book.title) > 0)
            root.right = deleteRec(root.right, title);
        else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            Node min = findMin(root.right);
            root.book = min.book;
            root.right = deleteRec(root.right, min.book.title);
        }
        return root;
    }

    private Node findMin(Node root) {
        while (root.left != null)
            root = root.left;
        return root;
    }
}
