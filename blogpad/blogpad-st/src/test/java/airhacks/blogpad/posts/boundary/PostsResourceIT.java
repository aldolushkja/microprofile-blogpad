package airhacks.blogpad.posts.boundary;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author airhacks.com
 */
public class PostsResourceIT {

    private PostsResourceClient client;

    @BeforeEach
    public void init() {
        URI uri = URI.create("http://localhost:9080/blogpad/resources/");
        this.client = RestClientBuilder.
                newBuilder().
                baseUri(uri).
                build(PostsResourceClient.class);
    }

    @Test
    public void createNew() {
        String title = "Test/purpose";
        JsonObject post = Json.createObjectBuilder()
                .add("title", title)
                .add("content", "first st")
                .build();
        Response response = this.client.createNew(post);
        int status = response.getStatus();
        assertEquals(201, status);

        response = this.client.findPost(title);
        status = response.getStatus();
        assertEquals(200, status);

    }

    @Test
    public void updatePost() {
        String title = "Test/purpose";
        JsonObject post = Json.createObjectBuilder()
                .add("title", title)
                .add("content", "updated content 2")
                .build();
        Response response = this.client.update(post);
        int status = response.getStatus();
        assertEquals(201, status);

        response = this.client.findPost(title);
        status = response.getStatus();
        assertEquals(200, status);

    }

    @Test
    public void savePostWithInvalidTitle() {
        String title = "/";
        JsonObject post = Json.createObjectBuilder()
                .add("title", title)
                .add("content", "first st")
                .build();
        this.client.createNew(post);

        Response response = this.client.findPost("-");
        int status = response.getStatus();
        assertEquals(200, status);



    }

}