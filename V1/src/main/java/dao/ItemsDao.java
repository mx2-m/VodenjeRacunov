package dao;

import model.Items;

//ovo nece trebati
public interface ItemsDao<O, D> extends DaoCrud<Items, O, D> {
    Items getByName(String name);//???????????????
}
