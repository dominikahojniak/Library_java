package Repository;

import Resources.Resource;

public interface ResourceDetailsRepository<T extends Resource> {
    void addDetails(T resource, int resourceId);
}
