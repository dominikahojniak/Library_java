package Factory;

import Resources.Audiobook;
import Resources.Resource;

public class AudiobookFactory implements Factory {
    private int duration;

    public AudiobookFactory(int duration) {
        this.duration = duration;
    }
    @Override
    public Resource createResource(String title, String author, Object additionalParameter) {
        return new Audiobook(title, author, (Integer) additionalParameter);
    }
}
