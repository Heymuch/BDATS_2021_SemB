package cz.upce.bdats.ds;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Table<K extends Comparable<K>, V> implements ITable<K, V> {
    // Atributy
    private Node root;

    // Metody
    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean isEmpty() {
        return Objects.isNull(root);
    }

    @Override
    public V find(K key) throws Exception {
        if (Objects.isNull(key)) throw Error.KEY_IS_NULL;
        if (isEmpty()) return null;

        Node node = findNode(key, root);
        if (Objects.isNull(node)) throw Error.NO_KEY_VALUE;
        return node.value;
    }

    @Override
    public void add(K key, V value) throws Exception {
        // Kontrola parametru
        if (Objects.isNull(key)) throw Error.KEY_IS_NULL;

        Node node = new Node(key, value);

        if (Objects.isNull(root))
            root = node;
        else
            addNode(root, node);
    }

    @Override
    public V remove(K key) throws Exception {
        throw Error.NOT_IMPL;
    }

    @Override
    public Iterator<V> iterator(IterationType type) throws Exception {
        switch (type) {
            case BREADTH: return new BreadthIterator(root);
            case DEPTH: return new DepthIterator(root);
            default: throw Error.UKNOWN_ITERATOR;
        }
    }

    // Pomocné metody
    private void addNode(Node parent, Node child) {
        int comparison = child.key.compareTo(parent.key); // hodnota porovnání

        if (comparison <= 0) { // pokud je klíč menší nebo roven
            if (Objects.nonNull(parent.left))
                addNode(parent.left, child);
            else
                parent.left = child;
        } else { // pokud je klíč větší
            if (Objects.nonNull(parent.right))
                addNode(parent.right, child);
            else
                parent.right = child;
        }
    }

    private Node findNode(K key, Node node) {
        if (key.equals(node.key)) return node;

        int comparison = key.compareTo(node.key);
        if (comparison <= 0 && Objects.nonNull(node.left))
            return findNode(key, node.left);
        else if (comparison > 0 && Objects.nonNull(node.right))
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
        Objects.requireNonNull(root);
        Objects.requireNonNull(child);

        if (child == root) return null;

        if (root.left == child || root.right == child) return root;

        int comparison = child.key.compareTo(root.key);
        if (hasLeftChild(root) && comparison <= 0)
            return getParentNode(root.left, child);
        else if (hasRightChild(root) && comparison > 0)
            return getParentNode(root.right, child);
        else

    }

    private boolean hasChildren(Node node) {
        return (hasLeftChild(node) || hasRightChild(node));
    }

    private boolean hasLeftChild(Node node) {
        Objects.requireNonNull(node);
        return Objects.nonNull(node.left)
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
        IStack<Node> stack = new Stack<>();

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
        private static final Error NOT_IMPL = new Error("Metoda není implementována!");
        private static final Error KEY_IS_NULL = new Error("Klíč má hodnotu null!");
        private static final Error UKNOWN_ITERATOR = new Error("Neznámý typ iterátoru!");
        private static final Error NO_KEY_VALUE = new Error("Klíč nemá přiřazenou hodnotu!");
        private static final Error UNKNOWN_NODE = new Error("Ve stromě se daný uzel nenachází!");

        // Konstruktor
        public Error(String message, Throwable cause) {
            super(message, cause);
        }

        public Error(String message) {
            super(message);
        }
    }
}