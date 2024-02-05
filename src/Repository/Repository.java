package Repository;

import Resources.Resource;

import java.util.List;

public interface Repository<T> {
    List<T> read();
    void add(T item);
    void remove(T item);
    int find(String resourceTitle);
    List<Resource> findResources(String username);
    boolean isResourceBorrowed(String resourceTitle);
}
