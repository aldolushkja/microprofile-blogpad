package airhacks.blogpad.posts.control;

import airhacks.blogpad.posts.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PostStoreTest {

    private PostStore cut;

    @BeforeEach
    public void init() {
        this.cut = new PostStore();
    }

    @Test
    public void serialize() {
        String stringified = this.cut.serialize(new Post("Hello", "World"));
        assertNotNull(stringified);
        System.out.println("->" + stringified);
    }

    @Test
    public void writeString() throws IOException {
        String path = "target/nextPost";
        String expected = "hello, duke";
        this.cut.write(path, expected);
        String actual = this.cut.read(path);
        assertEquals(expected, actual);
    }

}