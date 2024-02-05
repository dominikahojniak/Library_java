package Factory;

import Resources.Magazine;
import Resources.Resource;

public class MagazineFactory implements Factory {
    private int issueNumber;

    public MagazineFactory(int issueNumber) {
        this.issueNumber = issueNumber;
    }
    @Override
    public Resource createResource(String title, String author, Object additionalParameter) {
        return new Magazine(title, author, (Integer) additionalParameter);
    }
}
