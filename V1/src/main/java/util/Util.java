/**
 * Razred skrbi za pisanje in branje JSON datoteke.
 * Pisanje opravljamo uz pomoc razreda FileWriter, branje uz pomoc razreda File i JsonReader
 */


package util;

import com.google.gson.stream.JsonReader;

import java.io.*;


public class Util {


    private Util() {
    }

    public static boolean writeJsonToFile(String filename, String json) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(json);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean writeJsonToEmptyFile(String filename, String json) {
        //metodu imamo zbog napaki ki se pojavla da json fajl ni arraj nego objekt
        // in zaradi tega niso delale metode kot insert
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("[");
            writer.write(json);
            writer.write("]");
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static JsonReader readJsonFromFile(String filename) {

        try {
            if (new File(filename).exists()) {

                return new JsonReader(new FileReader(filename));
            } else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}