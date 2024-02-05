package Resources;

public class Book extends Resource {
    private int pageCount;

    public Book(String title, String author, int pageCount) {
        super(title, author,"book");
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        return pageCount;
    }


}
