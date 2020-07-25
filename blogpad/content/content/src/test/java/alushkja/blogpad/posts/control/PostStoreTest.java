package alushkja.blogpad.posts.control;

import alushkja.blogpad.posts.entity.Post;
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
        this.cut.storageDir = "target";
        this.cut.normalizer = TitleNormalizerTest.create();
        this.cut.init();
    }

    @Test
    public void serialize() {
        String stringified = this.cut.serialize(new Post("Hello", "World"));
        assertNotNull(stringified);
        System.out.println("->" + stringified);
    }

    @Test
    public void writeString() throws IOException {
        String path = "nextPost";
        String expected = "hello, duke";
        this.cut.write(path, expected);
        String actual = this.cut.readString(path);
        assertEquals(expected, actual);
    }

    @Test
    public void savePost() throws IOException {
        String title = "first";
        Post expected = new Post(title, "hey,duke");
        this.cut.createNew(expected);
        Post actual = this.cut.read(title);
        assertEquals(expected.title, actual.title);
        assertEquals(expected.content, actual.content);
    }

}