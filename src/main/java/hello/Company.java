package hello;

public class Company {
    private final long id;
    private final String content;

    public Company(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
