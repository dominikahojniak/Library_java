package Repository;
import Database.Database;
import Resources.Audiobook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AudiobookRepository implements ResourceDetailsRepository<Audiobook> {

    @Override
    public void addDetails(Audiobook audiobook, int resourceId) {
        try (Connection connection = Database.getInstance().getConnection()) {
            String query = "INSERT INTO audiobooks (resource_id, duration) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, resourceId);
                preparedStatement.setInt(2, audiobook.getDuration());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error adding details of the audiobook to database.");
        }
    }
}
