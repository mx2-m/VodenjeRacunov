package dao;

import model.Invoice;

import java.util.List;
import java.util.UUID;

public interface DaoCrud<T, O, D> {
    T getById(UUID id);

    List<T> getAll();

    boolean insert(T m);

    boolean update(T m);

    boolean delete(T m);

    O mapDataToObject(D data) throws Exception;


}
