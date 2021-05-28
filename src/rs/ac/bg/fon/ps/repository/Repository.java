package rs.ac.bg.fon.ps.repository;

import java.util.List;
import rs.ac.bg.fon.ps.domain.GenericEntity;

public interface Repository<T> {

    List<GenericEntity> getAll(T param, String condition) throws Exception;
    
    GenericEntity get(T param, String condition) throws Exception;

    void add(T param) throws Exception;

    void edit(T param) throws Exception;

    void delete(T param) throws Exception;
    
    long getMaxId(T param) throws Exception;
}
