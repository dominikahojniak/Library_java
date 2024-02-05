package Repository;
import Database.Database;
import Resources.Magazine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MagazineRepository implements ResourceDetailsRepository<Magazine> {
    @Override
    public void addDetails(Magazine magazine, int resourceId) {
        try (Connection connection = Database.getInstance().getConnection()) {
            String query = "INSERT INTO magazines (resource_id, issue_number) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, resourceId);
                preparedStatement.setInt(2, magazine.getIssueNumber());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error adding details of the magazine to database.");
        }
    }
}
