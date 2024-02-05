package Factory;

import Resources.Book;
import Resources.Resource;

public class BookFactory implements Factory {
    private int pageCount;

    public BookFactory(int pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public Resource createResource(String title, String author, Object additionalParameter) {
        return new Book(title, author, (Integer) additionalParameter);
    }
}
