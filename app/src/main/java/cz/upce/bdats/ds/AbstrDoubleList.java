package cz.upce.bdats.ds;

import java.util.Objects;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class AbstrDoubleList<T> implements IAbstrDoubleList<T> {
    // Vnitřní třídy
    private class Prvek {
        // Atributy
        T data;
        Prvek predchozi;
        Prvek nasledujici;

        // Konstruktor
        Prvek(T data, Prvek predchozi, Prvek nasledujici) {
            this.data = data;
            this.predchozi = predchozi;
            this.nasledujici = nasledujici;
        }
    }

    private class ListIterator implements Iterator<T> {
        // Atributy
        private Prvek aktualni;

        // Konstruktor
        private ListIterator(Prvek prvni) {
            this.aktualni = prvni;
        }

        // Metody
        @Override
        public boolean hasNext() {
            return (Objects.nonNull(aktualni));
        }

        @Override
        public T next() throws NoSuchElementException {
            if (!hasNext()) throw new NoSuchElementException();
            T data = aktualni.data;
            aktualni = aktualni.nasledujici;
            return data;
        }
    }

    public static class ListException extends Exception {
        // Konstanty
        private static final ListException NENI_AKTUALNI = new ListException("Není nastaven aktuální prvek!");
        // private static final ListException NEIMPL = new ListException("Není implementováno!");
        private static final ListException PRAZDNY = new ListException("Seznam je prázdný!");
        private static final ListException KONEC = new ListException("Konec seznamu!");

        // Konstruktor
        private ListException(String zprava) {
            super(zprava);
        }
    }

    // Atributy
    Prvek aktualni;
    Prvek prvni;
    Prvek posledni;
    int pocet;

    // Konstruktory
    public AbstrDoubleList() {
    }

    public AbstrDoubleList(T... data) {
        for (T d : data)
            vlozPosledni(d);
    }

    // Metody
    @Override // M001
    public void zrus() {
        aktualni = null;
        prvni = null;
        posledni = null;
    }

    @Override // M002
    public boolean jePrazdny() {
        return Objects.isNull(prvni);
    }

    @Override // M003
    public int velikost() {
        return pocet;
    }

    @Override // M101
    public void vlozPrvni(T data) {
        Prvek p = new Prvek(data, null, null);

        if (jePrazdny()) {
            posledni = p;
        } else {
            p.nasledujici = prvni;
            prvni.predchozi = p;
        }
        prvni = p;
        pocet++;
    }

    @Override // M102
    public void vlozPosledni(T data) {
        Prvek p = new Prvek(data, null, null);

        if (jePrazdny()) {
            prvni = p;
        } else {
            p.predchozi = posledni;
            posledni.nasledujici = p;
        }
        posledni = p;
        pocet++;
    }

    @Override // M103
    public void vlozNaslednika(T data) throws ListException {
        if (Objects.isNull(aktualni)) throw ListException.NENI_AKTUALNI; // pokud není nastaven aktuální prvek

        if (aktualni == posledni) { // pokud je aktuální poslední
            vlozPosledni(data);
            return;
        }

        Prvek p = new Prvek(data, aktualni, aktualni.nasledujici);
        aktualni.nasledujici.predchozi = p;
        aktualni.nasledujici = p;
        pocet++;
    }

    @Override // M104
    public void vlozPredchudce(T data) throws ListException {
        if (Objects.isNull(aktualni)) throw ListException.NENI_AKTUALNI;

        if (aktualni == prvni) {
            vlozPrvni(data);
            return;
        }

        Prvek p = new Prvek(data, aktualni.predchozi, aktualni);
        aktualni.predchozi.nasledujici = p;
        aktualni.predchozi = p;
        pocet++;
    }

    @Override // M201
    public T zpristupniAktualni() throws ListException {
        if (Objects.isNull(aktualni)) throw ListException.NENI_AKTUALNI; // pokud není nastaven aktuální

        return aktualni.data;
    }

    @Override // M202
    public T zpristupniPrvni() throws ListException {
        if (jePrazdny()) throw ListException.PRAZDNY;
        aktualni = prvni;
        return aktualni.data;
    }

    @Override // M203
    public T zpristupniPosledni() throws ListException {
        if (jePrazdny()) throw ListException.PRAZDNY;
        aktualni = posledni;
        return aktualni.data;
    }

    @Override // M204
    public T zpristupniNaslednika() throws ListException {
        if (jePrazdny()) throw ListException.PRAZDNY;
        if (aktualni == posledni) throw ListException.KONEC;

        aktualni = aktualni.nasledujici;
        return aktualni.data;
    }

    @Override // M205
    public T zpristupniPredchudce() throws ListException {
        if (jePrazdny()) throw ListException.PRAZDNY;
        if (aktualni == prvni) throw ListException.KONEC;

        aktualni = aktualni.predchozi;
        return aktualni.data;
    }

    @Override // M301
    public T odeberAktualni() throws ListException {
        if (Objects.isNull(aktualni)) throw ListException.NENI_AKTUALNI;

        T data = aktualni.data;

        if (aktualni == prvni) {
            odeberPrvni();
        } else if (aktualni == posledni) {
            odeberPosledni();
        } else {
            aktualni.predchozi.nasledujici = aktualni.nasledujici;
            aktualni.nasledujici.predchozi = aktualni.predchozi;
        }

        aktualni = null;
        pocet--;
        return data;
    }

    @Override // M302
    public T odeberPrvni() throws ListException {
        if (jePrazdny()) throw ListException.PRAZDNY;

        T data = prvni.data;

        if (prvni == posledni) {
            zrus();
        } else {
            if (prvni == aktualni) aktualni = null;
            prvni = prvni.nasledujici;
            prvni.predchozi = null;
        }

        pocet--;
        return data;
    }

    @Override // M303
    public T odeberPosledni() throws ListException {
        if (jePrazdny()) throw ListException.PRAZDNY;

        T data = posledni.data;

        if (posledni == prvni) {
            zrus();
        } else {
            if (posledni == aktualni) aktualni = null;
            posledni = posledni.predchozi;
            posledni.nasledujici = null;
        }

        pocet--;
        return data;
    }

    @Override // M304
    public T odeberNaslednika() throws ListException {
        if (Objects.isNull(aktualni)) throw ListException.NENI_AKTUALNI;
        if (aktualni == posledni) throw ListException.KONEC;

        if (aktualni.nasledujici == posledni) return odeberPosledni();
        T data = aktualni.nasledujici.data;
        aktualni.nasledujici = aktualni.nasledujici.nasledujici;
        aktualni.nasledujici.predchozi = aktualni;

        pocet--;
        return data;
    }

    @Override // M305
    public T odeberPredchudce() throws ListException {
        if (Objects.isNull(aktualni)) throw ListException.NENI_AKTUALNI;
        if (aktualni == prvni) throw ListException.KONEC;

        if (aktualni.predchozi == prvni) return odeberPrvni();
        T data = aktualni.predchozi.data;
        aktualni.predchozi = aktualni.predchozi.predchozi;
        aktualni.predchozi.nasledujici = aktualni;

        pocet--;
        return data;
    }

    @Override // M401
    public Iterator<T> iterator() {
        return new ListIterator(prvni);
    }
}