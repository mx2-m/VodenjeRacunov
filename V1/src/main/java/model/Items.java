package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;


public class Items implements Searchable {
    private LocalDateTime created;
    private LocalDateTime modified;
    private Map<String, Item> Lista = new LinkedHashMap<String, Item>();


    public Items(Map<String, Item> itemsTemp) {
        this.Lista = itemsTemp;
        modified = LocalDateTime.now();
        created = LocalDateTime.now();

    }

    public Map<String, Item> getItemsTemp() {
        return Lista;
    }

    public void setItemsTemp(Map<String, Item> itemsTemp) {
        this.Lista = itemsTemp;
        modified = LocalDateTime.now();
    }


    public void add(Item item) {

        if (Lista.containsKey(item.getEan())) {
            item.setQuantity(item.getQuantity().add(new BigDecimal("1")));
        } else
            item.setQuantity(new BigDecimal("1"));

        Lista.put(item.getEan(), item);
    }


    public void remove(Item item) {

        if (Lista.containsKey(item.getEan())) {
            item.setQuantity(item.getQuantity().subtract(new BigDecimal("1")));
        }
        Lista.remove(item.getEan());
    }


    public void print() {
        String b = null;
        for (Map.Entry<String, Item> entry : Lista.entrySet()) {
            Item a = entry.getValue();
            a.print();
        }
    }

    public boolean search(String niz) {
        Boolean search = false;
        for (Map.Entry<String, Item> entry : Lista.entrySet()) {
            Item a = entry.getValue();
            if (a.search(niz) == true) search = true;
        }
        return search;
    }
}