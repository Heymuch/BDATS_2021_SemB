package cz.upce.bdats.ds;

import java.util.Iterator;

public interface IAbstrDoubleList<T> extends Iterable {
    // M001 zrušení celého seznamu
    void zrus();
    // M002 test naplněnosti seznamu
    boolean jePrazdny();
    // M003 počet prvků v seznamu
    int velikost();

    // M101 vložení prvku do seznamu na první místo
    void vlozPrvni(T data);
    // M102 vložení prvku do seznamu na poslední místo
    void vlozPosledni(T data);
    // M103 vložení prvku do seznamu jakožto následníka aktuálního prvku
    void vlozNaslednika(T data) throws Exception;
    // M104 vložení prvku do seznamu jakožto předchůdce aktuálního prvku
    void vlozPredchudce(T data) throws Exception;

    // Operace zpřístupnění mění pozici aktuálního prvku
    // M201 zpřístupnení aktuálního prvku seznamu
    T zpristupniAktualni() throws Exception;
    // M202 zpristupneni prvního prvku seznamu
    T zpristupniPrvni() throws Exception;
    // M203 zpřístupnění posledního prvku prvku seznamu
    T zpristupniPosledni() throws Exception;
    // M204 zpřístupnění následníha aktuálního prvku
    T zpristupniNaslednika() throws Exception;
    // M205 zpřístupnění předchůdce aktuálního prvku
    T zpristupniPredchudce() throws Exception;

    // M301 odebrání aktuálního prvku ze seznamu, aktuální prvek je nastaven na první prvek seznamu
    T odeberAktualni() throws Exception;
    // M302 odebrání prvního prvku ze seznamu
    T odeberPrvni() throws Exception;
    // M303 odebrání posledního prvku ze seznamu
    T odeberPosledni() throws Exception;
    // M304 odebrání následníka aktuálního prvku ze seznamu
    T odeberNaslednika() throws Exception;
    // M305 odebrani předchůdce aktuálního prvku ze seznamu
    T odeberPredchudce() throws Exception;

    // M401 vytvoření iterátoru
    Iterator<T> iterator();
}