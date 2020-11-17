package dao.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import dao.ItemInternalDao;
import model.Item;
import model.ItemInternal;
import util.Util;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

public class JsonItemInternal implements ItemInternalDao<List<ItemInternal>, JsonReader> {
    private final String filename = "src/main/resources/itemInternal.json";

    @Override
    public ItemInternal getByBarcode(String barcode) {
        List<ItemInternal> items = getAll();
        for (ItemInternal item : items) {
            if (item.getEan().equals(barcode)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public List<ItemInternal> getByDepartment(String department) {
        List<ItemInternal> items = getAll();
        List<ItemInternal> tmp = getAll();
        for (ItemInternal item : items) {
            if (!item.getDepartment().equals(department)) {
                //return item;
                tmp.remove(item);
            }
        }
        //return null;
        return tmp;
    }

    @Override
    public ItemInternal getById(UUID id) {
        List<ItemInternal> items = getAll();
        for (ItemInternal item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public List<ItemInternal> getAll() {
        JsonReader reader = Util.readJsonFromFile(filename);
        return mapDataToObject(reader);
    }

    @Override
    public boolean insert(ItemInternal addedItem) {

        List<ItemInternal> items = getAll();
        for (Item item : items) {
            if (item.getId().equals(addedItem.getId()))
                return false;
        }
        items.add(addedItem);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return Util.writeJsonToFile(filename, gson.toJson(items));
    }

    @Override
    public boolean update(ItemInternal updatedItem) {
        List<ItemInternal> items = getAll();
        for (ItemInternal item : items) {
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
    public boolean delete(ItemInternal deletedItem) {
        List<ItemInternal> items = getAll();
        for (ItemInternal item : items) {
            if (item.getId().equals(deletedItem.getId())) {
                items.remove(item);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return Util.writeJsonToFile(filename, gson.toJson(items));
        }
        return false;
    }

    @Override
    public List<ItemInternal> mapDataToObject(JsonReader data) {
        Type itemsListType = new TypeToken<List<ItemInternal>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(data, itemsListType);
    }
}