package cz.upce.bdats.ds;

public interface IStack<T> {
    // vloží data do zásobníku
    void push(T data);

    // odebere a vrátí data ze zásobníku
    T pop() throws Exception;

    // velikost zásobníku
    int size();
}