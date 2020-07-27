package alushkja.blogpad.posts.control;

public class ConstraitError {
    public String path;
    public String message;
    public String constraintType;
    public String value;

    public ConstraitError(String path, String message, String constraintType, String value) {
        this.path = path;
        this.message = message;
        this.constraintType = constraintType;
        this.value = value;
    }
}
