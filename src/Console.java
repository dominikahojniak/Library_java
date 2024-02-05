import Repository.BorrowRepository;
import Repository.RateRepository;
import Repository.ResourceRepository;
import Repository.UserRepository;
import Resources.Audiobook;
import Resources.Book;
import Resources.Comic;
import Resources.Magazine;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Console extends Show{
    private final LibrarySystem librarySystem;
    public Console() {
        this.librarySystem = new LibrarySystem(new ResourceRepository(), new BorrowRepository(),new UserRepository(), new RateRepository());
    }

    public static void main(String[] args) {
        Console console = new Console();
        console.menu();
    }
    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            Show.show();
            System.out.print("--->>>> ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    Show.showResources();
                    System.out.print("--->>>> ");
                    int pick = scanner.nextInt();
                    scanner.nextLine();
                    if(pick!=0) {
                        switch (pick) {
                            case 1:
                                addBook(scanner);
                                break;
                            case 2:
                                addAudiobook(scanner);
                                break;
                            case 3:
                                addComic(scanner);
                                break;
                            case 4:
                                addMagazine(scanner);
                                break;
                            default:
                                System.out.println("Ups, you wrote something wrong!");
                        }
                    }
                    break;
                case 2:
                    Show.showResources();
                    System.out.print("--->>>> ");
                    pick = scanner.nextInt();
                    scanner.nextLine();
                    if(pick!=0) {
                        switch (pick) {
                            case 1:
                                removeBook(scanner);
                                break;
                            case 2:
                                removeAudiobook(scanner);
                                break;
                            case 3:
                                removeComic(scanner);
                                break;
                            case 4:
                                removeMagazine(scanner);
                                break;
                            default:
                                System.out.println("Ups, you wrote something wrong!");
                        }
                    }
                    break;
                case 3:
                    addUser(scanner);
                    break;
                case 4:
                    removeUser(scanner);
                    break;
                case 5:
                    borrowResources(scanner);
                    break;
                case 6:
                    returnResources(scanner);
                    break;
                case 7:
                    displayResources();
                    break;
                case 8:
                    displayUsers();
                    break;
                case 9:
                    displayBorrows();
                    break;
                case 10:
                    displayUserBorrows(scanner);
                    break;
                case 11:
                    displayRating();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Ups, you wrote something wrong!");
            }
        }
    }
    private void borrowResources(Scanner scanner) {
        String username = getString("Username", scanner);
        String title = getString("Title", scanner);
        librarySystem.borrowResources(username, title);
    }
    private void returnResources(Scanner scanner) {
        String username = getString("Username", scanner);
        String title = getString("Title", scanner);
        System.out.println("Maybe leave a Rating(1-5)? Yes or No?");
        String decision = getString("", scanner);
        if("yes".equals(decision)){
            int rating = getInt("Rating", scanner);
            librarySystem.returnResources(username, title,rating);
        }else{
            librarySystem.returnResources(username, title,0);
        }
    }
    private void addUser(Scanner scanner) {
        String username = getString("Username", scanner);
        librarySystem.addUser(username);
    }
    private void removeUser(Scanner scanner) {
        String username = getString("Username", scanner);
        librarySystem.removeUser(username);
    }
    private void addBook(Scanner scanner) {
        try {
            String title = getString("Title", scanner);
            String author = getString("Author", scanner);
            int pageCount = getInt("Page Count", scanner);
            librarySystem.createResource(Book.class, title, author, pageCount);
        }catch (InputMismatchException e){
            System.err.println("Invalid input type.");
            scanner.nextLine();
        }
    }
    private void addAudiobook(Scanner scanner) {
        try{
            String title = getString("Title", scanner);
            String author = getString("Author", scanner);
            int duration = getInt("Duration", scanner);
        librarySystem.createResource(Audiobook.class, title, author, duration);
    }catch (InputMismatchException e){
        System.err.println("Invalid input type.");
        scanner.nextLine();
    }
    }
    private void addComic(Scanner scanner) {
        String title = getString("Title", scanner);
        String author = getString("Author", scanner);
        String illustrator = getString("Illustrator", scanner);
        if (!illustrator.matches("^[a-zA-Z\\s]+$")) {
            System.err.println("Invalid input.");
            return;
        }
        librarySystem.createResource(Comic.class,title, author, illustrator);
    }
    private void addMagazine(Scanner scanner) {
        try{
            String title = getString("Title", scanner);
            String author = getString("Author", scanner);
            int issueNumber = getInt("Issue Number", scanner);
        librarySystem.createResource(Magazine.class, title, author, issueNumber);
        }catch (InputMismatchException e){
            System.err.println("Invalid input type.");
            scanner.nextLine();
        }
    }
    private void removeBook(Scanner scanner) {
        String title = getString("Title", scanner);
        String author = getString("Author", scanner);
        librarySystem.removeResource(title, author,Book.class);
    }
    private void removeAudiobook(Scanner scanner) {
        String title = getString("Title", scanner);
        String author = getString("Author", scanner);
        librarySystem.removeResource(title, author, Audiobook.class);
    }
    private void removeComic(Scanner scanner) {
        String title = getString("Title", scanner);
        String author = getString("Author", scanner);
        librarySystem.removeResource(title, author,Comic.class);
    }
    private void removeMagazine(Scanner scanner) {
        String title = getString("Title", scanner);
        String author = getString("Author", scanner);
        librarySystem.removeResource(title, author, Magazine.class);
    }
    public void displayResources() {
        librarySystem.displayResources();
    }
    public void displayUsers() {
        librarySystem.displayUsers();
    }
    public void displayBorrows() {
        librarySystem.displayBorrows();
    }
    public void displayUserBorrows(Scanner scanner) {
        String username = getString("Username", scanner);
        librarySystem.displayUserBorrows(username);
    }
    public void displayRating() {
        librarySystem.displayRating();
    }
}
