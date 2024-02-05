package Repository;

import java.time.LocalDate;

public class Borrow {
    private int id;
    private String username;
    private int userId;
    private String resourceTitle;
    private String resourceAuthor;
    private int resourceId;
    private LocalDate startDate;
    private LocalDate returnDate;

    public Borrow( int userId, int resourceId, LocalDate startDate) {
        this.userId = userId;
        this.resourceId = resourceId;
        this.startDate = startDate;
        this.returnDate = null;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    public int getUserId() {
        return userId;
    }

    public String getResourceTitle() {
        return resourceTitle;
    }

    public int getResourceId() {
        return resourceId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setResourceTitle(String resourceTitle) {
        this.resourceTitle = resourceTitle;
    }

    public String getResourceAuthor() {
        return resourceAuthor;
    }

    public void setResourceAuthor(String resourceAuthor) {
        this.resourceAuthor = resourceAuthor;
    }
}