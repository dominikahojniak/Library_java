package Repository;

import Database.Database;
import Resources.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class ResourceRepository implements Repository<Resource> {
    private static final String INSERT_RESOURCE_QUERY = "INSERT INTO resources (title, author, type) VALUES (?, ?, ?)";
    private static final String DELETE_RESOURCE_QUERY = "DELETE FROM resources WHERE title = ? AND author = ?";
        @Override
        public void add(Resource resource) {
            try (Connection connection = Database.getInstance().getConnection()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RESOURCE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setString(1, resource.getTitle());
                    preparedStatement.setString(2, resource.getAuthor());
                    preparedStatement.setString(3, resource.getType());
                    preparedStatement.executeUpdate();

                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int resourceId = generatedKeys.getInt(1);
                        resource.setId(resourceId);
                        if (resource instanceof Book) {
                            new BookRepository().addDetails((Book) resource, resourceId);
                        } else if (resource instanceof Comic) {
                            new ComicRepository().addDetails((Comic) resource, resourceId);
                        } else if (resource instanceof Magazine) {
                            new MagazineRepository().addDetails((Magazine) resource, resourceId);
                        } else if (resource instanceof Audiobook) {
                            new AudiobookRepository().addDetails((Audiobook) resource, resourceId);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Error adding resources to database.");
            }
        }
    public void remove(Resource resource) {
        try (Connection connection = Database.getInstance().getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESOURCE_QUERY)) {
                preparedStatement.setString(1, resource.getTitle());
                preparedStatement.setString(2, resource.getAuthor());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error deleting resources from database.");
        }
    }
    public int find(String resourceTitle) {
        try (Connection connection = Database.getInstance().getConnection()) {
            String query = "SELECT id FROM resources WHERE title = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, resourceTitle);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    @Override
    public List<Resource> findResources(String username) {
        List<Resource> resources = new ArrayList<>();

        try (Connection connection = Database.getInstance().getConnection()) {
            String query = "SELECT r.*, b.start_date, b.return_date, u.name as username " +
                    "FROM borrows b " +
                    "JOIN resources r ON b.resource_id = r.id " +
                    "JOIN users u ON b.user_id = u.id " +
                    "WHERE u.name = ? AND b.return_date IS NULL";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String title = resultSet.getString("title");
                        String author = resultSet.getString("author");
                        String type = resultSet.getString("type");
                        LocalDate startDate = resultSet.getObject("start_date", LocalDate.class);
                        Resource resource = new Resource(title, author, type);
                        resource.setStartDate(startDate);
                        resource.setUsername(resultSet.getString("username"));
                        resources.add(resource);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error finding borrowed resources for user in the database.");
        }

        return resources;
    }

    @Override
    public boolean isResourceBorrowed(String resourceTitle) {
        return false;
    }
    public List<Resource> read() {
        List<Resource> resources = new ArrayList<>();
        try (Connection connection = Database.getInstance().getConnection()) {
            String query = "SELECT r.id, r.title, r.author, r.type, " +
                    "b.page_count, a.duration, c.illustrator, m.issue_number " +
                    "FROM resources r " +
                    "LEFT JOIN books b ON r.id = b.resource_id " +
                    "LEFT JOIN audiobooks a ON r.id = a.resource_id " +
                    "LEFT JOIN comics c ON r.id = c.resource_id " +
                    "LEFT JOIN magazines m ON r.id = m.resource_id";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String title = resultSet.getString("title");
                        String author = resultSet.getString("author");
                        String type = resultSet.getString("type");
                        Resource resource;
                        switch (type) {
                            case "book":
                                int pageCount = resultSet.getInt("page_count");
                                resource = new Book(title, author, pageCount);
                                break;
                            case "audiobook":
                                int duration = resultSet.getInt("duration");
                                resource = new Audiobook(title, author, duration);
                                break;
                            case "comic":
                                String illustrator = resultSet.getString("illustrator");
                                resource = new Comic(title, author, illustrator);
                                break;
                            case "magazine":
                                int issueNumber = resultSet.getInt("issue_number");
                                resource = new Magazine(title, author, issueNumber);
                                break;
                            default:
                                resource = new Resource(title, author, type);
                                break;
                        }

                        resource.setId(id);
                        resources.add(resource);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resources;
    }
}