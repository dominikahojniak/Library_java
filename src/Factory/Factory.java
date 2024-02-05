package Factory;

import Resources.Resource;

public interface Factory {
    Resource createResource(String title, String author,Object additionalParameter);
}
