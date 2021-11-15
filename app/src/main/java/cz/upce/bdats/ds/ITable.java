package cz.upce.bdats.ds;

import java.util.Iterator;

public interface ITable<K extends Comparable<K>, V> {
    /**
     * Zrušení celé tabulky
     */
    void clear();

    // test prázdnosti tabulky
    boolean isEmpty();

    // velikost tabulky
    int size();

    // vyhledá prvek dle klíče
    V find(K key) throws Exception;

    // vloží prvek do tabulky
    void add(K key, V value) throws Exception;

    // odebere prvek z tabulky
    V remove(K key) throws Exception;

    // vytvoří terátor, který umožní procházení stromu do šířky/hloubky
    Iterator<V> iterator(IterationType type);
}