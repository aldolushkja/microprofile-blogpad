package airhacks.blogpad.posts.control;

import airhacks.blogpad.posts.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PostStoreTest {

    private PostStore cut;

    @BeforeEach
    public void init(){
        this.cut = new PostStore();
    }

    @Test
    public void serialize(){
        String stringified = this.cut.serialize(new Post("Hello", "World"));
        assertNotNull(stringified);
        System.out.println("->" + stringified);
    }
}