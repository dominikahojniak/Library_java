import Decorator.RatedResource;
import Factory.*;
import Repository.*;
import Resources.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibrarySystem{
    private Repository<Resource> resourceRepository;
    private Repository<Borrow> borrowRepository;
    private Repository<User> userRepository;
    private Repository<RatedResource> rateRepository;
    private Map<Class<? extends Resource>, Factory> resourceFactory;
    public LibrarySystem(Repository<Resource> resourceRepository, Repository<Borrow> borrowRepository, Repository<User> userRepository, Repository<RatedResource> rateRepository) {
        this.resourceRepository = resourceRepository;
        this.borrowRepository = borrowRepository;
        this.userRepository = userRepository;
        this.rateRepository = rateRepository;
        this.resourceFactory = new HashMap<>();
        resourceFactory.put(Book.class, new BookFactory(0));
        resourceFactory.put(Audiobook.class, new AudiobookFactory(0));
        resourceFactory.put(Comic.class, new ComicFactory(""));
        resourceFactory.put(Magazine.class, new MagazineFactory(0));
    }
    private void addResourceToDatabase(Resource resource) {
        resourceRepository.add(resource);
        System.out.println(resource.getTitle()+ " added to database.");
    }
    public void removeResourceFromDatabase(Resource resource) {
            resourceRepository.remove(resource);
            System.out.println(resource.getTitle() + " deleted from database.");
    }
    public void addUser(String name) {
        User newUser = new User(name);
        userRepository.add(newUser);
        System.out.println(newUser.getName()+ " added to database.");
    }
    public void removeUser(String name) {
        if(userRepository.find(name) == -1){
            System.err.println("User not exist");
        }else {
            User newUser = new User(name);
            userRepository.remove(newUser);
            System.out.println(newUser.getName() + " deleted from database.");
        }
    }
    public Resource createResource(Class<? extends Resource> resourceType, String title, String author,Object additionalParameter) {
        Factory factory = resourceFactory.get(resourceType);
        if (factory != null) {
            Resource resource = factory.createResource(title, author,additionalParameter);
            addResourceToDatabase(resource);
            return resource;
        } else {
            throw new IllegalArgumentException("Unsupported resource type");
        }
    }
    public void removeResource(String title, String author, Class<? extends Resource> resourceType) {
        if (resourceRepository.find(title) != -1) {
            Resource resource = createResource(resourceType, title, author, 0);
            removeResourceFromDatabase(resource);
        }else {
            System.err.println("Resource not exist");
        }
    }
    public void borrowResources(String username, String resourceTitle) {
        LocalDate startDate = LocalDate.now();
        if (!borrowRepository.isResourceBorrowed(resourceTitle)) {
        int userId = userRepository.find(username);
        int resourceId = resourceRepository.find(resourceTitle);

        if (userId != -1 && resourceId != -1) {
            Borrow borrow = new Borrow(userId, resourceId, startDate);
            borrowRepository.add(borrow);
            System.out.println("Successfully borrow to user: " + username);
        } else {
            System.err.println(username + " or " + resourceTitle+ " doesn't exist.");}
        } else {
            System.err.println("Sorry, but " + resourceTitle + " is already borrowed.");
        }
    }
    public void returnResources(String username, String resourceTitle,int rating) {
        int userId = userRepository.find(username);
        int resourceId = resourceRepository.find(resourceTitle);
        if (userId != -1 && resourceId != -1) {
            if(borrowRepository.isResourceBorrowed(resourceTitle)) {
                if (rating >= 1 && rating <= 5) {
                    Resource resource = new Resource(resourceTitle, username, "");
                    RatedResource ratedResource = new RatedResource(resource, rating);
                    rateRepository.add(ratedResource);
                } else if (rating!=0) {
                    System.err.println("Wrong Rating!!");
                }
                Borrow borrow = new Borrow(userId, resourceId, LocalDate.now());
                borrowRepository.remove(borrow);
                System.out.println("Successfully returned by user: " + username);
            }else {
                System.err.println(resourceTitle+ " is not borrowed.");
            }
        } else {
            System.err.println(username + " or " + resourceTitle+ " doesn't exist.");
        }
    }
    public void displayResources() {
        List<Resource> resources = resourceRepository.read();
        if (resources.isEmpty()) {
            System.out.println("Table is Empty.");
        } else {
            System.out.println("Resources:");
            System.out.println("-".repeat(150));
            for (Resource resource : resources) {
                LibrarySystemDisplay.displayResourceDetails(resource);
            }

        }
    }
    public void displayUsers() {
        List<User> users = userRepository.read();
        if (users.isEmpty()) {
            System.out.println("Table is Empty.");
        } else {
            System.out.println("User:");
            System.out.println("-".repeat(25));
            for (User user : users) {
                LibrarySystemDisplay.displayUser(user);
            }
        }
    }
    public void displayBorrows() {
        List<Borrow> borrows = borrowRepository.read();
        boolean activeBorrows = false;
        if (borrows.isEmpty()) {
            System.out.println("Table is Empty.");
        } else {
            System.out.println("Borrows:");
            System.out.println("-".repeat(160));
            for (Borrow borrow : borrows) {
                LibrarySystemDisplay.displayBorrow(borrow);
                if (borrow.getReturnDate() == null) {
                    activeBorrows = true;
                }
            }
            if (!activeBorrows) {
                System.out.println("No active borrows found.");
                System.out.println("-".repeat(160));
            }
        }
    }
    public void displayUserBorrows(String username) {
        List<Resource> borrowedResources = resourceRepository.findResources(username);
        if (borrowedResources.isEmpty()) {
            System.out.println("User " + username + " has no borrowed resources.");
        } else {
            System.out.println("User " + username + " has borrowed:");
            System.out.println("-".repeat(160));
            for (Resource resource : borrowedResources) {
                LibrarySystemDisplay.displayUserBorrow(resource);
            }
        }
    }
    public void displayRating() {
        List<RatedResource> ratedResources = rateRepository.read();
        if (ratedResources.isEmpty()) {
            System.out.println("Table is Empty.");
        } else {
            System.out.println("Rating:");
            System.out.println("-".repeat(25));
            for (RatedResource rating : ratedResources) {
                LibrarySystemDisplay.displayRating(rating);
            }
        }
    }
}