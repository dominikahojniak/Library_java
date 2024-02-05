package Resources;

public class Comic extends Resource {
    private String illustrator;

    public Comic(String title, String author, String illustrator) {
        super(title, author,"comic");
        this.illustrator = illustrator;
    }

    public String getIllustrator() {
        return illustrator;
    }
}