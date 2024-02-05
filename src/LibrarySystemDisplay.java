import Decorator.RatedResource;
import Repository.Borrow;
import Repository.User;
import Resources.*;

import java.time.format.DateTimeFormatter;

class LibrarySystemDisplay {
    public static void displayResourceDetails(Resource resource) {
        System.out.printf("Title: %-20s | Author: %-20s | Type: %-10s", resource.getTitle(), resource.getAuthor(), resource.getType());
        if (resource instanceof Book) {
            displayBookDetails((Book) resource);
        } else if (resource instanceof Audiobook) {
            displayAudiobookDetails((Audiobook) resource);
        } else if (resource instanceof Comic) {
            displayComicDetails((Comic) resource);
        } else if (resource instanceof Magazine) {
            displayMagazineDetails((Magazine) resource);
        } else {
            System.out.println("Details not available.");
        }

        System.out.println("-".repeat(150));
    }

    private static void displayBookDetails(Book book) {
        System.out.printf(" | Page Count: %-4d%n", book.getPageCount());
    }

    private static void displayAudiobookDetails(Audiobook audiobook) {
        System.out.printf(" | Duration: %-4d  minutes%n", audiobook.getDuration());
    }

    private static void displayComicDetails(Comic comic) {
        System.out.printf(" | Illustrator: %-4s%n", comic.getIllustrator());
    }

    private static void displayMagazineDetails(Magazine magazine) {
        System.out.printf(" | Issue Number: %-4d%n", magazine.getIssueNumber());
    }
    public static void displayUser(User user) {
        System.out.printf("Name: %-20s %n", user.getName());
        System.out.println("-".repeat(25));
    }
    public static void displayBorrow(Borrow borrow) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (borrow.getReturnDate() == null) {
            System.out.printf("ID: %-2d | .User: %-20s | Resource: %-20s | Author: %-10s | Start Date: %-20s | Return Date: %-20s %n",
                    borrow.getId(), borrow.getUsername(), borrow.getResourceTitle(), borrow.getResourceAuthor(),
                    borrow.getStartDate().format(formatter), (borrow.getReturnDate() != null ? borrow.getReturnDate().format(formatter) : "N/A"));
            System.out.println("-".repeat(160));
        }
    }
    public static void displayUserBorrow(Resource resource) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.printf("Title: %-20s | Author: %-20s | Type: %-10s | Start Date: %-20s %n",
                resource.getTitle(), resource.getAuthor(), resource.getType(), resource.getStartDate().format(formatter));
        System.out.println("-".repeat(160));
    }
    public static void displayRating(RatedResource rating) {
        System.out.printf("Title: %-20s | Rating: %-2d %n", rating.getDecoratedResource().getTitle(), rating.getRating());
        System.out.println("-".repeat(25));
    }
}
