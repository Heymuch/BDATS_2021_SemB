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
        throw Error.NOT_IMPL;
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

        // Konstruktor
        public Error(String message, Throwable cause) {
            super(message, cause);
        }

        public Error(String message) {
            super(message);
        }
    }
}