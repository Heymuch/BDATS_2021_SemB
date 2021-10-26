package cz.upce.bdats.ds;

public class Stack<T> implements IStack<T> {
    // Atributy
    private IAbstrDoubleList<T> list = new AbstrDoubleList<>();

    // Konstruktor
    public Stack(T... data) {
        for (T d : data)
            push(d);
    }

    // Metody
    public void push(T data) {
        list.vlozPrvni(data);
    }

    // odebere a vrátí data ze zásobníku
    public T pop() throws Exception {
        try {
            return list.odeberPrvni();
        } catch (Exception e) {
            throw new Exception("Chyba při odebírání ze zásobníku!", e);
        }
    }

    // velikost zásobníku
    public int size() {
        return list.velikost();
    }
}