package Repository;
import Database.Database;
import Resources.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookRepository implements ResourceDetailsRepository<Book> {
        private static final String INSERT_BOOK_DETAILS_QUERY = "INSERT INTO books (resource_id, page_count) VALUES (?, ?)";

        @Override
        public void addDetails(Book book, int resourceId) {
            try (Connection connection = Database.getInstance().getConnection()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOK_DETAILS_QUERY)) {
                    preparedStatement.setInt(1, resourceId);
                    preparedStatement.setInt(2, book.getPageCount());
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Error adding details of the book to the database.");
            }
        }
    }

