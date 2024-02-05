package Resources;

import java.time.LocalDate;

public class Resource {
    private int id;
    private String title;
    private String author;
    private String type;
    private LocalDate startDate;
    private String username;

    public Resource(String title, String author, String type) {
        this.title = title;
        this.author = author;
        this.type = type;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

}