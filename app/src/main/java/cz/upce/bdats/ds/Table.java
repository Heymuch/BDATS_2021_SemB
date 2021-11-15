package cz.upce.bdats.ds;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Table<K extends Comparable<K>, V> implements ITable<K, V> {
    // Atributy
    private Node root;
    private int counter;

    // *** Metody rozhraní ITable ***
    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean isEmpty() {
        return Objects.isNull(root);
    }

    @Override
    public int size() {
        return counter;
    }

    @Override
    public V find(K key) throws Exception {
        checkKey(key);                      // pokud je klíč null...
        if (isEmpty()) throw Error.EMPTY;   // pokud je tabulka prázná ...

        Node node = findNode(key, root); // vyhledání uzlu podle klíče

        if (Objects.isNull(node)) throw Error.NO_KEY_VALUE; // pokud neexistuje hodnota pro daný klíč ...

        return node.value; // vrácení hodnoty uzlu
    }

    @Override
    public void add(K key, V value) throws Exception {
        checkKey(key); // pokud je klíč null ...

        Node node = new Node(key, value); // vytvoření nového uzlu

        if (this.isEmpty())
            root = node;
        else
            addNode(root, node);

        counter++;
    }

    @Override
    public V remove(K key) throws Exception {
        checkKey(key);                      // pokud je klíč null...
        if (isEmpty()) throw Error.EMPTY;   // pokud je tabulka prázdná...

        Node node = findNode(key, root);                    // vyhledání uzlu
        if (Objects.isNull(node)) throw Error.NO_KEY_VALUE; // pokud neexistuje uzel s daným klíčem

        V value = node.value;
        removeNode(node);

        counter--;

        return value;
    }

    @Override
    public Iterator<V> iterator(IterationType type) {
        switch (type) {
            case BREADTH: return (!isEmpty()) ? new BreadthIterator(root) : new BreadthIterator();
            case DEPTH: return (!isEmpty()) ? new DepthIterator(root) : new DepthIterator();
            default: return null;
        }
    }

    // Pomocné metody
    private void checkKey(K key) throws Exception {
        if (Objects.isNull(key))
            throw Error.KEY_IS_NULL;
    }

    private void addNode(Node parent, Node child) {
        int comparison = child.key.compareTo(parent.key); // hodnota porovnání

        if (comparison <= 0) { // pokud je klíč menší nebo roven
            if (hasLeftChild(parent))
                addNode(parent.left, child);
            else
                parent.left = child;
        } else { // pokud je klíč větší
            if (hasRightChild(parent))
                addNode(parent.right, child);
            else
                parent.right = child;
        }
    }

    private void removeNode(Node node) {
        if (hasLeftChild(node) || hasRightChild(node)) // pokud má uzel potomky...
            removeNodeWithChild(node);
        else
            removeLeaf(node);
    }

    private void removeNodeWithChild(Node node) {
        Node successor = (Objects.nonNull(node.right)) ? getMinimum(node.right) : getMaximum(node.left);
        removeNode(successor);
        node.value = successor.value;
    }

    private void removeLeaf(Node node) {
        if (node == root) { // pokud je uzel zároveň root uzel...
            clear();
            return;
        }

        Node parent = getParentNode(root, node);    // nalezení rodičovského uzlu

        if (parent.left == node)   // pokud je potomek levým uzlem...
            parent.left = null;
        else                        // pokud je potomek pravým uzlem...
            parent.right = null;
    }

    private Node findNode(K key, Node node) {
        if (key.equals(node.key)) return node;

        int comparison = key.compareTo(node.key);
        if (comparison <= 0 && hasLeftChild(node))
            return findNode(key, node.left);
        else if (comparison > 0 && hasRightChild(node))
            return findNode(key, node.right);

        return null;
    }

    private Node getMinimum(Node root) {
        if (hasLeftChild(root))
            return getMinimum(root.left);
        return root;
    }

    private Node getMaximum(Node root) {
        if (hasRightChild(root))
            return getMaximum(root.right);
        return root;
    }

    private Node getParentNode(final Node root, final Node child) {
        Objects.requireNonNull(root);   // pokud je root uzel null...
        Objects.requireNonNull(child);  // pokud je potomek null...

        if (child == root) return null; // pokud uzel roven kořenu

        if (root.left == child || root.right == child) return root;

        int comparison = child.key.compareTo(root.key);
        if (hasLeftChild(root) && comparison <= 0)
            return getParentNode(root.left, child);
        else if (hasRightChild(root) && comparison > 0)
            return getParentNode(root.right, child);
        else
            return null;
    }

    private boolean hasLeftChild(Node node) {
        Objects.requireNonNull(node);
        return Objects.nonNull(node.left);
    }

    private boolean hasRightChild(Node node) {
        Objects.requireNonNull(node);
        return Objects.nonNull(node.right);
    }

    private class Node {
        // Atributy
        K key;
        V value;
        Node left;
        Node right;

        // Konstruktor
        Node(K key, V value, Node left, Node right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        Node(K key, V value) {
            this(key, value, null, null);
        }
    }

    private class DepthIterator implements Iterator<V> {
        // Atributy
        private final IStack<Node> stack = new Stack<>();

        private DepthIterator() {
        }

        private DepthIterator(Node root) {
            stack.push(root);
        }

        @Override
        public boolean hasNext() {
            return stack.size() > 0;
        }

        @Override
        public V next() throws NoSuchElementException {
            if (!hasNext()) throw new NoSuchElementException();

            try {
                Node node = stack.pop();

                if (Objects.nonNull(node.right)) stack.push(node.right);
                if (Objects.nonNull(node.left)) stack.push(node.left);

                return node.value;
            } catch (Exception e) {
                throw new NoSuchElementException("Chyba při průchodu stromem!");
            }
        }
    }

    private class BreadthIterator implements Iterator<V> {
        // Atributy
        private IQueue<Node> queue = new Queue<>();

        // Konstruktor
        private BreadthIterator() {
        }

        private BreadthIterator(Node root) {
            queue.push(root);
        }

        @Override
        public boolean hasNext() {
            return queue.size() > 0;
        }

        @Override
        public V next() throws NoSuchElementException {
            if (!hasNext()) throw new NoSuchElementException();

            try {
                Node node = queue.pop();

                if (Objects.nonNull(node.left)) queue.push(node.left);
                if (Objects.nonNull(node.right)) queue.push(node.right);

                return node.value;
            } catch (Exception e) {
                throw new NoSuchElementException("Chyba při průchodu stromem!");
            }
        }
    }

    public static class Error extends Exception {
        // Konstanty
        //private static final Error NOT_IMPL = new Error("Metoda není implementována!");
        private static final Error EMPTY = new Error("Tabulka je prázdná!");
        private static final Error KEY_IS_NULL = new Error("Klíč má hodnotu null!");
        private static final Error UKNOWN_ITERATOR = new Error("Neznámý typ iterátoru!");
        private static final Error NO_KEY_VALUE = new Error("Klíč nemá přiřazenou hodnotu!");
        //private static final Error UNKNOWN_NODE = new Error("Ve stromě se daný uzel nenachází!");

        // Konstruktor
        public Error(String message, Throwable cause) {
            super(message, cause);
        }

        public Error(String message) {
            super(message);
        }
    }
}