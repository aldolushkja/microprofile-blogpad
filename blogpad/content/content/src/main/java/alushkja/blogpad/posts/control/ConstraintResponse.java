package alushkja.blogpad.posts.control;

import java.util.List;

public class ConstraintResponse {

    public String message;
    public List<ConstraitError> errors;

    public ConstraintResponse(String message, List<ConstraitError> errors) {
        this.message = message;
        this.errors = errors;
    }
}
