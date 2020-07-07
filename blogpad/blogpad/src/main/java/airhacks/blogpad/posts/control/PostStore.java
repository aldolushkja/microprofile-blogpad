package airhacks.blogpad.posts.control;

import airhacks.blogpad.posts.entity.Post;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PostStore {

    @Inject
    @ConfigProperty(name = "root.storage.dir")
    String storageDir;

    Path storageDirectoryPath;

    @PostConstruct
    public void init(){
        this.storageDirectoryPath = Path.of(this.storageDir);
    }

    public void save(Post post) throws IOException {
        String fileName = post.title;
        String stringified = serialize(post);
        write(fileName, stringified);
    }

    String serialize(Post post) {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.toJson(post);
    }

    void write(String fileName, String content) throws IOException {
        Path path = this.storageDirectoryPath.resolve(fileName);
        Files.writeString(path, content);
    }

    String read(String fileName) throws IOException {
        Path path = this.storageDirectoryPath.resolve(fileName);
        return Files.readString(path);
    }
}
