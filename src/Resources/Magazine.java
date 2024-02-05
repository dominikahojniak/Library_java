package Resources;

import Resources.Resource;

public class Magazine extends Resource {
    private int issueNumber;

    public Magazine(String title, String author, int issueNumber) {
        super(title, author,"magazine");
        this.issueNumber = issueNumber;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

}