package personal;

import java.util.ArrayList;

public interface IPersonal<ID extends Number, T extends Personal> {
    ArrayList<T> getAll() throws Exception;
    void insert(T obj);
    void remove(ID id);
    void update(T obj);
    T getById(ID id);
    Integer count();
}
