package Repository;

import Resources.Comic;
import Database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ComicRepository implements ResourceDetailsRepository<Comic> {

    @Override
    public void addDetails(Comic comic, int resourceId) {
        try (Connection connection = Database.getInstance().getConnection()) {
            String query = "INSERT INTO comics (resource_id, illustrator) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, resourceId);
                preparedStatement.setString(2, comic.getIllustrator());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error adding details of the comic to database.");
        }
    }
}
