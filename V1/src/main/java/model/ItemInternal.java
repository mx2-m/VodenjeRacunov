/**
 * Razred skrbi za interno crtno kodo za svezo hrano kot so npr. sadje, zelenjava i sl.
 * Znotraj crtne kode je definisan: oddelek(prva tri stevila), ID izdelka(od 4 do 7 stevila kode),
 * tezina izdelka ter check digit(zadnje stevilo).
 * Metoda print() omogucava izpis teze izdelka na racun.
 */


package model;

import java.math.BigDecimal;
import java.util.UUID;
// barkod treba generirati

public class ItemInternal extends Item {
    private UUID id;
    private String department;

    public ItemInternal(String name, String ean13, BigDecimal price, BigDecimal quantity, double davek) {
        super(name, ean13, price, quantity, davek);
        this.id = UUID.randomUUID();
        this.department = setWeighable();
    }

    private static boolean isBarcodeValid(String ean) {
        if (ean.length() != 13)
            return false;
        String[] d = ean.split("");
        short[] digits = new short[14];
        for (short i = 0; i < 13; i++) {
            digits[i] = Short.parseShort(d[i]);
            if (i % 2 == 1 && i < 12)
                digits[i] *= 3;
        }
        int sum = 0;
        for (int i = 0; i < 12; i++)
            sum = sum + digits[i];
        short lastDigit = (short) ((10 - (sum % 10)) % 10);
        if (digits[12] == lastDigit)
            return true;
        return false;
    }


    @Override
    public UUID getId() {
        return id;
    }

    public String getDepartment() {
        return department;
    }

    private String setWeighable() {
        int kodaOddelka = 0;
        String[] e = getEan().split("");


        for (int i = 0; i < 3; i++) {
            kodaOddelka *= 10;
            kodaOddelka += Integer.parseInt(e[i]);
        }

        int kodaIzdelka = 0;
        for (int i = 3; i < 7; i++) {
            kodaIzdelka *= 10;
            kodaIzdelka += Integer.parseInt(e[i]);
            System.out.println(kodaIzdelka);
        }

        if (kodaOddelka == 211) {
            this.department = "Sadje";
            if (kodaIzdelka == 4444) {
                this.name = "Jabolka";
            } else if (kodaIzdelka == 6789)
                this.name = "Banane";
            else if (kodaIzdelka == 1212)
                this.name = "Jagode";
            else if (kodaIzdelka == 1024)
                this.name = "Hruske";

        } else if (kodaOddelka == 241) {
            this.department = "Zelenjava";
            if (kodaIzdelka == 4444)
                this.name = "Paprika";
            else if (kodaIzdelka == 6666)
                this.name = "Cebula";
            else if (kodaIzdelka == 1024)
                this.name = "Paradiznik";

        } else if (kodaOddelka == 241) {
            this.department = "Meso";
            if (kodaIzdelka == 6666)
                this.name = "Piscancja prsa";
            else if (kodaIzdelka == 1212)
                this.name = "Kranjske Kosak";
            else if (kodaIzdelka == 1024)
                this.name = "Goveji hrbet";

        } else if (kodaOddelka == 241) {
            this.department = "Pekarski izdelki";
            if (kodaIzdelka == 4444)
                this.name = "Beli kruh";
            else if (kodaIzdelka == 6666)
                this.name = "Bageta";
            else if (kodaIzdelka == 1212)
                this.name = "Crni kruh";
            else if (kodaIzdelka == 1024)
                this.name = "Polcrni kruh";

        }
        return department;
    }


    public double getWeight() {

        String[] e = getEan().split("");
        double kodaTeze = 0;
        for (int i = 7; i < 12; i++) {
            kodaTeze *= 10;
            kodaTeze += Integer.parseInt(e[i]);
        }
        return kodaTeze / 1000;
    }


    public void print() {
        System.out.println(this.getName() + "\t" + this.getWeight() + " kg" + "\t" + this.getPrice());
    }


}
