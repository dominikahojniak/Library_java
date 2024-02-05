package Repository;

import Database.Database;
import Decorator.RatedResource;
import Resources.Resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RateRepository implements Repository<RatedResource>{
    private static final String INSERT_RATING_QUERY = "INSERT INTO ratings (user_id, resource_id, rate) VALUES (?, ?, ?)";
    private static final String DELETE_RATING_QUERY = "DELETE FROM ratings WHERE user_id = ? AND resource_id = ?";
    @Override
    public void add(RatedResource item) {
            int userId = getUserId(item.getDecoratedResource().getAuthor());
            int resourceId = getResourceId(item.getDecoratedResource().getTitle());
            if (userId != -1 && resourceId != -1) {
                try (Connection connection = Database.getInstance().getConnection()) {
                    try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RATING_QUERY)) {
                        preparedStatement.setInt(1, userId);
                        preparedStatement.setInt(2, resourceId);
                        preparedStatement.setInt(3, item.getRating());
                        preparedStatement.executeUpdate();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    private int getUserId(String username) {
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM users WHERE name = ?")) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int getResourceId(String resourceTitle) {
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM resources WHERE title = ?")) {
            preparedStatement.setString(1, resourceTitle);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    @Override
    public List<RatedResource> read() {
        List<RatedResource> ratedResources = new ArrayList<>();
        try (Connection connection = Database.getInstance().getConnection()) {
            String query = "SELECT r.title, ra.rate FROM resources r " +
                    "JOIN ratings ra ON r.id = ra.resource_id";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String title = resultSet.getString("title");
                    int rating = resultSet.getInt("rate");
                    Resource resource = new Resource(title, "", "");
                    RatedResource ratedResource = new RatedResource(resource, rating);
                    ratedResources.add(ratedResource);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ratedResources;
    }

    @Override
    public int find(String resourceTitle) {
        return 0;
    }

    @Override
    public List<Resource> findResources(String username) {
        return null;
    }


    @Override
    public boolean isResourceBorrowed(String resourceTitle) {
        return false;
    }

    @Override
    public void remove(RatedResource item) {
        int userId = getUserId(item.getDecoratedResource().getAuthor());
        int resourceId = getResourceId(item.getDecoratedResource().getTitle());
        if (userId != -1 && resourceId != -1) {
            try (Connection connection = Database.getInstance().getConnection()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RATING_QUERY)) {
                    preparedStatement.setInt(1, userId);
                    preparedStatement.setInt(2, resourceId);
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Error deleting resources from database.");
            }
        }
    }
}
