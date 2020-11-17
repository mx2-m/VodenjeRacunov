package dao;

import model.Item;

public interface ItemDao<O, D> extends DaoCrud<Item, O, D> {
    Item getByName(String name);

    Item getByBarcode(String barcode);
}
