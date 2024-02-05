package Repository;

import Resources.Resource;
import Database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository<User> {
    private static final String INSERT_USER_QUERY = "INSERT INTO users (name) VALUES (?)";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE name = ?";
    public void add(User user) {
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error adding user to database.");
        }
    }

    @Override
    public void remove(User user) {
        try (Connection connection = Database.getInstance().getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_QUERY)) {
                preparedStatement.setString(1, user.getName());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error deleting resources from database.");
        }
    }
    public int find(String username) {
        try (Connection connection = Database.getInstance().getConnection()) {
            String query = "SELECT id FROM users WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting userID from database.");
        }
        return -1;
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
    public List<User> read() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Database.getInstance().getConnection()) {
            String query = "SELECT * FROM users";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String name = resultSet.getString("name");
                        User user = new User(name);
                        users.add(user);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

}
