package cz.upce.bdats.autopujcovna;

import java.util.Iterator;

public interface IPobocka extends Iterable<Auto> {
    String getNazev();

    // M101 vloží nové auto do seznamu na příslušnou pozici (první, poslední, předchůdce, následník)
    void vlozAuto(Auto auto) throws Exception;

    // zpřístupní auto z požadované pozice (první, poslední, předchůdce, následník, aktuální)
    Auto zpristupniAuto(String spz) throws Exception;

    // odebere auto z požadované pozice (první, poslední, předchůdce, následník, aktuální)
    Auto odeberAuto(String spz) throws Exception;

    // vytvoří iterátor
    Iterator<Auto> iterator();

    // zruší všechna auta
    void zrus();

    // počet aut v pobočce
    int pocetAut();
}