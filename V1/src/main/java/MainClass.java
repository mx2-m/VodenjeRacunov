import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import dao.json.JsonInvoice;
import dao.json.JsonItemInternal;
import dao.mysql.MySqlItem;
import db.DatabaseUtil;
import model.*;
import util.Util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//import db.DatabaseUtil;

public class MainClass {
    public static void main(String[] args) throws SQLException, FileNotFoundException {

        String EAN = "45056123451";
        String EAN1 = "13456123451";
        String EAN3 = "33456123451";

        LocalDateTime created = LocalDateTime.now();
        BigDecimal price = new BigDecimal(6.2);
        BigDecimal quantity = new BigDecimal(1);
        BigDecimal discount = new BigDecimal(10.5);
        BigDecimal discount1 = new BigDecimal(38.4);
        Map<String, Item> itemsTemp = new LinkedHashMap<>();
        Map<String, Item> items = new LinkedHashMap<>();

       /* System.out.println("Check ean: " + Item.checkDigit(EAN));
        System.out.println(Item.eanCountry(EAN));

        Items a=new Items(itemsTemp);
        Item item1 = new Item(1, "jabuka", EAN, price, Item.eanCountry(EAN), new BigDecimal(1), 0.05);
        Item item2 = new Item(1, "mandarina", EAN, price, Item.eanCountry(EAN), new BigDecimal(1), 0.05);


        Invoice invoice = new Invoice(21234, 1, new BigDecimal(10),a);
        invoice.addItem(item1);
        /*invoice.addItem(new Item());
        invoice.addItem(new Item());
        invoice.addItem(new Item());
        invoice.addItem(new Item());
        invoice.addItem(new Item());
        invoice.addItem(new Item());
        invoice.addItem(new Item());
        invoice.addItem(new Item());
        invoice.addItem(new Item());
        invoice.addItem(new Item());
        invoice.addItem(new Item());
        invoice.addItem(new Item());
        invoice.addItem(new Item());
        invoice.addItem(new Item());
        invoice.addItem(new Item());
        invoice.addItem(new Item());
        invoice.addItem(new Item());*/


        // System.out.println(items1.getInformationAbout());
        // System.out.println(invoice.print());

        // Company c=new Company("Lidl Slovenija d.o.o. k.d",746243764,"14535136","null","Pod lipami 1, 1218 Komenda");
        Item a = new Item("Cola", EAN, price, quantity, 0.10);
        Item d = new Item("Vafli", EAN1, price, quantity, 0.10);
        Item e = new Item("Mandarine", EAN3, price, quantity, 0.10);
        Item f = new Item("Cokolada", "23456123451", price, quantity, 0.10);
        ItemInternal p = new ItemInternal(6, "banane", "2116789002000", price, null, 0.10);
        Company l = new Company("Lidl Slovenija d.o.o. k.d", 746243764, "14535136", "Pod lipami 1, 1218 Komenda");

        Items b = new Items(itemsTemp);
        Items g = new Items(items);
        Invoice racun = new Invoice(discount, price, l, null, " 24", b);
        racun.addItem(a);
        racun.addItem(d);
        racun.addItem(e);
        racun.addItem(f);

        racun.print();

        Invoice racun1 = new Invoice(discount, price, l, null, " 14", g);

        racun1.addItem(e);
        racun1.addItem(f);
        racun1.addItem(a);

        racun1.print();

        Company c = new Company("HENKEL MARIBOR d.o.o.", 58665765, "58665765", "Industrijska ulica 23, Maribor, 2000 Maribor");
        Invoice racun2 = new Invoice(discount1, price, l, c, " 24", b);
        racun2.addItem(a);
        racun2.addItem(d);
        racun2.addItem(e);
        racun2.addItem(f);
        racun2.addItem(p);

        racun2.print();

       // Gson gson = new Gson();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json1 = gson.toJson(a);
        String json2 = gson.toJson(racun); //3
        String json3 = gson.toJson(p);

       // Util.writeJsonToEmptyFile("src/main/resources/itemInternal.json", json3);
        Util.writeJsonToEmptyFile("src/main/resources/invoice.json", json2);

        //Util.readJsonFromFile("src/main/resources/invoice.json");

        JsonElement json = gson.fromJson(new FileReader("src/main/resources/invoice.json"), JsonElement.class);
        String result2 = gson.toJson(json);



        JsonInvoice invoice=new JsonInvoice();
        //invoice.insert(racun);
        //invoice.insert(racun1);
        racun.removeItem(a);
        //invoice.delete(racun2);
        invoice.update(racun);

        List<ItemInternal> wtf = new JsonItemInternal().getByDepartment("Sadje");
        for( ItemInternal itam : wtf)
            System.out.println("oddelak "+itam.getDepartment()+" " + itam.getName());



        DatabaseUtil.testiraj("Select * from actor");


        MySqlItem ii= new MySqlItem();
        ii.insert(a);
        ii.delete(a);
        ii.update(a);// za ovo ne vem ce dela ni testirano!!!

    }
}
