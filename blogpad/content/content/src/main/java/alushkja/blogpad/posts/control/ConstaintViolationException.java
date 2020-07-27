package alushkja.blogpad.posts.control;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ConstaintViolationException extends WebApplicationException {

    public ConstaintViolationException(String json) {
        super(Response.status(422)
                .entity(json)
                .build());
    }
}
