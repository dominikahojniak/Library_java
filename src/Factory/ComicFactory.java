package Factory;

import Resources.Comic;
import Resources.Resource;

public class ComicFactory implements Factory {
    private String illustrator;

    public ComicFactory(String illustrator) {
        this.illustrator = illustrator;
    }
    @Override
    public Resource createResource(String title, String author, Object additionalParameter) {
        return new Comic(title, author, (String) additionalParameter);
    }
}
