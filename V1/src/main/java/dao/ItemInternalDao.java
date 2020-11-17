package dao;

import model.Item;
import model.ItemInternal;

import java.util.List;

public interface ItemInternalDao<O, D> extends DaoCrud<ItemInternal, O, D> {

    Item getByBarcode(String barcode);
    List<ItemInternal> getByDepartment(String department);

}
