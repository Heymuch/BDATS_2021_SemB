package cz.upce.bdats.autopujcovna;

import java.util.Iterator;

public interface IAutopujcovna {
    // M101 vloží novou pobočku do seznamu na příslušnou pozici (první, poslední, předchůdce, následník)
    void vlozPobocku(IPobocka pobocka, Pozice pozice) throws Exception;

    // zpřístupní pobočku z požadované pozice (první, poslední, předchůdce, následník, aktuální)
    IPobocka zpristupniPobocku(Pozice pozice) throws Exception;

    // odebere pobočku z požadované pozice (první, poslední, předchůdce, následník, aktuální)
    IPobocka odeberPobocku(Pozice pozice) throws Exception;

    // vloží nové auto do seznamu aktuální pobocky na příslušnou pozici
    void vlozAuto(Auto auto) throws Exception;

    // zpřístupní auto z požadované pozice aktuální pobočky (první, poslední, předchůdce, následník, aktuální)
    Auto zpristupniAuto(String spz) throws Exception;

    // odebere auto z požadované pozice (první, poslední, předchůdce, následník, aktuální)
    Auto odeberAuto(String spz) throws Exception;

    // odebere auto z požadované pozice aktuální pobočky a vloží ho do seznamu výpůjček (první, poslední, následník, předchůdce, aktuální)
    Auto vypujcAuto(String spz) throws Exception;

    // odebere auto z požadované pozice výpůjček a vloží ho do seznamu aktuální pobočky (první, poslední, předchůdce, následník, aktuální)
    Auto vratAuto(Pozice pozice) throws Exception;

    // zpřístupní auto z požadované pozice ze seznamu vypůjčených aut (první, poslední, předchůdce, následník, aktuální)
    Auto zpristupniVypujceneAuto(Pozice pozice) throws Exception;

    // vrací požadovaný iterátor poboček/automobilů/vypůjčených automobilů
    Iterator iterator(IteratorTyp typ) throws Exception;

    // zruší všechna auta v aktuální pobočce
    void zrusPobocku() throws Exception;

    // zruší všechny pobočky
    void zrus();

    // počet poboček
    int pocetPobocek();

    // počet vypůčených aut
    int pocetVypujcenychAut();

    String getNazev();

    void vlozVypujceneAuto(Auto auto) throws Exception;
}