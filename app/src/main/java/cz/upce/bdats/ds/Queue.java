package cz.upce.bdats.ds;

public class Queue<T> implements IQueue<T> {
    // Atributy
    IAbstrDoubleList<T> list = new AbstrDoubleList<>();

    // Konstruktor
    public Queue(T... data) {
        for (T d : data)
            push(d);
    }

    // Metody
    @Override
    public int size() {
        return list.velikost();
    }

    @Override
    public void push(T data) {
        list.vlozPosledni(data);
    }

    @Override
    public T pop() throws Exception {
        try {
            return list.odeberPrvni();
        } catch (Exception e) {
            throw new Exception("Chyba při odebírání z fronty!", e);
        }
    }
}