package db;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;


public final class DatabaseUtil {      //final pomeni da se iz ovog razreda ne moze dedovati oz. razred ni objekat
    // sve metode u razredu moraju biti public i static
    // razred mora vsebovat private konstruktor
    // ce vas bo slucajno vprasal kaksni so razredi UTIL te stvari mu povejta

    private static BasicDataSource dataSource;

    private DatabaseUtil() {
    }

    public static void testiraj(String query) {   //metoda sluzi samo za testiranje da bi radila treba biti
        //odkomentiran sakila url v config.prperties
        //ne pozabit config.properties staviti na git.ignore listo
        try {
            InputStream input = new FileInputStream("src/main/java/db/config.properties");
            if(input==null)
                System.out.println("ERROR");

            Properties prop = new Properties();
            Class.forName("com.mysql.cj.jdbc.Driver");
            prop.load(input);

            String url =prop.getProperty("db.url");
            String user = prop.getProperty("db.user");
            String pass = prop.getProperty("db.password");


            Connection con = DriverManager.getConnection(
                    url, user, pass);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
                System.out.println(rs.getString(2)); //column index je npr tukaj name ker je v
            // sakila bazi v tabeli actor v 2 "column" ime actora
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static BasicDataSource getDataSource() { // metode so podobne prvoj samo ove sluze vise za 8 nalogo
        if (dataSource == null) {
            BasicDataSource ds = new BasicDataSource();
            try {
                InputStream input = new FileInputStream("src/main/java/db/config.properties");
                Properties prop = new Properties();

                prop.load(input);
                ds.setUsername(prop.getProperty("db.user"));
                ds.setPassword(prop.getProperty("db.password"));
                ds.setUrl(prop.getProperty("db.url"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);

            dataSource = ds;
        }
        return dataSource;
    }

    public static Connection getConnection() {
        try {
            return getDataSource().getConnection();
        } catch (java.sql.SQLException i) {
            System.out.println("SQL napaka conn! -- " + i.getMessage() + "\n");
            return null;
        }
    }



}


