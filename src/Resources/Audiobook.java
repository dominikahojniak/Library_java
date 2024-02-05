package Resources;

public class Audiobook extends Resource {
    private int duration; //in minutes

    public Audiobook(String title, String author, int duration) {
        super(title, author,"audiobook");
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }


}