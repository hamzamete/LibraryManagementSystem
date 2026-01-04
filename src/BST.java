public class BST {

    /*
     * Inner Node class representing each node of the Binary Search Tree.
     * Each node stores:
     * - a Book object
     * - a reference to the left child
     * - a reference to the right child
     */
    private class Node {
        Book book;
        Node left, right;

        // Constructor that initializes the node with a Book object
        Node(Book book) {
            this.book = book;
        }
    }

    // Root node of the BST
    private Node root;

    /*
     * Inserts a new Book into the BST.
     * The book title is used as the key for ordering.
     *
     * Time Complexity:
     * Average case: O(h), where h is the height of the tree
     * Worst case: O(n), when the tree becomes skewed
     */
    public void insert(Book book) {
        root = insertRec(root, book);
    }

    /*
     * Recursive helper method for inserting a book into the BST.
     * Compares book titles case-insensitively to maintain alphabetical order.
     */
    private Node insertRec(Node root, Book book) {
        // If the current position is empty, create a new node
        if (root == null) return new Node(book);

        // If the new book's title comes before the current node's title,
        // insert it into the left subtree
        if (book.title.compareToIgnoreCase(root.book.title) < 0)
            root.left = insertRec(root.left, book);
        else
            // Otherwise, insert it into the right subtree
            root.right = insertRec(root.right, book);

        return root;
    }

    /*
     * Searches for a book by its title in the BST.
     *
     * Time Complexity:
     * Average case: O(h)
     * Worst case: O(n)
     */
    public Book search(String title) {
        return searchRec(root, title);
    }

    /*
     * Recursive helper method for searching a book by title.
     * Traverses left or right subtree based on title comparison.
     */
    private Book searchRec(Node root, String title) {
        // If the node is null, the book was not found
        if (root == null) return null;

        // If titles match (case-insensitive), return the book
        if (root.book.title.equalsIgnoreCase(title)) return root.book;

        // Decide which subtree to search
        if (title.compareToIgnoreCase(root.book.title) < 0)
            return searchRec(root.left, title);

        return searchRec(root.right, title);
    }

    /*
     * Performs an in-order traversal of the BST.
     * This prints all books in alphabetical order by title.
     *
     * Time Complexity:
     * O(n), where n is the number of nodes in the tree
     */
    public void inOrderTraversal() {
        inOrderRec(root);
    }

    /*
     * Recursive helper method for in-order traversal.
     * Visits left subtree, current node, then right subtree.
     */
    private void inOrderRec(Node root) {
        if (root != null) {
            inOrderRec(root.left);
            System.out.println(root.book);
            inOrderRec(root.right);
        }
    }

    /*
     * Deletes a book from the BST using its title as the key.
     *
     * Time Complexity:
     * Average case: O(h)
     * Worst case: O(n)
     */
    public void delete(String title) {
        root = deleteRec(root, title);
    }

    /*
     * Recursive helper method for deleting a node from the BST.
     * Handles three cases:
     * 1. Node has no children
     * 2. Node has one child
     * 3. Node has two children
     */
    private Node deleteRec(Node root, String title) {
        // If the tree is empty or the title is not found
        if (root == null) return null;

        // Traverse the tree to find the node to delete
        if (title.compareToIgnoreCase(root.book.title) < 0)
            root.left = deleteRec(root.left, title);
        else if (title.compareToIgnoreCase(root.book.title) > 0)
            root.right = deleteRec(root.right, title);
        else {
            // Node found

            // Case 1 & 2: node has at most one child
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            // Case 3: node has two children
            // Find the smallest node in the right subtree (in-order successor)
            Node min = findMin(root.right);

            // Replace current node's book with successor's book
            root.book = min.book;

            // Delete the successor node
            root.right = deleteRec(root.right, min.book.title);
        }
        return root;
    }

    /*
     * Finds the node with the minimum key (alphabetically smallest title)
     * in a given subtree by traversing left children.
     *
     * Time Complexity:
     * Worst case: O(n)
     */
    private Node findMin(Node root) {
        while (root.left != null)
            root = root.left;
        return root;
    }
}
