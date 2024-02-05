package Repository;
import Database.Database;
import Resources.Resource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class BorrowRepository implements Repository<Borrow> {
    private static final String INSERT_BORROW_QUERY = "INSERT INTO borrows (user_id, resource_id, start_date, return_date) VALUES (?, ?, ?, ?)";
    @Override
    public void add(Borrow borrow) {
        int userId = borrow.getUserId();
        int resourceId = borrow.getResourceId();
        try (Connection connection = Database.getInstance().getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BORROW_QUERY)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, resourceId);
                preparedStatement.setDate(3, java.sql.Date.valueOf(borrow.getStartDate()));
                preparedStatement.setNull(4, Types.DATE);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error adding borrows to database.");
        }
    }
    @Override
    public void remove(Borrow borrow) {
        try (Connection connection = Database.getInstance().getConnection()) {
            String query = "UPDATE borrows SET return_date = CURRENT_DATE WHERE user_id = ? AND resource_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, borrow.getUserId());
                preparedStatement.setInt(2, borrow.getResourceId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error updating return date to database.");
        }
    }
    public int find(String username) {
        return -1;
    }
    @Override
    public List<Resource> findResources(String username) {
        return null;
    }
    public boolean isResourceBorrowed(String resourceTitle) {
        try (Connection connection = Database.getInstance().getConnection()) {
            String query = "SELECT 1 FROM borrows b " +
                    "JOIN resources r ON b.resource_id = r.id " +
                    "WHERE r.title = ? AND b.return_date IS NULL";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, resourceTitle);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public List<Borrow> read() {
        List<Borrow> borrows = new ArrayList<>();
        String query = "SELECT b.id, b.user_id, b.resource_id, b.start_date, b.return_date, u.name AS user_name, r.title AS resource_title, r.author AS resource_author " +
                "FROM borrows b " +
                "JOIN users u ON b.user_id = u.id " +
                "JOIN resources r ON b.resource_id = r.id";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int borrowId = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int resourceId = resultSet.getInt("resource_id");
                LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
                LocalDate returnDate = resultSet.getDate("return_date") != null ? resultSet.getDate("return_date").toLocalDate() : null;
                String userName = resultSet.getString("user_name");
                String resourceTitle = resultSet.getString("resource_title");
                String resourceAuthor = resultSet.getString("resource_author");
                Borrow borrow = new Borrow(userId, resourceId, startDate);
                borrow.setId(borrowId);
                borrow.setReturnDate(returnDate);
                borrow.setUsername(userName);
                borrow.setResourceTitle(resourceTitle);
                borrow.setResourceAuthor(resourceAuthor);
                borrows.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error retrieving borrow details from the database.");
        }

        return borrows;
    }
}