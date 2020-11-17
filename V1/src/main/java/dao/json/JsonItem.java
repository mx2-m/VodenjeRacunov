package dao.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import dao.ItemDao;
import model.Invoice;
import model.Item;
import util.Util;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

public class JsonItem implements ItemDao<List<Item>, JsonReader> {
    private final String filename = "src/main/resources/item.json";

    @Override
    public Item getByName(String name) {
        List<Item> items = getAll();
        for (Item item : items) {
            if (item.getEan().equals(name)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public Item getByBarcode(String barcode) {
        List<Item> items = getAll();
        for (Item item : items) {
            if (item.getEan().equals(barcode)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public Item getById(UUID id) {
        List<Item> items = getAll();
        for (Item item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public List<Item> getAll() {
        JsonReader reader = Util.readJsonFromFile(filename);
        return mapDataToObject(reader);
    }

    @Override
    public boolean insert(Item addedItem) {
        List<Item> items = getAll();
        for (Item item : items) {
            if (item.getId().equals(addedItem.getId()))
                return false;
        }
        items.add(addedItem);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return Util.writeJsonToFile(filename, gson.toJson(items));
    }

    @Override
    public boolean update(Item updatedItem) {
        List<Item> items = getAll();
        for (Item item : items) {
            if (item.getId().equals(updatedItem.getId())) {
                items.remove(item);
                items.add(updatedItem);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return Util.writeJsonToFile(filename, gson.toJson(items));
        }
        return false;
    }

    @Override
    public boolean delete(Item deletedItem) {
        List<Item> items = getAll();
        for (Item item : items) {
            if (item.getId().equals(deletedItem.getId())) {
                items.remove(item);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return Util.writeJsonToFile(filename, gson.toJson(items));
        }
        return false;
    }

    @Override
    public List<Item> mapDataToObject(JsonReader data) {
        Type itemsListType = new TypeToken<List<Item>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(data, itemsListType);
    }
}