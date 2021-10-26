package cz.upce.bdats.ds;

public interface IQueue<T> {
    // vloží data do fronty
    void push(T data);

    // odebere a vrátí data z fronty
    T pop() throws Exception;

    // velikost fronty
    int size();
}