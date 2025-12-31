/**
 * Custom Stack implementation for Undo operations.
 */
public class Stack {
    // Defines the type of action to undo
    public enum ActionType {
        BORROW, RETURN
    }

    public static class Action {
        ActionType type;
        int bookId;
        Action next;

        public Action(ActionType type, int bookId) {
            this.type = type;
            this.bookId = bookId;
        }
    }

    private Action top;

    // Time Complexity: O(1)
    public void push(ActionType type, int bookId) {
        Action newAction = new Action(type, bookId);
        newAction.next = top;
        top = newAction;
    }

    // Time Complexity: O(1)
    public Action pop() {
        if (top == null) return null;
        Action temp = top;
        top = top.next;
        return temp;
    }

    public boolean isEmpty() {
        return top == null;
    }
}
