package cz.upce.bdats.data;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import cz.upce.bdats.autopujcovna.Auto;
import cz.upce.bdats.autopujcovna.OsobniAuto;
import cz.upce.bdats.autopujcovna.UzitkoveAuto;
import cz.upce.bdats.autopujcovna.Typ;
import cz.upce.bdats.autopujcovna.Barva;
import cz.upce.bdats.autopujcovna.IPobocka;
import cz.upce.bdats.autopujcovna.Pobocka;
import cz.upce.bdats.autopujcovna.Pozice;
import cz.upce.bdats.autopujcovna.IAutopujcovna;
import cz.upce.bdats.autopujcovna.Autopujcovna;
import cz.upce.bdats.autopujcovna.IteratorTyp;

public class Persistence {
    public static class Automobily {
        public static String toCSV(Auto a) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%s;%s;%.2f;%d", a.getTyp().name(), a.getSPZ(), a.getStavKm(), a.getPocetVypujceni()));
            switch (a.getTyp()) {
                case OSOBNI:
                    OsobniAuto o = (OsobniAuto) a;
                    sb.append(String.format(";%s", o.getBarva().name()));
                    break;
                case UZITKOVY:
                    UzitkoveAuto u = (UzitkoveAuto) a;
                    sb.append(String.format(";%.2f", u.getNosnost()));
                    break;
            }
            return sb.toString();
        }

        public static Auto fromCSV(String s) {
            String[] frags = s.split(";");
            Typ typ = Typ.valueOf(frags[0]);
            String spz = frags[1];
            float stavKm = Float.valueOf(frags[2]);
            int pocetVypujceni = Integer.valueOf(frags[3]);

            switch (typ) {
                case OSOBNI: return new OsobniAuto(spz, stavKm, pocetVypujceni, Barva.valueOf(frags[4]));
                case UZITKOVY: return new UzitkoveAuto(spz, stavKm, pocetVypujceni, Float.valueOf(frags[4]));
                default: return null;
            }
        }

        public static byte[] toBin(Auto a) throws IOException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(a);
            oos.flush();
            byte[] b = baos.toByteArray();
            oos.close();
            return b;
        }

        public static Auto fromBin(byte[] b) throws IOException, ClassNotFoundException {
            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Auto a = (Auto) ois.readObject();
            ois.close();
            return a;
        }
    }

    public static class Pobocky {
        public static String[] toCSV(IPobocka p) {
            List<String> l = new ArrayList<>();
            l.add(String.format("%s;%d", p.getNazev(), p.pocetAut()));
            Iterator<Auto> i = p.iterator();
            while (i.hasNext())
                l.add(Persistence.Automobily.toCSV(i.next()));
            return l.toArray(new String[l.size()]);
        }

        public static IPobocka fromCSV(String[] csv) throws Exception {
            String[] frags = csv[0].split(";");
            String nazev = frags[0];
            int pocetAut = Integer.valueOf(frags[1]);

            IPobocka p = new Pobocka(nazev);
            for (int i = 1; i <= pocetAut; i++)
                p.vlozAuto(Persistence.Automobily.fromCSV(csv[i]));
            return p;
        }
    }

    public static class Autopujcovny {
        public static String[] toCSV(IAutopujcovna a) throws Exception {
            List<String> l = new ArrayList<>();
            l.add(String.format("%s;%d;%d", a.getNazev(), a.pocetVypujcenychAut(), a.pocetPobocek()));
            Iterator<Auto> vypujcene = a.iterator(IteratorTyp.VYPUJCENE_AUTOMOBILY);
            while (vypujcene.hasNext())
                l.add(Persistence.Automobily.toCSV(vypujcene.next()));

            Iterator<IPobocka> pobocky = a.iterator(IteratorTyp.POBOCKY);
            while (pobocky.hasNext())
                l.addAll(Arrays.asList(Persistence.Pobocky.toCSV(pobocky.next())));

            return l.toArray(new String[l.size()]);
        }

        public static IAutopujcovna fromCSV(String[] csv) throws Exception {
            String[] frags = csv[0].split(";");
            String nazev = frags[0];
            int pocetVypujcenychAut = Integer.valueOf(frags[1]);
            int pocetPobocek = Integer.valueOf(frags[2]);

            IAutopujcovna a = new Autopujcovna(nazev);

            for (int i = 1; i <= pocetVypujcenychAut; i++)
                a.vlozVypujceneAuto(Persistence.Automobily.fromCSV(csv[i]));

            csv = Arrays.copyOfRange(csv, pocetVypujcenychAut + 1, csv.length);
            for (int i = 0; i < pocetPobocek; i++) {
                IPobocka p = Persistence.Pobocky.fromCSV(csv);
                a.vlozPobocku(p, Pozice.POSLEDNI);
                csv = Arrays.copyOfRange(csv, p.pocetAut() + 1, csv.length);
            }

            return a;
        }
    }
}